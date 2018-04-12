/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from document-library.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMDocumentLibrary.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMDocumentLibrary.incrementaldom');

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
function __deltemplate_s2_118789fc(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_118789fc = __deltemplate_s2_118789fc;
if (goog.DEBUG) {
  __deltemplate_s2_118789fc.soyTemplateName = 'DDMDocumentLibrary.__deltemplate_s2_118789fc';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'document_library', 0, __deltemplate_s2_118789fc);


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  strings: {select: (!goog.soy.data.SanitizedContent|string)},
 *  visible: boolean,
 *  clearButtonVisible: (boolean|null|undefined),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fileEntryTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fileEntryURL: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  lexiconIconsPath: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  value: (?)
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
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {boolean} */
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {{select: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{select: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {boolean} */
  var visible = soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  /** @type {boolean|null|undefined} */
  var clearButtonVisible = soy.asserts.assertType(opt_data.clearButtonVisible == null || (goog.isBoolean(opt_data.clearButtonVisible) || opt_data.clearButtonVisible === 1 || opt_data.clearButtonVisible === 0), 'clearButtonVisible', opt_data.clearButtonVisible, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var fileEntryTitle = soy.asserts.assertType(opt_data.fileEntryTitle == null || (goog.isString(opt_data.fileEntryTitle) || opt_data.fileEntryTitle instanceof goog.soy.data.SanitizedContent), 'fileEntryTitle', opt_data.fileEntryTitle, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var fileEntryURL = soy.asserts.assertType(opt_data.fileEntryURL == null || (goog.isString(opt_data.fileEntryURL) || opt_data.fileEntryURL instanceof goog.soy.data.SanitizedContent), 'fileEntryURL', opt_data.fileEntryURL, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var label = soy.asserts.assertType(opt_data.label == null || (goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent), 'label', opt_data.label, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var lexiconIconsPath = soy.asserts.assertType(opt_data.lexiconIconsPath == null || (goog.isString(opt_data.lexiconIconsPath) || opt_data.lexiconIconsPath instanceof goog.soy.data.SanitizedContent), 'lexiconIconsPath', opt_data.lexiconIconsPath, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var value = opt_data.value;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group' + (visible ? '' : ' hide'));
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    if (showLabel) {
      incrementalDom.elementOpen('label');
        soyIdom.print(label);
        incrementalDom.text(' ');
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
      if (tip) {
        incrementalDom.elementOpenStart('span');
            incrementalDom.attr('class', 'form-text');
        incrementalDom.elementOpenEnd();
          soyIdom.print(tip);
        incrementalDom.elementClose('span');
      }
    }
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'form-builder-document-library-field');
    incrementalDom.elementOpenEnd();
      if (fileEntryURL) {
        $card_item(opt_data, null, opt_ijData);
      } else {
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'input-group');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('input');
              incrementalDom.attr('class', 'field form-control');
              if (dir) {
                incrementalDom.attr('dir', dir);
              }
              if (readOnly) {
                incrementalDom.attr('disabled', '');
              }
              incrementalDom.attr('id', 'inputFile');
              incrementalDom.attr('type', 'text');
              incrementalDom.attr('value', fileEntryTitle ? fileEntryTitle : '');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('input');
          $button_group(opt_data, null, opt_ijData);
        incrementalDom.elementClose('div');
      }
      incrementalDom.elementOpenStart('input');
          incrementalDom.attr('class', 'field form-control');
          incrementalDom.attr('name', name);
          incrementalDom.attr('type', 'hidden');
          incrementalDom.attr('value', value ? value : '');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('input');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showLabel: boolean,
 *  strings: {select: (!goog.soy.data.SanitizedContent|string)},
 *  visible: boolean,
 *  clearButtonVisible: (boolean|null|undefined),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fileEntryTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fileEntryURL: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  label: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  lexiconIconsPath: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  value: (?)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMDocumentLibrary.render';
}


/**
 * @param {{
 *  readOnly: boolean,
 *  strings: {select: (!goog.soy.data.SanitizedContent|string)},
 *  clearButtonVisible: (boolean|null|undefined),
 *  lexiconIconsPath: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $button_group(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {{select: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{select: (!goog.soy.data.SanitizedContent|string)}');
  /** @type {boolean|null|undefined} */
  var clearButtonVisible = soy.asserts.assertType(opt_data.clearButtonVisible == null || (goog.isBoolean(opt_data.clearButtonVisible) || opt_data.clearButtonVisible === 1 || opt_data.clearButtonVisible === 0), 'clearButtonVisible', opt_data.clearButtonVisible, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var lexiconIconsPath = soy.asserts.assertType(opt_data.lexiconIconsPath == null || (goog.isString(opt_data.lexiconIconsPath) || opt_data.lexiconIconsPath instanceof goog.soy.data.SanitizedContent), 'lexiconIconsPath', opt_data.lexiconIconsPath, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('span');
      incrementalDom.attr('class', 'input-group-btn');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('button');
        incrementalDom.attr('class', 'btn btn-secondary select-button');
        if (readOnly) {
          incrementalDom.attr('disabled', '');
        }
        incrementalDom.attr('type', 'button');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('span');
          incrementalDom.attr('class', 'lfr-btn-label');
      incrementalDom.elementOpenEnd();
        soyIdom.print(strings.select);
      incrementalDom.elementClose('span');
    incrementalDom.elementClose('button');
    if (clearButtonVisible) {
      incrementalDom.elementOpenStart('button');
          incrementalDom.attr('class', 'btn btn-secondary clear-button');
          incrementalDom.attr('type', 'button');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('svg');
            incrementalDom.attr('class', 'lexicon-icon');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('use');
              incrementalDom.attr('xlink:href', lexiconIconsPath + 'times');
          incrementalDom.elementOpenEnd();
          incrementalDom.elementClose('use');
        incrementalDom.elementClose('svg');
      incrementalDom.elementClose('button');
    }
  incrementalDom.elementClose('span');
}
exports.button_group = $button_group;
/**
 * @typedef {{
 *  readOnly: boolean,
 *  strings: {select: (!goog.soy.data.SanitizedContent|string)},
 *  clearButtonVisible: (boolean|null|undefined),
 *  lexiconIconsPath: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$button_group.Params;
if (goog.DEBUG) {
  $button_group.soyTemplateName = 'DDMDocumentLibrary.button_group';
}


/**
 * @param {{
 *  fileEntryTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fileEntryURL: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  lexiconIconsPath: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $card_item(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var fileEntryTitle = soy.asserts.assertType(opt_data.fileEntryTitle == null || (goog.isString(opt_data.fileEntryTitle) || opt_data.fileEntryTitle instanceof goog.soy.data.SanitizedContent), 'fileEntryTitle', opt_data.fileEntryTitle, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var fileEntryURL = soy.asserts.assertType(opt_data.fileEntryURL == null || (goog.isString(opt_data.fileEntryURL) || opt_data.fileEntryURL instanceof goog.soy.data.SanitizedContent), 'fileEntryURL', opt_data.fileEntryURL, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var lexiconIconsPath = soy.asserts.assertType(opt_data.lexiconIconsPath == null || (goog.isString(opt_data.lexiconIconsPath) || opt_data.lexiconIconsPath instanceof goog.soy.data.SanitizedContent), 'lexiconIconsPath', opt_data.lexiconIconsPath, '!goog.soy.data.SanitizedContent|null|string|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'card card-horizontal');
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'card-row card-row-padded');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'card-col-content card-col-gutters');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('h4');
            incrementalDom.attr('class', 'truncate-text');
            incrementalDom.attr('title', fileEntryTitle);
        incrementalDom.elementOpenEnd();
          soyIdom.print(fileEntryTitle);
        incrementalDom.elementClose('h4');
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'card-col-field');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('a');
            incrementalDom.attr('href', fileEntryURL);
            incrementalDom.attr('download', fileEntryTitle);
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('svg');
              incrementalDom.attr('class', 'lexicon-icon');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('use');
                incrementalDom.attr('xlink:href', lexiconIconsPath + 'download');
            incrementalDom.elementOpenEnd();
            incrementalDom.elementClose('use');
          incrementalDom.elementClose('svg');
        incrementalDom.elementClose('a');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.card_item = $card_item;
/**
 * @typedef {{
 *  fileEntryTitle: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  fileEntryURL: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  lexiconIconsPath: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$card_item.Params;
if (goog.DEBUG) {
  $card_item.soyTemplateName = 'DDMDocumentLibrary.card_item';
}

exports.render.params = ["name","pathThemeImages","readOnly","showLabel","strings","visible","clearButtonVisible","dir","fileEntryTitle","fileEntryURL","label","lexiconIconsPath","required","tip","value"];
exports.render.types = {"name":"string","pathThemeImages":"string","readOnly":"bool","showLabel":"bool","strings":"[select: string]","visible":"bool","clearButtonVisible":"bool","dir":"string","fileEntryTitle":"string","fileEntryURL":"string","label":"string","lexiconIconsPath":"string","required":"bool","tip":"string","value":"?"};
exports.button_group.params = ["readOnly","strings","clearButtonVisible","lexiconIconsPath"];
exports.button_group.types = {"readOnly":"bool","strings":"[select: string]","clearButtonVisible":"bool","lexiconIconsPath":"string"};
exports.card_item.params = ["fileEntryTitle","fileEntryURL","lexiconIconsPath"];
exports.card_item.types = {"fileEntryTitle":"string","fileEntryURL":"string","lexiconIconsPath":"string"};
templates = exports;
return exports;

});

class DDMDocumentLibrary extends Component {}
Soy.register(DDMDocumentLibrary, templates);
export { DDMDocumentLibrary, templates };
export default templates;
/* jshint ignore:end */
