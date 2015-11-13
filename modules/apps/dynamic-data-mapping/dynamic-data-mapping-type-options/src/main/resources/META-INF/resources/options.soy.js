// This file was automatically generated from options.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.options = function(opt_data, opt_ignored) {
return '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-options" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</label>' : '') + '<input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" /><div class="options"></div></div>';
};