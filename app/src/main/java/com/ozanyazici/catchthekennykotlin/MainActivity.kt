package com.ozanyazici.catchthekennykotlin

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import com.ozanyazici.catchthekennykotlin.databinding.ActivityMainBinding

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.R
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var score = 0;
    val random = Random(System.currentTimeMillis())
    var randomNumber  = 0
    private var imageViews = listOf<ImageView>()
    private var runnable = Runnable{}
    private var handler : Handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        moveKenny()

    }

    override fun onStart() {
        super.onStart()

        object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                binding.textViewTime.text = "Time: ${p0 / 1000}"

            }

            override fun onFinish() {
                binding.textViewTime.text = "Time is Over"
                dialogAlert()
                handler.removeCallbacks(runnable)
                hideImages()
            }

        }.start()

    }

    fun scorePlus(view : View) {
        score++
        binding.textViewScore.text = "Score: ${score}"

    }

    fun dialogAlert() {
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Game Over")
        alert.setMessage("Do you want play again?")

        //Restart
        alert.setPositiveButton("Yes"
        ) { p0, p1 -> val intent = intent
            finish()
            startActivity(intent)}

        alert.setNegativeButton("No"
        ) { p0, p1 -> Toast.makeText(this@MainActivity, "OK", Toast.LENGTH_LONG).show() }

        alert.show()
    }

    private fun hideImages() {
        imageViews = listOf<ImageView>(binding.imageView,binding.imageView2,binding.imageView3,binding.imageView4,binding.imageView5,binding.imageView6,binding.imageView7,binding.imageView8,binding.imageView9)

        for (imageView in imageViews) {
            imageView.visibility = View.INVISIBLE
        }
    }

    private fun moveKenny() {

        runnable = object : Runnable{
            override fun run() {

                hideImages()

                randomNumber = random.nextInt(9)
                imageViews[randomNumber].visibility = View.VISIBLE
                handler.postDelayed(runnable, 600)
            }

        }
        handler.post(runnable)

    }

    fun playAgain(view : View) {
        if(binding.textViewTime.text == "Time is over"){
            dialogAlert()
        }
    }

}