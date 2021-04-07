/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from MetalFieldMock.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace MetalFieldMock.
 * @public
 */

goog.module('MetalFieldMock.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {{
 *  _handleDuplicateClick: (*|null|undefined),
 *  _handleInputBlur: (*|null|undefined),
 *  _handleInputChange: (*|null|undefined),
 *  _handleInputFocus: (*|null|undefined),
 *  _handleRemoveClick: (*|null|undefined),
 *  value: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {*|null|undefined} */
  var _handleDuplicateClick = opt_data._handleDuplicateClick;
  /** @type {*|null|undefined} */
  var _handleInputBlur = opt_data._handleInputBlur;
  /** @type {*|null|undefined} */
  var _handleInputChange = opt_data._handleInputChange;
  /** @type {*|null|undefined} */
  var _handleInputFocus = opt_data._handleInputFocus;
  /** @type {*|null|undefined} */
  var _handleRemoveClick = opt_data._handleRemoveClick;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var value = soy.asserts.assertType(opt_data.value == null || (goog.isString(opt_data.value) || opt_data.value instanceof goog.soy.data.SanitizedContent), 'value', opt_data.value, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpen('div');
    incrementalDom.elementOpenStart('button');
        incrementalDom.attr('class', 'btn btn-danger');
        incrementalDom.attr('data-onclick', _handleRemoveClick);
        incrementalDom.attr('type', 'button');
    incrementalDom.elementOpenEnd();
      incrementalDom.text('Remove');
    incrementalDom.elementClose('button');
    incrementalDom.elementOpenStart('button');
        incrementalDom.attr('class', 'btn btn-primary');
        incrementalDom.attr('data-onclick', _handleDuplicateClick);
        incrementalDom.attr('type', 'button');
    incrementalDom.elementOpenEnd();
      incrementalDom.text('Duplicate');
    incrementalDom.elementClose('button');
    incrementalDom.elementOpenStart('input');
        incrementalDom.attr('class', 'form-control');
        incrementalDom.attr('data-onblur', _handleInputBlur);
        incrementalDom.attr('data-onfocus', _handleInputFocus);
        incrementalDom.attr('data-oninput', _handleInputChange);
        incrementalDom.attr('value', value);
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('input');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  _handleDuplicateClick: (*|null|undefined),
 *  _handleInputBlur: (*|null|undefined),
 *  _handleInputChange: (*|null|undefined),
 *  _handleInputFocus: (*|null|undefined),
 *  _handleRemoveClick: (*|null|undefined),
 *  value: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'MetalFieldMock.render';
}

exports.render.params = ["_handleDuplicateClick","_handleInputBlur","_handleInputChange","_handleInputFocus","_handleRemoveClick","value"];
exports.render.types = {"_handleDuplicateClick":"any","_handleInputBlur":"any","_handleInputChange":"any","_handleInputFocus":"any","_handleRemoveClick":"any","value":"string"};
templates = exports;
return exports;

});

class MetalFieldMock extends Component {}
Soy.register(MetalFieldMock, templates);
export { MetalFieldMock, templates };
export default templates;
/* jshint ignore:end */
