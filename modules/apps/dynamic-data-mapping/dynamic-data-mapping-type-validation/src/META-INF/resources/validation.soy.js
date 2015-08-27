// This file was automatically generated from validation.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.validation = function(opt_data, opt_ignored) {
return '\t<link href="/o/ddm-type-validation/validation.css" rel="stylesheet"></link><div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + ' lfr-ddm-form-field-validation" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><span class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<b>*</b>' : '') + '</span><div class="row"><div class="col-md-6"><select class="form-control types-select"></select></div><div class="col-md-6"><select class="form-control validations-select"></select></div></div><div class="row"><div class="col-md-6"><input class="field form-control parameter-input" placeholder="Contains this text" type="text" /></div><div class="col-md-6"><input class="field form-control message-input" placeholder="Validation message here" type="text" /></div></div><input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtml(opt_data.value) + '" /></div>';
};