/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from form.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field.idom}
 * @hassoydelcall {ddm.field.idom}
 * @public
 */

goog.module('ddm.incrementaldom');

/** @suppress {extraRequire} */
var soy = goog.require('soy');
/** @suppress {extraRequire} */
var soydata = goog.require('soydata');
/** @suppress {extraRequire} */
goog.require('goog.asserts');
/** @suppress {extraRequire} */
goog.require('soy.asserts');
/** @suppress {extraRequire} */
goog.require('goog.i18n.bidi');
/** @suppress {extraRequire} */
goog.require('goog.string');
var IncrementalDom = goog.require('incrementaldom');
var ie_open = IncrementalDom.elementOpen;
var ie_close = IncrementalDom.elementClose;
var ie_void = IncrementalDom.elementVoid;
var ie_open_start = IncrementalDom.elementOpenStart;
var ie_open_end = IncrementalDom.elementOpenEnd;
var itext = IncrementalDom.text;
var iattr = IncrementalDom.attr;


/**
 * @param {Object<string, *>=} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2_86478705(opt_data, opt_ignored, opt_ijData) {
}
exports.__deltemplate_s2_86478705 = __deltemplate_s2_86478705;
if (goog.DEBUG) {
  __deltemplate_s2_86478705.soyTemplateName = 'ddm.__deltemplate_s2_86478705';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), '', 0, __deltemplate_s2_86478705);


/**
 * @param {{
 *    fields: !Array<(?)>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $fields(opt_data, opt_ignored, opt_ijData) {
  var fields = goog.asserts.assertArray(opt_data.fields, "expected parameter 'fields' of type list<?>.");
  var fieldList12 = fields;
  var fieldListLen12 = fieldList12.length;
  for (var fieldIndex12 = 0; fieldIndex12 < fieldListLen12; fieldIndex12++) {
    var fieldData12 = fieldList12[fieldIndex12];
    var variant__soy4 = fieldData12.type;
    ie_open('div', null, null,
        'class', 'clearfix ' + ((! fieldData12.visible) ? 'hide' : '') + ' lfr-ddm-form-field-container');
      soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), variant__soy4, false)(fieldData12, null, opt_ijData);
    ie_close('div');
  }
}
exports.fields = $fields;
if (goog.DEBUG) {
  $fields.soyTemplateName = 'ddm.fields';
}


/**
 * @param {{
 *    containerId: string,
 *    context: (?),
 *    evaluatorURL: string,
 *    fieldTypes: string,
 *    portletNamespace: string,
 *    readOnly: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_renderer_js(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.containerId) || (opt_data.containerId instanceof goog.soy.data.SanitizedContent), 'containerId', opt_data.containerId, 'string|goog.soy.data.SanitizedContent');
  var containerId = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.containerId);
  soy.asserts.assertType(goog.isString(opt_data.evaluatorURL) || (opt_data.evaluatorURL instanceof goog.soy.data.SanitizedContent), 'evaluatorURL', opt_data.evaluatorURL, 'string|goog.soy.data.SanitizedContent');
  var evaluatorURL = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.evaluatorURL);
  soy.asserts.assertType(goog.isString(opt_data.fieldTypes) || (opt_data.fieldTypes instanceof goog.soy.data.SanitizedContent), 'fieldTypes', opt_data.fieldTypes, 'string|goog.soy.data.SanitizedContent');
  var fieldTypes = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.fieldTypes);
  soy.asserts.assertType(goog.isString(opt_data.portletNamespace) || (opt_data.portletNamespace instanceof goog.soy.data.SanitizedContent), 'portletNamespace', opt_data.portletNamespace, 'string|goog.soy.data.SanitizedContent');
  var portletNamespace = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.portletNamespace);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  ie_open('script', null, null,
      'type', 'text/javascript');
    itext('AUI().use( \'liferay-ddm-form-renderer\', \'liferay-ddm-form-renderer-field\', \'liferay-ddm-soy-template-util\', function(A) {var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil; SoyTemplateUtil.loadModules( function() {Liferay.DDM.Renderer.FieldTypes.register(');
    var dyn0 = fieldTypes;
    if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
    itext(');');
    itext((goog.asserts.assert(($render_form(opt_data, null, opt_ijData)) != null), $render_form(opt_data, null, opt_ijData)));
    itext('}); var destroyFormHandle = function(event) {var form = Liferay.component(\'');
    var dyn1 = containerId;
    if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
    itext('DDMForm\'); var portlet = event.portlet; if (portlet && portlet.contains(form.get(\'container\'))) {form.destroy(); Liferay.detach(\'destroyPortlet\', destroyFormHandle);}}; Liferay.on(\'destroyPortlet\', destroyFormHandle);});');
  ie_close('script');
}
exports.form_renderer_js = $form_renderer_js;
if (goog.DEBUG) {
  $form_renderer_js.soyTemplateName = 'ddm.form_renderer_js';
}


/**
 * @param {{
 *    containerId: string,
 *    context: (?),
 *    evaluatorURL: string,
 *    portletNamespace: string,
 *    readOnly: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_form(opt_data, opt_ignored, opt_ijData) {
  var output = '';
  soy.asserts.assertType(goog.isString(opt_data.containerId) || (opt_data.containerId instanceof goog.soy.data.SanitizedContent), 'containerId', opt_data.containerId, 'string|goog.soy.data.SanitizedContent');
  var containerId = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.containerId);
  soy.asserts.assertType(goog.isString(opt_data.evaluatorURL) || (opt_data.evaluatorURL instanceof goog.soy.data.SanitizedContent), 'evaluatorURL', opt_data.evaluatorURL, 'string|goog.soy.data.SanitizedContent');
  var evaluatorURL = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.evaluatorURL);
  soy.asserts.assertType(goog.isString(opt_data.portletNamespace) || (opt_data.portletNamespace instanceof goog.soy.data.SanitizedContent), 'portletNamespace', opt_data.portletNamespace, 'string|goog.soy.data.SanitizedContent');
  var portletNamespace = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.portletNamespace);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  output += 'var form = Liferay.component( \'';
  output += containerId;
  output += 'DDMForm\', new Liferay.DDM.Renderer.Form({container: \'#';
  output += containerId;
  output += '\', context: ';
  output += opt_data.context;
  output += ', evaluatorURL: \'';
  output += evaluatorURL;
  output += '\', portletNamespace: \'';
  output += portletNamespace;
  output += '\', readOnly: ';
  output += (readOnly) ? 'true' : 'false';
  output += '}).render() ); Liferay.fire( \'';
  output += containerId;
  output += 'DDMForm:render\',{form: form});';
  return output;
}
exports.render_form = $render_form;
if (goog.DEBUG) {
  $render_form.soyTemplateName = 'ddm.render_form';
}


/**
 * @param {{
 *    rows: !Array<{columns: !Array<{fields: !Array<(?)>, size: number}>}>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_rows(opt_data, opt_ignored, opt_ijData) {
  var rows = goog.asserts.assertArray(opt_data.rows, "expected parameter 'rows' of type list<[columns: list<[fields: list<?>, size: int]>]>.");
  var rowList47 = rows;
  var rowListLen47 = rowList47.length;
  for (var rowIndex47 = 0; rowIndex47 < rowListLen47; rowIndex47++) {
    var rowData47 = rowList47[rowIndex47];
    ie_open('div', null, null,
        'class', 'row');
      $form_row_columns(soy.$$assignDefaults({columns: rowData47.columns}, opt_data), null, opt_ijData);
    ie_close('div');
  }
}
exports.form_rows = $form_rows;
if (goog.DEBUG) {
  $form_rows.soyTemplateName = 'ddm.form_rows';
}


/**
 * @param {{
 *    column: {fields: !Array<(?)>, size: number}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_row_column(opt_data, opt_ignored, opt_ijData) {
  var column = goog.asserts.assertObject(opt_data.column, "expected parameter 'column' of type [fields: list<?>, size: int].");
  ie_open('div', null, null,
      'class', 'col-md-' + column.size);
    $fields(soy.$$assignDefaults({fields: column.fields}, opt_data), null, opt_ijData);
  ie_close('div');
}
exports.form_row_column = $form_row_column;
if (goog.DEBUG) {
  $form_row_column.soyTemplateName = 'ddm.form_row_column';
}


/**
 * @param {{
 *    columns: !Array<{fields: !Array<(?)>, size: number}>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $form_row_columns(opt_data, opt_ignored, opt_ijData) {
  var columns = goog.asserts.assertArray(opt_data.columns, "expected parameter 'columns' of type list<[fields: list<?>, size: int]>.");
  var columnList59 = columns;
  var columnListLen59 = columnList59.length;
  for (var columnIndex59 = 0; columnIndex59 < columnListLen59; columnIndex59++) {
    var columnData59 = columnList59[columnIndex59];
    $form_row_column(soy.$$assignDefaults({column: columnData59}, opt_data), null, opt_ijData);
  }
}
exports.form_row_columns = $form_row_columns;
if (goog.DEBUG) {
  $form_row_columns.soyTemplateName = 'ddm.form_row_columns';
}


/**
 * @param {{
 *    showRequiredFieldsWarning: boolean,
 *    requiredFieldsWarningMessageHTML: (!soydata.SanitizedHtml|string)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $required_warning_message(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isBoolean(opt_data.showRequiredFieldsWarning) || opt_data.showRequiredFieldsWarning === 1 || opt_data.showRequiredFieldsWarning === 0, 'showRequiredFieldsWarning', opt_data.showRequiredFieldsWarning, 'boolean');
  var showRequiredFieldsWarning = /** @type {boolean} */ (!!opt_data.showRequiredFieldsWarning);
  soy.asserts.assertType((opt_data.requiredFieldsWarningMessageHTML instanceof Function) || (opt_data.requiredFieldsWarningMessageHTML instanceof soydata.UnsanitizedText) || goog.isString(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'Function');
  var requiredFieldsWarningMessageHTML = /** @type {Function} */ (opt_data.requiredFieldsWarningMessageHTML);
  if (showRequiredFieldsWarning) {
    requiredFieldsWarningMessageHTML();
  }
}
exports.required_warning_message = $required_warning_message;
if (goog.DEBUG) {
  $required_warning_message.soyTemplateName = 'ddm.required_warning_message';
}


/**
 * @param {{
 *    containerId: string,
 *    pages: !Array<{description: string, rows: !Array<{columns: !Array<{fields: !Array<(?)>, size: number}>}>, showRequiredFieldsWarning: boolean, title: string}>,
 *    requiredFieldsWarningMessageHTML: (!soydata.SanitizedHtml|string),
 *    submitLabel: string,
 *    showSubmitButton: boolean,
 *    strings: {next: string, previous: string}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $wizard_form(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.containerId) || (opt_data.containerId instanceof goog.soy.data.SanitizedContent), 'containerId', opt_data.containerId, 'string|goog.soy.data.SanitizedContent');
  var containerId = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.containerId);
  var pages = goog.asserts.assertArray(opt_data.pages, "expected parameter 'pages' of type list<[description: string, rows: list<[columns: list<[fields: list<?>, size: int]>]>, showRequiredFieldsWarning: bool, title: string]>.");
  soy.asserts.assertType((opt_data.requiredFieldsWarningMessageHTML instanceof Function) || (opt_data.requiredFieldsWarningMessageHTML instanceof soydata.UnsanitizedText) || goog.isString(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'Function');
  var requiredFieldsWarningMessageHTML = /** @type {Function} */ (opt_data.requiredFieldsWarningMessageHTML);
  soy.asserts.assertType(goog.isString(opt_data.submitLabel) || (opt_data.submitLabel instanceof goog.soy.data.SanitizedContent), 'submitLabel', opt_data.submitLabel, 'string|goog.soy.data.SanitizedContent');
  var submitLabel = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.submitLabel);
  soy.asserts.assertType(goog.isBoolean(opt_data.showSubmitButton) || opt_data.showSubmitButton === 1 || opt_data.showSubmitButton === 0, 'showSubmitButton', opt_data.showSubmitButton, 'boolean');
  var showSubmitButton = /** @type {boolean} */ (!!opt_data.showSubmitButton);
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [next: string, previous: string].");
  ie_open('div', null, null,
      'class', 'lfr-ddm-form-container',
      'id', containerId);
    ie_open('div', null, null,
        'class', 'lfr-ddm-form-content');
      if (pages.length > 1) {
        ie_open('div', null, null,
            'class', 'lfr-ddm-form-wizard');
          ie_open('ul', null, null,
              'class', 'multi-step-progress-bar multi-step-progress-bar-collapse');
            var pageList81 = pages;
            var pageListLen81 = pageList81.length;
            for (var pageIndex81 = 0; pageIndex81 < pageListLen81; pageIndex81++) {
              var pageData81 = pageList81[pageIndex81];
              ie_open_start('li');
                  if (pageIndex81 == 0) {
                    iattr('class', 'active');
                  }
              ie_open_end();
                ie_open('div', null, null,
                    'class', 'progress-bar-title');
                  var dyn2 = pageData81.title;
                  if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
                ie_close('div');
                ie_void('div', null, null,
                    'class', 'divider');
                ie_open('div', null, null,
                    'class', 'progress-bar-step');
                  var dyn3 = pageIndex81 + 1;
                  if (typeof dyn3 == 'function') dyn3(); else if (dyn3 != null) itext(dyn3);
                ie_close('div');
              ie_close('li');
            }
          ie_close('ul');
        ie_close('div');
      }
      ie_open('div', null, null,
          'class', 'lfr-ddm-form-pages');
        var pageList106 = pages;
        var pageListLen106 = pageList106.length;
        for (var pageIndex106 = 0; pageIndex106 < pageListLen106; pageIndex106++) {
          var pageData106 = pageList106[pageIndex106];
          ie_open('div', null, null,
              'class', ((pageIndex106 == 0) ? 'active' : '') + ' lfr-ddm-form-page');
            if (pageData106.title) {
              ie_open('h3', null, null,
                  'class', 'lfr-ddm-form-page-title');
                var dyn4 = pageData106.title;
                if (typeof dyn4 == 'function') dyn4(); else if (dyn4 != null) itext(dyn4);
              ie_close('h3');
            }
            if (pageData106.description) {
              ie_open('h4', null, null,
                  'class', 'lfr-ddm-form-page-description');
                var dyn5 = pageData106.description;
                if (typeof dyn5 == 'function') dyn5(); else if (dyn5 != null) itext(dyn5);
              ie_close('h4');
            }
            $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: pageData106.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
            $form_rows(soy.$$assignDefaults({rows: pageData106.rows}, opt_data), null, opt_ijData);
          ie_close('div');
        }
      ie_close('div');
    ie_close('div');
    ie_open('div', null, null,
        'class', 'lfr-ddm-form-pagination-controls');
      ie_open('button', null, null,
          'class', 'btn btn-primary hide lfr-ddm-form-pagination-prev',
          'type', 'button');
        ie_void('i', null, null,
            'class', 'icon-angle-left');
        itext(' ');
        var dyn6 = strings.previous;
        if (typeof dyn6 == 'function') dyn6(); else if (dyn6 != null) itext(dyn6);
      ie_close('button');
      ie_open('button', null, null,
          'class', 'btn btn-primary' + ((pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right',
          'type', 'button');
        var dyn7 = strings.next;
        if (typeof dyn7 == 'function') dyn7(); else if (dyn7 != null) itext(dyn7);
        itext(' ');
        ie_void('i', null, null,
            'class', 'icon-angle-right');
      ie_close('button');
      if (showSubmitButton) {
        ie_open('button', null, null,
            'class', 'btn btn-primary' + ((pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right',
            'disabled', '',
            'type', 'submit');
          var dyn8 = submitLabel;
          if (typeof dyn8 == 'function') dyn8(); else if (dyn8 != null) itext(dyn8);
        ie_close('button');
      }
    ie_close('div');
  ie_close('div');
}
exports.wizard_form = $wizard_form;
if (goog.DEBUG) {
  $wizard_form.soyTemplateName = 'ddm.wizard_form';
}


/**
 * @param {{
 *    containerId: string,
 *    pages: !Array<{description: string, rows: !Array<{columns: !Array<{fields: !Array<(?)>, size: number}>}>, showRequiredFieldsWarning: boolean, title: string}>,
 *    requiredFieldsWarningMessageHTML: (!soydata.SanitizedHtml|string),
 *    showSubmitButton: boolean,
 *    strings: {next: string, previous: string},
 *    submitLabel: string
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $paginated_form(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.containerId) || (opt_data.containerId instanceof goog.soy.data.SanitizedContent), 'containerId', opt_data.containerId, 'string|goog.soy.data.SanitizedContent');
  var containerId = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.containerId);
  var pages = goog.asserts.assertArray(opt_data.pages, "expected parameter 'pages' of type list<[description: string, rows: list<[columns: list<[fields: list<?>, size: int]>]>, showRequiredFieldsWarning: bool, title: string]>.");
  soy.asserts.assertType((opt_data.requiredFieldsWarningMessageHTML instanceof Function) || (opt_data.requiredFieldsWarningMessageHTML instanceof soydata.UnsanitizedText) || goog.isString(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'Function');
  var requiredFieldsWarningMessageHTML = /** @type {Function} */ (opt_data.requiredFieldsWarningMessageHTML);
  soy.asserts.assertType(goog.isBoolean(opt_data.showSubmitButton) || opt_data.showSubmitButton === 1 || opt_data.showSubmitButton === 0, 'showSubmitButton', opt_data.showSubmitButton, 'boolean');
  var showSubmitButton = /** @type {boolean} */ (!!opt_data.showSubmitButton);
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [next: string, previous: string].");
  soy.asserts.assertType(goog.isString(opt_data.submitLabel) || (opt_data.submitLabel instanceof goog.soy.data.SanitizedContent), 'submitLabel', opt_data.submitLabel, 'string|goog.soy.data.SanitizedContent');
  var submitLabel = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.submitLabel);
  ie_open('div', null, null,
      'class', 'lfr-ddm-form-container',
      'id', containerId);
    ie_open('div', null, null,
        'class', 'lfr-ddm-form-content');
      ie_open('div', null, null,
          'class', 'lfr-ddm-form-pages');
        var pageList152 = pages;
        var pageListLen152 = pageList152.length;
        for (var pageIndex152 = 0; pageIndex152 < pageListLen152; pageIndex152++) {
          var pageData152 = pageList152[pageIndex152];
          ie_open('div', null, null,
              'class', ((pageIndex152 == 0) ? 'active' : '') + ' lfr-ddm-form-page');
            if (pageData152.title) {
              ie_open('h3', null, null,
                  'class', 'lfr-ddm-form-page-title');
                var dyn9 = pageData152.title;
                if (typeof dyn9 == 'function') dyn9(); else if (dyn9 != null) itext(dyn9);
              ie_close('h3');
            }
            if (pageData152.description) {
              ie_open('h4', null, null,
                  'class', 'lfr-ddm-form-page-description');
                var dyn10 = pageData152.description;
                if (typeof dyn10 == 'function') dyn10(); else if (dyn10 != null) itext(dyn10);
              ie_close('h4');
            }
            $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: pageData152.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
            $form_rows(soy.$$assignDefaults({rows: pageData152.rows}, opt_data), null, opt_ijData);
          ie_close('div');
        }
      ie_close('div');
      if (pages.length > 1) {
        ie_open('div', null, null,
            'class', 'lfr-ddm-form-paginated');
          ie_open('ul', null, null,
              'class', 'pagination pagination-content');
            var pageList165 = pages;
            var pageListLen165 = pageList165.length;
            for (var pageIndex165 = 0; pageIndex165 < pageListLen165; pageIndex165++) {
              var pageData165 = pageList165[pageIndex165];
              ie_open_start('li');
                  if (pageIndex165 == 0) {
                    iattr('class', 'active');
                  }
              ie_open_end();
                ie_open('a', null, null,
                    'href', '#');
                  var dyn11 = pageIndex165 + 1;
                  if (typeof dyn11 == 'function') dyn11(); else if (dyn11 != null) itext(dyn11);
                ie_close('a');
              ie_close('li');
            }
          ie_close('ul');
        ie_close('div');
      }
    ie_close('div');
    ie_open('div', null, null,
        'class', 'lfr-ddm-form-pagination-controls');
      ie_open('button', null, null,
          'class', 'btn btn-primary hide lfr-ddm-form-pagination-prev',
          'type', 'button');
        ie_void('i', null, null,
            'class', 'icon-angle-left');
        itext(' ');
        var dyn12 = strings.previous;
        if (typeof dyn12 == 'function') dyn12(); else if (dyn12 != null) itext(dyn12);
      ie_close('button');
      ie_open('button', null, null,
          'class', 'btn btn-primary' + ((pages.length == 1) ? ' hide' : '') + ' lfr-ddm-form-pagination-next pull-right',
          'type', 'button');
        var dyn13 = strings.next;
        if (typeof dyn13 == 'function') dyn13(); else if (dyn13 != null) itext(dyn13);
        itext(' ');
        ie_void('i', null, null,
            'class', 'icon-angle-right');
      ie_close('button');
      if (showSubmitButton) {
        ie_open('button', null, null,
            'class', 'btn btn-primary' + ((pages.length > 1) ? ' hide' : '') + ' lfr-ddm-form-submit pull-right',
            'disabled', '',
            'type', 'submit');
          var dyn14 = submitLabel;
          if (typeof dyn14 == 'function') dyn14(); else if (dyn14 != null) itext(dyn14);
        ie_close('button');
      }
    ie_close('div');
  ie_close('div');
}
exports.paginated_form = $paginated_form;
if (goog.DEBUG) {
  $paginated_form.soyTemplateName = 'ddm.paginated_form';
}


/**
 * @param {{
 *    containerId: string,
 *    pages: !Array<{description: string, rows: !Array<{columns: !Array<{fields: !Array<(?)>, size: number}>}>, showRequiredFieldsWarning: boolean, title: string}>,
 *    requiredFieldsWarningMessageHTML: (!soydata.SanitizedHtml|string)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $simple_form(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.containerId) || (opt_data.containerId instanceof goog.soy.data.SanitizedContent), 'containerId', opt_data.containerId, 'string|goog.soy.data.SanitizedContent');
  var containerId = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.containerId);
  var pages = goog.asserts.assertArray(opt_data.pages, "expected parameter 'pages' of type list<[description: string, rows: list<[columns: list<[fields: list<?>, size: int]>]>, showRequiredFieldsWarning: bool, title: string]>.");
  soy.asserts.assertType((opt_data.requiredFieldsWarningMessageHTML instanceof Function) || (opt_data.requiredFieldsWarningMessageHTML instanceof soydata.UnsanitizedText) || goog.isString(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'Function');
  var requiredFieldsWarningMessageHTML = /** @type {Function} */ (opt_data.requiredFieldsWarningMessageHTML);
  ie_open('div', null, null,
      'class', 'lfr-ddm-form-container',
      'id', containerId);
    ie_open('div', null, null,
        'class', 'lfr-ddm-form-fields');
      var pageList196 = pages;
      var pageListLen196 = pageList196.length;
      for (var pageIndex196 = 0; pageIndex196 < pageListLen196; pageIndex196++) {
        var pageData196 = pageList196[pageIndex196];
        $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: pageData196.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
        $form_rows(soy.$$assignDefaults({rows: pageData196.rows}, opt_data), null, opt_ijData);
      }
    ie_close('div');
  ie_close('div');
}
exports.simple_form = $simple_form;
if (goog.DEBUG) {
  $simple_form.soyTemplateName = 'ddm.simple_form';
}


/**
 * @param {{
 *    containerId: string,
 *    pages: !Array<{description: string, rows: !Array<{columns: !Array<{fields: !Array<(?)>, size: number}>}>, showRequiredFieldsWarning: boolean, title: string}>,
 *    requiredFieldsWarningMessageHTML: (!soydata.SanitizedHtml|string)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $tabbed_form(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.containerId) || (opt_data.containerId instanceof goog.soy.data.SanitizedContent), 'containerId', opt_data.containerId, 'string|goog.soy.data.SanitizedContent');
  var containerId = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.containerId);
  var pages = goog.asserts.assertArray(opt_data.pages, "expected parameter 'pages' of type list<[description: string, rows: list<[columns: list<[fields: list<?>, size: int]>]>, showRequiredFieldsWarning: bool, title: string]>.");
  soy.asserts.assertType((opt_data.requiredFieldsWarningMessageHTML instanceof Function) || (opt_data.requiredFieldsWarningMessageHTML instanceof soydata.UnsanitizedText) || goog.isString(opt_data.requiredFieldsWarningMessageHTML), 'requiredFieldsWarningMessageHTML', opt_data.requiredFieldsWarningMessageHTML, 'Function');
  var requiredFieldsWarningMessageHTML = /** @type {Function} */ (opt_data.requiredFieldsWarningMessageHTML);
  ie_open('div', null, null,
      'class', 'lfr-ddm-form-container',
      'id', containerId);
    ie_open('div', null, null,
        'class', 'lfr-ddm-form-tabs');
      ie_open('ul', null, null,
          'class', 'nav navbar-nav');
        var pageList206 = pages;
        var pageListLen206 = pageList206.length;
        for (var pageIndex206 = 0; pageIndex206 < pageListLen206; pageIndex206++) {
          var pageData206 = pageList206[pageIndex206];
          ie_open('li');
            ie_open('a', null, null,
                'href', 'javascript:;');
              var dyn15 = pageData206.title;
              if (typeof dyn15 == 'function') dyn15(); else if (dyn15 != null) itext(dyn15);
            ie_close('a');
          ie_close('li');
        }
      ie_close('ul');
      ie_open('div', null, null,
          'class', 'tab-content lfr-ddm-form-tabs-content');
        var pageList220 = pages;
        var pageListLen220 = pageList220.length;
        for (var pageIndex220 = 0; pageIndex220 < pageListLen220; pageIndex220++) {
          var pageData220 = pageList220[pageIndex220];
          ie_open('div', null, null,
              'class', 'lfr-ddm-form-page tab-pane ' + ((pageIndex220 == 0) ? 'active' : ''));
            $required_warning_message(soy.$$assignDefaults({showRequiredFieldsWarning: pageData220.showRequiredFieldsWarning, requiredFieldsWarningMessageHTML: requiredFieldsWarningMessageHTML}, opt_data), null, opt_ijData);
            $form_rows(soy.$$assignDefaults({rows: pageData220.rows}, opt_data), null, opt_ijData);
          ie_close('div');
        }
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.tabbed_form = $tabbed_form;
if (goog.DEBUG) {
  $tabbed_form.soyTemplateName = 'ddm.tabbed_form';
}


/**
 * @param {{
 *    active: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $tabbed_form_frame(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  ie_void('div', null, null,
      'class', 'lfr-ddm-form-page tab-pane ' + ((opt_data.active) ? 'active' : ''));
}
exports.tabbed_form_frame = $tabbed_form_frame;
if (goog.DEBUG) {
  $tabbed_form_frame.soyTemplateName = 'ddm.tabbed_form_frame';
}


/**
 * @param {{
 *    containerId: string,
 *    pages: !Array<{description: string, rows: !Array<{columns: !Array<{fields: !Array<(?)>, size: number}>}>, showRequiredFieldsWarning: boolean, title: string}>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $settings_form(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.containerId) || (opt_data.containerId instanceof goog.soy.data.SanitizedContent), 'containerId', opt_data.containerId, 'string|goog.soy.data.SanitizedContent');
  var containerId = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.containerId);
  var pages = goog.asserts.assertArray(opt_data.pages, "expected parameter 'pages' of type list<[description: string, rows: list<[columns: list<[fields: list<?>, size: int]>]>, showRequiredFieldsWarning: bool, title: string]>.");
  ie_open('div', null, null,
      'class', 'lfr-ddm-form-container',
      'id', containerId);
    ie_open('div', null, null,
        'class', 'lfr-ddm-settings-form');
      var pageList244 = pages;
      var pageListLen244 = pageList244.length;
      for (var pageIndex244 = 0; pageIndex244 < pageListLen244; pageIndex244++) {
        var pageData244 = pageList244[pageIndex244];
        ie_open('div', null, null,
            'class', 'lfr-ddm-form-page' + ((pageIndex244 == 0) ? ' active basic' : '') + ((pageIndex244 == pageListLen244 - 1) ? ' advanced' : ''));
          $form_rows(soy.$$assignDefaults({rows: pageData244.rows}, opt_data), null, opt_ijData);
        ie_close('div');
      }
    ie_close('div');
  ie_close('div');
}
exports.settings_form = $settings_form;
if (goog.DEBUG) {
  $settings_form.soyTemplateName = 'ddm.settings_form';
}

exports.fields.params = ["fields"];
exports.fields.types = {"fields":"list<?>"};
exports.form_renderer_js.params = ["containerId","context","evaluatorURL","fieldTypes","portletNamespace","readOnly"];
exports.form_renderer_js.types = {"containerId":"string","context":"?","evaluatorURL":"string","fieldTypes":"string","portletNamespace":"string","readOnly":"bool"};
exports.render_form.params = ["containerId","context","evaluatorURL","portletNamespace","readOnly"];
exports.render_form.types = {"containerId":"string","context":"?","evaluatorURL":"string","portletNamespace":"string","readOnly":"bool"};
exports.form_rows.params = [];
exports.form_rows.types = {};
exports.form_row_column.params = [];
exports.form_row_column.types = {};
exports.form_row_columns.params = [];
exports.form_row_columns.types = {};
exports.required_warning_message.params = ["showRequiredFieldsWarning","requiredFieldsWarningMessageHTML"];
exports.required_warning_message.types = {"showRequiredFieldsWarning":"bool","requiredFieldsWarningMessageHTML":"html"};
exports.wizard_form.params = ["containerId","requiredFieldsWarningMessageHTML","submitLabel","showSubmitButton"];
exports.wizard_form.types = {"containerId":"string","requiredFieldsWarningMessageHTML":"html","submitLabel":"string","showSubmitButton":"bool"};
exports.paginated_form.params = ["containerId","requiredFieldsWarningMessageHTML","showSubmitButton","submitLabel"];
exports.paginated_form.types = {"containerId":"string","requiredFieldsWarningMessageHTML":"html","showSubmitButton":"bool","submitLabel":"string"};
exports.simple_form.params = ["containerId","requiredFieldsWarningMessageHTML"];
exports.simple_form.types = {"containerId":"string","requiredFieldsWarningMessageHTML":"html"};
exports.tabbed_form.params = ["containerId","requiredFieldsWarningMessageHTML"];
exports.tabbed_form.types = {"containerId":"string","requiredFieldsWarningMessageHTML":"html"};
exports.tabbed_form_frame.params = ["active"];
exports.tabbed_form_frame.types = {"active":"?"};
exports.settings_form.params = ["containerId"];
exports.settings_form.types = {"containerId":"string"};
templates = exports;
return exports;

});

export { templates };
export default templates;
/* jshint ignore:end */
