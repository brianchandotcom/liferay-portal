/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from RuleEditor.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace RuleEditor.
 * @hassoydelcall {PageRenderer.RegisterFieldType.idom}
 * @public
 */

goog.module('RuleEditor.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {$render.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $render = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>} */
  var conditions = soy.asserts.assertType(goog.isArray(opt_data.conditions), 'conditions', opt_data.conditions, '!Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>');
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>} */
  var firstOperandList = soy.asserts.assertType(goog.isArray(opt_data.firstOperandList), 'firstOperandList', opt_data.firstOperandList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var logicalOperator = soy.asserts.assertType(goog.isString(opt_data.logicalOperator) || opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent, 'logicalOperator', opt_data.logicalOperator, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {?} */
  var strings = opt_data.strings;
  /** @type {*|null|undefined} */
  var _handleFirstOperandSelection = opt_data._handleFirstOperandSelection;
  /** @type {*|null|undefined} */
  var _handleOperatorSelection = opt_data._handleOperatorSelection;
  /** @type {*|null|undefined} */
  var _handleSecondOperandSelection = opt_data._handleSecondOperandSelection;
  /** @type {*|null|undefined} */
  var _handleTypeSelection = opt_data._handleTypeSelection;
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var operators = soy.asserts.assertType(opt_data.operators == null || goog.isArray(opt_data.operators), 'operators', opt_data.operators, '!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var secondOperandTypeList = soy.asserts.assertType(opt_data.secondOperandTypeList == null || goog.isArray(opt_data.secondOperandTypeList), 'secondOperandTypeList', opt_data.secondOperandTypeList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var secondOperandTypeSelectedList = soy.asserts.assertType(opt_data.secondOperandTypeSelectedList == null || goog.isArray(opt_data.secondOperandTypeSelectedList), 'secondOperandTypeSelectedList', opt_data.secondOperandTypeSelectedList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var spritemap = soy.asserts.assertType(opt_data.spritemap == null || (goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent), 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-builder-rule-builder');
  incrementalDom.elementOpenEnd();
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
  incrementalDom.elementOpenStart('ul');
      incrementalDom.attr('class', 'liferay-ddm-form-builder-rule-condition-list liferay-ddm-form-rule-builder-timeline timeline ' + ((conditions.length) > 1 ? 'can-remove-item' : ''));
  incrementalDom.elementOpenEnd();
  var enableLogicalOperator__soy1398 = (conditions.length) > 1 ? true : false;
  var param1406 = function() {
    $logicalOperatorDropDown({logicalOperator: strings[logicalOperator], strings: strings, enableLogicalOperator: enableLogicalOperator__soy1398}, opt_ijData);
  };
  $rulesHeader({extraContent: param1406, logicalOperator: strings[logicalOperator], title: strings.condition}, opt_ijData);
  var condition1411List = conditions;
  var condition1411ListLen = condition1411List.length;
  if (condition1411ListLen > 0) {
    for (var condition1411Index = 0; condition1411Index < condition1411ListLen; condition1411Index++) {
      var condition1411Data = condition1411List[condition1411Index];
      $condition({_handleFirstOperandSelection: _handleFirstOperandSelection, _handleOperatorSelection: _handleOperatorSelection, _handleSecondOperandSelection: _handleSecondOperandSelection, _handleTypeSelection: _handleTypeSelection, firstOperandList: firstOperandList, firstOperandValueSelected: condition1411Data.operands[0].value, if: strings.if, index: condition1411Index, logicalOperator: strings[logicalOperator], operators: operators, operatorSelected: condition1411Data.operator, readOnly: readOnly, secondOperandTypeList: secondOperandTypeList, secondOperandTypeName: '', secondOperandTypeSelected: condition1411Data.operands[0].type, secondOperandTypeSelectedList: secondOperandTypeSelectedList, secondOperandTypeValue: (condition1411Data.operands[1] != null) ? condition1411Data.operands[1].value : '', spritemap: spritemap}, opt_ijData);
    }
  } else {
    $condition({_handleFirstOperandSelection: _handleFirstOperandSelection, _handleOperatorSelection: _handleOperatorSelection, _handleSecondOperandSelection: _handleSecondOperandSelection, _handleTypeSelection: _handleTypeSelection, firstOperandList: firstOperandList, if: strings.if, index: 0, logicalOperator: strings[logicalOperator], operators: operators, readOnly: readOnly, secondOperandTypeList: secondOperandTypeList, secondOperandTypeName: '', spritemap: spritemap}, opt_ijData);
  }
  incrementalDom.elementClose('ul');
  incrementalDom.elementClose('div');
};
exports.render = $render;
/**
 * @typedef {{
 *  conditions: !Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>,
 *  firstOperandList: !Array<{name: (!goog.soy.data.SanitizedContent|string), options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>,
 *  logicalOperator: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  strings: ?,
 *  _handleFirstOperandSelection: (*|null|undefined),
 *  _handleOperatorSelection: (*|null|undefined),
 *  _handleSecondOperandSelection: (*|null|undefined),
 *  _handleTypeSelection: (*|null|undefined),
 *  operators: (!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  secondOperandTypeList: (!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  secondOperandTypeSelectedList: (!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  spritemap: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'RuleEditor.render';
}


/**
 * @param {$condition.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $condition = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>} */
  var firstOperandList = soy.asserts.assertType(goog.isArray(opt_data.firstOperandList), 'firstOperandList', opt_data.firstOperandList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var param$if = soy.asserts.assertType(goog.isString(opt_data.if) || opt_data.if instanceof goog.soy.data.SanitizedContent, 'if', opt_data.if, '!goog.soy.data.SanitizedContent|string');
  /** @type {number} */
  var index = soy.asserts.assertType(goog.isNumber(opt_data.index), 'index', opt_data.index, 'number');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {*|null|undefined} */
  var _handleFirstOperandSelection = opt_data._handleFirstOperandSelection;
  /** @type {*|null|undefined} */
  var _handleOperatorSelection = opt_data._handleOperatorSelection;
  /** @type {*|null|undefined} */
  var _handleSecondOperandSelection = opt_data._handleSecondOperandSelection;
  /** @type {*|null|undefined} */
  var _handleTypeSelection = opt_data._handleTypeSelection;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var firstOperandValueSelected = soy.asserts.assertType(opt_data.firstOperandValueSelected == null || (goog.isString(opt_data.firstOperandValueSelected) || opt_data.firstOperandValueSelected instanceof goog.soy.data.SanitizedContent), 'firstOperandValueSelected', opt_data.firstOperandValueSelected, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var operators = soy.asserts.assertType(opt_data.operators == null || goog.isArray(opt_data.operators), 'operators', opt_data.operators, '!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var operatorSelected = soy.asserts.assertType(opt_data.operatorSelected == null || (goog.isString(opt_data.operatorSelected) || opt_data.operatorSelected instanceof goog.soy.data.SanitizedContent), 'operatorSelected', opt_data.operatorSelected, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var secondOperandTypeName = soy.asserts.assertType(opt_data.secondOperandTypeName == null || (goog.isString(opt_data.secondOperandTypeName) || opt_data.secondOperandTypeName instanceof goog.soy.data.SanitizedContent), 'secondOperandTypeName', opt_data.secondOperandTypeName, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var secondOperandTypeList = soy.asserts.assertType(opt_data.secondOperandTypeList == null || goog.isArray(opt_data.secondOperandTypeList), 'secondOperandTypeList', opt_data.secondOperandTypeList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var secondOperandTypeSelectedList = soy.asserts.assertType(opt_data.secondOperandTypeSelectedList == null || goog.isArray(opt_data.secondOperandTypeSelectedList), 'secondOperandTypeSelectedList', opt_data.secondOperandTypeSelectedList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {*|null|undefined} */
  var secondOperandTypeValue = opt_data.secondOperandTypeValue;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var spritemap = soy.asserts.assertType(opt_data.spritemap == null || (goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent), 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('li');
      incrementalDom.attr('class', 'form-builder-rule-condition-container timeline-item');
      incrementalDom.attr('condition-index', index);
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'panel panel-default');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'flex-container panel-body');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group-autofit');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group-item form-group-item-label form-group-item-shrink');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpen('h4');
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'text-truncate-inline');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'text-truncate');
  incrementalDom.elementOpenEnd();
  soyIdom.print(param$if);
  incrementalDom.elementClose('span');
  incrementalDom.elementClose('span');
  incrementalDom.elementClose('h4');
  incrementalDom.elementClose('div');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'condition-if form-group-item');
      incrementalDom.attr('condition-if-index', index);
  incrementalDom.elementOpenEnd();
  soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), 'select', false)({events: {fieldEdited: _handleFirstOperandSelection}, options: firstOperandList, ref: 'conditionIf', spritemap: spritemap, value: [firstOperandValueSelected]}, opt_ijData);
  incrementalDom.elementClose('div');
  $operators({_handleOperatorSelection: _handleOperatorSelection, index: index, operators: operators, operatorSelected: operatorSelected, spritemap: spritemap}, opt_ijData);
  $secondOperandTypeList({_handleTypeSelection: _handleTypeSelection, index: index, operatorSelected: operatorSelected, secondOperandTypeList: secondOperandTypeList, secondOperandTypeSelectedList: secondOperandTypeSelectedList, spritemap: spritemap}, opt_ijData);
  if ((secondOperandTypeSelectedList != null) && (secondOperandTypeSelectedList[index] != null)) {
    var textFieldVisible__soy1502 = secondOperandTypeSelectedList[index].name == 'text' ? true : false;
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'condition-type-value form-group-item ' + (!textFieldVisible__soy1502 ? 'hide' : ''));
        incrementalDom.attr('condition-type-value-index', index);
    incrementalDom.elementOpenEnd();
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), 'text', false)({events: {fieldEdited: _handleSecondOperandSelection}, name: secondOperandTypeName, readOnly: readOnly, ref: 'typeValueInput', spritemap: spritemap, value: secondOperandTypeValue}, opt_ijData);
    incrementalDom.elementClose('div');
    var selectFieldVisible__soy1518 = secondOperandTypeSelectedList[index].name == 'field' ? true : false;
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'condition-type-value-select form-group-item ' + (!selectFieldVisible__soy1518 ? 'hide' : ''));
        incrementalDom.attr('condition-type-value-select-index', index);
    incrementalDom.elementOpenEnd();
    soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), 'select', false)({events: {fieldEdited: _handleSecondOperandSelection}, options: firstOperandList, ref: 'typeValueSelect', spritemap: spritemap, value: [secondOperandTypeValue]}, opt_ijData);
    incrementalDom.elementClose('div');
    var selectOptionsFieldVisible__soy1533 = secondOperandTypeSelectedList[index].name == 'select' ? true : false;
    var operand1535List = firstOperandList;
    var operand1535ListLen = operand1535List.length;
    for (var operand1535Index = 0; operand1535Index < operand1535ListLen; operand1535Index++) {
      var operand1535Data = operand1535List[operand1535Index];
      if (operand1535Data.value == firstOperandValueSelected) {
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'condition-type-value-select-options form-group-item ' + (!selectOptionsFieldVisible__soy1533 ? 'hide' : ''));
            incrementalDom.attr('condition-type-value-select-options-index', index);
        incrementalDom.elementOpenEnd();
        soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), 'select', false)({events: {fieldEdited: _handleSecondOperandSelection}, options: operand1535Data.options, ref: 'typeValueSelectOptions', spritemap: spritemap, value: [secondOperandTypeValue]}, opt_ijData);
        incrementalDom.elementClose('div');
      }
    }
  }
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
  incrementalDom.elementClose('li');
};
exports.condition = $condition;
/**
 * @typedef {{
 *  firstOperandList: !Array<{name: (!goog.soy.data.SanitizedContent|string), options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>,
 *  param$if: (!goog.soy.data.SanitizedContent|string),
 *  index: number,
 *  readOnly: boolean,
 *  _handleFirstOperandSelection: (*|null|undefined),
 *  _handleOperatorSelection: (*|null|undefined),
 *  _handleSecondOperandSelection: (*|null|undefined),
 *  _handleTypeSelection: (*|null|undefined),
 *  firstOperandValueSelected: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  operators: (!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  operatorSelected: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  secondOperandTypeName: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  secondOperandTypeList: (!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  secondOperandTypeSelectedList: (!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  secondOperandTypeValue: (*|null|undefined),
 *  spritemap: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$condition.Params;
if (goog.DEBUG) {
  $condition.soyTemplateName = 'RuleEditor.condition';
}


/**
 * @param {$operators.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $operators = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var index = soy.asserts.assertType(goog.isNumber(opt_data.index), 'index', opt_data.index, 'number');
  /** @type {*|null|undefined} */
  var _handleOperatorSelection = opt_data._handleOperatorSelection;
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var operators = soy.asserts.assertType(opt_data.operators == null || goog.isArray(opt_data.operators), 'operators', opt_data.operators, '!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var operatorSelected = soy.asserts.assertType(opt_data.operatorSelected == null || (goog.isString(opt_data.operatorSelected) || opt_data.operatorSelected instanceof goog.soy.data.SanitizedContent), 'operatorSelected', opt_data.operatorSelected, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var spritemap = soy.asserts.assertType(opt_data.spritemap == null || (goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent), 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var readOnly__soy1564 = operators && (operators.length) > 0 ? false : true;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'condition-operator form-group-item');
      incrementalDom.attr('condition-operator-index', index);
  incrementalDom.elementOpenEnd();
  soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), 'select', false)({events: {fieldEdited: _handleOperatorSelection}, options: operators, readOnly: readOnly__soy1564, ref: 'conditionOperator', spritemap: spritemap, value: [operatorSelected]}, opt_ijData);
  incrementalDom.elementClose('div');
};
exports.operators = $operators;
/**
 * @typedef {{
 *  index: number,
 *  _handleOperatorSelection: (*|null|undefined),
 *  operators: (!Array<{name: (!goog.soy.data.SanitizedContent|string), parameterTypes: !Array<!goog.soy.data.SanitizedContent|string>, returnType: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  operatorSelected: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  spritemap: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$operators.Params;
if (goog.DEBUG) {
  $operators.soyTemplateName = 'RuleEditor.operators';
}


/**
 * @param {$secondOperandTypeList.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $secondOperandTypeList = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var index = soy.asserts.assertType(goog.isNumber(opt_data.index), 'index', opt_data.index, 'number');
  /** @type {*|null|undefined} */
  var _handleTypeSelection = opt_data._handleTypeSelection;
  /** @type {*|null|undefined} */
  var operatorSelected = opt_data.operatorSelected;
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var secondOperandTypeList = soy.asserts.assertType(opt_data.secondOperandTypeList == null || goog.isArray(opt_data.secondOperandTypeList), 'secondOperandTypeList', opt_data.secondOperandTypeList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined} */
  var secondOperandTypeSelectedList = soy.asserts.assertType(opt_data.secondOperandTypeSelectedList == null || goog.isArray(opt_data.secondOperandTypeSelectedList), 'secondOperandTypeSelectedList', opt_data.secondOperandTypeSelectedList, '!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var spritemap = soy.asserts.assertType(opt_data.spritemap == null || (goog.isString(opt_data.spritemap) || opt_data.spritemap instanceof goog.soy.data.SanitizedContent), 'spritemap', opt_data.spritemap, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var visible__soy1584 = operatorSelected && (secondOperandTypeSelectedList != null) && (secondOperandTypeSelectedList[index] != null) ? true : false;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'condition-type form-group-item ' + (!visible__soy1584 ? 'hide' : ''));
      incrementalDom.attr('condition-type-index', index);
  incrementalDom.elementOpenEnd();
  soy.$$getDelegateFn(soy.$$getDelTemplateId('PageRenderer.RegisterFieldType.idom'), 'select', false)({events: {fieldEdited: _handleTypeSelection}, options: secondOperandTypeList, ref: 'type', spritemap: spritemap, value: [visible__soy1584 ? (secondOperandTypeSelectedList == null ? null : secondOperandTypeSelectedList[index].value) : '']}, opt_ijData);
  incrementalDom.elementClose('div');
};
exports.secondOperandTypeList = $secondOperandTypeList;
/**
 * @typedef {{
 *  index: number,
 *  _handleTypeSelection: (*|null|undefined),
 *  operatorSelected: (*|null|undefined),
 *  secondOperandTypeList: (!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  secondOperandTypeSelectedList: (!Array<{name: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>|null|undefined),
 *  spritemap: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$secondOperandTypeList.Params;
if (goog.DEBUG) {
  $secondOperandTypeList.soyTemplateName = 'RuleEditor.secondOperandTypeList';
}


/**
 * @param {$logicalOperatorDropDown.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $logicalOperatorDropDown = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var strings = opt_data.strings;
  /** @type {boolean|null|undefined} */
  var enableLogicalOperator = soy.asserts.assertType(opt_data.enableLogicalOperator == null || (goog.isBoolean(opt_data.enableLogicalOperator) || opt_data.enableLogicalOperator === 1 || opt_data.enableLogicalOperator === 0), 'enableLogicalOperator', opt_data.enableLogicalOperator, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var logicalOperator = soy.asserts.assertType(opt_data.logicalOperator == null || (goog.isString(opt_data.logicalOperator) || opt_data.logicalOperator instanceof goog.soy.data.SanitizedContent), 'logicalOperator', opt_data.logicalOperator, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var attributes__soy1609 = function() {
    incrementalDom.attr('class', 'btn btn-default dropdown-toggle dropdown-toggle-operator text-uppercase');
    incrementalDom.attr('data-toggle', 'dropdown');
    incrementalDom.attr('type', 'button');
    if (!enableLogicalOperator) {
      incrementalDom.attr('disabled', '');
    }
  };
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'btn-group dropdown');
      incrementalDom.attr('style', 'block');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('button');
      attributes__soy1609();
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
};
exports.logicalOperatorDropDown = $logicalOperatorDropDown;
/**
 * @typedef {{
 *  strings: ?,
 *  enableLogicalOperator: (boolean|null|undefined),
 *  logicalOperator: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$logicalOperatorDropDown.Params;
if (goog.DEBUG) {
  $logicalOperatorDropDown.soyTemplateName = 'RuleEditor.logicalOperatorDropDown';
}


/**
 * @param {$rulesHeader.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $rulesHeader = function(opt_data, opt_ijData, opt_ijData_deprecated) {
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
};
exports.rulesHeader = $rulesHeader;
/**
 * @typedef {{
 *  title: (!goog.soy.data.SanitizedContent|string),
 *  extraContent: (function()|null|undefined),
 * }}
 */
$rulesHeader.Params;
if (goog.DEBUG) {
  $rulesHeader.soyTemplateName = 'RuleEditor.rulesHeader';
}

exports.render.params = ["conditions","firstOperandList","logicalOperator","readOnly","strings","_handleFirstOperandSelection","_handleOperatorSelection","_handleSecondOperandSelection","_handleTypeSelection","operators","secondOperandTypeList","secondOperandTypeSelectedList","spritemap"];
exports.render.types = {"conditions":"list<[\n\t\t\toperator: string,\n\t\t\toperands: list<[\n\t\t\t\ttype: string,\n\t\t\t\tlabel: string,\n\t\t\t\tvalue: string\n\t\t\t]>\n\t\t]>\n\t","firstOperandList":"list<[\n\t\t\tvalue: string,\n\t\t\ttype: string,\n\t\t\tname: string,\n\t\t\toptions: list<[\n\t\t\t\tlabel: string,\n\t\t\t\tvalue: string\n\t\t\t]>\n\t\t]>\n\t","logicalOperator":"string","readOnly":"bool","strings":"?","_handleFirstOperandSelection":"any","_handleOperatorSelection":"any","_handleSecondOperandSelection":"any","_handleTypeSelection":"any","operators":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string,\n\t\t\tparameterTypes: list<string>,\n\t\t\treturnType: string\n\t\t]>\n\t","secondOperandTypeList":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string\n\t\t]>\n\t","secondOperandTypeSelectedList":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string\n\t\t]>\n\t","spritemap":"string"};
exports.condition.params = ["firstOperandList","if","index","readOnly","_handleFirstOperandSelection","_handleOperatorSelection","_handleSecondOperandSelection","_handleTypeSelection","firstOperandValueSelected","operators","operatorSelected","secondOperandTypeName","secondOperandTypeList","secondOperandTypeSelectedList","secondOperandTypeValue","spritemap"];
exports.condition.types = {"firstOperandList":"list<[\n\t\t\tvalue: string,\n\t\t\ttype: string,\n\t\t\tname: string,\n\t\t\toptions: list<[\n\t\t\t\tlabel: string,\n\t\t\t\tvalue: string\n\t\t\t]>\n\t\t]>\n\t","if":"string","index":"int","readOnly":"bool","_handleFirstOperandSelection":"any","_handleOperatorSelection":"any","_handleSecondOperandSelection":"any","_handleTypeSelection":"any","firstOperandValueSelected":"string","operators":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string,\n\t\t\tparameterTypes: list<string>,\n\t\t\treturnType: string\n\t\t]>\n\t","operatorSelected":"string","secondOperandTypeName":"string ","secondOperandTypeList":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string\n\t\t]>\n\t","secondOperandTypeSelectedList":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string\n\t\t]>\n\t","secondOperandTypeValue":"any ","spritemap":"string"};
exports.operators.params = ["index","_handleOperatorSelection","operators","operatorSelected","spritemap"];
exports.operators.types = {"index":"int","_handleOperatorSelection":"any","operators":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string,\n\t\t\tparameterTypes: list<string>,\n\t\t\treturnType: string\n\t\t]>\n\t","operatorSelected":"string","spritemap":"string"};
exports.secondOperandTypeList.params = ["index","_handleTypeSelection","operatorSelected","secondOperandTypeList","secondOperandTypeSelectedList","spritemap"];
exports.secondOperandTypeList.types = {"index":"int","_handleTypeSelection":"any","operatorSelected":"any","secondOperandTypeList":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string\n\t\t]>\n\t","secondOperandTypeSelectedList":"list<[\n\t\t\tvalue: string,\n\t\t\tname: string\n\t\t]>\n\t","spritemap":"string"};
exports.logicalOperatorDropDown.params = ["strings","enableLogicalOperator","logicalOperator"];
exports.logicalOperatorDropDown.types = {"strings":"?","enableLogicalOperator":"bool","logicalOperator":"string"};
exports.rulesHeader.params = ["title","extraContent"];
exports.rulesHeader.types = {"title":"string","extraContent":"html"};
templates = exports;
return exports;

});

class RuleEditor extends Component {}
Soy.register(RuleEditor, templates);
export { RuleEditor, templates };
export default templates;
/* jshint ignore:end */
