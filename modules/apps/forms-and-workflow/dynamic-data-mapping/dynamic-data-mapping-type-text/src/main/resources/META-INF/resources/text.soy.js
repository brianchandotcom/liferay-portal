/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from text.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMText.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMText.incrementaldom');

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
function __deltemplate_s2_10305041(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_10305041 = __deltemplate_s2_10305041;
if (goog.DEBUG) {
  __deltemplate_s2_10305041.soyTemplateName = 'DDMText.__deltemplate_s2_10305041';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'text', 0, __deltemplate_s2_10305041);


/**
 * @param {{
 *    name: string,
 *    placeholder: string,
 *    value: (?),
 *    visible: boolean,
 *    dir: (null|string|undefined),
 *    displayStyle: (null|string|undefined),
 *    label: (null|string|undefined),
 *    predefinedValue: (null|string|undefined),
 *    readOnly: (boolean|null|undefined),
 *    required: (boolean|null|undefined),
 *    showLabel: (boolean|null|undefined),
 *    tip: (null|string|undefined),
 *    tooltip: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isString(opt_data.placeholder) || (opt_data.placeholder instanceof goog.soy.data.SanitizedContent), 'placeholder', opt_data.placeholder, 'string|goog.soy.data.SanitizedContent');
  var placeholder = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.placeholder);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.displayStyle == null || (opt_data.displayStyle instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.displayStyle), 'displayStyle', opt_data.displayStyle, 'null|string|undefined');
  var displayStyle = /** @type {null|string|undefined} */ (opt_data.displayStyle);
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(opt_data.predefinedValue == null || (opt_data.predefinedValue instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.predefinedValue), 'predefinedValue', opt_data.predefinedValue, 'null|string|undefined');
  var predefinedValue = /** @type {null|string|undefined} */ (opt_data.predefinedValue);
  soy.asserts.assertType(opt_data.readOnly == null || goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean|null|undefined');
  var readOnly = /** @type {boolean|null|undefined} */ (opt_data.readOnly);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(opt_data.showLabel == null || goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean|null|undefined');
  var showLabel = /** @type {boolean|null|undefined} */ (opt_data.showLabel);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  soy.asserts.assertType(opt_data.tooltip == null || (opt_data.tooltip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tooltip), 'tooltip', opt_data.tooltip, 'null|string|undefined');
  var tooltip = /** @type {null|string|undefined} */ (opt_data.tooltip);
  var displayValue__soy5 = opt_data.value ? opt_data.value : predefinedValue ? predefinedValue : '';
  ie_open('div', null, null,
      'class', 'form-group ' + (visible ? '' : 'hide') + ' liferay-ddm-form-field-text',
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label', null, null,
          'for', name);
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        if (required) {
          ie_void('span', null, null,
              'class', 'icon-asterisk text-warning');
        }
      ie_close('label');
      if (tip) {
        ie_open('span', null, null,
            'class', 'form-text');
          var dyn1 = tip;
          if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
        ie_close('span');
      }
    }
    ie_open('div', null, null,
        'class', 'input-group');
      if (displayStyle == 'multiline') {
        ie_open_start('textarea');
            iattr('class', 'field form-control');
            if (dir) {
              iattr('dir', dir);
            }
            iattr('id', name);
            if (readOnly) {
              iattr('disabled', '');
            }
            iattr('name', name);
            iattr('placeholder', placeholder);
        ie_open_end();
          var dyn2 = displayValue__soy5;
          if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
        ie_close('textarea');
      } else {
        ie_open_start('input');
            iattr('class', 'field form-control');
            if (dir) {
              iattr('dir', dir);
            }
            if (readOnly) {
              iattr('disabled', '');
            }
            iattr('id', name);
            iattr('name', name);
            iattr('placeholder', placeholder);
            iattr('type', 'text');
            iattr('value', displayValue__soy5);
        ie_open_end();
        ie_close('input');
      }
      if (tooltip) {
        ie_open('a', null, null,
            'class', 'input-group-addon help-icon',
            'data-original-title', tooltip,
            'data-toggle', 'popover',
            'href', 'javascript:;',
            'title', tooltip);
          ie_void('span', null, null,
              'class', 'icon-question');
        ie_close('a');
      }
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMText.render';
}

exports.render.params = ["name","placeholder","value","visible","dir","displayStyle","label","predefinedValue","readOnly","required","showLabel","tip","tooltip"];
exports.render.types = {"name":"string","placeholder":"string","value":"?","visible":"bool","dir":"string","displayStyle":"string","label":"string","predefinedValue":"string","readOnly":"bool","required":"bool","showLabel":"bool","tip":"string","tooltip":"string"};
templates = exports;
return exports;

});

class DDMText extends Component {}
Soy.register(DDMText, templates);
export { DDMText, templates };
export default templates;
/* jshint ignore:end */
