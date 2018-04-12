/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';

var templates;
goog.loadModule(function(exports) {
var soy = goog.require('soy');
var soydata = goog.require('soydata');
// This file was automatically generated from data-provider-parameter.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMDataProviderParameter.
 * @public
 */

goog.module('DDMDataProviderParameter.incrementaldom');

goog.require('goog.soy.data.SanitizedContent');
var incrementalDom = goog.require('incrementaldom');
goog.require('soy.asserts');
var soyIdom = goog.require('soy.idom');


/**
 * @param {{
 *  hasInputs: boolean,
 *  hasRequiredInputs: boolean,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  strings: {dataProviderParameterInput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterInputDescription: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutputDescription: (!goog.soy.data.SanitizedContent|string), requiredField: (!goog.soy.data.SanitizedContent|string)}
 * }} opt_data
 * @param {Object<string, *>=} opt_ijData
 * @param {Object<string, *>=} opt_ijData_deprecated
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ijData, opt_ijData_deprecated) {
  opt_ijData = opt_ijData_deprecated || opt_ijData;
  /** @type {boolean} */
  var hasInputs = soy.asserts.assertType(goog.isBoolean(opt_data.hasInputs) || opt_data.hasInputs === 1 || opt_data.hasInputs === 0, 'hasInputs', opt_data.hasInputs, 'boolean');
  /** @type {boolean} */
  var hasRequiredInputs = soy.asserts.assertType(goog.isBoolean(opt_data.hasRequiredInputs) || opt_data.hasRequiredInputs === 1 || opt_data.hasRequiredInputs === 0, 'hasRequiredInputs', opt_data.hasRequiredInputs, 'boolean');
  /** @type {!goog.soy.data.SanitizedContent|string} */
  var pathThemeImages = soy.asserts.assertType(goog.isString(opt_data.pathThemeImages) || opt_data.pathThemeImages instanceof goog.soy.data.SanitizedContent, 'pathThemeImages', opt_data.pathThemeImages, '!goog.soy.data.SanitizedContent|string');
  /** @type {{dataProviderParameterInput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterInputDescription: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutputDescription: (!goog.soy.data.SanitizedContent|string), requiredField: (!goog.soy.data.SanitizedContent|string)}} */
  var strings = soy.asserts.assertType(goog.isObject(opt_data.strings), 'strings', opt_data.strings, '{dataProviderParameterInput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterInputDescription: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutputDescription: (!goog.soy.data.SanitizedContent|string), requiredField: (!goog.soy.data.SanitizedContent|string)}');
  incrementalDom.elementOpen('div');
    if (hasInputs) {
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'data-provider-parameter-input-container');
      incrementalDom.elementOpenEnd();
        if (hasRequiredInputs) {
          incrementalDom.elementOpenStart('div');
              incrementalDom.attr('class', 'data-provider-label-container');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpen('label');
              incrementalDom.elementOpenStart('p');
                  incrementalDom.attr('class', 'data-provider-parameter-input-required-field');
              incrementalDom.elementOpenEnd();
                soyIdom.print(strings.requiredField);
              incrementalDom.elementClose('p');
              incrementalDom.text(' ');
              incrementalDom.elementOpenStart('svg');
                  incrementalDom.attr('aria-hidden', 'true');
                  incrementalDom.attr('class', 'lexicon-icon lexicon-icon-asterisk reference-mark');
              incrementalDom.elementOpenEnd();
                incrementalDom.elementOpenStart('use');
                    incrementalDom.attr('xlink:href', pathThemeImages + '/lexicon/icons.svg#asterisk');
                incrementalDom.elementOpenEnd();
                incrementalDom.elementClose('use');
              incrementalDom.elementClose('svg');
            incrementalDom.elementClose('label');
          incrementalDom.elementClose('div');
        }
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'data-provider-label-container');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpenStart('p');
              incrementalDom.attr('class', 'data-provider-parameter-input');
          incrementalDom.elementOpenEnd();
            incrementalDom.elementOpen('b');
              soyIdom.print(strings.dataProviderParameterInput);
            incrementalDom.elementClose('b');
          incrementalDom.elementClose('p');
          incrementalDom.elementOpenStart('p');
              incrementalDom.attr('class', 'data-provider-parameter-input-description');
          incrementalDom.elementOpenEnd();
            soyIdom.print(strings.dataProviderParameterInputDescription);
          incrementalDom.elementClose('p');
        incrementalDom.elementClose('div');
        incrementalDom.elementOpenStart('div');
            incrementalDom.attr('class', 'data-provider-parameter-input-list row');
        incrementalDom.elementOpenEnd();
        incrementalDom.elementClose('div');
      incrementalDom.elementClose('div');
    }
    incrementalDom.elementOpenStart('div');
        incrementalDom.attr('class', 'data-provider-parameter-output-container');
    incrementalDom.elementOpenEnd();
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'data-provider-label-container');
      incrementalDom.elementOpenEnd();
        incrementalDom.elementOpenStart('p');
            incrementalDom.attr('class', 'data-provider-parameter-output');
        incrementalDom.elementOpenEnd();
          incrementalDom.elementOpen('b');
            soyIdom.print(strings.dataProviderParameterOutput);
          incrementalDom.elementClose('b');
        incrementalDom.elementClose('p');
        incrementalDom.elementOpenStart('p');
            incrementalDom.attr('class', 'data-provider-parameter-output-description');
        incrementalDom.elementOpenEnd();
          soyIdom.print(strings.dataProviderParameterOutputDescription);
        incrementalDom.elementClose('p');
      incrementalDom.elementClose('div');
      incrementalDom.elementOpenStart('div');
          incrementalDom.attr('class', 'data-provider-parameter-output-list row');
      incrementalDom.elementOpenEnd();
      incrementalDom.elementClose('div');
    incrementalDom.elementClose('div');
  incrementalDom.elementClose('div');
}
exports.render = $render;
/**
 * @typedef {{
 *  hasInputs: boolean,
 *  hasRequiredInputs: boolean,
 *  pathThemeImages: (!goog.soy.data.SanitizedContent|string),
 *  strings: {dataProviderParameterInput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterInputDescription: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutput: (!goog.soy.data.SanitizedContent|string), dataProviderParameterOutputDescription: (!goog.soy.data.SanitizedContent|string), requiredField: (!goog.soy.data.SanitizedContent|string)}
 * }}
 */
$render.Params;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMDataProviderParameter.render';
}

exports.render.params = ["hasInputs","hasRequiredInputs","pathThemeImages","strings"];
exports.render.types = {"hasInputs":"bool","hasRequiredInputs":"bool","pathThemeImages":"string","strings":"[requiredField: string, dataProviderParameterInput: string, dataProviderParameterInputDescription: string, dataProviderParameterOutput: string, dataProviderParameterOutputDescription: string]"};
templates = exports;
return exports;

});

class DDMDataProviderParameter extends Component {}
Soy.register(DDMDataProviderParameter, templates);
export { DDMDataProviderParameter, templates };
export default templates;
/* jshint ignore:end */
