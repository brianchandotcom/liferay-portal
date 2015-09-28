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
return '\t<link href="/o/ddm-form-renderer/css/main.css" rel="stylesheet" type="text/css" /><script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeHtml(opt_data.containerId) + '\', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ',' + ((opt_data.layout) ? 'layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ',' : '') + 'portletNamespace: \'' + soy.$$escapeHtml(opt_data.portletNamespace) + '\', templateNamespace: \'' + soy.$$escapeHtml(opt_data.templateNamespace) + '\', values: ' + soy.$$filterNoAutoescape(opt_data.values) + '}).render();});<\/script>';
};


ddm.form_rows = function(opt_data, opt_ignored) {
var output = '\t';
var rowList40 = opt_data.rows;
var rowListLen40 = rowList40.length;
for (var rowIndex40 = 0; rowIndex40 < rowListLen40; rowIndex40++) {
	var rowData40 = rowList40[rowIndex40];
	output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData40.columns})) + '</div>';
}
return output;
};


ddm.form_row_column = function(opt_data, opt_ignored) {
return '\t<div class="col-md-' + soy.$$escapeHtml(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};


ddm.form_row_columns = function(opt_data, opt_ignored) {
var output = '\t';
var columnList55 = opt_data.columns;
var columnListLen55 = columnList55.length;
for (var columnIndex55 = 0; columnIndex55 < columnListLen55; columnIndex55++) {
	var columnData55 = columnList55[columnIndex55];
	output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData55}));
}
return output;
};


ddm.paginated_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
if (opt_data.pages.length > 1) {
	output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
	var pageList66 = opt_data.pages;
	var pageListLen66 = pageList66.length;
	for (var pageIndex66 = 0; pageIndex66 < pageListLen66; pageIndex66++) {
	  var pageData66 = pageList66[pageIndex66];
	  output += '<li ' + ((pageIndex66 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData66.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex66 + 1) + '</div></li>';
	}
	output += '</ul></div>';
}
output += '<div class="lfr-ddm-form-pages">';
var pageList80 = opt_data.pages;
var pageListLen80 = pageList80.length;
for (var pageIndex80 = 0; pageIndex80 < pageListLen80; pageIndex80++) {
	var pageData80 = pageList80[pageIndex80];
	output += '<div class="' + ((pageIndex80 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData80.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$filterNoAutoescape(pageData80.title) + '</h3>' : '') + ((pageData80.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$filterNoAutoescape(pageData80.description) + '</h4>' : '') + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData80.rows})) + '</div>';
}
output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> Prev</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">Next <i class="icon-angle-right"></i></button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" type="submit">Submit</button></div></div>';
return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
var pageList115 = opt_data.pages;
var pageListLen115 = pageList115.length;
for (var pageIndex115 = 0; pageIndex115 < pageListLen115; pageIndex115++) {
	var pageData115 = pageList115[pageIndex115];
	output += ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData115.rows}));
}
output += '</div></div>';
return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs">';
var pageList124 = opt_data.pages;
var pageListLen124 = pageList124.length;
for (var pageIndex124 = 0; pageIndex124 < pageListLen124; pageIndex124++) {
	var pageData124 = pageList124[pageIndex124];
	output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData124.title) + '</a></li>';
}
output += '</ul><div class="tab-content">';
var pageList130 = opt_data.pages;
var pageListLen130 = pageList130.length;
for (var pageIndex130 = 0; pageIndex130 < pageListLen130; pageIndex130++) {
	var pageData130 = pageList130[pageIndex130];
	output += '<div class="tab-pane ' + ((pageIndex130 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData130.rows})) + '</div>';
}
output += '</div></div></div>';
return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
var pageList145 = opt_data.pages;
var pageListLen145 = pageList145.length;
for (var pageIndex145 = 0; pageIndex145 < pageListLen145; pageIndex145++) {
	var pageData145 = pageList145[pageIndex145];
	output += '<div class="lfr-ddm-form-page' + ((pageIndex145 == 0) ? ' active basic' : '') + ((pageIndex145 == pageListLen145 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData145.rows})) + '</div>';
}
output += '</div></div>';
return output;
};