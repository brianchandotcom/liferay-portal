// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.radio = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group field-wrapper" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><span class="control-label">' + soy.$$escapeHtml(opt_data.label) + '</span>';
var optionList8 = opt_data.options;
var optionListLen8 = optionList8.length;
for (var optionIndex8 = 0; optionIndex8 < optionListLen8; optionIndex8++) {
	var optionData8 = optionList8[optionIndex8];
	output += '<div class="radio"><label for="' + soy.$$escapeHtml(optionData8.value) + '"><input class="field" dir="' + soy.$$escapeHtml(opt_data.dir) + '" id="' + soy.$$escapeHtml(optionData8.value) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(optionData8.status) + ' type="radio" value="' + soy.$$escapeHtml(optionData8.value) + '" />' + soy.$$escapeHtml(optionData8.label) + '</label></div>';
}
output += soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
return output;
};