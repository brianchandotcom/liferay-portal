/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from rule.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMRule.
 * @public
 */

goog.module('DDMRule.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {{
 *  actions: !Array<!goog.soy.data.SanitizedContent|string>,
 *  conditions: !Array<!goog.soy.data.SanitizedContent|string>,
 *  deleteIcon: function(),
 *  invalid: boolean,
 *  plusIcon: function(),
 *  strings: (?),
 *  logicalOperator: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var actions = soy.asserts.assertType(goog.isArray(opt_data.actions), 'actions', opt_data.actions, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var conditions = soy.asserts.assertType(goog.isArray(opt_data.conditions), 'conditions', opt_data.conditions, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {function()} */
  var deleteIcon = soy.asserts.assertType(goog.isFunction(opt_data.deleteIcon), 'deleteIcon', opt_data.deleteIcon, 'function()');
  /** @type {boolean} */
  var invalid = soy.asserts.assertType(goog.isBoolean(opt_data.invalid) || opt_data.invalid === 1 || opt_data.invalid === 0, 'invalid', opt_data.invalid, 'boolean');
  /** @type {function()} */
  var plusIcon = soy.asserts.assertType(goog.isFunction(opt_data.plusIcon), 'plusIcon', opt_data.plusIcon, 'function()');
  /** @type {?} */
  var strings = opt_data.strings;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var logicalOperator = soy.asserts.assertType(opt_data.logicalOperator == null || (goog.isString(opt_data.logicalOperator) || opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent), 'logicalOperator', opt_data.logicalOperator, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpen('div');
    incrementalDom.elementOpenStart('h2');
        incrementalDom.attr('class', 'form-builder-section-title text-default');
    incrementalDom.elementOpenEnd();
      soyIdom.print(strings.title);
    incrementalDom.elementClose('h2');
    incrementalDom.elementOpenStart('h4');
        incrementalDom.attr('class', 'text-default');
    incrementalDom.elementOpenEnd();
      soyIdom.print(strings.description);
    incrementalDom.elementClose('h4');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'ddm-form-body-content');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'liferay-ddm-form-builder-rule-condition-list liferay-ddm-form-rule-builder-timeline timeline ' + ((conditions.length) > 1 ? 'can-remove-item' : ''));
      incrementalDom.elementOpenEnd();
        var showLogicalOperator__soy1684 = (conditions.length) > 1 ? true : false;
        var param1687 = function() {
          $logicalOperatorDropDown({logicalOperator: strings[logicalOperator], strings: strings}, null, opt_ijData);
        };
        $rulesHeader({extraContent: param1687, logicalOperator: strings[logicalOperator], title: strings.condition}, null, opt_ijData);
        var condition1710List = conditions;
        var condition1710ListLen = condition1710List.length;
        if (condition1710ListLen > 0) {
          for (var condition1710Index = 0; condition1710Index < condition1710ListLen; condition1710Index++) {
            var condition1710Data = condition1710List[condition1710Index];
            $condition({deleteIcon: deleteIcon, if: strings.if, index: condition1710Index, logicalOperator: strings[logicalOperator]}, null, opt_ijData);
          }
        } else {
          $condition({deleteIcon: deleteIcon, if: strings.if, index: 0, logicalOperator: strings[logicalOperator]}, null, opt_ijData);
        }
      incrementalDom.elementClose('ul');
      $btnAddNewTimelineItem({cssClass: 'form-builder-rule-add-condition', plusIcon: plusIcon}, null, opt_ijData);
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'action-list liferay-ddm-form-builder-rule-action-list liferay-ddm-form-rule-builder-timeline timeline ' + ((actions.length) > 1 ? 'can-remove-item' : ''));
      incrementalDom.elementOpenEnd();
        $rulesHeader({logicalOperator: strings[logicalOperator], title: strings.actions}, null, opt_ijData);
        var action1736List = actions;
        var action1736ListLen = action1736List.length;
        if (action1736ListLen > 0) {
          for (var action1736Index = 0; action1736Index < action1736ListLen; action1736Index++) {
            var action1736Data = action1736List[action1736Index];
            $action({deleteIcon: deleteIcon, do: strings.do, index: action1736Index}, null, opt_ijData);
          }
        } else {
          $action({deleteIcon: deleteIcon, do: strings.do, index: 0}, null, opt_ijData);
        }
      incrementalDom.elementClose('ul');
      $btnAddNewTimelineItem({cssClass: 'form-builder-rule-add-action', plusIcon: plusIcon}, null, opt_ijData);
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'liferay-ddm-form-rule-builder-footer');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('button');
            incrementalDom.attr('class', 'btn btn-lg btn-primary ddm-button form-builder-rule-settings-save');
            if (invalid) {
              incrementalDom.attr('disabled', '');
            }
            incrementalDom.attr('type', 'button');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'form-builder-rule-settings-save-label');
          incrementalDom.elementOpenEnd();
            soyIdom.print(strings.save);
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('button');
        incrementalDom.elementOpenStart('button');
            incrementalDom.attr('class', 'btn btn-lg btn-cancel btn-secondary form-builder-rule-settings-cancel');
            incrementalDom.attr('type', 'button');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'lfr-btn-label');
          incrementalDom.elementOpenEnd();
            soyIdom.print(strings.cancel);
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('button');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  actions: !Array<!goog.soy.data.SanitizedContent|string>,
 *  conditions: !Array<!goog.soy.data.SanitizedContent|string>,
 *  deleteIcon: function(),
 *  invalid: boolean,
 *  plusIcon: function(),
 *  strings: (?),
 *  logicalOperator: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMRule.render';
}


/**
 * @param {{
 *  deleteIcon: function(),
 *  param$if: (!goog.soy.data.SanitizedContent|string),
 *  index: number,
 *  logicalOperator: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $condition(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var deleteIcon = soy.asserts.assertType(goog.isFunction(opt_data.deleteIcon), 'deleteIcon', opt_data.deleteIcon, 'function()');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var param$if = soy.asserts.assertType(goog.isString(opt_data.if) || opt_data.if instanceof goog.soy.data.SanitizedContent, 'if', opt_data.if, '!goog.soy.data.SanitizedContent|string');
  /** @type {number} */
  var index = soy.asserts.assertType(goog.isNumber(opt_data.index), 'index', opt_data.index, 'number');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var logicalOperator = soy.asserts.assertType(opt_data.logicalOperator == null || (goog.isString(opt_data.logicalOperator) || opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent), 'logicalOperator', opt_data.logicalOperator, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('li');
      incrementalDom.attr('class', 'form-builder-rule-condition-container-' + index + ' timeline-item');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'panel panel-default');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'flex-container panel-body');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpen('h4');
          soyIdom.print(param$if);
        incrementalDom.elementClose('h4');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'condition-if-' + index + ' form-group');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'condition-operator-' + index + ' form-group');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'condition-the-' + index + ' form-group');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'condition-type-value-' + index + ' form-group');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'condition-type-value-options-' + index + ' form-group');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'timeline-increment');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'timeline-icon');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'operator panel panel-default panel-inline');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'panel-body text-uppercase');
      incrementalDom.elementOpenEnd();
        soyIdom.print(logicalOperator);
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'container-trash');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-link condition-card-delete');
          incrementalDom.attr('data-card-id', index);
          incrementalDom.attr('href', 'javascript:;');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        deleteIcon();
      incrementalDom.elementClose('button');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('li');
}
exports.condition = $condition;
/**
 * @typedef {{
 *  deleteIcon: function(),
 *  param$if: (!goog.soy.data.SanitizedContent|string),
 *  index: number,
 *  logicalOperator: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$condition.Params;
if (goog.DEBUG) {
  $condition.soyTemplateName = 'DDMRule.condition';
}


/**
 * @param {{
 *  plusIcon: function(),
 *  cssClass: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $btnAddNewTimelineItem(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var plusIcon = soy.asserts.assertType(goog.isFunction(opt_data.plusIcon), 'plusIcon', opt_data.plusIcon, 'function()');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var cssClass = soy.asserts.assertType(opt_data.cssClass == null || (goog.isString(opt_data.cssClass) || opt_data.cssClass instanceof goog.soy.data.SanitizedContent), 'cssClass', opt_data.cssClass, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'addbutton-timeline-item');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'add-condition timeline-increment-icon');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('a');
          incrementalDom.attr('aria-role', 'button');
          incrementalDom.attr('class', 'btn-primary rounded-circle sticker sticker-primary form-builder-timeline-add-item ' + (cssClass || ''));
          incrementalDom.attr('href', 'javascript:;');
      incrementalDom.elementOpenEnd();
        plusIcon();
      incrementalDom.elementClose('a');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.btnAddNewTimelineItem = $btnAddNewTimelineItem;
/**
 * @typedef {{
 *  plusIcon: function(),
 *  cssClass: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$btnAddNewTimelineItem.Params;
if (goog.DEBUG) {
  $btnAddNewTimelineItem.soyTemplateName = 'DDMRule.btnAddNewTimelineItem';
}


/**
 * @param {{
 *  title: (!goog.soy.data.SanitizedContent|string),
 *  extraContent: (function()|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $rulesHeader(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var title = soy.asserts.assertType(goog.isString(opt_data.title) || opt_data.title instanceof goog.soy.data.SanitizedContent, 'title', opt_data.title, '!goog.soy.data.SanitizedContent|string');
  /** @type {function()|null|undefined} */
  var extraContent = soy.asserts.assertType(opt_data.extraContent == null || goog.isFunction(opt_data.extraContent), 'extraContent', opt_data.extraContent, 'function()|null|undefined');
  incrementalDom.elementOpenStart('li');
      incrementalDom.attr('class', 'timeline-item');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'panel panel-default');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'flex-container panel-body');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'h4 panel-title');
        incrementalDom.elementOpenEnd();
          soyIdom.print(title);
        incrementalDom.elementClose('div');
        if (extraContent) {
          extraContent();
        }
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'timeline-increment');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'timeline-icon');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('li');
}
exports.rulesHeader = $rulesHeader;
/**
 * @typedef {{
 *  title: (!goog.soy.data.SanitizedContent|string),
 *  extraContent: (function()|null|undefined)
 * }}
 */
$rulesHeader.Params;
if (goog.DEBUG) {
  $rulesHeader.soyTemplateName = 'DDMRule.rulesHeader';
}


/**
 * @param {{
 *  strings: (?),
 *  logicalOperator: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $logicalOperatorDropDown(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var strings = opt_data.strings;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var logicalOperator = soy.asserts.assertType(opt_data.logicalOperator == null || (goog.isString(opt_data.logicalOperator) || opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent), 'logicalOperator', opt_data.logicalOperator, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'btn-group dropdown');
      incrementalDom.attr('style', 'block');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('button');
        incrementalDom.attr('class', 'btn btn-default dropdown-toggle dropdown-toggle-operator text-uppercase');
        incrementalDom.attr('data-toggle', 'dropdown');
        incrementalDom.attr('type', 'button');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('span');
          incrementalDom.attr('class', 'dropdown-toggle-selected-value');
      incrementalDom.elementOpenEnd();
        soyIdom.print(logicalOperator);
      incrementalDom.elementClose('span');
      incrementalDom.text(' ');
      incrementalDom.elementOpenStart('span');
          incrementalDom.attr('class', 'caret');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('span');
    incrementalDom.elementClose('button');
    incrementalDom.elementOpenStart('ul');
        incrementalDom.attr('class', 'dropdown-menu');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'logic-operator text-uppercase');
          incrementalDom.attr('data-logical-operator-value', 'or');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('a');
            incrementalDom.attr('href', '#');
        incrementalDom.elementOpenEnd();
          soyIdom.print(strings['or']);
        incrementalDom.elementClose('a');
      incrementalDom.elementClose('li');
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'divider');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('li');
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'logic-operator text-uppercase');
          incrementalDom.attr('data-logical-operator-value', 'and');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('a');
            incrementalDom.attr('href', '#');
        incrementalDom.elementOpenEnd();
          soyIdom.print(strings['and']);
        incrementalDom.elementClose('a');
      incrementalDom.elementClose('li');
    incrementalDom.elementClose('ul');
  incrementalDom.elementClose('div');
}
exports.logicalOperatorDropDown = $logicalOperatorDropDown;
/**
 * @typedef {{
 *  strings: (?),
 *  logicalOperator: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$logicalOperatorDropDown.Params;
if (goog.DEBUG) {
  $logicalOperatorDropDown.soyTemplateName = 'DDMRule.logicalOperatorDropDown';
}


/**
 * @param {{
 *  deleteIcon: function(),
 *  param$do: (!goog.soy.data.SanitizedContent|string),
 *  index: number
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $action(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var deleteIcon = soy.asserts.assertType(goog.isFunction(opt_data.deleteIcon), 'deleteIcon', opt_data.deleteIcon, 'function()');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var param$do = soy.asserts.assertType(goog.isString(opt_data.do) || opt_data.do instanceof goog.soy.data.SanitizedContent, 'do', opt_data.do, '!goog.soy.data.SanitizedContent|string');
  /** @type {number} */
  var index = soy.asserts.assertType(goog.isNumber(opt_data.index), 'index', opt_data.index, 'number');
  incrementalDom.elementOpenStart('li');
      incrementalDom.attr('class', 'form-builder-rule-action-container-' + index + ' timeline-item');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'panel panel-default');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'panel-body');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'row');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'col-md-12 flex-container ');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpen('h4');
              soyIdom.print(param$do);
            incrementalDom.elementClose('h4');
            incrementalDom.elementOpenStart('div');
                incrementalDom.attr('class', 'action-' + index + ' form-group');
            incrementalDom.elementOpenEnd();
            incrementalDom.elementClose('div');
            incrementalDom.elementOpenStart('div');
                incrementalDom.attr('class', 'container-target-action form-group target-' + index);
            incrementalDom.elementOpenEnd();
            incrementalDom.elementClose('div');
          incrementalDom.elementClose('div');
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'action-rule-data-provider row');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'col-md-12 no-padding');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('div');
                incrementalDom.attr('class', 'additional-info-' + index);
            incrementalDom.elementOpenEnd();
            incrementalDom.elementClose('div');
          incrementalDom.elementClose('div');
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'timeline-increment');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'timeline-icon');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'container-trash');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-link action-card-delete');
          incrementalDom.attr('data-card-id', index);
          incrementalDom.attr('href', 'javascript:;');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        deleteIcon();
      incrementalDom.elementClose('button');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('li');
}
exports.action = $action;
/**
 * @typedef {{
 *  deleteIcon: function(),
 *  param$do: (!goog.soy.data.SanitizedContent|string),
 *  index: number
 * }}
 */
$action.Params;
if (goog.DEBUG) {
  $action.soyTemplateName = 'DDMRule.action';
}

exports.render.params = ["actions","conditions","deleteIcon","invalid","plusIcon","strings","logicalOperator"];
exports.render.types = {"actions":"list<string>","conditions":"list<string>","deleteIcon":"html","invalid":"bool","plusIcon":"html","strings":"?","logicalOperator":"string"};
exports.condition.params = ["deleteIcon","if","index","logicalOperator"];
exports.condition.types = {"deleteIcon":"html","if":"string","index":"int","logicalOperator":"string"};
exports.btnAddNewTimelineItem.params = ["plusIcon","cssClass"];
exports.btnAddNewTimelineItem.types = {"plusIcon":"html","cssClass":"string"};
exports.rulesHeader.params = ["title","extraContent"];
exports.rulesHeader.types = {"title":"string","extraContent":"html"};
exports.logicalOperatorDropDown.params = ["strings","logicalOperator"];
exports.logicalOperatorDropDown.types = {"strings":"?","logicalOperator":"string"};
exports.action.params = ["deleteIcon","do","index"];
exports.action.types = {"deleteIcon":"html","do":"string","index":"int"};
templates = exports;
return exports;

});

class DDMRule extends Component {}
Soy.register(DDMRule, templates);
export { DDMRule, templates };
export default templates;
/* jshint ignore:end */
