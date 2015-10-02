// This file was automatically generated from checkbox.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.checkbox = function(opt_data, opt_ignored) {
  return '\t<div class="form-group form-inline' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '">' + ((opt_data.showAsSwitcher) ? '<div class="checkbox"><label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '"><input class="toggle-switch" id="' + soy.$$escapeHtml(opt_data.name) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" type="checkbox" ' + soy.$$escapeHtml(opt_data.status) + ' value="true" /><span aria-hidden="true" class="toggle-switch-bar"><span class="toggle-switch-handle" data-label-off="' + soy.$$escapeHtml(opt_data.label) + '" data-label-on="' + soy.$$escapeHtml(opt_data.label) + '"></span></span></label></div>' : '<div class="checkbox checkbox-default">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' : '') + '<input id="' + soy.$$escapeHtml(opt_data.name) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" type="checkbox" ' + soy.$$escapeHtml(opt_data.status) + ' value="true" />' + ((opt_data.showLabel) ? ' ' + soy.$$escapeHtml(opt_data.label) : '') + ((opt_data.showLabel) ? ((opt_data.required) ? '<b>*</b>' : '') + '</label>' : '') + '</div>') + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
};
