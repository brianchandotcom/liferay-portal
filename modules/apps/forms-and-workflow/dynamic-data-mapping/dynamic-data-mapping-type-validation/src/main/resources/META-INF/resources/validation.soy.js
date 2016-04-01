// This file was automatically generated from validation.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.validationOption = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<option ' + soy.$$filterHtmlAttributes(opt_data.option.status) + ' value="' + soy.$$escapeHtmlAttribute(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>');
};
if (goog.DEBUG) {
  ddm.validationOption.soyTemplateName = 'ddm.validationOption';
}


ddm.validation = function(opt_data, opt_ignored) {
  var output = '<div class="form-group lfr-ddm-form-field-validation" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="form-group"><label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'EnableValidation"><input class="enable-validation toggle-switch" ' + soy.$$filterHtmlAttributes(opt_data.enableValidationValue ? 'checked' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'EnableValidation" type="checkbox" value="true" /><span aria-hidden="true" class="toggle-switch-bar"><span class="toggle-switch-handle" data-label-off="' + soy.$$escapeHtmlAttribute(opt_data.enableValidationMessage) + '" data-label-on="' + soy.$$escapeHtmlAttribute(opt_data.disableValidationMessage) + '"></span></span></label></div><div class="' + soy.$$escapeHtmlAttribute(opt_data.enableValidationValue ? '' : 'hide') + ' row"><div class="col-md-6"><select class="form-control types-select">';
  var optionList28 = opt_data.typesOptions;
  var optionListLen28 = optionList28.length;
  for (var optionIndex28 = 0; optionIndex28 < optionListLen28; optionIndex28++) {
    var optionData28 = optionList28[optionIndex28];
    output += ddm.validationOption(soy.$$augmentMap(opt_data, {option: optionData28}));
  }
  output += '</select></div><div class="col-md-6"><select class="form-control validations-select">';
  var optionList33 = opt_data.validationsOptions;
  var optionListLen33 = optionList33.length;
  for (var optionIndex33 = 0; optionIndex33 < optionListLen33; optionIndex33++) {
    var optionData33 = optionList33[optionIndex33];
    output += ddm.validationOption(soy.$$augmentMap(opt_data, {option: optionData33}));
  }
  output += '</select></div></div><div class="' + soy.$$escapeHtmlAttribute(opt_data.enableValidationValue ? '' : 'hide') + ' row"><div class="col-md-6"><input class="field form-control ' + soy.$$escapeHtmlAttribute(opt_data.parameterMessagePlaceholder ? '' : ' hide') + ' parameter-input" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.parameterMessagePlaceholder) + '" type="text" value="' + soy.$$escapeHtmlAttribute(opt_data.parameterValue) + '" /></div><div class="col-md-6"><input class="field form-control message-input" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.errorMessagePlaceholder) + '" type="text" value="' + soy.$$escapeHtmlAttribute(opt_data.errorMessageValue) + '" /></div></div><input name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" /></div>';
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.validation.soyTemplateName = 'ddm.validation';
}
