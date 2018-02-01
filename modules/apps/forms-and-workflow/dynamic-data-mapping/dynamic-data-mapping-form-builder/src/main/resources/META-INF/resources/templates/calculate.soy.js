/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from calculate.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCalculate.
 * @public
 */

goog.module('DDMCalculate.incrementaldom');

/** @suppress {extraRequire} */
var soy = goog.require('soy');
/** @suppress {extraRequire} */
var soydata = goog.require('soydata');
/** @suppress {extraRequire} */
goog.require('goog.i18n.bidi');
/** @suppress {extraRequire} */
goog.require('goog.asserts');
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
 * @param {Object<string, *>=} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  ie_open('div', null, null,
      'class', 'calculate-container');
    ie_void('div', null, null,
        'class', 'calculate-container-calculator-component col-md-3');
    ie_void('div', null, null,
        'class', 'calculate-container-fields col-md-9');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMCalculate.render';
}

exports.render.params = [];
exports.render.types = {};
templates = exports;
return exports;

});

class DDMCalculate extends Component {}
Soy.register(DDMCalculate, templates);
export { DDMCalculate, templates };
export default templates;
/* jshint ignore:end */
