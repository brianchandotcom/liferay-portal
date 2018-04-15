/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from field-options-toolbar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMFieldSettingsToolbar.
 * @public
 */

goog.module('DDMFieldSettingsToolbar.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {{
 *  toolbarButtonIcon: function(),
 *  toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>})
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var toolbarButtonIcon = soy.asserts.assertType(goog.isFunction(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'function()');
  /** @type {null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}} */
  var toolbarTemplateContext = soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'dropdown dropdown-action show');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('a');
        incrementalDom.attr('aria-expanded', 'false');
        incrementalDom.attr('aria-haspopup', 'true');
        incrementalDom.attr('class', 'dropdown-toggle nav-link nav-link-monospaced sidebar-link');
        incrementalDom.attr('data-toggle', 'dropdown');
        incrementalDom.attr('href', 'javascript:;');
        incrementalDom.attr('id', 'dropdownFieldToolbar');
        incrementalDom.attr('role', 'button');
    incrementalDom.elementOpenEnd();
      toolbarButtonIcon();
    incrementalDom.elementClose('a');
    $field_settings_toolbar_list({options: toolbarTemplateContext.options}, null, opt_ijData);
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  toolbarButtonIcon: function(),
 *  toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>})
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMFieldSettingsToolbar.render';
}


/**
 * @param {{
 *  options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $field_settings_toolbar_list(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>} */
  var options = soy.asserts.assertType(goog.isArray(opt_data.options), 'options', opt_data.options, '!Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('aria-labelledby', 'dropdownFieldToolbar');
      incrementalDom.attr('class', 'dropdown-menu dropdown-menu-right');
  incrementalDom.elementOpenEnd();
    var option659List = options;
    var option659ListLen = option659List.length;
    for (var option659Index = 0; option659Index < option659ListLen; option659Index++) {
        var option659Data = option659List[option659Index];
        $field_settings_toolbar_item({option: option659Data}, null, opt_ijData);
      }
  incrementalDom.elementClose('div');
}
exports.field_settings_toolbar_list = $field_settings_toolbar_list;
/**
 * @typedef {{
 *  options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>
 * }}
 */
$field_settings_toolbar_list.Params;
if (goog.DEBUG) {
  $field_settings_toolbar_list.soyTemplateName = 'DDMFieldSettingsToolbar.field_settings_toolbar_list';
}


/**
 * @param {{
 *  option: {buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $field_settings_toolbar_item(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}} */
  var option = soy.asserts.assertType(goog.isObject(opt_data.option), 'option', opt_data.option, '{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}');
  incrementalDom.elementOpenStart('a');
      incrementalDom.attr('class', 'dropdown-item ' + (option.buttonClass || ''));
      incrementalDom.attr('data-handler', option.handler);
      incrementalDom.attr('href', 'javascript:;');
      incrementalDom.attr('title', option.label);
  incrementalDom.elementOpenEnd();
    soyIdom.print(option.label);
  incrementalDom.elementClose('a');
}
exports.field_settings_toolbar_item = $field_settings_toolbar_item;
/**
 * @typedef {{
 *  option: {buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}
 * }}
 */
$field_settings_toolbar_item.Params;
if (goog.DEBUG) {
  $field_settings_toolbar_item.soyTemplateName = 'DDMFieldSettingsToolbar.field_settings_toolbar_item';
}

exports.render.params = ["toolbarButtonIcon","toolbarTemplateContext"];
exports.render.types = {"toolbarButtonIcon":"html","toolbarTemplateContext":"[options: list<[buttonClass: string, handler: string, label: string]>]"};
exports.field_settings_toolbar_list.params = ["options"];
exports.field_settings_toolbar_list.types = {"options":"list<[buttonClass: string, handler: string, label: string]>"};
exports.field_settings_toolbar_item.params = ["option"];
exports.field_settings_toolbar_item.types = {"option":"[buttonClass: string, handler: string, label: string]"};
templates = exports;
return exports;

});

class DDMFieldSettingsToolbar extends Component {}
Soy.register(DDMFieldSettingsToolbar, templates);
export { DDMFieldSettingsToolbar, templates };
export default templates;
/* jshint ignore:end */
