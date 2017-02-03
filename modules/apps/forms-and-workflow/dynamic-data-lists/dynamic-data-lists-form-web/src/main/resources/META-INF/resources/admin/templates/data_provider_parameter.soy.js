// This file was automatically generated from data_provider_parameter.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddl.data_provider_parameter.
 * @public
 */

if (typeof ddl == 'undefined') { var ddl = {}; }
if (typeof ddl.data_provider_parameter == 'undefined') { ddl.data_provider_parameter = {}; }


ddl.data_provider_parameter.settings = function(opt_data, opt_ignored) {
  return '<div class="data-provider-parameter-input-container"><p class="data-provider-parameter-input-required-field">' + soy.$$escapeHtml(opt_data.strings.requiredField) + '</p><p class="data-provider-parameter-input-description">' + soy.$$escapeHtml(opt_data.strings.dataProviderParameterInputDescription) + '</p><div class="data-provider-parameter-input-list"></div></div><div class="data-provider-parameter-output-container"><p class="data-provider-parameter-output-description">' + soy.$$escapeHtml(opt_data.strings.dataProviderParameterOutputDescription) + '</p><div class="data-provider-parameter-output-list"></div></div>';
};
if (goog.DEBUG) {
  ddl.data_provider_parameter.settings.soyTemplateName = 'ddl.data_provider_parameter.settings';
}
