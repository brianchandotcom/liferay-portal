// This file was automatically generated from form.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @hassoydelcall {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_86478705 = function(opt_data, opt_ignored) {
  return '';
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_86478705.soyTemplateName = 'ddm.__deltemplate_s2_86478705';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), '', 0, ddm.__deltemplate_s2_86478705);


ddm.fields = function(opt_data, opt_ignored) {
  var output = '';
  var fieldList12 = opt_data.fields;
  var fieldListLen12 = fieldList12.length;
  for (var fieldIndex12 = 0; fieldIndex12 < fieldListLen12; fieldIndex12++) {
    var fieldData12 = fieldList12[fieldIndex12];
    var variant__soy4 = fieldData12.type;
    output += '<div class="clearfix ' + ((! fieldData12.visible) ? 'hide' : '') + ' lfr-ddm-form-field-container">' + soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field'), variant__soy4, true)(fieldData12) + '</div>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.fields.soyTemplateName = 'ddm.fields';
}


ddm.form_renderer_js = function(opt_data, opt_ignored) {
  return '<script type="text/javascript">AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', function(A) {Liferay.DDM.Renderer.FieldTypes.register(' + soy.$$filterNoAutoescape(opt_data.fieldTypes) + '); Liferay.component( \'' + soy.$$escapeJsString(opt_data.containerId) + 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#' + soy.$$escapeJsString(opt_data.containerId) + '\', context: ' + soy.$$filterNoAutoescape(opt_data.context) + ', definition: ' + soy.$$filterNoAutoescape(opt_data.definition) + ', evaluableFields: ' + soy.$$escapeJsValue(opt_data.evaluableFields) + ', evaluatorURL: \'' + soy.$$escapeJsString(opt_data.evaluatorURL) + '\', layout: ' + soy.$$filterNoAutoescape(opt_data.layout) + ', portletNamespace: \'' + soy.$$escapeJsString(opt_data.portletNamespace) + '\', readOnly: ' + soy.$$escapeJsValue(opt_data.readOnly) + '}).render() ); var destroyFormHandle = function(event) {var form = Liferay.component(\'' + soy.$$escapeJsString(opt_data.containerId) + 'DDMForm\'); var portlet = event.portlet; if (portlet && portlet.contains(form.get(\'container\'))) {form.destroy(); Liferay.detach(\'destroyPortlet\', destroyFormHandle);}}; Liferay.on(\'destroyPortlet\', destroyFormHandle);});<\/script>';
};
if (goog.DEBUG) {
  ddm.form_renderer_js.soyTemplateName = 'ddm.form_renderer_js';
}


ddm.form_rows = function(opt_data, opt_ignored) {
  var output = '';
  var rowList47 = opt_data.rows;
  var rowListLen47 = rowList47.length;
  for (var rowIndex47 = 0; rowIndex47 < rowListLen47; rowIndex47++) {
    var rowData47 = rowList47[rowIndex47];
    output += '<div class="row">' + ddm.form_row_columns(soy.$$augmentMap(opt_data, {columns: rowData47.columns})) + '</div>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.form_rows.soyTemplateName = 'ddm.form_rows';
}


ddm.form_row_column = function(opt_data, opt_ignored) {
  return '<div class="col-md-' + soy.$$escapeHtmlAttribute(opt_data.column.size) + '">' + ddm.fields(soy.$$augmentMap(opt_data, {fields: opt_data.column.fields})) + '</div>';
};
if (goog.DEBUG) {
  ddm.form_row_column.soyTemplateName = 'ddm.form_row_column';
}


ddm.form_row_columns = function(opt_data, opt_ignored) {
  var output = '';
  var columnList59 = opt_data.columns;
  var columnListLen59 = columnList59.length;
  for (var columnIndex59 = 0; columnIndex59 < columnListLen59; columnIndex59++) {
    var columnData59 = columnList59[columnIndex59];
    output += ddm.form_row_column(soy.$$augmentMap(opt_data, {column: columnData59}));
  }
  return output;
};
if (goog.DEBUG) {
  ddm.form_row_columns.soyTemplateName = 'ddm.form_row_columns';
}


ddm.required_warning_message = function(opt_data, opt_ignored) {
  return '' + ((opt_data.showRequiredFieldsWarning) ? soy.$$filterNoAutoescape(opt_data.requiredFieldsWarningMessageHTML) : '');
};
if (goog.DEBUG) {
  ddm.required_warning_message.soyTemplateName = 'ddm.required_warning_message';
}


ddm.paginated_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-form-content">';
  if (opt_data.pages.length > 1) {
    output += '<div class="lfr-ddm-form-wizard"><ul class="multi-step-progress-bar">';
    var pageList83 = opt_data.pages;
    var pageListLen83 = pageList83.length;
    for (var pageIndex83 = 0; pageIndex83 < pageListLen83; pageIndex83++) {
      var pageData83 = pageList83[pageIndex83];
      output += '<li ' + ((pageIndex83 == 0) ? 'class="active"' : '') + '><div class="progress-bar-title">' + soy.$$filterNoAutoescape(pageData83.title) + '</div><div class="divider"></div><div class="progress-bar-step">' + soy.$$escapeHtml(pageIndex83 + 1) + '</div></li>';
    }
    output += '</ul></div>';
  }
  output += '<div class="lfr-ddm-form-pages">';
  var pageList110 = opt_data.pages;
  var pageListLen110 = pageList110.length;
  for (var pageIndex110 = 0; pageIndex110 < pageListLen110; pageIndex110++) {
    var pageData110 = pageList110[pageIndex110];
    output += '<div class="' + ((pageIndex110 == 0) ? 'active' : '') + ' lfr-ddm-form-page">' + ((pageData110.title) ? '<h3 class="lfr-ddm-form-page-title">' + soy.$$filterNoAutoescape(pageData110.title) + '</h3>' : '') + ((pageData110.description) ? '<h4 class="lfr-ddm-form-page-description">' + soy.$$filterNoAutoescape(pageData110.description) + '</h4>' : '') + ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData110.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData110.rows})) + '</div>';
  }
  output += '</div></div><div class="lfr-ddm-form-pagination-controls"><button class="btn btn-lg btn-primary hide lfr-ddm-form-pagination-prev" type="button"><i class="icon-angle-left"></i> ' + soy.$$escapeHtml(opt_data.strings.previous) + '</button><button class="btn btn-lg btn-primary' + ((opt_data.pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right" type="button">' + soy.$$escapeHtml(opt_data.strings.next) + ' <i class="icon-angle-right"></i></button>' + ((opt_data.showSubmitButton) ? '<button class="btn btn-lg btn-primary' + ((opt_data.pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right" disabled type="submit">' + soy.$$escapeHtml(opt_data.submitLabel) + '</button>' : '') + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.paginated_form.soyTemplateName = 'ddm.paginated_form';
}


ddm.simple_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-form-fields">';
  var pageList140 = opt_data.pages;
  var pageListLen140 = pageList140.length;
  for (var pageIndex140 = 0; pageIndex140 < pageListLen140; pageIndex140++) {
    var pageData140 = pageList140[pageIndex140];
    output += ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData140.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData140.rows}));
  }
  output += '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.simple_form.soyTemplateName = 'ddm.simple_form';
}


ddm.tabbed_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-form-tabs"><ul class="nav navbar-nav">';
  var pageList150 = opt_data.pages;
  var pageListLen150 = pageList150.length;
  for (var pageIndex150 = 0; pageIndex150 < pageListLen150; pageIndex150++) {
    var pageData150 = pageList150[pageIndex150];
    output += '<li><a href="javascript:;">' + soy.$$escapeHtml(pageData150.title) + '</a></li>';
  }
  output += '</ul><div class="tab-content">';
  var pageList164 = opt_data.pages;
  var pageListLen164 = pageList164.length;
  for (var pageIndex164 = 0; pageIndex164 < pageListLen164; pageIndex164++) {
    var pageData164 = pageList164[pageIndex164];
    output += '<div class="lfr-ddm-form-page tab-pane ' + ((pageIndex164 == 0) ? 'active' : '') + '">' + ddm.required_warning_message(soy.$$augmentMap(opt_data, {showRequiredFieldsWarning: pageData164.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: opt_data.requiredFieldsWarningMessageHTML})) + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData164.rows})) + '</div>';
  }
  output += '</div></div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.tabbed_form.soyTemplateName = 'ddm.tabbed_form';
}


ddm.tabbed_form_frame = function(opt_data, opt_ignored) {
  opt_data = opt_data || {};
  return '<div class="lfr-ddm-form-page tab-pane ' + ((opt_data.active) ? 'active' : '') + '"></div>';
};
if (goog.DEBUG) {
  ddm.tabbed_form_frame.soyTemplateName = 'ddm.tabbed_form_frame';
}


ddm.settings_form = function(opt_data, opt_ignored) {
  var output = '<div class="lfr-ddm-form-container" id="' + soy.$$escapeHtmlAttribute(opt_data.containerId) + '"><div class="lfr-ddm-settings-form">';
  var pageList188 = opt_data.pages;
  var pageListLen188 = pageList188.length;
  for (var pageIndex188 = 0; pageIndex188 < pageListLen188; pageIndex188++) {
    var pageData188 = pageList188[pageIndex188];
    output += '<div class="lfr-ddm-form-page' + ((pageIndex188 == 0) ? ' active basic' : '') + ((pageIndex188 == pageListLen188 - 1) ? ' advanced' : '') + '">' + ddm.form_rows(soy.$$augmentMap(opt_data, {rows: pageData188.rows})) + '</div>';
  }
  output += '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.settings_form.soyTemplateName = 'ddm.settings_form';
}
