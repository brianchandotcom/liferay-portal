// This file was automatically generated from form.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.field = function(opt_data, opt_ignored) {
  return '\t' + ((opt_data.field != null) ? '<div class="lfr-ddm-form-field-container">' + soy.$$filterNoAutoescape(opt_data.field) + '</div>' : '');
};


ddm.fields = function(opt_data, opt_ignored) {
  var output = '\t';
  var fieldList12 = opt_data.fields;
  var fieldListLen12 = fieldList12.length;
  for (var fieldIndex12 = 0; fieldIndex12 < fieldListLen12; fieldIndex12++) {
    var fieldData12 = fieldList12[fieldIndex12];
    output += ddm.field(soy.$$augmentMap(opt_data, {field: fieldData12}));
  }
  return output;
};


ddm.form_renderer_js = function(opt_data, opt_ignored) {
  return '\t<script src="/o/ddm-form-renderer/js/modules.js"><\/script><script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeHtml(opt_data.containerId) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', portletNamespace: \'' + soy.$$escapeHtml(opt_data.portletNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render();});<\/script>';
};


ddm.form_rows = function(opt_data, opt_ignored) {
  var output = '\t';
  var rowList33 = opt_data.rows;
  var rowListLen33 = rowList33.length;
  for (var rowIndex33 = 0; rowIndex33 < rowListLen33; rowIndex33++) {
    var rowData33 = rowList33[rowIndex33];
    output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData33.columns})) + '</div>';
  }
  return output;
};


ddm.form_row_column = function(opt_data, opt_ignored) {
  return '\t<div class="col-md-' + soy.$$escapeHtml(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};


ddm.form_row_columns = function(opt_data, opt_ignored) {
  var output = '\t';
  var columnList48 = opt_data.columns;
  var columnListLen48 = columnList48.length;
  for (var columnIndex48 = 0; columnIndex48 < columnListLen48; columnIndex48++) {
    var columnData48 = columnList48[columnIndex48];
    output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData48}));
  }
  return output;
};


ddm.paginated_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
  var pageList54 = opt_data.pages;
  var pageListLen54 = pageList54.length;
  for (var pageIndex54 = 0; pageIndex54 < pageListLen54; pageIndex54++) {
    var pageData54 = pageList54[pageIndex54];
    output += '<li ' + ((pageIndex54 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData54.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex54 + 1) + '</div></li>';
  }
  output += '</ul></div><div class="lfr-ddm-form-pages">';
  var pageList67 = opt_data.pages;
  var pageListLen67 = pageList67.length;
  for (var pageIndex67 = 0; pageIndex67 < pageListLen67; pageIndex67++) {
    var pageData67 = pageList67[pageIndex67];
    output += '<div class="' + ((pageIndex67 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData67.title != null) ? '<h3>' + soy.$$filterNoAutoescape(pageData67.title) + '</h3>' : '') + ((pageData67.description != null) ? '<h4>' + soy.$$filterNoAutoescape(pageData67.description) + '</h4>' : '') + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData67.rows})) + '</div>';
  }
  output += '</div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-primary hide lfr-ddm-form-pagination-prev" type="button">Prev</button><button class="btn btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">Next</button><button class="btn btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" type="submit">Submit</button></div>';
  return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-fields">';
  var fieldList100 = opt_data.fields;
  var fieldListLen100 = fieldList100.length;
  for (var fieldIndex100 = 0; fieldIndex100 < fieldListLen100; fieldIndex100++) {
    var fieldData100 = fieldList100[fieldIndex100];
    output += ddm.field(soy.$$augmentMap(opt_data, {field: fieldData100}));
  }
  output += '</div>';
  return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs">';
  var pageList107 = opt_data.pages;
  var pageListLen107 = pageList107.length;
  for (var pageIndex107 = 0; pageIndex107 < pageListLen107; pageIndex107++) {
    var pageData107 = pageList107[pageIndex107];
    output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData107.title) + '</a></li>';
  }
  output += '</ul><div class="tab-content">';
  var pageList113 = opt_data.pages;
  var pageListLen113 = pageList113.length;
  for (var pageIndex113 = 0; pageIndex113 < pageListLen113; pageIndex113++) {
    var pageData113 = pageList113[pageIndex113];
    output += '<div class="tab-pane ' + ((pageIndex113 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData113.rows})) + '</div>';
  }
  output += '</div></div>';
  return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-settings-form">';
  var pageList126 = opt_data.pages;
  var pageListLen126 = pageList126.length;
  for (var pageIndex126 = 0; pageIndex126 < pageListLen126; pageIndex126++) {
    var pageData126 = pageList126[pageIndex126];
    output += '<div class="lfr-ddm-form-page' + ((pageIndex126 == 0) ? ' active basic' : '') + ((pageIndex126 == pageListLen126 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData126.rows})) + '</div>';
  }
  output += '</div>';
  return output;
};
