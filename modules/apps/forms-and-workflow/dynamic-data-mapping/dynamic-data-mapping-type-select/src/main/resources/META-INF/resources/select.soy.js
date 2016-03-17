// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.select = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="input-group-container"><select class="form-control" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + soy.$$filterHtmlAttributes(opt_data.multiple) + ' ' + ((opt_data.multiple == 'multiple') ? 'size="' + soy.$$escapeHtmlAttribute(opt_data.options.length) + '"' : '') + '>' + ((! opt_data.readOnly) ? '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" disabled ' + ((! opt_data.value) ? 'selected' : '') + ' value="">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</option>' : '');
  var optionList62 = opt_data.options;
  var optionListLen62 = optionList62.length;
  for (var optionIndex62 = 0; optionIndex62 < optionListLen62; optionIndex62++) {
    var optionData62 = optionList62[optionIndex62];
    output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + soy.$$filterHtmlAttributes(optionData62.status) + ' value="' + soy.$$escapeHtmlAttribute(optionData62.value) + '">' + soy.$$escapeHtml(optionData62.label) + '</option>';
  }
  output += '</select></div>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.select.soyTemplateName = 'ddm.select';
}
