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
  var columnList34 = opt_data.columns;
  var columnListLen34 = columnList34.length;
  for (var columnIndex34 = 0; columnIndex34 < columnListLen34; columnIndex34++) {
    var columnData34 = columnList34[columnIndex34];
    output += '<th>' + soy.$$escapeHtml(columnData34.label) + '</th>';
  }
  output += '</tr></thead><tbody>';
  var rowList57 = opt_data.rows;
  var rowListLen57 = rowList57.length;
  for (var rowIndex57 = 0; rowIndex57 < rowListLen57; rowIndex57++) {
    var rowData57 = rowList57[rowIndex57];
    output += '<tr><td>' + soy.$$escapeHtml(rowData57.label) + '</td>';
    var columnList54 = opt_data.columns;
    var columnListLen54 = columnList54.length;
    for (var columnIndex54 = 0; columnIndex54 < columnListLen54; columnIndex54++) {
      var columnData54 = columnList54[columnIndex54];
      var checked__soy40 = columnData54.value == opt_data.value[rowData57.value] ? 'checked' : '';
      output += '<td><input ' + soy.$$filterHtmlAttributes(checked__soy40) + ' class="form-builder-grid-field" data-row-index="' + soy.$$escapeHtmlAttribute(rowIndex57) + '" ' + ((opt_data.readOnly) ? 'disabled' : '') + ' name="' + soy.$$escapeHtmlAttribute(rowData57.value) + '" type="radio" value="' + soy.$$escapeHtmlAttribute(columnData54.value) + '" /></td>';
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
  var rowList71 = opt_data.rows;
  var rowListLen71 = rowList71.length;
  for (var rowIndex71 = 0; rowIndex71 < rowListLen71; rowIndex71++) {
    var rowData71 = rowList71[rowIndex71];
    output += '<input class="form-control hide" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden"/>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.hidden_grid.soyTemplateName = 'ddm.hidden_grid';
}
