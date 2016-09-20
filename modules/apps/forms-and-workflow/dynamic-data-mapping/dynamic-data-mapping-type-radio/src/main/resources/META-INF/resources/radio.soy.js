// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_a0071001 = function(opt_data, opt_ignored) {
  return '' + ddm.radio(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_a0071001.soyTemplateName = 'ddm.__deltemplate_s2_a0071001';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'radio', 0, ddm.__deltemplate_s2_a0071001);


ddm.radio = function(opt_data, opt_ignored) {
  var output = '';
  var displayValue__soy5 = opt_data.value ? opt_data.value : opt_data.predefinedValue;
  output += '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="clearfix radio radio-options">';
  var optionList60 = opt_data.options;
  var optionListLen60 = optionList60.length;
  for (var optionIndex60 = 0; optionIndex60 < optionListLen60; optionIndex60++) {
    var optionData60 = optionList60[optionIndex60];
    output += ((! opt_data.inline) ? '<div class="radio">' : '') + '<label class="' + soy.$$escapeHtmlAttribute(opt_data.inline ? ' radio-inline' : '') + ' radio-option-' + soy.$$escapeHtmlAttribute(optionData60.value) + '" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData60.value) + '">';
    var checked__soy37 = optionData60.value == displayValue__soy5 ? 'checked' : '';
    output += '<input ' + soy.$$filterHtmlAttributes(checked__soy37) + ' class="field" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir || '') + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData60.value) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="radio" value="' + soy.$$escapeHtmlAttribute(optionData60.value) + '" /> ' + soy.$$escapeHtml(optionData60.label) + '</label>' + ((! opt_data.inline) ? '</div>' : '');
  }
  output += '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.radio.soyTemplateName = 'ddm.radio';
}
