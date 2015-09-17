define(
  "frontend-js-metal-web@1.0.0/crystal-tooltip/src/Tooltip.soy",
  ['exports', 'module', 'metal/src/component/ComponentRegistry'],
  function (exports, module, _metalSrcComponentComponentRegistry) {
    /* jshint ignore:start */
    'use strict';

    function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

    var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

    var Templates = _ComponentRegistry['default'].Templates;
    // This file was automatically generated from Tooltip.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Templates.Tooltip.
     */

    if (typeof Templates.Tooltip == 'undefined') {
      Templates.Tooltip = {};
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Tooltip.content = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="tooltip component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '" role="tooltip"><div class="tooltip-arrow"></div>' + Templates.Tooltip.inner(opt_data, null, opt_ijData) + '</div>');
    };
    if (goog.DEBUG) {
      Templates.Tooltip.content.soyTemplateName = 'Templates.Tooltip.content';
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Tooltip.inner = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<section id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-inner" class="tooltip-inner">' + soy.$$escapeHtml(opt_data.content) + '</section>');
    };
    if (goog.DEBUG) {
      Templates.Tooltip.inner.soyTemplateName = 'Templates.Tooltip.inner';
    }

    Templates.Tooltip.content.params = ["id"];
    Templates.Tooltip.inner.params = ["content", "id"];
    module.exports = Templates.Tooltip;

    /* jshint ignore:end */
  }
);