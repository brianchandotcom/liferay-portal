/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from calculator.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCalculator.
 * @public
 */

goog.module('DDMCalculator.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {{
 *  strings: {addField: (!goog.soy.data.SanitizedContent|string)},
 *  calculatorAngleLeft: (function()|null|undefined),
 *  calculatorEllipsis: (function()|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{addField: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{addField: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {function()|null|undefined} */
  var calculatorAngleLeft = soy.asserts.assertType(opt_data.calculatorAngleLeft == null || goog.isFunction(opt_data.calculatorAngleLeft), 'calculatorAngleLeft', opt_data.calculatorAngleLeft, 'function()|null|undefined');
  /** @type {function()|null|undefined} */
  var calculatorEllipsis = soy.asserts.assertType(opt_data.calculatorEllipsis == null || goog.isFunction(opt_data.calculatorEllipsis), 'calculatorEllipsis', opt_data.calculatorEllipsis, 'function()|null|undefined');
  incrementalDom.elementOpen('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'calculator-add-field-button-container');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-lg btn-primary calculator-add-field-button ddm-button');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', '');
        incrementalDom.elementOpenEnd();
          soyIdom.print(strings.addField);
        incrementalDom.elementClose('span');
      incrementalDom.elementClose('button');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'calculator-button-area');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'calculator-buttons calculator-buttons-numbers');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'border-top-left btn btn-secondary button-padding-icons calculator-button');
            incrementalDom.attr('data-calculator-key', 'backspace');
        incrementalDom.elementOpenEnd();
          soyIdom.print(calculatorAngleLeft);
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '(');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('(');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary border-top-right calculator-button');
            incrementalDom.attr('data-calculator-key', ')');
        incrementalDom.elementOpenEnd();
          incrementalDom.text(')');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '1');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('1');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '2');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('2');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '3');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('3');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '4');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('4');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '5');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('5');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '6');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('6');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '7');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('7');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '8');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('8');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '9');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('9');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary border-bottom-left button-two-columns calculator-button');
            incrementalDom.attr('data-calculator-key', '0');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('0');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary border-bottom-right calculator-button');
            incrementalDom.attr('data-calculator-key', '.');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('.');
        incrementalDom.elementClose('li');
      incrementalDom.elementClose('ul');
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'calculator-buttons calculator-buttons-operators');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'border-top-left border-top-right btn btn-secondary button-padding-icons calculator-add-operator-button-area');
        incrementalDom.elementOpenEnd();
          soyIdom.print(calculatorEllipsis);
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'calculator-add-operator-button');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('div');
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'container-list-advanced-operators');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('div');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '+');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('+');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '-');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('-');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary calculator-button');
            incrementalDom.attr('data-calculator-key', '*');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('*');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'btn btn-secondary border-bottom-left border-bottom-right calculator-button');
            incrementalDom.attr('data-calculator-key', '/');
        incrementalDom.elementOpenEnd();
          incrementalDom.text('/');
        incrementalDom.elementClose('li');
      incrementalDom.elementClose('ul');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  strings: {addField: (!goog.soy.data.SanitizedContent|string)},
 *  calculatorAngleLeft: (function()|null|undefined),
 *  calculatorEllipsis: (function()|null|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMCalculator.render';
}

exports.render.params = ["strings","calculatorAngleLeft","calculatorEllipsis"];
exports.render.types = {"strings":"[addField: string]","calculatorAngleLeft":"html","calculatorEllipsis":"html"};
templates = exports;
return exports;

});

class DDMCalculator extends Component {}
Soy.register(DDMCalculator, templates);
export { DDMCalculator, templates };
export default templates;
/* jshint ignore:end */
