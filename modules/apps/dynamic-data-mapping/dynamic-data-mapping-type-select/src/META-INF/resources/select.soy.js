// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.select = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group field-wrapper" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><div class="form-group input-select-wrapper"><label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + '</label><select id="' + soy.$$escapeHtml(opt_data.name) + '" class="form-control" dir="' + soy.$$escapeHtml(opt_data.dir) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(opt_data.multiple) + '>';
var optionList18 = opt_data.options;
var optionListLen18 = optionList18.length;
for (var optionIndex18 = 0; optionIndex18 < optionListLen18; optionIndex18++) {
	var optionData18 = optionList18[optionIndex18];
	output += '<option dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + soy.$$escapeHtml(optionData18.status) + ' value="' + soy.$$escapeHtml(optionData18.value) + '">' + soy.$$escapeHtml(optionData18.label) + '</option>';
}
output += '</select>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div></div>';
return output;
};