// This file was automatically generated from editor.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.editor = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-editor" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="input-group-container"><div class="alloy-editor-container" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'Container"><div class="alloy-editor alloy-editor-placeholder form-control" contenteditable="false" data-placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'Editor" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'Editor">' + soy.$$filterNoAutoescape(opt_data.value) + '</div></div><input id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" /></div>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
};
if (goog.DEBUG) {
  ddm.editor.soyTemplateName = 'ddm.editor';
}
