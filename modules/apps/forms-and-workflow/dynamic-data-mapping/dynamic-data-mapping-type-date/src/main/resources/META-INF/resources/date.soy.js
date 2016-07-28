// This file was automatically generated from date.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_cfb11076 = function(opt_data, opt_ignored) {
  return '' + ddm.date(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_cfb11076.soyTemplateName = 'ddm.__deltemplate_s2_cfb11076';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'date', 0, ddm.__deltemplate_s2_cfb11076);


ddm.date = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-date" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="input-group input-group-container"><input aria-label="' + soy.$$escapeHtmlAttribute(opt_data.label) + '" class="form-control trigger" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' type="text" value="' + soy.$$escapeHtmlAttribute(opt_data.displayValue) + '" /><input name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" /><span class="input-group-addon"><span class="icon-calendar"></span></span></div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
};
if (goog.DEBUG) {
  ddm.date.soyTemplateName = 'ddm.date';
}
