define(
  "frontend-js-metal-web@1.0.0/crystal-modal/src/Modal.soy",
  ['exports', 'module', 'metal/src/component/ComponentRegistry'],
  function (exports, module, _metalSrcComponentComponentRegistry) {
    /* jshint ignore:start */
    'use strict';

    function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

    var _ComponentRegistry = _interopRequireDefault(_metalSrcComponentComponentRegistry);

    var Templates = _ComponentRegistry['default'].Templates;
    // This file was automatically generated from Modal.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Templates.Modal.
     */

    if (typeof Templates.Modal == 'undefined') {
      Templates.Modal = {};
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Modal.content = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="modal component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '" role="' + soy.$$escapeHtmlAttribute(opt_data.role ? opt_data.role : 'dialog') + '" aria-labelledby="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-header"><div class="modal-dialog" tabindex="0"><div class="modal-content">' + Templates.Modal.header(opt_data, null, opt_ijData) + Templates.Modal.body(opt_data, null, opt_ijData) + Templates.Modal.footer(opt_data, null, opt_ijData) + '</div></div></div>');
    };
    if (goog.DEBUG) {
      Templates.Modal.content.soyTemplateName = 'Templates.Modal.content';
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Modal.body = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<section id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-body" class="modal-body">' + (opt_data.body ? soy.$$escapeHtml(opt_data.body) : '') + '</section>');
    };
    if (goog.DEBUG) {
      Templates.Modal.body.soyTemplateName = 'Templates.Modal.body';
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Modal.footer = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<footer id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-footer" class="modal-footer">' + (opt_data.footer ? soy.$$escapeHtml(opt_data.footer) : '') + '</footer>');
    };
    if (goog.DEBUG) {
      Templates.Modal.footer.soyTemplateName = 'Templates.Modal.footer';
    }

    /**
     * @param {Object.<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object.<string, *>=} opt_ijData
     * @return {!soydata.SanitizedHtml}
     * @suppress {checkTypes}
     */
    Templates.Modal.header = function (opt_data, opt_ignored, opt_ijData) {
      return soydata.VERY_UNSAFE.ordainSanitizedHtml('<header id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-header" class="modal-header">' + (opt_data.header ? '<button type="button" class="close" data-onclick="hide" aria-label="Close"><span aria-hidden="true">×</span></button>' + soy.$$escapeHtml(opt_data.header) : '') + '</header>');
    };
    if (goog.DEBUG) {
      Templates.Modal.header.soyTemplateName = 'Templates.Modal.header';
    }

    Templates.Modal.content.params = ["id", "role"];
    Templates.Modal.body.params = ["id", "body"];
    Templates.Modal.footer.params = ["footer", "id"];
    Templates.Modal.header.params = ["header", "id"];
    module.exports = Templates.Modal;

    /* jshint ignore:end */
  }
);