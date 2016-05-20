// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.radio = function(opt_data, opt_ignored) {
  var output = '<div class="form-group" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="clearfix radio radio-options">';
  var optionList53 = opt_data.options;
  var optionListLen53 = optionList53.length;
  for (var optionIndex53 = 0; optionIndex53 < optionListLen53; optionIndex53++) {
    var optionData53 = optionList53[optionIndex53];
    output += ((! opt_data.inline) ? '<div class="radio">' : '') + '<label class="' + soy.$$escapeHtmlAttribute(opt_data.inline ? ' radio-inline' : '') + ' radio-option-' + soy.$$escapeHtmlAttribute(optionData53.value) + '" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData53.value) + '"><input class="field" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir || '') + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '_' + soy.$$escapeHtmlAttribute(optionData53.value) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + soy.$$filterHtmlAttributes(optionData53.status) + ' type="radio" value="' + soy.$$escapeHtmlAttribute(optionData53.value) + '" /> ' + soy.$$escapeHtml(optionData53.label) + '</label>' + ((! opt_data.inline) ? '</div>' : '');
  }
  output += '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.radio.soyTemplateName = 'ddm.radio';
}
