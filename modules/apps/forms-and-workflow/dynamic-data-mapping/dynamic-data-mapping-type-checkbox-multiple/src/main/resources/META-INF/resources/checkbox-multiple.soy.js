/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from checkbox-multiple.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMCheckboxMultiple.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMCheckboxMultiple.incrementaldom');

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
function __deltemplate_s2_2ea1799c(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_2ea1799c = __deltemplate_s2_2ea1799c;
if (goog.DEBUG) {
  __deltemplate_s2_2ea1799c.soyTemplateName = 'DDMCheckboxMultiple.__deltemplate_s2_2ea1799c';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'checkbox_multiple', 0, __deltemplate_s2_2ea1799c);


/**
 * @param {{
 *  inline: boolean,
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showAsSwitcher: boolean,
 *  showLabel: boolean,
 *  value: (?),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  predefinedValue: (?),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {boolean} */
  var inline = soy.asserts.assertType(goog.isBoolean(opt_data.inline) || opt_data.inline === 1 || opt_data.inline === 0, 'inline', opt_data.inline, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var label = soy.asserts.assertType(goog.isString(opt_data.label) || opt_data.label instanceof goog.soy.data.SanitizedContent, 'label', opt_data.label, '!goog.soy.data.SanitizedContent|string');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var name = soy.asserts.assertType(goog.isString(opt_data.name) || opt_data.name instanceof goog.soy.data.SanitizedContent, 'name', opt_data.name, '!goog.soy.data.SanitizedContent|string');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>} */
  var options = soy.asserts.assertType(goog.isArray(opt_data.options), 'options', opt_data.options, '!Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {boolean} */
  var readOnly = soy.asserts.assertType(goog.isBoolean(opt_data.readOnly) || opt_data.readOnly === 1 || opt_data.readOnly === 0, 'readOnly', opt_data.readOnly, 'boolean');
  /** @type {boolean} */
  var showAsSwitcher = soy.asserts.assertType(goog.isBoolean(opt_data.showAsSwitcher) || opt_data.showAsSwitcher === 1 || opt_data.showAsSwitcher === 0, 'showAsSwitcher', opt_data.showAsSwitcher, 'boolean');
  /** @type {boolean} */
  var showLabel = soy.asserts.assertType(goog.isBoolean(opt_data.showLabel) || opt_data.showLabel === 1 || opt_data.showLabel === 0, 'showLabel', opt_data.showLabel, 'boolean');
  /** @type {?} */
  var value = opt_data.value;
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var dir = soy.asserts.assertType(opt_data.dir == null || (goog.isString(opt_data.dir) || opt_data.dir instanceof goog.soy.data.SanitizedContent), 'dir', opt_data.dir, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {?} */
  var predefinedValue = opt_data.predefinedValue;
  /** @type {boolean|null|undefined} */
  var required = soy.asserts.assertType(opt_data.required == null || (goog.isBoolean(opt_data.required) || opt_data.required === 1 || opt_data.required === 0), 'required', opt_data.required, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var tip = soy.asserts.assertType(opt_data.tip == null || (goog.isString(opt_data.tip) || opt_data.tip instanceof goog.soy.data.SanitizedContent), 'tip', opt_data.tip, '!goog.soy.data.SanitizedContent|null|string|undefined');
  var displayValue__soy21 = value ? value : predefinedValue;
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group liferay-ddm-form-field-checkbox-multiple');
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpen('div');
      incrementalDom.elementOpenStart('label');
          incrementalDom.attr('for', name);
      incrementalDom.elementOpenEnd();
        if (showLabel) {
          soyIdom.print(label);
          incrementalDom.text(' ');
        }
        if (required && (options.length) > 1) {
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
    incrementalDom.elementClose('div');
    var option137List = options;
    var option137ListLen = option137List.length;
    for (var option137Index = 0; option137Index < option137ListLen; option137Index++) {
        var option137Data = option137List[option137Index];
        var checked__soy50 = function() {
          if (displayValue__soy21) {
            var currentValue60List = displayValue__soy21;
            var currentValue60ListLen = currentValue60List.length;
            for (var currentValue60Index = 0; currentValue60Index < currentValue60ListLen; currentValue60Index++) {
                var currentValue60Data = currentValue60List[currentValue60Index];
                if (currentValue60Data == option137Data.value) {
                  incrementalDom.attr('checked', '');
                }
              }
          }
        };
        if (showAsSwitcher) {
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'lfr-ddm-form-field-checkbox-switch ' + (inline ? 'lfr-ddm-form-field-checkbox-switch-inline' : ''));
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('label');
                incrementalDom.attr('class', 'toggle-switch toggle-switch-option-' + option137Data.value);
                incrementalDom.attr('for', name + '_' + option137Data.value);
            incrementalDom.elementOpenEnd();
              incrementalDom.elementOpenStart('input');
                  checked__soy50();
                  if (readOnly) {
                    incrementalDom.attr('disabled', '');
                  }
                  incrementalDom.attr('class', 'toggle-switch-check');
                  incrementalDom.attr('id', name + '_' + option137Data.value);
                  incrementalDom.attr('name', name);
                  incrementalDom.attr('type', 'checkbox');
                  incrementalDom.attr('value', option137Data.value);
              incrementalDom.elementOpenEnd();
              incrementalDom.elementClose('input');
              incrementalDom.elementOpenStart('span');
                  incrementalDom.attr('aria-hidden', 'true');
                  incrementalDom.attr('class', 'toggle-switch-bar');
              incrementalDom.elementOpenEnd();
                incrementalDom.elementOpenStart('span');
                    incrementalDom.attr('class', 'toggle-switch-handle');
                incrementalDom.elementOpenEnd();
                incrementalDom.elementClose('span');
              incrementalDom.elementClose('span');
              incrementalDom.elementOpenStart('span');
                  incrementalDom.attr('class', 'toggle-switch-text toggle-switch-text-right');
              incrementalDom.elementOpenEnd();
                soyIdom.print(option137Data.label);
                incrementalDom.text(' ');
                if (required && (options.length) == 1) {
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
              incrementalDom.elementClose('span');
            incrementalDom.elementClose('label');
          incrementalDom.elementClose('div');
        } else {
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'form-check custom-checkbox ' + (inline ? 'form-check-inline' : 'custom-control'));
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpenStart('label');
                incrementalDom.attr('class', 'form-check-label-option-' + option137Data.value);
                incrementalDom.attr('for', name + '_' + option137Data.value);
            incrementalDom.elementOpenEnd();
              incrementalDom.elementOpenStart('input');
                  checked__soy50();
                  if (dir) {
                    incrementalDom.attr('dir', dir);
                  }
                  if (readOnly) {
                    incrementalDom.attr('disabled', '');
                  }
                  incrementalDom.attr('class', 'custom-control-input');
                  incrementalDom.attr('id', name + '_' + option137Data.value);
                  incrementalDom.attr('name', name);
                  incrementalDom.attr('type', 'checkbox');
                  incrementalDom.attr('value', option137Data.value);
              incrementalDom.elementOpenEnd();
              incrementalDom.elementClose('input');
              incrementalDom.elementOpenStart('span');
                  incrementalDom.attr('class', 'form-check-description custom-control-label');
              incrementalDom.elementOpenEnd();
                incrementalDom.elementOpenStart('span');
                    incrementalDom.attr('class', 'custom-control-label-text');
                incrementalDom.elementOpenEnd();
                  soyIdom.print(option137Data.label);
                  incrementalDom.text(' ');
                  if (required && (options.length) == 1) {
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
                incrementalDom.elementClose('span');
              incrementalDom.elementClose('span');
            incrementalDom.elementClose('label');
          incrementalDom.elementClose('div');
        }
      }
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  inline: boolean,
 *  label: (!goog.soy.data.SanitizedContent|string),
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  options: !Array<{label: (!goog.soy.data.SanitizedContent|string), value: (?)}>,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  readOnly: boolean,
 *  showAsSwitcher: boolean,
 *  showLabel: boolean,
 *  value: (?),
 *  dir: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  predefinedValue: (?),
 *  required: (boolean|null|undefined),
 *  tip: (!goog.soy.data.SanitizedContent|null|string|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMCheckboxMultiple.render';
}

exports.render.params = ["inline","label","name","options","pathThemeImages","readOnly","showAsSwitcher","showLabel","value","dir","predefinedValue","required","tip"];
exports.render.types = {"inline":"bool","label":"string","name":"string","options":"list<[label: string, value: ?]>","pathThemeImages":"string","readOnly":"bool","showAsSwitcher":"bool","showLabel":"bool","value":"?","dir":"string","predefinedValue":"?","required":"bool","tip":"string"};
templates = exports;
return exports;

});

class DDMCheckboxMultiple extends Component {}
Soy.register(DDMCheckboxMultiple, templates);
export { DDMCheckboxMultiple, templates };
export default templates;
/* jshint ignore:end */
