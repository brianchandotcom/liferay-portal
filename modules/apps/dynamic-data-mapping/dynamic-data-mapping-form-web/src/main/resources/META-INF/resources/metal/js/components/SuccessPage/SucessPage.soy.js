/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from SucessPage.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace SuccessPage.
 * @hassoydeltemplate {SuccessPage.container.idom}
 * @hassoydelcall {SuccessPage.container.idom}
 * @public
 */

goog.module('SuccessPage.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {$render.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $render = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {*|null|undefined} */
  var _handleSuccessPageSettingsChanged = opt_data._handleSuccessPageSettingsChanged;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentLabel = soy.asserts.assertType(opt_data.contentLabel == null || (goog.isString(opt_data.contentLabel) || opt_data.contentLabel instanceof goog.soy.data.SanitizedContent), 'contentLabel', opt_data.contentLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var successPageSettings = opt_data.successPageSettings;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titleLabel = soy.asserts.assertType(opt_data.titleLabel == null || (goog.isString(opt_data.titleLabel) || opt_data.titleLabel instanceof goog.soy.data.SanitizedContent), 'titleLabel', opt_data.titleLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  soy.$$getDelegateFn(soy.$$getDelTemplateId('SuccessPage.container.idom'), '', false)({_handleSuccessPageSettingsChanged: _handleSuccessPageSettingsChanged, contentLabel: contentLabel, successPageSettings: successPageSettings, titleLabel: titleLabel}, opt_ijData);
};
exports.render = $render;
/**
 * @typedef {{
 *  _handleSuccessPageSettingsChanged: (*|null|undefined),
 *  contentLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageSettings: (?|undefined),
 *  titleLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'SuccessPage.render';
}


/**
 * @param {__deltemplate__SuccessPage_container_.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__SuccessPage_container_ = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {*|null|undefined} */
  var _handleSuccessPageSettingsChanged = opt_data._handleSuccessPageSettingsChanged;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentLabel = soy.asserts.assertType(opt_data.contentLabel == null || (goog.isString(opt_data.contentLabel) || opt_data.contentLabel instanceof goog.soy.data.SanitizedContent), 'contentLabel', opt_data.contentLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var successPageSettings = opt_data.successPageSettings;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titleLabel = soy.asserts.assertType(opt_data.titleLabel == null || (goog.isString(opt_data.titleLabel) || opt_data.titleLabel instanceof goog.soy.data.SanitizedContent), 'titleLabel', opt_data.titleLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'ddm-page-success-layout');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-builder-success-page pb-3 pt-2');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('label');
      incrementalDom.attr('class', 'control-label');
  incrementalDom.elementOpenEnd();
  soyIdom.print(titleLabel);
  incrementalDom.elementClose('label');
  incrementalDom.elementOpenStart('input');
      incrementalDom.attr('class', 'form-builder-success-page-title form-control');
      incrementalDom.attr('data-onkeyup', _handleSuccessPageSettingsChanged);
      incrementalDom.attr('data-setting', 'title');
      incrementalDom.attr('type', 'text');
      incrementalDom.attr('value', successPageSettings.title.en_US);
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('input');
  incrementalDom.elementOpen('br');
  incrementalDom.elementClose('br');
  incrementalDom.elementOpenStart('label');
      incrementalDom.attr('class', 'control-label');
  incrementalDom.elementOpenEnd();
  soyIdom.print(contentLabel);
  incrementalDom.elementClose('label');
  incrementalDom.elementOpenStart('input');
      incrementalDom.attr('class', 'form-builder-success-page-content form-control');
      incrementalDom.attr('data-onkeyup', _handleSuccessPageSettingsChanged);
      incrementalDom.attr('data-setting', 'body');
      incrementalDom.attr('type', 'text');
      incrementalDom.attr('value', successPageSettings.body.en_US);
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('input');
  incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
};
exports.__deltemplate__SuccessPage_container_ = __deltemplate__SuccessPage_container_;
/**
 * @typedef {{
 *  _handleSuccessPageSettingsChanged: (*|null|undefined),
 *  contentLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageSettings: (?|undefined),
 *  titleLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
__deltemplate__SuccessPage_container_.Params;
if (goog.DEBUG) {
  __deltemplate__SuccessPage_container_.soyTemplateName = 'SuccessPage.__deltemplate__SuccessPage_container_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('SuccessPage.container.idom'), '', 0, __deltemplate__SuccessPage_container_);

exports.render.params = ["_handleSuccessPageSettingsChanged","contentLabel","successPageSettings","titleLabel"];
exports.render.types = {"_handleSuccessPageSettingsChanged":"any","contentLabel":"string","successPageSettings":"?","titleLabel":"string"};
templates = exports;
return exports;

});

class SuccessPage extends Component {}
Soy.register(SuccessPage, templates);
export { SuccessPage, templates };
export default templates;
/* jshint ignore:end */
