/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from fieldset.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMFieldset.
 * @hassoydeltemplate {ddm.field.idom}
 * @hassoydelcall {ddm.field.idom}
 * @public
 */

goog.module('DDMFieldset.incrementaldom');

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
function __deltemplate_s2_95ba1b96(opt_data, opt_ignored, opt_ijData) {
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
 *    columnSize: number,
 *    field: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $fieldset_column(opt_data, opt_ignored, opt_ijData) {
  var columnSize = goog.asserts.assertNumber(opt_data.columnSize, "expected parameter 'columnSize' of type int.");
  ie_open('div', null, null,
      'class', 'form-group-item col-md-' + columnSize);
    ie_open('div', null, null,
        'class', 'clearfix ' + ((! opt_data.field.visible) ? 'hide' : '') + ' lfr-ddm-form-field-container');
      var variant__soy12 = opt_data.field.type;
      soy.$$getDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), variant__soy12, false)(opt_data.field, null, opt_ijData);
    ie_close('div');
  ie_close('div');
}
exports.fieldset_column = $fieldset_column;
if (goog.DEBUG) {
  $fieldset_column.soyTemplateName = 'DDMFieldset.fieldset_column';
}


/**
 * @param {{
 *    columnSize: number,
 *    fields: !Array<(?)>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $fieldset_columns(opt_data, opt_ignored, opt_ijData) {
  var columnSize = goog.asserts.assertNumber(opt_data.columnSize, "expected parameter 'columnSize' of type int.");
  var fields = goog.asserts.assertArray(opt_data.fields, "expected parameter 'fields' of type list<?>.");
  var fieldList19 = fields;
  var fieldListLen19 = fieldList19.length;
  for (var fieldIndex19 = 0; fieldIndex19 < fieldListLen19; fieldIndex19++) {
    var fieldData19 = fieldList19[fieldIndex19];
    $fieldset_column(soy.$$assignDefaults({columnSize: columnSize, field: fieldData19}, opt_data), null, opt_ijData);
  }
}
exports.fieldset_columns = $fieldset_columns;
if (goog.DEBUG) {
  $fieldset_columns.soyTemplateName = 'DDMFieldset.fieldset_columns';
}


/**
 * @param {{
 *    columnSize: number,
 *    name: string,
 *    nestedFields: !Array<(?)>,
 *    visible: boolean,
 *    label: (null|string|undefined),
 *    showBorderBottom: (boolean|null|undefined),
 *    showBorderTop: (boolean|null|undefined),
 *    showLabel: (boolean|null|undefined),
 *    tip: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  var columnSize = goog.asserts.assertNumber(opt_data.columnSize, "expected parameter 'columnSize' of type int.");
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  var nestedFields = goog.asserts.assertArray(opt_data.nestedFields, "expected parameter 'nestedFields' of type list<?>.");
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(opt_data.showBorderBottom == null || goog.isBoolean(opt_data.showBorderBottom) || opt_data.showBorderBottom === 1 || opt_data.showBorderBottom === 0, 'showBorderBottom', opt_data.showBorderBottom, 'boolean|null|undefined');
  var showBorderBottom = /** @type {boolean|null|undefined} */ (opt_data.showBorderBottom);
  soy.asserts.assertType(opt_data.showBorderTop == null || goog.isBoolean(opt_data.showBorderTop) || opt_data.showBorderTop === 1 || opt_data.showBorderTop === 0, 'showBorderTop', opt_data.showBorderTop, 'boolean|null|undefined');
  var showBorderTop = /** @type {boolean|null|undefined} */ (opt_data.showBorderTop);
  soy.asserts.assertType(opt_data.showLabel == null || goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean|null|undefined');
  var showLabel = /** @type {boolean|null|undefined} */ (opt_data.showLabel);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-fieldset' + (showBorderBottom ? ' border-bottom' : ''),
      'data-fieldname', name);
    if (tip) {
      ie_open('p', null, null,
          'class', 'liferay-ddm-form-field-tip');
        var dyn0 = tip;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
      ie_close('p');
    }
    ie_open('fieldset', null, null,
        'class', showBorderTop ? ' border-top' : '');
      if (showLabel) {
        ie_open('legend');
          var dyn1 = label;
          if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
        ie_close('legend');
      }
      ie_open('div', null, null,
          'class', 'form-group-autofit');
        $fieldset_columns(soy.$$assignDefaults({columnSize: columnSize, fields: nestedFields}, opt_data), null, opt_ijData);
      ie_close('div');
    ie_close('fieldset');
  ie_close('div');
}
exports.render = $render;
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
