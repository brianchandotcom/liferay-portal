/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from captcha.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCaptcha.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMCaptcha.incrementaldom');

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
 * @param {Object<string, *>=} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2_ffe4bfd9(opt_data, opt_ignored, opt_ijData) {
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
 *    html: (!soydata.SanitizedHtml|string),
 *    name: string,
 *    visible: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.html instanceof Function) || (opt_data.html instanceof soydata.UnsanitizedText) || goog.isString(opt_data.html), 'html', opt_data.html, 'Function');
  var html = /** @type {Function} */ (opt_data.html);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-captcha',
      'data-fieldname', name);
    html();
    ie_open('input', null, null,
        'id', name,
        'type', 'hidden');
    ie_close('input');
  ie_close('div');
}
exports.render = $render;
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
