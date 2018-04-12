/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from paragraph.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMParagraph.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMParagraph.incrementaldom');

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
function __deltemplate_s2_8906c307(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_8906c307 = __deltemplate_s2_8906c307;
if (goog.DEBUG) {
  __deltemplate_s2_8906c307.soyTemplateName = 'DDMParagraph.__deltemplate_s2_8906c307';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'paragraph', 0, __deltemplate_s2_8906c307);


/**
 * @param {{
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  text: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var label = soy.asserts.assertType(goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent, 'label', opt_data.label, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var text = soy.asserts.assertType(goog.isString(opt_data.text) || opt_data.text instanceof goog.soy.data.SanitizedContent, 'text', opt_data.text, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-paragraph');
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (label) {
      incrementalDom.elementOpenStart('label');
          incrementalDom.attr('for', name);
      incrementalDom.elementOpenEnd();
        soyIdom.print(label);
      incrementalDom.elementClose('label');
    }
    incrementalDom.elementOpen('div');
      soyIdom.print(text);
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  text: (!goog.soy.data.SanitizedContent|string),
 *  visible: boolean
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMParagraph.render';
}

exports.render.params = ["label","name","text","visible"];
exports.render.types = {"label":"string","name":"string","text":"string","visible":"bool"};
templates = exports;
return exports;

});

class DDMParagraph extends Component {}
Soy.register(DDMParagraph, templates);
export { DDMParagraph, templates };
export default templates;
/* jshint ignore:end */
