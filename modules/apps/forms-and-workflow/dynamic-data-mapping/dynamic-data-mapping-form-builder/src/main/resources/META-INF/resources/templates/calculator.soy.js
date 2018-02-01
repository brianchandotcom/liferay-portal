/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from calculator.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCalculator.
 * @public
 */

goog.module('DDMCalculator.incrementaldom');

/** @suppress {extraRequire} */
var soy = goog.require('soy');
/** @suppress {extraRequire} */
var soydata = goog.require('soydata');
/** @suppress {extraRequire} */
goog.require('goog.asserts');
/** @suppress {extraRequire} */
goog.require('soy.asserts');
/** @suppress {extraRequire} */
goog.require('goog.i18n.bidi');
/** @suppress {extraRequire} */
goog.require('goog.string');
var IncrementalDom = goog.require('incrementaldom');
var ie_open = IncrementalDom.elementOpen;
var ie_close = IncrementalDom.elementClose;
var ie_void = IncrementalDom.elementVoid;
var ie_open_start = IncrementalDom.elementOpenStart;
var ie_open_end = IncrementalDom.elementOpenEnd;
var itext = IncrementalDom.text;
var iattr = IncrementalDom.attr;


/**
 * @param {{
 *    strings: {addField: string},
 *    calculatorAngleLeft: (?soydata.SanitizedHtml|string|undefined),
 *    calculatorEllipsis: (?soydata.SanitizedHtml|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [addField: string].");
  soy.asserts.assertType(opt_data.calculatorAngleLeft == null || (opt_data.calculatorAngleLeft instanceof Function) || (opt_data.calculatorAngleLeft instanceof soydata.UnsanitizedText) || goog.isString(opt_data.calculatorAngleLeft), 'calculatorAngleLeft', opt_data.calculatorAngleLeft, '?soydata.SanitizedHtml|string|undefined');
  var calculatorAngleLeft = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.calculatorAngleLeft);
  soy.asserts.assertType(opt_data.calculatorEllipsis == null || (opt_data.calculatorEllipsis instanceof Function) || (opt_data.calculatorEllipsis instanceof soydata.UnsanitizedText) || goog.isString(opt_data.calculatorEllipsis), 'calculatorEllipsis', opt_data.calculatorEllipsis, '?soydata.SanitizedHtml|string|undefined');
  var calculatorEllipsis = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.calculatorEllipsis);
  ie_open('div');
    ie_open('div', null, null,
        'class', 'calculator-add-field-button-container');
      ie_open('button', null, null,
          'class', 'btn btn-lg btn-primary calculator-add-field-button ddm-button',
          'type', 'button');
        ie_open('span', null, null,
            'class', '');
          var dyn0 = strings.addField;
          if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        ie_close('span');
      ie_close('button');
    ie_close('div');
    ie_open('div', null, null,
        'class', 'calculator-button-area');
      ie_open('ul', null, null,
          'class', 'calculator-buttons calculator-buttons-numbers');
        ie_open('li', null, null,
            'class', 'border-top-left btn btn-secondary button-padding-icons calculator-button',
            'data-calculator-key', 'backspace');
          var dyn1 = calculatorAngleLeft;
          if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '(');
          itext('(');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary border-top-right calculator-button',
            'data-calculator-key', ')');
          itext(')');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '1');
          itext('1');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '2');
          itext('2');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '3');
          itext('3');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '4');
          itext('4');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '5');
          itext('5');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '6');
          itext('6');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '7');
          itext('7');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '8');
          itext('8');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '9');
          itext('9');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary border-bottom-left button-two-columns calculator-button',
            'data-calculator-key', '0');
          itext('0');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary border-bottom-right calculator-button',
            'data-calculator-key', '.');
          itext('.');
        ie_close('li');
      ie_close('ul');
      ie_open('ul', null, null,
          'class', 'calculator-buttons calculator-buttons-operators');
        ie_open('li', null, null,
            'class', 'border-top-left border-top-right btn btn-secondary button-padding-icons calculator-add-operator-button-area');
          var dyn2 = calculatorEllipsis;
          if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
          ie_void('div', null, null,
              'class', 'calculator-add-operator-button');
          ie_void('div', null, null,
              'class', 'container-list-advanced-operators');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '+');
          itext('+');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '-');
          itext('-');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary calculator-button',
            'data-calculator-key', '*');
          itext('*');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'btn btn-secondary border-bottom-left border-bottom-right calculator-button',
            'data-calculator-key', '/');
          itext('/');
        ie_close('li');
      ie_close('ul');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMCalculator.render';
}

exports.render.params = ["calculatorAngleLeft","calculatorEllipsis"];
exports.render.types = {"calculatorAngleLeft":"html","calculatorEllipsis":"html"};
templates = exports;
return exports;

});

class DDMCalculator extends Component {}
Soy.register(DDMCalculator, templates);
export { DDMCalculator, templates };
export default templates;
/* jshint ignore:end */
