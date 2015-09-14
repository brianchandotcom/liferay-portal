// This file was automatically generated from validation.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.validationOption = function(opt_data, opt_ignored) {
return '\t<option ' + soy.$$escapeHtml(opt_data.option.status) + ' value="' + soy.$$escapeHtml(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>';
};


ddm.validation = function(opt_data, opt_ignored) {
var output = '\t<link href="/o/ddm-type-validation/validation.css" rel="stylesheet"></link><div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + ' lfr-ddm-form-field-validation" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</label><div class="checkbox checkbox-default"><label><input ' + soy.$$escapeHtml(opt_data.enableValidationValue ? 'checked="checked"' : '') + ' class="enable-validation" type="checkbox" /> ' + soy.$$escapeHtml(opt_data.enableValidationMessage) + '</label></div><div class="' + soy.$$escapeHtml(opt_data.enableValidationValue ? '' : 'hide') + ' row"><div class="col-md-6"><select class="form-control types-select">';
var optionList27 = opt_data.typesOptions;
var optionListLen27 = optionList27.length;
for (var optionIndex27 = 0; optionIndex27 < optionListLen27; optionIndex27++) {
	var optionData27 = optionList27[optionIndex27];
	output += ddm.validationOption(soy.$$augmentMap(opt_data, {option: optionData27}));
}
output += '</select></div><div class="col-md-6"><select class="form-control validations-select">';
var optionList32 = opt_data.validationsOptions;
var optionListLen32 = optionList32.length;
for (var optionIndex32 = 0; optionIndex32 < optionListLen32; optionIndex32++) {
	var optionData32 = optionList32[optionIndex32];
	output += ddm.validationOption(soy.$$augmentMap(opt_data, {option: optionData32}));
}
output += '</select></div></div><div class="' + soy.$$escapeHtml(opt_data.enableValidationValue ? '' : 'hide') + ' row"><div class="col-md-6"><input class="field form-control ' + soy.$$escapeHtml(opt_data.parameterMessagePlaceholder ? '' : ' hide') + ' parameter-input" placeholder="' + soy.$$escapeHtml(opt_data.parameterMessagePlaceholder) + '" type="text" value="' + soy.$$escapeHtml(opt_data.parameterValue) + '" /></div><div class="col-md-6"><input class="field form-control message-input" placeholder="' + soy.$$escapeHtml(opt_data.errorMessagePlaceholder) + '" type="text" value="' + soy.$$escapeHtml(opt_data.errorMessageValue) + '" /></div></div><input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtml(opt_data.value) + '" /></div>';
return output;
};