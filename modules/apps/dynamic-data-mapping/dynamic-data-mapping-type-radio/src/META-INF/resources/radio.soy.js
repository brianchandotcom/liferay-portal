// This file was automatically generated from radio.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.radio = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><span class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</span>';
var optionList13 = opt_data.options;
var optionListLen13 = optionList13.length;
for (var optionIndex13 = 0; optionIndex13 < optionListLen13; optionIndex13++) {
	var optionData13 = optionList13[optionIndex13];
	output += '<div class="radio"><label for="' + soy.$$escapeHtml(optionData13.value) + '"><input class="field" dir="' + soy.$$escapeHtml(opt_data.dir) + '" id="' + soy.$$escapeHtml(optionData13.value) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(optionData13.status) + ' type="radio" value="' + soy.$$escapeHtml(optionData13.value) + '" />' + soy.$$escapeHtml(optionData13.label) + '</label></div>';
}
output += soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
return output;
};