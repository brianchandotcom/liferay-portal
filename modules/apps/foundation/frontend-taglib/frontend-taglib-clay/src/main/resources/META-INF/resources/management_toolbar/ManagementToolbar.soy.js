/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from ManagementToolbar.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace com.liferay.frontend.taglib.clay.ManagementToolbar.
 * @public
 */

goog.module('com.liferay.frontend.taglib.clay.ManagementToolbar.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');

var $templateAlias1 = Soy.getTemplate('ClayManagementToolbar.incrementaldom', 'render');


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
  var _handleSelectPageCheckboxChanged = opt_data._handleSelectPageCheckboxChanged;
  /** @type {*|null|undefined} */
  var _handleDeselectAllClicked = opt_data._handleDeselectAllClicked;
  /** @type {*|null|undefined} */
  var _handleSelectAllClicked = opt_data._handleSelectAllClicked;
  /** @type {!Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), quickAction: boolean, separator: boolean, type: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var actionItems = soy.asserts.assertType(opt_data.actionItems == null || goog.isArray(opt_data.actionItems), 'actionItems', opt_data.actionItems, '!Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), quickAction: boolean, separator: boolean, type: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var clearResultsURL = soy.asserts.assertType(opt_data.clearResultsURL == null || (goog.isString(opt_data.clearResultsURL) || opt_data.clearResultsURL instanceof goog.soy.data.SanitizedContent), 'clearResultsURL', opt_data.clearResultsURL, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var contentRenderer = soy.asserts.assertType(opt_data.contentRenderer == null || (goog.isString(opt_data.contentRenderer) || opt_data.contentRenderer instanceof goog.soy.data.SanitizedContent), 'contentRenderer', opt_data.contentRenderer, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {null|undefined|{caption: (!goog.soy.data.SanitizedContent|string), helpText: (!goog.soy.data.SanitizedContent|string), maxPrimaryItems: number, maxSecondaryItems: number, maxTotalItems: number, primaryItems: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, secondaryItems: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), items: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, viewMoreURL: (!goog.soy.data.SanitizedContent|string),}} */
  var creationMenu = soy.asserts.assertType(opt_data.creationMenu == null || goog.isObject(opt_data.creationMenu), 'creationMenu', opt_data.creationMenu, 'null|undefined|{caption: (!goog.soy.data.SanitizedContent|string), helpText: (!goog.soy.data.SanitizedContent|string), maxPrimaryItems: number, maxSecondaryItems: number, maxTotalItems: number, primaryItems: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, secondaryItems: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), items: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, viewMoreURL: (!goog.soy.data.SanitizedContent|string),}');
  /** @type {boolean|null|undefined} */
  var disabled = soy.asserts.assertType(opt_data.disabled == null || (goog.isBoolean(opt_data.disabled) || opt_data.disabled === 1 || opt_data.disabled === 0), 'disabled', opt_data.disabled, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var elementClasses = soy.asserts.assertType(opt_data.elementClasses == null || (goog.isString(opt_data.elementClasses) || opt_data.elementClasses instanceof goog.soy.data.SanitizedContent), 'elementClasses', opt_data.elementClasses, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!Array<{checked: boolean, disabled: boolean, inputName: (!goog.soy.data.SanitizedContent|string), inputValue: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean, type: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var filterItems = soy.asserts.assertType(opt_data.filterItems == null || goog.isArray(opt_data.filterItems), 'filterItems', opt_data.filterItems, '!Array<{checked: boolean, disabled: boolean, inputName: (!goog.soy.data.SanitizedContent|string), inputValue: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean, type: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var id = soy.asserts.assertType(opt_data.id == null || (goog.isString(opt_data.id) || opt_data.id instanceof goog.soy.data.SanitizedContent), 'id', opt_data.id, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var searchActionURL = soy.asserts.assertType(opt_data.searchActionURL == null || (goog.isString(opt_data.searchActionURL) || opt_data.searchActionURL instanceof goog.soy.data.SanitizedContent), 'searchActionURL', opt_data.searchActionURL, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var searchFormName = soy.asserts.assertType(opt_data.searchFormName == null || (goog.isString(opt_data.searchFormName) || opt_data.searchFormName instanceof goog.soy.data.SanitizedContent), 'searchFormName', opt_data.searchFormName, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var searchInputName = soy.asserts.assertType(opt_data.searchInputName == null || (goog.isString(opt_data.searchInputName) || opt_data.searchInputName instanceof goog.soy.data.SanitizedContent), 'searchInputName', opt_data.searchInputName, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var searchValue = soy.asserts.assertType(opt_data.searchValue == null || (goog.isString(opt_data.searchValue) || opt_data.searchValue instanceof goog.soy.data.SanitizedContent), 'searchValue', opt_data.searchValue, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var selectable = soy.asserts.assertType(opt_data.selectable == null || (goog.isBoolean(opt_data.selectable) || opt_data.selectable === 1 || opt_data.selectable === 0), 'selectable', opt_data.selectable, 'boolean|null|undefined');
  /** @type {null|number|undefined} */
  var selectedItems = soy.asserts.assertType(opt_data.selectedItems == null || goog.isNumber(opt_data.selectedItems), 'selectedItems', opt_data.selectedItems, 'null|number|undefined');
  /** @type {boolean|null|undefined} */
  var showAdvancedSearch = soy.asserts.assertType(opt_data.showAdvancedSearch == null || (goog.isBoolean(opt_data.showAdvancedSearch) || opt_data.showAdvancedSearch === 1 || opt_data.showAdvancedSearch === 0), 'showAdvancedSearch', opt_data.showAdvancedSearch, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var showCreationMenu = soy.asserts.assertType(opt_data.showCreationMenu == null || (goog.isBoolean(opt_data.showCreationMenu) || opt_data.showCreationMenu === 1 || opt_data.showCreationMenu === 0), 'showCreationMenu', opt_data.showCreationMenu, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var showFiltersDoneButton = soy.asserts.assertType(opt_data.showFiltersDoneButton == null || (goog.isBoolean(opt_data.showFiltersDoneButton) || opt_data.showFiltersDoneButton === 1 || opt_data.showFiltersDoneButton === 0), 'showFiltersDoneButton', opt_data.showFiltersDoneButton, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var showInfoButton = soy.asserts.assertType(opt_data.showInfoButton == null || (goog.isBoolean(opt_data.showInfoButton) || opt_data.showInfoButton === 1 || opt_data.showInfoButton === 0), 'showInfoButton', opt_data.showInfoButton, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var showSearch = soy.asserts.assertType(opt_data.showSearch == null || (goog.isBoolean(opt_data.showSearch) || opt_data.showSearch === 1 || opt_data.showSearch === 0), 'showSearch', opt_data.showSearch, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var sortingOrder = soy.asserts.assertType(opt_data.sortingOrder == null || (goog.isString(opt_data.sortingOrder) || opt_data.sortingOrder instanceof goog.soy.data.SanitizedContent), 'sortingOrder', opt_data.sortingOrder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var sortingURL = soy.asserts.assertType(opt_data.sortingURL == null || (goog.isString(opt_data.sortingURL) || opt_data.sortingURL instanceof goog.soy.data.SanitizedContent), 'sortingURL', opt_data.sortingURL, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {null|number|undefined} */
  var totalItems = soy.asserts.assertType(opt_data.totalItems == null || goog.isNumber(opt_data.totalItems), 'totalItems', opt_data.totalItems, 'null|number|undefined');
  /** @type {!Array<{active: boolean, disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var viewTypes = soy.asserts.assertType(opt_data.viewTypes == null || goog.isArray(opt_data.viewTypes), 'viewTypes', opt_data.viewTypes, '!Array<{active: boolean, disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  $templateAlias1({actionItems: actionItems, clearResultsURL: clearResultsURL, contentRenderer: contentRenderer, creationMenu: creationMenu, disabled: disabled, elementClasses: elementClasses, events: {deselectAllClicked: _handleDeselectAllClicked, selectPageCheckboxChanged: _handleSelectPageCheckboxChanged, selectAllClicked: _handleSelectAllClicked}, filterItems: filterItems, id: id, ref: 'managementToolbar', searchActionURL: searchActionURL, searchFormName: searchFormName, searchInputName: searchInputName, searchValue: searchValue, selectable: selectable, selectedItems: selectedItems, showAdvancedSearch: showAdvancedSearch, showCreationMenu: showCreationMenu, showFiltersDoneButton: showFiltersDoneButton, showInfoButton: showInfoButton, showSearch: showSearch, sortingOrder: sortingOrder, sortingURL: sortingURL, spritemap: spritemap, totalItems: totalItems, viewTypes: viewTypes}, opt_ijData);
};
exports.render = $render;
/**
 * @typedef {{
 *  spritemap: (!goog.soy.data.SanitizedContent|string),
 *  _handleSelectPageCheckboxChanged: (*|null|undefined),
 *  _handleDeselectAllClicked: (*|null|undefined),
 *  _handleSelectAllClicked: (*|null|undefined),
 *  actionItems: (!Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), quickAction: boolean, separator: boolean, type: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  clearResultsURL: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  contentRenderer: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  creationMenu: (null|undefined|{caption: (!goog.soy.data.SanitizedContent|string), helpText: (!goog.soy.data.SanitizedContent|string), maxPrimaryItems: number, maxSecondaryItems: number, maxTotalItems: number, primaryItems: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, secondaryItems: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), items: !Array<{disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, label: (!goog.soy.data.SanitizedContent|string), separator: boolean,}>, viewMoreURL: (!goog.soy.data.SanitizedContent|string),}),
 *  disabled: (boolean|null|undefined),
 *  elementClasses: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  filterItems: (!Array<{checked: boolean, disabled: boolean, inputName: (!goog.soy.data.SanitizedContent|string), inputValue: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string), separator: boolean, type: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  id: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  searchActionURL: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  searchFormName: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  searchInputName: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  searchValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  selectable: (boolean|null|undefined),
 *  selectedItems: (null|number|undefined),
 *  showAdvancedSearch: (boolean|null|undefined),
 *  showCreationMenu: (boolean|null|undefined),
 *  showFiltersDoneButton: (boolean|null|undefined),
 *  showInfoButton: (boolean|null|undefined),
 *  showSearch: (boolean|null|undefined),
 *  sortingOrder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  sortingURL: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  totalItems: (null|number|undefined),
 *  viewTypes: (!Array<{active: boolean, disabled: boolean, href: (!goog.soy.data.SanitizedContent|string), icon: (!goog.soy.data.SanitizedContent|string), label: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'com.liferay.frontend.taglib.clay.ManagementToolbar.render';
}

exports.render.params = ["spritemap","_handleSelectPageCheckboxChanged","_handleDeselectAllClicked","_handleSelectAllClicked","actionItems","clearResultsURL","contentRenderer","creationMenu","disabled","elementClasses","filterItems","id","searchActionURL","searchFormName","searchInputName","searchValue","selectable","selectedItems","showAdvancedSearch","showCreationMenu","showFiltersDoneButton","showInfoButton","showSearch","sortingOrder","sortingURL","totalItems","viewTypes"];
exports.render.types = {"spritemap":"string","_handleSelectPageCheckboxChanged":"any","_handleDeselectAllClicked":"any","_handleSelectAllClicked":"any","actionItems":"list<[\n\t\tdisabled: bool,\n\t\thref: string,\n\t\ticon: string,\n\t\tlabel: string,\n\t\tquickAction: bool,\n\t\tseparator: bool,\n\t\ttype: string\n\t]>","clearResultsURL":"string","contentRenderer":"string","creationMenu":"[\n\t\tcaption: string,\n\t\thelpText: string,\n\t\tmaxPrimaryItems: int,\n\t\tmaxSecondaryItems: int,\n\t\tmaxTotalItems: int,\n\t\tprimaryItems: list<[\n\t\t\tdisabled: bool,\n\t\t\thref: string,\n\t\t\ticon: string,\n\t\t\tlabel: string,\n\t\t\tseparator: bool\n\t\t]>,\n\t\tsecondaryItems: list<[\n\t\t\tdisabled: bool,\n\t\t\thref: string,\n\t\t\ticon: string,\n\t\t\titems: list<[\n\t\t\t\tdisabled: bool,\n\t\t\t\thref: string,\n\t\t\t\ticon: string,\n\t\t\t\tlabel: string,\n\t\t\t\tseparator: bool\n\t\t\t]>,\n\t\t\tlabel: string,\n\t\t\tseparator: bool\n\t\t]>,\n\t\tviewMoreURL: string\n\t]","disabled":"bool","elementClasses":"string","filterItems":"list<[\n\t\tchecked: bool,\n\t\tdisabled: bool,\n\t\tinputName: string,\n\t\tinputValue: string,\n\t\tlabel: string,\n\t\tseparator: bool,\n\t\ttype: string\n\t]>","id":"string","searchActionURL":"string","searchFormName":"string","searchInputName":"string","searchValue":"string","selectable":"bool","selectedItems":"number","showAdvancedSearch":"bool","showCreationMenu":"bool","showFiltersDoneButton":"bool","showInfoButton":"bool","showSearch":"bool","sortingOrder":"string","sortingURL":"string","totalItems":"number","viewTypes":"list<[\n\t\tactive: bool,\n\t\tdisabled: bool,\n\t\thref: string,\n\t\ticon: string,\n\t\tlabel: string\n\t]>"};
templates = exports;
return exports;

});

class comliferayfrontendtaglibclayManagementToolbar extends Component {}
Soy.register(comliferayfrontendtaglibclayManagementToolbar, templates);
export { comliferayfrontendtaglibclayManagementToolbar, templates };
export default templates;
/* jshint ignore:end */
