// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.radio = function(opt_data, opt_ignored) {
  var output = '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="radio-options">';
  var optionList22 = opt_data.options;
  var optionListLen22 = optionList22.length;
  for (var optionIndex22 = 0; optionIndex22 < optionListLen22; optionIndex22++) {
    var optionData22 = optionList22[optionIndex22];
    output += ((! opt_data.inline) ? '<div class="radio">' : '') + '<label class="radio-default' + soy.$$escapeHtml(opt_data.inline ? ' radio-inline' : '') + '" for="' + soy.$$escapeHtml(optionData22.value) + '"><input class="field" dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtml(optionData22.value) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(optionData22.status) + ' type="radio" value="' + soy.$$escapeHtml(optionData22.value) + '" /> ' + soy.$$escapeHtml(optionData22.label) + '</label>' + ((! opt_data.inline) ? '</div>' : '');
  }
  output += '</div>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
  return output;
};
