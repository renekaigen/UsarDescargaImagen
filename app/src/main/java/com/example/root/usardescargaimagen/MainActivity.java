package com.example.root.usardescargaimagen;

/**
 * Created by root on 27/04/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends Activity {
    Button load_img,save_img;
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;
    String image = "naruhina";
    EditText etImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load_img = (Button)findViewById(R.id.load);
        save_img = (Button)findViewById(R.id.save);
        img = (ImageView)findViewById(R.id.img);
        etImagen=(EditText) findViewById(R.id.etImagen);

        load_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new LoadImage().execute("https://raw.githubusercontent.com/renekaigen/UsarDescargaImagen/master/naruhina.jpg");
            }
        });

        save_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String nombre_imagen = etImagen.getText().toString();
                if (nombre_imagen == null || nombre_imagen.equals("")) {
                    nombre_imagen=image;
                }
                Log.d("imagen", "" + nombre_imagen);
                guardar_imagen(nombre_imagen + ".jpg");
            }
        });


    }

    public void guardar_imagen(String nombre_imagen) {
        if (bitmap != null) {
            File sdCard = Environment.getExternalStorageDirectory();
            File path = new File(sdCard.getAbsolutePath() + "/myImages");
            if (!path.exists())
                path.mkdirs();
            File file = new File(path,nombre_imagen);
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();
                Toast.makeText(MainActivity.this, "Imagen guardada", Toast.LENGTH_LONG)
                        .show();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        } else {
            Toast.makeText(MainActivity.this, "No se ha bajado la imagen",
                    Toast.LENGTH_LONG).show();
        }

    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

}