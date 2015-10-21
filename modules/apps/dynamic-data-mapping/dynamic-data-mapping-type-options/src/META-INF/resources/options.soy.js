// This file was automatically generated from options.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.options = function(opt_data, opt_ignored) {
var output = '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</label>' : '') + '<input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" /><div class="auto-fields">';
if (opt_data.value.length == 0) {
	output += ddm.option(soy.$$augmentMap(opt_data, {label: '', value: ''}));
} else {
	var optionList25 = opt_data.value;
	var optionListLen25 = optionList25.length;
	for (var optionIndex25 = 0; optionIndex25 < optionListLen25; optionIndex25++) {
	  var optionData25 = optionList25[optionIndex25];
	  output += ddm.option(soy.$$augmentMap(opt_data, {label: optionData25.label, value: optionData25.value}));
	}
}
output += '</div></div>';
return output;
};


ddm.option = function(opt_data, opt_ignored) {
return '\t<div class="ddm-options-row lfr-form-row"><div class="row"><div class="col-md-5"><label>Label</label></div><div class="col-md-5"><label>Value</label></div><div class="col-md-2"></div></div><div class="row"><div class="col-md-5"><input class="ddm-options-field-label form-control" name="label" type="text" value="' + soy.$$escapeHtml(opt_data.label) + '" /></div><div class="col-md-5"><input class="ddm-options-field-value form-control" name="value" type="text" value="' + soy.$$escapeHtml(opt_data.value) + '" /></div><div class="col-md-2"></div></div></div>';
};