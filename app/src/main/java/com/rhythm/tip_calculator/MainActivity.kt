package com.rhythm.tip_calculator

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * Values and Variable declarations:
     * MAX_PARTYSIZE — Defines the maximum partySize the application allows.
     * MAX_TIP_PERCENT — Defines the maximum Tip Percentage the application allows.
     * DEFAULT_TIP_PERCENT — Defines the default Tip Percentage.
     * DEFAULT_PARTYSIZE — Defines the default partySize.
     * cur_tip — Holds the current tip percentage.
     * amount — Holds the amount string.
     * partysize — Holds the current partySize
     * pointflag — A flag that shows the decimal status. The value of pointflag are given below with their indications:
     *             0 —> No decimal point exists.
     *             1 —> Decimal point exists.
     *             2 —> A numeral after the decimal point exists.
     *             3 —> Two numerals after the decimal point exists.
     */

    private val MAX_PARTY_SIZE = 20
    private val MAX_TIP_PERCENT = 25
    private val DEFAULT_TIP_PERCENT = 15
    private val DEFAULT_PARTYSIZE = 1

    private var cur_tip = 15
    private var amount = "0"
    private var partysize = 1
    private var pointflag = 0

    /**
     * Functions to update the TextView values and seekbar progress:
     * updateamount() — This function updates the amount EditText from amount variable.
     * updatetip() — This function updates the tip TextView and progress of the Tip seekbar from the cur_tip variable.
     * updatepartysize() — This function updates the partysize (No. of people) TextView and sets the progress of the partysize seekbar from partysize variable.
     * resetError() — This function resets the corresponding error TextView based on the passed argument.
     *                If num is 1, the error Textview of tip% will reset and if num is 2, the error TextView of partysize will reset.
     * reset() — This function will reset all values, errors and fields to default.
     * addtoamount() — This function adds the passed Integer argument to the amount string.
     */

    private fun  updateamount (){
        amount_edittext.setText(amount)
    }
    private fun updatetip(){
        tv_tip.setText(cur_tip.toString()+"%")
        sb_tip.setProgress(cur_tip)
    }
    private fun updatepartysize(){
        sb_partysize.setProgress(partysize-1)
        tv_partysize.setText(partysize.toString())
    }

    private fun resetError (num: Int){
        if(num == 1)
            tv_error.setText("")
        else if(num == 2)
            tv_error2.setText("")
    }

    private fun reset(){
        amount="0"
        pointflag=0
        partysize = DEFAULT_PARTYSIZE
        cur_tip = DEFAULT_TIP_PERCENT
        resetError(1)
        resetError(2)
        updatepartysize()
        updateamount()
        updatetip()
        tv_tipamount.setText("")
        tv_total.setText("")
        tv_perperson.setText("")
    }

    private fun addtoamount (num: Int){
        if (amount=="0"){                       //If the amount is 0, then replace the '0' by the entered value
            amount=num.toString()
            updateamount()
        }
        else{                                   //Else, add the number to the amount string
            if(pointflag!=3){
                if(pointflag == 1 || pointflag == 2)
                    ++pointflag
                amount+=num
                updateamount()
            }
            else
                Toast.makeText(this, "Only 2 decimal Places.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculate(){
        var tip = (amount.toFloat()*cur_tip)/100
        var total = amount.toFloat() + tip
        var perperson = total/partysize
        tv_tipamount.setText("$%.2f".format(tip))
        tv_total.setText("$%.2f".format(total))
        tv_perperson.setText("$%.2f".format(perperson))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var acbar = this.supportActionBar
        val hide = acbar?.hide()    // The action bar of the application is hidden.

        reset()                 //To Initialize all the variable values and fields

        /**
         * To update values and seekbar while pressing '+' or '–', onClickListener for each '+' and '–' buttons are set with appropriate error responses and conditions.
         * buttonplus — '+' button to increase and update the tip percentage.
         * buttonminus — '–' button to decrease and update the tip percentage.
         * buttonplus1 — '+' button to increase and update the partySize value.
         * buttonminus2 — '–' button to decrease and update the partySize value.
         * These buttons are associated with error messages that restricts the user to go above/below the allowed tip or partySize range.
         */

        //Tip percentage "+" button
        button_plus.setOnClickListener(){
            if(cur_tip<MAX_TIP_PERCENT){
                cur_tip = ++cur_tip;
                updatetip()
                resetError(1)
            }
            else
                tv_error.setText("Sorry, the tip cannot be greater than 25%.")
        }

        //Tip percentage "-" button
        button_minus.setOnClickListener(){
            if(cur_tip>0){
                cur_tip = --cur_tip;
                updatetip()
                resetError(1)
            }
            else
                tv_error.setText("Sorry, the tip cannot be less than 0%.")
        }

        //PartySize "+" button
        button_plus2.setOnClickListener(){
            if(partysize<MAX_PARTY_SIZE){
                partysize = ++partysize;
                updatepartysize()
                resetError(2)
            }
            else
                tv_error2.setText("Sorry, the Party Size cannot be greater than $MAX_PARTY_SIZE.")
        }

        //PartySize "-" button
        button_minus2.setOnClickListener(){
            if(partysize>1){
                partysize = --partysize;
                updatepartysize()
                resetError(2)
            }
            else
                tv_error2.setText("Sorry, the Party Size cannot be less than 1.")
        }

        /**
         * OnClickListener is set for each Keypad button.
         * Each button calls addtoamount() function with corresponding values.
         * The function adds the values to the amount string.
         */

        //Keypad Button '1'
        btn_1.setOnClickListener(){
            addtoamount(1)
        }

        //Keypad Button '2'
        btn_2.setOnClickListener(){
            addtoamount(2)
        }

        //Keypad Button '3'
        btn_3.setOnClickListener(){
            addtoamount(3)
        }

        //Keypad Button '4'
        btn_4.setOnClickListener(){
            addtoamount(4)
        }

        //Keypad Button '5'
        btn_5.setOnClickListener(){
            addtoamount(5)
        }

        //Keypad Button '6'
        btn_6.setOnClickListener(){
            addtoamount(6)
        }

        //Keypad Button '7'
        btn_7.setOnClickListener(){
            addtoamount(7)
        }

        //Keypad Button '8'
        btn_8.setOnClickListener(){
            addtoamount(8)
        }

        //Keypad Button '9'
        btn_9.setOnClickListener(){
            addtoamount(9)
        }

        //Keypad Button '0'
        btn_0.setOnClickListener(){
            addtoamount(0)
        }

        //Keypad Button '.'
        btn_point.setOnClickListener(){
            if(pointflag==0){
                amount+="."
                updateamount()
                pointflag=1
            }
        }

        //Keypad Button 'backspace'
        btn_back.setOnClickListener(){
            if(amount.length == 1){
                amount="0"
                updateamount()
            }
            else{
                if(pointflag!=0)
                    --pointflag
                amount=amount.substring(0..amount.length-2)
                updateamount()
            }
        }

        //'Clear' Button
        btn_clear.setOnClickListener(){
            reset()
        }

        //'Calculate' Button
        btn_calculate.setOnClickListener(){
            calculate()
        }

        //Tip percentage SeekBar
        sb_tip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                cur_tip = p1
                updatetip()
                resetError(1)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        //PartySize SeekBar
        sb_partysize.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                partysize = p1+1
                tv_partysize.setText((partysize).toString())
                resetError(2)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }
}
