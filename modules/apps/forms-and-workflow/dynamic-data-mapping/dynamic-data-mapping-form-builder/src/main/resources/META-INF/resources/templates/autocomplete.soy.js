/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from autocomplete.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMAutoComplete.
 * @public
 */

goog.module('DDMAutoComplete.incrementaldom');

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
 * @param {{
 *    addAutoCompleteButton: (!soydata.SanitizedHtml|string),
 *    label: (!soydata.SanitizedHtml|string)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $actionPanel(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.addAutoCompleteButton instanceof Function) || (opt_data.addAutoCompleteButton instanceof soydata.UnsanitizedText) || goog.isString(opt_data.addAutoCompleteButton), 'addAutoCompleteButton', opt_data.addAutoCompleteButton, 'Function');
  var addAutoCompleteButton = /** @type {Function} */ (opt_data.addAutoCompleteButton);
  soy.asserts.assertType((opt_data.label instanceof Function) || (opt_data.label instanceof soydata.UnsanitizedText) || goog.isString(opt_data.label), 'label', opt_data.label, 'Function');
  var label = /** @type {Function} */ (opt_data.label);
  ie_open('div', null, null,
      'class', 'row');
    ie_open('div', null, null,
        'class', 'col-md-12');
      ie_open('div', null, null,
          'class', 'autocomplete-action-panel cursor-pointer panel panel-default');
        ie_open('div', null, null,
            'class', 'panel-body');
          label();
          ie_open('span', null, null,
              'class', 'pull-right');
            addAutoCompleteButton();
          ie_close('span');
        ie_close('div');
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.actionPanel = $actionPanel;
if (goog.DEBUG) {
  $actionPanel.soyTemplateName = 'DDMAutoComplete.actionPanel';
}


/**
 * @param {{
 *    backButton: (!soydata.SanitizedHtml|string),
 *    label: (!soydata.SanitizedHtml|string)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $container(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.backButton instanceof Function) || (opt_data.backButton instanceof soydata.UnsanitizedText) || goog.isString(opt_data.backButton), 'backButton', opt_data.backButton, 'Function');
  var backButton = /** @type {Function} */ (opt_data.backButton);
  soy.asserts.assertType((opt_data.label instanceof Function) || (opt_data.label instanceof soydata.UnsanitizedText) || goog.isString(opt_data.label), 'label', opt_data.label, 'Function');
  var label = /** @type {Function} */ (opt_data.label);
  ie_open('div', null, null,
      'class', 'autocomplete-container');
    ie_open('header', null, null,
        'class', 'header-toolbar');
      ie_open('div', null, null,
          'class', 'toolbar-group');
        ie_open('div', null, null,
            'class', 'toolbar-group-content');
          ie_open('a', null, null,
              'class', 'autocomplete-header-back',
              'href', 'javascript:;');
            backButton();
          ie_close('a');
        ie_close('div');
      ie_close('div');
      ie_open('div', null, null,
          'class', 'toolbar-group-expand-text');
        ie_open('span', null, null,
            'title', 'Autocomplete');
          label();
        ie_close('span');
      ie_close('div');
    ie_close('header');
    ie_void('div', null, null,
        'class', 'autocomplete-body');
  ie_close('div');
}
exports.container = $container;
if (goog.DEBUG) {
  $container.soyTemplateName = 'DDMAutoComplete.container';
}

exports.actionPanel.params = ["addAutoCompleteButton","label"];
exports.actionPanel.types = {"addAutoCompleteButton":"html","label":"html"};
exports.container.params = ["backButton","label"];
exports.container.types = {"backButton":"html","label":"html"};
templates = exports;
return exports;

});

export { templates };
export default templates;
/* jshint ignore:end */
