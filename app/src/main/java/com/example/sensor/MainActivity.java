package com.example.sensor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorListener;
    private Button button;
    private boolean started;

    private TextView value_x, value_y, value_z;

    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal z;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        value_x= findViewById(R.id.xval);
        value_y= findViewById(R.id.yval);
        value_z= findViewById(R.id.zval);
        button= findViewById(R.id.button);
        started= false;
        button.setBackgroundColor(Color.rgb(0,220,120));



        sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                x= new BigDecimal(event.values[0]);
                y = new BigDecimal(event.values[1]);
                z = new BigDecimal(event.values[2]);
                x= x.setScale(4, BigDecimal.ROUND_HALF_UP);
                y= y.setScale(4, BigDecimal.ROUND_HALF_UP);
                z= z.setScale(4, BigDecimal.ROUND_HALF_UP);

                value_x.setText(String.format("%s",x));
                value_y.setText(String.format("%s",y));
                value_z.setText(String.format("%s",z));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!started){
                    sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_UI);
                    started=true;
                    String label= "Stop";
                    button.setText(label);

                    button.setBackgroundColor(Color.rgb(255,60,75));
                }
                else {
                    sensorManager.unregisterListener(sensorListener);
                    started=false;
                    String label= "Start";
                    button.setText(label);
                    button.setBackgroundColor(Color.rgb(0,220,120));

                }
            }
        });


    }
}
