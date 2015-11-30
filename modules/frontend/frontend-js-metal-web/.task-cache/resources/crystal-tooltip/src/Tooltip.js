'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-tooltip/src/Tooltip", ['exports', 'metal/src/component/ComponentRegistry', 'crystal-tooltip/src/TooltipBase', 'metal-jquery-adapter/src/JQueryAdapter', 'crystal-tooltip/src/Tooltip.soy'], function (exports, _ComponentRegistry, _TooltipBase2, _JQueryAdapter) {
  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _ComponentRegistry2 = _interopRequireDefault(_ComponentRegistry);

  var _TooltipBase3 = _interopRequireDefault(_TooltipBase2);

  var _JQueryAdapter2 = _interopRequireDefault(_JQueryAdapter);

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

    return call && ((typeof call === 'undefined' ? 'undefined' : _typeof(call)) === "object" || typeof call === "function") ? call : self;
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

  var Tooltip = (function (_TooltipBase) {
    _inherits(Tooltip, _TooltipBase);

    function Tooltip() {
      _classCallCheck(this, Tooltip);

      return _possibleConstructorReturn(this, _TooltipBase.apply(this, arguments));
    }

    Tooltip.prototype.syncVisible = function syncVisible(visible) {
      this.element.style.opacity = visible ? 1 : '';

      _TooltipBase.prototype.syncVisible.call(this, visible);
    };

    return Tooltip;
  })(_TooltipBase3.default);

  Tooltip.Align = _TooltipBase3.default.Align;
  Tooltip.ELEMENT_CLASSES = 'tooltip';

  _ComponentRegistry2.default.register(Tooltip);

  exports.default = Tooltip;

  _JQueryAdapter2.default.register('tooltip', Tooltip);
});
//# sourceMappingURL=Tooltip.js.map