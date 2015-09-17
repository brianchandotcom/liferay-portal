define(
  "frontend-js-metal-web@1.0.0/crystal-slider/src/Slider.soy",
  ['exports', 'module', 'metal/src/component/ComponentRegistry'],
  function (exports, module, _metalSrcComponentComponentRegistry) {
    /* jshint ignore:start */
    'use strict';

    function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

    var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

    var Templates = _ComponentRegistry['default'].Templates;
    // This file was automatically generated from Slider.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Templates.Slider.
     */

    if (typeof Templates.Slider == 'undefined') {
      Templates.Slider = {};
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Slider.content = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="slider component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '">' + Templates.Slider.input(opt_data, null, opt_ijData) + Templates.Slider.label(opt_data, null, opt_ijData) + Templates.Slider.rail(opt_data, null, opt_ijData) + '</div>');
    };
    if (goog.DEBUG) {
      Templates.Slider.content.soyTemplateName = 'Templates.Slider.content';
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Slider.input = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-input"><input name="' + soy.$$escapeHtmlAttribute(opt_data.inputName ? opt_data.inputName : opt_data.id) + '" type="hidden" value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '"></div>');
    };
    if (goog.DEBUG) {
      Templates.Slider.input.soyTemplateName = 'Templates.Slider.input';
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Slider.label = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-label"><span>' + soy.$$escapeHtml(opt_data.value) + '</span></div>');
    };
    if (goog.DEBUG) {
      Templates.Slider.label.soyTemplateName = 'Templates.Slider.label';
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Slider.rail = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-rail"><div class="rail" data-onmousedown="onRailMouseDown_"><div class="rail-active"></div><div class="rail-handle"><div class="handle" tabindex="0"></div></div></div></div>');
    };
    if (goog.DEBUG) {
      Templates.Slider.rail.soyTemplateName = 'Templates.Slider.rail';
    }

    Templates.Slider.content.params = ["id"];
    Templates.Slider.input.params = ["id", "inputName", "value"];
    Templates.Slider.label.params = ["id", "value"];
    Templates.Slider.rail.params = ["id"];
    module.exports = Templates.Slider;

    /* jshint ignore:end */
  }
);