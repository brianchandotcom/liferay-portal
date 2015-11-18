// This file was automatically generated from date.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.date = function(opt_data, opt_ignored) {
return '\t<div class="form-group ' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-date" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</label>' : '') + '<div class="input-group input-group-container"><input aria-label="' + soy.$$escapeHtml(opt_data.label) + '" class="form-control trigger" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' type="text" value="' + soy.$$escapeHtml(opt_data.displayValue) + '" /><input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtml(opt_data.value) + '" /><span class="input-group-addon"><span class="icon-calendar"></span></span></div>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
};