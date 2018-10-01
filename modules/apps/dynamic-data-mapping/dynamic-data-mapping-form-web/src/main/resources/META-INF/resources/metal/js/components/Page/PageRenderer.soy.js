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
goog.require('goog.string');
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
  var _handlePageDescriptionChanged = opt_data._handlePageDescriptionChanged;
  /** @type {*|null|undefined} */
  var _handlePageTitleChanged = opt_data._handlePageTitleChanged;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handleModal = opt_data._handleModal;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var descriptionPlaceholder = soy.asserts.assertType(opt_data.descriptionPlaceholder == null || (goog.isString(opt_data.descriptionPlaceholder) || opt_data.descriptionPlaceholder instanceof goog.soy.data.SanitizedContent), 'descriptionPlaceholder', opt_data.descriptionPlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var empty = soy.asserts.assertType(opt_data.empty == null || (goog.isBoolean(opt_data.empty) || opt_data.empty === 1 || opt_data.empty === 0), 'empty', opt_data.empty, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {null|undefined|{delete: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogQuestion: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogTitle: (!goog.soy.data.SanitizedContent|string), dismiss: (!goog.soy.data.SanitizedContent|string),}} */
  var strings = soy.asserts.assertType(opt_data.strings == null || goog.isObject(opt_data.strings), 'strings', opt_data.strings, 'null|undefined|{delete: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogQuestion: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogTitle: (!goog.soy.data.SanitizedContent|string), dismiss: (!goog.soy.data.SanitizedContent|string),}');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titlePlaceholder = soy.asserts.assertType(opt_data.titlePlaceholder == null || (goog.isString(opt_data.titlePlaceholder) || opt_data.titlePlaceholder instanceof goog.soy.data.SanitizedContent), 'titlePlaceholder', opt_data.titlePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.container.idom'), contentRenderer, false)({_handlePageDescriptionChanged: _handlePageDescriptionChanged, _handlePageTitleChanged: _handlePageTitleChanged, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldEdited: _handleFieldEdited, _handleModal: _handleModal, _handleOnClickResize: _handleOnClickResize, _handleSelectFieldFocused: _handleSelectFieldFocused, activePage: activePage, contentRenderer: contentRenderer, descriptionPlaceholder: descriptionPlaceholder, editable: editable, empty: empty, page: page, pageId: pageId, spritemap: spritemap, strings: strings, titlePlaceholder: titlePlaceholder}, opt_ijData);
};
exports.render = $render;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handlePageDescriptionChanged: (*|null|undefined),
 *  _handlePageTitleChanged: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handleModal: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  descriptionPlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  empty: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  strings: (null|undefined|{delete: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogQuestion: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogTitle: (!goog.soy.data.SanitizedContent|string), dismiss: (!goog.soy.data.SanitizedContent|string),}),
 *  titlePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
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
  /** @type {*|null|undefined} */
  var _handlePageDescriptionChanged = opt_data._handlePageDescriptionChanged;
  /** @type {*|null|undefined} */
  var _handlePageTitleChanged = opt_data._handlePageTitleChanged;
  /** @type {*|null|undefined} */
  var _handleModal = opt_data._handleModal;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var descriptionPlaceholder = soy.asserts.assertType(opt_data.descriptionPlaceholder == null || (goog.isString(opt_data.descriptionPlaceholder) || opt_data.descriptionPlaceholder instanceof goog.soy.data.SanitizedContent), 'descriptionPlaceholder', opt_data.descriptionPlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var empty = soy.asserts.assertType(opt_data.empty == null || (goog.isBoolean(opt_data.empty) || opt_data.empty === 1 || opt_data.empty === 0), 'empty', opt_data.empty, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {null|undefined|{delete: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogQuestion: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogTitle: (!goog.soy.data.SanitizedContent|string), dismiss: (!goog.soy.data.SanitizedContent|string),}} */
  var strings = soy.asserts.assertType(opt_data.strings == null || goog.isObject(opt_data.strings), 'strings', opt_data.strings, 'null|undefined|{delete: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogQuestion: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogTitle: (!goog.soy.data.SanitizedContent|string), dismiss: (!goog.soy.data.SanitizedContent|string),}');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titlePlaceholder = soy.asserts.assertType(opt_data.titlePlaceholder == null || (goog.isString(opt_data.titlePlaceholder) || opt_data.titlePlaceholder instanceof goog.soy.data.SanitizedContent), 'titlePlaceholder', opt_data.titlePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'ddm-page-container-layout');
  incrementalDom.elementOpenEnd();
  if (activePage == pageId) {
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'ddm-form-page pl-4 pr-4');
    incrementalDom.elementOpenEnd();
    $form({_handlePageDescriptionChanged: _handlePageDescriptionChanged, _handlePageTitleChanged: _handlePageTitleChanged, contentRenderer: contentRenderer, descriptionPlaceholder: descriptionPlaceholder, editable: editable, page: page, pageId: pageId, titlePlaceholder: titlePlaceholder}, opt_ijData);
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'form-builder-layout');
    incrementalDom.elementOpenEnd();
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.page.idom'), contentRenderer, false)({_handleModal: _handleModal, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldEdited: _handleFieldEdited, _handleOnClickResize: _handleOnClickResize, _handleSelectFieldFocused: _handleSelectFieldFocused, contentRenderer: contentRenderer, editable: editable, empty: empty, page: page, pageId: pageId, spritemap: spritemap}, opt_ijData);
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_container_grid = __deltemplate__PageRenderer_container_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handlePageDescriptionChanged: (*|null|undefined),
 *  _handlePageTitleChanged: (*|null|undefined),
 *  _handleModal: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  descriptionPlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  empty: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  strings: (null|undefined|{delete: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogQuestion: (!goog.soy.data.SanitizedContent|string), deleteFieldDialogTitle: (!goog.soy.data.SanitizedContent|string), dismiss: (!goog.soy.data.SanitizedContent|string),}),
 *  titlePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
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
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
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
  var classes__soy733 = '';
  classes__soy733 += 'fade tab-pane';
  classes__soy733 += activePage == pageId ? ' active show' : '';
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('aria-labelledby', 'sidebarLightFormsFieldTab');
      incrementalDom.attr('class', classes__soy733);
      incrementalDom.attr('id', 'sidebarLightDetails');
      incrementalDom.attr('role', 'tabpanel');
  incrementalDom.elementOpenEnd();
  soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.page.idom'), contentRenderer, false)({_handleFieldEdited: _handleFieldEdited, contentRenderer: contentRenderer, editable: editable, page: page, pageId: pageId, spritemap: spritemap}, opt_ijData);
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_container_list = __deltemplate__PageRenderer_container_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleFieldEdited: (*|null|undefined),
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
  /** @type {*|null|undefined} */
  var _handlePageDescriptionChanged = opt_data._handlePageDescriptionChanged;
  /** @type {*|null|undefined} */
  var _handlePageTitleChanged = opt_data._handlePageTitleChanged;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var descriptionPlaceholder = soy.asserts.assertType(opt_data.descriptionPlaceholder == null || (goog.isString(opt_data.descriptionPlaceholder) || opt_data.descriptionPlaceholder instanceof goog.soy.data.SanitizedContent), 'descriptionPlaceholder', opt_data.descriptionPlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var titlePlaceholder = soy.asserts.assertType(opt_data.titlePlaceholder == null || (goog.isString(opt_data.titlePlaceholder) || opt_data.titlePlaceholder instanceof goog.soy.data.SanitizedContent), 'titlePlaceholder', opt_data.titlePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var pageTitleAttributes__soy762 = function() {
    incrementalDom.attr('class', 'form-builder-page-header-title form-control p-0');
    incrementalDom.attr('data-onkeyup', _handlePageTitleChanged);
    incrementalDom.attr('data-page-id', pageId);
    incrementalDom.attr('maxlength', '120');
    incrementalDom.attr('placeholder', titlePlaceholder);
    incrementalDom.attr('value', page.title);
  };
  var pageDescriptionAttributes__soy773 = function() {
    incrementalDom.attr('class', 'form-builder-page-header-description form-control p-0');
    incrementalDom.attr('data-onkeyup', _handlePageDescriptionChanged);
    incrementalDom.attr('data-page-id', pageId);
    incrementalDom.attr('maxlength', '120');
    incrementalDom.attr('placeholder', descriptionPlaceholder);
    incrementalDom.attr('value', page.description);
  };
  incrementalDom.elementOpenStart('textarea');
      pageTitleAttributes__soy762();
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('textarea');
  incrementalDom.elementOpenStart('textarea');
      pageDescriptionAttributes__soy773();
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('textarea');
};
exports.form = $form;
/**
 * @typedef {{
 *  _handlePageDescriptionChanged: (*|null|undefined),
 *  _handlePageTitleChanged: (*|null|undefined),
 *  descriptionPlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
 *  titlePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
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
  /** @type {*|null|undefined} */
  var _handleModal = opt_data._handleModal;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  var row791List = page.rows;
  var row791ListLen = row791List.length;
  for (var row791Index = 0; row791Index < row791ListLen; row791Index++) {
    var row791Data = row791List[row791Index];
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.row.idom'), 'grid', false)({_handleModal: _handleModal, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldEdited: _handleFieldEdited, _handleOnClickResize: _handleOnClickResize, _handleSelectFieldFocused: _handleSelectFieldFocused, contentRenderer: contentRenderer, editable: editable, pageId: pageId, row: row791Data, rowIndex: row791Index, spritemap: spritemap}, opt_ijData);
  }
};
exports.__deltemplate__PageRenderer_page_list = __deltemplate__PageRenderer_page_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleModal: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
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
  /** @type {*|null|undefined} */
  var _handleModal = opt_data._handleModal;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var empty = soy.asserts.assertType(opt_data.empty == null || (goog.isBoolean(opt_data.empty) || opt_data.empty === 1 || opt_data.empty === 0), 'empty', opt_data.empty, 'boolean|null|undefined');
  /** @type {?} */
  var page = opt_data.page;
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'layout-page');
  incrementalDom.elementOpenEnd();
  if (empty) {
    var attributes__soy828 = function() {
      incrementalDom.attr('class', 'col col-empty col-md-12 last-col lfr-initial-col mt-5 mb-4');
      incrementalDom.attr('data-ddm-field-column', '0');
      incrementalDom.attr('data-ddm-field-page', pageId);
      incrementalDom.attr('data-ddm-field-row', '0');
    };
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'row');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        attributes__soy828();
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'ddm-target');
        incrementalDom.attr('id', 'lfr-initial-col-message');
    incrementalDom.elementOpenEnd();
    /** @desc  */
    var MSG_EXTERNAL_3366078975712358322 = goog.getMsg('drag-from-sidebar-and-drop-here');
    incrementalDom.text(goog.string.unescapeEntities(MSG_EXTERNAL_3366078975712358322));
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  } else {
    var row838List = page.rows;
    var row838ListLen = row838List.length;
    for (var row838Index = 0; row838Index < row838ListLen; row838Index++) {
      var row838Data = row838List[row838Index];
      if (row838Index == 0 && editable) {
        $fieldEmpty({isRow: true, pageIndex: pageId, rowIndex: 0, size: 12}, opt_ijData);
      }
      soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.row.idom'), contentRenderer, false)({_handleModal: _handleModal, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldEdited: _handleFieldEdited, _handleOnClickResize: _handleOnClickResize, _handleSelectFieldFocused: _handleSelectFieldFocused, contentRenderer: contentRenderer, editable: editable, pageId: pageId, row: row838Data, rowIndex: row838Index, spritemap: spritemap}, opt_ijData);
      if (editable) {
        $fieldEmpty({isRow: true, pageIndex: pageId, rowIndex: row838Index + 1, size: 12}, opt_ijData);
      }
    }
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_page_grid = __deltemplate__PageRenderer_page_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleModal: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  empty: (boolean|null|undefined),
 *  page: (?|undefined),
 *  pageId: (null|number|undefined),
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
  /** @type {*|null|undefined} */
  var _handleModal = opt_data._handleModal;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {?} */
  var row = opt_data.row;
  /** @type {null|number|undefined} */
  var rowIndex = soy.asserts.assertType(opt_data.rowIndex == null || goog.isNumber(opt_data.rowIndex), 'rowIndex', opt_data.rowIndex, 'null|number|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'row');
  incrementalDom.elementOpenEnd();
  var column890List = row.columns;
  var column890ListLen = column890List.length;
  for (var column890Index = 0; column890Index < column890ListLen; column890Index++) {
    var column890Data = column890List[column890Index];
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.column.idom'), contentRenderer, false)({_handleModal: _handleModal, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleFieldEdited: _handleFieldEdited, _handleOnClickResize: _handleOnClickResize, _handleSelectFieldFocused: _handleSelectFieldFocused, column: column890Data, columnIndex: column890Index, editable: editable, pageId: pageId, rowIndex: rowIndex, spritemap: spritemap}, opt_ijData);
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__PageRenderer_row_grid = __deltemplate__PageRenderer_row_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleModal: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  editable: (boolean|null|undefined),
 *  pageId: (null|number|undefined),
 *  row: (?|undefined),
 *  rowIndex: (null|number|undefined),
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
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {?} */
  var column = opt_data.column;
  /** @type {null|number|undefined} */
  var columnIndex = soy.asserts.assertType(opt_data.columnIndex == null || goog.isNumber(opt_data.columnIndex), 'columnIndex', opt_data.columnIndex, 'null|number|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var rowIndex = soy.asserts.assertType(opt_data.rowIndex == null || goog.isNumber(opt_data.rowIndex), 'rowIndex', opt_data.rowIndex, 'null|number|undefined');
  if ((column.fields.length) != 0) {
    var attributes__soy922 = function() {
      incrementalDom.attr('class', 'col-md-' + column.size);
    };
    incrementalDom.elementOpenStart('div');
        attributes__soy922();
    incrementalDom.elementOpenEnd();
    var field926List = column.fields;
    var field926ListLen = field926List.length;
    for (var field926Index = 0; field926Index < field926ListLen; field926Index++) {
      var field926Data = field926List[field926Index];
      var $tmp = field926Data.type;
      var fieldType__soy929 = $tmp != null ? $tmp : 'empty';
      soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), fieldType__soy929, true)(soy.$$assignDefaults({editable: editable, events: {fieldEdited: _handleFieldEdited}, spritemap: spritemap}, field926Data), opt_ijData);
    }
    incrementalDom.elementClose('div');
  }
};
exports.__deltemplate__PageRenderer_column_list = __deltemplate__PageRenderer_column_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleFieldEdited: (*|null|undefined),
 *  column: (?|undefined),
 *  columnIndex: (null|number|undefined),
 *  editable: (boolean|null|undefined),
 *  pageId: (null|number|undefined),
 *  rowIndex: (null|number|undefined),
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
  /** @type {*|null|undefined} */
  var _handleModal = opt_data._handleModal;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handleOnClickResize = opt_data._handleOnClickResize;
  /** @type {*|null|undefined} */
  var _handleSelectFieldFocused = opt_data._handleSelectFieldFocused;
  /** @type {?} */
  var column = opt_data.column;
  /** @type {null|number|undefined} */
  var columnIndex = soy.asserts.assertType(opt_data.columnIndex == null || goog.isNumber(opt_data.columnIndex), 'columnIndex', opt_data.columnIndex, 'null|number|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var pageId = soy.asserts.assertType(opt_data.pageId == null || goog.isNumber(opt_data.pageId), 'pageId', opt_data.pageId, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var rowIndex = soy.asserts.assertType(opt_data.rowIndex == null || goog.isNumber(opt_data.rowIndex), 'rowIndex', opt_data.rowIndex, 'null|number|undefined');
  if ((column.fields.length) != 0) {
    var attributes__soy975 = function() {
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
        attributes__soy975();
    incrementalDom.elementOpenEnd();
    var content__soy992 = function() {
      var field980List = column.fields;
      var field980ListLen = field980List.length;
      for (var field980Index = 0; field980Index < field980ListLen; field980Index++) {
        var field980Data = field980List[field980Index];
        var fieldType__soy983 = field980Data.type;
        soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), fieldType__soy983, true)(soy.$$assignDefaults({editable: editable, events: {visibleChanged: _handleFieldEdited, fieldEdited: _handleFieldEdited}, ref: 'field', spritemap: spritemap}, field980Data), opt_ijData);
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
      var attributesDrag__soy1002 = function() {
        incrementalDom.attr('class', 'ddm-drag');
        incrementalDom.attr('data-onclick', _handleSelectFieldFocused);
      };
      incrementalDom.elementOpenStart('div');
          attributesDrag__soy1002();
      incrementalDom.elementOpenEnd();
      content__soy992();
      incrementalDom.elementClose('div');
      $fieldActions({_handleModal: _handleModal, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, spritemap: spritemap}, opt_ijData);
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'ddm-resize-handle-right');
          incrementalDom.attr('data-onclick', _handleOnClickResize);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    } else {
      content__soy992();
    }
    incrementalDom.elementClose('div');
  } else if (editable) {
    $fieldEmpty({columnIndex: columnIndex, pageIndex: pageId, rowIndex: rowIndex, size: column.size}, opt_ijData);
  }
};
exports.__deltemplate__PageRenderer_column_grid = __deltemplate__PageRenderer_column_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleModal: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handleOnClickResize: (*|null|undefined),
 *  _handleSelectFieldFocused: (*|null|undefined),
 *  column: (?|undefined),
 *  columnIndex: (null|number|undefined),
 *  editable: (boolean|null|undefined),
 *  pageId: (null|number|undefined),
 *  rowIndex: (null|number|undefined),
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
  var _handleModal = opt_data._handleModal;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'ddm-field-actions-container');
  incrementalDom.elementOpenEnd();
  $templateAlias1({events: {click: _handleDuplicateButtonClicked}, icon: 'paste', monospaced: true, size: 'sm', spritemap: spritemap, style: 'secondary'}, opt_ijData);
  $templateAlias1({events: {click: _handleModal}, icon: 'trash', monospaced: true, size: 'sm', spritemap: spritemap, style: 'secondary'}, opt_ijData);
  incrementalDom.elementClose('div');
};
exports.fieldActions = $fieldActions;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleModal: (*|null|undefined),
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
  var columnIndex = soy.asserts.assertType(opt_data.columnIndex == null || goog.isNumber(opt_data.columnIndex), 'columnIndex', opt_data.columnIndex, 'null|number|undefined');
  /** @type {boolean|null|undefined} */
  var isRow = soy.asserts.assertType(opt_data.isRow == null || (goog.isBoolean(opt_data.isRow) || opt_data.isRow === 1 || opt_data.isRow === 0), 'isRow', opt_data.isRow, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var pageIndex = soy.asserts.assertType(opt_data.pageIndex == null || goog.isNumber(opt_data.pageIndex), 'pageIndex', opt_data.pageIndex, 'null|number|undefined');
  /** @type {null|number|undefined} */
  var rowIndex = soy.asserts.assertType(opt_data.rowIndex == null || goog.isNumber(opt_data.rowIndex), 'rowIndex', opt_data.rowIndex, 'null|number|undefined');
  var attributes__soy1079 = function() {
    incrementalDom.attr('class', 'col col-empty col-md-' + size);
    if (rowIndex != 'undefined') {
      incrementalDom.attr('data-ddm-field-row', rowIndex);
    }
    if (pageIndex != 'undefined') {
      incrementalDom.attr('data-ddm-field-page', pageIndex);
    }
    if (columnIndex != 'undefined') {
      incrementalDom.attr('data-ddm-field-column', columnIndex);
    }
  };
  var content__soy1084 = function() {
    incrementalDom.elementOpenStart('div');
        attributes__soy1079();
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'ddm-target');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  };
  if (isRow) {
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'row');
    incrementalDom.elementOpenEnd();
    content__soy1084();
    incrementalDom.elementClose('div');
  } else {
    content__soy1084();
  }
};
exports.fieldEmpty = $fieldEmpty;
/**
 * @typedef {{
 *  size: number,
 *  columnIndex: (null|number|undefined),
 *  isRow: (boolean|null|undefined),
 *  pageIndex: (null|number|undefined),
 *  rowIndex: (null|number|undefined),
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

exports.render.params = ["spritemap","_handlePageDescriptionChanged","_handlePageTitleChanged","_handleDuplicateButtonClicked","_handleFieldEdited","_handleModal","_handleOnClickResize","_handleSelectFieldFocused","activePage","contentRenderer","descriptionPlaceholder","editable","empty","page","pageId","strings","titlePlaceholder"];
exports.render.types = {"spritemap":"string","_handlePageDescriptionChanged":"any","_handlePageTitleChanged":"any","_handleDuplicateButtonClicked":"any","_handleFieldEdited":"any","_handleModal":"any","_handleOnClickResize":"any","_handleSelectFieldFocused":"any","activePage":"number","contentRenderer":"string","descriptionPlaceholder":"string","editable":"bool","empty":"bool","page":"?","pageId":"number","strings":"[\n\t\tdismiss: string,\n\t\tdelete: string,\n\t\tdeleteFieldDialogQuestion: string,\n\t\tdeleteFieldDialogTitle:string\n\t\t]\n\t","titlePlaceholder":"string"};
exports.form.params = ["_handlePageDescriptionChanged","_handlePageTitleChanged","descriptionPlaceholder","page","pageId","titlePlaceholder"];
exports.form.types = {"_handlePageDescriptionChanged":"any","_handlePageTitleChanged":"any","descriptionPlaceholder":"string","page":"?","pageId":"number","titlePlaceholder":"string"};
exports.fieldActions.params = ["spritemap","_handleModal","_handleDuplicateButtonClicked"];
exports.fieldActions.types = {"spritemap":"string","_handleModal":"any","_handleDuplicateButtonClicked":"any"};
exports.fieldEmpty.params = ["size","columnIndex","isRow","pageIndex","rowIndex"];
exports.fieldEmpty.types = {"size":"number","columnIndex":"number","isRow":"bool","pageIndex":"number","rowIndex":"number"};
templates = exports;
return exports;

});

class PageRenderer extends Component {}
Soy.register(PageRenderer, templates);
export { PageRenderer, templates };
export default templates;
/* jshint ignore:end */
