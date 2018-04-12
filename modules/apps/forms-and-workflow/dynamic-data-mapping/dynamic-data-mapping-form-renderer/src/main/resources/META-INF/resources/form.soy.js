/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from form.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field.idom}
 * @hassoydelcall {ddm.field.idom}
 * @public
 */

goog.module('ddm.incrementaldom');

goog.require('goog.asserts');
goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2_86478705(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
}
exports.__deltemplate_s2_86478705 = __deltemplate_s2_86478705;
if (goog.DEBUG) {
  __deltemplate_s2_86478705.soyTemplateName = 'ddm.__deltemplate_s2_86478705';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), '', 0, __deltemplate_s2_86478705);


/**
 * @param {{
 *  fields: !Array<?>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $fields(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<?>} */
  var fields = soy.asserts.assertType(goog.isArray(opt_data.fields), 'fields', opt_data.fields, '!Array<?>');
  var field16List = fields;
  var field16ListLen = field16List.length;
  for (var field16Index = 0; field16Index < field16ListLen; field16Index++) {
      var field16Data = field16List[field16Index];
      var variant__soy8 = field16Data.type;
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'clearfix ' + (!field16Data.visible ? 'hide' : '') + ' lfr-ddm-form-field-container');
      incrementalDom.elementOpenEnd();
        soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), variant__soy8, false)(field16Data, null, opt_ijData);
      incrementalDom.elementClose('div');
    }
}
exports.fields = $fields;
/**
 * @typedef {{
 *  fields: !Array<?>
 * }}
 */
$fields.Params;
if (goog.DEBUG) {
  $fields.soyTemplateName = 'ddm.fields';
}


/**
 * @param {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  context: (?),
 *  evaluatorURL: (!goog.soy.data.SanitizedContent|string),
 *  fieldTypes: (!goog.soy.data.SanitizedContent|string),
 *  portletNamespace: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_renderer_js(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var containerId = soy.asserts.assertType(goog.isString(opt_data.containerId) || opt_data.containerId instanceof goog.soy.data.SanitizedContent, 'containerId', opt_data.containerId, '!goog.soy.data.SanitizedContent|string');
  /** @type {?} */
  var context = opt_data.context;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var evaluatorURL = soy.asserts.assertType(goog.isString(opt_data.evaluatorURL) || opt_data.evaluatorURL instanceof goog.soy.data.SanitizedContent, 'evaluatorURL', opt_data.evaluatorURL, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var fieldTypes = soy.asserts.assertType(goog.isString(opt_data.fieldTypes) || opt_data.fieldTypes instanceof goog.soy.data.SanitizedContent, 'fieldTypes', opt_data.fieldTypes, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var portletNamespace = soy.asserts.assertType(goog.isString(opt_data.portletNamespace) || opt_data.portletNamespace instanceof goog.soy.data.SanitizedContent, 'portletNamespace', opt_data.portletNamespace, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  incrementalDom.elementOpenStart('script');
      incrementalDom.attr('type', 'text/javascript');
  incrementalDom.elementOpenEnd();
    incrementalDom.text('AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', \'liferay-ddm-soy-template-util\', function(A) {var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil; SoyTemplateUtil.loadModules( function() {Liferay.DDM.Renderer.FieldTypes.register(');
    soyIdom.print(fieldTypes);
    incrementalDom.text(');');
    var $tmp = $render_form(opt_data, null, opt_ijData);
    goog.asserts.assert($tmp != null);
    incrementalDom.text($tmp);
    incrementalDom.text('}); var destroyFormHandle = function(event) {var form = Liferay.component(\'');
    soyIdom.print(containerId);
    incrementalDom.text('DDMForm\'); var portlet = event.portlet; if (portlet && portlet.contains(form.get(\'container\'))) {form.destroy(); Liferay.detach(\'destroyPortlet\', destroyFormHandle);}}; Liferay.on(\'destroyPortlet\', destroyFormHandle);});');
  incrementalDom.elementClose('script');
}
exports.form_renderer_js = $form_renderer_js;
/**
 * @typedef {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  context: (?),
 *  evaluatorURL: (!goog.soy.data.SanitizedContent|string),
 *  fieldTypes: (!goog.soy.data.SanitizedContent|string),
 *  portletNamespace: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean
 * }}
 */
$form_renderer_js.Params;
if (goog.DEBUG) {
  $form_renderer_js.soyTemplateName = 'ddm.form_renderer_js';
}


/**
 * @param {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  context: (?),
 *  evaluatorURL: (!goog.soy.data.SanitizedContent|string),
 *  portletNamespace: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_form(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  var output = '';
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var containerId = soy.asserts.assertType(goog.isString(opt_data.containerId) || opt_data.containerId instanceof goog.soy.data.SanitizedContent, 'containerId', opt_data.containerId, '!goog.soy.data.SanitizedContent|string');
  /** @type {?} */
  var context = opt_data.context;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var evaluatorURL = soy.asserts.assertType(goog.isString(opt_data.evaluatorURL) || opt_data.evaluatorURL instanceof goog.soy.data.SanitizedContent, 'evaluatorURL', opt_data.evaluatorURL, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var portletNamespace = soy.asserts.assertType(goog.isString(opt_data.portletNamespace) || opt_data.portletNamespace instanceof goog.soy.data.SanitizedContent, 'portletNamespace', opt_data.portletNamespace, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  output += 'var form = Liferay.component( \'';
  output += containerId;
  output += 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#';
  output += containerId;
  output += '\', context: ';
  output += context;
  output += ', evaluatorURL: \'';
  output += evaluatorURL;
  output += '\', portletNamespace: \'';
  output += portletNamespace;
  output += '\', readOnly: ';
  output += readOnly ? 'true' : 'false';
  output += '}).render() ); Liferay.fire( \'';
  output += containerId;
  output += 'DDMForm:render\',{form: form});';
  return output;
}
exports.render_form = $render_form;
/**
 * @typedef {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  context: (?),
 *  evaluatorURL: (!goog.soy.data.SanitizedContent|string),
 *  portletNamespace: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean
 * }}
 */
$render_form.Params;
if (goog.DEBUG) {
  $render_form.soyTemplateName = 'ddm.render_form';
}


/**
 * @param {{
 *  rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_rows(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{columns: !Array<{fields: !Array<?>, size: number}>}>} */
  var rows = soy.asserts.assertType(goog.isArray(opt_data.rows), 'rows', opt_data.rows, '!Array<{columns: !Array<{fields: !Array<?>, size: number}>}>');
  var row67List = rows;
  var row67ListLen = row67List.length;
  for (var row67Index = 0; row67Index < row67ListLen; row67Index++) {
      var row67Data = row67List[row67Index];
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'row');
      incrementalDom.elementOpenEnd();
        $form_row_columns(soy.$$assignDefaults({columns: row67Data.columns}, opt_data), null, opt_ijData);
      incrementalDom.elementClose('div');
    }
}
exports.form_rows = $form_rows;
/**
 * @typedef {{
 *  rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>
 * }}
 */
$form_rows.Params;
if (goog.DEBUG) {
  $form_rows.soyTemplateName = 'ddm.form_rows';
}


/**
 * @param {{
 *  column: {fields: !Array<?>, size: number}
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_row_column(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{fields: !Array<?>, size: number}} */
  var column = soy.asserts.assertType(goog.isObject(opt_data.column), 'column', opt_data.column, '{fields: !Array<?>, size: number}');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'col-md-' + column.size);
  incrementalDom.elementOpenEnd();
    $fields(soy.$$assignDefaults({fields: column.fields}, opt_data), null, opt_ijData);
  incrementalDom.elementClose('div');
}
exports.form_row_column = $form_row_column;
/**
 * @typedef {{
 *  column: {fields: !Array<?>, size: number}
 * }}
 */
$form_row_column.Params;
if (goog.DEBUG) {
  $form_row_column.soyTemplateName = 'ddm.form_row_column';
}


/**
 * @param {{
 *  columns: !Array<{fields: !Array<?>, size: number}>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_row_columns(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{fields: !Array<?>, size: number}>} */
  var columns = soy.asserts.assertType(goog.isArray(opt_data.columns), 'columns', opt_data.columns, '!Array<{fields: !Array<?>, size: number}>');
  var column86List = columns;
  var column86ListLen = column86List.length;
  for (var column86Index = 0; column86Index < column86ListLen; column86Index++) {
      var column86Data = column86List[column86Index];
      $form_row_column(soy.$$assignDefaults({column: column86Data}, opt_data), null, opt_ijData);
    }
}
exports.form_row_columns = $form_row_columns;
/**
 * @typedef {{
 *  columns: !Array<{fields: !Array<?>, size: number}>
 * }}
 */
$form_row_columns.Params;
if (goog.DEBUG) {
  $form_row_columns.soyTemplateName = 'ddm.form_row_columns';
}


/**
 * @param {{
 *  showRequiredFieldsWarning: boolean,
 *  requiredFieldsWarningMessageHTML: function()
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $required_warning_message(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {boolean} */
  var showRequiredFieldsWarning = soy.asserts.assertType(goog.isBoolean(opt_data.showRequiredFieldsWarning) || opt_data.showRequiredFieldsWarning === 1 || opt_data.showRequiredFieldsWarning === 0, 'showRequiredFieldsWarning', opt_data.showRequiredFieldsWarning, 'boolean');
  /** @type {function()} */
  var requiredFieldsWarningMessageHTML = soy.asserts.assertType(goog.isFunction(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'function()');
  if (showRequiredFieldsWarning) {
    requiredFieldsWarningMessageHTML();
  }
}
exports.required_warning_message = $required_warning_message;
/**
 * @typedef {{
 *  showRequiredFieldsWarning: boolean,
 *  requiredFieldsWarningMessageHTML: function()
 * }}
 */
$required_warning_message.Params;
if (goog.DEBUG) {
  $required_warning_message.soyTemplateName = 'ddm.required_warning_message';
}


/**
 * @param {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function(),
 *  submitLabel: (!goog.soy.data.SanitizedContent|string),
 *  showSubmitButton: boolean,
 *  strings: {next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)}
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $wizard_form(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var containerId = soy.asserts.assertType(goog.isString(opt_data.containerId) || opt_data.containerId instanceof goog.soy.data.SanitizedContent, 'containerId', opt_data.containerId, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>} */
  var pages = soy.asserts.assertType(goog.isArray(opt_data.pages), 'pages', opt_data.pages, '!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {function()} */
  var requiredFieldsWarningMessageHTML = soy.asserts.assertType(goog.isFunction(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'function()');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var submitLabel = soy.asserts.assertType(goog.isString(opt_data.submitLabel) || opt_data.submitLabel instanceof goog.soy.data.SanitizedContent, 'submitLabel', opt_data.submitLabel, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var showSubmitButton = soy.asserts.assertType(goog.isBoolean(opt_data.showSubmitButton) || opt_data.showSubmitButton === 1 || opt_data.showSubmitButton === 0, 'showSubmitButton', opt_data.showSubmitButton, 'boolean');
  /** @type {{next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)}');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'lfr-ddm-form-container');
      incrementalDom.attr('id', containerId);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'lfr-ddm-form-content');
    incrementalDom.elementOpenEnd();
      if ((pages.length) > 1) {
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'lfr-ddm-form-wizard');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('ul');
              incrementalDom.attr('class', 'multi-step-progress-bar multi-step-progress-bar-collapse');
          incrementalDom.elementOpenEnd();
            var page122List = pages;
            var page122ListLen = page122List.length;
            for (var page122Index = 0; page122Index < page122ListLen; page122Index++) {
                var page122Data = page122List[page122Index];
                incrementalDom.elementOpenStart('li');
                    if (page122Index == 0) {
                      incrementalDom.attr('class', 'active');
                    }
                incrementalDom.elementOpenEnd();
                  incrementalDom.elementOpenStart('div');
                      incrementalDom.attr('class', 'progress-bar-title');
                  incrementalDom.elementOpenEnd();
                    soyIdom.print(page122Data.title);
                  incrementalDom.elementClose('div');
                  incrementalDom.elementOpenStart('div');
                      incrementalDom.attr('class', 'divider');
                  incrementalDom.elementOpenEnd();
                  incrementalDom.elementClose('div');
                  incrementalDom.elementOpenStart('div');
                      incrementalDom.attr('class', 'progress-bar-step');
                  incrementalDom.elementOpenEnd();
                    soyIdom.print(page122Index + 1);
                  incrementalDom.elementClose('div');
                incrementalDom.elementClose('li');
              }
          incrementalDom.elementClose('ul');
        incrementalDom.elementClose('div');
      }
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'lfr-ddm-form-pages');
      incrementalDom.elementOpenEnd();
        var page150List = pages;
        var page150ListLen = page150List.length;
        for (var page150Index = 0; page150Index < page150ListLen; page150Index++) {
            var page150Data = page150List[page150Index];
            incrementalDom.elementOpenStart('div');
                incrementalDom.attr('class', (page150Index == 0 ? 'active' : '') + ' lfr-ddm-form-page');
            incrementalDom.elementOpenEnd();
              if (page150Data.title) {
                incrementalDom.elementOpenStart('h3');
                    incrementalDom.attr('class', 'lfr-ddm-form-page-title');
                incrementalDom.elementOpenEnd();
                  soyIdom.print(page150Data.title);
                incrementalDom.elementClose('h3');
              }
              if (page150Data.description) {
                incrementalDom.elementOpenStart('h4');
                    incrementalDom.attr('class', 'lfr-ddm-form-page-description');
                incrementalDom.elementOpenEnd();
                  soyIdom.print(page150Data.description);
                incrementalDom.elementClose('h4');
              }
              $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: page150Data.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
              $form_rows(soy.$$assignDefaults({rows: page150Data.rows}, opt_data), null, opt_ijData);
            incrementalDom.elementClose('div');
          }
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'lfr-ddm-form-pagination-controls');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-primary hide lfr-ddm-form-pagination-prev');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('i');
            incrementalDom.attr('class', 'icon-angle-left');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('i');
        incrementalDom.text(' ');
        soyIdom.print(strings.previous);
      incrementalDom.elementClose('button');
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-primary' + ((pages.length) == 1 ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        soyIdom.print(strings.next);
        incrementalDom.text(' ');
        incrementalDom.elementOpenStart('i');
            incrementalDom.attr('class', 'icon-angle-right');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('i');
      incrementalDom.elementClose('button');
      if (showSubmitButton) {
        incrementalDom.elementOpenStart('button');
            incrementalDom.attr('class', 'btn btn-primary' + ((pages.length) > 1 ? ' hide' : '') + ' lfr-ddm-form-submit pull-right');
            incrementalDom.attr('disabled', '');
            incrementalDom.attr('type', 'submit');
        incrementalDom.elementOpenEnd();
          soyIdom.print(submitLabel);
        incrementalDom.elementClose('button');
      }
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.wizard_form = $wizard_form;
/**
 * @typedef {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function(),
 *  submitLabel: (!goog.soy.data.SanitizedContent|string),
 *  showSubmitButton: boolean,
 *  strings: {next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)}
 * }}
 */
$wizard_form.Params;
if (goog.DEBUG) {
  $wizard_form.soyTemplateName = 'ddm.wizard_form';
}


/**
 * @param {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function(),
 *  showSubmitButton: boolean,
 *  strings: {next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)},
 *  submitLabel: (!goog.soy.data.SanitizedContent|string)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $paginated_form(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var containerId = soy.asserts.assertType(goog.isString(opt_data.containerId) || opt_data.containerId instanceof goog.soy.data.SanitizedContent, 'containerId', opt_data.containerId, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>} */
  var pages = soy.asserts.assertType(goog.isArray(opt_data.pages), 'pages', opt_data.pages, '!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {function()} */
  var requiredFieldsWarningMessageHTML = soy.asserts.assertType(goog.isFunction(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'function()');
  /** @type {boolean} */
  var showSubmitButton = soy.asserts.assertType(goog.isBoolean(opt_data.showSubmitButton) || opt_data.showSubmitButton === 1 || opt_data.showSubmitButton === 0, 'showSubmitButton', opt_data.showSubmitButton, 'boolean');
  /** @type {{next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var submitLabel = soy.asserts.assertType(goog.isString(opt_data.submitLabel) || opt_data.submitLabel instanceof goog.soy.data.SanitizedContent, 'submitLabel', opt_data.submitLabel, '!goog.soy.data.SanitizedContent|string');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'lfr-ddm-form-container');
      incrementalDom.attr('id', containerId);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'lfr-ddm-form-content');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'lfr-ddm-form-pages');
      incrementalDom.elementOpenEnd();
        var page206List = pages;
        var page206ListLen = page206List.length;
        for (var page206Index = 0; page206Index < page206ListLen; page206Index++) {
            var page206Data = page206List[page206Index];
            incrementalDom.elementOpenStart('div');
                incrementalDom.attr('class', (page206Index == 0 ? 'active' : '') + ' lfr-ddm-form-page');
            incrementalDom.elementOpenEnd();
              if (page206Data.title) {
                incrementalDom.elementOpenStart('h3');
                    incrementalDom.attr('class', 'lfr-ddm-form-page-title');
                incrementalDom.elementOpenEnd();
                  soyIdom.print(page206Data.title);
                incrementalDom.elementClose('h3');
              }
              if (page206Data.description) {
                incrementalDom.elementOpenStart('h4');
                    incrementalDom.attr('class', 'lfr-ddm-form-page-description');
                incrementalDom.elementOpenEnd();
                  soyIdom.print(page206Data.description);
                incrementalDom.elementClose('h4');
              }
              $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: page206Data.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
              $form_rows(soy.$$assignDefaults({rows: page206Data.rows}, opt_data), null, opt_ijData);
            incrementalDom.elementClose('div');
          }
      incrementalDom.elementClose('div');
      if ((pages.length) > 1) {
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'lfr-ddm-form-paginated');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('ul');
              incrementalDom.attr('class', 'pagination pagination-content');
          incrementalDom.elementOpenEnd();
            var page219List = pages;
            var page219ListLen = page219List.length;
            for (var page219Index = 0; page219Index < page219ListLen; page219Index++) {
                var page219Data = page219List[page219Index];
                incrementalDom.elementOpenStart('li');
                    if (page219Index == 0) {
                      incrementalDom.attr('class', 'active');
                    }
                incrementalDom.elementOpenEnd();
                  incrementalDom.elementOpenStart('a');
                      incrementalDom.attr('href', '#');
                  incrementalDom.elementOpenEnd();
                    soyIdom.print(page219Index + 1);
                  incrementalDom.elementClose('a');
                incrementalDom.elementClose('li');
              }
          incrementalDom.elementClose('ul');
        incrementalDom.elementClose('div');
      }
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'lfr-ddm-form-pagination-controls');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-primary hide lfr-ddm-form-pagination-prev');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('i');
            incrementalDom.attr('class', 'icon-angle-left');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('i');
        incrementalDom.text(' ');
        soyIdom.print(strings.previous);
      incrementalDom.elementClose('button');
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-primary' + ((pages.length) == 1 ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        soyIdom.print(strings.next);
        incrementalDom.text(' ');
        incrementalDom.elementOpenStart('i');
            incrementalDom.attr('class', 'icon-angle-right');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('i');
      incrementalDom.elementClose('button');
      if (showSubmitButton) {
        incrementalDom.elementOpenStart('button');
            incrementalDom.attr('class', 'btn btn-primary' + ((pages.length) > 1 ? ' hide' : '') + ' lfr-ddm-form-submit pull-right');
            incrementalDom.attr('disabled', '');
            incrementalDom.attr('type', 'submit');
        incrementalDom.elementOpenEnd();
          soyIdom.print(submitLabel);
        incrementalDom.elementClose('button');
      }
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.paginated_form = $paginated_form;
/**
 * @typedef {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function(),
 *  showSubmitButton: boolean,
 *  strings: {next: (!goog.soy.data.SanitizedContent|string), previous: (!goog.soy.data.SanitizedContent|string)},
 *  submitLabel: (!goog.soy.data.SanitizedContent|string)
 * }}
 */
$paginated_form.Params;
if (goog.DEBUG) {
  $paginated_form.soyTemplateName = 'ddm.paginated_form';
}


/**
 * @param {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function()
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $simple_form(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var containerId = soy.asserts.assertType(goog.isString(opt_data.containerId) || opt_data.containerId instanceof goog.soy.data.SanitizedContent, 'containerId', opt_data.containerId, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>} */
  var pages = soy.asserts.assertType(goog.isArray(opt_data.pages), 'pages', opt_data.pages, '!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {function()} */
  var requiredFieldsWarningMessageHTML = soy.asserts.assertType(goog.isFunction(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'function()');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'lfr-ddm-form-container');
      incrementalDom.attr('id', containerId);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'lfr-ddm-form-fields');
    incrementalDom.elementOpenEnd();
      var page257List = pages;
      var page257ListLen = page257List.length;
      for (var page257Index = 0; page257Index < page257ListLen; page257Index++) {
          var page257Data = page257List[page257Index];
          $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: page257Data.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
          $form_rows(soy.$$assignDefaults({rows: page257Data.rows}, opt_data), null, opt_ijData);
        }
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.simple_form = $simple_form;
/**
 * @typedef {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function()
 * }}
 */
$simple_form.Params;
if (goog.DEBUG) {
  $simple_form.soyTemplateName = 'ddm.simple_form';
}


/**
 * @param {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function()
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $tabbed_form(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var containerId = soy.asserts.assertType(goog.isString(opt_data.containerId) || opt_data.containerId instanceof goog.soy.data.SanitizedContent, 'containerId', opt_data.containerId, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>} */
  var pages = soy.asserts.assertType(goog.isArray(opt_data.pages), 'pages', opt_data.pages, '!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {function()} */
  var requiredFieldsWarningMessageHTML = soy.asserts.assertType(goog.isFunction(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'function()');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'lfr-ddm-form-container');
      incrementalDom.attr('id', containerId);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'lfr-ddm-form-tabs');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'nav navbar-nav');
      incrementalDom.elementOpenEnd();
        var page271List = pages;
        var page271ListLen = page271List.length;
        for (var page271Index = 0; page271Index < page271ListLen; page271Index++) {
            var page271Data = page271List[page271Index];
            incrementalDom.elementOpen('li');
              incrementalDom.elementOpenStart('a');
                  incrementalDom.attr('href', 'javascript:;');
              incrementalDom.elementOpenEnd();
                soyIdom.print(page271Data.title);
              incrementalDom.elementClose('a');
            incrementalDom.elementClose('li');
          }
      incrementalDom.elementClose('ul');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'tab-content lfr-ddm-form-tabs-content');
      incrementalDom.elementOpenEnd();
        var page286List = pages;
        var page286ListLen = page286List.length;
        for (var page286Index = 0; page286Index < page286ListLen; page286Index++) {
            var page286Data = page286List[page286Index];
            incrementalDom.elementOpenStart('div');
                incrementalDom.attr('class', 'lfr-ddm-form-page tab-pane ' + (page286Index == 0 ? 'active' : ''));
            incrementalDom.elementOpenEnd();
              $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: page286Data.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
              $form_rows(soy.$$assignDefaults({rows: page286Data.rows}, opt_data), null, opt_ijData);
            incrementalDom.elementClose('div');
          }
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.tabbed_form = $tabbed_form;
/**
 * @typedef {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>,
 *  requiredFieldsWarningMessageHTML: function()
 * }}
 */
$tabbed_form.Params;
if (goog.DEBUG) {
  $tabbed_form.soyTemplateName = 'ddm.tabbed_form';
}


/**
 * @param {{
 *  active: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $tabbed_form_frame(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {?} */
  var active = opt_data.active;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'lfr-ddm-form-page tab-pane ' + (active ? 'active' : ''));
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('div');
}
exports.tabbed_form_frame = $tabbed_form_frame;
/**
 * @typedef {{
 *  active: (?)
 * }}
 */
$tabbed_form_frame.Params;
if (goog.DEBUG) {
  $tabbed_form_frame.soyTemplateName = 'ddm.tabbed_form_frame';
}


/**
 * @param {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $settings_form(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var containerId = soy.asserts.assertType(goog.isString(opt_data.containerId) || opt_data.containerId instanceof goog.soy.data.SanitizedContent, 'containerId', opt_data.containerId, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>} */
  var pages = soy.asserts.assertType(goog.isArray(opt_data.pages), 'pages', opt_data.pages, '!Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'lfr-ddm-form-container');
      incrementalDom.attr('id', containerId);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'lfr-ddm-settings-form');
    incrementalDom.elementOpenEnd();
      var page315List = pages;
      var page315ListLen = page315List.length;
      for (var page315Index = 0; page315Index < page315ListLen; page315Index++) {
          var page315Data = page315List[page315Index];
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'lfr-ddm-form-page' + (page315Index == 0 ? ' active basic' : '') + (page315Index == page315ListLen - 1 ? ' advanced' : ''));
          incrementalDom.elementOpenEnd();
            $form_rows(soy.$$assignDefaults({rows: page315Data.rows}, opt_data), null, opt_ijData);
          incrementalDom.elementClose('div');
        }
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.settings_form = $settings_form;
/**
 * @typedef {{
 *  containerId: (!goog.soy.data.SanitizedContent|string),
 *  pages: !Array<{description: (!goog.soy.data.SanitizedContent|string), rows: !Array<{columns: !Array<{fields: !Array<?>, size: number}>}>, showRequiredFieldsWarning: boolean, title: (!goog.soy.data.SanitizedContent|string)}>
 * }}
 */
$settings_form.Params;
if (goog.DEBUG) {
  $settings_form.soyTemplateName = 'ddm.settings_form';
}

exports.fields.params = ["fields"];
exports.fields.types = {"fields":"list<?>"};
exports.form_renderer_js.params = ["containerId","context","evaluatorURL","fieldTypes","portletNamespace","readOnly"];
exports.form_renderer_js.types = {"containerId":"string","context":"?","evaluatorURL":"string","fieldTypes":"string","portletNamespace":"string","readOnly":"bool"};
exports.render_form.params = ["containerId","context","evaluatorURL","portletNamespace","readOnly"];
exports.render_form.types = {"containerId":"string","context":"?","evaluatorURL":"string","portletNamespace":"string","readOnly":"bool"};
exports.form_rows.params = ["rows"];
exports.form_rows.types = {"rows":"list<[columns: list<[size: int, fields: list<?>]>]>"};
exports.form_row_column.params = ["column"];
exports.form_row_column.types = {"column":"[size: int, fields: list<?>]"};
exports.form_row_columns.params = ["columns"];
exports.form_row_columns.types = {"columns":"list<[size: int, fields: list<?>]>"};
exports.required_warning_message.params = ["showRequiredFieldsWarning","requiredFieldsWarningMessageHTML"];
exports.required_warning_message.types = {"showRequiredFieldsWarning":"bool","requiredFieldsWarningMessageHTML":"html"};
exports.wizard_form.params = ["containerId","pages","requiredFieldsWarningMessageHTML","submitLabel","showSubmitButton","strings"];
exports.wizard_form.types = {"containerId":"string","pages":"list<[\n\t\ttitle: string,\n\t\tdescription: string,\n\t\tshowRequiredFieldsWarning: bool,\n\t\trows: list<[columns: list<[size: int, fields: list<?>]>]>\n\t]>","requiredFieldsWarningMessageHTML":"html","submitLabel":"string","showSubmitButton":"bool","strings":"[previous: string, next: string]"};
exports.paginated_form.params = ["containerId","pages","requiredFieldsWarningMessageHTML","showSubmitButton","strings","submitLabel"];
exports.paginated_form.types = {"containerId":"string","pages":"list<[\n\t\ttitle: string,\n\t\tdescription: string,\n\t\tshowRequiredFieldsWarning: bool,\n\t\trows: list<[columns: list<[size: int, fields: list<?>]>]>\n\t]>","requiredFieldsWarningMessageHTML":"html","showSubmitButton":"bool","strings":"[previous: string, next: string]","submitLabel":"string"};
exports.simple_form.params = ["containerId","pages","requiredFieldsWarningMessageHTML"];
exports.simple_form.types = {"containerId":"string","pages":"list<[\n\t\ttitle: string,\n\t\tdescription: string,\n\t\tshowRequiredFieldsWarning: bool,\n\t\trows: list<[columns: list<[size: int, fields: list<?>]>]>\n\t]>","requiredFieldsWarningMessageHTML":"html"};
exports.tabbed_form.params = ["containerId","pages","requiredFieldsWarningMessageHTML"];
exports.tabbed_form.types = {"containerId":"string","pages":"list<[\n\t\ttitle: string,\n\t\tdescription: string,\n\t\tshowRequiredFieldsWarning: bool,\n\t\trows: list<[columns: list<[size: int, fields: list<?>]>]>\n\t]>","requiredFieldsWarningMessageHTML":"html"};
exports.tabbed_form_frame.params = ["active"];
exports.tabbed_form_frame.types = {"active":"?"};
exports.settings_form.params = ["containerId","pages"];
exports.settings_form.types = {"containerId":"string","pages":"list<[\n\t\ttitle: string,\n\t\tdescription: string,\n\t\tshowRequiredFieldsWarning: bool,\n\t\trows: list<[columns: list<[size: int, fields: list<?>]>]>\n\t]>"};
templates = exports;
return exports;

});

export { templates };
export default templates;
/* jshint ignore:end */
