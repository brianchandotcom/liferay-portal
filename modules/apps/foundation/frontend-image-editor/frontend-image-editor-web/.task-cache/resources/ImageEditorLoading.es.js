define("frontend-image-editor-web@1.0.7/ImageEditorLoading.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', './ImageEditorLoading.soy'], function (exports, _Component2, _Soy, _ImageEditorLoading) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _Component3 = _interopRequireDefault(_Component2);

  var _Soy2 = _interopRequireDefault(_Soy);

  var _ImageEditorLoading2 = _interopRequireDefault(_ImageEditorLoading);

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

  var ImageEditorLoading = function (_Component) {
    _inherits(ImageEditorLoading, _Component);

    function ImageEditorLoading() {
      _classCallCheck(this, ImageEditorLoading);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ImageEditorLoading;
  }(_Component3.default);

  // Register component
  _Soy2.default.register(ImageEditorLoading, _ImageEditorLoading2.default);

  exports.default = ImageEditorLoading;
});
//# sourceMappingURL=ImageEditorLoading.es.js.map