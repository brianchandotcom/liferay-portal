// This file was automatically generated from editor.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.editor = function(opt_data, opt_ignored) {
  return '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-editor" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="input-group-container"><div class="alloy-editor-container" id="' + soy.$$escapeHtml(opt_data.name) + 'Container"><div class="alloy-editor alloy-editor-placeholder form-control" contenteditable="false" data-placeholder="' + soy.$$escapeHtml(opt_data.placeholder) + '" id="' + soy.$$escapeHtml(opt_data.name) + 'Editor" name="' + soy.$$escapeHtml(opt_data.name) + 'Editor">' + soy.$$filterNoAutoescape(opt_data.value) + '</div></div><input id="' + soy.$$escapeHtml(opt_data.name) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtml(opt_data.value) + '" /></div>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
};
