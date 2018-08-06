/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from Fields.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace Fields.
 * @public
 */

goog.module('Fields.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {$render.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $render = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var type = soy.asserts.assertType(opt_data.type == null || (goog.isString(opt_data.type) || opt_data.type instanceof goog.soy.data.SanitizedContent), 'type', opt_data.type, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var $tmp = type;
  switch (goog.isObject($tmp) ? $tmp.toString() : $tmp) {
    case 'text':
      $Text(null, opt_ijData);
      break;
    case 'select':
      $Select(null, opt_ijData);
      break;
    case 'radio':
      $Radio(null, opt_ijData);
      break;
    default:
      incrementalDom.elementOpen('div');
      incrementalDom.text('Undefined template.');
      incrementalDom.elementClose('div');
  }
};
exports.render = $render;
/**
 * @typedef {{
 *  type: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'Fields.render';
}


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $Text = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  incrementalDom.elementOpen('p');
  incrementalDom.text('Text Field');
  incrementalDom.elementClose('p');
};
exports.Text = $Text;
if (goog.DEBUG) {
  $Text.soyTemplateName = 'Fields.Text';
}


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $Radio = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  incrementalDom.elementOpen('p');
  incrementalDom.text('Radio Field');
  incrementalDom.elementClose('p');
};
exports.Radio = $Radio;
if (goog.DEBUG) {
  $Radio.soyTemplateName = 'Fields.Radio';
}


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $Select = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  incrementalDom.elementOpen('p');
  incrementalDom.text('Select Field');
  incrementalDom.elementClose('p');
};
exports.Select = $Select;
if (goog.DEBUG) {
  $Select.soyTemplateName = 'Fields.Select';
}

exports.render.params = ["type"];
exports.render.types = {"type":"string"};
exports.Text.params = [];
exports.Text.types = {};
exports.Radio.params = [];
exports.Radio.types = {};
exports.Select.params = [];
exports.Select.types = {};
templates = exports;
return exports;

});

class Fields extends Component {}
Soy.register(Fields, templates);
export { Fields, templates };
export default templates;
/* jshint ignore:end */
