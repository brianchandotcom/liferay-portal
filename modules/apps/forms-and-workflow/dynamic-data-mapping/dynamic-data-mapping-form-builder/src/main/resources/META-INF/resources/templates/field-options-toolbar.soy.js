/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from field-options-toolbar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMFieldSettingsToolbar.
 * @public
 */

goog.module('DDMFieldSettingsToolbar.incrementaldom');

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
 *    toolbarButtonIcon: (!soydata.SanitizedHtml|string),
 *    toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>})
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.toolbarButtonIcon instanceof Function) || (opt_data.toolbarButtonIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'Function');
  var toolbarButtonIcon = /** @type {Function} */ (opt_data.toolbarButtonIcon);
  soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}');
  var toolbarTemplateContext = /** @type {null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}} */ (opt_data.toolbarTemplateContext);
  ie_open('div', null, null,
      'class', 'dropdown dropdown-action show');
    ie_open('a', null, null,
        'aria-expanded', 'false',
        'aria-haspopup', 'true',
        'class', 'dropdown-toggle nav-link nav-link-monospaced sidebar-link',
        'data-toggle', 'dropdown',
        'href', 'javascript:;',
        'id', 'dropdownFieldToolbar',
        'role', 'button');
      toolbarButtonIcon();
    ie_close('a');
    $field_settings_toolbar_list({options: toolbarTemplateContext.options}, null, opt_ijData);
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMFieldSettingsToolbar.render';
}


/**
 * @param {{
 *    options: !Array<{buttonClass: string, handler: string, label: string}>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $field_settings_toolbar_list(opt_data, opt_ignored, opt_ijData) {
  var options = goog.asserts.assertArray(opt_data.options, "expected parameter 'options' of type list<[buttonClass: string, handler: string, label: string]>.");
  ie_open('div', null, null,
      'aria-labelledby', 'dropdownFieldToolbar',
      'class', 'dropdown-menu dropdown-menu-right');
    var optionList59 = options;
    var optionListLen59 = optionList59.length;
    for (var optionIndex59 = 0; optionIndex59 < optionListLen59; optionIndex59++) {
      var optionData59 = optionList59[optionIndex59];
      $field_settings_toolbar_item({option: optionData59}, null, opt_ijData);
    }
  ie_close('div');
}
exports.field_settings_toolbar_list = $field_settings_toolbar_list;
if (goog.DEBUG) {
  $field_settings_toolbar_list.soyTemplateName = 'DDMFieldSettingsToolbar.field_settings_toolbar_list';
}


/**
 * @param {{
 *    option: {buttonClass: string, handler: string, label: string}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $field_settings_toolbar_item(opt_data, opt_ignored, opt_ijData) {
  var option = goog.asserts.assertObject(opt_data.option, "expected parameter 'option' of type [buttonClass: string, handler: string, label: string].");
  ie_open('a', null, null,
      'class', 'dropdown-item ' + (option.buttonClass || ''),
      'data-handler', option.handler,
      'href', 'javascript:;',
      'title', option.label);
    var dyn8 = option.label;
    if (typeof dyn8 == 'function') dyn8(); else if (dyn8 != null) itext(dyn8);
  ie_close('a');
}
exports.field_settings_toolbar_item = $field_settings_toolbar_item;
if (goog.DEBUG) {
  $field_settings_toolbar_item.soyTemplateName = 'DDMFieldSettingsToolbar.field_settings_toolbar_item';
}

exports.render.params = ["toolbarButtonIcon"];
exports.render.types = {"toolbarButtonIcon":"html"};
exports.field_settings_toolbar_list.params = [];
exports.field_settings_toolbar_list.types = {};
exports.field_settings_toolbar_item.params = [];
exports.field_settings_toolbar_item.types = {};
templates = exports;
return exports;

});

class DDMFieldSettingsToolbar extends Component {}
Soy.register(DDMFieldSettingsToolbar, templates);
export { DDMFieldSettingsToolbar, templates };
export default templates;
/* jshint ignore:end */
