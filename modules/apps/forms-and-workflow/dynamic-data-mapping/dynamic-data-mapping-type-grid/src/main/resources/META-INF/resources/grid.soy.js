/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from grid.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMGrid.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMGrid.incrementaldom');

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
function __deltemplate_s2_b69d8aa9(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
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
 *  columns: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  rows: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  showLabel: boolean,
 *  value: (?),
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  focusTarget: (null|undefined|{index: number, row: number}),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>} */
  var columns = soy.asserts.assertType(goog.isArray(opt_data.columns), 'columns', opt_data.columns, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var label = soy.asserts.assertType(goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent, 'label', opt_data.label, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>} */
  var rows = soy.asserts.assertType(goog.isArray(opt_data.rows), 'rows', opt_data.rows, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>');
  /** @type {boolean} */
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {?} */
  var value = opt_data.value;
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {null|undefined|{index: number, row: number}} */
  var focusTarget = soy.asserts.assertType(opt_data.focusTarget == null || goog.isObject(opt_data.focusTarget), 'focusTarget', opt_data.focusTarget, 'null|undefined|{index: number, row: number}');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide'));
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpen('label');
      if (showLabel) {
        soyIdom.print(label);
        incrementalDom.text(' ');
      }
      if (required) {
        incrementalDom.elementOpenStart('svg');
            incrementalDom.attr('aria-hidden', 'true');
            incrementalDom.attr('class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('use');
              incrementalDom.attr('xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('use');
        incrementalDom.elementClose('svg');
      }
    incrementalDom.elementClose('label');
    if (showLabel) {
      if (tip) {
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', 'form-text');
        incrementalDom.elementOpenEnd();
          soyIdom.print(tip);
        incrementalDom.elementClose('span');
      }
    }
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'liferay-ddm-form-field-grid table-responsive');
    incrementalDom.elementOpenEnd();
      if (!readOnly) {
        $hidden_grid(opt_data, null, opt_ijData);
      }
      incrementalDom.elementOpenStart('table');
          incrementalDom.attr('class', 'table table-autofit table-list table-striped');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpen('thead');
          incrementalDom.elementOpen('tr');
            incrementalDom.elementOpen('th');
            incrementalDom.elementClose('th');
            var column61List = columns;
            var column61ListLen = column61List.length;
            for (var column61Index = 0; column61Index < column61ListLen; column61Index++) {
                var column61Data = column61List[column61Index];
                incrementalDom.elementOpen('th');
                  soyIdom.print(column61Data.label);
                incrementalDom.elementClose('th');
              }
          incrementalDom.elementClose('tr');
        incrementalDom.elementClose('thead');
        incrementalDom.elementOpen('tbody');
          var row91List = rows;
          var row91ListLen = row91List.length;
          for (var row91Index = 0; row91Index < row91ListLen; row91Index++) {
              var row91Data = row91List[row91Index];
              incrementalDom.elementOpenStart('tr');
                  incrementalDom.attr('name', row91Data.value);
              incrementalDom.elementOpenEnd();
                incrementalDom.elementOpen('td');
                  soyIdom.print(row91Data.label);
                incrementalDom.elementClose('td');
                var column88List = columns;
                var column88ListLen = column88List.length;
                for (var column88Index = 0; column88Index < column88ListLen; column88Index++) {
                    var column88Data = column88List[column88Index];
                    incrementalDom.elementOpen('td');
                      incrementalDom.elementOpenStart('input');
                          if (focusTarget && (focusTarget.row == row91Data.value && focusTarget.index == column88Index)) {
                            incrementalDom.attr('autoFocus', '');
                          }
                          if (column88Data.value == value[row91Data.value]) {
                            incrementalDom.attr('checked', '');
                          }
                          incrementalDom.attr('class', 'form-builder-grid-field');
                          incrementalDom.attr('data-row-index', column88Index);
                          if (readOnly) {
                            incrementalDom.attr('disabled', '');
                          }
                          incrementalDom.attr('name', row91Data.value);
                          incrementalDom.attr('type', 'radio');
                          incrementalDom.attr('value', column88Data.value);
                      incrementalDom.elementOpenEnd();
                      incrementalDom.elementClose('input');
                    incrementalDom.elementClose('td');
                  }
              incrementalDom.elementClose('tr');
            }
        incrementalDom.elementClose('tbody');
      incrementalDom.elementClose('table');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  columns: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  rows: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  showLabel: boolean,
 *  value: (?),
 *  visible: boolean,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  focusTarget: (null|undefined|{index: number, row: number}),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMGrid.render';
}


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  rows: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  value: (?),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $hidden_grid(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>} */
  var rows = soy.asserts.assertType(goog.isArray(opt_data.rows), 'rows', opt_data.rows, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>');
  /** @type {?} */
  var value = opt_data.value;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var row117List = rows;
  var row117ListLen = row117List.length;
  for (var row117Index = 0; row117Index < row117ListLen; row117Index++) {
      var row117Data = row117List[row117Index];
      var inputValue__soy101 = value[row117Data.value] ? row117Data.value + ';' + value[row117Data.value] : '';
      incrementalDom.elementOpenStart('input');
          incrementalDom.attr('class', 'form-control');
          if (dir) {
            incrementalDom.attr('dir', dir);
          }
          incrementalDom.attr('name', name);
          incrementalDom.attr('type', 'hidden');
          if (inputValue__soy101) {
            incrementalDom.attr('value', inputValue__soy101);
          }
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('input');
    }
}
exports.hidden_grid = $hidden_grid;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  rows: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  value: (?),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$hidden_grid.Params;
if (goog.DEBUG) {
  $hidden_grid.soyTemplateName = 'DDMGrid.hidden_grid';
}

exports.render.params = ["columns","label","name","pathThemeImages","readOnly","rows","showLabel","value","visible","dir","focusTarget","required","tip"];
exports.render.types = {"columns":"list<[label: string, value: ?]>","label":"string","name":"string","pathThemeImages":"string","readOnly":"bool","rows":"list<[label: string, value: ?]>","showLabel":"bool","value":"?","visible":"bool","dir":"string","focusTarget":"[row: int, index: int]","required":"bool","tip":"string"};
exports.hidden_grid.params = ["name","rows","value","dir"];
exports.hidden_grid.types = {"name":"string","rows":"list<[label: string, value: ?]>","value":"?","dir":"string"};
templates = exports;
return exports;

});

class DDMGrid extends Component {}
Soy.register(DDMGrid, templates);
export { DDMGrid, templates };
export default templates;
/* jshint ignore:end */
