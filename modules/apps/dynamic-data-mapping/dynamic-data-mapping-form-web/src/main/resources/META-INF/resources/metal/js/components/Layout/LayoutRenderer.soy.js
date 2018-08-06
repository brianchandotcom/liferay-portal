/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from LayoutRenderer.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace LayoutRenderer.
 * @hassoydeltemplate {LayoutRenderer.RegisterFieldType.idom}
 * @hassoydeltemplate {LayoutRenderer.column.idom}
 * @hassoydeltemplate {LayoutRenderer.page.idom}
 * @hassoydeltemplate {LayoutRenderer.pages.idom}
 * @hassoydeltemplate {LayoutRenderer.row.idom}
 * @hassoydelcall {LayoutRenderer.RegisterFieldType.idom}
 * @hassoydelcall {LayoutRenderer.column.idom}
 * @hassoydelcall {LayoutRenderer.page.idom}
 * @hassoydelcall {LayoutRenderer.pages.idom}
 * @hassoydelcall {LayoutRenderer.row.idom}
 * @public
 */

goog.module('LayoutRenderer.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');

var $templateAlias1 = Soy.getTemplate('ClayButton.incrementaldom', 'render');


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
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var modeRenderer = soy.asserts.assertType(opt_data.modeRenderer == null || (goog.isString(opt_data.modeRenderer) || opt_data.modeRenderer instanceof goog.soy.data.SanitizedContent), 'modeRenderer', opt_data.modeRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  var contentRenderer__soy13 = modeRenderer != null ? modeRenderer : 'grid';
  soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.pages.idom'), contentRenderer__soy13, false)({_handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleFieldChange: _handleFieldChange, _handleFocusSelectField: _handleFocusSelectField, _handleOnClickResize: _handleOnClickResize, activePage: activePage, contentRenderer: contentRenderer__soy13, editable: editable, pages: pages, spritemap: spritemap}, opt_ijData);
};
exports.render = $render;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  editable: (boolean|null|undefined),
 *  modeRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  pages: (?|undefined),
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'LayoutRenderer.render';
}


/**
 * @param {__deltemplate__LayoutRenderer_pages_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_pages_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  var page37List = pages;
  var page37ListLen = page37List.length;
  if (page37ListLen > 0) {
    for (var page37Index = 0; page37Index < page37ListLen; page37Index++) {
      var page37Data = page37List[page37Index];
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'form-builder-layout');
      incrementalDom.elementOpenEnd();
      soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.page.idom'), contentRenderer, false)({_handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleFieldChange: _handleFieldChange, _handleFocusSelectField: _handleFocusSelectField, _handleOnClickResize: _handleOnClickResize, contentRenderer: contentRenderer, editable: editable, page: page37Data, pageId: page37Index, spritemap: spritemap}, opt_ijData);
      incrementalDom.elementClose('div');
    }
  } else {
    incrementalDom.elementOpen('p');
    incrementalDom.text('Drag from sidebar and drop here');
    incrementalDom.elementClose('p');
  }
};
exports.__deltemplate__LayoutRenderer_pages_grid = __deltemplate__LayoutRenderer_pages_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  pages: (?|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_pages_grid.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_pages_grid.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_pages_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.pages.idom'), 'grid', 0, __deltemplate__LayoutRenderer_pages_grid);


/**
 * @param {__deltemplate__LayoutRenderer_pages_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_pages_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  var page65List = pages;
  var page65ListLen = page65List.length;
  for (var page65Index = 0; page65Index < page65ListLen; page65Index++) {
    var page65Data = page65List[page65Index];
    var classes__soy73 = '';
    classes__soy73 += 'fade tab-pane';
    classes__soy73 += activePage == page65Index ? ' active show' : '';
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('aria-labelledby', 'sidebarLightFormsFieldTab');
        incrementalDom.attr('class', classes__soy73);
        incrementalDom.attr('id', 'sidebarLightDetails');
        incrementalDom.attr('role', 'tabpanel');
    incrementalDom.elementOpenEnd();
    soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.page.idom'), contentRenderer, false)({_handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleFieldChange: _handleFieldChange, _handleFocusSelectField: _handleFocusSelectField, _handleOnClickResize: _handleOnClickResize, contentRenderer: contentRenderer, editable: editable, page: page65Data, pageId: page65Index, spritemap: spritemap}, opt_ijData);
    incrementalDom.elementClose('div');
  }
};
exports.__deltemplate__LayoutRenderer_pages_list = __deltemplate__LayoutRenderer_pages_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  pages: (?|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_pages_list.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_pages_list.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_pages_list';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.pages.idom'), 'list', 0, __deltemplate__LayoutRenderer_pages_list);


/**
 * @param {__deltemplate__LayoutRenderer_page_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_page_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  var row100List = page.rows;
  var row100ListLen = row100List.length;
  for (var row100Index = 0; row100Index < row100ListLen; row100Index++) {
    var row100Data = row100List[row100Index];
    soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.row.idom'), 'grid', false)({_handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleFieldChange: _handleFieldChange, _handleFocusSelectField: _handleFocusSelectField, _handleOnClickResize: _handleOnClickResize, contentRenderer: contentRenderer, editable: editable, indexRow: row100Index, pageId: pageId, row: row100Data, spritemap: spritemap}, opt_ijData);
  }
};
exports.__deltemplate__LayoutRenderer_page_list = __deltemplate__LayoutRenderer_page_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_page_list.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_page_list.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_page_list';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.page.idom'), 'list', 0, __deltemplate__LayoutRenderer_page_list);


/**
 * @param {__deltemplate__LayoutRenderer_page_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_page_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'layout-page');
  incrementalDom.elementOpenEnd();
  var row128List = page.rows;
  var row128ListLen = row128List.length;
  for (var row128Index = 0; row128Index < row128ListLen; row128Index++) {
    var row128Data = row128List[row128Index];
    if (row128Index == 0 && editable) {
      $fieldEmpty({isRow: true, pageId: pageId, row: 0, size: 12}, opt_ijData);
    }
    soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.row.idom'), contentRenderer, false)({_handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleFieldChange: _handleFieldChange, _handleFocusSelectField: _handleFocusSelectField, _handleOnClickResize: _handleOnClickResize, contentRenderer: contentRenderer, editable: editable, indexRow: row128Index, pageId: pageId, row: row128Data, spritemap: spritemap}, opt_ijData);
    if (editable) {
      $fieldEmpty({isRow: true, pageId: pageId, row: row128Index + 1, size: 12}, opt_ijData);
    }
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__LayoutRenderer_page_grid = __deltemplate__LayoutRenderer_page_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_page_grid.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_page_grid.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_page_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.page.idom'), 'grid', 0, __deltemplate__LayoutRenderer_page_grid);


/**
 * @param {__deltemplate__LayoutRenderer_row_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_row_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var indexRow = soy.asserts.assertType(opt_data.indexRow == null || goog.isNumber(opt_data.indexRow), 'indexRow', opt_data.indexRow, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {?} */
  var row = opt_data.row;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'row');
  incrementalDom.elementOpenEnd();
  var column177List = row.columns;
  var column177ListLen = column177List.length;
  for (var column177Index = 0; column177Index < column177ListLen; column177Index++) {
    var column177Data = column177List[column177Index];
    soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.column.idom'), contentRenderer, false)({_handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleFieldChange: _handleFieldChange, _handleFocusSelectField: _handleFocusSelectField, _handleOnClickResize: _handleOnClickResize, column: column177Data, editable: editable, indexColumn: column177Index, indexRow: indexRow, pageId: pageId, spritemap: spritemap}, opt_ijData);
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__LayoutRenderer_row_grid = __deltemplate__LayoutRenderer_row_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  indexRow: (null|number|undefined),
 *  pageId: (null|number|undefined),
 *  row: (?|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_row_grid.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_row_grid.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_row_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.row.idom'), 'grid', 0, __deltemplate__LayoutRenderer_row_grid);


/**
 * @param {__deltemplate__LayoutRenderer_column_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_column_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {?} */
  var column = opt_data.column;
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var indexColumn = soy.asserts.assertType(opt_data.indexColumn == null || goog.isNumber(opt_data.indexColumn), 'indexColumn', opt_data.indexColumn, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var indexRow = soy.asserts.assertType(opt_data.indexRow == null || goog.isNumber(opt_data.indexRow), 'indexRow', opt_data.indexRow, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  if ((column.fields.length) != 0) {
    var attributes__soy211 = function() {
      incrementalDom.attr('class', 'col-md-' + column.size);
    };
    incrementalDom.elementOpenStart('div');
        attributes__soy211();
    incrementalDom.elementOpenEnd();
    var field215List = column.fields;
    var field215ListLen = field215List.length;
    for (var field215Index = 0; field215Index < field215ListLen; field215Index++) {
      var field215Data = field215List[field215Index];
      var $tmp = field215Data.type;
      var fieldType__soy218 = $tmp != null ? $tmp : 'empty';
      soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.RegisterFieldType.idom'), fieldType__soy218, true)(soy.$$assignDefaults({editable: editable, events: {fieldEdit: _handleFieldChange}, spritemap: spritemap}, field215Data), opt_ijData);
    }
    incrementalDom.elementClose('div');
  }
};
exports.__deltemplate__LayoutRenderer_column_list = __deltemplate__LayoutRenderer_column_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  column: (?|undefined),
 *  editable: (boolean|null|undefined),
 *  indexColumn: (null|number|undefined),
 *  indexRow: (null|number|undefined),
 *  pageId: (null|number|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_column_list.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_column_list.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_column_list';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.column.idom'), 'list', 0, __deltemplate__LayoutRenderer_column_list);


/**
 * @param {__deltemplate__LayoutRenderer_column_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_column_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChange = opt_data._handleFieldChange;
  /** @type {*|null|undefined} */
  var _handleFocusSelectField = opt_data._handleFocusSelectField;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {?} */
  var column = opt_data.column;
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var indexColumn = soy.asserts.assertType(opt_data.indexColumn == null || goog.isNumber(opt_data.indexColumn), 'indexColumn', opt_data.indexColumn, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var indexRow = soy.asserts.assertType(opt_data.indexRow == null || goog.isNumber(opt_data.indexRow), 'indexRow', opt_data.indexRow, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  if ((column.fields.length) != 0) {
    var attributes__soy263 = function() {
      incrementalDom.attr('class', 'col-md-' + column.size + ' col-ddm');
      if (indexRow != 'undefined') {
        incrementalDom.attr('data-ddm-field-row', indexRow);
      }
      if (pageId != 'undefined') {
        incrementalDom.attr('data-ddm-field-page', pageId);
      }
      if (indexColumn != 'undefined') {
        incrementalDom.attr('data-ddm-field-column', indexColumn);
      }
    };
    incrementalDom.elementOpenStart('div');
        attributes__soy263();
    incrementalDom.elementOpenEnd();
    var content__soy280 = function() {
      var field268List = column.fields;
      var field268ListLen = field268List.length;
      for (var field268Index = 0; field268Index < field268ListLen; field268Index++) {
        var field268Data = field268List[field268Index];
        var fieldType__soy271 = field268Data.type;
        soy.$$getDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.RegisterFieldType.idom'), fieldType__soy271, true)(soy.$$assignDefaults({editable: editable, events: {fieldEdit: _handleFieldChange}, ref: 'field', spritemap: spritemap}, field268Data), opt_ijData);
      }
    };
    if (editable) {
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'ddm-field-container');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'ddm-resize-handle-left');
          incrementalDom.attr('data-onclick', _handleOnClickResize);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('div');
      var attributesDrag__soy290 = function() {
        incrementalDom.attr('class', 'ddm-drag');
        incrementalDom.attr('data-onclick', _handleFocusSelectField);
      };
      incrementalDom.elementOpenStart('div');
          attributesDrag__soy290();
      incrementalDom.elementOpenEnd();
      content__soy280();
      incrementalDom.elementClose('div');
      $fieldActions({_handleDeleteButtonClicked: _handleDeleteButtonClicked, spritemap: spritemap}, opt_ijData);
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'ddm-resize-handle-right');
          incrementalDom.attr('data-onclick', _handleOnClickResize);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    } else {
      content__soy280();
    }
    incrementalDom.elementClose('div');
  } else if (editable) {
    $fieldEmpty({column: indexColumn, pageId: pageId, row: indexRow, size: column.size}, opt_ijData);
  }
};
exports.__deltemplate__LayoutRenderer_column_grid = __deltemplate__LayoutRenderer_column_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleFieldChange: (*|null|undefined),
 *  _handleFocusSelectField: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  column: (?|undefined),
 *  editable: (boolean|null|undefined),
 *  indexColumn: (null|number|undefined),
 *  indexRow: (null|number|undefined),
 *  pageId: (null|number|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_column_grid.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_column_grid.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_column_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.column.idom'), 'grid', 0, __deltemplate__LayoutRenderer_column_grid);


/**
 * @param {$fieldActions.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $fieldActions = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'ddm-field-actions-container');
  incrementalDom.elementOpenEnd();
  $templateAlias1({style: 'secondary', size: 'sm', spritemap: spritemap, icon: 'paste', disabled: true, monospaced: true}, opt_ijData);
  $templateAlias1({events: {click: _handleDeleteButtonClicked}, size: 'sm', style: 'secondary', spritemap: spritemap, icon: 'trash', monospaced: true}, opt_ijData);
  incrementalDom.elementClose('div');
};
exports.fieldActions = $fieldActions;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 * }}
 */
$fieldActions.Params;
if (goog.DEBUG) {
  $fieldActions.soyTemplateName = 'LayoutRenderer.fieldActions';
}


/**
 * @param {$fieldEmpty.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $fieldEmpty = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var size = soy.asserts.assertType(goog.isNumber(opt_data.size), 'size', opt_data.size, 'number');
  /** @type {null|number|undefined} */
  var column = soy.asserts.assertType(opt_data.column == null || goog.isNumber(opt_data.column), 'column', opt_data.column, 'null|number|undefined');
  /** @type {boolean|null|undefined} */
  var isRow = soy.asserts.assertType(opt_data.isRow == null || (goog.isBoolean(opt_data.isRow) || opt_data.isRow === 1 || opt_data.isRow === 0), 'isRow', opt_data.isRow, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var row = soy.asserts.assertType(opt_data.row == null || goog.isNumber(opt_data.row), 'row', opt_data.row, 'null|number|undefined');
  var attributes__soy365 = function() {
    incrementalDom.attr('class', 'col-md-' + size);
    if (row != 'undefined') {
      incrementalDom.attr('data-ddm-field-row', row);
    }
    if (pageId != 'undefined') {
      incrementalDom.attr('data-ddm-field-page', pageId);
    }
    if (column != 'undefined') {
      incrementalDom.attr('data-ddm-field-column', column);
    }
  };
  var content__soy370 = function() {
    incrementalDom.elementOpenStart('div');
        attributes__soy365();
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'ddm-target col-empty');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  };
  if (isRow) {
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'row');
    incrementalDom.elementOpenEnd();
    content__soy370();
    incrementalDom.elementClose('div');
  } else {
    content__soy370();
  }
};
exports.fieldEmpty = $fieldEmpty;
/**
 * @typedef {{
 *  size: number,
 *  column: (null|number|undefined),
 *  isRow: (boolean|null|undefined),
 *  pageId: (null|number|undefined),
 *  row: (null|number|undefined),
 * }}
 */
$fieldEmpty.Params;
if (goog.DEBUG) {
  $fieldEmpty.soyTemplateName = 'LayoutRenderer.fieldEmpty';
}


/**
 * @param {__deltemplate__LayoutRenderer_RegisterFieldType_.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__LayoutRenderer_RegisterFieldType_ = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var field = opt_data.field;
  if (editable) {
    incrementalDom.elementOpen('div');
    incrementalDom.text('Field doesn\'t exist.');
    incrementalDom.elementClose('div');
  }
};
exports.__deltemplate__LayoutRenderer_RegisterFieldType_ = __deltemplate__LayoutRenderer_RegisterFieldType_;
/**
 * @typedef {{
 *  editable: (boolean|null|undefined),
 *  field: (?|undefined),
 * }}
 */
__deltemplate__LayoutRenderer_RegisterFieldType_.Params;
if (goog.DEBUG) {
  __deltemplate__LayoutRenderer_RegisterFieldType_.soyTemplateName = 'LayoutRenderer.__deltemplate__LayoutRenderer_RegisterFieldType_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('LayoutRenderer.RegisterFieldType.idom'), '', 0, __deltemplate__LayoutRenderer_RegisterFieldType_);

exports.render.params = ["spritemap","_handleDeleteButtonClicked","_handleFieldChange","_handleFocusSelectField","_handleOnClickResize","activePage","editable","modeRenderer","pages"];
exports.render.types = {"spritemap":"string","_handleDeleteButtonClicked":"any","_handleFieldChange":"any","_handleFocusSelectField":"any","_handleOnClickResize":"any","activePage":"number","editable":"bool","modeRenderer":"string","pages":"?"};
exports.fieldActions.params = ["spritemap","_handleDeleteButtonClicked"];
exports.fieldActions.types = {"spritemap":"string","_handleDeleteButtonClicked":"any"};
exports.fieldEmpty.params = ["size","column","isRow","pageId","row"];
exports.fieldEmpty.types = {"size":"number","column":"number","isRow":"bool","pageId":"number","row":"number"};
templates = exports;
return exports;

});

class LayoutRenderer extends Component {}
Soy.register(LayoutRenderer, templates);
export { LayoutRenderer, templates };
export default templates;
/* jshint ignore:end */
