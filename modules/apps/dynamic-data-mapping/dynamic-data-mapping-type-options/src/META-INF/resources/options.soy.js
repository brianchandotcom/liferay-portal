// This file was automatically generated from options.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.options = function(opt_data, opt_ignored) {
  var output = '\t<div class="form-group field-wrapper' + soy.$$escapeHtml(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtml(opt_data.name) + '"><span class="control-label">' + soy.$$escapeHtml(opt_data.label) + '</span><input name="' + soy.$$escapeHtml(opt_data.name) + '" type="hidden" /><div class="auto-fields">';
  if (opt_data.value.length == 0) {
    output += ddm.option(soy.$$augmentMap(opt_data, {label: '', value: ''}));
  } else {
    var optionList18 = opt_data.value;
    var optionListLen18 = optionList18.length;
    for (var optionIndex18 = 0; optionIndex18 < optionListLen18; optionIndex18++) {
      var optionData18 = optionList18[optionIndex18];
      output += ddm.option(soy.$$augmentMap(opt_data, {label: optionData18.label, value: optionData18.value}));
    }
  }
  output += '</div></div>';
  return output;
};


ddm.option = function(opt_data, opt_ignored) {
  return '\t<div class="ddm-options-row lfr-form-row"><div class="row"><div class="col-md-4"><label>Label</label></div><div class="col-md-4"><label>Value</label></div><div class="col-md-4"></div></div><div class="row"><div class="col-md-4"><input class="ddm-options-field-label" name="label" type="text" value="' + soy.$$escapeHtml(opt_data.label) + '" /></div><div class="col-md-4"><input class="ddm-options-field-value" name="value" type="text" value="' + soy.$$escapeHtml(opt_data.value) + '" /></div><div class="col-md-4"></div></div></div>';
};
