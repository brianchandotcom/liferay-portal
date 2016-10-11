// This file was automatically generated from fieldset.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @hassoydelcall {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_95ba1b96 = function(opt_data, opt_ignored) {
  return '' + ddm.fieldset(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_95ba1b96.soyTemplateName = 'ddm.__deltemplate_s2_95ba1b96';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'fieldset', 0, ddm.__deltemplate_s2_95ba1b96);


ddm.fieldset_column = function(opt_data, opt_ignored) {
  var output = '<div class="col-md-' + soy.$$escapeHtmlAttribute(opt_data.columnSize) + '">';
  var variant__soy8 = opt_data.field.type;
  output += soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field'), variant__soy8, true)(opt_data.field) + '</div>';
  return output;
};
if (goog.DEBUG) {
  ddm.fieldset_column.soyTemplateName = 'ddm.fieldset_column';
}


ddm.fieldset_columns = function(opt_data, opt_ignored) {
  var output = '';
  var fieldList15 = opt_data.fields;
  var fieldListLen15 = fieldList15.length;
  for (var fieldIndex15 = 0; fieldIndex15 < fieldListLen15; fieldIndex15++) {
    var fieldData15 = fieldList15[fieldIndex15];
    output += ddm.fieldset_column(soy.$$augmentMap(opt_data, {columnSize: opt_data.columnSize, field: fieldData15}));
  }
  return output;
};
if (goog.DEBUG) {
  ddm.fieldset_columns.soyTemplateName = 'ddm.fieldset_columns';
}


ddm.fieldset = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-fieldset" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') + '<fieldset><legend>' + soy.$$escapeHtml(opt_data.label) + '</legend>' + ddm.fieldset_columns(opt_data) + '</fieldset></div>';
};
if (goog.DEBUG) {
  ddm.fieldset.soyTemplateName = 'ddm.fieldset';
}
