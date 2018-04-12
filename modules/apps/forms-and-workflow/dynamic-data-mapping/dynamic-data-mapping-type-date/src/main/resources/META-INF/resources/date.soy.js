/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from date.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMDate.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMDate.incrementaldom');

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
function __deltemplate_s2_cfb11076(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
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
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
 *  formattedValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  predefinedValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var label = soy.asserts.assertType(goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent, 'label', opt_data.label, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {boolean} */
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var value = soy.asserts.assertType(goog.isString(opt_data.value) || opt_data.value instanceof goog.soy.data.SanitizedContent, 'value', opt_data.value, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var formattedValue = soy.asserts.assertType(opt_data.formattedValue == null || (goog.isString(opt_data.formattedValue) || opt_data.formattedValue instanceof goog.soy.data.SanitizedContent), 'formattedValue', opt_data.formattedValue, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var predefinedValue = soy.asserts.assertType(opt_data.predefinedValue == null || (goog.isString(opt_data.predefinedValue) || opt_data.predefinedValue instanceof goog.soy.data.SanitizedContent), 'predefinedValue', opt_data.predefinedValue, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var formGroupClass__soy19 = '';
  formGroupClass__soy19 += 'form-group';
  formGroupClass__soy19 += !visible ? ' hide' : '';
  formGroupClass__soy19 += ' liferay-ddm-form-field-date';
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', formGroupClass__soy19);
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (showLabel) {
      var labelAttributes__soy33 = function() {
        incrementalDom.attr('for', name);
      };
      incrementalDom.elementOpenStart('label');
          labelAttributes__soy33();
      incrementalDom.elementOpenEnd();
        soyIdom.print(label);
        incrementalDom.text(' ');
        if (required) {
          incrementalDom.elementOpenStart('svg');
              incrementalDom.attr('aria-hidden', 'true');
              incrementalDom.attr('class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('use');
                incrementalDom.attr('xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
            incrementalDom.elementOpenEnd();
            incrementalDom.elementClose('use');
          incrementalDom.elementClose('svg');
        }
      incrementalDom.elementClose('label');
      if (tip) {
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', 'form-text');
        incrementalDom.elementOpenEnd();
          soyIdom.print(tip);
        incrementalDom.elementClose('span');
      }
    }
    var displayValue__soy55 = formattedValue ? formattedValue : predefinedValue;
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'input-group input-group-container');
    incrementalDom.elementOpenEnd();
      var inputAttributes__soy57 = function() {
        incrementalDom.attr('class', 'form-control');
        if (label) {
          incrementalDom.attr('aria-label', label);
        }
        if (displayValue__soy55) {
          incrementalDom.attr('value', displayValue__soy55);
        }
        if (readOnly) {
          incrementalDom.attr('disabled', '');
        }
        incrementalDom.attr('type', 'text');
      };
      incrementalDom.elementOpenStart('input');
          inputAttributes__soy57();
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('input');
      incrementalDom.elementOpenStart('input');
          incrementalDom.attr('name', name);
          incrementalDom.attr('type', 'hidden');
          incrementalDom.attr('value', value);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('input');
      incrementalDom.elementOpenStart('span');
          incrementalDom.attr('class', 'input-group-addon');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', 'icon-calendar');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('span');
      incrementalDom.elementClose('span');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
 *  formattedValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  predefinedValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMDate.render';
}

exports.render.params = ["label","name","pathThemeImages","readOnly","showLabel","value","visible","formattedValue","predefinedValue","required","tip"];
exports.render.types = {"label":"string","name":"string","pathThemeImages":"string","readOnly":"bool","showLabel":"bool","value":"string","visible":"bool","formattedValue":"string","predefinedValue":"string","required":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMDate extends Component {}
Soy.register(DDMDate, templates);
export { DDMDate, templates };
export default templates;
/* jshint ignore:end */
