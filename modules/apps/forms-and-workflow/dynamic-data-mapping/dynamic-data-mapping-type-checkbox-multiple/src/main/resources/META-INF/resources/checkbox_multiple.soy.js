// This file was automatically generated from multi_checkbox.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.multi_checkbox = function(opt_data, opt_ignored) {
  var output = '<div class="form-group liferay-ddm-form-field-multi-checkbox" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel && opt_data.options.length > 1) ? '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="clearfix multi-checkbox-options">';
  var optionList83 = opt_data.options;
  var optionListLen83 = optionList83.length;
  for (var optionIndex83 = 0; optionIndex83 < optionListLen83; optionIndex83++) {
    var optionData83 = optionList83[optionIndex83];
    output += ((! opt_data.inline) ? '<div class="checkbox">' : '') + ((opt_data.showAsSwitcher) ? '<label class="checkbox-default' + soy.$$escapeHtmlAttribute(opt_data.inline ? ' checkbox-inline' : '') + ' checkbox-option-' + soy.$$escapeHtmlAttribute(optionData83.value) + '" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData83.value) + '"><input class="hide toggle-switch " ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData83.value) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + soy.$$filterHtmlAttributes(optionData83.status) + ' type="checkbox" value="' + soy.$$escapeHtmlAttribute(optionData83.value) + '" /><span aria-hidden="true" class="toggle-switch-bar"><span class="toggle-switch-handle"></span></span><span class="toggle-switch-text toggle-switch-text-right">' + soy.$$escapeHtml(optionData83.label) + '</span></label>' : '<label class="checkbox-default' + soy.$$escapeHtmlAttribute(opt_data.inline ? ' checkbox-inline' : '') + ' checkbox-option-' + soy.$$escapeHtmlAttribute(optionData83.value) + '" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData83.value) + '"><input class="field" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData83.value) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + soy.$$filterHtmlAttributes(optionData83.status) + ' type="checkbox" value="' + soy.$$escapeHtmlAttribute(optionData83.value) + '" /> ' + soy.$$escapeHtml(optionData83.label) + '</label>') + ((! opt_data.inline) ? '</div>' : '');
  }
  output += '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.multi_checkbox.soyTemplateName = 'ddm.multi_checkbox';
}
