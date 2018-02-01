/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from paragraph.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMParagraph.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMParagraph.incrementaldom');

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
function __deltemplate_s2_8906c307(opt_data, opt_ignored, opt_ijData) {
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
 *    label: string,
 *    name: string,
 *    text: string,
 *    visible: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.label) || (opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, 'string|goog.soy.data.SanitizedContent');
  var label = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.label);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isString(opt_data.text) || (opt_data.text instanceof goog.soy.data.SanitizedContent), 'text', opt_data.text, 'string|goog.soy.data.SanitizedContent');
  var text = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.text);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide') + ' liferay-ddm-form-field-paragraph',
      'data-fieldname', name);
    if (label) {
      ie_open('label', null, null,
          'for', name);
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
      ie_close('label');
    }
    ie_open('div');
      var dyn1 = text;
      if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
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
