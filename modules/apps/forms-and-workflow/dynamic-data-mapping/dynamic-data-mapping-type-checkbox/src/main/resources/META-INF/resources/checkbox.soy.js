/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from checkbox.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCheckbox.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMCheckbox.incrementaldom');

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
function __deltemplate_s2_560e40fa(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_560e40fa = __deltemplate_s2_560e40fa;
if (goog.DEBUG) {
  __deltemplate_s2_560e40fa.soyTemplateName = 'DDMCheckbox.__deltemplate_s2_560e40fa';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'checkbox', 0, __deltemplate_s2_560e40fa);


/**
 * @param {{
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  predefinedValue: boolean,
 *  readOnly: boolean,
 *  showAsSwitcher: boolean,
 *  showLabel: boolean,
 *  value: boolean,
 *  visible: boolean,
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
  var predefinedValue = soy.asserts.assertType(goog.isBoolean(opt_data.predefinedValue) || opt_data.predefinedValue === 1 || opt_data.predefinedValue === 0, 'predefinedValue', opt_data.predefinedValue, 'boolean');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {boolean} */
  var showAsSwitcher = soy.asserts.assertType(goog.isBoolean(opt_data.showAsSwitcher) || opt_data.showAsSwitcher === 1 || opt_data.showAsSwitcher === 0, 'showAsSwitcher', opt_data.showAsSwitcher, 'boolean');
  /** @type {boolean} */
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {boolean} */
  var value = soy.asserts.assertType(goog.isBoolean(opt_data.value) || opt_data.value === 1 || opt_data.value === 0, 'value', opt_data.value, 'boolean');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var displayValue__soy19 = value ? value : predefinedValue;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-checkbox');
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (showAsSwitcher) {
      incrementalDom.elementOpenStart('label');
          incrementalDom.attr('aria-checked', displayValue__soy19 ? 'true' : 'false');
          incrementalDom.attr('for', name);
          incrementalDom.attr('role', 'checkbox');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('input');
            incrementalDom.attr('class', 'toggle-switch');
            if (displayValue__soy19) {
              incrementalDom.attr('checked', '');
            }
            if (readOnly) {
              incrementalDom.attr('disabled', '');
            }
            incrementalDom.attr('id', name);
            incrementalDom.attr('name', name);
            incrementalDom.attr('type', 'checkbox');
            incrementalDom.attr('value', 'true');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('input');
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('aria-hidden', 'true');
            incrementalDom.attr('class', 'toggle-switch-bar');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'toggle-switch-handle');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('span');
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', 'toggle-switch-text toggle-switch-text-right');
        incrementalDom.elementOpenEnd();
          if (showLabel) {
            soyIdom.print(label);
            incrementalDom.text(' ');
          }
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
        incrementalDom.elementClose('span');
      incrementalDom.elementClose('label');
      if (tip) {
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', 'form-text');
        incrementalDom.elementOpenEnd();
          soyIdom.print(tip);
        incrementalDom.elementClose('span');
      }
    } else {
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'custom-control custom-checkbox');
      incrementalDom.elementOpenEnd();
          if (showLabel) {
            incrementalDom.elementOpenStart('label');
              incrementalDom.attr('for', name);
          incrementalDom.elementOpenEnd();
          }
          incrementalDom.elementOpenStart('input');
              if (displayValue__soy19) {
                incrementalDom.attr('checked', '');
              }
              if (readOnly) {
                incrementalDom.attr('disabled', '');
              }
              incrementalDom.attr('class', 'custom-control-input');
              incrementalDom.attr('id', name);
              incrementalDom.attr('name', name);
              incrementalDom.attr('type', 'checkbox');
              incrementalDom.attr('value', 'true');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('input');
          if (showLabel) {
            incrementalDom.elementOpenStart('span');
                incrementalDom.attr('class', 'custom-control-label');
            incrementalDom.elementOpenEnd();
              incrementalDom.elementOpenStart('span');
                  incrementalDom.attr('class', 'custom-control-label-text');
              incrementalDom.elementOpenEnd();
                soyIdom.print(label);
                incrementalDom.text(' ');
              incrementalDom.elementClose('span');
            incrementalDom.elementClose('span');
          }
        if (showLabel) {
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
        }
      incrementalDom.elementClose('div');
      if (showLabel) {
        if (tip) {
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'form-text');
          incrementalDom.elementOpenEnd();
            soyIdom.print(tip);
          incrementalDom.elementClose('span');
        }
      }
    }
    if (!displayValue__soy19) {
      $hidden_input({displayValue: displayValue__soy19, name: name}, null, opt_ijData);
    }
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  predefinedValue: boolean,
 *  readOnly: boolean,
 *  showAsSwitcher: boolean,
 *  showLabel: boolean,
 *  value: boolean,
 *  visible: boolean,
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMCheckbox.render';
}


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  displayValue: boolean
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $hidden_input(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var displayValue = soy.asserts.assertType(goog.isBoolean(opt_data.displayValue) || opt_data.displayValue === 1 || opt_data.displayValue === 0, 'displayValue', opt_data.displayValue, 'boolean');
  incrementalDom.elementOpenStart('input');
      incrementalDom.attr('id', name);
      incrementalDom.attr('name', name);
      incrementalDom.attr('type', 'hidden');
      incrementalDom.attr('value', displayValue ? 'true' : 'false');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('input');
}
exports.hidden_input = $hidden_input;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  displayValue: boolean
 * }}
 */
$hidden_input.Params;
if (goog.DEBUG) {
  $hidden_input.soyTemplateName = 'DDMCheckbox.hidden_input';
}

exports.render.params = ["label","name","pathThemeImages","predefinedValue","readOnly","showAsSwitcher","showLabel","value","visible","required","tip"];
exports.render.types = {"label":"string","name":"string","pathThemeImages":"string","predefinedValue":"bool","readOnly":"bool","showAsSwitcher":"bool","showLabel":"bool","value":"bool","visible":"bool","required":"bool","tip":"string"};
exports.hidden_input.params = ["name","displayValue"];
exports.hidden_input.types = {"name":"string","displayValue":"bool"};
templates = exports;
return exports;

});

class DDMCheckbox extends Component {}
Soy.register(DDMCheckbox, templates);
export { DDMCheckbox, templates };
export default templates;
/* jshint ignore:end */
