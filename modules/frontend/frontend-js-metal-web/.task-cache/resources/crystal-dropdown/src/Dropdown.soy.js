'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-dropdown/src/Dropdown.soy", ['exports', 'metal/src/component/Component', 'metal/src/component/ComponentRegistry', 'metal/src/soy/SoyAop', 'metal/src/soy/SoyRenderer', 'metal/src/soy/SoyTemplates'], function (exports, _Component2, _ComponentRegistry, _SoyAop, _SoyRenderer, _SoyTemplates) {
  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _Component3 = _interopRequireDefault(_Component2);

  var _ComponentRegistry2 = _interopRequireDefault(_ComponentRegistry);

  var _SoyAop2 = _interopRequireDefault(_SoyAop);

  var _SoyRenderer2 = _interopRequireDefault(_SoyRenderer);

  var _SoyTemplates2 = _interopRequireDefault(_SoyTemplates);

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

  var Templates = _SoyTemplates2.default.get();

  if (typeof Templates.Dropdown == 'undefined') {
    Templates.Dropdown = {};
  }

  Templates.Dropdown.content = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="dropdown component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + soy.$$escapeHtmlAttribute(opt_data.expanded ? ' open' : '') + '">' + (opt_data.header ? soy.$$escapeHtml(opt_data.header) : '') + Templates.Dropdown.body(opt_data, null, opt_ijData) + '</div>');
  };

  if (goog.DEBUG) {
    Templates.Dropdown.content.soyTemplateName = 'Templates.Dropdown.content';
  }

  Templates.Dropdown.body = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<ul id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-body" class="dropdown-menu">' + (opt_data.body ? soy.$$escapeHtml(opt_data.body) : '') + '</ul>');
  };

  if (goog.DEBUG) {
    Templates.Dropdown.body.soyTemplateName = 'Templates.Dropdown.body';
  }

  Templates.Dropdown.content.params = ["header", "id"];
  Templates.Dropdown.body.params = ["body", "id"];

  var Dropdown = (function (_Component) {
    _inherits(Dropdown, _Component);

    function Dropdown() {
      _classCallCheck(this, Dropdown);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    Dropdown.setImpl = function setImpl(ctor) {
      _ComponentRegistry2.default.register(ctor, 'Dropdown');
    };

    return Dropdown;
  })(_Component3.default);

  Dropdown.prototype.registerMetalComponent && Dropdown.prototype.registerMetalComponent(Dropdown, 'Dropdown')
  Dropdown.RENDERER = _SoyRenderer2.default;
  Dropdown.setImpl(Dropdown);

  _SoyAop2.default.registerTemplates('Dropdown');

  exports.default = Dropdown;
});
//# sourceMappingURL=Dropdown.soy.js.map