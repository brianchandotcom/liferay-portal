/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from PageRenderer.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace PageRenderer.
 * @hassoydeltemplate {PageRenderer.RegisterFieldType.idom}
 * @hassoydeltemplate {PageRenderer.column.idom}
 * @hassoydeltemplate {PageRenderer.container.idom}
 * @hassoydeltemplate {PageRenderer.page.idom}
 * @hassoydeltemplate {PageRenderer.row.idom}
 * @hassoydelcall {PageRenderer.RegisterFieldType.idom}
 * @hassoydelcall {PageRenderer.column.idom}
 * @hassoydelcall {PageRenderer.container.idom}
 * @hassoydelcall {PageRenderer.page.idom}
 * @hassoydelcall {PageRenderer.row.idom}
 * @public
 */

goog.module('PageRenderer.incrementaldom');

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
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var descriptionPlaceholder = soy.asserts.assertType(opt_data.descriptionPlaceholder == null || (goog.isString(opt_data.descriptionPlaceholder) || opt_data.descriptionPlaceholder instanceof goog.soy.data.SanitizedContent), 'descriptionPlaceholder', opt_data.descriptionPlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titlePlaceholder = soy.asserts.assertType(opt_data.titlePlaceholder == null || (goog.isString(opt_data.titlePlaceholder) || opt_data.titlePlaceholder instanceof goog.soy.data.SanitizedContent), 'titlePlaceholder', opt_data.titlePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {*|null|undefined} */
  var _handleChangePageDescription = opt_data._handleChangePageDescription;
  /** @type {*|null|undefined} */
  var _handleChangePageTitle = opt_data._handleChangePageTitle;
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChanged = opt_data._handleFieldChanged;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.container.idom'), contentRenderer, false)({activePage: activePage, contentRenderer: contentRenderer, descriptionPlaceholder: descriptionPlaceholder, editable: editable, page: page, pageId: pageId, spritemap: spritemap, titlePlaceholder: titlePlaceholder, _handleChangePageDescription: _handleChangePageDescription, _handleChangePageTitle: _handleChangePageTitle, _handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldChanged: _handleFieldChanged, _handleSelectFieldFocused: _handleSelectFieldFocused, _handleOnClickResize: _handleOnClickResize}, opt_ijData);
};
exports.render = $render;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  descriptionPlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  titlePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  _handleChangePageDescription: (*|null|undefined),
 *  _handleChangePageTitle: (*|null|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldChanged: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'PageRenderer.render';
}


/**
 * @param {__deltemplate__PageRenderer_container_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_container_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var descriptionPlaceholder = soy.asserts.assertType(opt_data.descriptionPlaceholder == null || (goog.isString(opt_data.descriptionPlaceholder) || opt_data.descriptionPlaceholder instanceof goog.soy.data.SanitizedContent), 'descriptionPlaceholder', opt_data.descriptionPlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titlePlaceholder = soy.asserts.assertType(opt_data.titlePlaceholder == null || (goog.isString(opt_data.titlePlaceholder) || opt_data.titlePlaceholder instanceof goog.soy.data.SanitizedContent), 'titlePlaceholder', opt_data.titlePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {*|null|undefined} */
  var _handleChangePageDescription = opt_data._handleChangePageDescription;
  /** @type {*|null|undefined} */
  var _handleChangePageTitle = opt_data._handleChangePageTitle;
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChanged = opt_data._handleFieldChanged;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  incrementalDom.elementOpen('div');
  if (activePage == pageId) {
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'dmm-form-page pr-3 pl-3');
    incrementalDom.elementOpenEnd();
    $form({contentRenderer: contentRenderer, descriptionPlaceholder: descriptionPlaceholder, editable: editable, page: page, pageId: pageId, titlePlaceholder: titlePlaceholder, _handleChangePageDescription: _handleChangePageDescription, _handleChangePageTitle: _handleChangePageTitle}, opt_ijData);
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'form-builder-layout');
    incrementalDom.elementOpenEnd();
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.page.idom'), contentRenderer, false)({contentRenderer: contentRenderer, editable: editable, page: page, pageId: pageId, spritemap: spritemap, _handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldChanged: _handleFieldChanged, _handleSelectFieldFocused: _handleSelectFieldFocused, _handleOnClickResize: _handleOnClickResize}, opt_ijData);
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_container_grid = __deltemplate__PageRenderer_container_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  descriptionPlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  titlePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  _handleChangePageDescription: (*|null|undefined),
 *  _handleChangePageTitle: (*|null|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldChanged: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 * }}
 */
__deltemplate__PageRenderer_container_grid.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_container_grid.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_container_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.container.idom'), 'grid', 0, __deltemplate__PageRenderer_container_grid);


/**
 * @param {__deltemplate__PageRenderer_container_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_container_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  var classes__soy359 = '';
  classes__soy359 += 'fade tab-pane';
  classes__soy359 += activePage == pageId ? ' active show' : '';
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('aria-labelledby', 'sidebarLightFormsFieldTab');
      incrementalDom.attr('class', classes__soy359);
      incrementalDom.attr('id', 'sidebarLightDetails');
      incrementalDom.attr('role', 'tabpanel');
  incrementalDom.elementOpenEnd();
  soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.page.idom'), contentRenderer, false)({contentRenderer: contentRenderer, editable: editable, page: page, pageId: pageId, spritemap: spritemap}, opt_ijData);
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_container_list = __deltemplate__PageRenderer_container_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 * }}
 */
__deltemplate__PageRenderer_container_list.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_container_list.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_container_list';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.container.idom'), 'list', 0, __deltemplate__PageRenderer_container_list);


/**
 * @param {$form.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $form = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var descriptionPlaceholder = soy.asserts.assertType(opt_data.descriptionPlaceholder == null || (goog.isString(opt_data.descriptionPlaceholder) || opt_data.descriptionPlaceholder instanceof goog.soy.data.SanitizedContent), 'descriptionPlaceholder', opt_data.descriptionPlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titlePlaceholder = soy.asserts.assertType(opt_data.titlePlaceholder == null || (goog.isString(opt_data.titlePlaceholder) || opt_data.titlePlaceholder instanceof goog.soy.data.SanitizedContent), 'titlePlaceholder', opt_data.titlePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {*|null|undefined} */
  var _handleChangePageDescription = opt_data._handleChangePageDescription;
  /** @type {*|null|undefined} */
  var _handleChangePageTitle = opt_data._handleChangePageTitle;
  var pageTitleAttributes__soy387 = function() {
    incrementalDom.attr('class', 'form-builder-page-header-title form-control');
    incrementalDom.attr('data-onkeyup', _handleChangePageTitle);
    incrementalDom.attr('data-page-id', pageId);
    incrementalDom.attr('maxlength', '120');
    incrementalDom.attr('value', page.title);
    incrementalDom.attr('placeholder', titlePlaceholder);
  };
  var pageDescriptionAttributes__soy398 = function() {
    incrementalDom.attr('class', 'form-builder-page-header-description form-control');
    incrementalDom.attr('data-onkeyup', _handleChangePageDescription);
    incrementalDom.attr('data-page-id', pageId);
    incrementalDom.attr('maxlength', '120');
    incrementalDom.attr('value', page.description);
    incrementalDom.attr('placeholder', descriptionPlaceholder);
  };
  incrementalDom.elementOpenStart('textarea');
      pageTitleAttributes__soy387();
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('textarea');
  incrementalDom.elementOpenStart('textarea');
      pageDescriptionAttributes__soy398();
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('textarea');
};
exports.form = $form;
/**
 * @typedef {{
 *  descriptionPlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  titlePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  _handleChangePageDescription: (*|null|undefined),
 *  _handleChangePageTitle: (*|null|undefined),
 * }}
 */
$form.Params;
if (goog.DEBUG) {
  $form.soyTemplateName = 'PageRenderer.form';
}


/**
 * @param {__deltemplate__PageRenderer_page_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_page_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChanged = opt_data._handleFieldChanged;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  var row416List = page.rows;
  var row416ListLen = row416List.length;
  for (var row416Index = 0; row416Index < row416ListLen; row416Index++) {
    var row416Data = row416List[row416Index];
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.row.idom'), 'grid', false)({contentRenderer: contentRenderer, editable: editable, rowIndex: row416Index, pageId: pageId, row: row416Data, spritemap: spritemap, _handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldChanged: _handleFieldChanged, _handleSelectFieldFocused: _handleSelectFieldFocused, _handleOnClickResize: _handleOnClickResize}, opt_ijData);
  }
};
exports.__deltemplate__PageRenderer_page_list = __deltemplate__PageRenderer_page_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldChanged: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 * }}
 */
__deltemplate__PageRenderer_page_list.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_page_list.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_page_list';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.page.idom'), 'list', 0, __deltemplate__PageRenderer_page_list);


/**
 * @param {__deltemplate__PageRenderer_page_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_page_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChanged = opt_data._handleFieldChanged;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'layout-page');
  incrementalDom.elementOpenEnd();
  var row446List = page.rows;
  var row446ListLen = row446List.length;
  for (var row446Index = 0; row446Index < row446ListLen; row446Index++) {
    var row446Data = row446List[row446Index];
    if (row446Index == 0 && editable) {
      $fieldEmpty({isRow: true, pageId: pageId, row: 0, size: 12}, opt_ijData);
    }
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.row.idom'), contentRenderer, false)({contentRenderer: contentRenderer, editable: editable, rowIndex: row446Index, pageId: pageId, row: row446Data, spritemap: spritemap, _handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldChanged: _handleFieldChanged, _handleSelectFieldFocused: _handleSelectFieldFocused, _handleOnClickResize: _handleOnClickResize}, opt_ijData);
    if (editable) {
      $fieldEmpty({isRow: true, pageId: pageId, row: row446Index + 1, size: 12}, opt_ijData);
    }
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_page_grid = __deltemplate__PageRenderer_page_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldChanged: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 * }}
 */
__deltemplate__PageRenderer_page_grid.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_page_grid.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_page_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.page.idom'), 'grid', 0, __deltemplate__PageRenderer_page_grid);


/**
 * @param {__deltemplate__PageRenderer_row_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_row_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var rowIndex = soy.asserts.assertType(opt_data.rowIndex == null || goog.isNumber(opt_data.rowIndex), 'rowIndex', opt_data.rowIndex, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {?} */
  var row = opt_data.row;
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChanged = opt_data._handleFieldChanged;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'row');
  incrementalDom.elementOpenEnd();
  var column497List = row.columns;
  var column497ListLen = column497List.length;
  for (var column497Index = 0; column497Index < column497ListLen; column497Index++) {
    var column497Data = column497List[column497Index];
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.column.idom'), contentRenderer, false)({column: column497Data, editable: editable, columnIndex: column497Index, rowIndex: rowIndex, pageId: pageId, spritemap: spritemap, _handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldChanged: _handleFieldChanged, _handleSelectFieldFocused: _handleSelectFieldFocused, _handleOnClickResize: _handleOnClickResize}, opt_ijData);
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_row_grid = __deltemplate__PageRenderer_row_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  rowIndex: (null|number|undefined),
 *  pageId: (null|number|undefined),
 *  row: (?|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldChanged: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 * }}
 */
__deltemplate__PageRenderer_row_grid.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_row_grid.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_row_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.row.idom'), 'grid', 0, __deltemplate__PageRenderer_row_grid);


/**
 * @param {__deltemplate__PageRenderer_column_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_column_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {?} */
  var column = opt_data.column;
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var columnIndex = soy.asserts.assertType(opt_data.columnIndex == null || goog.isNumber(opt_data.columnIndex), 'columnIndex', opt_data.columnIndex, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var rowIndex = soy.asserts.assertType(opt_data.rowIndex == null || goog.isNumber(opt_data.rowIndex), 'rowIndex', opt_data.rowIndex, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {*|null|undefined} */
  var _handleFieldChanged = opt_data._handleFieldChanged;
  if ((column.fields.length) != 0) {
    var attributes__soy529 = function() {
      incrementalDom.attr('class', 'col-md-' + column.size);
    };
    incrementalDom.elementOpenStart('div');
        attributes__soy529();
    incrementalDom.elementOpenEnd();
    var field533List = column.fields;
    var field533ListLen = field533List.length;
    for (var field533Index = 0; field533Index < field533ListLen; field533Index++) {
      var field533Data = field533List[field533Index];
      var $tmp = field533Data.type;
      var fieldType__soy536 = $tmp != null ? $tmp : 'empty';
      soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), fieldType__soy536, true)(soy.$$assignDefaults({editable: editable, events: {fieldEdited: _handleFieldChanged}, spritemap: spritemap}, field533Data), opt_ijData);
    }
    incrementalDom.elementClose('div');
  }
};
exports.__deltemplate__PageRenderer_column_list = __deltemplate__PageRenderer_column_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  column: (?|undefined),
 *  editable: (boolean|null|undefined),
 *  columnIndex: (null|number|undefined),
 *  rowIndex: (null|number|undefined),
 *  pageId: (null|number|undefined),
 *  _handleFieldChanged: (*|null|undefined),
 * }}
 */
__deltemplate__PageRenderer_column_list.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_column_list.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_column_list';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.column.idom'), 'list', 0, __deltemplate__PageRenderer_column_list);


/**
 * @param {__deltemplate__PageRenderer_column_grid.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_column_grid = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {?} */
  var column = opt_data.column;
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var columnIndex = soy.asserts.assertType(opt_data.columnIndex == null || goog.isNumber(opt_data.columnIndex), 'columnIndex', opt_data.columnIndex, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var rowIndex = soy.asserts.assertType(opt_data.rowIndex == null || goog.isNumber(opt_data.rowIndex), 'rowIndex', opt_data.rowIndex, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {*|null|undefined} */
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldChanged = opt_data._handleFieldChanged;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  if ((column.fields.length) != 0) {
    var attributes__soy582 = function() {
      incrementalDom.attr('class', 'col-md-' + column.size + ' col-ddm');
      if (rowIndex != 'undefined') {
        incrementalDom.attr('data-ddm-field-row', rowIndex);
      }
      if (pageId != 'undefined') {
        incrementalDom.attr('data-ddm-field-page', pageId);
      }
      if (columnIndex != 'undefined') {
        incrementalDom.attr('data-ddm-field-column', columnIndex);
      }
    };
    incrementalDom.elementOpenStart('div');
        attributes__soy582();
    incrementalDom.elementOpenEnd();
    var content__soy599 = function() {
      var field587List = column.fields;
      var field587ListLen = field587List.length;
      for (var field587Index = 0; field587Index < field587ListLen; field587Index++) {
        var field587Data = field587List[field587Index];
        var fieldType__soy590 = field587Data.type;
        soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), fieldType__soy590, true)(soy.$$assignDefaults({editable: editable, events: {fieldEdited: _handleFieldChanged}, ref: 'field', spritemap: spritemap}, field587Data), opt_ijData);
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
      var attributesDrag__soy609 = function() {
        incrementalDom.attr('class', 'ddm-drag');
        incrementalDom.attr('data-onclick', _handleSelectFieldFocused);
      };
      incrementalDom.elementOpenStart('div');
          attributesDrag__soy609();
      incrementalDom.elementOpenEnd();
      content__soy599();
      incrementalDom.elementClose('div');
      $fieldActions({_handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, spritemap: spritemap}, opt_ijData);
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'ddm-resize-handle-right');
          incrementalDom.attr('data-onclick', _handleOnClickResize);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    } else {
      content__soy599();
    }
    incrementalDom.elementClose('div');
  } else if (editable) {
    $fieldEmpty({column: columnIndex, pageId: pageId, row: rowIndex, size: column.size}, opt_ijData);
  }
};
exports.__deltemplate__PageRenderer_column_grid = __deltemplate__PageRenderer_column_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  column: (?|undefined),
 *  editable: (boolean|null|undefined),
 *  columnIndex: (null|number|undefined),
 *  rowIndex: (null|number|undefined),
 *  pageId: (null|number|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldChanged: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 * }}
 */
__deltemplate__PageRenderer_column_grid.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_column_grid.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_column_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.column.idom'), 'grid', 0, __deltemplate__PageRenderer_column_grid);


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
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'ddm-field-actions-container');
  incrementalDom.elementOpenEnd();
  $templateAlias1({events: {click: _handleDuplicateButtonClicked}, style: 'secondary', size: 'sm', spritemap: spritemap, icon: 'paste', monospaced: true}, opt_ijData);
  $templateAlias1({events: {click: _handleDeleteButtonClicked}, size: 'sm', style: 'secondary', spritemap: spritemap, icon: 'trash', monospaced: true}, opt_ijData);
  incrementalDom.elementClose('div');
};
exports.fieldActions = $fieldActions;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 * }}
 */
$fieldActions.Params;
if (goog.DEBUG) {
  $fieldActions.soyTemplateName = 'PageRenderer.fieldActions';
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
  var attributes__soy686 = function() {
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
  var content__soy691 = function() {
    incrementalDom.elementOpenStart('div');
        attributes__soy686();
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'col-empty ddm-target');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  };
  if (isRow) {
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'row');
    incrementalDom.elementOpenEnd();
    content__soy691();
    incrementalDom.elementClose('div');
  } else {
    content__soy691();
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
  $fieldEmpty.soyTemplateName = 'PageRenderer.fieldEmpty';
}


/**
 * @param {__deltemplate__PageRenderer_RegisterFieldType_.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__PageRenderer_RegisterFieldType_ = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var field = opt_data.field;
  if (editable) {
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'ddm-field-not-found');
    incrementalDom.elementOpenEnd();
    incrementalDom.text('Field doesn\'t exist.');
    incrementalDom.elementClose('div');
  }
};
exports.__deltemplate__PageRenderer_RegisterFieldType_ = __deltemplate__PageRenderer_RegisterFieldType_;
/**
 * @typedef {{
 *  editable: (boolean|null|undefined),
 *  field: (?|undefined),
 * }}
 */
__deltemplate__PageRenderer_RegisterFieldType_.Params;
if (goog.DEBUG) {
  __deltemplate__PageRenderer_RegisterFieldType_.soyTemplateName = 'PageRenderer.__deltemplate__PageRenderer_RegisterFieldType_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), '', 0, __deltemplate__PageRenderer_RegisterFieldType_);

exports.render.params = ["spritemap","activePage","contentRenderer","descriptionPlaceholder","editable","page","pageId","titlePlaceholder","_handleChangePageDescription","_handleChangePageTitle","_handleDeleteButtonClicked","_handleDuplicateButtonClicked","_handleFieldChanged","_handleSelectFieldFocused","_handleOnClickResize"];
exports.render.types = {"spritemap":"string","activePage":"number","contentRenderer":"string","descriptionPlaceholder":"string","editable":"bool","page":"?","pageId":"int","titlePlaceholder":"string","_handleChangePageDescription":"any","_handleChangePageTitle":"any","_handleDeleteButtonClicked":"any","_handleDuplicateButtonClicked":"any","_handleFieldChanged":"any","_handleSelectFieldFocused":"any","_handleOnClickResize":"any"};
exports.form.params = ["descriptionPlaceholder","page","pageId","titlePlaceholder","_handleChangePageDescription","_handleChangePageTitle"];
exports.form.types = {"descriptionPlaceholder":"string","page":"?","pageId":"int","titlePlaceholder":"string","_handleChangePageDescription":"any","_handleChangePageTitle":"any"};
exports.fieldActions.params = ["spritemap","_handleDeleteButtonClicked","_handleDuplicateButtonClicked"];
exports.fieldActions.types = {"spritemap":"string","_handleDeleteButtonClicked":"any","_handleDuplicateButtonClicked":"any"};
exports.fieldEmpty.params = ["size","column","isRow","pageId","row"];
exports.fieldEmpty.types = {"size":"number","column":"number","isRow":"bool","pageId":"number","row":"number"};
templates = exports;
return exports;

});

class PageRenderer extends Component {}
Soy.register(PageRenderer, templates);
export { PageRenderer, templates };
export default templates;
/* jshint ignore:end */
