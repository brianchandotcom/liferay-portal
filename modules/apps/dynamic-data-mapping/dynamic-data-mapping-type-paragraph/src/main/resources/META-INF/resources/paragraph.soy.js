// This file was automatically generated from paragraph.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.paragraph = function(opt_data, opt_ignored) {
return '\t<div class="form-group form-inline' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><p dir="' + soy.$$escapeHtml(opt_data.dir) + '">' + soy.$$escapeHtml(opt_data.text) + '</p></div>';
};