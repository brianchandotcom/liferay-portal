// This file was automatically generated from validation.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.validationOption = function(opt_data, opt_ignored) {
return '\t<option ' + soy.$$escapeHtml(opt_data.option.status) + ' value="' + soy.$$escapeHtml(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>';
};


ddm.validation = function(opt_data, opt_ignored) {
var output = '\t<link href="/o/ddm-type-validation/validation.css" rel="stylesheet"></link><div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + ' lfr-ddm-form-field-validation" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><span class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</span><div class="row"><div class="col-md-6"><select class="form-control types-select">';
var optionList21 = opt_data.typesOptions;
var optionListLen21 = optionList21.length;
for (var optionIndex21 = 0; optionIndex21 < optionListLen21; optionIndex21++) {
	var optionData21 = optionList21[optionIndex21];
	output += ddm.validationOption(soy.$$augmentMap(opt_data, {option: optionData21}));
}
output += '</select></div><div class="col-md-6"><select class="form-control validations-select">';
var optionList26 = opt_data.validationsOptions;
var optionListLen26 = optionList26.length;
for (var optionIndex26 = 0; optionIndex26 < optionListLen26; optionIndex26++) {
	var optionData26 = optionList26[optionIndex26];
	output += ddm.validationOption(soy.$$augmentMap(opt_data, {option: optionData26}));
}
output += '</select></div></div><div class="row"><div class="col-md-6"><input class="field form-control parameter-input" placeholder="Contains this text" type="text" /></div><div class="col-md-6"><input class="field form-control message-input" placeholder="Validation message here" type="text" /></div></div><input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtml(opt_data.value) + '" /></div>';
return output;
};