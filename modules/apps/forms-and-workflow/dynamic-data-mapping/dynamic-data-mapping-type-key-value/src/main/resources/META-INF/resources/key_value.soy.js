// This file was automatically generated from key_value.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.key_value = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-key-value liferay-ddm-form-field-text" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' : '') + '<div class="input-group-container ' + ((opt_data.tooltip) ? 'input-group-default' : '') + '"><input class="field form-control" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '" type="text" value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" />' + ((opt_data.tooltip) ? '<span class="input-group-addon"><span class="input-group-addon-content"><a class="help-icon help-icon-default icon-monospaced icon-question" data-original-title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '" data-toggle="popover" href="javascript:;" title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '"></a></span></span>' : '') + '</div><div class="' + soy.$$escapeHtmlAttribute(opt_data.editing ? 'active ' : '') + 'key-value-editor"><label class="control-label key-value-label">' + soy.$$escapeHtml(opt_data.strings.keyLabel) + ':</label><span class="key-value-output">' + soy.$$escapeHtml(opt_data.key) + '</span><input class="key-value-input" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.key) + '" /><a class="key-value-done" href="javascript:;">' + soy.$$escapeHtml(opt_data.strings.done) + '</a><a class="key-value-cancel" href="javascript:;">' + soy.$$escapeHtml(opt_data.strings.cancel) + '</a></div></div>');
};
if (goog.DEBUG) {
  ddm.key_value.soyTemplateName = 'ddm.key_value';
}
