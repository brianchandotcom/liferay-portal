/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from rule-builder.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMRuleBuilder.
 * @public
 */

goog.module('DDMRuleBuilder.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {{
 *  strings: {ruleBuilder: (!goog.soy.data.SanitizedContent|string)}
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{ruleBuilder: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{ruleBuilder: (!goog.soy.data.SanitizedContent|string)}');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-builder-rule-builder-container');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('h1');
        incrementalDom.attr('class', 'form-builder-section-title text-default');
    incrementalDom.elementOpenEnd();
      soyIdom.print(strings.ruleBuilder);
    incrementalDom.elementClose('h1');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'liferay-ddm-form-rule-rules-list-container');
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  strings: {ruleBuilder: (!goog.soy.data.SanitizedContent|string)}
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMRuleBuilder.render';
}


/**
 * @param {{
 *  kebab: function(),
 *  rules: !Array<{actions: !Array<function()>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>,
 *  strings: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $rule_list(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var kebab = soy.asserts.assertType(goog.isFunction(opt_data.kebab), 'kebab', opt_data.kebab, 'function()');
  /** @type {!Array<{actions: !Array<function()>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>} */
  var rules = soy.asserts.assertType(goog.isArray(opt_data.rules), 'rules', opt_data.rules, '!Array<{actions: !Array<function()>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {?} */
  var strings = opt_data.strings;
  incrementalDom.elementOpen('div');
    if ((rules.length) > 0) {
      incrementalDom.elementOpenStart('ul');
          incrementalDom.attr('class', 'ddm-form-body-content form-builder-rule-builder-rules-list tabular-list-group');
      incrementalDom.elementOpenEnd();
        var rule1187List = rules;
        var rule1187ListLen = rule1187List.length;
        for (var rule1187Index = 0; rule1187Index < rule1187ListLen; rule1187Index++) {
            var rule1187Data = rule1187List[rule1187Index];
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
                  var condition1161List = rule1187Data.conditions;
                  var condition1161ListLen = condition1161List.length;
                  for (var condition1161Index = 0; condition1161Index < condition1161ListLen; condition1161Index++) {
                      var condition1161Data = condition1161List[condition1161Index];
                      $condition({operandType: condition1161Data.operands[0].type, operandValue: condition1161Data.operands[0].label, strings: strings}, null, opt_ijData);
                      incrementalDom.elementOpenStart('b');
                          incrementalDom.attr('class', 'text-lowercase');
                      incrementalDom.elementOpenEnd();
                        incrementalDom.elementOpen('em');
                          incrementalDom.text(' ');
                          soyIdom.print(strings[condition1161Data.operator]);
                          incrementalDom.text(' ');
                        incrementalDom.elementClose('em');
                      incrementalDom.elementClose('b');
                      if (condition1161Data.operands[1]) {
                        $condition({operandType: condition1161Data.operands[1].type, operandValue: (condition1161Data.operands[1].label != null) ? condition1161Data.operands[1].label : condition1161Data.operands[1].value, strings: strings}, null, opt_ijData);
                      }
                      if (!(condition1161Index == condition1161ListLen - 1)) {
                        incrementalDom.elementOpen('br');
                        incrementalDom.elementClose('br');
                        incrementalDom.elementOpen('b');
                          incrementalDom.text(' ');
                          soyIdom.print(strings[rule1187Data.logicOperator]);
                          incrementalDom.text(' ');
                        incrementalDom.elementClose('b');
                      }
                    }
                  incrementalDom.elementOpen('br');
                  incrementalDom.elementClose('br');
                  var action1174List = rule1187Data.actions;
                  var action1174ListLen = action1174List.length;
                  for (var action1174Index = 0; action1174Index < action1174ListLen; action1174Index++) {
                      var action1174Data = action1174List[action1174Index];
                      $action({action: action1174Data}, null, opt_ijData);
                      if (!(action1174Index == action1174ListLen - 1)) {
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
                          incrementalDom.attr('data-card-id', rule1187Index);
                      incrementalDom.elementOpenEnd();
                        incrementalDom.elementOpenStart('a');
                            incrementalDom.attr('href', 'javascript:;');
                        incrementalDom.elementOpenEnd();
                          soyIdom.print(strings.edit);
                        incrementalDom.elementClose('a');
                      incrementalDom.elementClose('li');
                      incrementalDom.elementOpenStart('li');
                          incrementalDom.attr('class', 'rule-card-delete');
                          incrementalDom.attr('data-card-id', rule1187Index);
                      incrementalDom.elementOpenEnd();
                        incrementalDom.elementOpenStart('a');
                            incrementalDom.attr('href', 'javascript:;');
                        incrementalDom.elementOpenEnd();
                          soyIdom.print(strings.delete);
                        incrementalDom.elementClose('a');
                      incrementalDom.elementClose('li');
                    incrementalDom.elementClose('ul');
                    incrementalDom.elementOpenStart('a');
                        incrementalDom.attr('class', 'dropdown-toggle icon-monospaced');
                        incrementalDom.attr('data-toggle', 'dropdown');
                        incrementalDom.attr('href', '#1');
                    incrementalDom.elementOpenEnd();
                      kebab();
                    incrementalDom.elementClose('a');
                  incrementalDom.elementClose('div');
                incrementalDom.elementClose('div');
              incrementalDom.elementClose('div');
            incrementalDom.elementClose('li');
          }
      incrementalDom.elementClose('ul');
    } else {
      $empty_list({message: strings.emptyListText}, null, opt_ijData);
    }
  incrementalDom.elementClose('div');
}
exports.rule_list = $rule_list;
/**
 * @typedef {{
 *  kebab: function(),
 *  rules: !Array<{actions: !Array<function()>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>,
 *  strings: (?)
 * }}
 */
$rule_list.Params;
if (goog.DEBUG) {
  $rule_list.soyTemplateName = 'DDMRuleBuilder.rule_list';
}


/**
 * @param {{
 *  message: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $empty_list(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var message = soy.asserts.assertType(opt_data.message == null || (goog.isString(opt_data.message) || opt_data.message instanceof goog.soy.data.SanitizedContent), 'message', opt_data.message, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'main-content-body');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'card main-content-card taglib-empty-result-message');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'card-row card-row-padded');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'taglib-empty-result-message-header-has-plus-btn');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
        if (message) {
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'text-center text-muted');
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
  incrementalDom.elementClose('div');
}
exports.empty_list = $empty_list;
/**
 * @typedef {{
 *  message: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$empty_list.Params;
if (goog.DEBUG) {
  $empty_list.soyTemplateName = 'DDMRuleBuilder.empty_list';
}


/**
 * @param {{
 *  strings: {enableDisable: (!goog.soy.data.SanitizedContent|string), require: (!goog.soy.data.SanitizedContent|string), showHide: (!goog.soy.data.SanitizedContent|string)}
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $rule_types(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{enableDisable: (!goog.soy.data.SanitizedContent|string), require: (!goog.soy.data.SanitizedContent|string), showHide: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{enableDisable: (!goog.soy.data.SanitizedContent|string), require: (!goog.soy.data.SanitizedContent|string), showHide: (!goog.soy.data.SanitizedContent|string)}');
  incrementalDom.elementOpenStart('ul');
      incrementalDom.attr('class', 'dropdown-menu');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpen('li');
      incrementalDom.elementOpenStart('a');
          incrementalDom.attr('data-rule-type', 'visibility');
          incrementalDom.attr('href', 'javascript:;');
      incrementalDom.elementOpenEnd();
        soyIdom.print(strings.showHide);
      incrementalDom.elementClose('a');
      incrementalDom.elementOpenStart('a');
          incrementalDom.attr('data-rule-type', 'readonly');
          incrementalDom.attr('href', 'javascript:;');
      incrementalDom.elementOpenEnd();
        soyIdom.print(strings.enableDisable);
      incrementalDom.elementClose('a');
      incrementalDom.elementOpenStart('a');
          incrementalDom.attr('data-rule-type', 'require');
          incrementalDom.attr('href', 'javascript:;');
      incrementalDom.elementOpenEnd();
        soyIdom.print(strings.require);
      incrementalDom.elementClose('a');
    incrementalDom.elementClose('li');
  incrementalDom.elementClose('ul');
}
exports.rule_types = $rule_types;
/**
 * @typedef {{
 *  strings: {enableDisable: (!goog.soy.data.SanitizedContent|string), require: (!goog.soy.data.SanitizedContent|string), showHide: (!goog.soy.data.SanitizedContent|string)}
 * }}
 */
$rule_types.Params;
if (goog.DEBUG) {
  $rule_types.soyTemplateName = 'DDMRuleBuilder.rule_types';
}


/**
 * @param {{
 *  content: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $label(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var content = soy.asserts.assertType(opt_data.content == null || (goog.isString(opt_data.content) || opt_data.content instanceof goog.soy.data.SanitizedContent), 'content', opt_data.content, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'label label-lg label-secondary');
  incrementalDom.elementOpenEnd();
    soyIdom.print(content);
  incrementalDom.elementClose('span');
}
exports.label = $label;
/**
 * @typedef {{
 *  content: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$label.Params;
if (goog.DEBUG) {
  $label.soyTemplateName = 'DDMRuleBuilder.label';
}


/**
 * @param {{
 *  operandType: (!goog.soy.data.SanitizedContent|string),
 *  operandValue: (!goog.soy.data.SanitizedContent|string),
 *  strings: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $condition(opt_data, opt_ijData, opt_ijData_deprecated) {
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
  $label({content: operandValue}, null, opt_ijData);
}
exports.condition = $condition;
/**
 * @typedef {{
 *  operandType: (!goog.soy.data.SanitizedContent|string),
 *  operandValue: (!goog.soy.data.SanitizedContent|string),
 *  strings: (?)
 * }}
 */
$condition.Params;
if (goog.DEBUG) {
  $condition.soyTemplateName = 'DDMRuleBuilder.condition';
}


/**
 * @param {{
 *  action: function()
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $action(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {function()} */
  var action = soy.asserts.assertType(goog.isFunction(opt_data.action), 'action', opt_data.action, 'function()');
  incrementalDom.elementOpen('b');
    action();
  incrementalDom.elementClose('b');
}
exports.action = $action;
/**
 * @typedef {{
 *  action: function()
 * }}
 */
$action.Params;
if (goog.DEBUG) {
  $action.soyTemplateName = 'DDMRuleBuilder.action';
}

exports.render.params = ["strings"];
exports.render.types = {"strings":"[ruleBuilder: string]"};
exports.rule_list.params = ["kebab","rules","strings"];
exports.rule_list.types = {"kebab":"html","rules":"list<[conditions: list<[operandType: string, operandValue: string, strings: any, operator: string, operands: list<[type: string, label: string, value: string]>]>, actions: list<html>, logicOperator: string]>","strings":"?"};
exports.empty_list.params = ["message"];
exports.empty_list.types = {"message":"string"};
exports.rule_types.params = ["strings"];
exports.rule_types.types = {"strings":"[showHide: string, enableDisable: string, require: string]"};
exports.label.params = ["content"];
exports.label.types = {"content":"string"};
exports.condition.params = ["operandType","operandValue","strings"];
exports.condition.types = {"operandType":"string","operandValue":"string","strings":"?"};
exports.action.params = ["action"];
exports.action.types = {"action":"html"};
templates = exports;
return exports;

});

class DDMRuleBuilder extends Component {}
Soy.register(DDMRuleBuilder, templates);
export { DDMRuleBuilder, templates };
export default templates;
/* jshint ignore:end */
