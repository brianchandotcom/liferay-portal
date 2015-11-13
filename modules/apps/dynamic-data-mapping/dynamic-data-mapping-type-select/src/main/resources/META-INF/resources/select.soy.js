// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.select = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</label>' : '') + '<select class="form-control" dir="' + soy.$$escapeHtml(opt_data.dir) + '" id="' + soy.$$escapeHtml(opt_data.name) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(opt_data.multiple) + ' ' + ((opt_data.readOnly) ? 'disabled' : '') + '>';
var optionList31 = opt_data.options;
var optionListLen31 = optionList31.length;
for (var optionIndex31 = 0; optionIndex31 < optionListLen31; optionIndex31++) {
	var optionData31 = optionList31[optionIndex31];
	output += '<option dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + soy.$$escapeHtml(optionData31.status) + ' value="' + soy.$$escapeHtml(optionData31.value) + '">' + soy.$$escapeHtml(optionData31.label) + '</option>';
}
output += '</select>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div></div>';
return output;
};