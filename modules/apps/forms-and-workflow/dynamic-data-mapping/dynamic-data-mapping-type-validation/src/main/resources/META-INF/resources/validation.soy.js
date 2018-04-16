/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from validation.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMValidation.
 * @hassoydeltemplate {ddm.field.idom}
 * @public
 */

goog.module('DDMValidation.incrementaldom');

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
function __deltemplate_s2_49e0bcef(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
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
 *  option: {label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $validationOption(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {{label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}} */
  var option = soy.asserts.assertType(goog.isObject(opt_data.option), 'option', opt_data.option, '{label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}');
  incrementalDom.elementOpenStart('option');
      if (option.status == 'selected') {
        incrementalDom.attr('selected', '');
      }
      incrementalDom.attr('value', option.value);
  incrementalDom.elementOpenEnd();
    soyIdom.print(option.label);
  incrementalDom.elementClose('option');
}
exports.validationOption = $validationOption;
/**
 * @typedef {{
 *  option: {label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}
 * }}
 */
$validationOption.Params;
if (goog.DEBUG) {
  $validationOption.soyTemplateName = 'DDMValidation.validationOption';
}


/**
 * @param {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  value: (?),
 *  enableValidationValue: (boolean|null|undefined),
 *  errorMessagePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  errorMessageValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  parameterMessagePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  parameterValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  validationMessage: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  validationsOptions: (!Array<{label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined)
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
  /** @type {?} */
  var value = opt_data.value;
  /** @type {boolean|null|undefined} */
  var enableValidationValue = soy.asserts.assertType(opt_data.enableValidationValue == null || (goog.isBoolean(opt_data.enableValidationValue) || opt_data.enableValidationValue === 1 || opt_data.enableValidationValue === 0), 'enableValidationValue', opt_data.enableValidationValue, 'boolean|null|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var errorMessagePlaceholder = soy.asserts.assertType(opt_data.errorMessagePlaceholder == null || (goog.isString(opt_data.errorMessagePlaceholder) || opt_data.errorMessagePlaceholder instanceof goog.soy.data.SanitizedContent), 'errorMessagePlaceholder', opt_data.errorMessagePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var errorMessageValue = soy.asserts.assertType(opt_data.errorMessageValue == null || (goog.isString(opt_data.errorMessageValue) || opt_data.errorMessageValue instanceof goog.soy.data.SanitizedContent), 'errorMessageValue', opt_data.errorMessageValue, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var parameterMessagePlaceholder = soy.asserts.assertType(opt_data.parameterMessagePlaceholder == null || (goog.isString(opt_data.parameterMessagePlaceholder) || opt_data.parameterMessagePlaceholder instanceof goog.soy.data.SanitizedContent), 'parameterMessagePlaceholder', opt_data.parameterMessagePlaceholder, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var parameterValue = soy.asserts.assertType(opt_data.parameterValue == null || (goog.isString(opt_data.parameterValue) || opt_data.parameterValue instanceof goog.soy.data.SanitizedContent), 'parameterValue', opt_data.parameterValue, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!goog.soy.data.SanitizedContent|null|string|undefined} */
  var validationMessage = soy.asserts.assertType(opt_data.validationMessage == null || (goog.isString(opt_data.validationMessage) || opt_data.validationMessage instanceof goog.soy.data.SanitizedContent), 'validationMessage', opt_data.validationMessage, '!goog.soy.data.SanitizedContent|null|string|undefined');
  /** @type {!Array<{label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined} */
  var validationsOptions = soy.asserts.assertType(opt_data.validationsOptions == null || goog.isArray(opt_data.validationsOptions), 'validationsOptions', opt_data.validationsOptions, '!Array<{label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined');
  incrementalDom.elementOpenStart('div');
      incrementalDom.attr('class', 'form-group lfr-ddm-form-field-validation');
      incrementalDom.attr('data-fieldname', name);
  incrementalDom.elementOpenEnd();
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'form-group');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('label');
          incrementalDom.attr('class', 'control-label');
          incrementalDom.attr('for', name + 'EnableValidation');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('input');
            incrementalDom.attr('class', 'enable-validation toggle-switch');
            if (enableValidationValue) {
              incrementalDom.attr('checked', '');
            }
            incrementalDom.attr('id', name + 'EnableValidation');
            incrementalDom.attr('type', 'checkbox');
            incrementalDom.attr('value', 'true');
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
          soyIdom.print(validationMessage);
        incrementalDom.elementClose('span');
      incrementalDom.elementClose('label');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', (enableValidationValue ? '' : 'hide') + ' mb-3 mt-3 row');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'col-md-6');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('select');
            incrementalDom.attr('class', 'form-control validations-select');
        incrementalDom.elementOpenEnd();
          if (validationsOptions) {
            var option51List = validationsOptions;
            var option51ListLen = option51List.length;
            for (var option51Index = 0; option51Index < option51ListLen; option51Index++) {
                var option51Data = option51List[option51Index];
                $validationOption(soy.$$assignDefaults({option: option51Data}, opt_data), null, opt_ijData);
              }
          }
        incrementalDom.elementClose('select');
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'col-md-6');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('input');
            incrementalDom.attr('class', 'field form-control ' + (parameterMessagePlaceholder ? '' : ' hide') + ' parameter-input');
            incrementalDom.attr('placeholder', parameterMessagePlaceholder);
            incrementalDom.attr('type', 'text');
            incrementalDom.attr('value', parameterValue);
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('input');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', (enableValidationValue ? '' : 'hide') + ' row');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'col-md-12');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('input');
            incrementalDom.attr('class', 'field form-control message-input');
            incrementalDom.attr('placeholder', errorMessagePlaceholder);
            incrementalDom.attr('type', 'text');
            incrementalDom.attr('value', errorMessageValue);
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('input');
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
    incrementalDom.elementOpenStart('input');
        incrementalDom.attr('name', name);
        incrementalDom.attr('type', 'hidden');
        incrementalDom.attr('value', value);
    incrementalDom.elementOpenEnd();
    incrementalDom.elementClose('input');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  name: (!goog.soy.data.SanitizedContent|string),
 *  value: (?),
 *  enableValidationValue: (boolean|null|undefined),
 *  errorMessagePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  errorMessageValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  parameterMessagePlaceholder: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  parameterValue: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  validationMessage: (!goog.soy.data.SanitizedContent|null|string|undefined),
 *  validationsOptions: (!Array<{label: (!goog.soy.data.SanitizedContent|string), status: (!goog.soy.data.SanitizedContent|string), value: (!goog.soy.data.SanitizedContent|string)}>|null|undefined)
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMValidation.render';
}

exports.validationOption.params = ["option"];
exports.validationOption.types = {"option":"[label: string, status: string, value: string]"};
exports.render.params = ["name","value","enableValidationValue","errorMessagePlaceholder","errorMessageValue","parameterMessagePlaceholder","parameterValue","validationMessage","validationsOptions"];
exports.render.types = {"name":"string","value":"?","enableValidationValue":"bool","errorMessagePlaceholder":"string","errorMessageValue":"string","parameterMessagePlaceholder":"string","parameterValue":"string","validationMessage":"string","validationsOptions":"list<[label: string, status: string, value: string]>"};
templates = exports;
return exports;

});

class DDMValidation extends Component {}
Soy.register(DDMValidation, templates);
export { DDMValidation, templates };
export default templates;
/* jshint ignore:end */
