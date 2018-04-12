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
 * @hassoydeltemplate {DDMRuleBuilder.action.idom}
 * @hassoydelcall {DDMRuleBuilder.action.idom}
 * @public
 */

goog.module('DDMRuleBuilder.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
goog.require('goog.string');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
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
 *  rules: !Array<{actions: !Array<?>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>,
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
  /** @type {!Array<{actions: !Array<?>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>} */
  var rules = soy.asserts.assertType(goog.isArray(opt_data.rules), 'rules', opt_data.rules, '!Array<{actions: !Array<?>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>');
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
                      soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), action1174Data.type, false)({action: action1174Data}, null, opt_ijData);
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
 *  rules: !Array<{actions: !Array<?>, conditions: !Array<{operandType: (!goog.soy.data.SanitizedContent|string), operandValue: (!goog.soy.data.SanitizedContent|string), operands: !Array<{label: (!goog.soy.data.SanitizedContent|string), type: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>, operator: (!goog.soy.data.SanitizedContent|string), strings: *}>, logicOperator: (!goog.soy.data.SanitizedContent|string)}>,
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
function $badge(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var content = soy.asserts.assertType(opt_data.content == null || (goog.isString(opt_data.content) || opt_data.content instanceof goog.soy.data.SanitizedContent), 'content', opt_data.content, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'badge badge-default badge-sm');
  incrementalDom.elementOpenEnd();
    soyIdom.print(content);
  incrementalDom.elementClose('span');
}
exports.badge = $badge;
/**
 * @typedef {{
 *  content: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$badge.Params;
if (goog.DEBUG) {
  $badge.soyTemplateName = 'DDMRuleBuilder.badge';
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
  $badge({content: operandValue}, null, opt_ijData);
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
 *  outputs: !Array<?>
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $autofill_outputs(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<?>} */
  var outputs = soy.asserts.assertType(goog.isArray(opt_data.outputs), 'outputs', opt_data.outputs, '!Array<?>');
  var output1255List = outputs;
  var output1255ListLen = output1255List.length;
  for (var output1255Index = 0; output1255Index < output1255ListLen; output1255Index++) {
      var output1255Data = output1255List[output1255Index];
      $badge({content: output1255Data}, null, opt_ijData);
      if (!(output1255Index == output1255ListLen - 1)) {
        incrementalDom.text(',');
      }
    }
}
exports.autofill_outputs = $autofill_outputs;
/**
 * @typedef {{
 *  outputs: !Array<?>
 * }}
 */
$autofill_outputs.Params;
if (goog.DEBUG) {
  $autofill_outputs.soyTemplateName = 'DDMRuleBuilder.autofill_outputs';
}


/**
 * @param {{
 *  action: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s1258_e38e35bf(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var action = opt_data.action;
  incrementalDom.elementOpen('b');
    /** @desc show-x */
    var MSG_EXTERNAL_6763821615557154 = goog.getMsg('show-{$xxx}', {'xxx': '\u00010\u0001'});
    var lastIndex_1262 = 0, partRe_1262 = /\x01\d+\x01/g, match_1262;
    do {
      match_1262 = partRe_1262.exec(MSG_EXTERNAL_6763821615557154) || undefined;
      incrementalDom.text(goog.string.unescapeEntities(MSG_EXTERNAL_6763821615557154.substring(lastIndex_1262, match_1262 && match_1262.index)));
      lastIndex_1262 = partRe_1262.lastIndex;
      switch (match_1262 && match_1262[0]) {
        case '\u00010\u0001':
          $badge({content: action.param0}, null, opt_ijData);
          break;
      }
    } while (match_1262);
  incrementalDom.elementClose('b');
}
exports.__deltemplate_s1258_e38e35bf = __deltemplate_s1258_e38e35bf;
/**
 * @typedef {{
 *  action: (?)
 * }}
 */
__deltemplate_s1258_e38e35bf.Params;
if (goog.DEBUG) {
  __deltemplate_s1258_e38e35bf.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s1258_e38e35bf';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'show', 0, __deltemplate_s1258_e38e35bf);


/**
 * @param {{
 *  action: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s1271_b56ff7a3(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var action = opt_data.action;
  incrementalDom.elementOpen('b');
    /** @desc enable-x */
    var MSG_EXTERNAL_1991085455776803984 = goog.getMsg('enable-{$xxx}', {'xxx': '\u00010\u0001'});
    var lastIndex_1275 = 0, partRe_1275 = /\x01\d+\x01/g, match_1275;
    do {
      match_1275 = partRe_1275.exec(MSG_EXTERNAL_1991085455776803984) || undefined;
      incrementalDom.text(goog.string.unescapeEntities(MSG_EXTERNAL_1991085455776803984.substring(lastIndex_1275, match_1275 && match_1275.index)));
      lastIndex_1275 = partRe_1275.lastIndex;
      switch (match_1275 && match_1275[0]) {
        case '\u00010\u0001':
          $badge({content: action.param0}, null, opt_ijData);
          break;
      }
    } while (match_1275);
  incrementalDom.elementClose('b');
}
exports.__deltemplate_s1271_b56ff7a3 = __deltemplate_s1271_b56ff7a3;
/**
 * @typedef {{
 *  action: (?)
 * }}
 */
__deltemplate_s1271_b56ff7a3.Params;
if (goog.DEBUG) {
  __deltemplate_s1271_b56ff7a3.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s1271_b56ff7a3';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'enable', 0, __deltemplate_s1271_b56ff7a3);


/**
 * @param {{
 *  action: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s1284_a5108aa8(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var action = opt_data.action;
  incrementalDom.elementOpen('b');
    /** @desc calculate-field-x-as-x */
    var MSG_EXTERNAL_5511274467569914026 = goog.getMsg('calculate-field-{$xxx_1}-as-{$xxx_2}', {'xxx_1': '\u00010\u0001', 'xxx_2': '\u00011\u0001'});
    var lastIndex_1288 = 0, partRe_1288 = /\x01\d+\x01/g, match_1288;
    do {
      match_1288 = partRe_1288.exec(MSG_EXTERNAL_5511274467569914026) || undefined;
      incrementalDom.text(goog.string.unescapeEntities(MSG_EXTERNAL_5511274467569914026.substring(lastIndex_1288, match_1288 && match_1288.index)));
      lastIndex_1288 = partRe_1288.lastIndex;
      switch (match_1288 && match_1288[0]) {
        case '\u00011\u0001':
          $badge({content: action.param1}, null, opt_ijData);
          break;
        case '\u00010\u0001':
          $badge({content: action.param0}, null, opt_ijData);
          break;
      }
    } while (match_1288);
  incrementalDom.elementClose('b');
}
exports.__deltemplate_s1284_a5108aa8 = __deltemplate_s1284_a5108aa8;
/**
 * @typedef {{
 *  action: (?)
 * }}
 */
__deltemplate_s1284_a5108aa8.Params;
if (goog.DEBUG) {
  __deltemplate_s1284_a5108aa8.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s1284_a5108aa8';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'calculate', 0, __deltemplate_s1284_a5108aa8);


/**
 * @param {{
 *  action: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s1301_f2670823(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var action = opt_data.action;
  incrementalDom.elementOpen('b');
    /** @desc autofill-x-from-data-provider-x */
    var MSG_EXTERNAL_7333391118384881260 = goog.getMsg('autofill-{$xxx_1}-from-data-provider-{$xxx_2}', {'xxx_1': '\u00010\u0001', 'xxx_2': '\u00011\u0001'});
    var lastIndex_1305 = 0, partRe_1305 = /\x01\d+\x01/g, match_1305;
    do {
      match_1305 = partRe_1305.exec(MSG_EXTERNAL_7333391118384881260) || undefined;
      incrementalDom.text(goog.string.unescapeEntities(MSG_EXTERNAL_7333391118384881260.substring(lastIndex_1305, match_1305 && match_1305.index)));
      lastIndex_1305 = partRe_1305.lastIndex;
      switch (match_1305 && match_1305[0]) {
        case '\u00011\u0001':
          $badge({content: action.param1}, null, opt_ijData);
          break;
        case '\u00010\u0001':
          $autofill_outputs({outputs: action.param0}, null, opt_ijData);
          break;
      }
    } while (match_1305);
  incrementalDom.elementClose('b');
}
exports.__deltemplate_s1301_f2670823 = __deltemplate_s1301_f2670823;
/**
 * @typedef {{
 *  action: (?)
 * }}
 */
__deltemplate_s1301_f2670823.Params;
if (goog.DEBUG) {
  __deltemplate_s1301_f2670823.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s1301_f2670823';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'autofill', 0, __deltemplate_s1301_f2670823);


/**
 * @param {{
 *  action: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s1318_9bf2c8a4(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var action = opt_data.action;
  incrementalDom.elementOpen('b');
    /** @desc jump-to-page-x */
    var MSG_EXTERNAL_7070892870969211427 = goog.getMsg('jump-to-page-{$xxx}', {'xxx': '\u00010\u0001'});
    var lastIndex_1322 = 0, partRe_1322 = /\x01\d+\x01/g, match_1322;
    do {
      match_1322 = partRe_1322.exec(MSG_EXTERNAL_7070892870969211427) || undefined;
      incrementalDom.text(goog.string.unescapeEntities(MSG_EXTERNAL_7070892870969211427.substring(lastIndex_1322, match_1322 && match_1322.index)));
      lastIndex_1322 = partRe_1322.lastIndex;
      switch (match_1322 && match_1322[0]) {
        case '\u00010\u0001':
          $badge({content: action.param0}, null, opt_ijData);
          break;
      }
    } while (match_1322);
  incrementalDom.elementClose('b');
}
exports.__deltemplate_s1318_9bf2c8a4 = __deltemplate_s1318_9bf2c8a4;
/**
 * @typedef {{
 *  action: (?)
 * }}
 */
__deltemplate_s1318_9bf2c8a4.Params;
if (goog.DEBUG) {
  __deltemplate_s1318_9bf2c8a4.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s1318_9bf2c8a4';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'jumptopage', 0, __deltemplate_s1318_9bf2c8a4);


/**
 * @param {{
 *  action: (?)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s1331_831406f5(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {?} */
  var action = opt_data.action;
  incrementalDom.elementOpen('b');
    /** @desc require-x */
    var MSG_EXTERNAL_3017347163412966732 = goog.getMsg('require-{$xxx}', {'xxx': '\u00010\u0001'});
    var lastIndex_1335 = 0, partRe_1335 = /\x01\d+\x01/g, match_1335;
    do {
      match_1335 = partRe_1335.exec(MSG_EXTERNAL_3017347163412966732) || undefined;
      incrementalDom.text(goog.string.unescapeEntities(MSG_EXTERNAL_3017347163412966732.substring(lastIndex_1335, match_1335 && match_1335.index)));
      lastIndex_1335 = partRe_1335.lastIndex;
      switch (match_1335 && match_1335[0]) {
        case '\u00010\u0001':
          $badge({content: action.param0}, null, opt_ijData);
          break;
      }
    } while (match_1335);
  incrementalDom.elementClose('b');
}
exports.__deltemplate_s1331_831406f5 = __deltemplate_s1331_831406f5;
/**
 * @typedef {{
 *  action: (?)
 * }}
 */
__deltemplate_s1331_831406f5.Params;
if (goog.DEBUG) {
  __deltemplate_s1331_831406f5.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s1331_831406f5';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'require', 0, __deltemplate_s1331_831406f5);

exports.render.params = ["strings"];
exports.render.types = {"strings":"[ruleBuilder: string]"};
exports.rule_list.params = ["kebab","rules","strings"];
exports.rule_list.types = {"kebab":"html","rules":"list<[conditions: list<[operandType: string, operandValue: string, strings: any, operator: string, operands: list<[type: string, label: string, value: string]>]>, actions: list<?>, logicOperator: string]>","strings":"?"};
exports.empty_list.params = ["message"];
exports.empty_list.types = {"message":"string"};
exports.rule_types.params = ["strings"];
exports.rule_types.types = {"strings":"[showHide: string, enableDisable: string, require: string]"};
exports.badge.params = ["content"];
exports.badge.types = {"content":"string"};
exports.condition.params = ["operandType","operandValue","strings"];
exports.condition.types = {"operandType":"string","operandValue":"string","strings":"?"};
exports.autofill_outputs.params = ["outputs"];
exports.autofill_outputs.types = {"outputs":"list<?>"};
templates = exports;
return exports;

});

class DDMRuleBuilder extends Component {}
Soy.register(DDMRuleBuilder, templates);
export { DDMRuleBuilder, templates };
export default templates;
/* jshint ignore:end */
