/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from captcha.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCaptcha.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMCaptcha.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2_ffe4bfd9(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_ffe4bfd9 = __deltemplate_s2_ffe4bfd9;
if (goog.DEBUG) {
  __deltemplate_s2_ffe4bfd9.soyTemplateName = 'DDMCaptcha.__deltemplate_s2_ffe4bfd9';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'captcha', 0, __deltemplate_s2_ffe4bfd9);


/**
 * @param {{
 *  html: function(),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var html = soy.asserts.assertType(goog.isFunction(opt_data.html), 'html', opt_data.html, 'function()');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-captcha');
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    html();
    incrementalDom.elementOpenStart('input');
        incrementalDom.attr('id', name);
        incrementalDom.attr('type', 'hidden');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('input');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  html: function(),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMCaptcha.render';
}

exports.render.params = ["html","name","visible"];
exports.render.types = {"html":"html","name":"string","visible":"bool"};
templates = exports;
return exports;

});

class DDMCaptcha extends Component {}
Soy.register(DDMCaptcha, templates);
export { DDMCaptcha, templates };
export default templates;
/* jshint ignore:end */
