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
 * @hassoydeltemplate {FormRenderer.pages.idom}
 * @hassoydeltemplate {FormRenderer.pagination.idom}
 * @hassoydeltemplate {FormRenderer.paginationContainer.idom}
 * @hassoydeltemplate {FormRenderer.wizard.idom}
 * @hassoydelcall {FormRenderer.pages.idom}
 * @hassoydelcall {FormRenderer.pagination.idom}
 * @hassoydelcall {FormRenderer.paginationContainer.idom}
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

var $templateAlias3 = Soy.getTemplate('PageRenderer.incrementaldom', 'render');

var $templateAlias2 = Soy.getTemplate('SuccessPage.incrementaldom', 'render');


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
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleExpandedChanged = opt_data._handleExpandedChanged;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handlePaginationLeftClicked = opt_data._handlePaginationLeftClicked;
  /** @type {*|null|undefined} */
  var _handlePaginationRightClicked = opt_data._handlePaginationRightClicked;
  /** @type {*|null|undefined} */
  var _handlePageSettingsClicked = opt_data._handlePageSettingsClicked;
  /** @type {*|null|undefined} */
  var _handleSuccesPageChanged = opt_data._handleSuccesPageChanged;
  /** @type {*|null|undefined} */
  var _handleUpdatePage = opt_data._handleUpdatePage;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var defaultPageTitle = soy.asserts.assertType(opt_data.defaultPageTitle == null || (goog.isString(opt_data.defaultPageTitle) || opt_data.defaultPageTitle instanceof goog.soy.data.SanitizedContent), 'defaultPageTitle', opt_data.defaultPageTitle, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var dropdownExpanded = soy.asserts.assertType(opt_data.dropdownExpanded == null || (goog.isBoolean(opt_data.dropdownExpanded) || opt_data.dropdownExpanded === 1 || opt_data.dropdownExpanded === 0), 'dropdownExpanded', opt_data.dropdownExpanded, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var modeRenderer = soy.asserts.assertType(opt_data.modeRenderer == null || (goog.isString(opt_data.modeRenderer) || opt_data.modeRenderer instanceof goog.soy.data.SanitizedContent), 'modeRenderer', opt_data.modeRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {?} */
  var pageSettingsItems = opt_data.pageSettingsItems;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var paginationMode = soy.asserts.assertType(opt_data.paginationMode == null || (goog.isString(opt_data.paginationMode) || opt_data.paginationMode instanceof goog.soy.data.SanitizedContent), 'paginationMode', opt_data.paginationMode, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var successPageLabel = soy.asserts.assertType(opt_data.successPageLabel == null || (goog.isString(opt_data.successPageLabel) || opt_data.successPageLabel instanceof goog.soy.data.SanitizedContent), 'successPageLabel', opt_data.successPageLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var successPageSettings = opt_data.successPageSettings;
  var contentRenderer__soy25 = modeRenderer != null ? modeRenderer : 'grid';
  var total__soy27 = (pages.length);
  soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.pages.idom'), contentRenderer__soy25, false)({_handleChangePage: _handleChangePage, _handleDeleteButtonClicked: _handleDeleteButtonClicked, _handleDuplicateButtonClicked: _handleDuplicateButtonClicked, _handleExpandedChanged: _handleExpandedChanged, _handleFieldEdited: _handleFieldEdited, _handlePaginationLeftClicked: _handlePaginationLeftClicked, _handlePaginationRightClicked: _handlePaginationRightClicked, _handlePageSettingsClicked: _handlePageSettingsClicked, _handleSuccesPageChanged: _handleSuccesPageChanged, _handleUpdatePage: _handleUpdatePage, activePage: activePage, contentRenderer: contentRenderer__soy25, defaultPageTitle: defaultPageTitle, dropdownExpanded: dropdownExpanded, editable: editable, pages: pages, pageSettingsItems: pageSettingsItems, paginationMode: paginationMode, spritemap: spritemap, successPageLabel: successPageLabel, successPageSettings: successPageSettings, total: total__soy27}, opt_ijData);
};
exports.render = $render;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleChangePage: (*|null|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleExpandedChanged: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handlePaginationLeftClicked: (*|null|undefined),
 *  _handlePaginationRightClicked: (*|null|undefined),
 *  _handlePageSettingsClicked: (*|null|undefined),
 *  _handleSuccesPageChanged: (*|null|undefined),
 *  _handleUpdatePage: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  defaultPageTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  dropdownExpanded: (boolean|null|undefined),
 *  editable: (boolean|null|undefined),
 *  modeRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  pages: (?|undefined),
 *  pageSettingsItems: (?|undefined),
 *  paginationMode: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageSettings: (?|undefined),
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
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleExpandedChanged = opt_data._handleExpandedChanged;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
  /** @type {*|null|undefined} */
  var _handlePaginationLeftClicked = opt_data._handlePaginationLeftClicked;
  /** @type {*|null|undefined} */
  var _handlePaginationRightClicked = opt_data._handlePaginationRightClicked;
  /** @type {*|null|undefined} */
  var _handlePageSettingsClicked = opt_data._handlePageSettingsClicked;
  /** @type {*|null|undefined} */
  var _handleSuccesPageChanged = opt_data._handleSuccesPageChanged;
  /** @type {*|null|undefined} */
  var _handleUpdatePage = opt_data._handleUpdatePage;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var defaultPageTitle = soy.asserts.assertType(opt_data.defaultPageTitle == null || (goog.isString(opt_data.defaultPageTitle) || opt_data.defaultPageTitle instanceof goog.soy.data.SanitizedContent), 'defaultPageTitle', opt_data.defaultPageTitle, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var dropdownExpanded = soy.asserts.assertType(opt_data.dropdownExpanded == null || (goog.isBoolean(opt_data.dropdownExpanded) || opt_data.dropdownExpanded === 1 || opt_data.dropdownExpanded === 0), 'dropdownExpanded', opt_data.dropdownExpanded, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var editable = soy.asserts.assertType(opt_data.editable == null || (goog.isBoolean(opt_data.editable) || opt_data.editable === 1 || opt_data.editable === 0), 'editable', opt_data.editable, 'boolean|null|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {?} */
  var pageSettingsItems = opt_data.pageSettingsItems;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var paginationMode = soy.asserts.assertType(opt_data.paginationMode == null || (goog.isString(opt_data.paginationMode) || opt_data.paginationMode instanceof goog.soy.data.SanitizedContent), 'paginationMode', opt_data.paginationMode, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var successPageLabel = soy.asserts.assertType(opt_data.successPageLabel == null || (goog.isString(opt_data.successPageLabel) || opt_data.successPageLabel instanceof goog.soy.data.SanitizedContent), 'successPageLabel', opt_data.successPageLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var successPageSettings = opt_data.successPageSettings;
  /** @type {null|number|undefined} */
  var total = soy.asserts.assertType(opt_data.total == null || goog.isNumber(opt_data.total), 'total', opt_data.total, 'null|number|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'position-relative');
  incrementalDom.elementOpenEnd();
  if (paginationMode == 'wizard' || (pages.length) == 1 && !successPageSettings.enabled) {
    soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.paginationContainer.idom'), '', false)({_handleChangePage: _handleChangePage, _handleExpandedChanged: _handleExpandedChanged, _handlePaginationLeftClicked: _handlePaginationLeftClicked, _handlePaginationRightClicked: _handlePaginationRightClicked, _handlePageSettingsClicked: _handlePageSettingsClicked, activePage: activePage, defaultPageTitle: defaultPageTitle, dropdownExpanded: dropdownExpanded, pages: pages, pageSettingsItems: pageSettingsItems, paginationMode: paginationMode, spritemap: spritemap, successPageLabel: successPageLabel, successPageSettings: successPageSettings}, opt_ijData);
  }
  if (activePage == -1) {
    $templateAlias2({successPageSettings: successPageSettings, events: {successPageChanged: _handleSuccesPageChanged}}, opt_ijData);
  } else {
    var page107List = pages;
    var page107ListLen = page107List.length;
    for (var page107Index = 0; page107Index < page107ListLen; page107Index++) {
      var page107Data = page107List[page107Index];
      $templateAlias3({activePage: activePage, contentRenderer: contentRenderer, editable: editable, events: {deleteFieldClicked: _handleDeleteButtonClicked, duplicateButtonClicked: _handleDuplicateButtonClicked, fieldEdited: _handleFieldEdited, updatePage: _handleUpdatePage}, page: page107Data, pageId: page107Index, spritemap: spritemap, total: total}, opt_ijData);
    }
  }
  var multiple__soy122 = (pages.length) > 1;
  if (multiple__soy122 && paginationMode == 'pagination' || paginationMode == 'pagination' && (pages.length) == 1 && successPageSettings.enabled) {
    soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.paginationContainer.idom'), '', false)({_handleChangePage: _handleChangePage, _handleExpandedChanged: _handleExpandedChanged, _handlePaginationLeftClicked: _handlePaginationLeftClicked, _handlePaginationRightClicked: _handlePaginationRightClicked, _handlePageSettingsClicked: _handlePageSettingsClicked, activePage: activePage, defaultPageTitle: defaultPageTitle, dropdownExpanded: dropdownExpanded, pages: pages, pageSettingsItems: pageSettingsItems, paginationMode: paginationMode, spritemap: spritemap, successPageLabel: successPageLabel, successPageSettings: successPageSettings}, opt_ijData);
  }
  incrementalDom.elementClose('div');
};
exports.__deltemplate__FormRenderer_pages_grid = __deltemplate__FormRenderer_pages_grid;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleChangePage: (*|null|undefined),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleExpandedChanged: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handlePaginationLeftClicked: (*|null|undefined),
 *  _handlePaginationRightClicked: (*|null|undefined),
 *  _handlePageSettingsClicked: (*|null|undefined),
 *  _handleSuccesPageChanged: (*|null|undefined),
 *  _handleUpdatePage: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  defaultPageTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  dropdownExpanded: (boolean|null|undefined),
 *  editable: (boolean|null|undefined),
 *  pages: (?|undefined),
 *  pageSettingsItems: (?|undefined),
 *  paginationMode: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageSettings: (?|undefined),
 *  total: (null|number|undefined),
 * }}
 */
__deltemplate__FormRenderer_pages_grid.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_pages_grid.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_pages_grid';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.pages.idom'), 'grid', 0, __deltemplate__FormRenderer_pages_grid);


/**
 * @param {__deltemplate__FormRenderer_paginationContainer_.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__FormRenderer_paginationContainer_ = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var spritemap = soy.asserts.assertType(goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent, 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|string');
  /** @type {*|null|undefined} */
  var _handleChangePage = opt_data._handleChangePage;
  /** @type {*|null|undefined} */
  var _handleExpandedChanged = opt_data._handleExpandedChanged;
  /** @type {*|null|undefined} */
  var _handlePaginationLeftClicked = opt_data._handlePaginationLeftClicked;
  /** @type {*|null|undefined} */
  var _handlePaginationRightClicked = opt_data._handlePaginationRightClicked;
  /** @type {*|null|undefined} */
  var _handlePageSettingsClicked = opt_data._handlePageSettingsClicked;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var defaultPageTitle = soy.asserts.assertType(opt_data.defaultPageTitle == null || (goog.isString(opt_data.defaultPageTitle) || opt_data.defaultPageTitle instanceof goog.soy.data.SanitizedContent), 'defaultPageTitle', opt_data.defaultPageTitle, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var dropdownExpanded = soy.asserts.assertType(opt_data.dropdownExpanded == null || (goog.isBoolean(opt_data.dropdownExpanded) || opt_data.dropdownExpanded === 1 || opt_data.dropdownExpanded === 0), 'dropdownExpanded', opt_data.dropdownExpanded, 'boolean|null|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {?} */
  var pageSettingsItems = opt_data.pageSettingsItems;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var paginationMode = soy.asserts.assertType(opt_data.paginationMode == null || (goog.isString(opt_data.paginationMode) || opt_data.paginationMode instanceof goog.soy.data.SanitizedContent), 'paginationMode', opt_data.paginationMode, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var successPageLabel = soy.asserts.assertType(opt_data.successPageLabel == null || (goog.isString(opt_data.successPageLabel) || opt_data.successPageLabel instanceof goog.soy.data.SanitizedContent), 'successPageLabel', opt_data.successPageLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var successPageSettings = opt_data.successPageSettings;
  /** @type {null|number|undefined} */
  var total = soy.asserts.assertType(opt_data.total == null || goog.isNumber(opt_data.total), 'total', opt_data.total, 'null|number|undefined');
  var multiple__soy161 = (pages.length) > 1 || successPageSettings.enabled;
  var classes__soy176 = '';
  classes__soy176 += 'ddm-form-pagination';
  classes__soy176 += multiple__soy161 ? ' position-relative' : ' position-absolute';
  var $tmp;
  if (multiple__soy161 && paginationMode == 'wizard') {
    $tmp = ' wizard-mode';
  } else if (multiple__soy161 && paginationMode == 'pagination') {
    $tmp = ' pagination-mode';
  } else {
    $tmp = '';
  }
  classes__soy176 += $tmp;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', classes__soy176);
  incrementalDom.elementOpenEnd();
  if (multiple__soy161 && paginationMode == 'wizard') {
    soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.wizard.idom'), '', false)({_handleChangePage: _handleChangePage, activePage: activePage, defaultPageTitle: defaultPageTitle, pages: pages, spritemap: spritemap, successPageLabel: successPageLabel, successPageSettings: successPageSettings}, opt_ijData);
  } else if (multiple__soy161 && paginationMode == 'pagination') {
    soy.$$getDelegateFn(soy.$$getDelTemplateId('FormRenderer.pagination.idom'), '', false)({_handleChangePage: _handleChangePage, _handlePaginationLeftClicked: _handlePaginationLeftClicked, _handlePaginationRightClicked: _handlePaginationRightClicked, activePage: activePage, pages: pages, successPageLabel: successPageLabel, successPageSettings: successPageSettings}, opt_ijData);
  }
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'ddm-page-settings');
      incrementalDom.attr('role', 'group');
  incrementalDom.elementOpenEnd();
  $templateAlias1({items: pageSettingsItems, triggerClasses: 'component-action', events: {expandedChanged: _handleExpandedChanged, itemClicked: _handlePageSettingsClicked}, spritemap: spritemap}, opt_ijData);
  incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
};
exports.__deltemplate__FormRenderer_paginationContainer_ = __deltemplate__FormRenderer_paginationContainer_;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleChangePage: (*|null|undefined),
 *  _handleExpandedChanged: (*|null|undefined),
 *  _handlePaginationLeftClicked: (*|null|undefined),
 *  _handlePaginationRightClicked: (*|null|undefined),
 *  _handlePageSettingsClicked: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  defaultPageTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  dropdownExpanded: (boolean|null|undefined),
 *  pages: (?|undefined),
 *  pageSettingsItems: (?|undefined),
 *  paginationMode: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageSettings: (?|undefined),
 *  total: (null|number|undefined),
 * }}
 */
__deltemplate__FormRenderer_paginationContainer_.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_paginationContainer_.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_paginationContainer_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.paginationContainer.idom'), '', 0, __deltemplate__FormRenderer_paginationContainer_);


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
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var defaultPageTitle = soy.asserts.assertType(opt_data.defaultPageTitle == null || (goog.isString(opt_data.defaultPageTitle) || opt_data.defaultPageTitle instanceof goog.soy.data.SanitizedContent), 'defaultPageTitle', opt_data.defaultPageTitle, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var successPageLabel = soy.asserts.assertType(opt_data.successPageLabel == null || (goog.isString(opt_data.successPageLabel) || opt_data.successPageLabel instanceof goog.soy.data.SanitizedContent), 'successPageLabel', opt_data.successPageLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var successPageSettings = opt_data.successPageSettings;
  incrementalDom.elementOpenStart('ol');
      incrementalDom.attr('class', 'dmm-wizard multi-step-indicator-label-top multi-step-nav multi-step-nav-collapse-sm');
  incrementalDom.elementOpenEnd();
  var page219List = pages;
  var page219ListLen = page219List.length;
  for (var page219Index = 0; page219Index < page219ListLen; page219Index++) {
    var page219Data = page219List[page219Index];
    var title__soy222 = page219Data.title ? page219Data.title : defaultPageTitle;
    var pageId__soy224 = page219Index;
    var step__soy226 = pageId__soy224 + 1;
    var classes__soy237 = '';
    classes__soy237 += 'multi-step-item';
    classes__soy237 += step__soy226 != (pages.length) || successPageSettings.enabled ? ' multi-step-item-expand' : '';
    classes__soy237 += pageId__soy224 == activePage ? ' active' : '';
    incrementalDom.elementOpenStart('li');
        incrementalDom.attr('class', classes__soy237);
        incrementalDom.attr('data-onclick', _handleChangePage);
        incrementalDom.attr('data-page-id', pageId__soy224);
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
    soyIdom.print(title__soy222);
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('a');
        incrementalDom.attr('class', 'multi-step-icon');
        incrementalDom.attr('data-multi-step-icon', step__soy226);
        incrementalDom.attr('href', 'javascript:;');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('a');
    incrementalDom.elementClose('div');
    incrementalDom.elementClose('li');
  }
  if (successPageSettings.enabled) {
    var classes__soy258 = '';
    classes__soy258 += 'multi-step-item complete';
    classes__soy258 += activePage == -1 ? ' active' : '';
    incrementalDom.elementOpenStart('li');
        incrementalDom.attr('class', classes__soy258);
        incrementalDom.attr('data-onclick', _handleChangePage);
        incrementalDom.attr('data-page-id', '-1');
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
    soyIdom.print(successPageLabel);
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('a');
        incrementalDom.attr('class', 'multi-step-icon');
        incrementalDom.attr('href', 'javascript:;');
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
 *  defaultPageTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  pages: (?|undefined),
 *  successPageLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageSettings: (?|undefined),
 * }}
 */
__deltemplate__FormRenderer_wizard_.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_wizard_.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_wizard_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.wizard.idom'), '', 0, __deltemplate__FormRenderer_wizard_);


/**
 * @param {__deltemplate__FormRenderer_pagination_.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var __deltemplate__FormRenderer_pagination_ = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {*|null|undefined} */
  var _handleChangePage = opt_data._handleChangePage;
  /** @type {*|null|undefined} */
  var _handlePaginationLeftClicked = opt_data._handlePaginationLeftClicked;
  /** @type {*|null|undefined} */
  var _handlePaginationRightClicked = opt_data._handlePaginationRightClicked;
  /** @type {null|number|undefined} */
  var activePage = soy.asserts.assertType(opt_data.activePage == null || goog.isNumber(opt_data.activePage), 'activePage', opt_data.activePage, 'null|number|undefined');
  /** @type {?} */
  var pages = opt_data.pages;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var successPageLabel = soy.asserts.assertType(opt_data.successPageLabel == null || (goog.isString(opt_data.successPageLabel) || opt_data.successPageLabel instanceof goog.soy.data.SanitizedContent), 'successPageLabel', opt_data.successPageLabel, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var successPageSettings = opt_data.successPageSettings;
  incrementalDom.elementOpenStart('ul');
      incrementalDom.attr('class', 'ddm-pagination justify-content-center pagination');
  incrementalDom.elementOpenEnd();
  var wizardClasses__soy282 = '';
  wizardClasses__soy282 += 'page-item';
  wizardClasses__soy282 += activePage == 0 ? ' visibility-hidden' : '';
  incrementalDom.elementOpenStart('li');
      incrementalDom.attr('class', wizardClasses__soy282);
      incrementalDom.attr('data-onclick', _handlePaginationLeftClicked);
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('a');
      incrementalDom.attr('class', 'page-link');
      incrementalDom.attr('href', 'javascript:;');
      incrementalDom.attr('role', 'button');
  incrementalDom.elementOpenEnd();
  incrementalDom.text('\u00AB');
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'sr-only');
  incrementalDom.elementOpenEnd();
  incrementalDom.text('Previous');
  incrementalDom.elementClose('span');
  incrementalDom.elementClose('a');
  incrementalDom.elementClose('li');
  var page288List = pages;
  var page288ListLen = page288List.length;
  for (var page288Index = 0; page288Index < page288ListLen; page288Index++) {
    var page288Data = page288List[page288Index];
    var pageId__soy291 = page288Index;
    var step__soy293 = pageId__soy291 + 1;
    var classes__soy300 = '';
    classes__soy300 += 'page-item';
    classes__soy300 += pageId__soy291 == activePage ? ' active' : '';
    incrementalDom.elementOpenStart('li');
        incrementalDom.attr('class', classes__soy300);
        incrementalDom.attr('data-onclick', _handleChangePage);
        incrementalDom.attr('data-page-id', pageId__soy291);
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('a');
        incrementalDom.attr('class', 'page-link');
        incrementalDom.attr('href', 'javascript:;');
    incrementalDom.elementOpenEnd();
    soyIdom.print(step__soy293);
    incrementalDom.elementClose('a');
    incrementalDom.elementClose('li');
  }
  if (successPageSettings.enabled) {
    var classes__soy319 = '';
    classes__soy319 += 'page-item';
    classes__soy319 += activePage == -1 ? ' active' : '';
    incrementalDom.elementOpenStart('li');
        incrementalDom.attr('class', classes__soy319);
        incrementalDom.attr('data-onclick', _handleChangePage);
        incrementalDom.attr('data-page-id', '-1');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('a');
        incrementalDom.attr('class', 'page-link');
        incrementalDom.attr('href', 'javascript:;');
    incrementalDom.elementOpenEnd();
    soyIdom.print(successPageLabel);
    incrementalDom.elementClose('a');
    incrementalDom.elementClose('li');
  }
  var paginationClasses__soy333 = '';
  paginationClasses__soy333 += 'page-item';
  paginationClasses__soy333 += activePage == (pages.length) - 1 && !successPageSettings.enabled || successPageSettings.enabled && activePage == -1 ? ' visibility-hidden' : '';
  incrementalDom.elementOpenStart('li');
      incrementalDom.attr('class', paginationClasses__soy333);
      incrementalDom.attr('data-onclick', _handlePaginationRightClicked);
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('a');
      incrementalDom.attr('class', 'page-link');
      incrementalDom.attr('href', 'javascript:;');
      incrementalDom.attr('role', 'button');
  incrementalDom.elementOpenEnd();
  incrementalDom.text('\u00BB');
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'sr-only');
  incrementalDom.elementOpenEnd();
  incrementalDom.text('Next');
  incrementalDom.elementClose('span');
  incrementalDom.elementClose('a');
  incrementalDom.elementClose('li');
  incrementalDom.elementClose('ul');
};
exports.__deltemplate__FormRenderer_pagination_ = __deltemplate__FormRenderer_pagination_;
/**
 * @typedef {{
 *  _handleChangePage: (*|null|undefined),
 *  _handlePaginationLeftClicked: (*|null|undefined),
 *  _handlePaginationRightClicked: (*|null|undefined),
 *  activePage: (null|number|undefined),
 *  pages: (?|undefined),
 *  successPageLabel: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  successPageSettings: (?|undefined),
 * }}
 */
__deltemplate__FormRenderer_pagination_.Params;
if (goog.DEBUG) {
  __deltemplate__FormRenderer_pagination_.soyTemplateName = 'FormRenderer.__deltemplate__FormRenderer_pagination_';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('FormRenderer.pagination.idom'), '', 0, __deltemplate__FormRenderer_pagination_);


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
  var _handleDeleteButtonClicked = opt_data._handleDeleteButtonClicked;
  /** @type {*|null|undefined} */
  var _handleDuplicateButtonClicked = opt_data._handleDuplicateButtonClicked;
  /** @type {*|null|undefined} */
  var _handleFieldEdited = opt_data._handleFieldEdited;
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
  /** @type {null|number|undefined} */
  var total = soy.asserts.assertType(opt_data.total == null || goog.isNumber(opt_data.total), 'total', opt_data.total, 'null|number|undefined');
  var page351List = pages;
  var page351ListLen = page351List.length;
  for (var page351Index = 0; page351Index < page351ListLen; page351Index++) {
    var page351Data = page351List[page351Index];
    $templateAlias3({activePage: activePage, contentRenderer: contentRenderer, editable: editable, events: {deleteFieldClicked: _handleDeleteButtonClicked, duplicateButtonClicked: _handleDuplicateButtonClicked, fieldEdited: _handleFieldEdited, updatePage: _handleUpdatePage}, page: page351Data, pageId: page351Index, spritemap: spritemap, total: total}, opt_ijData);
  }
};
exports.__deltemplate__FormRenderer_pages_list = __deltemplate__FormRenderer_pages_list;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleDeleteButtonClicked: (*|null|undefined),
 *  _handleDuplicateButtonClicked: (*|null|undefined),
 *  _handleFieldEdited: (*|null|undefined),
 *  _handleUpdatePage: (*|null|undefined),
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

exports.render.params = ["spritemap","_handleChangePage","_handleDeleteButtonClicked","_handleDuplicateButtonClicked","_handleExpandedChanged","_handleFieldEdited","_handlePaginationLeftClicked","_handlePaginationRightClicked","_handlePageSettingsClicked","_handleSuccesPageChanged","_handleUpdatePage","activePage","defaultPageTitle","dropdownExpanded","editable","modeRenderer","pages","pageSettingsItems","paginationMode","successPageLabel","successPageSettings"];
exports.render.types = {"spritemap":"string","_handleChangePage":"any","_handleDeleteButtonClicked":"any","_handleDuplicateButtonClicked":"any","_handleExpandedChanged":"any","_handleFieldEdited":"any","_handlePaginationLeftClicked":"any","_handlePaginationRightClicked":"any","_handlePageSettingsClicked":"any","_handleSuccesPageChanged":"any","_handleUpdatePage":"any","activePage":"number","defaultPageTitle":"string","dropdownExpanded":"bool","editable":"bool","modeRenderer":"string","pages":"?","pageSettingsItems":"?","paginationMode":"string","successPageLabel":"string","successPageSettings":"?"};
templates = exports;
return exports;

});

class FormRenderer extends Component {}
Soy.register(FormRenderer, templates);
export { FormRenderer, templates };
export default templates;
/* jshint ignore:end */
