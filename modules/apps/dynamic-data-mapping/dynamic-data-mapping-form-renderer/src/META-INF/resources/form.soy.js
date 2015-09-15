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
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
var pageList63 = opt_data.pages;
var pageListLen63 = pageList63.length;
for (var pageIndex63 = 0; pageIndex63 < pageListLen63; pageIndex63++) {
	var pageData63 = pageList63[pageIndex63];
	output += '<li ' + ((pageIndex63 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData63.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex63 + 1) + '</div></li>';
}
output += '</ul></div><div class="lfr-ddm-form-pages">';
var pageList76 = opt_data.pages;
var pageListLen76 = pageList76.length;
for (var pageIndex76 = 0; pageIndex76 < pageListLen76; pageIndex76++) {
	var pageData76 = pageList76[pageIndex76];
	output += '<div class="' + ((pageIndex76 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData76.title != null) ? '<h3>' + soy.$$filterNoAutoescape(pageData76.title) + '</h3>' : '') + ((pageData76.description != null) ? '<h4>' + soy.$$filterNoAutoescape(pageData76.description) + '</h4>' : '') + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData76.rows})) + '</div>';
}
output += '</div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-primary hide lfr-ddm-form-pagination-prev" type="button">Prev</button><button class="btn btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">Next</button><button class="btn btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" type="submit">Submit</button></div></div>';
return output;
};


ddm.simple_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
var pageList111 = opt_data.pages;
var pageListLen111 = pageList111.length;
for (var pageIndex111 = 0; pageIndex111 < pageListLen111; pageIndex111++) {
	var pageData111 = pageList111[pageIndex111];
	output += ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData111.rows}));
}
output += '</div></div>';
return output;
};


ddm.tabbed_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav nav-tabs">';
var pageList120 = opt_data.pages;
var pageListLen120 = pageList120.length;
for (var pageIndex120 = 0; pageIndex120 < pageListLen120; pageIndex120++) {
	var pageData120 = pageList120[pageIndex120];
	output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData120.title) + '</a></li>';
}
output += '</ul><div class="tab-content">';
var pageList126 = opt_data.pages;
var pageListLen126 = pageList126.length;
for (var pageIndex126 = 0; pageIndex126 < pageListLen126; pageIndex126++) {
	var pageData126 = pageList126[pageIndex126];
	output += '<div class="tab-pane ' + ((pageIndex126 == 0) ? 'active' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData126.rows})) + '</div>';
}
output += '</div></div></div>';
return output;
};


ddm.settings_form = function(opt_data, opt_ignored) {
var output = '\t<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtml(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
var pageList141 = opt_data.pages;
var pageListLen141 = pageList141.length;
for (var pageIndex141 = 0; pageIndex141 < pageListLen141; pageIndex141++) {
	var pageData141 = pageList141[pageIndex141];
	output += '<div class="lfr-ddm-form-page' + ((pageIndex141 == 0) ? ' active basic' : '') + ((pageIndex141 == pageListLen141 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData141.rows})) + '</div>';
}
output += '</div></div>';
return output;
};