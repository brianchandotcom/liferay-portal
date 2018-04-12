/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from calculate.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCalculate.
 * @public
 */

goog.module('DDMCalculate.incrementaldom');

var incrementalDom = goog.require('incrementaldom');
var soyIdom = goog.require('soy.idom');


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'calculate-container');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'calculate-container-calculator-component col-md-3');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'calculate-container-fields col-md-9');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
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
