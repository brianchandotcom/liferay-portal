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
 *    label: string,
 *    name: string,
 *    pathThemeImages: string,
 *    readOnly: boolean,
 *    rows: !Array<{label: string, value: (?)}>,
 *    showLabel: boolean,
 *    value: (?),
 *    visible: boolean,
 *    dir: (null|string|undefined),
 *    focusTarget: (null|undefined|{index: number, row: number}),
 *    required: (boolean|null|undefined),
 *    tip: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  var columns = goog.asserts.assertArray(opt_data.columns, "expected parameter 'columns' of type list<[label: string, value: ?]>.");
  soy.asserts.assertType(goog.isString(opt_data.label) || (opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, 'string|goog.soy.data.SanitizedContent');
  var label = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.label);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || (opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent), 'pathThemeImages', opt_data.pathThemeImages, 'string|goog.soy.data.SanitizedContent');
  var pathThemeImages = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.pathThemeImages);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  var rows = goog.asserts.assertArray(opt_data.rows, "expected parameter 'rows' of type list<[label: string, value: ?]>.");
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.focusTarget == null || goog.isObject(opt_data.focusTarget), 'focusTarget', opt_data.focusTarget, 'null|undefined|{index: number, row: number}');
  var focusTarget = /** @type {null|undefined|{index: number, row: number}} */ (opt_data.focusTarget);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide'),
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label');
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        itext(' ');
        if (required) {
          ie_open('svg', null, null,
              'aria-hidden', 'true',
              'class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
            ie_void('use', null, null,
                'xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
          ie_close('svg');
        }
      ie_close('label');
      if (tip) {
        ie_open('p', null, null,
            'class', 'liferay-ddm-form-field-tip');
          var dyn1 = tip;
          if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
        ie_close('p');
      }
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
            var columnList38 = columns;
            var columnListLen38 = columnList38.length;
            for (var columnIndex38 = 0; columnIndex38 < columnListLen38; columnIndex38++) {
              var columnData38 = columnList38[columnIndex38];
              ie_open('th');
                var dyn2 = columnData38.label;
                if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
              ie_close('th');
            }
          ie_close('tr');
        ie_close('thead');
        ie_open('tbody');
          var rowList67 = rows;
          var rowListLen67 = rowList67.length;
          for (var rowIndex67 = 0; rowIndex67 < rowListLen67; rowIndex67++) {
            var rowData67 = rowList67[rowIndex67];
            ie_open('tr', null, null,
                'name', rowData67.value);
              ie_open('td');
                var dyn3 = rowData67.label;
                if (typeof dyn3 == 'function') dyn3(); else if (dyn3 != null) itext(dyn3);
              ie_close('td');
              var columnList64 = columns;
              var columnListLen64 = columnList64.length;
              for (var columnIndex64 = 0; columnIndex64 < columnListLen64; columnIndex64++) {
                var columnData64 = columnList64[columnIndex64];
                ie_open('td');
                  ie_open_start('input');
                      if (focusTarget && (focusTarget.row == rowData67.value && focusTarget.index == columnIndex64)) {
                        iattr('autoFocus', '');
                      }
                      if (columnData64.value == opt_data.value[rowData67.value]) {
                        iattr('checked', '');
                      }
                      iattr('class', 'form-builder-grid-field');
                      iattr('data-row-index', columnIndex64);
                      if (readOnly) {
                        iattr('disabled', '');
                      }
                      iattr('name', rowData67.value);
                      iattr('type', 'radio');
                      iattr('value', columnData64.value);
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
 *    name: string,
 *    rows: !Array<{label: string, value: (?)}>,
 *    value: (?),
 *    dir: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $hidden_grid(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  var rows = goog.asserts.assertArray(opt_data.rows, "expected parameter 'rows' of type list<[label: string, value: ?]>.");
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  var rowList87 = rows;
  var rowListLen87 = rowList87.length;
  for (var rowIndex87 = 0; rowIndex87 < rowListLen87; rowIndex87++) {
    var rowData87 = rowList87[rowIndex87];
    var inputValue__soy71 = opt_data.value[rowData87.value] ? rowData87.value + ';' + opt_data.value[rowData87.value] : '';
    ie_open_start('input');
        iattr('class', 'form-control');
        if (dir) {
          iattr('dir', dir);
        }
        iattr('name', name);
        iattr('type', 'hidden');
        if (inputValue__soy71) {
          iattr('value', inputValue__soy71);
        }
    ie_open_end();
    ie_close('input');
  }
}
exports.hidden_grid = $hidden_grid;
if (goog.DEBUG) {
  $hidden_grid.soyTemplateName = 'DDMGrid.hidden_grid';
}

exports.render.params = ["label","name","pathThemeImages","readOnly","showLabel","value","visible","dir","required","tip"];
exports.render.types = {"label":"string","name":"string","pathThemeImages":"string","readOnly":"bool","showLabel":"bool","value":"?","visible":"bool","dir":"string","required":"bool","tip":"string"};
exports.hidden_grid.params = ["name","value","dir"];
exports.hidden_grid.types = {"name":"string","value":"?","dir":"string"};
templates = exports;
return exports;

});

class DDMGrid extends Component {}
Soy.register(DDMGrid, templates);
export { DDMGrid, templates };
export default templates;
/* jshint ignore:end */
