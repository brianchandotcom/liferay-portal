/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from field-types-sidebar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMFieldTypesSidebar.
 * @hassoydeltemplate {DDMSidebar.sidebar_body.idom}
 * @hassoydeltemplate {DDMSidebar.sidebar_toolbar.idom}
 * @public
 */

goog.module('DDMFieldTypesSidebar.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s745_a6c11d4d(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $toolbar(opt_data, null, opt_ijData);
}
exports.__deltemplate_s745_a6c11d4d = __deltemplate_s745_a6c11d4d;
if (goog.DEBUG) {
  __deltemplate_s745_a6c11d4d.soyTemplateName = 'DDMFieldTypesSidebar.__deltemplate_s745_a6c11d4d';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_toolbar.idom'), 'fieldTypes', 0, __deltemplate_s745_a6c11d4d);


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s749_40ef180d(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s749_40ef180d = __deltemplate_s749_40ef180d;
if (goog.DEBUG) {
  __deltemplate_s749_40ef180d.soyTemplateName = 'DDMFieldTypesSidebar.__deltemplate_s749_40ef180d';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMSidebar.sidebar_body.idom'), 'fieldTypes', 0, __deltemplate_s749_40ef180d);


/**
 * @param {{
 *  description: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  title: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $toolbar(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var description = soy.asserts.assertType(opt_data.description == null || (goog.isString(opt_data.description) || opt_data.description instanceof goog.soy.data.SanitizedContent), 'description', opt_data.description, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var title = soy.asserts.assertType(opt_data.title == null || (goog.isString(opt_data.title) || opt_data.title instanceof goog.soy.data.SanitizedContent), 'title', opt_data.title, '!goog.soy.data.SanitizedContent|null|string|undefined');
  if (title) {
    incrementalDom.elementOpenStart('h4');
        incrementalDom.attr('class', 'form-builder-sidebar-title truncate-text');
        incrementalDom.attr('title', title);
    incrementalDom.elementOpenEnd();
      soyIdom.print(title);
    incrementalDom.elementClose('h4');
  }
  if (description) {
    incrementalDom.elementOpenStart('h5');
        incrementalDom.attr('class', 'form-builder-sidebar-description');
        incrementalDom.attr('title', description);
    incrementalDom.elementOpenEnd();
      soyIdom.print(description);
    incrementalDom.elementClose('h5');
  }
}
exports.toolbar = $toolbar;
/**
 * @typedef {{
 *  description: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  title: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$toolbar.Params;
if (goog.DEBUG) {
  $toolbar.soyTemplateName = 'DDMFieldTypesSidebar.toolbar';
}


/**
 * @param {{
 *  fieldSets: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>,
 *  fieldTypes: {basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>},
 *  strings: {basic: (!goog.soy.data.SanitizedContent|string), customized: (!goog.soy.data.SanitizedContent|string), element_set: (!goog.soy.data.SanitizedContent|string), elements: (!goog.soy.data.SanitizedContent|string)},
 *  icons: (null|undefined|{angleDown: function(), angleRight: function()})
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>} */
  var fieldSets = soy.asserts.assertType(goog.isArray(opt_data.fieldSets), 'fieldSets', opt_data.fieldSets, '!Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}} */
  var fieldTypes = soy.asserts.assertType(goog.isObject(opt_data.fieldTypes), 'fieldTypes', opt_data.fieldTypes, '{basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>}');
  /** @type {{basic: (!goog.soy.data.SanitizedContent|string), customized: (!goog.soy.data.SanitizedContent|string), element_set: (!goog.soy.data.SanitizedContent|string), elements: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{basic: (!goog.soy.data.SanitizedContent|string), customized: (!goog.soy.data.SanitizedContent|string), element_set: (!goog.soy.data.SanitizedContent|string), elements: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {null|undefined|{angleDown: function(), angleRight: function()}} */
  var icons = soy.asserts.assertType(opt_data.icons == null || goog.isObject(opt_data.icons), 'icons', opt_data.icons, 'null|undefined|{angleDown: function(), angleRight: function()}');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'sidebar-body');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('nav');
        incrementalDom.attr('class', 'navbar navbar-default navbar-no-collapse');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'nav nav-tabs navbar-nav');
          incrementalDom.attr('role', 'tablist');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'active yui3-widget tab tab-selected');
            incrementalDom.attr('data-tab', 'field-types-content');
            incrementalDom.attr('role', 'presentation');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('a');
              incrementalDom.attr('href', 'javascript:;');
              incrementalDom.attr('class', 'tab-content tab-label');
              incrementalDom.attr('role', 'tab');
          incrementalDom.elementOpenEnd();
            soyIdom.print(strings.elements);
          incrementalDom.elementClose('a');
        incrementalDom.elementClose('li');
        incrementalDom.elementOpenStart('li');
            incrementalDom.attr('class', 'yui3-widget tab tab-selected');
            incrementalDom.attr('data-tab', 'field-types-fieldset');
            incrementalDom.attr('role', 'presentation');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('a');
              incrementalDom.attr('href', 'javascript:;');
              incrementalDom.attr('class', 'tab-content tab-label');
              incrementalDom.attr('role', 'tab');
          incrementalDom.elementOpenEnd();
            soyIdom.print(strings.element_set);
          incrementalDom.elementClose('a');
        incrementalDom.elementClose('li');
      incrementalDom.elementClose('ul');
    incrementalDom.elementClose('nav');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'form-builder-sidebar-list');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'field-types-content list-group');
      incrementalDom.elementOpenEnd();
        if (fieldTypes.basic) {
          $render_field_type_list_group({fieldTypes: fieldTypes.basic, iconClosed: icons.angleRight, iconOpen: icons.angleDown, title: strings.basic}, null, opt_ijData);
        }
        if (fieldTypes.customized) {
          $render_field_type_list_group({fieldTypes: fieldTypes.customized, iconClosed: icons.angleRight, iconOpen: icons.angleDown, title: strings.customized}, null, opt_ijData);
        }
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'field-types-fieldset hide list-group');
      incrementalDom.elementOpenEnd();
        if (fieldSets) {
          $render_field_set_list_group(opt_data, null, opt_ijData);
        }
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  fieldSets: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>,
 *  fieldTypes: {basic: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>, customized: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>},
 *  strings: {basic: (!goog.soy.data.SanitizedContent|string), customized: (!goog.soy.data.SanitizedContent|string), element_set: (!goog.soy.data.SanitizedContent|string), elements: (!goog.soy.data.SanitizedContent|string)},
 *  icons: (null|undefined|{angleDown: function(), angleRight: function()})
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMFieldTypesSidebar.render';
}


/**
 * @param {{
 *  fieldTypes: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>,
 *  iconClosed: function(),
 *  iconOpen: function(),
 *  title: (!goog.soy.data.SanitizedContent|string)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_field_type_list_group(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>} */
  var fieldTypes = soy.asserts.assertType(goog.isArray(opt_data.fieldTypes), 'fieldTypes', opt_data.fieldTypes, '!Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {function()} */
  var iconClosed = soy.asserts.assertType(goog.isFunction(opt_data.iconClosed), 'iconClosed', opt_data.iconClosed, 'function()');
  /** @type {function()} */
  var iconOpen = soy.asserts.assertType(goog.isFunction(opt_data.iconOpen), 'iconOpen', opt_data.iconOpen, 'function()');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var title = soy.asserts.assertType(goog.isString(opt_data.title) || opt_data.title instanceof goog.soy.data.SanitizedContent, 'title', opt_data.title, '!goog.soy.data.SanitizedContent|string');
  incrementalDom.elementOpenStart('a');
      incrementalDom.attr('class', 'list-group-header toggler-header-expanded list-group-item-action');
      incrementalDom.attr('href', 'javascript:;');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('h3');
        incrementalDom.attr('class', 'list-group-header-title');
    incrementalDom.elementOpenEnd();
      soyIdom.print(title);
    incrementalDom.elementClose('h3');
    incrementalDom.elementOpenStart('span');
        incrementalDom.attr('class', 'list-group-header-icon-open');
    incrementalDom.elementOpenEnd();
      iconOpen();
    incrementalDom.elementClose('span');
    incrementalDom.elementOpenStart('span');
        incrementalDom.attr('class', 'list-group-header-icon-closed');
    incrementalDom.elementOpenEnd();
      iconClosed();
    incrementalDom.elementClose('span');
  incrementalDom.elementClose('a');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'list-group-body');
  incrementalDom.elementOpenEnd();
    var fieldType832List = fieldTypes;
    var fieldType832ListLen = fieldType832List.length;
    for (var fieldType832Index = 0; fieldType832Index < fieldType832ListLen; fieldType832Index++) {
        var fieldType832Data = fieldType832List[fieldType832Index];
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'list-group-item list-group-item-flex list-group-item-action lfr-ddm-form-builder-draggable-item lfr-ddm-form-builder-field-type-item');
            incrementalDom.attr('data-field-type-name', fieldType832Data.name);
        incrementalDom.elementOpenEnd();
          $render_field_template({description: fieldType832Data.description, icon: fieldType832Data.icon, name: fieldType832Data.label}, null, opt_ijData);
        incrementalDom.elementClose('div');
      }
  incrementalDom.elementClose('div');
}
exports.render_field_type_list_group = $render_field_type_list_group;
/**
 * @typedef {{
 *  fieldTypes: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), label: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>,
 *  iconClosed: function(),
 *  iconOpen: function(),
 *  title: (!goog.soy.data.SanitizedContent|string)
 * }}
 */
$render_field_type_list_group.Params;
if (goog.DEBUG) {
  $render_field_type_list_group.soyTemplateName = 'DDMFieldTypesSidebar.render_field_type_list_group';
}


/**
 * @param {{
 *  fieldSets: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_field_set_list_group(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>} */
  var fieldSets = soy.asserts.assertType(goog.isArray(opt_data.fieldSets), 'fieldSets', opt_data.fieldSets, '!Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'list-group-body');
  incrementalDom.elementOpenEnd();
    var fieldSet847List = fieldSets;
    var fieldSet847ListLen = fieldSet847List.length;
    for (var fieldSet847Index = 0; fieldSet847Index < fieldSet847ListLen; fieldSet847Index++) {
        var fieldSet847Data = fieldSet847List[fieldSet847Index];
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'list-group-item list-group-item-flex list-group-item-action lfr-ddm-form-builder-field-set-item lfr-ddm-form-builder-draggable-item');
            incrementalDom.attr('data-field-set-id', fieldSet847Data.id);
        incrementalDom.elementOpenEnd();
          $render_field_template({description: fieldSet847Data.description, icon: fieldSet847Data.icon, name: fieldSet847Data.name}, null, opt_ijData);
        incrementalDom.elementClose('div');
      }
  incrementalDom.elementClose('div');
}
exports.render_field_set_list_group = $render_field_set_list_group;
/**
 * @typedef {{
 *  fieldSets: !Array<{description: (!goog.soy.data.SanitizedContent|string), icon: function(), id: (!goog.soy.data.SanitizedContent|string), name: (!goog.soy.data.SanitizedContent|string)}>
 * }}
 */
$render_field_set_list_group.Params;
if (goog.DEBUG) {
  $render_field_set_list_group.soyTemplateName = 'DDMFieldTypesSidebar.render_field_set_list_group';
}


/**
 * @param {{
 *  description: (!goog.soy.data.SanitizedContent|string),
 *  icon: function(),
 *  name: (!goog.soy.data.SanitizedContent|string)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render_field_template(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var description = soy.asserts.assertType(goog.isString(opt_data.description) || opt_data.description instanceof goog.soy.data.SanitizedContent, 'description', opt_data.description, '!goog.soy.data.SanitizedContent|string');
  /** @type {function()} */
  var icon = soy.asserts.assertType(goog.isFunction(opt_data.icon), 'icon', opt_data.icon, 'function()');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'flex-col');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'sticker sticker-secondary');
    incrementalDom.elementOpenEnd();
      icon();
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'flex-col flex-col-expand');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('h4');
        incrementalDom.attr('class', 'list-group-title');
    incrementalDom.elementOpenEnd();
      soyIdom.print(name);
    incrementalDom.elementClose('h4');
    incrementalDom.elementOpenStart('p');
        incrementalDom.attr('class', 'list-group-subtitle text-truncate');
        incrementalDom.attr('title', description);
    incrementalDom.elementOpenEnd();
      soyIdom.print(description);
    incrementalDom.elementClose('p');
  incrementalDom.elementClose('div');
}
exports.render_field_template = $render_field_template;
/**
 * @typedef {{
 *  description: (!goog.soy.data.SanitizedContent|string),
 *  icon: function(),
 *  name: (!goog.soy.data.SanitizedContent|string)
 * }}
 */
$render_field_template.Params;
if (goog.DEBUG) {
  $render_field_template.soyTemplateName = 'DDMFieldTypesSidebar.render_field_template';
}

exports.toolbar.params = ["description","title"];
exports.toolbar.types = {"description":"string","title":"string"};
exports.render.params = ["fieldSets","fieldTypes","strings","icons"];
exports.render.types = {"fieldSets":"list<[description: string, icon: html, id: string, name: string]>","fieldTypes":"[\n\t\t\tbasic: list<[description: string, icon: html, label: string, name: string]>,\n\t\t\tcustomized: list<[description: string, icon: html, label: string, name: string]>\n\t\t]","strings":"[basic: string, customized: string, elements: string, element_set: string]","icons":"[angleDown: html, angleRight: html]"};
exports.render_field_type_list_group.params = ["fieldTypes","iconClosed","iconOpen","title"];
exports.render_field_type_list_group.types = {"fieldTypes":"list<[description: string, icon: html, label: string, name: string]>","iconClosed":"html","iconOpen":"html","title":"string"};
exports.render_field_set_list_group.params = ["fieldSets"];
exports.render_field_set_list_group.types = {"fieldSets":"list<[description: string, icon: html, id: string, name: string]>"};
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
