/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMRadio.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMRadio.incrementaldom');

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
function __deltemplate_s2_a0071001(opt_data, opt_ignored, opt_ijData) {
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
 *    inline: boolean,
 *    label: string,
 *    name: string,
 *    options: !Array<{label: string, value: string}>,
 *    readOnly: boolean,
 *    showLabel: boolean,
 *    value: string,
 *    visible: boolean,
 *    dir: (null|string|undefined),
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
  soy.asserts.assertType(goog.isBoolean(opt_data.inline) || opt_data.inline === 1 || opt_data.inline === 0, 'inline', opt_data.inline, 'boolean');
  var inline = /** @type {boolean} */ (!!opt_data.inline);
  soy.asserts.assertType(goog.isString(opt_data.label) || (opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, 'string|goog.soy.data.SanitizedContent');
  var label = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.label);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  var options = goog.asserts.assertArray(opt_data.options, "expected parameter 'options' of type list<[label: string, value: string]>.");
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(goog.isString(opt_data.value) || (opt_data.value instanceof goog.soy.data.SanitizedContent), 'value', opt_data.value, 'string|goog.soy.data.SanitizedContent');
  var value = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.value);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.predefinedValue == null || (opt_data.predefinedValue instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.predefinedValue), 'predefinedValue', opt_data.predefinedValue, 'null|string|undefined');
  var predefinedValue = /** @type {null|string|undefined} */ (opt_data.predefinedValue);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  var displayValue__soy5 = value ? value : predefinedValue;
  ie_open('div', null, null,
      'class', 'form-group ' + (visible ? '' : 'hide'),
      'data-fieldname', name);
    if (showLabel) {
      ie_open('div');
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
      ie_close('div');
    }
    var optionList58 = options;
    var optionListLen58 = optionList58.length;
    for (var optionIndex58 = 0; optionIndex58 < optionListLen58; optionIndex58++) {
      var optionData58 = optionList58[optionIndex58];
      ie_open('div', null, null,
          'class', 'form-check ' + (inline ? 'form-check-inline' : ''));
        ie_open('label', null, null,
            'class', 'form-check-label form-check-label-option-' + optionData58.value,
            'for', name + '_' + optionData58.value);
          ie_open_start('input');
              if (optionData58.value == displayValue__soy5) {
                iattr('checked', '');
              }
              if (dir) {
                iattr('dir', dir);
              }
              if (readOnly) {
                iattr('disabled', '');
              }
              iattr('class', 'form-check-input');
              iattr('id', name + '_' + optionData58.value);
              iattr('name', name);
              iattr('type', 'radio');
              iattr('value', optionData58.value);
          ie_open_end();
          ie_close('input');
          ie_open('span', null, null,
              'class', 'form-check-description');
            var dyn2 = optionData58.label;
            if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
          ie_close('span');
        ie_close('label');
      ie_close('div');
    }
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMRadio.render';
}

exports.render.params = ["inline","label","name","readOnly","showLabel","value","visible","dir","predefinedValue","required","tip"];
exports.render.types = {"inline":"bool","label":"string","name":"string","readOnly":"bool","showLabel":"bool","value":"string","visible":"bool","dir":"string","predefinedValue":"string","required":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMRadio extends Component {}
Soy.register(DDMRadio, templates);
export { DDMRadio, templates };
export default templates;
/* jshint ignore:end */
