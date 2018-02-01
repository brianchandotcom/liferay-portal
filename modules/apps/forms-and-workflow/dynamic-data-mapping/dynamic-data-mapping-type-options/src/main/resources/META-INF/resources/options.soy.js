/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from options.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMOptions.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMOptions.incrementaldom');

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
function __deltemplate_s2_185c245a(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_185c245a = __deltemplate_s2_185c245a;
if (goog.DEBUG) {
  __deltemplate_s2_185c245a.soyTemplateName = 'DDMOptions.__deltemplate_s2_185c245a';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'options', 0, __deltemplate_s2_185c245a);


/**
 * @param {{
 *    label: (null|string|undefined),
 *    name: string,
 *    required: boolean,
 *    showLabel: boolean,
 *    visible: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean');
  var required = /** @type {boolean} */ (!!opt_data.required);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-options',
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label', null, null,
          'class', 'control-label');
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        if (required) {
          ie_void('span', null, null,
              'class', 'icon-asterisk text-warning');
        }
      ie_close('label');
    }
    ie_open('input', null, null,
        'name', name,
        'type', 'hidden');
    ie_close('input');
    ie_void('div', null, null,
        'class', 'options');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMOptions.render';
}

exports.render.params = ["label","name","required","showLabel","visible"];
exports.render.types = {"label":"string","name":"string","required":"bool","showLabel":"bool","visible":"bool"};
templates = exports;
return exports;

});

class DDMOptions extends Component {}
Soy.register(DDMOptions, templates);
export { DDMOptions, templates };
export default templates;
/* jshint ignore:end */
