/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMSelect.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMSelect.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {Object<string, *>=} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2_2dbfb377(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_2dbfb377 = __deltemplate_s2_2dbfb377;
if (goog.DEBUG) {
  __deltemplate_s2_2dbfb377.soyTemplateName = 'DDMSelect.__deltemplate_s2_2dbfb377';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'select', 0, __deltemplate_s2_2dbfb377);


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  predefinedValue: !Array<!goog.soy.data.SanitizedContent|string>,
 *  strings: {chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)},
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  visible: boolean,
 *  badgeCloseIcon: (function()|null|undefined),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fixedOptions: (!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  multiple: (boolean|null|undefined),
 *  open: (boolean|null|undefined),
 *  readOnly: (boolean|null|undefined),
 *  required: (boolean|null|undefined),
 *  selectCaretDoubleIcon: (function()|null|undefined),
 *  selectSearchIcon: (function()|null|undefined),
 *  showLabel: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>} */
  var options = soy.asserts.assertType(goog.isArray(opt_data.options), 'options', opt_data.options, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var predefinedValue = soy.asserts.assertType(goog.isArray(opt_data.predefinedValue), 'predefinedValue', opt_data.predefinedValue, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {{chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var value = soy.asserts.assertType(goog.isArray(opt_data.value), 'value', opt_data.value, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {function()|null|undefined} */
  var badgeCloseIcon = soy.asserts.assertType(opt_data.badgeCloseIcon == null || goog.isFunction(opt_data.badgeCloseIcon), 'badgeCloseIcon', opt_data.badgeCloseIcon, 'function()|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined} */
  var fixedOptions = soy.asserts.assertType(opt_data.fixedOptions == null || goog.isArray(opt_data.fixedOptions), 'fixedOptions', opt_data.fixedOptions, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var label = soy.asserts.assertType(opt_data.label == null || (goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var multiple = soy.asserts.assertType(opt_data.multiple == null || (goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0), 'multiple', opt_data.multiple, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var open = soy.asserts.assertType(opt_data.open == null || (goog.isBoolean(opt_data.open) || opt_data.open === 1 || opt_data.open === 0), 'open', opt_data.open, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var readOnly = soy.asserts.assertType(opt_data.readOnly == null || (goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0), 'readOnly', opt_data.readOnly, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {function()|null|undefined} */
  var selectCaretDoubleIcon = soy.asserts.assertType(opt_data.selectCaretDoubleIcon == null || goog.isFunction(opt_data.selectCaretDoubleIcon), 'selectCaretDoubleIcon', opt_data.selectCaretDoubleIcon, 'function()|null|undefined');
  /** @type {function()|null|undefined} */
  var selectSearchIcon = soy.asserts.assertType(opt_data.selectSearchIcon == null || goog.isFunction(opt_data.selectSearchIcon), 'selectSearchIcon', opt_data.selectSearchIcon, 'function()|null|undefined');
  /** @type {boolean|null|undefined} */
  var showLabel = soy.asserts.assertType(opt_data.showLabel == null || (goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0), 'showLabel', opt_data.showLabel, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var displayValues__soy27 = (value.length) > 0 ? value : (predefinedValue.length) > 0 ? predefinedValue : null;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide'));
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'input-select-wrapper');
    incrementalDom.elementOpenEnd();
      $select_label(opt_data, null, opt_ijData);
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'form-builder-select-field input-group-container');
      incrementalDom.elementOpenEnd();
        if (!readOnly && displayValues__soy27) {
          $hidden_select({dir: dir, displayValues: displayValues__soy27, multiple: multiple, name: name, options: options, strings: strings}, null, opt_ijData);
        }
        if (fixedOptions && (fixedOptions.length) > 0) {
          if (!readOnly && displayValues__soy27) {
            $hidden_select({dir: dir, displayValues: displayValues__soy27, multiple: multiple, name: name, options: fixedOptions, strings: strings}, null, opt_ijData);
          }
        }
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'form-control select-field-trigger');
            if (dir) {
              incrementalDom.attr('dir', dir);
            }
            incrementalDom.attr('id', name);
            incrementalDom.attr('name', name);
            if (readOnly) {
              incrementalDom.attr('disabled', '');
            }
        incrementalDom.elementOpenEnd();
          if (multiple) {
            if (displayValues__soy27) {
              incrementalDom.elementOpenStart('ul');
                  incrementalDom.attr('class', 'multiple-badge-list');
              incrementalDom.elementOpenEnd();
                var option90List = options;
                var option90ListLen = option90List.length;
                for (var option90Index = 0; option90Index < option90ListLen; option90Index++) {
                    var option90Data = option90List[option90Index];
                    $badge_item({badgeCloseIcon: badgeCloseIcon, option: option90Data, readOnly: readOnly, value: displayValues__soy27}, null, opt_ijData);
                  }
                if (fixedOptions) {
                  var fixedOption103List = fixedOptions;
                  var fixedOption103ListLen = fixedOption103List.length;
                  for (var fixedOption103Index = 0; fixedOption103Index < fixedOption103ListLen; fixedOption103Index++) {
                      var fixedOption103Data = fixedOption103List[fixedOption103Index];
                      $badge_item({badgeCloseIcon: badgeCloseIcon, option: fixedOption103Data, readOnly: readOnly, value: displayValues__soy27}, null, opt_ijData);
                    }
                }
              incrementalDom.elementClose('ul');
            } else {
              incrementalDom.elementOpenStart('div');
                  incrementalDom.attr('class', 'option-selected option-selected-placeholder');
              incrementalDom.elementOpenEnd();
                soyIdom.print(strings.chooseOptions);
              incrementalDom.elementClose('div');
            }
          } else {
            if (displayValues__soy27) {
              var displayValue146List = displayValues__soy27;
              var displayValue146ListLen = displayValue146List.length;
              for (var displayValue146Index = 0; displayValue146Index < displayValue146ListLen; displayValue146Index++) {
                  var displayValue146Data = displayValue146List[displayValue146Index];
                  var option127List = options;
                  var option127ListLen = option127List.length;
                  for (var option127Index = 0; option127Index < option127ListLen; option127Index++) {
                      var option127Data = option127List[option127Index];
                      if (option127Data.value == displayValue146Data) {
                        incrementalDom.elementOpenStart('div');
                            incrementalDom.attr('class', 'option-selected');
                            incrementalDom.attr('title', option127Data.label);
                        incrementalDom.elementOpenEnd();
                          soyIdom.print(option127Data.label);
                        incrementalDom.elementClose('div');
                      }
                    }
                  if (fixedOptions) {
                    var fixedOption142List = fixedOptions;
                    var fixedOption142ListLen = fixedOption142List.length;
                    for (var fixedOption142Index = 0; fixedOption142Index < fixedOption142ListLen; fixedOption142Index++) {
                        var fixedOption142Data = fixedOption142List[fixedOption142Index];
                        if (fixedOption142Data.value == displayValue146Data) {
                          incrementalDom.elementOpenStart('div');
                              incrementalDom.attr('class', 'option-selected');
                              incrementalDom.attr('title', fixedOption142Data.label);
                          incrementalDom.elementOpenEnd();
                            soyIdom.print(fixedOption142Data.label);
                          incrementalDom.elementClose('div');
                        }
                      }
                  }
                }
            } else {
              incrementalDom.elementOpenStart('div');
                  incrementalDom.attr('class', 'option-selected option-selected-placeholder');
              incrementalDom.elementOpenEnd();
                soyIdom.print(strings.chooseAnOption);
              incrementalDom.elementClose('div');
            }
          }
          incrementalDom.elementOpenStart('a');
              incrementalDom.attr('class', 'select-arrow-down-container');
              incrementalDom.attr('href', 'javascript:;');
          incrementalDom.elementOpenEnd();
            if (selectCaretDoubleIcon) {
              selectCaretDoubleIcon();
            }
          incrementalDom.elementClose('a');
        incrementalDom.elementClose('div');
        if (!readOnly) {
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'drop-chosen ' + (open ? '' : 'hide'));
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('div');
                incrementalDom.attr('aria-labelledby', 'theDropdownToggleId');
                incrementalDom.attr('class', 'dropdown-menu');
            incrementalDom.elementOpenEnd();
              incrementalDom.elementOpenStart('div');
                  incrementalDom.attr('class', 'dropdown-section');
              incrementalDom.elementOpenEnd();
                incrementalDom.elementOpenStart('div');
                    incrementalDom.attr('class', 'input-group input-group-sm');
                incrementalDom.elementOpenEnd();
                  incrementalDom.elementOpenStart('div');
                      incrementalDom.attr('class', 'input-group-item');
                  incrementalDom.elementOpenEnd();
                    incrementalDom.elementOpenStart('input');
                        incrementalDom.attr('autocomplete', 'off');
                        incrementalDom.attr('class', 'drop-chosen-search form-control input-group-inset input-group-inset-after');
                        incrementalDom.attr('placeholder', strings.search);
                        incrementalDom.attr('type', 'text');
                    incrementalDom.elementOpenEnd();
                    incrementalDom.elementClose('input');
                    incrementalDom.elementOpenStart('span');
                        incrementalDom.attr('class', 'input-group-inset-item input-group-inset-item-after');
                    incrementalDom.elementOpenEnd();
                      incrementalDom.elementOpenStart('button');
                          incrementalDom.attr('class', 'btn btn-link');
                          incrementalDom.attr('type', 'button');
                      incrementalDom.elementOpenEnd();
                        if (selectSearchIcon) {
                          selectSearchIcon();
                        }
                      incrementalDom.elementClose('button');
                    incrementalDom.elementClose('span');
                  incrementalDom.elementClose('div');
                incrementalDom.elementClose('div');
              incrementalDom.elementClose('div');
              $select_options(opt_data, null, opt_ijData);
            incrementalDom.elementClose('div');
          incrementalDom.elementClose('div');
        }
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  predefinedValue: !Array<!goog.soy.data.SanitizedContent|string>,
 *  strings: {chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)},
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  visible: boolean,
 *  badgeCloseIcon: (function()|null|undefined),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fixedOptions: (!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  multiple: (boolean|null|undefined),
 *  open: (boolean|null|undefined),
 *  readOnly: (boolean|null|undefined),
 *  required: (boolean|null|undefined),
 *  selectCaretDoubleIcon: (function()|null|undefined),
 *  selectSearchIcon: (function()|null|undefined),
 *  showLabel: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMSelect.render';
}


/**
 * @param {{
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  badgeCloseIcon: (function()|null|undefined),
 *  readOnly: (boolean|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $badge_item(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}} */
  var option = soy.asserts.assertType(goog.isObject(opt_data.option), 'option', opt_data.option, '{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var value = soy.asserts.assertType(goog.isArray(opt_data.value), 'value', opt_data.value, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {function()|null|undefined} */
  var badgeCloseIcon = soy.asserts.assertType(opt_data.badgeCloseIcon == null || goog.isFunction(opt_data.badgeCloseIcon), 'badgeCloseIcon', opt_data.badgeCloseIcon, 'function()|null|undefined');
  /** @type {boolean|null|undefined} */
  var readOnly = soy.asserts.assertType(opt_data.readOnly == null || (goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0), 'readOnly', opt_data.readOnly, 'boolean|null|undefined');
  if (value) {
    var currentValue205List = value;
    var currentValue205ListLen = currentValue205List.length;
    for (var currentValue205Index = 0; currentValue205Index < currentValue205ListLen; currentValue205Index++) {
        var currentValue205Data = currentValue205List[currentValue205Index];
        if (option.value == currentValue205Data) {
          incrementalDom.elementOpen('li');
            incrementalDom.elementOpenStart('span');
                incrementalDom.attr('class', 'badge badge-primary badge-sm multiple-badge');
                incrementalDom.attr('data-original-title', option.label);
                incrementalDom.attr('title', option.label);
            incrementalDom.elementOpenEnd();
              soyIdom.print(option.label);
              if (!readOnly) {
                incrementalDom.elementOpenStart('a');
                    incrementalDom.attr('class', 'trigger-badge-item-close');
                    incrementalDom.attr('data-badge-value', option.value);
                    incrementalDom.attr('href', 'javascript:void(0)');
                incrementalDom.elementOpenEnd();
                  soyIdom.print(badgeCloseIcon);
                incrementalDom.elementClose('a');
              }
            incrementalDom.elementClose('span');
          incrementalDom.elementClose('li');
        }
      }
  }
}
exports.badge_item = $badge_item;
/**
 * @typedef {{
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  badgeCloseIcon: (function()|null|undefined),
 *  readOnly: (boolean|null|undefined)
 * }}
 */
$badge_item.Params;
if (goog.DEBUG) {
  $badge_item.soyTemplateName = 'DDMSelect.badge_item';
}


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  showLabel: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $select_label(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var label = soy.asserts.assertType(opt_data.label == null || (goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var showLabel = soy.asserts.assertType(opt_data.showLabel == null || (goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0), 'showLabel', opt_data.showLabel, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('label');
      incrementalDom.attr('for', name);
  incrementalDom.elementOpenEnd();
    if (showLabel) {
      soyIdom.print(label);
      incrementalDom.text(' ');
    }
    if (required) {
      incrementalDom.elementOpenStart('svg');
          incrementalDom.attr('aria-hidden', 'true');
          incrementalDom.attr('class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('use');
            incrementalDom.attr('xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('use');
      incrementalDom.elementClose('svg');
    }
  incrementalDom.elementClose('label');
  if (showLabel) {
    if (tip) {
      incrementalDom.elementOpenStart('span');
          incrementalDom.attr('class', 'form-text');
      incrementalDom.elementOpenEnd();
        soyIdom.print(tip);
      incrementalDom.elementClose('span');
    }
  }
}
exports.select_label = $select_label;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  showLabel: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$select_label.Params;
if (goog.DEBUG) {
  $select_label.soyTemplateName = 'DDMSelect.select_label';
}


/**
 * @param {{
 *  displayValues: !Array<!goog.soy.data.SanitizedContent|string>,
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  strings: {chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)},
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  multiple: (boolean|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $hidden_select(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var displayValues = soy.asserts.assertType(goog.isArray(opt_data.displayValues), 'displayValues', opt_data.displayValues, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>} */
  var options = soy.asserts.assertType(goog.isArray(opt_data.options), 'options', opt_data.options, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {{chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var multiple = soy.asserts.assertType(opt_data.multiple == null || (goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0), 'multiple', opt_data.multiple, 'boolean|null|undefined');
  incrementalDom.elementOpenStart('select');
      incrementalDom.attr('class', 'form-control hide');
      if (dir) {
        incrementalDom.attr('dir', dir);
      }
      incrementalDom.attr('id', name);
      incrementalDom.attr('name', name);
      if (multiple) {
        incrementalDom.attr('multiple', '');
        incrementalDom.attr('size', (options.length));
      }
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('option');
        if (dir) {
          incrementalDom.attr('dir', dir);
        }
        incrementalDom.attr('disabled', '');
        if ((displayValues.length) == 0) {
          incrementalDom.attr('selected', '');
        }
        incrementalDom.attr('value', '');
    incrementalDom.elementOpenEnd();
      soyIdom.print(strings.chooseAnOption);
    incrementalDom.elementClose('option');
    var option285List = options;
    var option285ListLen = option285List.length;
    for (var option285Index = 0; option285Index < option285ListLen; option285Index++) {
        var option285Data = option285List[option285Index];
        $select_hidden_options({dir: dir, option: option285Data, values: displayValues}, null, opt_ijData);
      }
  incrementalDom.elementClose('select');
}
exports.hidden_select = $hidden_select;
/**
 * @typedef {{
 *  displayValues: !Array<!goog.soy.data.SanitizedContent|string>,
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  strings: {chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)},
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  multiple: (boolean|null|undefined)
 * }}
 */
$hidden_select.Params;
if (goog.DEBUG) {
  $hidden_select.soyTemplateName = 'DDMSelect.hidden_select';
}


/**
 * @param {{
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  values: !Array<!goog.soy.data.SanitizedContent|string>,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $select_hidden_options(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}} */
  var option = soy.asserts.assertType(goog.isObject(opt_data.option), 'option', opt_data.option, '{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var values = soy.asserts.assertType(goog.isArray(opt_data.values), 'values', opt_data.values, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var selected__soy293 = function() {
    var currentValue300List = values;
    var currentValue300ListLen = currentValue300List.length;
    for (var currentValue300Index = 0; currentValue300Index < currentValue300ListLen; currentValue300Index++) {
        var currentValue300Data = currentValue300List[currentValue300Index];
        if (currentValue300Data == option.value) {
          incrementalDom.attr('selected', '');
        }
      }
  };
  incrementalDom.elementOpenStart('option');
      if (dir) {
        incrementalDom.attr('dir', dir);
      }
      selected__soy293();
      incrementalDom.attr('value', option.value);
  incrementalDom.elementOpenEnd();
    soyIdom.print(option.label);
  incrementalDom.elementClose('option');
}
exports.select_hidden_options = $select_hidden_options;
/**
 * @typedef {{
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  values: !Array<!goog.soy.data.SanitizedContent|string>,
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$select_hidden_options.Params;
if (goog.DEBUG) {
  $select_hidden_options.soyTemplateName = 'DDMSelect.select_hidden_options';
}


/**
 * @param {{
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  strings: {chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)},
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  fixedOptions: (!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined),
 *  multiple: (boolean|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $select_options(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>} */
  var options = soy.asserts.assertType(goog.isArray(opt_data.options), 'options', opt_data.options, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {{chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var value = soy.asserts.assertType(goog.isArray(opt_data.value), 'value', opt_data.value, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined} */
  var fixedOptions = soy.asserts.assertType(opt_data.fixedOptions == null || goog.isArray(opt_data.fixedOptions), 'fixedOptions', opt_data.fixedOptions, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined');
  /** @type {boolean|null|undefined} */
  var multiple = soy.asserts.assertType(opt_data.multiple == null || (goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0), 'multiple', opt_data.multiple, 'boolean|null|undefined');
  incrementalDom.elementOpenStart('ul');
      incrementalDom.attr('class', 'dropdown-visible list-unstyled results-chosen');
  incrementalDom.elementOpenEnd();
    if ((options.length) > 0) {
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'inline-scroller');
      incrementalDom.elementOpenEnd();
        $plot_dropdown_rows({fixed: false, multiple: multiple, options: options, value: value}, null, opt_ijData);
      incrementalDom.elementClose('div');
    }
    if (fixedOptions) {
      if ((options.length) > 0 && (fixedOptions.length) > 0) {
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'dropdown-divider');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
      }
      $plot_dropdown_rows({fixed: true, multiple: multiple, options: fixedOptions, value: value}, null, opt_ijData);
    }
    if ((options.length) == 0) {
      incrementalDom.elementOpenStart('li');
          incrementalDom.attr('class', 'no-results-list');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpen('span');
          soyIdom.print(strings.emptyList);
        incrementalDom.elementClose('span');
      incrementalDom.elementClose('li');
    }
  incrementalDom.elementClose('ul');
}
exports.select_options = $select_options;
/**
 * @typedef {{
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  strings: {chooseAnOption: (!goog.soy.data.SanitizedContent|string), chooseOptions: (!goog.soy.data.SanitizedContent|string), emptyList: (!goog.soy.data.SanitizedContent|string), search: (!goog.soy.data.SanitizedContent|string)},
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  fixedOptions: (!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined),
 *  multiple: (boolean|null|undefined)
 * }}
 */
$select_options.Params;
if (goog.DEBUG) {
  $select_options.soyTemplateName = 'DDMSelect.select_options';
}


/**
 * @param {{
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  fixed: (boolean|null|undefined),
 *  multiple: (boolean|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $plot_dropdown_rows(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>} */
  var options = soy.asserts.assertType(goog.isArray(opt_data.options), 'options', opt_data.options, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>');
  /** @type {!Array<!goog.soy.data.SanitizedContent|string>} */
  var value = soy.asserts.assertType(goog.isArray(opt_data.value), 'value', opt_data.value, '!Array<!goog.soy.data.SanitizedContent|string>');
  /** @type {boolean|null|undefined} */
  var fixed = soy.asserts.assertType(opt_data.fixed == null || (goog.isBoolean(opt_data.fixed) || opt_data.fixed === 1 || opt_data.fixed === 0), 'fixed', opt_data.fixed, 'boolean|null|undefined');
  /** @type {boolean|null|undefined} */
  var multiple = soy.asserts.assertType(opt_data.multiple == null || (goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0), 'multiple', opt_data.multiple, 'boolean|null|undefined');
  var option394List = options;
  var option394ListLen = option394List.length;
  for (var option394Index = 0; option394Index < option394ListLen; option394Index++) {
      var option394Data = option394List[option394Index];
      var selected__soy361 = '';
      if (value) {
        var currentValue371List = value;
        var currentValue371ListLen = currentValue371List.length;
        for (var currentValue371Index = 0; currentValue371Index < currentValue371ListLen; currentValue371Index++) {
            var currentValue371Data = currentValue371List[currentValue371Index];
            selected__soy361 += currentValue371Data == option394Data.value ? 'selected' : '';
          }
      }
      if (multiple) {
        $multiple_selection({fixed: fixed, indexOption: option394Index, option: option394Data, selected: selected__soy361}, null, opt_ijData);
      } else {
        $simple_selection({fixed: fixed, indexOption: option394Index, option: option394Data, selected: selected__soy361}, null, opt_ijData);
      }
    }
}
exports.plot_dropdown_rows = $plot_dropdown_rows;
/**
 * @typedef {{
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>,
 *  value: !Array<!goog.soy.data.SanitizedContent|string>,
 *  fixed: (boolean|null|undefined),
 *  multiple: (boolean|null|undefined)
 * }}
 */
$plot_dropdown_rows.Params;
if (goog.DEBUG) {
  $plot_dropdown_rows.soyTemplateName = 'DDMSelect.plot_dropdown_rows';
}


/**
 * @param {{
 *  indexOption: number,
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  selected: (!goog.soy.data.SanitizedContent|string),
 *  fixed: (boolean|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $multiple_selection(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var indexOption = soy.asserts.assertType(goog.isNumber(opt_data.indexOption), 'indexOption', opt_data.indexOption, 'number');
  /** @type {{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}} */
  var option = soy.asserts.assertType(goog.isObject(opt_data.option), 'option', opt_data.option, '{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var selected = soy.asserts.assertType(goog.isString(opt_data.selected) || opt_data.selected instanceof goog.soy.data.SanitizedContent, 'selected', opt_data.selected, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean|null|undefined} */
  var fixed = soy.asserts.assertType(opt_data.fixed == null || (goog.isBoolean(opt_data.fixed) || opt_data.fixed === 1 || opt_data.fixed === 0), 'fixed', opt_data.fixed, 'boolean|null|undefined');
  var attributesMultipleSelection__soy403 = function() {
    incrementalDom.attr('class', (selected ? ' active ' : '') + 'dropdown-item' + (fixed ? ' fixed' : ' unfixed'));
    incrementalDom.attr('data-option-index', indexOption);
    incrementalDom.attr('data-option-value', option.value);
    incrementalDom.attr('data-option-selected', selected ? 'true' : '');
  };
  incrementalDom.elementOpenStart('li');
      attributesMultipleSelection__soy403();
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'custom-checkbox custom-control');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpen('label');
        incrementalDom.elementOpenStart('input');
            if (selected) {
              incrementalDom.attr('checked', '');
            }
            incrementalDom.attr('class', 'custom-control-input');
            incrementalDom.attr('type', 'checkbox');
            incrementalDom.attr('value', '');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('input');
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', 'custom-control-label ');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('span');
              incrementalDom.attr('class', 'custom-control-label-text');
          incrementalDom.elementOpenEnd();
            soyIdom.print(option.label);
          incrementalDom.elementClose('span');
        incrementalDom.elementClose('span');
      incrementalDom.elementClose('label');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('li');
}
exports.multiple_selection = $multiple_selection;
/**
 * @typedef {{
 *  indexOption: number,
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  selected: (!goog.soy.data.SanitizedContent|string),
 *  fixed: (boolean|null|undefined)
 * }}
 */
$multiple_selection.Params;
if (goog.DEBUG) {
  $multiple_selection.soyTemplateName = 'DDMSelect.multiple_selection';
}


/**
 * @param {{
 *  indexOption: number,
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  selected: (!goog.soy.data.SanitizedContent|string),
 *  fixed: (boolean|null|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $simple_selection(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {number} */
  var indexOption = soy.asserts.assertType(goog.isNumber(opt_data.indexOption), 'indexOption', opt_data.indexOption, 'number');
  /** @type {{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}} */
  var option = soy.asserts.assertType(goog.isObject(opt_data.option), 'option', opt_data.option, '{label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var selected = soy.asserts.assertType(goog.isString(opt_data.selected) || opt_data.selected instanceof goog.soy.data.SanitizedContent, 'selected', opt_data.selected, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean|null|undefined} */
  var fixed = soy.asserts.assertType(opt_data.fixed == null || (goog.isBoolean(opt_data.fixed) || opt_data.fixed === 1 || opt_data.fixed === 0), 'fixed', opt_data.fixed, 'boolean|null|undefined');
  var attributesNormalSelection__soy438 = function() {
    incrementalDom.attr('class', ' select-option-item' + (fixed ? ' fixed' : ' unfixed'));
    incrementalDom.attr('data-option-index', indexOption);
    incrementalDom.attr('data-option-value', option.value);
  };
  incrementalDom.elementOpenStart('li');
      attributesNormalSelection__soy438();
  incrementalDom.elementOpenEnd();
    var itemAttributes__soy453 = function() {
      incrementalDom.attr('class', ' dropdown-item' + (selected ? ' active' : ''));
      incrementalDom.attr('data-option-selected', selected ? 'true' : '');
      incrementalDom.attr('href', 'javascript:;');
      incrementalDom.attr('title', option.label);
    };
    incrementalDom.elementOpenStart('a');
        itemAttributes__soy453();
    incrementalDom.elementOpenEnd();
      soyIdom.print(option.label);
    incrementalDom.elementClose('a');
  incrementalDom.elementClose('li');
}
exports.simple_selection = $simple_selection;
/**
 * @typedef {{
 *  indexOption: number,
 *  option: {label: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)},
 *  selected: (!goog.soy.data.SanitizedContent|string),
 *  fixed: (boolean|null|undefined)
 * }}
 */
$simple_selection.Params;
if (goog.DEBUG) {
  $simple_selection.soyTemplateName = 'DDMSelect.simple_selection';
}

exports.render.params = ["name","options","pathThemeImages","predefinedValue","strings","value","visible","badgeCloseIcon","dir","fixedOptions","label","multiple","open","readOnly","required","selectCaretDoubleIcon","selectSearchIcon","showLabel","tip"];
exports.render.types = {"name":"string","options":"list<[label: string, value: string]>","pathThemeImages":"string","predefinedValue":"list<string>","strings":"[chooseAnOption: string, chooseOptions: string, search: string, emptyList: string]","value":"list<string>","visible":"bool","badgeCloseIcon":"html","dir":"string","fixedOptions":"list<[label: string, value: string]>","label":"string","multiple":"bool","open":"bool","readOnly":"bool","required":"bool","selectCaretDoubleIcon":"html","selectSearchIcon":"html","showLabel":"bool","tip":"string"};
exports.badge_item.params = ["option","value","badgeCloseIcon","readOnly"];
exports.badge_item.types = {"option":"[label: string, value: string]","value":"list<string>","badgeCloseIcon":"html","readOnly":"bool"};
exports.select_label.params = ["name","pathThemeImages","label","required","showLabel","tip"];
exports.select_label.types = {"name":"string","pathThemeImages":"string","label":"string","required":"bool","showLabel":"bool","tip":"string"};
exports.hidden_select.params = ["displayValues","name","options","strings","dir","multiple"];
exports.hidden_select.types = {"displayValues":"list<string>","name":"string","options":"list<[label: string, value: string]>","strings":"[chooseAnOption: string, chooseOptions: string, search: string, emptyList: string]","dir":"string","multiple":"bool"};
exports.select_hidden_options.params = ["option","values","dir"];
exports.select_hidden_options.types = {"option":"[label: string, value: string]","values":"list<string>","dir":"string"};
exports.select_options.params = ["options","strings","value","fixedOptions","multiple"];
exports.select_options.types = {"options":"list<[label: string, value: string]>","strings":"[chooseAnOption: string, chooseOptions: string, search: string, emptyList: string]","value":"list<string>","fixedOptions":"list<[label: string, value: string]>","multiple":"bool"};
exports.plot_dropdown_rows.params = ["options","value","fixed","multiple"];
exports.plot_dropdown_rows.types = {"options":"list<[label: string, value: string]>","value":"list<string>","fixed":"bool","multiple":"bool"};
exports.multiple_selection.params = ["indexOption","option","selected","fixed"];
exports.multiple_selection.types = {"indexOption":"int","option":"[label: string, value: string]","selected":"string","fixed":"bool"};
exports.simple_selection.params = ["indexOption","option","selected","fixed"];
exports.simple_selection.types = {"indexOption":"int","option":"[label: string, value: string]","selected":"string","fixed":"bool"};
templates = exports;
return exports;

});

class DDMSelect extends Component {}
Soy.register(DDMSelect, templates);
export { DDMSelect, templates };
export default templates;
/* jshint ignore:end */
