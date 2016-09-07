// This file was automatically generated from checkbox_multiple.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_2ea1799c = function(opt_data, opt_ignored) {
  return '' + ddm.checkbox_multiple(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_2ea1799c.soyTemplateName = 'ddm.__deltemplate_s2_2ea1799c';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'checkbox_multiple', 0, ddm.__deltemplate_s2_2ea1799c);


ddm.checkbox_multiple = function(opt_data, opt_ignored) {
  var output = '<div class="form-group liferay-ddm-form-field-checkbox-multiple" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required && opt_data.options.length > 1) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="clearfix checkbox-multiple-options">';
  var optionList97 = opt_data.options;
  var optionListLen97 = optionList97.length;
  for (var optionIndex97 = 0; optionIndex97 < optionListLen97; optionIndex97++) {
    var optionData97 = optionList97[optionIndex97];
    output += (! opt_data.inline) ? '<div>' : '';
    var checked__soy27 = '';
    var currentValueList31 = opt_data.value;
    var currentValueListLen31 = currentValueList31.length;
    for (var currentValueIndex31 = 0; currentValueIndex31 < currentValueListLen31; currentValueIndex31++) {
      var currentValueData31 = currentValueList31[currentValueIndex31];
      checked__soy27 += (currentValueData31 == optionData97.value) ? 'checked' : '';
    }
    output += ((opt_data.showAsSwitcher) ? '<label class="checkbox-default' + soy.$$escapeHtmlAttribute(opt_data.inline ? ' checkbox-multiple-switcher-inline' : '') + ' checkbox-option-' + soy.$$escapeHtmlAttribute(optionData97.value) + '" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData97.value) + '"><input ' + soy.$$filterHtmlAttributes(checked__soy27) + ' class="hide toggle-switch " ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData97.value) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="checkbox" value="' + soy.$$escapeHtmlAttribute(optionData97.value) + '" /><span aria-hidden="true" class="toggle-switch-bar"><span class="toggle-switch-handle"></span></span><span class="toggle-switch-text toggle-switch-text-right">' + soy.$$escapeHtml(optionData97.label) + ((opt_data.required && opt_data.options.length == 1) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</span></label>' : '<label class="checkbox-default' + soy.$$escapeHtmlAttribute(opt_data.inline ? ' checkbox-inline' : '') + ' checkbox-option-' + soy.$$escapeHtmlAttribute(optionData97.value) + '" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData97.value) + '"><input ' + soy.$$filterHtmlAttributes(checked__soy27) + ' class="field" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData97.value) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="checkbox" value="' + soy.$$escapeHtmlAttribute(optionData97.value) + '" /> ' + soy.$$escapeHtml(optionData97.label) + '</label>' + ((opt_data.required && opt_data.options.length == 1) ? '<span class="icon-asterisk text-warning"></span>' : '')) + ((! opt_data.inline) ? '</div>' : '');
  }
  output += '</div>' + ((opt_data.tip && opt_data.options.length == 1) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.checkbox_multiple.soyTemplateName = 'ddm.checkbox_multiple';
}
