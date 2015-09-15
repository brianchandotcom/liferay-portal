// This file was automatically generated from button-switch.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.buttonSwitch = function(opt_data, opt_ignored) {
  return '\t<div class="form-group form-inline' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '"><input type="hidden" name="' + soy.$$escapeHtml(opt_data.name) + '" /><div class="field" id="' + soy.$$escapeHtml(opt_data.name) + '"></div>' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</label>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div>';
};
