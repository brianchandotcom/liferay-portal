// This file was automatically generated from paragraph.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.paragraph = function(opt_data, opt_ignored) {
  return '<div class="form-group form-inline' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-paragraph" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + '</label><div>' + soy.$$filterNoAutoescape(opt_data.text) + '</div></div>';
};
if (goog.DEBUG) {
  ddm.paragraph.soyTemplateName = 'ddm.paragraph';
}
