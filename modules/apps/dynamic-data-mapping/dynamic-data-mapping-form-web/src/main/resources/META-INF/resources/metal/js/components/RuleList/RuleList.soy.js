/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from RuleList.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace LayoutRule.
 * @public
 */

goog.module('LayoutRule.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
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
  /** @type {!Array<{actions: !Array<{action: (!goog.soy.data.SanitizedContent|string), expression: ?, label: (!goog.soy.data.SanitizedContent|string), target: (!goog.soy.data.SanitizedContent|string),}>, conditions: !Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>, logicalOperator: (!goog.soy.data.SanitizedContent|string),}>} */
  var rules = soy.asserts.assertType(goog.isArray(opt_data.rules), 'rules', opt_data.rules, '!Array<{actions: !Array<{action: (!goog.soy.data.SanitizedContent|string), expression: ?, label: (!goog.soy.data.SanitizedContent|string), target: (!goog.soy.data.SanitizedContent|string),}>, conditions: !Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>, logicalOperator: (!goog.soy.data.SanitizedContent|string),}>');
  /** @type {?} */
  var strings = opt_data.strings;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-builder-rule-builder-container');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('h1');
      incrementalDom.attr('class', 'form-builder-section-title text-default');
  incrementalDom.elementOpenEnd();
  soyIdom.print(strings.rules);
  incrementalDom.elementClose('h1');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'liferay-ddm-form-rule-rules-list-container');
  incrementalDom.elementOpenEnd();
  $rule_list({rules: rules, strings: strings}, opt_ijData);
  incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
};
exports.render = $render;
/**
 * @typedef {{
 *  rules: !Array<{actions: !Array<{action: (!goog.soy.data.SanitizedContent|string), expression: ?, label: (!goog.soy.data.SanitizedContent|string), target: (!goog.soy.data.SanitizedContent|string),}>, conditions: !Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>, logicalOperator: (!goog.soy.data.SanitizedContent|string),}>,
 *  strings: ?,
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'LayoutRule.render';
}


/**
 * @param {$rule_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $rule_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{actions: !Array<{action: (!goog.soy.data.SanitizedContent|string), expression: ?, label: (!goog.soy.data.SanitizedContent|string), target: (!goog.soy.data.SanitizedContent|string),}>, conditions: !Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>, logicalOperator: (!goog.soy.data.SanitizedContent|string),}>} */
  var rules = soy.asserts.assertType(goog.isArray(opt_data.rules), 'rules', opt_data.rules, '!Array<{actions: !Array<{action: (!goog.soy.data.SanitizedContent|string), expression: ?, label: (!goog.soy.data.SanitizedContent|string), target: (!goog.soy.data.SanitizedContent|string),}>, conditions: !Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>, logicalOperator: (!goog.soy.data.SanitizedContent|string),}>');
  /** @type {?} */
  var strings = opt_data.strings;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-builder-rule-list');
  incrementalDom.elementOpenEnd();
  if ((rules.length) > 0) {
    incrementalDom.elementOpenStart('ul');
        incrementalDom.attr('class', 'ddm-form-body-content form-builder-rule-builder-rules-list tabular-list-group');
    incrementalDom.elementOpenEnd();
    var rule1985List = rules;
    var rule1985ListLen = rule1985List.length;
    for (var rule1985Index = 0; rule1985Index < rule1985ListLen; rule1985Index++) {
      var rule1985Data = rule1985List[rule1985Index];
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'list-group-item');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'clamp-horizontal list-group-item-content');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('p');
          incrementalDom.attr('class', 'form-builder-rule-builder-rule-description text-default');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpen('b');
      soyIdom.print(strings.if);
      incrementalDom.text(' ');
      incrementalDom.elementClose('b');
      var condition1990List = rule1985Data.conditions;
      var condition1990ListLen = condition1990List.length;
      for (var condition1990Index = 0; condition1990Index < condition1990ListLen; condition1990Index++) {
        var condition1990Data = condition1990List[condition1990Index];
        $condition({operandType: condition1990Data.operands[0].type, operandValue: condition1990Data.operands[0].value, strings: strings}, opt_ijData);
        incrementalDom.elementOpenStart('b');
            incrementalDom.attr('class', 'text-lowercase');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementOpen('em');
        incrementalDom.text(' ');
        soyIdom.print(strings[condition1990Data.operator]);
        incrementalDom.text(' ');
        incrementalDom.elementClose('em');
        incrementalDom.elementClose('b');
        if (condition1990Data.operands[1]) {
          $condition({operandType: condition1990Data.operands[1].type, operandValue: condition1990Data.operands[1].value, strings: strings}, opt_ijData);
        }
        if (!(condition1990Index == condition1990ListLen - 1)) {
          incrementalDom.elementOpen('br');
          incrementalDom.elementClose('br');
          incrementalDom.elementOpen('b');
          incrementalDom.text(' ');
          soyIdom.print(strings[rule1985Data.logicalOperator]);
          incrementalDom.text(' ');
          incrementalDom.elementClose('b');
        }
      }
      incrementalDom.elementOpen('br');
      incrementalDom.elementClose('br');
      var action2016List = rule1985Data.actions;
      var action2016ListLen = action2016List.length;
      for (var action2016Index = 0; action2016Index < action2016ListLen; action2016Index++) {
        var action2016Data = action2016List[action2016Index];
        $action({action: action2016Data.action, expression: action2016Data.expression, label: action2016Data.label, logicalOperator: rule1985Data.logicalOperator, strings: strings}, opt_ijData);
        if (!(action2016Index == action2016ListLen - 1)) {
          incrementalDom.text(', ');
          incrementalDom.elementOpen('br');
          incrementalDom.elementClose('br');
          incrementalDom.elementOpen('b');
          incrementalDom.text(' ');
          soyIdom.print(strings['and']);
          incrementalDom.text(' ');
          incrementalDom.elementClose('b');
        }
      }
      incrementalDom.elementClose('p');
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'list-group-item-field');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'card-col-field');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'dropdown dropdown-action');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'dropdown-menu dropdown-menu-right');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'rule-card-edit');
          incrementalDom.attr('data-card-id', rule1985Index);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('a');
          incrementalDom.attr('href', 'javascript:;');
      incrementalDom.elementOpenEnd();
      soyIdom.print(strings.edit);
      incrementalDom.elementClose('a');
      incrementalDom.elementClose('li');
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'rule-card-delete');
          incrementalDom.attr('data-card-id', rule1985Index);
      incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('a');
          incrementalDom.attr('href', 'javascript:;');
      incrementalDom.elementOpenEnd();
      soyIdom.print(strings.delete);
      incrementalDom.elementClose('a');
      incrementalDom.elementClose('li');
      incrementalDom.elementClose('ul');
      incrementalDom.elementOpenStart('a');
          incrementalDom.attr('class', 'component-action dropdown-toggle');
          incrementalDom.attr('data-toggle', 'dropdown');
          incrementalDom.attr('href', '#1');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('a');
      incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
      incrementalDom.elementClose('li');
    }
    incrementalDom.elementClose('ul');
  } else {
    $empty_list({message: strings.emptyListText}, opt_ijData);
  }
  incrementalDom.elementClose('div');
};
exports.rule_list = $rule_list;
/**
 * @typedef {{
 *  rules: !Array<{actions: !Array<{action: (!goog.soy.data.SanitizedContent|string), expression: ?, label: (!goog.soy.data.SanitizedContent|string), target: (!goog.soy.data.SanitizedContent|string),}>, conditions: !Array<{operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string),}>, operator: (!goog.soy.data.SanitizedContent|string),}>, logicalOperator: (!goog.soy.data.SanitizedContent|string),}>,
 *  strings: ?,
 * }}
 */
$rule_list.Params;
if (goog.DEBUG) {
  $rule_list.soyTemplateName = 'LayoutRule.rule_list';
}


/**
 * @param {$empty_list.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $empty_list = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var message = soy.asserts.assertType(opt_data.message == null || (goog.isString(opt_data.message) || opt_data.message instanceof goog.soy.data.SanitizedContent), 'message', opt_data.message, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'main-content-body');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'sheet taglib-empty-result-message');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'taglib-empty-result-message-header');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementClose('div');
  if (message) {
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'sheet-text text-center text-muted');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('p');
        incrementalDom.attr('class', 'text-default');
    incrementalDom.elementOpenEnd();
    soyIdom.print(message);
    incrementalDom.elementClose('p');
    incrementalDom.elementClose('div');
  }
  incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
};
exports.empty_list = $empty_list;
/**
 * @typedef {{
 *  message: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$empty_list.Params;
if (goog.DEBUG) {
  $empty_list.soyTemplateName = 'LayoutRule.empty_list';
}


/**
 * @param {$label.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $label = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var content = soy.asserts.assertType(opt_data.content == null || (goog.isString(opt_data.content) || opt_data.content instanceof goog.soy.data.SanitizedContent), 'content', opt_data.content, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'label label-lg label-secondary');
      incrementalDom.attr('data-original-title', content);
      incrementalDom.attr('title', content);
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'text-truncate-inline');
  incrementalDom.elementOpenEnd();
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'text-truncate');
  incrementalDom.elementOpenEnd();
  soyIdom.print(content);
  incrementalDom.elementClose('span');
  incrementalDom.elementClose('span');
  incrementalDom.elementClose('span');
};
exports.label = $label;
/**
 * @typedef {{
 *  content: (!goog.soy.data.SanitizedContent|null|string|undefined),
 * }}
 */
$label.Params;
if (goog.DEBUG) {
  $label.soyTemplateName = 'LayoutRule.label';
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
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var operandType = soy.asserts.assertType(goog.isString(opt_data.operandType) || opt_data.operandType instanceof goog.soy.data.SanitizedContent, 'operandType', opt_data.operandType, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var operandValue = soy.asserts.assertType(goog.isString(opt_data.operandValue) || opt_data.operandValue instanceof goog.soy.data.SanitizedContent, 'operandValue', opt_data.operandValue, '!goog.soy.data.SanitizedContent|string');
  /** @type {?} */
  var strings = opt_data.strings;
  if (operandType == 'double' || operandType == 'integer' || operandType == 'string') {
    incrementalDom.elementOpen('span');
    soyIdom.print(strings.value);
    incrementalDom.text(' ');
    incrementalDom.elementClose('span');
  } else {
    if (operandType != 'user' && operandType != 'list') {
      incrementalDom.elementOpen('span');
      soyIdom.print(strings[operandType]);
      incrementalDom.text(' ');
      incrementalDom.elementClose('span');
    }
  }
  $label({content: operandValue}, opt_ijData);
};
exports.condition = $condition;
/**
 * @typedef {{
 *  operandType: (!goog.soy.data.SanitizedContent|string),
 *  operandValue: (!goog.soy.data.SanitizedContent|string),
 *  strings: ?,
 * }}
 */
$condition.Params;
if (goog.DEBUG) {
  $condition.soyTemplateName = 'LayoutRule.condition';
}


/**
 * @param {$action.Params} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes|uselessCode}
 */
var $action = function(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var action = soy.asserts.assertType(goog.isString(opt_data.action) || opt_data.action instanceof goog.soy.data.SanitizedContent, 'action', opt_data.action, '!goog.soy.data.SanitizedContent|string');
  /** @type {?} */
  var expression = opt_data.expression;
  /** @type {?} */
  var label = opt_data.label;
  /** @type {?} */
  var strings = opt_data.strings;
  incrementalDom.elementOpen('span');
  incrementalDom.elementOpen('b');
  soyIdom.print(action);
  incrementalDom.text(' ');
  incrementalDom.elementClose('b');
  incrementalDom.elementClose('span');
  incrementalDom.elementOpen('span');
  soyIdom.print(strings['field']);
  incrementalDom.text(' ');
  incrementalDom.elementClose('span');
  if (action == 'auto-fill') {
    $label({content: label}, opt_ijData);
  } else if (action == 'calculate') {
    $label({content: expression}, opt_ijData);
    incrementalDom.elementOpen('b');
    incrementalDom.text(' into ');
    incrementalDom.elementClose('b');
    incrementalDom.elementOpen('span');
    soyIdom.print(strings['field']);
    incrementalDom.text(' ');
    incrementalDom.elementClose('span');
    $label({content: label}, opt_ijData);
  } else if (action == 'enable') {
    $label({content: label}, opt_ijData);
  } else if (action == 'jump-to-page') {
    $label({content: label}, opt_ijData);
  } else if (action == 'require') {
    $label({content: label}, opt_ijData);
  } else if (action == 'show') {
    $label({content: label}, opt_ijData);
  }
};
exports.action = $action;
/**
 * @typedef {{
 *  action: (!goog.soy.data.SanitizedContent|string),
 *  expression: ?,
 *  label: ?,
 *  strings: ?,
 * }}
 */
$action.Params;
if (goog.DEBUG) {
  $action.soyTemplateName = 'LayoutRule.action';
}

exports.render.params = ["rules","strings"];
exports.render.types = {"rules":"list<[\n\t\t\tconditions: list<[\n\t\t\t\toperator: string,\n\t\t\t\toperands: list<[\n\t\t\t\t\ttype: string,\n\t\t\t\t\tlabel: string,\n\t\t\t\t\tvalue: string\n\t\t\t\t]>\n\t\t\t]>,\n\t\t\tactions: list<[\n\t\t\t\taction: string,\n\t\t\t\texpression: ?,\n\t\t\t\ttarget: string,\n\t\t\t\tlabel: string\n\t\t\t]>,\n\t\t\tlogicalOperator: string\n\t\t]>\n\t","strings":"?"};
exports.rule_list.params = ["rules","strings"];
exports.rule_list.types = {"rules":"list<[\n\t\t\tconditions: list<[\n\t\t\t\toperator: string,\n\t\t\t\toperands: list<[\n\t\t\t\t\ttype: string,\n\t\t\t\t\tlabel: string,\n\t\t\t\t\tvalue: string\n\t\t\t\t]>\n\t\t\t]>,\n\t\t\tactions: list<[\n\t\t\t\taction: string,\n\t\t\t\texpression: ?,\n\t\t\t\ttarget: string,\n\t\t\t\tlabel: string\n\t\t\t]>,\n\t\t\tlogicalOperator: string\n\t\t]>\n\t","strings":"?"};
exports.empty_list.params = ["message"];
exports.empty_list.types = {"message":"string"};
exports.label.params = ["content"];
exports.label.types = {"content":"string"};
exports.condition.params = ["operandType","operandValue","strings"];
exports.condition.types = {"operandType":"string","operandValue":"string","strings":"?"};
exports.action.params = ["action","expression","label","strings"];
exports.action.types = {"action":"string","expression":"?","label":"?","strings":"?"};
templates = exports;
return exports;

});

class LayoutRule extends Component {}
Soy.register(LayoutRule, templates);
export { LayoutRule, templates };
export default templates;
/* jshint ignore:end */
