define("frontend-image-editor-capability-filter@1.0.0/FilterComponent.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.ImageEditorFilterComponent = undefined;

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

    // This file was automatically generated from FilterComponent.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace ImageEditorFilterComponent.
     * @public
     */

    goog.module('ImageEditorFilterComponent.incrementaldom');

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
      var filterList20 = opt_data.filters;
      var filterListLen20 = filterList20.length;
      for (var filterIndex20 = 0; filterIndex20 < filterListLen20; filterIndex20++) {
        var filterData20 = filterList20[filterIndex20];
        ie_open('li', null, null, 'class', 'item');
        ie_open('a', null, null, 'data-onclick', 'previewFilter', 'data-filter', filterData20, 'class', opt_data.currentFilter_ == filterData20 ? 'active' : '');
        ie_open('div', null, null, 'id', opt_data.key + filterData20, 'style', 'position: relative;');
        ie_void('canvas', null, null, 'width', opt_data.thumbnailSize, 'height', opt_data.thumbnailSize);
        ie_close('div');
        ie_open('span');
        itext((goog.asserts.assert(filterData20 != null), filterData20));
        ie_close('span');
        ie_close('a');
        ie_close('li');
      }
      ie_close('ul');
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'ImageEditorFilterComponent.render';
    }

    exports.render.params = ["filters", "key", "thumbnailSize", "currentFilter_"];
    exports.render.types = { "filters": "any", "key": "any", "thumbnailSize": "any", "currentFilter_": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var ImageEditorFilterComponent = function (_Component) {
    _inherits(ImageEditorFilterComponent, _Component);

    function ImageEditorFilterComponent() {
      _classCallCheck(this, ImageEditorFilterComponent);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ImageEditorFilterComponent;
  }(_Component3.default);

  _Soy2.default.register(ImageEditorFilterComponent, templates);
  exports.default = templates;
  exports.ImageEditorFilterComponent = ImageEditorFilterComponent;
  exports.templates = templates;
});
//# sourceMappingURL=FilterComponent.soy.js.map