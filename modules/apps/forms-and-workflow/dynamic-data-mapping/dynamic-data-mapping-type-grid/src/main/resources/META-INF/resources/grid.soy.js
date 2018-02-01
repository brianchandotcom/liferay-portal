/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from grid.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMGrid.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMGrid.incrementaldom');

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
function __deltemplate_s2_b69d8aa9(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_b69d8aa9 = __deltemplate_s2_b69d8aa9;
if (goog.DEBUG) {
  __deltemplate_s2_b69d8aa9.soyTemplateName = 'DDMGrid.__deltemplate_s2_b69d8aa9';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'grid', 0, __deltemplate_s2_b69d8aa9);


/**
 * @param {{
 *    columns: !Array<{label: string, value: (?)}>,
 *    dir: (null|string|undefined),
 *    focusTarget: (null|undefined|{index: number, row: number}),
 *    label: string,
 *    name: string,
 *    rows: !Array<{label: string, value: (?)}>,
 *    readOnly: boolean,
 *    required: (boolean|null|undefined),
 *    showLabel: boolean,
 *    tip: (null|string|undefined),
 *    value: (?),
 *    visible: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  var columns = goog.asserts.assertArray(opt_data.columns, "expected parameter 'columns' of type list<[label: string, value: ?]>.");
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.focusTarget == null || goog.isObject(opt_data.focusTarget), 'focusTarget', opt_data.focusTarget, 'null|undefined|{index: number, row: number}');
  var focusTarget = /** @type {null|undefined|{index: number, row: number}} */ (opt_data.focusTarget);
  soy.asserts.assertType(goog.isString(opt_data.label) || (opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, 'string|goog.soy.data.SanitizedContent');
  var label = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.label);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  var rows = goog.asserts.assertArray(opt_data.rows, "expected parameter 'rows' of type list<[label: string, value: ?]>.");
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide'),
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label', null, null,
          'class', 'control-label');
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        if (required) {
          ie_void('span', null, null,
              'class', 'icon-asterisk text-warning');
        }
      ie_close('label');
      ie_open('p', null, null,
          'class', 'liferay-ddm-form-field-tip');
        var dyn1 = tip ? tip : '';
        if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
      ie_close('p');
    }
    ie_open('div', null, null,
        'class', 'liferay-ddm-form-field-grid table-responsive');
      if (! readOnly) {
        $hidden_grid(opt_data, null, opt_ijData);
      }
      ie_open('table', null, null,
          'class', 'table table-autofit table-list table-striped');
        ie_open('thead');
          ie_open('tr');
            ie_void('th');
            var columnList32 = columns;
            var columnListLen32 = columnList32.length;
            for (var columnIndex32 = 0; columnIndex32 < columnListLen32; columnIndex32++) {
              var columnData32 = columnList32[columnIndex32];
              ie_open('th');
                var dyn2 = columnData32.label;
                if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
              ie_close('th');
            }
          ie_close('tr');
        ie_close('thead');
        ie_open('tbody');
          var rowList61 = rows;
          var rowListLen61 = rowList61.length;
          for (var rowIndex61 = 0; rowIndex61 < rowListLen61; rowIndex61++) {
            var rowData61 = rowList61[rowIndex61];
            ie_open('tr', null, null,
                'name', rowData61.value);
              ie_open('td');
                var dyn3 = rowData61.label;
                if (typeof dyn3 == 'function') dyn3(); else if (dyn3 != null) itext(dyn3);
              ie_close('td');
              var columnList58 = columns;
              var columnListLen58 = columnList58.length;
              for (var columnIndex58 = 0; columnIndex58 < columnListLen58; columnIndex58++) {
                var columnData58 = columnList58[columnIndex58];
                ie_open('td');
                  ie_open_start('input');
                      if (focusTarget && (focusTarget.row == rowData61.value && focusTarget.index == columnIndex58)) {
                        iattr('autoFocus', '');
                      }
                      if (columnData58.value == opt_data.value[rowData61.value]) {
                        iattr('checked', '');
                      }
                      iattr('class', 'form-builder-grid-field');
                      iattr('data-row-index', columnIndex58);
                      if (readOnly) {
                        iattr('disabled', '');
                      }
                      iattr('name', rowData61.value);
                      iattr('type', 'radio');
                      iattr('value', columnData58.value);
                  ie_open_end();
                  ie_close('input');
                ie_close('td');
              }
            ie_close('tr');
          }
        ie_close('tbody');
      ie_close('table');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMGrid.render';
}


/**
 * @param {{
 *    dir: (null|string|undefined),
 *    name: string,
 *    rows: !Array<{label: string, value: (?)}>,
 *    value: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $hidden_grid(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  var rows = goog.asserts.assertArray(opt_data.rows, "expected parameter 'rows' of type list<[label: string, value: ?]>.");
  var rowList81 = rows;
  var rowListLen81 = rowList81.length;
  for (var rowIndex81 = 0; rowIndex81 < rowListLen81; rowIndex81++) {
    var rowData81 = rowList81[rowIndex81];
    var inputValue__soy65 = opt_data.value[rowData81.value] ? rowData81.value + ';' + opt_data.value[rowData81.value] : '';
    ie_open_start('input');
        iattr('class', 'form-control');
        if (dir) {
          iattr('dir', dir);
        }
        iattr('name', name);
        iattr('type', 'hidden');
        if (inputValue__soy65) {
          iattr('value', inputValue__soy65);
        }
    ie_open_end();
    ie_close('input');
  }
}
exports.hidden_grid = $hidden_grid;
if (goog.DEBUG) {
  $hidden_grid.soyTemplateName = 'DDMGrid.hidden_grid';
}

exports.render.params = ["dir","label","name","readOnly","required","showLabel","tip","value","visible"];
exports.render.types = {"dir":"string","label":"string","name":"string","readOnly":"bool","required":"bool","showLabel":"bool","tip":"string","value":"?","visible":"bool"};
exports.hidden_grid.params = ["dir","name","value"];
exports.hidden_grid.types = {"dir":"string","name":"string","value":"?"};
templates = exports;
return exports;

});

class DDMGrid extends Component {}
Soy.register(DDMGrid, templates);
export { DDMGrid, templates };
export default templates;
/* jshint ignore:end */
