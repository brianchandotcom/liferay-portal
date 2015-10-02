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
  return '\t<link href="/o/ddm-form-renderer/css/main.css" rel="stylesheet" type="text/css" /><script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeHtml(opt_data.containerId) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluation: ' + soy.$$filterNoAutoescape(opt_data.evaluation) + ',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeHtml(opt_data.portletNamespace) + '\', templateNamespace: \'' + soy.$$escapeHtml(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render();});<\/script>';
};


ddm.form_rows = function(opt_data, opt_ignored) {
  var output = '\t';
  var rowList43 = opt_data.rows;
  var rowListLen43 = rowList43.length;
  for (var rowIndex43 = 0; rowIndex43 < rowListLen43; rowIndex43++) {
    var rowData43 = rowList43[rowIndex43];
    output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData43.columns})) + '</div>';
  }
  return output;
};


ddm.form_row_column = function(opt_data, opt_ignored) {
  return '\t<div class="col-md-' + soy.$$escapeHtml(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};


ddm.form_row_columns = function(opt_data, opt_ignored) {
  var output = '\t';
  var columnList58 = opt_data.columns;
  var columnListLen58 = columnList58.length;
  for (var columnIndex58 = 0; columnIndex58 < columnListLen58; columnIndex58++) {
    var columnData58 = columnList58[columnIndex58];
    output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData58}));
  }
  return output;
};


ddm.paginated_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
  if (opt_data.pages.length > 1) {
    output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
    var pageList69 = opt_data.pages;
    var pageListLen69 = pageList69.length;
    for (var pageIndex69 = 0; pageIndex69 < pageListLen69; pageIndex69++) {
      var pageData69 = pageList69[pageIndex69];
      output += '<li ' + ((pageIndex69 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData69.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex69 + 1) + '</div></li>';
    }
    output += '</ul></div>';
  }
  output += '<div class="lfr-ddm-form-pages">';
  var pageList83 = opt_data.pages;
  var pageListLen83 = pageList83.length;
  for (var pageIndex83 = 0; pageIndex83 < pageListLen83; pageIndex83++) {
    var pageData83 = pageList83[pageIndex83];
    output += '<div class="' + ((pageIndex83 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData83.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$filterNoAutoescape(pageData83.title) + '</h3>' : '') + ((pageData83.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$filterNoAutoescape(pageData83.description) + '</h4>' : '') + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData83.rows})) + '</div>';
  }
  output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> Prev</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">Next <i class="icon-angle-right"></i></button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" type="submit">Submit</button></div></div>';
  return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
  var pageList118 = opt_data.pages;
  var pageListLen118 = pageList118.length;
  for (var pageIndex118 = 0; pageIndex118 < pageListLen118; pageIndex118++) {
    var pageData118 = pageList118[pageIndex118];
    output += ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData118.rows}));
  }
  output += '</div></div>';
  return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs">';
  var pageList127 = opt_data.pages;
  var pageListLen127 = pageList127.length;
  for (var pageIndex127 = 0; pageIndex127 < pageListLen127; pageIndex127++) {
    var pageData127 = pageList127[pageIndex127];
    output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData127.title) + '</a></li>';
  }
  output += '</ul><div class="tab-content">';
  var pageList133 = opt_data.pages;
  var pageListLen133 = pageList133.length;
  for (var pageIndex133 = 0; pageIndex133 < pageListLen133; pageIndex133++) {
    var pageData133 = pageList133[pageIndex133];
    output += '<div class="tab-pane ' + ((pageIndex133 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData133.rows})) + '</div>';
  }
  output += '</div></div></div>';
  return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
  var pageList148 = opt_data.pages;
  var pageListLen148 = pageList148.length;
  for (var pageIndex148 = 0; pageIndex148 < pageListLen148; pageIndex148++) {
    var pageData148 = pageList148[pageIndex148];
    output += '<div class="lfr-ddm-form-page' + ((pageIndex148 == 0) ? ' active basic' : '') + ((pageIndex148 == pageListLen148 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData148.rows})) + '</div>';
  }
  output += '</div></div>';
  return output;
};
