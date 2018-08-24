/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from FormRenderer.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace FormRenderer.
 * @hassoydeltemplate {FormRenderer.formHeader.idom}
 * @hassoydeltemplate {FormRenderer.pages.idom}
 * @hassoydeltemplate {FormRenderer.wizard.idom}
 * @hassoydelcall {FormRenderer.formHeader.idom}
 * @hassoydelcall {FormRenderer.pages.idom}
 * @hassoydelcall {FormRenderer.wizard.idom}
 * @public
 */

goog.module('FormRenderer.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');

var $templateAlias1 = Soy.getTemplate('ClayActionsDropdown.incrementaldom', 'render');

var $templateAlias2 = Soy.getTemplate('PageRenderer.incrementaldom', 'render');


/**
 * @param {$render.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $render = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleChangePage = opt_data._handleChangePage;
  /** @type {*|null|undefined} */
  var _handleClickSettingsPage = opt_data._handleClickSettingsPage;
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleUpdatePage = opt_data._handleUpdatePage;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var modeRenderer = soy.asserts.assertType(opt_data.modeRenderer == null || (goog.isString(opt_data.modeRenderer) || opt_data.modeRenderer instanceof goog.soy.data.SanitizedContent), 'modeRenderer', opt_data.modeRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {?} */
  var pageSettingsItem = opt_data.pageSettingsItem;
  var contentRenderer__soy15 = modeRenderer != null ? modeRenderer : 'grid';
  var total__soy17 = (pages.length);
  soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.pages.idom'), contentRenderer__soy15, false)({_handleChangePage: _handleChangePage, _handleClickSettingsPage: _handleClickSettingsPage, _handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleUpdatePage: _handleUpdatePage, activePage: activePage, contentRenderer: contentRenderer__soy15, editable: editable, pages: pages, pageSettingsItem: pageSettingsItem, spritemap: spritemap}, opt_ijData);
};
exports.render = $render;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleChangePage: (*|null|undefined),
 *  _handleClickSettingsPage: (*|null|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleUpdatePage: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  editable: (boolean|null|undefined),
 *  modeRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  pages: (?|undefined),
 *  pageSettingsItem: (?|undefined),
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'FormRenderer.render';
}


/**
 * @param {__deltemplate__FormRenderer_pages_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__FormRenderer_pages_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleChangePage = opt_data._handleChangePage;
  /** @type {*|null|undefined} */
  var _handleClickSettingsPage = opt_data._handleClickSettingsPage;
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleUpdatePage = opt_data._handleUpdatePage;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {?} */
  var pageSettingsItem = opt_data.pageSettingsItem;
  /** @type {null|number|undefined} */
  var total = soy.asserts.assertType(opt_data.total == null || goog.isNumber(opt_data.total), 'total', opt_data.total, 'null|number|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'position-relative');
  incrementalDom.elementOpenEnd();
  soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.formHeader.idom'), '', false)({_handleChangePage: _handleChangePage, _handleClickSettingsPage: _handleClickSettingsPage, activePage: activePage, contentRenderer: contentRenderer, editable: editable, pages: pages, pageSettingsItem: pageSettingsItem, spritemap: spritemap}, opt_ijData);
  var page57List = pages;
  var page57ListLen = page57List.length;
  if (page57ListLen > 0) {
    for (var page57Index = 0; page57Index < page57ListLen; page57Index++) {
      var page57Data = page57List[page57Index];
      $templateAlias2({activePage: activePage, contentRenderer: contentRenderer, editable: editable, events: {deleteButtonClicked: _handleDeleteButtonClicked, duplicateButtonClicked: _handleDuplicateButtonClicked, updatePage: _handleUpdatePage}, page: page57Data, pageId: page57Index, spritemap: spritemap, total: total}, opt_ijData);
    }
  } else {
    incrementalDom.elementOpen('p');
    incrementalDom.text('Drag from sidebar and drop here');
    incrementalDom.elementClose('p');
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__FormRenderer_pages_grid = __deltemplate__FormRenderer_pages_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleChangePage: (*|null|undefined),
 *  _handleClickSettingsPage: (*|null|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleUpdatePage: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  pages: (?|undefined),
 *  pageSettingsItem: (?|undefined),
 *  total: (null|number|undefined),
 * }}
 */
__deltemplate__FormRenderer_pages_grid.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_pages_grid.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_pages_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.pages.idom'), 'grid', 0, __deltemplate__FormRenderer_pages_grid);


/**
 * @param {__deltemplate__FormRenderer_formHeader_.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__FormRenderer_formHeader_ = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleChangePage = opt_data._handleChangePage;
  /** @type {*|null|undefined} */
  var _handleClickSettingsPage = opt_data._handleClickSettingsPage;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {?} */
  var pageSettingsItem = opt_data.pageSettingsItem;
  /** @type {null|number|undefined} */
  var total = soy.asserts.assertType(opt_data.total == null || goog.isNumber(opt_data.total), 'total', opt_data.total, 'null|number|undefined');
  var multiple__soy84 = (pages.length) > 1;
  var classes__soy91 = '';
  classes__soy91 += 'ddm-form-header';
  classes__soy91 += multiple__soy84 ? ' pt-2 pb-3 mb-4 multiple' : '';
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', classes__soy91);
  incrementalDom.elementOpenEnd();
  if (multiple__soy84) {
    soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.wizard.idom'), '', false)({_handleChangePage: _handleChangePage, activePage: activePage, contentRenderer: contentRenderer, editable: editable, pages: pages, pageSettingsItem: pageSettingsItem, spritemap: spritemap}, opt_ijData);
  }
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'btn-group dmm-page-settings');
      incrementalDom.attr('role', 'group');
  incrementalDom.elementOpenEnd();
  $templateAlias1({items: pageSettingsItem, events: {itemClicked: _handleClickSettingsPage}, spritemap: spritemap}, opt_ijData);
  incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
};
exports.__deltemplate__FormRenderer_formHeader_ = __deltemplate__FormRenderer_formHeader_;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleChangePage: (*|null|undefined),
 *  _handleClickSettingsPage: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  pages: (?|undefined),
 *  pageSettingsItem: (?|undefined),
 *  total: (null|number|undefined),
 * }}
 */
__deltemplate__FormRenderer_formHeader_.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_formHeader_.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_formHeader_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.formHeader.idom'), '', 0, __deltemplate__FormRenderer_formHeader_);


/**
 * @param {__deltemplate__FormRenderer_wizard_.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__FormRenderer_wizard_ = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {*|null|undefined} */
  var _handleChangePage = opt_data._handleChangePage;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  incrementalDom.elementOpenStart('ol');
      incrementalDom.attr('class', 'multi-step-nav multi-step-nav-collapse-sm multi-step-indicator-label-top');
  incrementalDom.elementOpenEnd();
  var page119List = pages;
  var page119ListLen = page119List.length;
  for (var page119Index = 0; page119Index < page119ListLen; page119Index++) {
    var page119Data = page119List[page119Index];
    var title__soy122 = page119Data.title ? page119Data.title : 'Untitled Page';
    var pageId__soy124 = page119Index;
    var step__soy126 = pageId__soy124 + 1;
    var classes__soy137 = '';
    classes__soy137 += 'multi-step-item';
    classes__soy137 += step__soy126 != (pages.length) ? ' multi-step-item-expand' : '';
    classes__soy137 += pageId__soy124 == activePage ? ' active' : '';
    incrementalDom.elementOpenStart('li');
        incrementalDom.attr('class', classes__soy137);
        incrementalDom.attr('data-onclick', _handleChangePage);
        incrementalDom.attr('data-page-id', pageId__soy124);
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'multi-step-divider');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'multi-step-indicator');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'multi-step-indicator-label');
    incrementalDom.elementOpenEnd();
    soyIdom.print(title__soy122);
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('a');
        incrementalDom.attr('class', 'multi-step-icon');
        incrementalDom.attr('data-multi-step-icon', step__soy126);
        incrementalDom.attr('href', '#1');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('a');
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('li');
  }
  incrementalDom.elementClose('ol');
};
exports.__deltemplate__FormRenderer_wizard_ = __deltemplate__FormRenderer_wizard_;
/**
 * @typedef {{
 *  _handleChangePage: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  pages: (?|undefined),
 * }}
 */
__deltemplate__FormRenderer_wizard_.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_wizard_.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_wizard_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.wizard.idom'), '', 0, __deltemplate__FormRenderer_wizard_);


/**
 * @param {__deltemplate__FormRenderer_pages_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__FormRenderer_pages_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleUpdatePage = opt_data._handleUpdatePage;
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {null|number|undefined} */
  var total = soy.asserts.assertType(opt_data.total == null || goog.isNumber(opt_data.total), 'total', opt_data.total, 'null|number|undefined');
  var page161List = pages;
  var page161ListLen = page161List.length;
  for (var page161Index = 0; page161Index < page161ListLen; page161Index++) {
    var page161Data = page161List[page161Index];
    $templateAlias2({activePage: activePage, contentRenderer: contentRenderer, editable: editable, events: {deleteButtonClicked: _handleDeleteButtonClicked, duplicateButtonClicked: _handleDuplicateButtonClicked, updatePage: _handleUpdatePage}, page: page161Data, pageId: page161Index, spritemap: spritemap, total: total}, opt_ijData);
  }
};
exports.__deltemplate__FormRenderer_pages_list = __deltemplate__FormRenderer_pages_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleUpdatePage: (*|null|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  pages: (?|undefined),
 *  total: (null|number|undefined),
 * }}
 */
__deltemplate__FormRenderer_pages_list.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_pages_list.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_pages_list';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.pages.idom'), 'list', 0, __deltemplate__FormRenderer_pages_list);

exports.render.params = ["spritemap","_handleChangePage","_handleClickSettingsPage","_handleDeleteButtonClicked","_handleDuplicateButtonClicked","_handleUpdatePage","activePage","editable","modeRenderer","pages","pageSettingsItem"];
exports.render.types = {"spritemap":"string","_handleChangePage":"any","_handleClickSettingsPage":"any","_handleDeleteButtonClicked":"any","_handleDuplicateButtonClicked":"any","_handleUpdatePage":"any","activePage":"number","editable":"bool","modeRenderer":"string","pages":"?","pageSettingsItem":"?"};
templates = exports;
return exports;

});

class FormRenderer extends Component {}
Soy.register(FormRenderer, templates);
export { FormRenderer, templates };
export default templates;
/* jshint ignore:end */
