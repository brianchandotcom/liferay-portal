// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.select = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group field-wrapper' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><div class="form-group input-select-wrapper"><label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + '</label><select id="' + soy.$$escapeHtml(opt_data.name) + '" class="form-control" dir="' + soy.$$escapeHtml(opt_data.dir) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(opt_data.multiple) + '>';
var optionList20 = opt_data.options;
var optionListLen20 = optionList20.length;
for (var optionIndex20 = 0; optionIndex20 < optionListLen20; optionIndex20++) {
	var optionData20 = optionList20[optionIndex20];
	output += '<option dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + soy.$$escapeHtml(optionData20.status) + ' value="' + soy.$$escapeHtml(optionData20.value) + '">' + soy.$$escapeHtml(optionData20.label) + '</option>';
}
output += '</select>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div></div>';
return output;
};