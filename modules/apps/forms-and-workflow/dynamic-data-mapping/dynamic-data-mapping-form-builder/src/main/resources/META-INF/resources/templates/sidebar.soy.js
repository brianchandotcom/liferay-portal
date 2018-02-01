/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

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

var $templateAlias1 = Soy.getTemplate('DDMFieldSettingsToolbar.incrementaldom', 'render');


/**
 * @param {{
 *    closeButtonIcon: (!soydata.SanitizedHtml|string),
 *    toolbarButtonIcon: (!soydata.SanitizedHtml|string),
 *    toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}),
 *    type: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $header(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.closeButtonIcon instanceof Function) || (opt_data.closeButtonIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.closeButtonIcon), 'closeButtonIcon', opt_data.closeButtonIcon, 'Function');
  var closeButtonIcon = /** @type {Function} */ (opt_data.closeButtonIcon);
  soy.asserts.assertType((opt_data.toolbarButtonIcon instanceof Function) || (opt_data.toolbarButtonIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'Function');
  var toolbarButtonIcon = /** @type {Function} */ (opt_data.toolbarButtonIcon);
  soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}');
  var toolbarTemplateContext = /** @type {null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}} */ (opt_data.toolbarTemplateContext);
  soy.asserts.assertType(opt_data.type == null || (opt_data.type instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.type), 'type', opt_data.type, 'null|string|undefined');
  var type = /** @type {null|string|undefined} */ (opt_data.type);
  ie_open('div', null, null,
      'class', 'sidebar-header');
    ie_open('div', null, null,
        'class', 'sidebar-section-flex');
      ie_open('div', null, null,
          'class', 'flex-col flex-col-expand');
        if (type) {
          soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), type, false)(opt_data, null, opt_ijData);
        } else {
          soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), '', false)(opt_data, null, opt_ijData);
        }
      ie_close('div');
      ie_open('div', null, null,
          'class', 'flex-col');
        ie_open('ul', null, null,
            'class', 'nav nav-unstyled sidebar-actions');
          if (toolbarTemplateContext) {
            ie_open('li', null, null,
                'class', 'nav-item');
              $field_options_toolbar(opt_data, null, opt_ijData);
            ie_close('li');
          }
          ie_open('li', null, null,
              'class', 'nav-item');
            ie_open('a', null, null,
                'class', 'form-builder-sidebar-close nav-link nav-link-monospaced sidebar-link',
                'href', 'javascript:;');
              closeButtonIcon();
            ie_close('a');
          ie_close('li');
        ie_close('ul');
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.header = $header;
if (goog.DEBUG) {
  $header.soyTemplateName = 'DDMSidebar.header';
}


/**
 * @param {{
 *    closeButtonIcon: (!soydata.SanitizedHtml|string),
 *    toolbarButtonIcon: (!soydata.SanitizedHtml|string),
 *    bodyContent: (?soydata.SanitizedHtml|string|undefined),
 *    currentFieldType: (null|undefined|{icon: (!soydata.SanitizedHtml|string), name: string}),
 *    fieldTypeOptions: (null|undefined|{basic: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>, customized: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>}),
 *    toolbarTemplateContext: (null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}),
 *    type: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.closeButtonIcon instanceof Function) || (opt_data.closeButtonIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.closeButtonIcon), 'closeButtonIcon', opt_data.closeButtonIcon, 'Function');
  var closeButtonIcon = /** @type {Function} */ (opt_data.closeButtonIcon);
  soy.asserts.assertType((opt_data.toolbarButtonIcon instanceof Function) || (opt_data.toolbarButtonIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'Function');
  var toolbarButtonIcon = /** @type {Function} */ (opt_data.toolbarButtonIcon);
  soy.asserts.assertType(opt_data.bodyContent == null || (opt_data.bodyContent instanceof Function) || (opt_data.bodyContent instanceof soydata.UnsanitizedText) || goog.isString(opt_data.bodyContent), 'bodyContent', opt_data.bodyContent, '?soydata.SanitizedHtml|string|undefined');
  var bodyContent = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.bodyContent);
  soy.asserts.assertType(opt_data.currentFieldType == null || goog.isObject(opt_data.currentFieldType), 'currentFieldType', opt_data.currentFieldType, 'null|undefined|{icon: (!soydata.SanitizedHtml|string), name: string}');
  var currentFieldType = /** @type {null|undefined|{icon: (!soydata.SanitizedHtml|string), name: string}} */ (opt_data.currentFieldType);
  soy.asserts.assertType(opt_data.fieldTypeOptions == null || goog.isObject(opt_data.fieldTypeOptions), 'fieldTypeOptions', opt_data.fieldTypeOptions, 'null|undefined|{basic: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>, customized: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>}');
  var fieldTypeOptions = /** @type {null|undefined|{basic: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>, customized: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>}} */ (opt_data.fieldTypeOptions);
  soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}');
  var toolbarTemplateContext = /** @type {null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}} */ (opt_data.toolbarTemplateContext);
  soy.asserts.assertType(opt_data.type == null || (opt_data.type instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.type), 'type', opt_data.type, 'null|string|undefined');
  var type = /** @type {null|string|undefined} */ (opt_data.type);
  ie_open('div', null, null,
      'class', 'sidebar',
      'id', 'lfr-ddm-sidebar');
    if (type) {
      $header(opt_data, null, opt_ijData);
      soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), type, false)(opt_data, null, opt_ijData);
    } else {
      $header(opt_data, null, opt_ijData);
      soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), '', false)(opt_data, null, opt_ijData);
    }
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMSidebar.render';
}


/**
 * @param {{
 *    fieldTypeOptions: (null|undefined|{basic: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>, customized: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>})
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s478_85c87286(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  soy.asserts.assertType(opt_data.fieldTypeOptions == null || goog.isObject(opt_data.fieldTypeOptions), 'fieldTypeOptions', opt_data.fieldTypeOptions, 'null|undefined|{basic: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>, customized: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>}');
  var fieldTypeOptions = /** @type {null|undefined|{basic: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>, customized: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>}} */ (opt_data.fieldTypeOptions);
  if (soy.$$getMapKeys(fieldTypeOptions).length != 0) {
    ie_open('ul', null, null,
        'class', 'lfr-ddm-toolbar-field-type nav');
      ie_open('li', null, null,
          'class', 'dropdown nav-item');
        ie_void('button', null, null,
            'aria-expanded', 'false',
            'aria-haspopup', 'true',
            'class', 'btn btn-unstyled pl-0 dropdown-toggle nav-link',
            'data-toggle', 'dropdown',
            'type', 'button',
            'id', 'field-type-menu-content');
        ie_open('div', null, null,
            'aria-labelledby', '',
            'class', 'dropdown-menu',
            'x-placement', 'bottom-start');
          var fieldTypeList489 = fieldTypeOptions.basic;
          var fieldTypeListLen489 = fieldTypeList489.length;
          for (var fieldTypeIndex489 = 0; fieldTypeIndex489 < fieldTypeListLen489; fieldTypeIndex489++) {
            var fieldTypeData489 = fieldTypeList489[fieldTypeIndex489];
            ie_open('a', null, null,
                'class', 'dropdown-item',
                'data-name', fieldTypeData489.name,
                'href', '#1');
              var dyn41 = fieldTypeData489.icon;
              if (typeof dyn41 == 'function') dyn41(); else if (dyn41 != null) itext(dyn41);
              itext(' ');
              var dyn42 = fieldTypeData489.label;
              if (typeof dyn42 == 'function') dyn42(); else if (dyn42 != null) itext(dyn42);
            ie_close('a');
          }
          var fieldTypeList498 = fieldTypeOptions.customized;
          var fieldTypeListLen498 = fieldTypeList498.length;
          for (var fieldTypeIndex498 = 0; fieldTypeIndex498 < fieldTypeListLen498; fieldTypeIndex498++) {
            var fieldTypeData498 = fieldTypeList498[fieldTypeIndex498];
            ie_open('a', null, null,
                'class', 'dropdown-item',
                'data-name', fieldTypeData498.name,
                'href', '#1');
              var dyn43 = fieldTypeData498.icon;
              if (typeof dyn43 == 'function') dyn43(); else if (dyn43 != null) itext(dyn43);
              itext(' ');
              var dyn44 = fieldTypeData498.label;
              if (typeof dyn44 == 'function') dyn44(); else if (dyn44 != null) itext(dyn44);
            ie_close('a');
          }
        ie_close('div');
      ie_close('li');
    ie_close('ul');
  }
}
exports.__deltemplate_s478_85c87286 = __deltemplate_s478_85c87286;
if (goog.DEBUG) {
  __deltemplate_s478_85c87286.soyTemplateName = 'DDMSidebar.__deltemplate_s478_85c87286';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), '', 0, __deltemplate_s478_85c87286);


/**
 * @param {{
 *    bodyContent: (?soydata.SanitizedHtml|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s501_fdc381d3(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  soy.asserts.assertType(opt_data.bodyContent == null || (opt_data.bodyContent instanceof Function) || (opt_data.bodyContent instanceof soydata.UnsanitizedText) || goog.isString(opt_data.bodyContent), 'bodyContent', opt_data.bodyContent, '?soydata.SanitizedHtml|string|undefined');
  var bodyContent = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.bodyContent);
  ie_open('div', null, null,
      'class', 'sidebar-body');
    var dyn45 = bodyContent;
    if (typeof dyn45 == 'function') dyn45(); else if (dyn45 != null) itext(dyn45);
  ie_close('div');
}
exports.__deltemplate_s501_fdc381d3 = __deltemplate_s501_fdc381d3;
if (goog.DEBUG) {
  __deltemplate_s501_fdc381d3.soyTemplateName = 'DDMSidebar.__deltemplate_s501_fdc381d3';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), '', 0, __deltemplate_s501_fdc381d3);


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
function $field_options_toolbar(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.toolbarButtonIcon instanceof Function) || (opt_data.toolbarButtonIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.toolbarButtonIcon), 'toolbarButtonIcon', opt_data.toolbarButtonIcon, 'Function');
  var toolbarButtonIcon = /** @type {Function} */ (opt_data.toolbarButtonIcon);
  soy.asserts.assertType(opt_data.toolbarTemplateContext == null || goog.isObject(opt_data.toolbarTemplateContext), 'toolbarTemplateContext', opt_data.toolbarTemplateContext, 'null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}');
  var toolbarTemplateContext = /** @type {null|undefined|{options: !Array<{buttonClass: string, handler: string, label: string}>}} */ (opt_data.toolbarTemplateContext);
  $templateAlias1(opt_data, null, opt_ijData);
}
exports.field_options_toolbar = $field_options_toolbar;
if (goog.DEBUG) {
  $field_options_toolbar.soyTemplateName = 'DDMSidebar.field_options_toolbar';
}

exports.header.params = ["closeButtonIcon","toolbarButtonIcon","type"];
exports.header.types = {"closeButtonIcon":"html","toolbarButtonIcon":"html","type":"string"};
exports.render.params = ["closeButtonIcon","toolbarButtonIcon","bodyContent","type"];
exports.render.types = {"closeButtonIcon":"html","toolbarButtonIcon":"html","bodyContent":"html","type":"string"};
exports.field_options_toolbar.params = ["toolbarButtonIcon"];
exports.field_options_toolbar.types = {"toolbarButtonIcon":"html"};
templates = exports;
return exports;

});

class DDMSidebar extends Component {}
Soy.register(DDMSidebar, templates);
export { DDMSidebar, templates };
export default templates;
/* jshint ignore:end */
