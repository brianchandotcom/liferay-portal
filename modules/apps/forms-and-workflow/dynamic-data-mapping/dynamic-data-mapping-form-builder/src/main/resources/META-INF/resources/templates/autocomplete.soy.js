/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from autocomplete.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMAutoComplete.
 * @public
 */

goog.module('DDMAutoComplete.incrementaldom');

var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {{
 *  addAutoCompleteButton: function(),
 *  label: function()
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $actionPanel(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var addAutoCompleteButton = soy.asserts.assertType(goog.isFunction(opt_data.addAutoCompleteButton), 'addAutoCompleteButton', opt_data.addAutoCompleteButton, 'function()');
  /** @type {function()} */
  var label = soy.asserts.assertType(goog.isFunction(opt_data.label), 'label', opt_data.label, 'function()');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'row');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'col-md-12');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'autocomplete-action-panel cursor-pointer panel panel-default');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'panel-body');
        incrementalDom.elementOpenEnd();
          label();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'pull-right');
          incrementalDom.elementOpenEnd();
            addAutoCompleteButton();
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.actionPanel = $actionPanel;
/**
 * @typedef {{
 *  addAutoCompleteButton: function(),
 *  label: function()
 * }}
 */
$actionPanel.Params;
if (goog.DEBUG) {
  $actionPanel.soyTemplateName = 'DDMAutoComplete.actionPanel';
}


/**
 * @param {{
 *  backButton: function(),
 *  label: function()
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $container(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var backButton = soy.asserts.assertType(goog.isFunction(opt_data.backButton), 'backButton', opt_data.backButton, 'function()');
  /** @type {function()} */
  var label = soy.asserts.assertType(goog.isFunction(opt_data.label), 'label', opt_data.label, 'function()');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'autocomplete-container');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('header');
        incrementalDom.attr('class', 'header-toolbar');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'toolbar-group');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'toolbar-group-content');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('a');
              incrementalDom.attr('class', 'autocomplete-header-back');
              incrementalDom.attr('href', 'javascript:;');
          incrementalDom.elementOpenEnd();
            backButton();
          incrementalDom.elementClose('a');
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'toolbar-group-expand-text');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('title', 'Autocomplete');
        incrementalDom.elementOpenEnd();
          label();
        incrementalDom.elementClose('span');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('header');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'autocomplete-body');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.container = $container;
/**
 * @typedef {{
 *  backButton: function(),
 *  label: function()
 * }}
 */
$container.Params;
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
