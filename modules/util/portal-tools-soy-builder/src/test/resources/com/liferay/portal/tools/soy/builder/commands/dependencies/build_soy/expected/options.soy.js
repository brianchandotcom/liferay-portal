// This file was automatically generated from options.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

goog.provide('ddm');

goog.require('soy');
goog.require('soydata.VERY_UNSAFE');


/**
 * @param {Object<string, *>=} opt_data
 * @param {soy.IjData|Object<string, *>=} opt_ijData
 * @param {soy.IjData|Object<string, *>=} opt_ijData_deprecated
 * @return {string}
 * @suppress {checkTypes}
 */
ddm.__deltemplate__ddm_field_options = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = /** @type {!soy.IjData} */ (opt_ijData_deprecated || opt_ijData);
  opt_data = opt_data || {};
  return '' + (ddm.options(/** @type {?} */ (opt_data), opt_ijData));
};
if (goog.DEBUG) {
  ddm.__deltemplate__ddm_field_options.soyTemplateName = 'ddm.__deltemplate__ddm_field_options';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'options', 0, ddm.__deltemplate__ddm_field_options);


/**
 * @param {Object<string, *>=} opt_data
 * @param {soy.IjData|Object<string, *>=} opt_ijData
 * @param {soy.IjData|Object<string, *>=} opt_ijData_deprecated
 * @return {!goog.soy.data.SanitizedHtml}
 * @suppress {checkTypes}
 */
ddm.options = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = /** @type {!soy.IjData} */ (opt_ijData_deprecated || opt_ijData);
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-options" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"' + (goog.DEBUG && soy.$$debugSoyTemplateInfo ? ' data-debug-soy="ddm.options resources/com/liferay/portal/tools/soy/builder/commands/dependencies/build_soy/options.soy:20"' : '') + '>' + (opt_data.showLabel ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + (opt_data.required ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' : '') + '<input name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden"/><div class="options"></div></div>');
};
if (goog.DEBUG) {
  ddm.options.soyTemplateName = 'ddm.options';
}
