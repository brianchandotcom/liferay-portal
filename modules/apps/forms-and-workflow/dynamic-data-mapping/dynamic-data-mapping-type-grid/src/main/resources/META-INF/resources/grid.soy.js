// This file was automatically generated from grid.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_b69d8aa9 = function(opt_data, opt_ignored) {
  return '' + ddm.grid(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_b69d8aa9.soyTemplateName = 'ddm.__deltemplate_s2_b69d8aa9';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'grid', 0, ddm.__deltemplate_s2_b69d8aa9);


ddm.grid = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.showLabel) ? '<label class="control-label">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') : '') + '<div class="liferay-ddm-form-field-grid table-responsive">' + ((! opt_data.readOnly) ? ddm.hidden_grid(opt_data) : '') + '<table class="table table-autofit table-list table-striped"><thead><tr><th></th>';
  var columnList35 = opt_data.columns;
  var columnListLen35 = columnList35.length;
  for (var columnIndex35 = 0; columnIndex35 < columnListLen35; columnIndex35++) {
    var columnData35 = columnList35[columnIndex35];
    output += '<th>' + soy.$$escapeHtml(columnData35.label) + '</th>';
  }
  output += '</tr></thead><tbody>';
  var rowList63 = opt_data.rows;
  var rowListLen63 = rowList63.length;
  for (var rowIndex63 = 0; rowIndex63 < rowListLen63; rowIndex63++) {
    var rowData63 = rowList63[rowIndex63];
    output += '<tr name="' + soy.$$escapeHtmlAttribute(rowData63.value) + '"><td>' + soy.$$escapeHtml(rowData63.label) + '</td>';
    var columnList60 = opt_data.columns;
    var columnListLen60 = columnList60.length;
    for (var columnIndex60 = 0; columnIndex60 < columnListLen60; columnIndex60++) {
      var columnData60 = columnList60[columnIndex60];
      var checked__soy43 = columnData60.value == opt_data.value[rowData63.value] ? 'checked' : '';
      var autoFocus__soy44 = opt_data.focusTarget && (opt_data.focusTarget.row == rowData63.value && opt_data.focusTarget.index == columnIndex60) ? 'autofocus' : '';
      output += '<td><input ' + soy.$$filterHtmlAttributes(autoFocus__soy44) + ' ' + soy.$$filterHtmlAttributes(checked__soy43) + ' class="form-builder-grid-field" data-row-index="' + soy.$$escapeHtmlAttribute(columnIndex60) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' name="' + soy.$$escapeHtmlAttribute(rowData63.value) + '" type="radio" value="' + soy.$$escapeHtmlAttribute(columnData60.value) + '" /></td>';
    }
    output += '</tr>';
  }
  output += '</tbody></table></div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.grid.soyTemplateName = 'ddm.grid';
}


ddm.hidden_grid = function(opt_data, opt_ignored) {
  var output = '';
  var rowList84 = opt_data.rows;
  var rowListLen84 = rowList84.length;
  for (var rowIndex84 = 0; rowIndex84 < rowListLen84; rowIndex84++) {
    var rowData84 = rowList84[rowIndex84];
    var inputValue__soy72 = opt_data.value[rowData84.value] ? rowData84.value + ';' + opt_data.value[rowData84.value] : '';
    output += '<input class="form-control" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden" ' + ((inputValue__soy72) ? 'value="' + soy.$$escapeHtmlAttribute(inputValue__soy72) + '"' : '') + '/>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.hidden_grid.soyTemplateName = 'ddm.hidden_grid';
}
