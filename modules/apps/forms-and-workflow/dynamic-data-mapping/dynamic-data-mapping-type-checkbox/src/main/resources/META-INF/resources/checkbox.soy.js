/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from checkbox.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCheckbox.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMCheckbox.incrementaldom');

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
function __deltemplate_s2_560e40fa(opt_data, opt_ignored, opt_ijData) {
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
 *    label: string,
 *    name: string,
 *    pathThemeImages: string,
 *    readOnly: boolean,
 *    required: boolean,
 *    showAsSwitcher: boolean,
 *    showLabel: boolean,
 *    value: (?),
 *    visible: boolean,
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
  soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || (opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent), 'pathThemeImages', opt_data.pathThemeImages, 'string|goog.soy.data.SanitizedContent');
  var pathThemeImages = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.pathThemeImages);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  soy.asserts.assertType(goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean');
  var required = /** @type {boolean} */ (!!opt_data.required);
  soy.asserts.assertType(goog.isBoolean(opt_data.showAsSwitcher) || opt_data.showAsSwitcher === 1 || opt_data.showAsSwitcher === 0, 'showAsSwitcher', opt_data.showAsSwitcher, 'boolean');
  var showAsSwitcher = /** @type {boolean} */ (!!opt_data.showAsSwitcher);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-checkbox',
      'data-fieldname', name);
    if (showAsSwitcher) {
      ie_open('label', null, null,
          'aria-checked', opt_data.value ? 'true' : 'false',
          'for', name,
          'role', 'checkbox');
        ie_open_start('input');
            iattr('class', 'toggle-switch');
            if (opt_data.value) {
              iattr('checked', '');
            }
            if (readOnly) {
              iattr('disabled', '');
            }
            iattr('id', name);
            iattr('name', name);
            iattr('type', 'checkbox');
            iattr('value', 'true');
        ie_open_end();
        ie_close('input');
        ie_open('span', null, null,
            'aria-hidden', 'true',
            'class', 'toggle-switch-bar');
          ie_void('span', null, null,
              'class', 'toggle-switch-handle');
        ie_close('span');
        ie_open('span', null, null,
            'class', 'toggle-switch-text toggle-switch-text-right');
          if (showLabel) {
            var dyn0 = label;
            if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
            itext(' ');
          }
          if (required) {
            ie_open('svg', null, null,
                'aria-hidden', 'true',
                'class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
              ie_void('use', null, null,
                  'xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
            ie_close('svg');
          }
        ie_close('span');
      ie_close('label');
      if (tip) {
        ie_open('span', null, null,
            'class', 'form-text');
          var dyn1 = tip;
          if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
        ie_close('span');
      }
    } else {
      ie_open('div', null, null,
          'class', 'custom-control custom-checkbox');
        if (showLabel) {
          ie_open('label', null, null,
              'for', name);
          }
          ie_open_start('input');
              if (opt_data.value) {
                iattr('checked', '');
              }
              if (readOnly) {
                iattr('disabled', '');
              }
              iattr('class', 'custom-control-input');
              iattr('id', name);
              iattr('name', name);
              iattr('type', 'checkbox');
              iattr('value', 'true');
          ie_open_end();
          ie_close('input');
          if (showLabel) {
            ie_open('span', null, null,
                'class', 'custom-control-label');
              ie_open('span', null, null,
                  'class', 'custom-control-label-text');
                var dyn2 = label;
                if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
                itext(' ');
              ie_close('span');
            ie_close('span');
          }
          if (showLabel) {
            if (required) {
              ie_open('svg', null, null,
                  'aria-hidden', 'true',
                  'class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
                ie_void('use', null, null,
                    'xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
              ie_close('svg');
            }
          ie_close('label');
        }
      ie_close('div');
      if (showLabel) {
        if (tip) {
          ie_open('span', null, null,
              'class', 'form-text');
            var dyn3 = tip;
            if (typeof dyn3 == 'function') dyn3(); else if (dyn3 != null) itext(dyn3);
          ie_close('span');
        }
      }
    }
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMCheckbox.render';
}

exports.render.params = ["label","name","pathThemeImages","readOnly","required","showAsSwitcher","showLabel","value","visible","tip"];
exports.render.types = {"label":"string","name":"string","pathThemeImages":"string","readOnly":"bool","required":"bool","showAsSwitcher":"bool","showLabel":"bool","value":"?","visible":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMCheckbox extends Component {}
Soy.register(DDMCheckbox, templates);
export { DDMCheckbox, templates };
export default templates;
/* jshint ignore:end */
