/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from FieldsRegister.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace FieldsRegister.
 * @hassoydeltemplate {LayoutRenderer.RegisterFieldType.idom}
 * @public
 */

goog.module('FieldsRegister.incrementaldom');

goog.require('soy');
var soyIdom = goog.require('soy.idom');

var $templateAlias1 = Soy.getTemplate('Fields.incrementaldom', 'render');


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_RegisterFieldType_radio = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $templateAlias1(soy.$$assignDefaults({type: 'radio'}, opt_data), opt_ijData);
};
exports.__deltemplate__LayoutRenderer_RegisterFieldType_radio = __deltemplate__LayoutRenderer_RegisterFieldType_radio;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_RegisterFieldType_radio.soyTemplateName = 'FieldsRegister.__deltemplate__LayoutRenderer_RegisterFieldType_radio';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.RegisterFieldType.idom'), 'radio', 0, __deltemplate__LayoutRenderer_RegisterFieldType_radio);


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_RegisterFieldType_text = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $templateAlias1(soy.$$assignDefaults({type: 'text'}, opt_data), opt_ijData);
};
exports.__deltemplate__LayoutRenderer_RegisterFieldType_text = __deltemplate__LayoutRenderer_RegisterFieldType_text;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_RegisterFieldType_text.soyTemplateName = 'FieldsRegister.__deltemplate__LayoutRenderer_RegisterFieldType_text';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.RegisterFieldType.idom'), 'text', 0, __deltemplate__LayoutRenderer_RegisterFieldType_text);


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_RegisterFieldType_select = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $templateAlias1(soy.$$assignDefaults({type: 'select'}, opt_data), opt_ijData);
};
exports.__deltemplate__LayoutRenderer_RegisterFieldType_select = __deltemplate__LayoutRenderer_RegisterFieldType_select;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_RegisterFieldType_select.soyTemplateName = 'FieldsRegister.__deltemplate__LayoutRenderer_RegisterFieldType_select';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.RegisterFieldType.idom'), 'select', 0, __deltemplate__LayoutRenderer_RegisterFieldType_select);

templates = exports;
return exports;

});

export { templates };
export default templates;
/* jshint ignore:end */
