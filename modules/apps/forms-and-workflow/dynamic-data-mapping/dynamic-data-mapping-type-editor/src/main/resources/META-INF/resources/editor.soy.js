/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from editor.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMEditor.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMEditor.incrementaldom');

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
function __deltemplate_s2_262ebc73(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
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
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  placeholder: (!goog.soy.data.SanitizedContent|string),
 *  required: boolean,
 *  showLabel: boolean,
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
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
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var placeholder = soy.asserts.assertType(goog.isString(opt_data.placeholder) || opt_data.placeholder instanceof goog.soy.data.SanitizedContent, 'placeholder', opt_data.placeholder, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var required = soy.asserts.assertType(goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean');
  /** @type {boolean} */
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var value = soy.asserts.assertType(goog.isString(opt_data.value) || opt_data.value instanceof goog.soy.data.SanitizedContent, 'value', opt_data.value, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-editor');
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
      incrementalDom.elementOpenStart('p');
          incrementalDom.attr('class', 'liferay-ddm-form-field-tip');
      incrementalDom.elementOpenEnd();
        soyIdom.print(tip ? tip : '');
      incrementalDom.elementClose('p');
    }
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'form-control input-group-container');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'alloy-editor-container input-group');
          incrementalDom.attr('id', name + 'Container');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'alloy-editor alloy-editor-placeholder');
            incrementalDom.attr('contenteditable', 'false');
            incrementalDom.attr('data-placeholder', placeholder);
            incrementalDom.attr('id', name + 'Editor');
            incrementalDom.attr('name', name + 'Editor');
        incrementalDom.elementOpenEnd();
          soyIdom.print(value);
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('input');
          incrementalDom.attr('id', name);
          incrementalDom.attr('name', name);
          incrementalDom.attr('type', 'hidden');
          incrementalDom.attr('value', value);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('input');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  placeholder: (!goog.soy.data.SanitizedContent|string),
 *  required: boolean,
 *  showLabel: boolean,
 *  value: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean,
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMEditor.render';
}

exports.render.params = ["label","name","pathThemeImages","placeholder","required","showLabel","value","visible","tip"];
exports.render.types = {"label":"string","name":"string","pathThemeImages":"string","placeholder":"string","required":"bool","showLabel":"bool","value":"string","visible":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMEditor extends Component {}
Soy.register(DDMEditor, templates);
export { DDMEditor, templates };
export default templates;
/* jshint ignore:end */
