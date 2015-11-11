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
return '\t<link href="/o/dynamic-data-mapping-form-renderer/css/main.css" rel="stylesheet" type="text/css" /><script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeHtml(opt_data.containerId) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluation: ' + soy.$$filterNoAutoescape(opt_data.evaluation) + ',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeHtml(opt_data.portletNamespace) + '\', readOnly: ' + soy.$$escapeHtml(opt_data.readOnly) + ', templateNamespace: \'' + soy.$$escapeHtml(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render();});<\/script>';
};


ddm.form_rows = function(opt_data, opt_ignored) {
var output = '\t';
var rowList45 = opt_data.rows;
var rowListLen45 = rowList45.length;
for (var rowIndex45 = 0; rowIndex45 < rowListLen45; rowIndex45++) {
	var rowData45 = rowList45[rowIndex45];
	output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData45.columns})) + '</div>';
}
return output;
};


ddm.form_row_column = function(opt_data, opt_ignored) {
return '\t<div class="col-md-' + soy.$$escapeHtml(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};


ddm.form_row_columns = function(opt_data, opt_ignored) {
var output = '\t';
var columnList60 = opt_data.columns;
var columnListLen60 = columnList60.length;
for (var columnIndex60 = 0; columnIndex60 < columnListLen60; columnIndex60++) {
	var columnData60 = columnList60[columnIndex60];
	output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData60}));
}
return output;
};


ddm.paginated_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
if (opt_data.pages.length > 1) {
	output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
	var pageList71 = opt_data.pages;
	var pageListLen71 = pageList71.length;
	for (var pageIndex71 = 0; pageIndex71 < pageListLen71; pageIndex71++) {
	  var pageData71 = pageList71[pageIndex71];
	  output += '<li ' + ((pageIndex71 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData71.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex71 + 1) + '</div></li>';
	}
	output += '</ul></div>';
}
output += '<div class="lfr-ddm-form-pages">';
var pageList85 = opt_data.pages;
var pageListLen85 = pageList85.length;
for (var pageIndex85 = 0; pageIndex85 < pageListLen85; pageIndex85++) {
	var pageData85 = pageList85[pageIndex85];
	output += '<div class="' + ((pageIndex85 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData85.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$filterNoAutoescape(pageData85.title) + '</h3>' : '') + ((pageData85.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$filterNoAutoescape(pageData85.description) + '</h4>' : '') + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData85.rows})) + '</div>';
}
output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> Prev</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">Next <i class="icon-angle-right"></i></button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1 || opt_data.readOnly) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" type="submit">Submit</button></div></div>';
return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
var pageList120 = opt_data.pages;
var pageListLen120 = pageList120.length;
for (var pageIndex120 = 0; pageIndex120 < pageListLen120; pageIndex120++) {
	var pageData120 = pageList120[pageIndex120];
	output += ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData120.rows}));
}
output += '</div></div>';
return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs">';
var pageList129 = opt_data.pages;
var pageListLen129 = pageList129.length;
for (var pageIndex129 = 0; pageIndex129 < pageListLen129; pageIndex129++) {
	var pageData129 = pageList129[pageIndex129];
	output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData129.title) + '</a></li>';
}
output += '</ul><div class="tab-content">';
var pageList135 = opt_data.pages;
var pageListLen135 = pageList135.length;
for (var pageIndex135 = 0; pageIndex135 < pageListLen135; pageIndex135++) {
	var pageData135 = pageList135[pageIndex135];
	output += '<div class="tab-pane ' + ((pageIndex135 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData135.rows})) + '</div>';
}
output += '</div></div></div>';
return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
var pageList150 = opt_data.pages;
var pageListLen150 = pageList150.length;
for (var pageIndex150 = 0; pageIndex150 < pageListLen150; pageIndex150++) {
	var pageData150 = pageList150[pageIndex150];
	output += '<div class="lfr-ddm-form-page' + ((pageIndex150 == 0) ? ' active basic' : '') + ((pageIndex150 == pageListLen150 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData150.rows})) + '</div>';
}
output += '</div></div>';
return output;
};