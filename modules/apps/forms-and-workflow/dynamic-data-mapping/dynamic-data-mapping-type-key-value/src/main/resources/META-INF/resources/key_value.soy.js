// This file was automatically generated from key_value.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_7cdb575b = function(opt_data, opt_ignored) {
  return '' + ddm.key_value(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_7cdb575b.soyTemplateName = 'ddm.__deltemplate_s2_7cdb575b';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'key_value', 0, ddm.__deltemplate_s2_7cdb575b);


ddm.key_value = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-key-value liferay-ddm-form-field-text" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' : '') + '<div class="input-group-container ' + ((opt_data.tooltip) ? 'input-group-default' : '') + '"><input class="field form-control" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '" type="text" value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" />' + ((opt_data.tooltip) ? '<span class="input-group-addon"><span class="input-group-addon-content"><a class="help-icon help-icon-default icon-monospaced icon-question" data-original-title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '" data-toggle="popover" href="javascript:;" title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '"></a></span></span>' : '') + '</div><div class="active key-value-editor"><label class="control-label key-value-label">' + soy.$$escapeHtml(opt_data.strings.keyLabel) + ':</label><input class="key-value-input" ' + ((! opt_data.keyInputEnabled) ? 'readonly' : '') + ' size="' + soy.$$escapeHtmlAttribute(opt_data.keyInputSize) + '" tabindex="-1" type="text" value=\'' + soy.$$escapeHtmlAttribute(opt_data.key) + '\' /></div></div>');
};
if (goog.DEBUG) {
  ddm.key_value.soyTemplateName = 'ddm.key_value';
}
