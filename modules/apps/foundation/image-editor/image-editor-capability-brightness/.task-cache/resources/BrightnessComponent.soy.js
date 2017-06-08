define("liferay-image-editor-capability-brightness@1.0.0/BrightnessComponent.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.ImageEditorBrightnessComponent = undefined;

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

    // This file was automatically generated from BrightnessComponent.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace ImageEditorBrightnessComponent.
     * @public
     */

    goog.module('ImageEditorBrightnessComponent.incrementaldom');

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

    var $templateAlias1 = _Soy2.default.getTemplate('Slider.incrementaldom', 'render');

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'id', opt_data.key);
      $templateAlias1({ key: 'brightnessSlider', events: { valueChanged: opt_data.requestEditorPreview } }, null, opt_ijData);
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'ImageEditorBrightnessComponent.render';
    }

    exports.render.params = ["key", "requestEditorPreview"];
    exports.render.types = { "key": "any", "requestEditorPreview": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var ImageEditorBrightnessComponent = function (_Component) {
    _inherits(ImageEditorBrightnessComponent, _Component);

    function ImageEditorBrightnessComponent() {
      _classCallCheck(this, ImageEditorBrightnessComponent);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ImageEditorBrightnessComponent;
  }(_Component3.default);

  _Soy2.default.register(ImageEditorBrightnessComponent, templates);
  exports.default = templates;
  exports.ImageEditorBrightnessComponent = ImageEditorBrightnessComponent;
  exports.templates = templates;
});
//# sourceMappingURL=BrightnessComponent.soy.js.map