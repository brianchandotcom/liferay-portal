// This file was automatically generated from options.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_185c245a = function(opt_data, opt_ignored) {
  return '' + ddm.options(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_185c245a.soyTemplateName = 'ddm.__deltemplate_s2_185c245a';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'options', 0, ddm.__deltemplate_s2_185c245a);


ddm.options = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-options" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' : '') + '<input name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden" /><div class="options"></div></div>');
};
if (goog.DEBUG) {
  ddm.options.soyTemplateName = 'ddm.options';
}
