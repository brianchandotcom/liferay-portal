// This file was automatically generated from options.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.options = function(opt_data, opt_ignored) {
  var output = '\t<div class="form-group field-wrapper" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><span class="control-label">' + soy.$$escapeHtml(opt_data.label) + '</span><input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" /><div class="auto-fields">';
  if (opt_data.value.length == 0) {
    output += ddm.option(soy.$$augmentMap(opt_data, {label: '', value: ''}));
  } else {
    var optionList16 = opt_data.value;
    var optionListLen16 = optionList16.length;
    for (var optionIndex16 = 0; optionIndex16 < optionListLen16; optionIndex16++) {
      var optionData16 = optionList16[optionIndex16];
      output += ddm.option(soy.$$augmentMap(opt_data, {label: optionData16.label, value: optionData16.value}));
    }
  }
  output += '</div></div>';
  return output;
};


ddm.option = function(opt_data, opt_ignored) {
  return '\t<div class="lfr-form-row"><div class="row"><div class="col-md-4"><label>Label</label></div><div class="col-md-4"><label>Value</label></div><div class="col-md-4"></div></div><div class="row"><div class="col-md-4"><input class="ddm-options-field-label" name="label" value="' + soy.$$escapeHtml(opt_data.label) + '" /></div><div class="col-md-4"><input class="ddm-options-field-value" name="value" value="' + soy.$$escapeHtml(opt_data.value) + '" /></div><div class="col-md-4"></div></div></div>';
};
