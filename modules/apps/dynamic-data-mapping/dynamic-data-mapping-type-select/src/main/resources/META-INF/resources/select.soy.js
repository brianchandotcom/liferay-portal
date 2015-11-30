// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.select = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</label>' : '') + '<select class="form-control" dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtml(opt_data.name) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(opt_data.multiple) + '>' + ((! opt_data.readOnly) ? '<option dir="' + soy.$$escapeHtml(opt_data.dir) + '" selected value="">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</option>' : '');
var optionList38 = opt_data.options;
var optionListLen38 = optionList38.length;
for (var optionIndex38 = 0; optionIndex38 < optionListLen38; optionIndex38++) {
	var optionData38 = optionList38[optionIndex38];
	output += '<option dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + soy.$$escapeHtml(optionData38.status) + ' value="' + soy.$$escapeHtml(optionData38.value) + '">' + soy.$$escapeHtml(optionData38.label) + '</option>';
}
output += '</select>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div></div>';
return output;
};