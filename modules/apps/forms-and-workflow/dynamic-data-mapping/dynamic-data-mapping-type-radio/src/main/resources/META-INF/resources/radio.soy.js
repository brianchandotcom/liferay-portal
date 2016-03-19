// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.radio = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="clearfix radio-options">';
  var optionList51 = opt_data.options;
  var optionListLen51 = optionList51.length;
  for (var optionIndex51 = 0; optionIndex51 < optionListLen51; optionIndex51++) {
    var optionData51 = optionList51[optionIndex51];
    output += ((! opt_data.inline) ? '<div class="radio">' : '') + '<label class="radio-default' + soy.$$escapeHtmlAttribute(opt_data.inline ? ' radio-inline' : '') + ' radio-option-' + soy.$$escapeHtmlAttribute(optionData51.value) + '" for="' + soy.$$escapeHtmlAttribute(optionData51.value) + '"><input class="field" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(optionData51.value) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + soy.$$filterHtmlAttributes(optionData51.status) + ' type="radio" value="' + soy.$$escapeHtmlAttribute(optionData51.value) + '" /> ' + soy.$$escapeHtml(optionData51.label) + '</label>' + ((! opt_data.inline) ? '</div>' : '');
  }
  output += '</div>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.radio.soyTemplateName = 'ddm.radio';
}
