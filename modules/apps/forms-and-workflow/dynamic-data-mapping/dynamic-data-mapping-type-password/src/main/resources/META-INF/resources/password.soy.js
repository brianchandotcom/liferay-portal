/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from password.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMPassword.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMPassword.incrementaldom');

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
function __deltemplate_s2_316d888b(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_316d888b = __deltemplate_s2_316d888b;
if (goog.DEBUG) {
  __deltemplate_s2_316d888b.soyTemplateName = 'DDMPassword.__deltemplate_s2_316d888b';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'password', 0, __deltemplate_s2_316d888b);


/**
 * @param {{
 *    dir: (null|string|undefined),
 *    label: (null|string|undefined),
 *    name: string,
 *    placeholder: string,
 *    readOnly: boolean,
 *    required: boolean,
 *    showLabel: boolean,
 *    tip: (null|string|undefined),
 *    tooltip: (null|string|undefined),
 *    value: string,
 *    visible: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isString(opt_data.placeholder) || (opt_data.placeholder instanceof goog.soy.data.SanitizedContent), 'placeholder', opt_data.placeholder, 'string|goog.soy.data.SanitizedContent');
  var placeholder = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.placeholder);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  soy.asserts.assertType(goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean');
  var required = /** @type {boolean} */ (!!opt_data.required);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  soy.asserts.assertType(opt_data.tooltip == null || (opt_data.tooltip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tooltip), 'tooltip', opt_data.tooltip, 'null|string|undefined');
  var tooltip = /** @type {null|string|undefined} */ (opt_data.tooltip);
  soy.asserts.assertType(goog.isString(opt_data.value) || (opt_data.value instanceof goog.soy.data.SanitizedContent), 'value', opt_data.value, 'string|goog.soy.data.SanitizedContent');
  var value = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.value);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-password ' + (tip ? 'liferay-ddm-form-field-has-tip' : ''),
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label', null, null,
          'class', 'control-label' + (readOnly ? ' disabled' : ''),
          'for', name);
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        if (required) {
          ie_void('span', null, null,
              'class', 'icon-asterisk text-warning');
        }
      ie_close('label');
      ie_open('p', null, null,
          'class', 'liferay-ddm-form-field-tip');
        var dyn1 = tip ? tip : '';
        if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
      ie_close('p');
    }
    ie_open('div', null, null,
        'class', 'input-group-container ' + ((tooltip) ? 'input-group-default' : ''));
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
          iattr('type', 'password');
          iattr('value', value);
      ie_open_end();
      ie_close('input');
      if (tooltip) {
        ie_open('span', null, null,
            'class', 'input-group-addon');
          ie_open('span', null, null,
              'class', 'input-group-addon-content');
            ie_void('a', null, null,
                'class', 'help-icon help-icon-default icon-monospaced icon-question',
                'data-original-title', tooltip,
                'data-toggle', 'popover',
                'href', 'javascript:;',
                'title', tooltip);
          ie_close('span');
        ie_close('span');
      }
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMPassword.render';
}

exports.render.params = ["dir","label","name","placeholder","readOnly","required","showLabel","tip","tooltip","value","visible"];
exports.render.types = {"dir":"string","label":"string","name":"string","placeholder":"string","readOnly":"bool","required":"bool","showLabel":"bool","tip":"string","tooltip":"string","value":"string","visible":"bool"};
templates = exports;
return exports;

});

class DDMPassword extends Component {}
Soy.register(DDMPassword, templates);
export { DDMPassword, templates };
export default templates;
/* jshint ignore:end */
