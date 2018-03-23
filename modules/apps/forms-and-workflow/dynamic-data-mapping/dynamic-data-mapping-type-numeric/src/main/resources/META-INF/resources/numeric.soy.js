/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from numeric.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMNumeric.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMNumeric.incrementaldom');

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
function __deltemplate_s2_ee50516d(opt_data, opt_ignored, opt_ijData) {
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
 *    name: string,
 *    pathThemeImages: string,
 *    placeholder: string,
 *    readOnly: boolean,
 *    showLabel: boolean,
 *    value: *,
 *    visible: boolean,
 *    dir: (null|string|undefined),
 *    label: (null|string|undefined),
 *    predefinedValue: (*|null|undefined),
 *    required: (boolean|null|undefined),
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
  soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || (opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent), 'pathThemeImages', opt_data.pathThemeImages, 'string|goog.soy.data.SanitizedContent');
  var pathThemeImages = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.pathThemeImages);
  soy.asserts.assertType(goog.isString(opt_data.placeholder) || (opt_data.placeholder instanceof goog.soy.data.SanitizedContent), 'placeholder', opt_data.placeholder, 'string|goog.soy.data.SanitizedContent');
  var placeholder = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.placeholder);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(opt_data.predefinedValue == null || opt_data.predefinedValue != null, 'predefinedValue', opt_data.predefinedValue, '*|null|undefined');
  var predefinedValue = /** @type {*|null|undefined} */ (opt_data.predefinedValue);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  soy.asserts.assertType(opt_data.tooltip == null || (opt_data.tooltip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tooltip), 'tooltip', opt_data.tooltip, 'null|string|undefined');
  var tooltip = /** @type {null|string|undefined} */ (opt_data.tooltip);
  var displayValue__soy5 = opt_data.value ? opt_data.value : predefinedValue ? predefinedValue : '';
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-numeric ' + (tip ? 'liferay-ddm-form-field-has-tip' : ''),
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label', null, null,
          'for', name);
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        itext(' ');
        if (required) {
          ie_open('svg', null, null,
              'aria-hidden', 'true',
              'class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
            ie_void('use', null, null,
                'xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
          ie_close('svg');
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
        'class', 'input-group input-group-container ' + ((tooltip) ? 'input-group-default' : ''));
      ie_open('div', null, null,
          'class', 'input-group-item');
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
      ie_close('div');
      if (tooltip) {
        ie_open('div', null, null,
            'class', 'input-group-item input-group-item-shrink');
          ie_open('button', null, null,
              'class', 'btn btn-monospaced btn-unstyled trigger-tooltip',
              'data-original-title', tooltip,
              'data-toggle', 'popover',
              'title', tooltip,
              'type', 'button');
            ie_open('span', null, null,
                'class', 'help-icon rounded-circle sticker sticker-secondary');
              ie_void('span', null, null,
                  'class', 'icon-question');
            ie_close('span');
          ie_close('button');
        ie_close('div');
      }
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
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
