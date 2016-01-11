// This file was automatically generated from form.soy.
// Please don't edit this file by hand.

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.field = function(opt_data, opt_ignored) {
  return '\t' + ((opt_data.field != null) ? soy.$$filterNoAutoescape(opt_data.field) : '');
};


ddm.fields = function(opt_data, opt_ignored) {
  var output = '\t';
  var fieldList10 = opt_data.fields;
  var fieldListLen10 = fieldList10.length;
  for (var fieldIndex10 = 0; fieldIndex10 < fieldListLen10; fieldIndex10++) {
    var fieldData10 = fieldList10[fieldIndex10];
    output += ddm.field(soy.$$augmentMap(opt_data, {field: fieldData10}));
  }
  return output;
};


ddm.form_renderer_js = function(opt_data, opt_ignored) {
  return '\t<link href="/o/dynamic-data-mapping-form-renderer/css/main.css" rel="stylesheet" type="text/css" /><script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); Liferay.component( \'' + soy.$$escapeHtml(opt_data.containerId) + 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeHtml(opt_data.containerId) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluation: ' + soy.$$filterNoAutoescape(opt_data.evaluation) + ',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeHtml(opt_data.portletNamespace) + '\', readOnly: ' + soy.$$escapeHtml(opt_data.readOnly) + ', submitLabel: \'' + soy.$$escapeHtml(opt_data.submitLabel) + '\', templateNamespace: \'' + soy.$$escapeHtml(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render() );});<\/script>';
};


ddm.form_rows = function(opt_data, opt_ignored) {
  var output = '\t';
  var rowList49 = opt_data.rows;
  var rowListLen49 = rowList49.length;
  for (var rowIndex49 = 0; rowIndex49 < rowListLen49; rowIndex49++) {
    var rowData49 = rowList49[rowIndex49];
    output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData49.columns})) + '</div>';
  }
  return output;
};


ddm.form_row_column = function(opt_data, opt_ignored) {
  return '\t<div class="col-md-' + soy.$$escapeHtml(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};


ddm.form_row_columns = function(opt_data, opt_ignored) {
  var output = '\t';
  var columnList64 = opt_data.columns;
  var columnListLen64 = columnList64.length;
  for (var columnIndex64 = 0; columnIndex64 < columnListLen64; columnIndex64++) {
    var columnData64 = columnList64[columnIndex64];
    output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData64}));
  }
  return output;
};


ddm.paginated_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
  if (opt_data.pages.length > 1) {
    output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
    var pageList75 = opt_data.pages;
    var pageListLen75 = pageList75.length;
    for (var pageIndex75 = 0; pageIndex75 < pageListLen75; pageIndex75++) {
      var pageData75 = pageList75[pageIndex75];
      output += '<li ' + ((pageIndex75 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData75.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex75 + 1) + '</div></li>';
    }
    output += '</ul></div>';
  }
  output += '<div class="lfr-ddm-form-pages">';
  var pageList89 = opt_data.pages;
  var pageListLen89 = pageList89.length;
  for (var pageIndex89 = 0; pageIndex89 < pageListLen89; pageIndex89++) {
    var pageData89 = pageList89[pageIndex89];
    output += '<div class="' + ((pageIndex89 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData89.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$filterNoAutoescape(pageData89.title) + '</h3>' : '') + ((pageData89.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$filterNoAutoescape(pageData89.description) + '</h4>' : '') + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData89.rows})) + '</div>';
  }
  output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> ' + soy.$$escapeHtml(opt_data.strings.previous) + '</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">' + soy.$$escapeHtml(opt_data.strings.next) + ' <i class="icon-angle-right"></i></button>' + ((! opt_data.readOnly) ? '<button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" disabled type="submit">' + soy.$$escapeHtml(opt_data.submitLabel) + '</button>' : '') + '</div></div>';
  return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
  var pageList134 = opt_data.pages;
  var pageListLen134 = pageList134.length;
  for (var pageIndex134 = 0; pageIndex134 < pageListLen134; pageIndex134++) {
    var pageData134 = pageList134[pageIndex134];
    output += ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData134.rows}));
  }
  output += '</div></div>';
  return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs nav-tabs-default">';
  var pageList143 = opt_data.pages;
  var pageListLen143 = pageList143.length;
  for (var pageIndex143 = 0; pageIndex143 < pageListLen143; pageIndex143++) {
    var pageData143 = pageList143[pageIndex143];
    output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData143.title) + '</a></li>';
  }
  output += '</ul><div class="tab-content">';
  var pageList149 = opt_data.pages;
  var pageListLen149 = pageList149.length;
  for (var pageIndex149 = 0; pageIndex149 < pageListLen149; pageIndex149++) {
    var pageData149 = pageList149[pageIndex149];
    output += '<div class="tab-pane ' + ((pageIndex149 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData149.rows})) + '</div>';
  }
  output += '</div></div></div>';
  return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
  var pageList164 = opt_data.pages;
  var pageListLen164 = pageList164.length;
  for (var pageIndex164 = 0; pageIndex164 < pageListLen164; pageIndex164++) {
    var pageData164 = pageList164[pageIndex164];
    output += '<div class="lfr-ddm-form-page' + ((pageIndex164 == 0) ? ' active basic' : '') + ((pageIndex164 == pageListLen164 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData164.rows})) + '</div>';
  }
  output += '</div></div>';
  return output;
};
