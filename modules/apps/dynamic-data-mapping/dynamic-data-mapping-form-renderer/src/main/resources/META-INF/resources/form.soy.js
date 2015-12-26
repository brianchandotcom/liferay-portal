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
  return '\t<link href="/o/dynamic-data-mapping-form-renderer/css/main.css" rel="stylesheet" type="text/css" /><script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); Liferay.component( \'' + soy.$$escapeHtml(opt_data.containerId) + 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeHtml(opt_data.containerId) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluation: ' + soy.$$filterNoAutoescape(opt_data.evaluation) + ',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeHtml(opt_data.portletNamespace) + '\', readOnly: ' + soy.$$escapeHtml(opt_data.readOnly) + ', templateNamespace: \'' + soy.$$escapeHtml(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render() );});<\/script>';
};


ddm.form_rows = function(opt_data, opt_ignored) {
  var output = '\t';
  var rowList47 = opt_data.rows;
  var rowListLen47 = rowList47.length;
  for (var rowIndex47 = 0; rowIndex47 < rowListLen47; rowIndex47++) {
    var rowData47 = rowList47[rowIndex47];
    output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData47.columns})) + '</div>';
  }
  return output;
};


ddm.form_row_column = function(opt_data, opt_ignored) {
  return '\t<div class="col-md-' + soy.$$escapeHtml(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};


ddm.form_row_columns = function(opt_data, opt_ignored) {
  var output = '\t';
  var columnList62 = opt_data.columns;
  var columnListLen62 = columnList62.length;
  for (var columnIndex62 = 0; columnIndex62 < columnListLen62; columnIndex62++) {
    var columnData62 = columnList62[columnIndex62];
    output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData62}));
  }
  return output;
};


ddm.paginated_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
  if (opt_data.pages.length > 1) {
    output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
    var pageList73 = opt_data.pages;
    var pageListLen73 = pageList73.length;
    for (var pageIndex73 = 0; pageIndex73 < pageListLen73; pageIndex73++) {
      var pageData73 = pageList73[pageIndex73];
      output += '<li ' + ((pageIndex73 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData73.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex73 + 1) + '</div></li>';
    }
    output += '</ul></div>';
  }
  output += '<div class="lfr-ddm-form-pages">';
  var pageList87 = opt_data.pages;
  var pageListLen87 = pageList87.length;
  for (var pageIndex87 = 0; pageIndex87 < pageListLen87; pageIndex87++) {
    var pageData87 = pageList87[pageIndex87];
    output += '<div class="' + ((pageIndex87 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData87.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$filterNoAutoescape(pageData87.title) + '</h3>' : '') + ((pageData87.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$filterNoAutoescape(pageData87.description) + '</h4>' : '') + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData87.rows})) + '</div>';
  }
  output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> ' + soy.$$escapeHtml(opt_data.strings.previous) + '</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">' + soy.$$escapeHtml(opt_data.strings.next) + ' <i class="icon-angle-right"></i></button>' + ((! opt_data.readOnly) ? '<button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" type="submit">' + soy.$$escapeHtml(opt_data.strings.submit) + '</button>' : '') + '</div></div>';
  return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
  var pageList132 = opt_data.pages;
  var pageListLen132 = pageList132.length;
  for (var pageIndex132 = 0; pageIndex132 < pageListLen132; pageIndex132++) {
    var pageData132 = pageList132[pageIndex132];
    output += ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData132.rows}));
  }
  output += '</div></div>';
  return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs nav-tabs-default">';
  var pageList141 = opt_data.pages;
  var pageListLen141 = pageList141.length;
  for (var pageIndex141 = 0; pageIndex141 < pageListLen141; pageIndex141++) {
    var pageData141 = pageList141[pageIndex141];
    output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData141.title) + '</a></li>';
  }
  output += '</ul><div class="tab-content">';
  var pageList147 = opt_data.pages;
  var pageListLen147 = pageList147.length;
  for (var pageIndex147 = 0; pageIndex147 < pageListLen147; pageIndex147++) {
    var pageData147 = pageList147[pageIndex147];
    output += '<div class="tab-pane ' + ((pageIndex147 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData147.rows})) + '</div>';
  }
  output += '</div></div></div>';
  return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
  var pageList162 = opt_data.pages;
  var pageListLen162 = pageList162.length;
  for (var pageIndex162 = 0; pageIndex162 < pageListLen162; pageIndex162++) {
    var pageData162 = pageList162[pageIndex162];
    output += '<div class="lfr-ddm-form-page' + ((pageIndex162 == 0) ? ' active basic' : '') + ((pageIndex162 == pageListLen162 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData162.rows})) + '</div>';
  }
  output += '</div></div>';
  return output;
};
