// This file was automatically generated from paragraph.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_8906c307 = function(opt_data, opt_ignored) {
  return '' + ddm.paragraph(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_8906c307.soyTemplateName = 'ddm.__deltemplate_s2_8906c307';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'paragraph', 0, ddm.__deltemplate_s2_8906c307);


ddm.paragraph = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-paragraph" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + '</label><div>' + soy.$$filterNoAutoescape(opt_data.text) + '</div></div>';
};
if (goog.DEBUG) {
  ddm.paragraph.soyTemplateName = 'ddm.paragraph';
}
