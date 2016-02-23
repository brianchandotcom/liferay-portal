// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.select = function(opt_data, opt_ignored) {
  var output = '\t<div class="form-group' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? '<label class="control-label" for="' + soy.$$escapeHtml(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<select class="form-control" dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' id="' + soy.$$escapeHtml(opt_data.name) + '" name="' + soy.$$escapeHtml(opt_data.name) + '" ' + soy.$$escapeHtml(opt_data.multiple) + ' ' + ((opt_data.multiple == 'multiple') ? 'size="' + soy.$$escapeHtml(opt_data.options.length) + '"' : '') + '>' + ((! opt_data.readOnly) ? '<option dir="' + soy.$$escapeHtml(opt_data.dir) + '" disabled ' + ((! opt_data.value) ? 'selected' : '') + ' value="">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</option>' : '');
  var optionList53 = opt_data.options;
  var optionListLen53 = optionList53.length;
  for (var optionIndex53 = 0; optionIndex53 < optionListLen53; optionIndex53++) {
    var optionData53 = optionList53[optionIndex53];
    output += '<option dir="' + soy.$$escapeHtml(opt_data.dir) + '" ' + soy.$$escapeHtml(optionData53.status) + ' value="' + soy.$$escapeHtml(optionData53.value) + '">' + soy.$$escapeHtml(optionData53.label) + '</option>';
  }
  output += '</select>' + soy.$$filterNoAutoescape(opt_data.childElementsHTML) + '</div></div>';
  return output;
};
