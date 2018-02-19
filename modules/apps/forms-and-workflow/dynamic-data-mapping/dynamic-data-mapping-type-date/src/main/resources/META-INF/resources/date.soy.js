/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from date.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMDate.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMDate.incrementaldom');

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
function __deltemplate_s2_cfb11076(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_cfb11076 = __deltemplate_s2_cfb11076;
if (goog.DEBUG) {
  __deltemplate_s2_cfb11076.soyTemplateName = 'DDMDate.__deltemplate_s2_cfb11076';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'date', 0, __deltemplate_s2_cfb11076);


/**
 * @param {{
 *    label: string,
 *    name: string,
 *    readOnly: boolean,
 *    showLabel: boolean,
 *    value: string,
 *    visible: boolean,
 *    formattedValue: (null|string|undefined),
 *    predefinedValue: (null|string|undefined),
 *    required: (boolean|null|undefined),
 *    tip: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.label) || (opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, 'string|goog.soy.data.SanitizedContent');
  var label = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.label);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(goog.isString(opt_data.value) || (opt_data.value instanceof goog.soy.data.SanitizedContent), 'value', opt_data.value, 'string|goog.soy.data.SanitizedContent');
  var value = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.value);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.formattedValue == null || (opt_data.formattedValue instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.formattedValue), 'formattedValue', opt_data.formattedValue, 'null|string|undefined');
  var formattedValue = /** @type {null|string|undefined} */ (opt_data.formattedValue);
  soy.asserts.assertType(opt_data.predefinedValue == null || (opt_data.predefinedValue instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.predefinedValue), 'predefinedValue', opt_data.predefinedValue, 'null|string|undefined');
  var predefinedValue = /** @type {null|string|undefined} */ (opt_data.predefinedValue);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  var formGroupClass__soy5 = '';
  formGroupClass__soy5 += 'form-group';
  formGroupClass__soy5 += (! visible) ? ' hide' : '';
  formGroupClass__soy5 += ' liferay-ddm-form-field-date';
  ie_open('div', null, null,
      'class', formGroupClass__soy5,
      'data-fieldname', name);
    if (showLabel) {
      var labelAttributes__soy18 = function() {
        iattr('for', name);
      };
      ie_open_start('label');
          labelAttributes__soy18();
      ie_open_end();
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
    var displayValue__soy35 = formattedValue ? formattedValue : predefinedValue;
    ie_open('div', null, null,
        'class', 'input-group input-group-container');
      var inputAttributes__soy37 = function() {
        iattr('class', 'form-control');
        if (label) {
          iattr('aria-label', label);
        }
        if (displayValue__soy35) {
          iattr('value', displayValue__soy35);
        }
        if (readOnly) {
          iattr('disabled', '');
        }
        iattr('type', 'text');
      };
      ie_open_start('input');
          inputAttributes__soy37();
      ie_open_end();
      ie_close('input');
      ie_open('input', null, null,
          'name', name,
          'type', 'hidden',
          'value', value);
      ie_close('input');
      ie_open('span', null, null,
          'class', 'input-group-addon');
        ie_void('span', null, null,
            'class', 'icon-calendar');
      ie_close('span');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMDate.render';
}

exports.render.params = ["label","name","readOnly","showLabel","value","visible","formattedValue","predefinedValue","required","tip"];
exports.render.types = {"label":"string","name":"string","readOnly":"bool","showLabel":"bool","value":"string","visible":"bool","formattedValue":"string","predefinedValue":"string","required":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMDate extends Component {}
Soy.register(DDMDate, templates);
export { DDMDate, templates };
export default templates;
/* jshint ignore:end */
