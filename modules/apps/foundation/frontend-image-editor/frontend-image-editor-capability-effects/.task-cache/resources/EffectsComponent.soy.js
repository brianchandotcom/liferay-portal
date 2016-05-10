define("frontend-image-editor-capability-effects@1.0.0/EffectsComponent.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.ImageEditorEffectsComponent = undefined;

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

    // This file was automatically generated from EffectsComponent.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace ImageEditorEffectsComponent.
     * @public
     */

    goog.module('ImageEditorEffectsComponent.incrementaldom');

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
      ie_open('div', null, null, 'id', opt_data.key);
      ie_open('ul', null, null, 'class', 'carousel list-unstyled list-table');
      var effectList20 = opt_data.effects;
      var effectListLen20 = effectList20.length;
      for (var effectIndex20 = 0; effectIndex20 < effectListLen20; effectIndex20++) {
        var effectData20 = effectList20[effectIndex20];
        ie_open('li', null, null, 'class', 'item');
        ie_open('a', null, null, 'data-onclick', 'previewEffect', 'data-effect', effectData20, 'class', opt_data.currentEffect_ == effectData20 ? 'active' : '');
        ie_open('div', null, null, 'id', opt_data.key + effectData20, 'style', 'position: relative;');
        ie_void('canvas', null, null, 'width', opt_data.thumbnailSize, 'height', opt_data.thumbnailSize);
        ie_close('div');
        ie_open('span');
        itext((goog.asserts.assert(effectData20 != null), effectData20));
        ie_close('span');
        ie_close('a');
        ie_close('li');
      }
      ie_close('ul');
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'ImageEditorEffectsComponent.render';
    }

    exports.render.params = ["effects", "key", "thumbnailSize", "currentEffect_"];
    exports.render.types = { "effects": "any", "key": "any", "thumbnailSize": "any", "currentEffect_": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var ImageEditorEffectsComponent = function (_Component) {
    _inherits(ImageEditorEffectsComponent, _Component);

    function ImageEditorEffectsComponent() {
      _classCallCheck(this, ImageEditorEffectsComponent);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ImageEditorEffectsComponent;
  }(_Component3.default);

  _Soy2.default.register(ImageEditorEffectsComponent, templates);
  exports.default = templates;
  exports.ImageEditorEffectsComponent = ImageEditorEffectsComponent;
  exports.templates = templates;
});
//# sourceMappingURL=EffectsComponent.soy.js.map