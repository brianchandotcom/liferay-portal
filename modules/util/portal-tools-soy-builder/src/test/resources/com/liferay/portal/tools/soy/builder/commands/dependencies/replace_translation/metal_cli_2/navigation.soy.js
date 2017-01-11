Liferay.Loader.define("hello-soy-web@1.0.4/Navigation.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/Soy'], function (exports, _component, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.Navigation = undefined;

  var _component2 = _interopRequireDefault(_component);

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

    // This file was automatically generated from Navigation.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace Navigation.
     * @public
     */

    goog.module('Navigation.incrementaldom');

    /** @suppress {extraRequire} */
    var soy = goog.require('soy');
    /** @suppress {extraRequire} */
    var soydata = goog.require('soydata');
    /** @suppress {extraRequire} */
    goog.require('goog.i18n.bidi');
    /** @suppress {extraRequire} */
    goog.require('goog.asserts');
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

    var $templateAlias2 = _Soy2.default.getTemplate('Footer.incrementaldom', 'render');

    var $templateAlias1 = _Soy2.default.getTemplate('Header.incrementaldom', 'render');

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'id', opt_data.id);
      $templateAlias1(opt_data, null, opt_ijData);
      ie_open('p');
      /** @desc  */
      var MSG_EXTERNAL_8532436723280155945 = goog.getMsg('this-is-another-view');
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_8532436723280155945));
      ie_close('p');
      ie_open('a', null, null, 'href', opt_data.navigationURL);
      /** @desc  */
      var MSG_EXTERNAL_4596791579122762316 = goog.getMsg('click-here-to-navigate-back');
      itext(goog.string.unescapeEntities(MSG_EXTERNAL_4596791579122762316));
      ie_close('a');
      $templateAlias2(opt_data, null, opt_ijData);
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'Navigation.render';
    }

    exports.render.params = ["id", "navigationURL"];
    exports.render.types = { "id": "any", "navigationURL": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var Navigation = function (_Component) {
    _inherits(Navigation, _Component);

    function Navigation() {
      _classCallCheck(this, Navigation);

      return _possibleConstructorReturn(this, (Navigation.__proto__ || Object.getPrototypeOf(Navigation)).apply(this, arguments));
    }

    return Navigation;
  }(_component2.default);

  _Soy2.default.register(Navigation, templates);
  exports.Navigation = Navigation;
  exports.templates = templates;
  exports.default = templates;
});
//# sourceMappingURL=Navigation.soy.js.map