// This file was automatically generated from checkbox.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_560e40fa = function(opt_data, opt_ignored) {
  return '' + ddm.checkbox(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_560e40fa.soyTemplateName = 'ddm.__deltemplate_s2_560e40fa';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'checkbox', 0, ddm.__deltemplate_s2_560e40fa);


ddm.checkbox = function(opt_data, opt_ignored) {
  var output = '';
  var status__soy5 = opt_data.value ? 'checked' : '';
  output += '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-checkbox" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showAsSwitcher) ? '<label aria-checked="' + soy.$$escapeHtmlAttribute(status__soy5 ? true : false) + '" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" role="checkbox"><input class="toggle-switch" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="checkbox" ' + soy.$$filterHtmlAttributes(status__soy5) + ' value="true" /><span aria-hidden="true" class="toggle-switch-bar"><span class="toggle-switch-handle"></span></span><span class="toggle-switch-text toggle-switch-text-right">' + ((opt_data.showLabel) ? soy.$$escapeHtml(opt_data.label) : '') + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</span></label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '<div class="checkbox">' + ((opt_data.showLabel) ? '<label for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' : '') + '<input ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="checkbox" ' + soy.$$filterHtmlAttributes(status__soy5) + ' value="true" />' + ((opt_data.showLabel) ? ' ' + soy.$$escapeHtml(opt_data.label) : '') + ((opt_data.showLabel) ? ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '</div>') + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.checkbox.soyTemplateName = 'ddm.checkbox';
}
