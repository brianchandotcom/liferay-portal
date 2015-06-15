// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.radio = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group field-wrapper' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><span class="control-label">' + soy.$$escapeHtml(opt_data.label) + '</span>';
var optionList10 = opt_data.options;
var optionListLen10 = optionList10.length;
for (var optionIndex10 = 0; optionIndex10 < optionListLen10; optionIndex10++) {
	var optionData10 = optionList10[optionIndex10];
	output += '<div class="radio"><label for="' + soy.$$escapeHtml(optionData10.value) + '"><input class="field" dir="' + soy.$$escapeHtml(opt_data.dir) + '" id="' + soy.$$escapeHtml(optionData10.value) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(optionData10.status) + ' type="radio" value="' + soy.$$escapeHtml(optionData10.value) + '" />' + soy.$$escapeHtml(optionData10.label) + '</label></div>';
}
output += soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
return output;
};