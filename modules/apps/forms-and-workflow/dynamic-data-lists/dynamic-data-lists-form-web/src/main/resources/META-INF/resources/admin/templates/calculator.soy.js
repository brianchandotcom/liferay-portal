// This file was automatically generated from calculator.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.calculator.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.calculator == 'undefined') { ddl.calculator = {}; }


ddl.calculator.settings = function(opt_data, opt_ignored) {
  return '<div class="calculator-add-field-button-container"><button class="btn btn-default btn-lg btn-primary calculator-add-field-button ddl-button" type="button"><span class="">' + soy.$$escapeHtml(opt_data.strings.addField) + '</span></button></div><div class="calculator-button-area"><ul class="calculator-buttons calculator-buttons-numbers"><li class="border-top-left button-padding-icons calculator-button" data-calculator-key="backspace">' + soy.$$filterNoAutoescape(opt_data.calculatorAngleLeft) + '</li><li class="calculator-button" data-calculator-key="(">(</li><li class="border-top-right calculator-button" data-calculator-key=")">)</li><li class="calculator-button" data-calculator-key="1">1</li><li class="calculator-button" data-calculator-key="2">2</li><li class="calculator-button" data-calculator-key="3">3</li><li class="calculator-button" data-calculator-key="4">4</li><li class="calculator-button" data-calculator-key="5">5</li><li class="calculator-button" data-calculator-key="6">6</li><li class="calculator-button" data-calculator-key="7">7</li><li class="calculator-button" data-calculator-key="8">8</li><li class="calculator-button" data-calculator-key="9">9</li><li class="border-bottom-left button-two-columns calculator-button" data-calculator-key="0">0</li><li class="border-bottom-right calculator-button" data-calculator-key=".">.</li></ul><ul class="calculator-buttons calculator-buttons-operators"><li class="border-top-left border-top-right button-padding-icons calculator-add-operator-button-area">' + soy.$$filterNoAutoescape(opt_data.calculatorEllipsis) + '<div class="calculator-add-operator-button"></div><div class="container-list-advanced-operators"></div></li><li class="calculator-button" data-calculator-key="+">+</li><li class="calculator-button" data-calculator-key="-">-</li><li class="calculator-button" data-calculator-key="*">*</li><li class="border-bottom-left border-bottom-right calculator-button" data-calculator-key="/">/</li></ul></div>';
};
if (goog.DEBUG) {
  ddl.calculator.settings.soyTemplateName = 'ddl.calculator.settings';
}
