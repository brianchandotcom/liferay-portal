// This file was automatically generated from captcha.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_ffe4bfd9 = function(opt_data, opt_ignored) {
  return '' + ddm.captcha(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_ffe4bfd9.soyTemplateName = 'ddm.__deltemplate_s2_ffe4bfd9';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'captcha', 0, ddm.__deltemplate_s2_ffe4bfd9);


ddm.captcha = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-captcha" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$filterNoAutoescape(opt_data.html) + '</div>';
};
if (goog.DEBUG) {
  ddm.captcha.soyTemplateName = 'ddm.captcha';
}
