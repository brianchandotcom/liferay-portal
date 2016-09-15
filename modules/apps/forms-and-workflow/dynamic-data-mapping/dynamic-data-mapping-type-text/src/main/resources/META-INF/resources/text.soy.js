// This file was automatically generated from text.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_10305041 = function(opt_data, opt_ignored) {
  return '' + ddm.text(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_10305041.soyTemplateName = 'ddm.__deltemplate_s2_10305041';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'text', 0, ddm.__deltemplate_s2_10305041);


ddm.text = function(opt_data, opt_ignored) {
  var output = '';
  var displayValue__soy5 = opt_data.value ? opt_data.value : opt_data.predefinedValue ? opt_data.predefinedValue : '';
  output += '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-text ' + soy.$$escapeHtmlAttribute(opt_data.tip ? 'liferay-ddm-form-field-has-tip' : '') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="input-group-container ' + ((opt_data.tooltip) ? 'input-group-default' : '') + '">' + ((opt_data.displayStyle == 'multiline') ? '<textarea class="field form-control" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '" ' + ((opt_data.readOnly) ? 'readonly' : '') + ' >' + soy.$$escapeHtmlRcdata(displayValue__soy5) + '</textarea>' : '<input class="field form-control" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.placeholder) + '" ' + ((opt_data.readOnly) ? 'readonly' : '') + ' type="text" value="' + soy.$$escapeHtmlAttribute(displayValue__soy5) + '">') + ((opt_data.tooltip) ? '<span class="input-group-addon"><span class="input-group-addon-content"><a class="help-icon help-icon-default icon-monospaced icon-question" data-original-title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '" data-toggle="popover" href="javascript:;" title="' + soy.$$escapeHtmlAttribute(opt_data.tooltip) + '"></a></span></span>' : '') + '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.text.soyTemplateName = 'ddm.text';
}
