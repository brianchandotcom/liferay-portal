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
  return '\t<link href="/o/dynamic-data-mapping-form-renderer/css/main.css" rel="stylesheet" type="text/css" /><script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); Liferay.component( \'' + soy.$$escapeHtml(opt_data.containerId) + 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeHtml(opt_data.containerId) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluation: ' + soy.$$filterNoAutoescape(opt_data.evaluation) + ',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeHtml(opt_data.portletNamespace) + '\', readOnly: ' + soy.$$escapeHtml(opt_data.readOnly) + ', readOnlyFields : ' + soy.$$filterNoAutoescape(opt_data.readOnlyFields) + ', showRequiredFieldsWarning: ' + soy.$$escapeHtml(opt_data.showRequiredFieldsWarning) + ', showSubmitButton: ' + soy.$$escapeHtml(opt_data.showSubmitButton) + ', submitLabel: \'' + soy.$$escapeHtml(opt_data.submitLabel) + '\', templateNamespace: \'' + soy.$$escapeHtml(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render() );});<\/script>';
};


ddm.form_rows = function(opt_data, opt_ignored) {
  var output = '\t';
  var rowList56 = opt_data.rows;
  var rowListLen56 = rowList56.length;
  for (var rowIndex56 = 0; rowIndex56 < rowListLen56; rowIndex56++) {
    var rowData56 = rowList56[rowIndex56];
    output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData56.columns})) + '</div>';
  }
  return output;
};


ddm.form_row_column = function(opt_data, opt_ignored) {
  return '\t<div class="col-md-' + soy.$$escapeHtml(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};


ddm.form_row_columns = function(opt_data, opt_ignored) {
  var output = '\t';
  var columnList71 = opt_data.columns;
  var columnListLen71 = columnList71.length;
  for (var columnIndex71 = 0; columnIndex71 < columnListLen71; columnIndex71++) {
    var columnData71 = columnList71[columnIndex71];
    output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData71}));
  }
  return output;
};


ddm.required_warning_message = function(opt_data, opt_ignored) {
  return '\t' + ((opt_data.showRequiredFieldsWarning) ? soy.$$filterNoAutoescape(opt_data.requiredFieldsWarningMessageHTML) : '');
};


ddm.paginated_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
  if (opt_data.pages.length > 1) {
    output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
    var pageList88 = opt_data.pages;
    var pageListLen88 = pageList88.length;
    for (var pageIndex88 = 0; pageIndex88 < pageListLen88; pageIndex88++) {
      var pageData88 = pageList88[pageIndex88];
      output += '<li ' + ((pageIndex88 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData88.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex88 + 1) + '</div></li>';
    }
    output += '</ul></div>';
  }
  output += '<div class="lfr-ddm-form-pages">';
  var pageList102 = opt_data.pages;
  var pageListLen102 = pageList102.length;
  for (var pageIndex102 = 0; pageIndex102 < pageListLen102; pageIndex102++) {
    var pageData102 = pageList102[pageIndex102];
    output += '<div class="' + ((pageIndex102 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData102.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$filterNoAutoescape(pageData102.title) + '</h3>' : '') + ((pageData102.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$filterNoAutoescape(pageData102.description) + '</h4>' : '') + ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData102.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData102.rows})) + '</div>';
  }
  output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> ' + soy.$$escapeHtml(opt_data.strings.previous) + '</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">' + soy.$$escapeHtml(opt_data.strings.next) + ' <i class="icon-angle-right"></i></button>' + ((opt_data.showSubmitButton) ? '<button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" disabled type="submit">' + soy.$$escapeHtml(opt_data.submitLabel) + '</button>' : '') + '</div></div>';
  return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
  var pageList150 = opt_data.pages;
  var pageListLen150 = pageList150.length;
  for (var pageIndex150 = 0; pageIndex150 < pageListLen150; pageIndex150++) {
    var pageData150 = pageList150[pageIndex150];
    output += ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData150.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData150.rows}));
  }
  output += '</div></div>';
  return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs nav-tabs-default">';
  var pageList162 = opt_data.pages;
  var pageListLen162 = pageList162.length;
  for (var pageIndex162 = 0; pageIndex162 < pageListLen162; pageIndex162++) {
    var pageData162 = pageList162[pageIndex162];
    output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData162.title) + '</a></li>';
  }
  output += '</ul><div class="tab-content">';
  var pageList168 = opt_data.pages;
  var pageListLen168 = pageList168.length;
  for (var pageIndex168 = 0; pageIndex168 < pageListLen168; pageIndex168++) {
    var pageData168 = pageList168[pageIndex168];
    output += ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData168.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + '<div class="tab-pane ' + ((pageIndex168 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData168.rows})) + '</div>';
  }
  output += '</div></div></div>';
  return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
  var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
  var pageList186 = opt_data.pages;
  var pageListLen186 = pageList186.length;
  for (var pageIndex186 = 0; pageIndex186 < pageListLen186; pageIndex186++) {
    var pageData186 = pageList186[pageIndex186];
    output += '<div class="lfr-ddm-form-page' + ((pageIndex186 == 0) ? ' active basic' : '') + ((pageIndex186 == pageListLen186 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData186.rows})) + '</div>';
  }
  output += '</div></div>';
  return output;
};
