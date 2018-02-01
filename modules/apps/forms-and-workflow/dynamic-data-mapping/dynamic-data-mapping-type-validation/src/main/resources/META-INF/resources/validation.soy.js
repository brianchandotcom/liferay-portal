/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from validation.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMValidation.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMValidation.incrementaldom');

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
function __deltemplate_s2_49e0bcef(opt_data, opt_ignored, opt_ijData) {
  opt_data = opt_data || {};
  $render(opt_data, null, opt_ijData);
}
exports.__deltemplate_s2_49e0bcef = __deltemplate_s2_49e0bcef;
if (goog.DEBUG) {
  __deltemplate_s2_49e0bcef.soyTemplateName = 'DDMValidation.__deltemplate_s2_49e0bcef';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field.idom'), 'validation', 0, __deltemplate_s2_49e0bcef);


/**
 * @param {{
 *    option: {label: string, status: string, value: string}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $validationOption(opt_data, opt_ignored, opt_ijData) {
  var option = goog.asserts.assertObject(opt_data.option, "expected parameter 'option' of type [label: string, status: string, value: string].");
  ie_open_start('option');
      if (option.status == 'selected') {
        iattr('selected', '');
      }
      iattr('value', option.value);
  ie_open_end();
    var dyn0 = option.label;
    if (typeof dyn0 == 'function') dyn0(); else if (dyn0 != null) itext(dyn0);
  ie_close('option');
}
exports.validationOption = $validationOption;
if (goog.DEBUG) {
  $validationOption.soyTemplateName = 'DDMValidation.validationOption';
}


/**
 * @param {{
 *    enableValidationValue: (boolean|null|undefined),
 *    errorMessagePlaceholder: (null|string|undefined),
 *    errorMessageValue: (null|string|undefined),
 *    name: string,
 *    parameterMessagePlaceholder: (null|string|undefined),
 *    parameterValue: (null|string|undefined),
 *    validationMessage: (null|string|undefined),
 *    validationsOptions: (?Array<{label: string, status: string, value: string}>|undefined),
 *    value: (?)
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(opt_data.enableValidationValue == null || goog.isBoolean(opt_data.enableValidationValue) || opt_data.enableValidationValue === 1 || opt_data.enableValidationValue === 0, 'enableValidationValue', opt_data.enableValidationValue, 'boolean|null|undefined');
  var enableValidationValue = /** @type {boolean|null|undefined} */ (opt_data.enableValidationValue);
  soy.asserts.assertType(opt_data.errorMessagePlaceholder == null || (opt_data.errorMessagePlaceholder instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.errorMessagePlaceholder), 'errorMessagePlaceholder', opt_data.errorMessagePlaceholder, 'null|string|undefined');
  var errorMessagePlaceholder = /** @type {null|string|undefined} */ (opt_data.errorMessagePlaceholder);
  soy.asserts.assertType(opt_data.errorMessageValue == null || (opt_data.errorMessageValue instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.errorMessageValue), 'errorMessageValue', opt_data.errorMessageValue, 'null|string|undefined');
  var errorMessageValue = /** @type {null|string|undefined} */ (opt_data.errorMessageValue);
  soy.asserts.assertType(goog.isString(opt_data.name) || (opt_data.name instanceof goog.soy.data.SanitizedContent), 'name', opt_data.name, 'string|goog.soy.data.SanitizedContent');
  var name = /** @type {string|goog.soy.data.SanitizedContent} */ (opt_data.name);
  soy.asserts.assertType(opt_data.parameterMessagePlaceholder == null || (opt_data.parameterMessagePlaceholder instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.parameterMessagePlaceholder), 'parameterMessagePlaceholder', opt_data.parameterMessagePlaceholder, 'null|string|undefined');
  var parameterMessagePlaceholder = /** @type {null|string|undefined} */ (opt_data.parameterMessagePlaceholder);
  soy.asserts.assertType(opt_data.parameterValue == null || (opt_data.parameterValue instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.parameterValue), 'parameterValue', opt_data.parameterValue, 'null|string|undefined');
  var parameterValue = /** @type {null|string|undefined} */ (opt_data.parameterValue);
  soy.asserts.assertType(opt_data.validationMessage == null || (opt_data.validationMessage instanceof goog.soy.data.SanitizedContent) || goog.isString(opt_data.validationMessage), 'validationMessage', opt_data.validationMessage, 'null|string|undefined');
  var validationMessage = /** @type {null|string|undefined} */ (opt_data.validationMessage);
  soy.asserts.assertType(opt_data.validationsOptions == null || goog.isArray(opt_data.validationsOptions), 'validationsOptions', opt_data.validationsOptions, '?Array<{label: string, status: string, value: string}>|undefined');
  var validationsOptions = /** @type {?Array<{label: string, status: string, value: string}>|undefined} */ (opt_data.validationsOptions);
  ie_open('div', null, null,
      'class', 'form-group lfr-ddm-form-field-validation',
      'data-fieldname', name);
    ie_open('div', null, null,
        'class', 'form-group');
      ie_open('label', null, null,
          'class', 'control-label',
          'for', name + 'EnableValidation');
        ie_open_start('input');
            iattr('class', 'enable-validation toggle-switch');
            if (enableValidationValue) {
              iattr('checked', '');
            }
            iattr('id', name + 'EnableValidation');
            iattr('type', 'checkbox');
            iattr('value', 'true');
        ie_open_end();
        ie_close('input');
        ie_open('span', null, null,
            'aria-hidden', 'true',
            'class', 'toggle-switch-bar');
          ie_void('span', null, null,
              'class', 'toggle-switch-handle');
        ie_close('span');
        ie_open('span', null, null,
            'class', 'toggle-switch-text toggle-switch-text-right');
          var dyn1 = validationMessage;
          if (typeof dyn1 == 'function') dyn1(); else if (dyn1 != null) itext(dyn1);
        ie_close('span');
      ie_close('label');
    ie_close('div');
    ie_open('div', null, null,
        'class', (enableValidationValue ? '' : 'hide') + ' row');
      ie_open('div', null, null,
          'class', 'col-md-6');
        ie_open('select', null, null,
            'class', 'form-control validations-select');
          if (validationsOptions) {
            var optionList34 = validationsOptions;
            var optionListLen34 = optionList34.length;
            for (var optionIndex34 = 0; optionIndex34 < optionListLen34; optionIndex34++) {
              var optionData34 = optionList34[optionIndex34];
              $validationOption(soy.$$assignDefaults({option: optionData34}, opt_data), null, opt_ijData);
            }
          }
        ie_close('select');
      ie_close('div');
      ie_open('div', null, null,
          'class', 'col-md-6');
        ie_open('input', null, null,
            'class', 'field form-control ' + (parameterMessagePlaceholder ? '' : ' hide') + ' parameter-input',
            'placeholder', parameterMessagePlaceholder,
            'type', 'text',
            'value', parameterValue);
        ie_close('input');
      ie_close('div');
    ie_close('div');
    ie_open('div', null, null,
        'class', (enableValidationValue ? '' : 'hide') + ' row');
      ie_open('div', null, null,
          'class', 'col-md-12');
        ie_open('input', null, null,
            'class', 'field form-control message-input',
            'placeholder', errorMessagePlaceholder,
            'type', 'text',
            'value', errorMessageValue);
        ie_close('input');
      ie_close('div');
    ie_close('div');
    ie_open('input', null, null,
        'name', name,
        'type', 'hidden',
        'value', opt_data.value);
    ie_close('input');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMValidation.render';
}

exports.validationOption.params = [];
exports.validationOption.types = {};
exports.render.params = ["enableValidationValue","errorMessagePlaceholder","errorMessageValue","name","parameterMessagePlaceholder","parameterValue","validationMessage","value"];
exports.render.types = {"enableValidationValue":"bool","errorMessagePlaceholder":"string","errorMessageValue":"string","name":"string","parameterMessagePlaceholder":"string","parameterValue":"string","validationMessage":"string","value":"?"};
templates = exports;
return exports;

});

class DDMValidation extends Component {}
Soy.register(DDMValidation, templates);
export { DDMValidation, templates };
export default templates;
/* jshint ignore:end */
