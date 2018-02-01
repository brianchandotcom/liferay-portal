/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMSelect.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMSelect.incrementaldom');

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
 * @param {Object<string, *>=} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function __deltemplate_s2_2dbfb377(opt_data, opt_ignored, opt_ijData) {
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
 *    name: string,
 *    options: !Array<{label: string, value: string}>,
 *    predefinedValue: !Array<string>,
 *    strings: {chooseAnOption: string, chooseOptions: string, emptyList: string, search: string},
 *    value: !Array<string>,
 *    visible: boolean,
 *    badgeCloseIcon: (?soydata.SanitizedHtml|string|undefined),
 *    dir: (null|string|undefined),
 *    fixedOptions: (?Array<{label: string, value: string}>|undefined),
 *    label: (null|string|undefined),
 *    multiple: (boolean|null|undefined),
 *    open: (boolean|null|undefined),
 *    readOnly: (boolean|null|undefined),
 *    required: (boolean|null|undefined),
 *    selectCaretDoubleIcon: (?soydata.SanitizedHtml|string|undefined),
 *    selectSearchIcon: (?soydata.SanitizedHtml|string|undefined),
 *    showLabel: (boolean|null|undefined),
 *    tip: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  var options = goog.asserts.assertArray(opt_data.options, "expected parameter 'options' of type list<[label: string, value: string]>.");
  var predefinedValue = goog.asserts.assertArray(opt_data.predefinedValue, "expected parameter 'predefinedValue' of type list<string>.");
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [chooseAnOption: string, chooseOptions: string, emptyList: string, search: string].");
  var value = goog.asserts.assertArray(opt_data.value, "expected parameter 'value' of type list<string>.");
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  soy.asserts.assertType(opt_data.badgeCloseIcon == null || (opt_data.badgeCloseIcon instanceof Function) || (opt_data.badgeCloseIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.badgeCloseIcon), 'badgeCloseIcon', opt_data.badgeCloseIcon, '?soydata.SanitizedHtml|string|undefined');
  var badgeCloseIcon = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.badgeCloseIcon);
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.fixedOptions == null || goog.isArray(opt_data.fixedOptions), 'fixedOptions', opt_data.fixedOptions, '?Array<{label: string, value: string}>|undefined');
  var fixedOptions = /** @type {?Array<{label: string, value: string}>|undefined} */ (opt_data.fixedOptions);
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(opt_data.multiple == null || goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0, 'multiple', opt_data.multiple, 'boolean|null|undefined');
  var multiple = /** @type {boolean|null|undefined} */ (opt_data.multiple);
  soy.asserts.assertType(opt_data.open == null || goog.isBoolean(opt_data.open) || opt_data.open === 1 || opt_data.open === 0, 'open', opt_data.open, 'boolean|null|undefined');
  var open = /** @type {boolean|null|undefined} */ (opt_data.open);
  soy.asserts.assertType(opt_data.readOnly == null || goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean|null|undefined');
  var readOnly = /** @type {boolean|null|undefined} */ (opt_data.readOnly);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(opt_data.selectCaretDoubleIcon == null || (opt_data.selectCaretDoubleIcon instanceof Function) || (opt_data.selectCaretDoubleIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.selectCaretDoubleIcon), 'selectCaretDoubleIcon', opt_data.selectCaretDoubleIcon, '?soydata.SanitizedHtml|string|undefined');
  var selectCaretDoubleIcon = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.selectCaretDoubleIcon);
  soy.asserts.assertType(opt_data.selectSearchIcon == null || (opt_data.selectSearchIcon instanceof Function) || (opt_data.selectSearchIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.selectSearchIcon), 'selectSearchIcon', opt_data.selectSearchIcon, '?soydata.SanitizedHtml|string|undefined');
  var selectSearchIcon = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.selectSearchIcon);
  soy.asserts.assertType(opt_data.showLabel == null || goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean|null|undefined');
  var showLabel = /** @type {boolean|null|undefined} */ (opt_data.showLabel);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  var displayValue__soy5 = value.length > 0 ? value : predefinedValue.length > 0 ? predefinedValue : null;
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide'),
      'data-fieldname', name);
    ie_open('div', null, null,
        'class', 'input-select-wrapper');
      if (showLabel) {
        $select_label(opt_data, null, opt_ijData);
      }
      ie_open('div', null, null,
          'class', 'form-builder-select-field input-group-container');
        if (! readOnly && displayValue__soy5) {
          $hidden_select({dir: dir, displayValue: displayValue__soy5, multiple: multiple, name: name, options: options, strings: strings}, null, opt_ijData);
        }
        if (fixedOptions) {
          if (! readOnly && displayValue__soy5) {
            $hidden_select({dir: dir, displayValue: displayValue__soy5, multiple: multiple, name: name, options: fixedOptions, strings: strings}, null, opt_ijData);
          }
        }
        ie_open_start('div');
            iattr('class', 'form-control select-field-trigger');
            if (dir) {
              iattr('dir', dir);
            }
            iattr('id', name);
            iattr('name', name);
        ie_open_end();
          if (multiple) {
            if (displayValue__soy5) {
              ie_open('ul', null, null,
                  'class', 'multiple-badge-list');
                var optionList56 = options;
                var optionListLen56 = optionList56.length;
                for (var optionIndex56 = 0; optionIndex56 < optionListLen56; optionIndex56++) {
                  var optionData56 = optionList56[optionIndex56];
                  $badge_item({badgeCloseIcon: badgeCloseIcon, option: optionData56, readOnly: readOnly, value: displayValue__soy5}, null, opt_ijData);
                }
                if (fixedOptions) {
                  var fixedOptionList65 = fixedOptions;
                  var fixedOptionListLen65 = fixedOptionList65.length;
                  for (var fixedOptionIndex65 = 0; fixedOptionIndex65 < fixedOptionListLen65; fixedOptionIndex65++) {
                    var fixedOptionData65 = fixedOptionList65[fixedOptionIndex65];
                    $badge_item({badgeCloseIcon: badgeCloseIcon, option: fixedOptionData65, readOnly: readOnly, value: displayValue__soy5}, null, opt_ijData);
                  }
                }
              ie_close('ul');
            } else {
              ie_open('div', null, null,
                  'class', 'option-selected option-selected-placeholder');
                var dyn0 = strings.chooseOptions;
                if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
              ie_close('div');
            }
          } else {
            if (displayValue__soy5) {
              var optionList82 = options;
              var optionListLen82 = optionList82.length;
              for (var optionIndex82 = 0; optionIndex82 < optionListLen82; optionIndex82++) {
                var optionData82 = optionList82[optionIndex82];
                if (optionData82.value == displayValue__soy5) {
                  ie_open('div', null, null,
                      'class', 'option-selected',
                      'title', optionData82.label);
                    var dyn1 = optionData82.label;
                    if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
                  ie_close('div');
                }
              }
              if (fixedOptions) {
                var fixedOptionList93 = fixedOptions;
                var fixedOptionListLen93 = fixedOptionList93.length;
                for (var fixedOptionIndex93 = 0; fixedOptionIndex93 < fixedOptionListLen93; fixedOptionIndex93++) {
                  var fixedOptionData93 = fixedOptionList93[fixedOptionIndex93];
                  if (fixedOptionData93.value == displayValue__soy5) {
                    ie_open('div', null, null,
                        'class', 'option-selected',
                        'title', fixedOptionData93.label);
                      var dyn2 = fixedOptionData93.label;
                      if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
                    ie_close('div');
                  }
                }
              }
            } else {
              ie_open('div', null, null,
                  'class', 'option-selected option-selected-placeholder');
                var dyn3 = strings.chooseAnOption;
                if (typeof dyn3 == 'function') dyn3(); else if (dyn3 != null) itext(dyn3);
              ie_close('div');
            }
          }
          ie_open('a', null, null,
              'class', 'select-arrow-down-container',
              'href', 'javascript:;');
            if (selectCaretDoubleIcon) {
              selectCaretDoubleIcon();
            }
          ie_close('a');
        ie_close('div');
        if (! readOnly) {
          ie_open('div', null, null,
              'class', 'drop-chosen ' + (open ? '' : 'hide'));
            ie_open('div', null, null,
                'class', 'search-chosen');
              ie_open('div', null, null,
                  'class', 'select-search-container');
                if (selectSearchIcon) {
                  ie_open('a', null, null,
                      'class', '',
                      'href', 'javascript:;');
                    selectSearchIcon();
                  ie_close('a');
                }
              ie_close('div');
              ie_open('input', null, null,
                  'autocomplete', 'off',
                  'class', 'drop-chosen-search',
                  'placeholder', strings.search,
                  'type', 'text');
              ie_close('input');
            ie_close('div');
            $select_options(opt_data, null, opt_ijData);
          ie_close('div');
        }
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMSelect.render';
}


/**
 * @param {{
 *    option: {label: string, value: string},
 *    value: !Array<string>,
 *    badgeCloseIcon: (?soydata.SanitizedHtml|string|undefined),
 *    readOnly: (boolean|null|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $badge_item(opt_data, opt_ignored, opt_ijData) {
  var option = goog.asserts.assertObject(opt_data.option, "expected parameter 'option' of type [label: string, value: string].");
  var value = goog.asserts.assertArray(opt_data.value, "expected parameter 'value' of type list<string>.");
  soy.asserts.assertType(opt_data.badgeCloseIcon == null || (opt_data.badgeCloseIcon instanceof Function) || (opt_data.badgeCloseIcon instanceof soydata.UnsanitizedText) || goog.isString(opt_data.badgeCloseIcon), 'badgeCloseIcon', opt_data.badgeCloseIcon, '?soydata.SanitizedHtml|string|undefined');
  var badgeCloseIcon = /** @type {?soydata.SanitizedHtml|string|undefined} */ (opt_data.badgeCloseIcon);
  soy.asserts.assertType(opt_data.readOnly == null || goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean|null|undefined');
  var readOnly = /** @type {boolean|null|undefined} */ (opt_data.readOnly);
  if (value) {
    var currentValueList139 = value;
    var currentValueListLen139 = currentValueList139.length;
    for (var currentValueIndex139 = 0; currentValueIndex139 < currentValueListLen139; currentValueIndex139++) {
      var currentValueData139 = currentValueList139[currentValueIndex139];
      if (option.value == currentValueData139) {
        ie_open('li');
          ie_open('span', null, null,
              'class', 'badge badge-default badge-sm multiple-badge',
              'data-original-title', option.label,
              'title', option.label);
            var dyn4 = option.label;
            if (typeof dyn4 == 'function') dyn4(); else if (dyn4 != null) itext(dyn4);
            if (! readOnly) {
              ie_open('a', null, null,
                  'class', 'trigger-badge-item-close',
                  'data-badge-value', option.value,
                  'href', 'javascript:void(0)');
                var dyn5 = badgeCloseIcon;
                if (typeof dyn5 == 'function') dyn5(); else if (dyn5 != null) itext(dyn5);
              ie_close('a');
            }
          ie_close('span');
        ie_close('li');
      }
    }
  }
}
exports.badge_item = $badge_item;
if (goog.DEBUG) {
  $badge_item.soyTemplateName = 'DDMSelect.badge_item';
}


/**
 * @param {{
 *    name: string,
 *    label: (null|string|undefined),
 *    readOnly: (boolean|null|undefined),
 *    required: (boolean|null|undefined),
 *    tip: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $select_label(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(opt_data.readOnly == null || goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean|null|undefined');
  var readOnly = /** @type {boolean|null|undefined} */ (opt_data.readOnly);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  ie_open('label', null, null,
      'class', 'control-label' + (readOnly ? ' disabled' : ''),
      'for', name);
    var dyn6 = label;
    if (typeof dyn6 == 'function') dyn6(); else if (dyn6 != null) itext(dyn6);
    if (required) {
      ie_void('span', null, null,
          'class', 'icon-asterisk text-warning');
    }
  ie_close('label');
  ie_open('p', null, null,
      'class', 'liferay-ddm-form-field-tip');
    var dyn7 = tip ? tip : '';
    if (typeof dyn7 == 'function') dyn7(); else if (dyn7 != null) itext(dyn7);
  ie_close('p');
}
exports.select_label = $select_label;
if (goog.DEBUG) {
  $select_label.soyTemplateName = 'DDMSelect.select_label';
}


/**
 * @param {{
 *    displayValue: !Array<string>,
 *    name: string,
 *    options: !Array<{label: string, value: string}>,
 *    strings: {chooseAnOption: string, chooseOptions: string, emptyList: string, search: string},
 *    dir: (null|string|undefined),
 *    multiple: (boolean|null|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $hidden_select(opt_data, opt_ignored, opt_ijData) {
  var displayValue = goog.asserts.assertArray(opt_data.displayValue, "expected parameter 'displayValue' of type list<string>.");
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  var options = goog.asserts.assertArray(opt_data.options, "expected parameter 'options' of type list<[label: string, value: string]>.");
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [chooseAnOption: string, chooseOptions: string, emptyList: string, search: string].");
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.multiple == null || goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0, 'multiple', opt_data.multiple, 'boolean|null|undefined');
  var multiple = /** @type {boolean|null|undefined} */ (opt_data.multiple);
  ie_open_start('select');
      iattr('class', 'form-control hide');
      if (dir) {
        iattr('dir', dir);
      }
      iattr('id', name);
      iattr('name', name);
      if (multiple) {
        iattr('multiple', '');
        iattr('size', options.length);
      }
  ie_open_end();
    ie_open_start('option');
        if (dir) {
          iattr('dir', dir);
        }
        iattr('disabled', '');
        if (displayValue.length == 0) {
          iattr('selected', '');
        }
        iattr('value', '');
    ie_open_end();
      var dyn8 = strings.chooseAnOption;
      if (typeof dyn8 == 'function') dyn8(); else if (dyn8 != null) itext(dyn8);
    ie_close('option');
    var optionList188 = options;
    var optionListLen188 = optionList188.length;
    for (var optionIndex188 = 0; optionIndex188 < optionListLen188; optionIndex188++) {
      var optionData188 = optionList188[optionIndex188];
      $select_hidden_options({dir: dir, option: optionData188, values: displayValue}, null, opt_ijData);
    }
  ie_close('select');
}
exports.hidden_select = $hidden_select;
if (goog.DEBUG) {
  $hidden_select.soyTemplateName = 'DDMSelect.hidden_select';
}


/**
 * @param {{
 *    option: {label: string, value: string},
 *    values: !Array<string>,
 *    dir: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $select_hidden_options(opt_data, opt_ignored, opt_ijData) {
  var option = goog.asserts.assertObject(opt_data.option, "expected parameter 'option' of type [label: string, value: string].");
  var values = goog.asserts.assertArray(opt_data.values, "expected parameter 'values' of type list<string>.");
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  var selected__soy192 = function() {
    var currentValueList196 = values;
    var currentValueListLen196 = currentValueList196.length;
    for (var currentValueIndex196 = 0; currentValueIndex196 < currentValueListLen196; currentValueIndex196++) {
      var currentValueData196 = currentValueList196[currentValueIndex196];
      if (currentValueData196 == option.value) {
        iattr('selected', '');
      }
    }
  };
  ie_open_start('option');
      if (dir) {
        iattr('dir', dir);
      }
      selected__soy192();
      iattr('value', option.value);
  ie_open_end();
    var dyn9 = option.label;
    if (typeof dyn9 == 'function') dyn9(); else if (dyn9 != null) itext(dyn9);
  ie_close('option');
}
exports.select_hidden_options = $select_hidden_options;
if (goog.DEBUG) {
  $select_hidden_options.soyTemplateName = 'DDMSelect.select_hidden_options';
}


/**
 * @param {{
 *    options: !Array<{label: string, value: string}>,
 *    strings: {chooseAnOption: string, chooseOptions: string, emptyList: string, search: string},
 *    value: !Array<string>,
 *    fixedOptions: (?Array<{label: string, value: string}>|undefined),
 *    multiple: (boolean|null|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $select_options(opt_data, opt_ignored, opt_ijData) {
  var options = goog.asserts.assertArray(opt_data.options, "expected parameter 'options' of type list<[label: string, value: string]>.");
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [chooseAnOption: string, chooseOptions: string, emptyList: string, search: string].");
  var value = goog.asserts.assertArray(opt_data.value, "expected parameter 'value' of type list<string>.");
  soy.asserts.assertType(opt_data.fixedOptions == null || goog.isArray(opt_data.fixedOptions), 'fixedOptions', opt_data.fixedOptions, '?Array<{label: string, value: string}>|undefined');
  var fixedOptions = /** @type {?Array<{label: string, value: string}>|undefined} */ (opt_data.fixedOptions);
  soy.asserts.assertType(opt_data.multiple == null || goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0, 'multiple', opt_data.multiple, 'boolean|null|undefined');
  var multiple = /** @type {boolean|null|undefined} */ (opt_data.multiple);
  ie_open('ul', null, null,
      'class', 'dropdown-menu dropdown-visible results-chosen');
    if (options.length > 0) {
      ie_open('div', null, null,
          'class', 'inline-scroller');
        $plot_dropdown_rows({fixed: false, multiple: multiple, options: options, value: value}, null, opt_ijData);
      ie_close('div');
    }
    if (fixedOptions) {
      if (options.length > 0 && fixedOptions.length > 0) {
        ie_void('div', null, null,
            'class', 'dropdown-divider');
      }
      $plot_dropdown_rows({fixed: true, multiple: multiple, options: fixedOptions, value: value}, null, opt_ijData);
    }
    if (options.length == 0) {
      ie_open('li', null, null,
          'class', 'no-results-list');
        ie_open('span');
          var dyn10 = strings.emptyList;
          if (typeof dyn10 == 'function') dyn10(); else if (dyn10 != null) itext(dyn10);
        ie_close('span');
      ie_close('li');
    }
  ie_close('ul');
}
exports.select_options = $select_options;
if (goog.DEBUG) {
  $select_options.soyTemplateName = 'DDMSelect.select_options';
}


/**
 * @param {{
 *    options: !Array<{label: string, value: string}>,
 *    value: !Array<string>,
 *    fixed: (boolean|null|undefined),
 *    multiple: (boolean|null|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $plot_dropdown_rows(opt_data, opt_ignored, opt_ijData) {
  var options = goog.asserts.assertArray(opt_data.options, "expected parameter 'options' of type list<[label: string, value: string]>.");
  var value = goog.asserts.assertArray(opt_data.value, "expected parameter 'value' of type list<string>.");
  soy.asserts.assertType(opt_data.fixed == null || goog.isBoolean(opt_data.fixed) || opt_data.fixed === 1 || opt_data.fixed === 0, 'fixed', opt_data.fixed, 'boolean|null|undefined');
  var fixed = /** @type {boolean|null|undefined} */ (opt_data.fixed);
  soy.asserts.assertType(opt_data.multiple == null || goog.isBoolean(opt_data.multiple) || opt_data.multiple === 1 || opt_data.multiple === 0, 'multiple', opt_data.multiple, 'boolean|null|undefined');
  var multiple = /** @type {boolean|null|undefined} */ (opt_data.multiple);
  var optionList281 = options;
  var optionListLen281 = optionList281.length;
  for (var optionIndex281 = 0; optionIndex281 < optionListLen281; optionIndex281++) {
    var optionData281 = optionList281[optionIndex281];
    var selected__soy239 = '';
    if (value) {
      var currentValueList245 = value;
      var currentValueListLen245 = currentValueList245.length;
      for (var currentValueIndex245 = 0; currentValueIndex245 < currentValueListLen245; currentValueIndex245++) {
        var currentValueData245 = currentValueList245[currentValueIndex245];
        selected__soy239 += (currentValueData245 == optionData281.value) ? 'selected' : '';
      }
    }
    var attributes__soy247 = function() {
      iattr('class', ' dropdown-item select-option-item' + ((fixed) ? ' fixed' : ' unfixed') + ((selected__soy239) ? ' option-selected' : ''));
      iattr('data-option-index', optionIndex281);
      iattr('data-option-selected', (selected__soy239) ? 'true' : '');
      iattr('data-option-value', optionData281.value);
    };
    ie_open_start('li');
        attributes__soy247();
    ie_open_end();
      if (multiple) {
        ie_open_start('input');
            iattr('type', 'checkbox');
            iattr('value', '');
            if (selected__soy239 == 'selected') {
              iattr('checked', '');
            }
        ie_open_end();
        ie_close('input');
      }
      ie_open('span', null, null,
          'title', optionData281.label);
        var dyn11 = optionData281.label;
        if (typeof dyn11 == 'function') dyn11(); else if (dyn11 != null) itext(dyn11);
      ie_close('span');
    ie_close('li');
  }
}
exports.plot_dropdown_rows = $plot_dropdown_rows;
if (goog.DEBUG) {
  $plot_dropdown_rows.soyTemplateName = 'DDMSelect.plot_dropdown_rows';
}

exports.render.params = ["name","predefinedValue","value","visible","badgeCloseIcon","dir","label","multiple","open","readOnly","required","selectCaretDoubleIcon","selectSearchIcon","showLabel","tip"];
exports.render.types = {"name":"string","predefinedValue":"list<string>","value":"list<string>","visible":"bool","badgeCloseIcon":"html","dir":"string","label":"string","multiple":"bool","open":"bool","readOnly":"bool","required":"bool","selectCaretDoubleIcon":"html","selectSearchIcon":"html","showLabel":"bool","tip":"string"};
exports.badge_item.params = ["value","badgeCloseIcon","readOnly"];
exports.badge_item.types = {"value":"list<string>","badgeCloseIcon":"html","readOnly":"bool"};
exports.select_label.params = ["name","label","readOnly","required","tip"];
exports.select_label.types = {"name":"string","label":"string","readOnly":"bool","required":"bool","tip":"string"};
exports.hidden_select.params = ["displayValue","name","dir","multiple"];
exports.hidden_select.types = {"displayValue":"list<string>","name":"string","dir":"string","multiple":"bool"};
exports.select_hidden_options.params = ["values","dir"];
exports.select_hidden_options.types = {"values":"list<string>","dir":"string"};
exports.select_options.params = ["value","multiple"];
exports.select_options.types = {"value":"list<string>","multiple":"bool"};
exports.plot_dropdown_rows.params = ["value","fixed","multiple"];
exports.plot_dropdown_rows.types = {"value":"list<string>","fixed":"bool","multiple":"bool"};
templates = exports;
return exports;

});

class DDMSelect extends Component {}
Soy.register(DDMSelect, templates);
export { DDMSelect, templates };
export default templates;
/* jshint ignore:end */
