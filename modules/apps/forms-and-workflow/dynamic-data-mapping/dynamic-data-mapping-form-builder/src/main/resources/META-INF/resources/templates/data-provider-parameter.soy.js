/* jshint ignore:start */
import Component from 'metal-component';
import Soy from 'metal-soy';
var templates;
goog.loadModule(function(exports) {

// This file was automatically generated from data-provider-parameter.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace DDMDataProviderParameter.
 * @public
 */

goog.module('DDMDataProviderParameter.incrementaldom');

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
 *    hasInputs: boolean,
 *    hasRequiredInputs: boolean,
 *    strings: {dataProviderParameterInput: string, dataProviderParameterInputDescription: string, dataProviderParameterOutput: string, dataProviderParameterOutputDescription: string, requiredField: string}
 * }} opt_data
 * @param {(null|undefined)=} opt_ignored
 * @param {Object<string, *>=} opt_ijData
 * @return {void}
 * @suppress {checkTypes}
 */
function $render(opt_data, opt_ignored, opt_ijData) {
  soy.asserts.assertType(goog.isBoolean(opt_data.hasInputs) || opt_data.hasInputs === 1 || opt_data.hasInputs === 0, 'hasInputs', opt_data.hasInputs, 'boolean');
  var hasInputs = /** @type {boolean} */ (!!opt_data.hasInputs);
  soy.asserts.assertType(goog.isBoolean(opt_data.hasRequiredInputs) || opt_data.hasRequiredInputs === 1 || opt_data.hasRequiredInputs === 0, 'hasRequiredInputs', opt_data.hasRequiredInputs, 'boolean');
  var hasRequiredInputs = /** @type {boolean} */ (!!opt_data.hasRequiredInputs);
  var strings = goog.asserts.assertObject(opt_data.strings, "expected parameter 'strings' of type [dataProviderParameterInput: string, dataProviderParameterInputDescription: string, dataProviderParameterOutput: string, dataProviderParameterOutputDescription: string, requiredField: string].");
  ie_open('div');
    if (hasInputs) {
      ie_open('div', null, null,
          'class', 'data-provider-parameter-input-container');
        if (hasRequiredInputs) {
          ie_open('div', null, null,
              'class', 'data-provider-label-container');
            ie_open('p', null, null,
                'class', 'data-provider-parameter-input-required-field');
              var dyn3 = strings.requiredField;
              if (typeof dyn3 == 'function') dyn3(); else if (dyn3 != null) itext(dyn3);
            ie_close('p');
            ie_void('span', null, null,
                'class', 'icon-asterisk text-warning');
          ie_close('div');
        }
        ie_open('div', null, null,
            'class', 'data-provider-label-container');
          ie_open('p', null, null,
              'class', 'data-provider-parameter-input');
            ie_open('b');
              var dyn4 = strings.dataProviderParameterInput;
              if (typeof dyn4 == 'function') dyn4(); else if (dyn4 != null) itext(dyn4);
            ie_close('b');
          ie_close('p');
          ie_open('p', null, null,
              'class', 'data-provider-parameter-input-description');
            var dyn5 = strings.dataProviderParameterInputDescription;
            if (typeof dyn5 == 'function') dyn5(); else if (dyn5 != null) itext(dyn5);
          ie_close('p');
        ie_close('div');
        ie_void('div', null, null,
            'class', 'data-provider-parameter-input-list row');
      ie_close('div');
    }
    ie_open('div', null, null,
        'class', 'data-provider-parameter-output-container');
      ie_open('div', null, null,
          'class', 'data-provider-label-container');
        ie_open('p', null, null,
            'class', 'data-provider-parameter-output');
          ie_open('b');
            var dyn6 = strings.dataProviderParameterOutput;
            if (typeof dyn6 == 'function') dyn6(); else if (dyn6 != null) itext(dyn6);
          ie_close('b');
        ie_close('p');
        ie_open('p', null, null,
            'class', 'data-provider-parameter-output-description');
          var dyn7 = strings.dataProviderParameterOutputDescription;
          if (typeof dyn7 == 'function') dyn7(); else if (dyn7 != null) itext(dyn7);
        ie_close('p');
      ie_close('div');
      ie_void('div', null, null,
          'class', 'data-provider-parameter-output-list row');
    ie_close('div');
  ie_close('div');
}
exports.render = $render;
if (goog.DEBUG) {
  $render.soyTemplateName = 'DDMDataProviderParameter.render';
}

exports.render.params = ["hasInputs","hasRequiredInputs"];
exports.render.types = {"hasInputs":"bool","hasRequiredInputs":"bool"};
templates = exports;
return exports;

});

class DDMDataProviderParameter extends Component {}
Soy.register(DDMDataProviderParameter, templates);
export { DDMDataProviderParameter, templates };
export default templates;
/* jshint ignore:end */
