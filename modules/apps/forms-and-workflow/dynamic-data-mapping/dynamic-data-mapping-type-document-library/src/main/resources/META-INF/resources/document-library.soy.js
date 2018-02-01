/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from document-library.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMDocumentLibrary.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMDocumentLibrary.incrementaldom');

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
function __deltemplate_s2_118789fc(opt_data, opt_ignored, opt_ijData) {
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
 *    clearButtonVisible: (boolean|null|undefined),
 *    dir: (null|string|undefined),
 *    fileEntryURL: (null|string|undefined),
 *    fileEntryTitle: (null|string|undefined),
 *    label: (null|string|undefined),
 *    lexiconIconsPath: (null|string|undefined),
 *    name: string,
 *    readOnly: boolean,
 *    required: (boolean|null|undefined),
 *    showLabel: boolean,
 *    strings: {select: string},
 *    tip: (null|string|undefined),
 *    value: (?),
 *    visible: boolean
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(opt_data.clearButtonVisible == null || goog.isBoolean(opt_data.clearButtonVisible) || opt_data.clearButtonVisible === 1 || opt_data.clearButtonVisible === 0, 'clearButtonVisible', opt_data.clearButtonVisible, 'boolean|null|undefined');
  var clearButtonVisible = /** @type {boolean|null|undefined} */ (opt_data.clearButtonVisible);
  soy.asserts.assertType(opt_data.dir == null || (opt_data.dir instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.dir), 'dir', opt_data.dir, 'null|string|undefined');
  var dir = /** @type {null|string|undefined} */ (opt_data.dir);
  soy.asserts.assertType(opt_data.fileEntryURL == null || (opt_data.fileEntryURL instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.fileEntryURL), 'fileEntryURL', opt_data.fileEntryURL, 'null|string|undefined');
  var fileEntryURL = /** @type {null|string|undefined} */ (opt_data.fileEntryURL);
  soy.asserts.assertType(opt_data.fileEntryTitle == null || (opt_data.fileEntryTitle instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.fileEntryTitle), 'fileEntryTitle', opt_data.fileEntryTitle, 'null|string|undefined');
  var fileEntryTitle = /** @type {null|string|undefined} */ (opt_data.fileEntryTitle);
  soy.asserts.assertType(opt_data.label == null || (opt_data.label instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.label), 'label', opt_data.label, 'null|string|undefined');
  var label = /** @type {null|string|undefined} */ (opt_data.label);
  soy.asserts.assertType(opt_data.lexiconIconsPath == null || (opt_data.lexiconIconsPath instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.lexiconIconsPath), 'lexiconIconsPath', opt_data.lexiconIconsPath, 'null|string|undefined');
  var lexiconIconsPath = /** @type {null|string|undefined} */ (opt_data.lexiconIconsPath);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  soy.asserts.assertType(opt_data.required == null || goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0, 'required', opt_data.required, 'boolean|null|undefined');
  var required = /** @type {boolean|null|undefined} */ (opt_data.required);
  soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  var showLabel = /** @type {boolean} */ (!!opt_data.showLabel);
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [select: string].");
  soy.asserts.assertType(opt_data.tip == null || (opt_data.tip instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.tip), 'tip', opt_data.tip, 'null|string|undefined');
  var tip = /** @type {null|string|undefined} */ (opt_data.tip);
  soy.asserts.assertType(goog.isBoolean(opt_data.visible) || opt_data.visible === 1 || opt_data.visible === 0, 'visible', opt_data.visible, 'boolean');
  var visible = /** @type {boolean} */ (!!opt_data.visible);
  ie_open('div', null, null,
      'class', 'form-group' + (visible ? '' : ' hide'),
      'data-fieldname', name);
    if (showLabel) {
      ie_open('label', null, null,
          'class', 'control-label');
        var dyn0 = label;
        if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
        if (required) {
          ie_void('span', null, null,
              'class', 'icon-asterisk text-warning');
        }
      ie_close('label');
      ie_open('p', null, null,
          'class', 'liferay-ddm-form-field-tip');
        var dyn1 = tip ? tip : '';
        if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
      ie_close('p');
    }
    ie_open('div', null, null,
        'class', 'form-builder-document-library-field');
      if (fileEntryURL) {
        $card_item(opt_data, null, opt_ijData);
      } else {
        ie_open('div', null, null,
            'class', 'input-group');
          ie_open_start('input');
              iattr('class', 'field form-control');
              if (dir) {
                iattr('dir', dir);
              }
              iattr('id', 'inputFile');
              iattr('type', 'text');
              iattr('value', fileEntryTitle ? fileEntryTitle : '');
              iattr('readonly', '');
          ie_open_end();
          ie_close('input');
          $button_group(opt_data, null, opt_ijData);
        ie_close('div');
      }
      ie_open('input', null, null,
          'class', 'field form-control',
          'name', name,
          'type', 'hidden',
          'value', opt_data.value ? opt_data.value : '');
      ie_close('input');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMDocumentLibrary.render';
}


/**
 * @param {{
 *    clearButtonVisible: (boolean|null|undefined),
 *    lexiconIconsPath: (null|string|undefined),
 *    readOnly: boolean,
 *    strings: {select: string}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $button_group(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(opt_data.clearButtonVisible == null || goog.isBoolean(opt_data.clearButtonVisible) || opt_data.clearButtonVisible === 1 || opt_data.clearButtonVisible === 0, 'clearButtonVisible', opt_data.clearButtonVisible, 'boolean|null|undefined');
  var clearButtonVisible = /** @type {boolean|null|undefined} */ (opt_data.clearButtonVisible);
  soy.asserts.assertType(opt_data.lexiconIconsPath == null || (opt_data.lexiconIconsPath instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.lexiconIconsPath), 'lexiconIconsPath', opt_data.lexiconIconsPath, 'null|string|undefined');
  var lexiconIconsPath = /** @type {null|string|undefined} */ (opt_data.lexiconIconsPath);
  soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  var readOnly = /** @type {boolean} */ (!!opt_data.readOnly);
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [select: string].");
  ie_open('div', null, null,
      'class', 'input-group-btn');
    ie_open_start('button');
        iattr('class', 'btn btn-default select-button');
        if (readOnly) {
          iattr('disabled', '');
        }
        iattr('type', 'button');
    ie_open_end();
      ie_open('span', null, null,
          'class', 'lfr-btn-label');
        var dyn2 = strings.select;
        if (typeof dyn2 == 'function') dyn2(); else if (dyn2 != null) itext(dyn2);
      ie_close('span');
    ie_close('button');
    ie_open_start('button');
        iattr('class', 'btn btn-default clear-button ' + ((! clearButtonVisible) ? 'hide' : ''));
        if (readOnly) {
          iattr('disabled', '');
        }
        if (! clearButtonVisible) {
          iattr('hidden', 'hidden');
          iattr('style', 'display: none;');
          iattr('type', 'button');
        }
    ie_open_end();
      ie_open('svg', null, null,
          'class', 'lexicon-icon');
        ie_void('use', null, null,
            'xlink:href', lexiconIconsPath + 'times');
      ie_close('svg');
    ie_close('button');
  ie_close('div');
}
exports.button_group = $button_group;
if (goog.DEBUG) {
  $button_group.soyTemplateName = 'DDMDocumentLibrary.button_group';
}


/**
 * @param {{
 *    fileEntryURL: (null|string|undefined),
 *    fileEntryTitle: (null|string|undefined),
 *    lexiconIconsPath: (null|string|undefined)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $card_item(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  soy.asserts.assertType(opt_data.fileEntryURL == null || (opt_data.fileEntryURL instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.fileEntryURL), 'fileEntryURL', opt_data.fileEntryURL, 'null|string|undefined');
  var fileEntryURL = /** @type {null|string|undefined} */ (opt_data.fileEntryURL);
  soy.asserts.assertType(opt_data.fileEntryTitle == null || (opt_data.fileEntryTitle instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.fileEntryTitle), 'fileEntryTitle', opt_data.fileEntryTitle, 'null|string|undefined');
  var fileEntryTitle = /** @type {null|string|undefined} */ (opt_data.fileEntryTitle);
  soy.asserts.assertType(opt_data.lexiconIconsPath == null || (opt_data.lexiconIconsPath instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.lexiconIconsPath), 'lexiconIconsPath', opt_data.lexiconIconsPath, 'null|string|undefined');
  var lexiconIconsPath = /** @type {null|string|undefined} */ (opt_data.lexiconIconsPath);
  ie_open('div', null, null,
      'class', 'card card-horizontal');
    ie_open('div', null, null,
        'class', 'card-row card-row-padded');
      ie_open('div', null, null,
          'class', 'card-col-content card-col-gutters');
        ie_open('h4', null, null,
            'class', 'truncate-text',
            'title', fileEntryTitle);
          var dyn3 = fileEntryTitle;
          if (typeof dyn3 == 'function') dyn3(); else if (dyn3 != null) itext(dyn3);
        ie_close('h4');
      ie_close('div');
      ie_open('div', null, null,
          'class', 'card-col-field');
        ie_open('a', null, null,
            'href', fileEntryURL,
            'download', fileEntryTitle);
          ie_open('svg', null, null,
              'class', 'lexicon-icon');
            ie_void('use', null, null,
                'xlink:href', lexiconIconsPath + 'download');
          ie_close('svg');
        ie_close('a');
      ie_close('div');
    ie_close('div');
  ie_close('div');
}
exports.card_item = $card_item;
if (goog.DEBUG) {
  $card_item.soyTemplateName = 'DDMDocumentLibrary.card_item';
}

exports.render.params = ["clearButtonVisible","dir","fileEntryURL","fileEntryTitle","label","lexiconIconsPath","name","readOnly","required","showLabel","tip","value","visible"];
exports.render.types = {"clearButtonVisible":"bool","dir":"string","fileEntryURL":"string","fileEntryTitle":"string","label":"string","lexiconIconsPath":"string","name":"string","readOnly":"bool","required":"bool","showLabel":"bool","tip":"string","value":"?","visible":"bool"};
exports.button_group.params = ["clearButtonVisible","lexiconIconsPath","readOnly"];
exports.button_group.types = {"clearButtonVisible":"bool","lexiconIconsPath":"string","readOnly":"bool"};
exports.card_item.params = ["fileEntryURL","fileEntryTitle","lexiconIconsPath"];
exports.card_item.types = {"fileEntryURL":"string","fileEntryTitle":"string","lexiconIconsPath":"string"};
templates = exports;
return exports;

});

class DDMDocumentLibrary extends Component {}
Soy.register(DDMDocumentLibrary, templates);
export { DDMDocumentLibrary, templates };
export default templates;
/* jshint ignore:end */
