/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMRadio.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMRadio.incrementaldom');

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
function __deltemplate_s2_a0071001(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_a0071001 = __deltemplate_s2_a0071001;
if (goog.DEBUG) {
  __deltemplate_s2_a0071001.soyTemplateName = 'DDMRadio.__deltemplate_s2_a0071001';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'radio', 0, __deltemplate_s2_a0071001);


/**
 * @param {{
 *  inline: boolean,
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
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
  /** @type {boolean} */
  var inline = soy.asserts.assertType(goog.isBoolean(opt_data.inline) || opt_data.inline === 1 || opt_data.inline === 0, 'inline', opt_data.inline, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var label = soy.asserts.assertType(goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent, 'label', opt_data.label, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>} */
  var options = soy.asserts.assertType(goog.isArray(opt_data.options), 'options', opt_data.options, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>');
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
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var predefinedValue = soy.asserts.assertType(opt_data.predefinedValue == null || (goog.isString(opt_data.predefinedValue) || opt_data.predefinedValue instanceof goog.soy.data.SanitizedContent), 'predefinedValue', opt_data.predefinedValue, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var displayValue__soy21 = value ? value : predefinedValue;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group ' + (visible ? '' : 'hide'));
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (showLabel) {
      incrementalDom.elementOpen('div');
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
      incrementalDom.elementClose('div');
    }
    var option78List = options;
    var option78ListLen = option78List.length;
    for (var option78Index = 0; option78Index < option78ListLen; option78Index++) {
        var option78Data = option78List[option78Index];
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'custom-control ' + (inline ? 'custom-control-inline' : '') + ' custom-radio');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('label');
              incrementalDom.attr('for', name + '_' + option78Data.value);
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('input');
                if (option78Data.value == displayValue__soy21) {
                  incrementalDom.attr('checked', '');
                }
                if (dir) {
                  incrementalDom.attr('dir', dir);
                }
                if (readOnly) {
                  incrementalDom.attr('disabled', '');
                }
                incrementalDom.attr('class', 'custom-control-input');
                incrementalDom.attr('id', name + '_' + option78Data.value);
                incrementalDom.attr('name', name);
                incrementalDom.attr('type', 'radio');
                incrementalDom.attr('value', option78Data.value);
            incrementalDom.elementOpenEnd();
            incrementalDom.elementClose('input');
            incrementalDom.elementOpenStart('span');
                incrementalDom.attr('class', 'custom-control-label');
            incrementalDom.elementOpenEnd();
              incrementalDom.elementOpenStart('span');
                  incrementalDom.attr('class', 'custom-control-label-text');
              incrementalDom.elementOpenEnd();
                soyIdom.print(option78Data.label);
              incrementalDom.elementClose('span');
            incrementalDom.elementClose('span');
          incrementalDom.elementClose('label');
        incrementalDom.elementClose('div');
      }
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  inline: boolean,
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  predefinedValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMRadio.render';
}

exports.render.params = ["inline","label","name","options","pathThemeImages","readOnly","showLabel","value","visible","dir","predefinedValue","required","tip"];
exports.render.types = {"inline":"bool","label":"string","name":"string","options":"list<[label: string, value: string]>","pathThemeImages":"string","readOnly":"bool","showLabel":"bool","value":"string","visible":"bool","dir":"string","predefinedValue":"string","required":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMRadio extends Component {}
Soy.register(DDMRadio, templates);
export { DDMRadio, templates };
export default templates;
/* jshint ignore:end */
