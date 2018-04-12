/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from key-value.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMKeyValue.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMKeyValue.incrementaldom');

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
function __deltemplate_s2_7cdb575b(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_7cdb575b = __deltemplate_s2_7cdb575b;
if (goog.DEBUG) {
  __deltemplate_s2_7cdb575b.soyTemplateName = 'DDMKeyValue.__deltemplate_s2_7cdb575b';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'key_value', 0, __deltemplate_s2_7cdb575b);


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  placeholder: (!goog.soy.data.SanitizedContent|string),
 *  showLabel: boolean,
 *  strings: {keyLabel: (!goog.soy.data.SanitizedContent|string)},
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  key: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  keyInputSize: (null|number|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
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
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {{keyLabel: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{keyLabel: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var value = soy.asserts.assertType(goog.isString(opt_data.value) || opt_data.value instanceof goog.soy.data.SanitizedContent, 'value', opt_data.value, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var key = soy.asserts.assertType(opt_data.key == null || (goog.isString(opt_data.key) || opt_data.key instanceof goog.soy.data.SanitizedContent), 'key', opt_data.key, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {null|number|undefined} */
  var keyInputSize = soy.asserts.assertType(opt_data.keyInputSize == null || goog.isNumber(opt_data.keyInputSize), 'keyInputSize', opt_data.keyInputSize, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var label = soy.asserts.assertType(opt_data.label == null || (goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tooltip = soy.asserts.assertType(opt_data.tooltip == null || (goog.isString(opt_data.tooltip) || opt_data.tooltip instanceof goog.soy.data.SanitizedContent), 'tooltip', opt_data.tooltip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-key-value liferay-ddm-form-field-text');
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (showLabel) {
      incrementalDom.elementOpenStart('label');
          incrementalDom.attr('class', 'control-label');
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
    }
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'input-group input-group-container ' + (tooltip ? 'input-group-transparent' : ''));
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'input-group-item');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('input');
            incrementalDom.attr('class', 'field form-control');
            if (dir) {
              incrementalDom.attr('dir', dir);
            }
            incrementalDom.attr('id', name);
            incrementalDom.attr('name', name);
            incrementalDom.attr('placeholder', placeholder);
            incrementalDom.attr('type', 'text');
            incrementalDom.attr('value', value);
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
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'active key-value-editor');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('label');
          incrementalDom.attr('class', 'control-label key-value-label');
      incrementalDom.elementOpenEnd();
        soyIdom.print(strings.keyLabel);
        incrementalDom.text(':');
      incrementalDom.elementClose('label');
      incrementalDom.elementOpenStart('input');
          incrementalDom.attr('class', 'key-value-input');
          incrementalDom.attr('size', keyInputSize);
          incrementalDom.attr('tabindex', '-1');
          incrementalDom.attr('type', 'text');
          incrementalDom.attr('value', key);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('input');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  placeholder: (!goog.soy.data.SanitizedContent|string),
 *  showLabel: boolean,
 *  strings: {keyLabel: (!goog.soy.data.SanitizedContent|string)},
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  key: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  keyInputSize: (null|number|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  tooltip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMKeyValue.render';
}

exports.render.params = ["name","pathThemeImages","placeholder","showLabel","strings","value","visible","dir","key","keyInputSize","label","required","tooltip"];
exports.render.types = {"name":"string","pathThemeImages":"string","placeholder":"string","showLabel":"bool","strings":"[keyLabel: string]","value":"string","visible":"bool","dir":"string","key":"string","keyInputSize":"int","label":"string","required":"bool","tooltip":"string"};
templates = exports;
return exports;

});

class DDMKeyValue extends Component {}
Soy.register(DDMKeyValue, templates);
export { DDMKeyValue, templates };
export default templates;
/* jshint ignore:end */
