define("frontend-js-metal-web@1.0.6/metal-progressbar/src/ProgressBar.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.ProgressBar = undefined;

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

    // This file was automatically generated from ProgressBar.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace ProgressBar.
     * @public
     */

    goog.module('ProgressBar.incrementaldom');

    /** @suppress {extraRequire} */
    var soy = goog.require('soy');
    /** @suppress {extraRequire} */
    var soydata = goog.require('soydata');
    /** @suppress {extraRequire} */
    goog.require('goog.i18n.bidi');
    /** @suppress {extraRequire} */
    goog.require('goog.asserts');
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
    function $render(opt_data, opt_ignored, opt_ijData) {
      var curMax__soy3 = opt_data.max ? opt_data.max : 100;
      var curMin__soy4 = opt_data.min ? opt_data.min : 0;
      var curValue__soy5 = opt_data.value ? opt_data.value : 0;
      ie_open('div', null, null, 'id', opt_data.id, 'class', 'progress ' + (opt_data.elementClasses ? ' ' + opt_data.elementClasses : ''), 'role', 'progressbar', 'aria-valuemax', curMax__soy3, 'aria-valuemin', curMin__soy4, 'aria-valuenow', curValue__soy5, 'tabindex', '0');
      var percentage__soy17 = Math.floor((curValue__soy5 - curMin__soy4) * 100 / (curMax__soy3 - curMin__soy4));
      ie_open('div', null, null, 'class', 'progress-bar' + (opt_data.barClass ? ' ' + opt_data.barClass : ''), 'style', 'width: ' + percentage__soy17 + '%');
      itext((goog.asserts.assert((opt_data.label ? opt_data.label : '') != null), opt_data.label ? opt_data.label : ''));
      ie_close('div');
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'ProgressBar.render';
    }

    exports.render.params = ["barClass", "elementClasses", "id", "label", "max", "min", "value"];
    exports.templates = templates = exports;
    return exports;
  });

  var ProgressBar = function (_Component) {
    _inherits(ProgressBar, _Component);

    function ProgressBar() {
      _classCallCheck(this, ProgressBar);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ProgressBar;
  }(_Component3.default);

  _Soy2.default.register(ProgressBar, templates);
  exports.default = templates;
  exports.ProgressBar = ProgressBar;
  exports.templates = templates;
});
//# sourceMappingURL=ProgressBar.soy.js.map