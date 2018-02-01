/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from rule-builder.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMRuleBuilder.
 * @hassoydeltemplate {DDMRuleBuilder.action.idom}
 * @hassoydelcall {DDMRuleBuilder.action.idom}
 * @public
 */

goog.module('DDMRuleBuilder.incrementaldom');

/** @suppress {extraRequire} */
var soy = goog.require('soy');
/** @suppress {extraRequire} */
var soydata = goog.require('soydata');
/** @suppress {extraRequire} */
goog.require('goog.asserts');
/** @suppress {extraRequire} */
goog.require('soy.asserts');
/** @suppress {extraRequire} */
goog.require('goog.i18n.bidi');
/** @suppress {extraRequire} */
goog.require('goog.string');
var IncrementalDom = goog.require('incrementaldom');
var ie_open = IncrementalDom.elementOpen;
var ie_close = IncrementalDom.elementClose;
var ie_void = IncrementalDom.elementVoid;
var ie_open_start = IncrementalDom.elementOpenStart;
var ie_open_end = IncrementalDom.elementOpenEnd;
var itext = IncrementalDom.text;
var iattr = IncrementalDom.attr;


/**
 * @param {{
 *    plusIcon: (!soydata.SanitizedHtml|string),
 *    strings: {ruleBuilder: string}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.plusIcon instanceof Function) || (opt_data.plusIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.plusIcon), 'plusIcon', opt_data.plusIcon, 'Function');
  var plusIcon = /** @type {Function} */ (opt_data.plusIcon);
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [ruleBuilder: string].");
  ie_open('div', null, null,
      'class', 'form-builder-rule-builder-container');
    ie_open('h1', null, null,
        'class', 'form-builder-section-title text-default');
      var dyn16 = strings.ruleBuilder;
      if (typeof dyn16 == 'function') dyn16(); else if (dyn16 != null) itext(dyn16);
    ie_close('h1');
    ie_void('div', null, null,
        'class', 'liferay-ddm-form-rule-rules-list-container');
    ie_open('div', null, null,
        'class', 'form-builder-rule-builder-add-rule-container');
      ie_open('div', null, null,
          'class', 'btn-action-secondary btn-bottom-right dropdown form-builder-rule-builder-add-rule-button');
        ie_open('button', null, null,
            'class', 'btn btn-primary form-builder-rule-builder-add-rule-button-icon',
            'type', 'button');
          plusIcon();
        ie_close('button');
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMRuleBuilder.render';
}


/**
 * @param {{
 *    kebab: (!soydata.SanitizedHtml|string),
 *    rules: !Array<{actions: !Array<(?)>, conditions: !Array<{operandType: string, operandValue: string, operands: !Array<{label: string, type: string, value: string}>, operator: string, strings: *}>, logicOperator: string}>,
 *    strings: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $rule_list(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType((opt_data.kebab instanceof Function) || (opt_data.kebab instanceof soydata.UnsanitizedText) || goog.isString(opt_data.kebab), 'kebab', opt_data.kebab, 'Function');
  var kebab = /** @type {Function} */ (opt_data.kebab);
  var rules = goog.asserts.assertArray(opt_data.rules, "expected parameter 'rules' of type list<[actions: list<?>, conditions: list<[operandType: string, operandValue: string, operands: list<[label: string, type: string, value: string]>, operator: string, strings: any]>, logicOperator: string]>.");
  ie_open('div');
    if (rules.length > 0) {
      ie_open('ul', null, null,
          'class', 'ddm-form-body-content form-builder-rule-builder-rules-list tabular-list-group');
        var ruleList216 = rules;
        var ruleListLen216 = ruleList216.length;
        for (var ruleIndex216 = 0; ruleIndex216 < ruleListLen216; ruleIndex216++) {
          var ruleData216 = ruleList216[ruleIndex216];
          ie_open('li', null, null,
              'class', 'list-group-item');
            ie_open('div', null, null,
                'class', 'clamp-horizontal list-group-item-content');
              ie_open('p', null, null,
                  'class', 'form-builder-rule-builder-rule-description text-default');
                ie_open('b');
                  var dyn17 = opt_data.strings['if'];
                  if (typeof dyn17 == 'function') dyn17(); else if (dyn17 != null) itext(dyn17);
                  itext(' ');
                ie_close('b');
                var conditionList193 = ruleData216.conditions;
                var conditionListLen193 = conditionList193.length;
                for (var conditionIndex193 = 0; conditionIndex193 < conditionListLen193; conditionIndex193++) {
                  var conditionData193 = conditionList193[conditionIndex193];
                  $condition({operandType: conditionData193.operands[0].type, operandValue: conditionData193.operands[0].label, strings: opt_data.strings}, null, opt_ijData);
                  ie_open('b', null, null,
                      'class', 'text-lowercase');
                    ie_open('em');
                      itext(' ');
                      var dyn18 = opt_data.strings[conditionData193.operator];
                      if (typeof dyn18 == 'function') dyn18(); else if (dyn18 != null) itext(dyn18);
                      itext(' ');
                    ie_close('em');
                  ie_close('b');
                  if (conditionData193.operands[1]) {
                    $condition({operandType: conditionData193.operands[1].type, operandValue: conditionData193.operands[1].label != null ? conditionData193.operands[1].label : conditionData193.operands[1].value, strings: opt_data.strings}, null, opt_ijData);
                  }
                  if (! (conditionIndex193 == conditionListLen193 - 1)) {
                    ie_open('br');
                    ie_close('br');
                    ie_open('b');
                      itext(' ');
                      var dyn19 = opt_data.strings[ruleData216.logicOperator];
                      if (typeof dyn19 == 'function') dyn19(); else if (dyn19 != null) itext(dyn19);
                      itext(' ');
                    ie_close('b');
                  }
                }
                ie_open('br');
                ie_close('br');
                var actionList203 = ruleData216.actions;
                var actionListLen203 = actionList203.length;
                for (var actionIndex203 = 0; actionIndex203 < actionListLen203; actionIndex203++) {
                  var actionData203 = actionList203[actionIndex203];
                  soy.$$getDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), actionData203.type, false)({action: actionData203}, null, opt_ijData);
                  if (! (actionIndex203 == actionListLen203 - 1)) {
                    itext(', ');
                    ie_open('br');
                    ie_close('br');
                    ie_open('b');
                      itext(' ');
                      var dyn20 = opt_data.strings['and'];
                      if (typeof dyn20 == 'function') dyn20(); else if (dyn20 != null) itext(dyn20);
                      itext(' ');
                    ie_close('b');
                  }
                }
              ie_close('p');
            ie_close('div');
            ie_open('div', null, null,
                'class', 'list-group-item-field');
              ie_open('div', null, null,
                  'class', 'card-col-field');
                ie_open('div', null, null,
                    'class', 'dropdown dropdown-action');
                  ie_open('ul', null, null,
                      'class', 'dropdown-menu dropdown-menu-right');
                    ie_open('li', null, null,
                        'class', 'rule-card-edit',
                        'data-card-id', ruleIndex216);
                      ie_open('a', null, null,
                          'href', 'javascript:;');
                        var dyn21 = opt_data.strings.edit;
                        if (typeof dyn21 == 'function') dyn21(); else if (dyn21 != null) itext(dyn21);
                      ie_close('a');
                    ie_close('li');
                    ie_open('li', null, null,
                        'class', 'rule-card-delete',
                        'data-card-id', ruleIndex216);
                      ie_open('a', null, null,
                          'href', 'javascript:;');
                        var dyn22 = opt_data.strings['delete'];
                        if (typeof dyn22 == 'function') dyn22(); else if (dyn22 != null) itext(dyn22);
                      ie_close('a');
                    ie_close('li');
                  ie_close('ul');
                  ie_open('a', null, null,
                      'class', 'dropdown-toggle icon-monospaced',
                      'data-toggle', 'dropdown',
                      'href', '#1');
                    kebab();
                  ie_close('a');
                ie_close('div');
              ie_close('div');
            ie_close('div');
          ie_close('li');
        }
      ie_close('ul');
    } else {
      $empty_list({message: opt_data.strings.emptyListText}, null, opt_ijData);
    }
  ie_close('div');
}
exports.rule_list = $rule_list;
if (goog.DEBUG) {
  $rule_list.soyTemplateName = 'DDMRuleBuilder.rule_list';
}


/**
 * @param {{
 *    message: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $empty_list(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  soy.asserts.assertType(opt_data.message == null || (opt_data.message instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.message), 'message', opt_data.message, 'null|string|undefined');
  var message = /** @type {null|string|undefined} */ (opt_data.message);
  ie_open('div', null, null,
      'class', 'main-content-body');
    ie_open('div', null, null,
        'class', 'card main-content-card taglib-empty-result-message');
      ie_open('div', null, null,
          'class', 'card-row card-row-padded');
        ie_void('div', null, null,
            'class', 'taglib-empty-result-message-header-has-plus-btn');
        if (message) {
          ie_open('div', null, null,
              'class', 'text-center text-muted');
            ie_open('p', null, null,
                'class', 'text-default');
              var dyn23 = message;
              if (typeof dyn23 == 'function') dyn23(); else if (dyn23 != null) itext(dyn23);
            ie_close('p');
          ie_close('div');
        }
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.empty_list = $empty_list;
if (goog.DEBUG) {
  $empty_list.soyTemplateName = 'DDMRuleBuilder.empty_list';
}


/**
 * @param {{
 *    strings: {enableDisable: string, require: string, showHide: string}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $rule_types(opt_data, opt_ignored, opt_ijData) {
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [enableDisable: string, require: string, showHide: string].");
  ie_open('ul', null, null,
      'class', 'dropdown-menu');
    ie_open('li');
      ie_open('a', null, null,
          'data-rule-type', 'visibility',
          'href', 'javascript:;');
        var dyn24 = strings.showHide;
        if (typeof dyn24 == 'function') dyn24(); else if (dyn24 != null) itext(dyn24);
      ie_close('a');
      ie_open('a', null, null,
          'data-rule-type', 'readonly',
          'href', 'javascript:;');
        var dyn25 = strings.enableDisable;
        if (typeof dyn25 == 'function') dyn25(); else if (dyn25 != null) itext(dyn25);
      ie_close('a');
      ie_open('a', null, null,
          'data-rule-type', 'require',
          'href', 'javascript:;');
        var dyn26 = strings.require;
        if (typeof dyn26 == 'function') dyn26(); else if (dyn26 != null) itext(dyn26);
      ie_close('a');
    ie_close('li');
  ie_close('ul');
}
exports.rule_types = $rule_types;
if (goog.DEBUG) {
  $rule_types.soyTemplateName = 'DDMRuleBuilder.rule_types';
}


/**
 * @param {{
 *    content: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $badge(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  soy.asserts.assertType(opt_data.content == null || (opt_data.content instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.content), 'content', opt_data.content, 'null|string|undefined');
  var content = /** @type {null|string|undefined} */ (opt_data.content);
  ie_open('span', null, null,
      'class', 'badge badge-default badge-sm');
    var dyn27 = content;
    if (typeof dyn27 == 'function') dyn27(); else if (dyn27 != null) itext(dyn27);
  ie_close('span');
}
exports.badge = $badge;
if (goog.DEBUG) {
  $badge.soyTemplateName = 'DDMRuleBuilder.badge';
}


/**
 * @param {{
 *    operandType: string,
 *    operandValue: string,
 *    strings: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $condition(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.operandType) || (opt_data.operandType instanceof goog.soy.data.SanitizedContent), 'operandType', opt_data.operandType, 'string|goog.soy.data.SanitizedContent');
  var operandType = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.operandType);
  soy.asserts.assertType(goog.isString(opt_data.operandValue) || (opt_data.operandValue instanceof goog.soy.data.SanitizedContent), 'operandValue', opt_data.operandValue, 'string|goog.soy.data.SanitizedContent');
  var operandValue = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.operandValue);
  if (operandType == 'double' || operandType == 'integer' || operandType == 'string') {
    ie_open('span');
      var dyn28 = opt_data.strings.value;
      if (typeof dyn28 == 'function') dyn28(); else if (dyn28 != null) itext(dyn28);
      itext(' ');
    ie_close('span');
  } else {
    if (operandType != 'user' && operandType != 'list') {
      ie_open('span');
        var dyn29 = opt_data.strings[operandType];
        if (typeof dyn29 == 'function') dyn29(); else if (dyn29 != null) itext(dyn29);
        itext(' ');
      ie_close('span');
    }
  }
  $badge({content: operandValue}, null, opt_ijData);
}
exports.condition = $condition;
if (goog.DEBUG) {
  $condition.soyTemplateName = 'DDMRuleBuilder.condition';
}


/**
 * @param {{
 *    outputs: !Array<(?)>
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $autofill_outputs(opt_data, opt_ignored, opt_ijData) {
  var outputs = goog.asserts.assertArray(opt_data.outputs, "expected parameter 'outputs' of type list<?>.");
  var outputList263 = outputs;
  var outputListLen263 = outputList263.length;
  for (var outputIndex263 = 0; outputIndex263 < outputListLen263; outputIndex263++) {
    var outputData263 = outputList263[outputIndex263];
    $badge({content: outputData263}, null, opt_ijData);
    if (! (outputIndex263 == outputListLen263 - 1)) {
      itext(',');
    }
  }
}
exports.autofill_outputs = $autofill_outputs;
if (goog.DEBUG) {
  $autofill_outputs.soyTemplateName = 'DDMRuleBuilder.autofill_outputs';
}


/**
 * @param {{
 *    action: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s265_e38e35bf(opt_data, opt_ignored, opt_ijData) {
  ie_open('b');
    /** @desc show-x */
    var MSG_EXTERNAL_6763821615557154 = goog.getMsg(
        'show-{$xxx}',
        {'xxx': '\u00010\u0001'});
    var lastIndex_267 = 0, partRe_267 = /\x01\d+\x01/g, match_267;
    do {
      match_267 = partRe_267.exec(MSG_EXTERNAL_6763821615557154) || undefined;
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_6763821615557154.substring(lastIndex_267, match_267 && match_267.index)));
      lastIndex_267 = partRe_267.lastIndex;
      switch (match_267 && match_267[0]) {
        case '\u00010\u0001':
          $badge({content: opt_data.action.param0}, null, opt_ijData);
          break;
      }
    } while (match_267);
  ie_close('b');
}
exports.__deltemplate_s265_e38e35bf = __deltemplate_s265_e38e35bf;
if (goog.DEBUG) {
  __deltemplate_s265_e38e35bf.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s265_e38e35bf';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'show', 0, __deltemplate_s265_e38e35bf);


/**
 * @param {{
 *    action: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s274_b56ff7a3(opt_data, opt_ignored, opt_ijData) {
  ie_open('b');
    /** @desc enable-x */
    var MSG_EXTERNAL_1991085455776803984 = goog.getMsg(
        'enable-{$xxx}',
        {'xxx': '\u00010\u0001'});
    var lastIndex_276 = 0, partRe_276 = /\x01\d+\x01/g, match_276;
    do {
      match_276 = partRe_276.exec(MSG_EXTERNAL_1991085455776803984) || undefined;
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_1991085455776803984.substring(lastIndex_276, match_276 && match_276.index)));
      lastIndex_276 = partRe_276.lastIndex;
      switch (match_276 && match_276[0]) {
        case '\u00010\u0001':
          $badge({content: opt_data.action.param0}, null, opt_ijData);
          break;
      }
    } while (match_276);
  ie_close('b');
}
exports.__deltemplate_s274_b56ff7a3 = __deltemplate_s274_b56ff7a3;
if (goog.DEBUG) {
  __deltemplate_s274_b56ff7a3.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s274_b56ff7a3';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'enable', 0, __deltemplate_s274_b56ff7a3);


/**
 * @param {{
 *    action: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s283_a5108aa8(opt_data, opt_ignored, opt_ijData) {
  ie_open('b');
    /** @desc calculate-field-x-as-x */
    var MSG_EXTERNAL_5511274467569914026 = goog.getMsg(
        'calculate-field-{$xxx_1}-as-{$xxx_2}',
        {'xxx_1': '\u00010\u0001',
         'xxx_2': '\u00011\u0001'});
    var lastIndex_285 = 0, partRe_285 = /\x01\d+\x01/g, match_285;
    do {
      match_285 = partRe_285.exec(MSG_EXTERNAL_5511274467569914026) || undefined;
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_5511274467569914026.substring(lastIndex_285, match_285 && match_285.index)));
      lastIndex_285 = partRe_285.lastIndex;
      switch (match_285 && match_285[0]) {
        case '\u00011\u0001':
          $badge({content: opt_data.action.param1}, null, opt_ijData);
          break;
        case '\u00010\u0001':
          $badge({content: opt_data.action.param0}, null, opt_ijData);
          break;
      }
    } while (match_285);
  ie_close('b');
}
exports.__deltemplate_s283_a5108aa8 = __deltemplate_s283_a5108aa8;
if (goog.DEBUG) {
  __deltemplate_s283_a5108aa8.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s283_a5108aa8';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'calculate', 0, __deltemplate_s283_a5108aa8);


/**
 * @param {{
 *    action: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s296_f2670823(opt_data, opt_ignored, opt_ijData) {
  ie_open('b');
    /** @desc autofill-x-from-data-provider-x */
    var MSG_EXTERNAL_7333391118384881260 = goog.getMsg(
        'autofill-{$xxx_1}-from-data-provider-{$xxx_2}',
        {'xxx_1': '\u00010\u0001',
         'xxx_2': '\u00011\u0001'});
    var lastIndex_298 = 0, partRe_298 = /\x01\d+\x01/g, match_298;
    do {
      match_298 = partRe_298.exec(MSG_EXTERNAL_7333391118384881260) || undefined;
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_7333391118384881260.substring(lastIndex_298, match_298 && match_298.index)));
      lastIndex_298 = partRe_298.lastIndex;
      switch (match_298 && match_298[0]) {
        case '\u00011\u0001':
          $badge({content: opt_data.action.param1}, null, opt_ijData);
          break;
        case '\u00010\u0001':
          $autofill_outputs({outputs: opt_data.action.param0}, null, opt_ijData);
          break;
      }
    } while (match_298);
  ie_close('b');
}
exports.__deltemplate_s296_f2670823 = __deltemplate_s296_f2670823;
if (goog.DEBUG) {
  __deltemplate_s296_f2670823.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s296_f2670823';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'autofill', 0, __deltemplate_s296_f2670823);


/**
 * @param {{
 *    action: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s309_9bf2c8a4(opt_data, opt_ignored, opt_ijData) {
  ie_open('b');
    /** @desc jump-to-page-x */
    var MSG_EXTERNAL_7070892870969211427 = goog.getMsg(
        'jump-to-page-{$xxx}',
        {'xxx': '\u00010\u0001'});
    var lastIndex_311 = 0, partRe_311 = /\x01\d+\x01/g, match_311;
    do {
      match_311 = partRe_311.exec(MSG_EXTERNAL_7070892870969211427) || undefined;
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_7070892870969211427.substring(lastIndex_311, match_311 && match_311.index)));
      lastIndex_311 = partRe_311.lastIndex;
      switch (match_311 && match_311[0]) {
        case '\u00010\u0001':
          $badge({content: opt_data.action.param0}, null, opt_ijData);
          break;
      }
    } while (match_311);
  ie_close('b');
}
exports.__deltemplate_s309_9bf2c8a4 = __deltemplate_s309_9bf2c8a4;
if (goog.DEBUG) {
  __deltemplate_s309_9bf2c8a4.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s309_9bf2c8a4';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'jumptopage', 0, __deltemplate_s309_9bf2c8a4);


/**
 * @param {{
 *    action: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s318_831406f5(opt_data, opt_ignored, opt_ijData) {
  ie_open('b');
    /** @desc require-x */
    var MSG_EXTERNAL_3017347163412966732 = goog.getMsg(
        'require-{$xxx}',
        {'xxx': '\u00010\u0001'});
    var lastIndex_320 = 0, partRe_320 = /\x01\d+\x01/g, match_320;
    do {
      match_320 = partRe_320.exec(MSG_EXTERNAL_3017347163412966732) || undefined;
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_3017347163412966732.substring(lastIndex_320, match_320 && match_320.index)));
      lastIndex_320 = partRe_320.lastIndex;
      switch (match_320 && match_320[0]) {
        case '\u00010\u0001':
          $badge({content: opt_data.action.param0}, null, opt_ijData);
          break;
      }
    } while (match_320);
  ie_close('b');
}
exports.__deltemplate_s318_831406f5 = __deltemplate_s318_831406f5;
if (goog.DEBUG) {
  __deltemplate_s318_831406f5.soyTemplateName = 'DDMRuleBuilder.__deltemplate_s318_831406f5';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('DDMRuleBuilder.action.idom'), 'require', 0, __deltemplate_s318_831406f5);

exports.render.params = ["plusIcon"];
exports.render.types = {"plusIcon":"html"};
exports.rule_list.params = ["kebab","strings"];
exports.rule_list.types = {"kebab":"html","strings":"?"};
exports.empty_list.params = ["message"];
exports.empty_list.types = {"message":"string"};
exports.rule_types.params = [];
exports.rule_types.types = {};
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
