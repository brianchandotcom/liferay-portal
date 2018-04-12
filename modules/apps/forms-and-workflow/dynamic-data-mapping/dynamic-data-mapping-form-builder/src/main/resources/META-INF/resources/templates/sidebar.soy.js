/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from sidebar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMSidebar.
 * @hassoydeltemplate {DDMSidebar.sidebar_body.idom}
 * @hassoydeltemplate {DDMSidebar.sidebar_toolbar.idom}
 * @hassoydelcall {DDMSidebar.sidebar_body.idom}
 * @hassoydelcall {DDMSidebar.sidebar_toolbar.idom}
 * @public
 */

goog.module('DDMSidebar.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');

var $templateAlias1 = Soy.getTemplate('DDMFieldSettingsToolbar.incrementaldom', 'render');


/**
 * @param {{
 *  closeButtonIcon: function(),
 *  toolbarButtonIcon: function(),
 *  toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}),
 *  type: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $header(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var closeButtonIcon = soy.asserts.assertType(goog.isFunction(opt_data.closeButtonIcon), 'closeButtonIcon', opt_data.closeButtonIcon, 'function()');
  /** @type {function()} */
  var toolbarButtonIcon = soy.asserts.assertType(goog.isFunction(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'function()');
  /** @type {null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}} */
  var toolbarTemplateContext = soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var type = soy.asserts.assertType(opt_data.type == null || (goog.isString(opt_data.type) || opt_data.type instanceof goog.soy.data.SanitizedContent), 'type', opt_data.type, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'sidebar-header');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'sidebar-section-flex');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'flex-col flex-col-expand');
      incrementalDom.elementOpenEnd();
        if (type) {
          soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), type, false)(opt_data, null, opt_ijData);
        } else {
          soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), '', false)(opt_data, null, opt_ijData);
        }
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'flex-col');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('ul');
            incrementalDom.attr('class', 'nav nav-unstyled sidebar-actions');
        incrementalDom.elementOpenEnd();
          if (toolbarTemplateContext) {
            incrementalDom.elementOpenStart('li');
                incrementalDom.attr('class', 'nav-item');
            incrementalDom.elementOpenEnd();
              $field_options_toolbar(opt_data, null, opt_ijData);
            incrementalDom.elementClose('li');
          }
          incrementalDom.elementOpenStart('li');
              incrementalDom.attr('class', 'nav-item');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('a');
                incrementalDom.attr('class', 'form-builder-sidebar-close nav-link nav-link-monospaced sidebar-link');
                incrementalDom.attr('href', 'javascript:;');
            incrementalDom.elementOpenEnd();
              closeButtonIcon();
            incrementalDom.elementClose('a');
          incrementalDom.elementClose('li');
        incrementalDom.elementClose('ul');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.header = $header;
/**
 * @typedef {{
 *  closeButtonIcon: function(),
 *  toolbarButtonIcon: function(),
 *  toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}),
 *  type: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$header.Params;
if (goog.DEBUG) {
  $header.soyTemplateName = 'DDMSidebar.header';
}


/**
 * @param {{
 *  closeButtonIcon: function(),
 *  toolbarButtonIcon: function(),
 *  bodyContent: (function()|null|undefined),
 *  currentFieldType: (null|undefined|{icon: function(), name: (!goog.soy.data.SanitizedContent|string)}),
 *  fieldTypeOptions: (null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}),
 *  toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}),
 *  type: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var closeButtonIcon = soy.asserts.assertType(goog.isFunction(opt_data.closeButtonIcon), 'closeButtonIcon', opt_data.closeButtonIcon, 'function()');
  /** @type {function()} */
  var toolbarButtonIcon = soy.asserts.assertType(goog.isFunction(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'function()');
  /** @type {function()|null|undefined} */
  var bodyContent = soy.asserts.assertType(opt_data.bodyContent == null || goog.isFunction(opt_data.bodyContent), 'bodyContent', opt_data.bodyContent, 'function()|null|undefined');
  /** @type {null|undefined|{icon: function(), name: (!goog.soy.data.SanitizedContent|string)}} */
  var currentFieldType = soy.asserts.assertType(opt_data.currentFieldType == null || goog.isObject(opt_data.currentFieldType), 'currentFieldType', opt_data.currentFieldType, 'null|undefined|{icon: function(), name: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}} */
  var fieldTypeOptions = soy.asserts.assertType(opt_data.fieldTypeOptions == null || goog.isObject(opt_data.fieldTypeOptions), 'fieldTypeOptions', opt_data.fieldTypeOptions, 'null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}');
  /** @type {null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}} */
  var toolbarTemplateContext = soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var type = soy.asserts.assertType(opt_data.type == null || (goog.isString(opt_data.type) || opt_data.type instanceof goog.soy.data.SanitizedContent), 'type', opt_data.type, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'sidebar');
      incrementalDom.attr('id', 'lfr-ddm-sidebar');
  incrementalDom.elementOpenEnd();
    if (type) {
      $header(opt_data, null, opt_ijData);
      soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), type, false)(opt_data, null, opt_ijData);
    } else {
      $header(opt_data, null, opt_ijData);
      soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), '', false)(opt_data, null, opt_ijData);
    }
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  closeButtonIcon: function(),
 *  toolbarButtonIcon: function(),
 *  bodyContent: (function()|null|undefined),
 *  currentFieldType: (null|undefined|{icon: function(), name: (!goog.soy.data.SanitizedContent|string)}),
 *  fieldTypeOptions: (null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}),
 *  toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}),
 *  type: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMSidebar.render';
}


/**
 * @param {{
 *  fieldTypeOptions: (null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>})
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2419_85c87286(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}} */
  var fieldTypeOptions = soy.asserts.assertType(opt_data.fieldTypeOptions == null || goog.isObject(opt_data.fieldTypeOptions), 'fieldTypeOptions', opt_data.fieldTypeOptions, 'null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}');
  if (((soy.$$getMapKeys(fieldTypeOptions)).length) != 0) {
    incrementalDom.elementOpenStart('ul');
        incrementalDom.attr('class', 'lfr-ddm-toolbar-field-type nav');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'dropdown nav-item');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('button');
            incrementalDom.attr('aria-expanded', 'false');
            incrementalDom.attr('aria-haspopup', 'true');
            incrementalDom.attr('class', 'btn btn-unstyled pl-0 dropdown-toggle nav-link');
            incrementalDom.attr('data-toggle', 'dropdown');
            incrementalDom.attr('type', 'button');
            incrementalDom.attr('id', 'field-type-menu-content');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('button');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('aria-labelledby', '');
            incrementalDom.attr('class', 'dropdown-menu');
            incrementalDom.attr('x-placement', 'bottom-start');
        incrementalDom.elementOpenEnd();
          var fieldType2432List = fieldTypeOptions.basic;
          var fieldType2432ListLen = fieldType2432List.length;
          for (var fieldType2432Index = 0; fieldType2432Index < fieldType2432ListLen; fieldType2432Index++) {
              var fieldType2432Data = fieldType2432List[fieldType2432Index];
              incrementalDom.elementOpenStart('a');
                  incrementalDom.attr('class', 'dropdown-item');
                  incrementalDom.attr('data-name', fieldType2432Data.name);
                  incrementalDom.attr('href', '#1');
              incrementalDom.elementOpenEnd();
                soyIdom.print(fieldType2432Data.icon);
                incrementalDom.text(' ');
                soyIdom.print(fieldType2432Data.label);
              incrementalDom.elementClose('a');
            }
          var fieldType2442List = fieldTypeOptions.customized;
          var fieldType2442ListLen = fieldType2442List.length;
          for (var fieldType2442Index = 0; fieldType2442Index < fieldType2442ListLen; fieldType2442Index++) {
              var fieldType2442Data = fieldType2442List[fieldType2442Index];
              incrementalDom.elementOpenStart('a');
                  incrementalDom.attr('class', 'dropdown-item');
                  incrementalDom.attr('data-name', fieldType2442Data.name);
                  incrementalDom.attr('href', '#1');
              incrementalDom.elementOpenEnd();
                soyIdom.print(fieldType2442Data.icon);
                incrementalDom.text(' ');
                soyIdom.print(fieldType2442Data.label);
              incrementalDom.elementClose('a');
            }
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('li');
    incrementalDom.elementClose('ul');
  }
}
exports.__deltemplate_s2419_85c87286 = __deltemplate_s2419_85c87286;
/**
 * @typedef {{
 *  fieldTypeOptions: (null|undefined|{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>})
 * }}
 */
__deltemplate_s2419_85c87286.Params;
if (goog.DEBUG) {
  __deltemplate_s2419_85c87286.soyTemplateName = 'DDMSidebar.__deltemplate_s2419_85c87286';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), '', 0, __deltemplate_s2419_85c87286);


/**
 * @param {{
 *  bodyContent: (function()|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2446_fdc381d3(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {function()|null|undefined} */
  var bodyContent = soy.asserts.assertType(opt_data.bodyContent == null || goog.isFunction(opt_data.bodyContent), 'bodyContent', opt_data.bodyContent, 'function()|null|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'sidebar-body');
  incrementalDom.elementOpenEnd();
    soyIdom.print(bodyContent);
  incrementalDom.elementClose('div');
}
exports.__deltemplate_s2446_fdc381d3 = __deltemplate_s2446_fdc381d3;
/**
 * @typedef {{
 *  bodyContent: (function()|null|undefined)
 * }}
 */
__deltemplate_s2446_fdc381d3.Params;
if (goog.DEBUG) {
  __deltemplate_s2446_fdc381d3.soyTemplateName = 'DDMSidebar.__deltemplate_s2446_fdc381d3';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), '', 0, __deltemplate_s2446_fdc381d3);


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
function $field_options_toolbar(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var toolbarButtonIcon = soy.asserts.assertType(goog.isFunction(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'function()');
  /** @type {null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}} */
  var toolbarTemplateContext = soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>}');
  $templateAlias1(opt_data, null, opt_ijData);
}
exports.field_options_toolbar = $field_options_toolbar;
/**
 * @typedef {{
 *  toolbarButtonIcon: function(),
 *  toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: (!goog.soy.data.SanitizedContent|string), handler: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string)}>})
 * }}
 */
$field_options_toolbar.Params;
if (goog.DEBUG) {
  $field_options_toolbar.soyTemplateName = 'DDMSidebar.field_options_toolbar';
}

exports.header.params = ["closeButtonIcon","toolbarButtonIcon","toolbarTemplateContext","type"];
exports.header.types = {"closeButtonIcon":"html","toolbarButtonIcon":"html","toolbarTemplateContext":"[options: list<[buttonClass: string, handler: string, label: string]>]","type":"string"};
exports.render.params = ["closeButtonIcon","toolbarButtonIcon","bodyContent","currentFieldType","fieldTypeOptions","toolbarTemplateContext","type"];
exports.render.types = {"closeButtonIcon":"html","toolbarButtonIcon":"html","bodyContent":"html","currentFieldType":"[\n\t\ticon: html,\n\t\tname: string\n\t]","fieldTypeOptions":"[\n\t\tbasic: list<[description: string, icon: html, label: string, name: string]>,\n\t\tcustomized: list<[description: string, icon: html, label: string, name: string]>\n\t]","toolbarTemplateContext":"[options: list<[buttonClass: string, handler: string, label: string]>]","type":"string"};
exports.field_options_toolbar.params = ["toolbarButtonIcon","toolbarTemplateContext"];
exports.field_options_toolbar.types = {"toolbarButtonIcon":"html","toolbarTemplateContext":"[options: list<[buttonClass: string, handler: string, label: string]>]"};
templates = exports;
return exports;

});

class DDMSidebar extends Component {}
Soy.register(DDMSidebar, templates);
export { DDMSidebar, templates };
export default templates;
/* jshint ignore:end */
