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
  var output = '<div class="col-md-' + soy.$$escapeHtmlAttribute(opt_data.columnSize) + '"><div class="clearfix ' + ((! opt_data.field.visible) ? 'hide' : '') + ' lfr-ddm-form-field-container">';
  var variant__soy12 = opt_data.field.type;
  output += soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field'), variant__soy12, true)(opt_data.field) + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.fieldset_column.soyTemplateName = 'ddm.fieldset_column';
}


ddm.fieldset_columns = function(opt_data, opt_ignored) {
  var output = '';
  var fieldList19 = opt_data.fields;
  var fieldListLen19 = fieldList19.length;
  for (var fieldIndex19 = 0; fieldIndex19 < fieldListLen19; fieldIndex19++) {
    var fieldData19 = fieldList19[fieldIndex19];
    output += ddm.fieldset_column(soy.$$augmentMap(opt_data, {columnSize: opt_data.columnSize, field: fieldData19}));
  }
  return output;
};
if (goog.DEBUG) {
  ddm.fieldset_columns.soyTemplateName = 'ddm.fieldset_columns';
}


ddm.fieldset = function(opt_data, opt_ignored) {
  return '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + ' liferay-ddm-form-field-fieldset' + soy.$$escapeHtmlAttribute(opt_data.showBorderTop ? ' border-top' : '') + ' ' + soy.$$escapeHtmlAttribute(opt_data.showBorderBottom ? ' border-bottom' : '') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '') + '<fieldset>' + ((opt_data.showLabel) ? '<legend>' + soy.$$escapeHtml(opt_data.label) + '</legend>' : '') + ddm.fieldset_columns(soy.$$augmentMap(opt_data, {columnSize: opt_data.columnSize, fields: opt_data.nestedFields})) + '</fieldset></div>';
};
if (goog.DEBUG) {
  ddm.fieldset.soyTemplateName = 'ddm.fieldset';
}
