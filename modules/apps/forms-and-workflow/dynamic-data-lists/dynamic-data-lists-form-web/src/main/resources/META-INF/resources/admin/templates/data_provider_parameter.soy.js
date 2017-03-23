// This file was automatically generated from data_provider_parameter.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.data_provider_parameter.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.data_provider_parameter == 'undefined') { ddl.data_provider_parameter = {}; }


ddl.data_provider_parameter.settings = function(opt_data, opt_ignored) {
  return ((opt_data.hasInputs) ? '<div class="data-provider-parameter-input-container">' + ((opt_data.hasRequiredInputs) ? '<div class="data-provider-label-container"><p class="data-provider-parameter-input-required-field">' + soy.$$escapeHtml(opt_data.strings.requiredField) + '</p><span class="icon-asterisk text-warning"></span></div>' : '') + '<div class="data-provider-label-container"><p class="data-provider-parameter-input"><b>' + soy.$$escapeHtml(opt_data.strings.dataProviderParameterInput) + '</b></p><p class="data-provider-parameter-input-description">' + soy.$$escapeHtml(opt_data.strings.dataProviderParameterInputDescription) + '</p></div><div class="data-provider-parameter-input-list row"></div></div>' : '') + '<div class="data-provider-parameter-output-container"><div class="data-provider-label-container"><p class="data-provider-parameter-output"><b>' + soy.$$escapeHtml(opt_data.strings.dataProviderParameterOutput) + '</b></p><p class="data-provider-parameter-output-description">' + soy.$$escapeHtml(opt_data.strings.dataProviderParameterOutputDescription) + '</p></div><div class="data-provider-parameter-output-list row"></div></div>';
};
if (goog.DEBUG) {
  ddl.data_provider_parameter.settings.soyTemplateName = 'ddl.data_provider_parameter.settings';
}
