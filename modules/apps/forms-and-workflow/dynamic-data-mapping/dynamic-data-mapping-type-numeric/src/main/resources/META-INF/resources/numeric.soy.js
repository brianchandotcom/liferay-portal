/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from numeric.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMNumeric.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMNumeric.incrementaldom');

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
function __deltemplate_s2_ee50516d(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_ee50516d = __deltemplate_s2_ee50516d;
if (goog.DEBUG) {
  __deltemplate_s2_ee50516d.soyTemplateName = 'DDMNumeric.__deltemplate_s2_ee50516d';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'numeric', 0, __deltemplate_s2_ee50516d);


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  placeholder: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  value: *,
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  predefinedValue: (*|null|undefined),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  tooltip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var placeholder = soy.asserts.assertType(goog.isString(opt_data.placeholder) || opt_data.placeholder instanceof goog.soy.data.SanitizedContent, 'placeholder', opt_data.placeholder, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {boolean} */
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {*} */
  var value = opt_data.value;
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var label = soy.asserts.assertType(opt_data.label == null || (goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {*|null|undefined} */
  var predefinedValue = opt_data.predefinedValue;
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tooltip = soy.asserts.assertType(opt_data.tooltip == null || (goog.isString(opt_data.tooltip) || opt_data.tooltip instanceof goog.soy.data.SanitizedContent), 'tooltip', opt_data.tooltip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var displayValue__soy21 = value ? value : predefinedValue ? predefinedValue : '';
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-numeric ' + (tip ? 'liferay-ddm-form-field-has-tip' : ''));
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (showLabel) {
      incrementalDom.elementOpenStart('label');
          incrementalDom.attr('for', name);
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
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'input-group input-group-container ' + (tooltip ? 'input-group-default' : ''));
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'input-group-item');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('input');
            incrementalDom.attr('class', 'field form-control');
            if (dir) {
              incrementalDom.attr('dir', dir);
            }
            if (readOnly) {
              incrementalDom.attr('disabled', '');
            }
            incrementalDom.attr('id', name);
            incrementalDom.attr('name', name);
            incrementalDom.attr('placeholder', placeholder);
            incrementalDom.attr('type', 'text');
            incrementalDom.attr('value', displayValue__soy21);
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('input');
      incrementalDom.elementClose('div');
      if (tooltip) {
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'input-group-item input-group-item-shrink');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('button');
              incrementalDom.attr('class', 'btn btn-monospaced btn-unstyled trigger-tooltip');
              incrementalDom.attr('data-original-title', tooltip);
              incrementalDom.attr('data-toggle', 'popover');
              incrementalDom.attr('title', tooltip);
              incrementalDom.attr('type', 'button');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('span');
                incrementalDom.attr('class', 'help-icon rounded-circle sticker sticker-secondary');
            incrementalDom.elementOpenEnd();
              incrementalDom.elementOpenStart('span');
                  incrementalDom.attr('class', 'icon-question');
              incrementalDom.elementOpenEnd();
              incrementalDom.elementClose('span');
            incrementalDom.elementClose('span');
          incrementalDom.elementClose('button');
        incrementalDom.elementClose('div');
      }
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  placeholder: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  value: *,
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  predefinedValue: (*|null|undefined),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  tooltip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMNumeric.render';
}

exports.render.params = ["name","pathThemeImages","placeholder","readOnly","showLabel","value","visible","dir","label","predefinedValue","required","tip","tooltip"];
exports.render.types = {"name":"string","pathThemeImages":"string","placeholder":"string","readOnly":"bool","showLabel":"bool","value":"any","visible":"bool","dir":"string","label":"string","predefinedValue":"any","required":"bool","tip":"string","tooltip":"string"};
templates = exports;
return exports;

});

class DDMNumeric extends Component {}
Soy.register(DDMNumeric, templates);
export { DDMNumeric, templates };
export default templates;
/* jshint ignore:end */
