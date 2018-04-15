/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from fieldset.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMFieldset.
 * @hassoydeltemplate {ddm.field.idom}
 * @hassoydelcall {ddm.field.idom}
 * @public
 */

goog.module('DDMFieldset.incrementaldom');

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
function __deltemplate_s2_95ba1b96(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_95ba1b96 = __deltemplate_s2_95ba1b96;
if (goog.DEBUG) {
  __deltemplate_s2_95ba1b96.soyTemplateName = 'DDMFieldset.__deltemplate_s2_95ba1b96';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'fieldset', 0, __deltemplate_s2_95ba1b96);


/**
 * @param {{
 *  columnSize: number,
 *  field: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $fieldset_column(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var columnSize = soy.asserts.assertType(goog.isNumber(opt_data.columnSize), 'columnSize', opt_data.columnSize, 'number');
  /** @type {?} */
  var field = opt_data.field;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group-item col-md-' + columnSize);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'clearfix ' + (!field.visible ? 'hide' : '') + ' lfr-ddm-form-field-container');
    incrementalDom.elementOpenEnd();
      var variant__soy17 = field.type;
      soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), variant__soy17, false)(field, null, opt_ijData);
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.fieldset_column = $fieldset_column;
/**
 * @typedef {{
 *  columnSize: number,
 *  field: (?)
 * }}
 */
$fieldset_column.Params;
if (goog.DEBUG) {
  $fieldset_column.soyTemplateName = 'DDMFieldset.fieldset_column';
}


/**
 * @param {{
 *  columnSize: number,
 *  fields: !Array<?>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $fieldset_columns(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var columnSize = soy.asserts.assertType(goog.isNumber(opt_data.columnSize), 'columnSize', opt_data.columnSize, 'number');
  /** @type {!Array<?>} */
  var fields = soy.asserts.assertType(goog.isArray(opt_data.fields), 'fields', opt_data.fields, '!Array<?>');
  var field30List = fields;
  var field30ListLen = field30List.length;
  for (var field30Index = 0; field30Index < field30ListLen; field30Index++) {
      var field30Data = field30List[field30Index];
      $fieldset_column(soy.$$assignDefaults({columnSize: columnSize, field: field30Data}, opt_data), null, opt_ijData);
    }
}
exports.fieldset_columns = $fieldset_columns;
/**
 * @typedef {{
 *  columnSize: number,
 *  fields: !Array<?>
 * }}
 */
$fieldset_columns.Params;
if (goog.DEBUG) {
  $fieldset_columns.soyTemplateName = 'DDMFieldset.fieldset_columns';
}


/**
 * @param {{
 *  columnSize: number,
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  nestedFields: !Array<?>,
 *  visible: boolean,
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  showBorderBottom: (boolean|null|undefined),
 *  showBorderTop: (boolean|null|undefined),
 *  showLabel: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var columnSize = soy.asserts.assertType(goog.isNumber(opt_data.columnSize), 'columnSize', opt_data.columnSize, 'number');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<?>} */
  var nestedFields = soy.asserts.assertType(goog.isArray(opt_data.nestedFields), 'nestedFields', opt_data.nestedFields, '!Array<?>');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var label = soy.asserts.assertType(opt_data.label == null || (goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var showBorderBottom = soy.asserts.assertType(opt_data.showBorderBottom == null || (goog.isBoolean(opt_data.showBorderBottom) || opt_data.showBorderBottom === 1 || opt_data.showBorderBottom === 0), 'showBorderBottom', opt_data.showBorderBottom, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var showBorderTop = soy.asserts.assertType(opt_data.showBorderTop == null || (goog.isBoolean(opt_data.showBorderTop) || opt_data.showBorderTop === 1 || opt_data.showBorderTop === 0), 'showBorderTop', opt_data.showBorderTop, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var showLabel = soy.asserts.assertType(opt_data.showLabel == null || (goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0), 'showLabel', opt_data.showLabel, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-fieldset' + (showBorderBottom ? ' border-bottom' : ''));
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (tip) {
      incrementalDom.elementOpenStart('p');
          incrementalDom.attr('class', 'liferay-ddm-form-field-tip');
      incrementalDom.elementOpenEnd();
        soyIdom.print(tip);
      incrementalDom.elementClose('p');
    }
    incrementalDom.elementOpenStart('fieldset');
        incrementalDom.attr('class', showBorderTop ? ' border-top' : '');
    incrementalDom.elementOpenEnd();
      if (showLabel) {
        incrementalDom.elementOpen('legend');
          soyIdom.print(label);
        incrementalDom.elementClose('legend');
      }
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'form-group-autofit');
      incrementalDom.elementOpenEnd();
        $fieldset_columns(soy.$$assignDefaults({columnSize: columnSize, fields: nestedFields}, opt_data), null, opt_ijData);
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('fieldset');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  columnSize: number,
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  nestedFields: !Array<?>,
 *  visible: boolean,
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  showBorderBottom: (boolean|null|undefined),
 *  showBorderTop: (boolean|null|undefined),
 *  showLabel: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMFieldset.render';
}

exports.fieldset_column.params = ["columnSize","field"];
exports.fieldset_column.types = {"columnSize":"int","field":"?"};
exports.fieldset_columns.params = ["columnSize","fields"];
exports.fieldset_columns.types = {"columnSize":"int","fields":"list<?>"};
exports.render.params = ["columnSize","name","nestedFields","visible","label","showBorderBottom","showBorderTop","showLabel","tip"];
exports.render.types = {"columnSize":"int","name":"string","nestedFields":"list<?>","visible":"bool","label":"string","showBorderBottom":"bool","showBorderTop":"bool","showLabel":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMFieldset extends Component {}
Soy.register(DDMFieldset, templates);
export { DDMFieldset, templates };
export default templates;
/* jshint ignore:end */
