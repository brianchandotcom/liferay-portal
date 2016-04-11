define("frontend-js-metal-web@1.0.6/metal-alert/src/Alert.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.Alert = undefined;

  var _Component3 = _interopRequireDefault(_Component2);

  var _Soy2 = _interopRequireDefault(_Soy);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _possibleConstructorReturn(self, call) {
    if (!self) {
      throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
    }

    return call && (typeof call === "object" || typeof call === "function") ? call : self;
  }

  function _inherits(subClass, superClass) {
    if (typeof superClass !== "function" && superClass !== null) {
      throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
    }

    subClass.prototype = Object.create(superClass && superClass.prototype, {
      constructor: {
        value: subClass,
        enumerable: false,
        writable: true,
        configurable: true
      }
    });
    if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
  }

  var templates;
  goog.loadModule(function (exports) {

    // This file was automatically generated from Alert.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Alert.
     * @public
     */

    goog.module('Alert.incrementaldom');

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
     *    dismissible: (?),
     *    spinner: (?),
     *    spinnerDone: (?),
     *    elementClasses: (?),
     *    spinnerClasses: (?),
     *    body: (?soydata.SanitizedHtml|string|undefined)
     * }} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      soy.asserts.assertType(opt_data.body == null || opt_data.body instanceof Function || opt_data.body instanceof soydata.UnsanitizedText || goog.isString(opt_data.body), 'body', opt_data.body, '?soydata.SanitizedHtml|string|undefined');
      var body = /** @type {?soydata.SanitizedHtml|string|undefined} */opt_data.body;
      ie_open('div', null, null, 'class', 'alert' + (opt_data.dismissible ? ' alert-dismissible' : '') + (opt_data.elementClasses ? ' ' + opt_data.elementClasses : ''), 'role', 'alert');
      if (opt_data.spinner) {
        ie_void('span', null, null, 'class', 'alert-spinner' + (opt_data.spinnerClasses ? ' ' + opt_data.spinnerClasses : '') + (opt_data.spinnerDone ? ' alert-spinner-done' : ''));
      }
      ie_open('span', null, null, 'class', 'alert-body');
      if (body) {
        body();
      }
      ie_close('span');
      if (opt_data.dismissible) {
        ie_open('button', null, null, 'type', 'button', 'class', 'close', 'aria-label', 'Close', 'data-onclick', 'toggle');
        ie_open('span', null, null, 'aria-hidden', 'true');
        itext('×');
        ie_close('span');
        ie_close('button');
      }
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'Alert.render';
    }

    exports.render.params = ["body", "dismissible", "spinner", "spinnerDone", "elementClasses", "spinnerClasses"];
    exports.templates = templates = exports;
    return exports;
  });

  var Alert = function (_Component) {
    _inherits(Alert, _Component);

    function Alert() {
      _classCallCheck(this, Alert);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Alert;
  }(_Component3.default);

  _Soy2.default.register(Alert, templates);
  exports.default = templates;
  exports.Alert = Alert;
  exports.templates = templates;
});
//# sourceMappingURL=Alert.soy.js.map