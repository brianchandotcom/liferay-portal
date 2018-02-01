/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from field-types-sidebar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMFieldTypesSidebar.
 * @hassoydeltemplate {DDMSidebar.sidebar_body.idom}
 * @hassoydeltemplate {DDMSidebar.sidebar_toolbar.idom}
 * @public
 */

goog.module('DDMFieldTypesSidebar.incrementaldom');

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
function __deltemplate_s73_a6c11d4d(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $toolbar(opt_data, null, opt_ijData);
}
exports.__deltemplate_s73_a6c11d4d = __deltemplate_s73_a6c11d4d;
if (goog.DEBUG) {
  __deltemplate_s73_a6c11d4d.soyTemplateName = 'DDMFieldTypesSidebar.__deltemplate_s73_a6c11d4d';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), 'fieldTypes', 0, __deltemplate_s73_a6c11d4d);


/**
 * @param {Object<string, *>=} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s75_40ef180d(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s75_40ef180d = __deltemplate_s75_40ef180d;
if (goog.DEBUG) {
  __deltemplate_s75_40ef180d.soyTemplateName = 'DDMFieldTypesSidebar.__deltemplate_s75_40ef180d';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), 'fieldTypes', 0, __deltemplate_s75_40ef180d);


/**
 * @param {{
 *    description: (null|string|undefined),
 *    title: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $toolbar(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  soy.asserts.assertType(opt_data.description == null || (opt_data.description instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.description), 'description', opt_data.description, 'null|string|undefined');
  var description = /** @type {null|string|undefined} */ (opt_data.description);
  soy.asserts.assertType(opt_data.title == null || (opt_data.title instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.title), 'title', opt_data.title, 'null|string|undefined');
  var title = /** @type {null|string|undefined} */ (opt_data.title);
  if (title) {
    ie_open('h4', null, null,
        'class', 'form-builder-sidebar-title truncate-text',
        'title', title);
      var dyn9 = title;
      if (typeof dyn9 == 'function') dyn9(); else if (dyn9 != null) itext(dyn9);
    ie_close('h4');
  }
  if (description) {
    ie_open('h5', null, null,
        'class', 'form-builder-sidebar-description',
        'title', description);
      var dyn10 = description;
      if (typeof dyn10 == 'function') dyn10(); else if (dyn10 != null) itext(dyn10);
    ie_close('h5');
  }
}
exports.toolbar = $toolbar;
if (goog.DEBUG) {
  $toolbar.soyTemplateName = 'DDMFieldTypesSidebar.toolbar';
}


/**
 * @param {{
 *    fieldSets: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), id: string, name: string}>,
 *    fieldTypes: {basic: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>, customized: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>},
 *    strings: {basic: string, customized: string, element_set: string, elements: string},
 *    icons: (null|undefined|{angleDown: (!soydata.SanitizedHtml|string), angleRight: (!soydata.SanitizedHtml|string)})
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  var fieldSets = goog.asserts.assertArray(opt_data.fieldSets, "expected parameter 'fieldSets' of type list<[description: string, icon: html, id: string, name: string]>.");
  var fieldTypes = goog.asserts.assertObject(opt_data.fieldTypes, "expected parameter 'fieldTypes' of type [basic: list<[description: string, icon: html, label: string, name: string]>, customized: list<[description: string, icon: html, label: string, name: string]>].");
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [basic: string, customized: string, element_set: string, elements: string].");
  soy.asserts.assertType(opt_data.icons == null || goog.isObject(opt_data.icons), 'icons', opt_data.icons, 'null|undefined|{angleDown: (!soydata.SanitizedHtml|string), angleRight: (!soydata.SanitizedHtml|string)}');
  var icons = /** @type {null|undefined|{angleDown: (!soydata.SanitizedHtml|string), angleRight: (!soydata.SanitizedHtml|string)}} */ (opt_data.icons);
  ie_open('div', null, null,
      'class', 'sidebar-body');
    ie_open('nav', null, null,
        'class', 'navbar navbar-default navbar-no-collapse');
      ie_open('ul', null, null,
          'class', 'nav nav-tabs navbar-nav',
          'role', 'tablist');
        ie_open('li', null, null,
            'class', 'active yui3-widget tab tab-selected',
            'data-tab', 'field-types-content',
            'role', 'presentation');
          ie_open('a', null, null,
              'href', 'javascript:;',
              'class', 'tab-content tab-label',
              'role', 'tab');
            var dyn11 = strings.elements;
            if (typeof dyn11 == 'function') dyn11(); else if (dyn11 != null) itext(dyn11);
          ie_close('a');
        ie_close('li');
        ie_open('li', null, null,
            'class', 'yui3-widget tab tab-selected',
            'data-tab', 'field-types-fieldset',
            'role', 'presentation');
          ie_open('a', null, null,
              'href', 'javascript:;',
              'class', 'tab-content tab-label',
              'role', 'tab');
            var dyn12 = strings.element_set;
            if (typeof dyn12 == 'function') dyn12(); else if (dyn12 != null) itext(dyn12);
          ie_close('a');
        ie_close('li');
      ie_close('ul');
    ie_close('nav');
    ie_open('div', null, null,
        'class', 'form-builder-sidebar-list');
      ie_open('div', null, null,
          'class', 'field-types-content list-group');
        if (fieldTypes.basic) {
          $render_field_type_list_group({fieldTypes: fieldTypes.basic, iconClosed: icons.angleRight, iconOpen: icons.angleDown, title: strings.basic}, null, opt_ijData);
        }
        if (fieldTypes.customized) {
          $render_field_type_list_group({fieldTypes: fieldTypes.customized, iconClosed: icons.angleRight, iconOpen: icons.angleDown, title: strings.customized}, null, opt_ijData);
        }
      ie_close('div');
      ie_open('div', null, null,
          'class', 'field-types-fieldset hide list-group');
        if (fieldSets) {
          $render_field_set_list_group(opt_data, null, opt_ijData);
        }
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMFieldTypesSidebar.render';
}


/**
 * @param {{
 *    fieldTypes: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), label: string, name: string}>,
 *    iconClosed: (!soydata.SanitizedHtml|string),
 *    iconOpen: (!soydata.SanitizedHtml|string),
 *    title: string
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_field_type_list_group(opt_data, opt_ignored, opt_ijData) {
  var fieldTypes = goog.asserts.assertArray(opt_data.fieldTypes, "expected parameter 'fieldTypes' of type list<[description: string, icon: html, label: string, name: string]>.");
  soy.asserts.assertType((opt_data.iconClosed instanceof Function) || (opt_data.iconClosed instanceof soydata.UnsanitizedText) || goog.isString(opt_data.iconClosed), 'iconClosed', opt_data.iconClosed, 'Function');
  var iconClosed = /** @type {Function} */ (opt_data.iconClosed);
  soy.asserts.assertType((opt_data.iconOpen instanceof Function) || (opt_data.iconOpen instanceof soydata.UnsanitizedText) || goog.isString(opt_data.iconOpen), 'iconOpen', opt_data.iconOpen, 'Function');
  var iconOpen = /** @type {Function} */ (opt_data.iconOpen);
  soy.asserts.assertType(goog.isString(opt_data.title) || (opt_data.title instanceof goog.soy.data.SanitizedContent), 'title', opt_data.title, 'string|goog.soy.data.SanitizedContent');
  var title = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.title);
  ie_open('a', null, null,
      'class', 'list-group-header toggler-header-expanded list-group-item-action',
      'href', 'javascript:;');
    ie_open('h3', null, null,
        'class', 'list-group-header-title');
      var dyn13 = title;
      if (typeof dyn13 == 'function') dyn13(); else if (dyn13 != null) itext(dyn13);
    ie_close('h3');
    ie_open('span', null, null,
        'class', 'list-group-header-icon-open');
      iconOpen();
    ie_close('span');
    ie_open('span', null, null,
        'class', 'list-group-header-icon-closed');
      iconClosed();
    ie_close('span');
  ie_close('a');
  ie_open('div', null, null,
      'class', 'list-group-body');
    var fieldTypeList134 = fieldTypes;
    var fieldTypeListLen134 = fieldTypeList134.length;
    for (var fieldTypeIndex134 = 0; fieldTypeIndex134 < fieldTypeListLen134; fieldTypeIndex134++) {
      var fieldTypeData134 = fieldTypeList134[fieldTypeIndex134];
      ie_open('div', null, null,
          'class', 'list-group-item list-group-item-flex list-group-item-action lfr-ddm-form-builder-draggable-item lfr-ddm-form-builder-field-type-item',
          'data-field-type-name', fieldTypeData134.name);
        $render_field_template({description: fieldTypeData134.description, icon: fieldTypeData134.icon, name: fieldTypeData134.label}, null, opt_ijData);
      ie_close('div');
    }
  ie_close('div');
}
exports.render_field_type_list_group = $render_field_type_list_group;
if (goog.DEBUG) {
  $render_field_type_list_group.soyTemplateName = 'DDMFieldTypesSidebar.render_field_type_list_group';
}


/**
 * @param {{
 *    fieldSets: !Array<{description: string, icon: (!soydata.SanitizedHtml|string), id: string, name: string}>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_field_set_list_group(opt_data, opt_ignored, opt_ijData) {
  var fieldSets = goog.asserts.assertArray(opt_data.fieldSets, "expected parameter 'fieldSets' of type list<[description: string, icon: html, id: string, name: string]>.");
  ie_open('div', null, null,
      'class', 'list-group-body');
    var fieldSetList147 = fieldSets;
    var fieldSetListLen147 = fieldSetList147.length;
    for (var fieldSetIndex147 = 0; fieldSetIndex147 < fieldSetListLen147; fieldSetIndex147++) {
      var fieldSetData147 = fieldSetList147[fieldSetIndex147];
      ie_open('div', null, null,
          'class', 'list-group-item list-group-item-flex list-group-item-action lfr-ddm-form-builder-field-set-item lfr-ddm-form-builder-draggable-item',
          'data-field-set-id', fieldSetData147.id);
        $render_field_template({description: fieldSetData147.description, icon: fieldSetData147.icon, name: fieldSetData147.name}, null, opt_ijData);
      ie_close('div');
    }
  ie_close('div');
}
exports.render_field_set_list_group = $render_field_set_list_group;
if (goog.DEBUG) {
  $render_field_set_list_group.soyTemplateName = 'DDMFieldTypesSidebar.render_field_set_list_group';
}


/**
 * @param {{
 *    description: string,
 *    icon: (!soydata.SanitizedHtml|string),
 *    name: string
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_field_template(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.description) || (opt_data.description instanceof goog.soy.data.SanitizedContent), 'description', opt_data.description, 'string|goog.soy.data.SanitizedContent');
  var description = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.description);
  soy.asserts.assertType((opt_data.icon instanceof Function) || (opt_data.icon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.icon), 'icon', opt_data.icon, 'Function');
  var icon = /** @type {Function} */ (opt_data.icon);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  ie_open('div', null, null,
      'class', 'flex-col');
    ie_open('div', null, null,
        'class', 'sticker sticker-secondary');
      icon();
    ie_close('div');
  ie_close('div');
  ie_open('div', null, null,
      'class', 'flex-col flex-col-expand');
    ie_open('h4', null, null,
        'class', 'list-group-title');
      var dyn14 = name;
      if (typeof dyn14 == 'function') dyn14(); else if (dyn14 != null) itext(dyn14);
    ie_close('h4');
    ie_open('p', null, null,
        'class', 'list-group-subtitle text-truncate',
        'title', description);
      var dyn15 = description;
      if (typeof dyn15 == 'function') dyn15(); else if (dyn15 != null) itext(dyn15);
    ie_close('p');
  ie_close('div');
}
exports.render_field_template = $render_field_template;
if (goog.DEBUG) {
  $render_field_template.soyTemplateName = 'DDMFieldTypesSidebar.render_field_template';
}

exports.toolbar.params = ["description","title"];
exports.toolbar.types = {"description":"string","title":"string"};
exports.render.params = [];
exports.render.types = {};
exports.render_field_type_list_group.params = ["iconClosed","iconOpen","title"];
exports.render_field_type_list_group.types = {"iconClosed":"html","iconOpen":"html","title":"string"};
exports.render_field_set_list_group.params = [];
exports.render_field_set_list_group.types = {};
exports.render_field_template.params = ["description","icon","name"];
exports.render_field_template.types = {"description":"string","icon":"html","name":"string"};
templates = exports;
return exports;

});

class DDMFieldTypesSidebar extends Component {}
Soy.register(DDMFieldTypesSidebar, templates);
export { DDMFieldTypesSidebar, templates };
export default templates;
/* jshint ignore:end */
