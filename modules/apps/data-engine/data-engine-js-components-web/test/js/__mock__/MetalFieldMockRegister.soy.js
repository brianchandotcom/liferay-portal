/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from MetalFieldMockRegister.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace MetalFieldMockRegister.
 * @hassoydeltemplate {PageRenderer.RegisterFieldType.idom}
 * @public
 */

goog.module('MetalFieldMockRegister.incrementaldom');

goog.require('soy');
var soyIdom = goog.require('soy.idom');

var $templateAlias1 = Soy.getTemplate('MetalFieldMock.incrementaldom', 'render');


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s78_cd95f095(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $templateAlias1(opt_data, null, opt_ijData);
}
exports.__deltemplate_s78_cd95f095 = __deltemplate_s78_cd95f095;
if (goog.DEBUG) {
  __deltemplate_s78_cd95f095.soyTemplateName = 'MetalFieldMockRegister.__deltemplate_s78_cd95f095';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), 'metal_field', 0, __deltemplate_s78_cd95f095);

templates = exports;
return exports;

});

export { templates };
export default templates;
/* jshint ignore:end */
