/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from editor.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMEditor.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMEditor.incrementaldom');

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
function __deltemplate_s2_262ebc73(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_262ebc73 = __deltemplate_s2_262ebc73;
if (goog.DEBUG) {
  __deltemplate_s2_262ebc73.soyTemplateName = 'DDMEditor.__deltemplate_s2_262ebc73';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'editor', 0, __deltemplate_s2_262ebc73);


/**
 * @param {{
 *    label: string,
 *    name: string,
 *    placeholder: string,
 *    required: boolean,
 *    showLabel: boolean,
 *    tip: (null|string|undefined),
 *    value: string,
 *    visible: boolean
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
  soy.asserts.assertType(goog.isString(opt_data.placeholder) || (opt_data.placeholder instanceof goog.soy.data.SanitizedContent), 'placeholder', opt_data.placeholder, 'string|goog.soy.data.SanitizedContent');
  var placeholder = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.placeholder);
  soy.asserts.assertType(goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean');
  var required = /** @type {boolean} */ (!!opt_data.required);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  soy.asserts.assertType(goog.isString(opt_data.value) || (opt_data.value instanceof goog.soy.data.SanitizedContent), 'value', opt_data.value, 'string|goog.soy.data.SanitizedContent');
  var value = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.value);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-editor',
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label', null, null,
          'class', 'control-label',
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
        'class', 'input-group-container');
      ie_open('div', null, null,
          'class', 'alloy-editor-container',
          'id', name + 'Container');
        ie_open('div', null, null,
            'class', 'alloy-editor alloy-editor-placeholder form-control',
            'contenteditable', 'false',
            'data-placeholder', placeholder,
            'id', name + 'Editor',
            'name', name + 'Editor');
          var dyn2 = value;
          if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
        ie_close('div');
      ie_close('div');
      ie_open('input', null, null,
          'id', name,
          'name', name,
          'type', 'hidden',
          'value', value);
      ie_close('input');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMEditor.render';
}

exports.render.params = ["label","name","placeholder","required","showLabel","tip","value","visible"];
exports.render.types = {"label":"string","name":"string","placeholder":"string","required":"bool","showLabel":"bool","tip":"string","value":"string","visible":"bool"};
templates = exports;
return exports;

});

class DDMEditor extends Component {}
Soy.register(DDMEditor, templates);
export { DDMEditor, templates };
export default templates;
/* jshint ignore:end */
