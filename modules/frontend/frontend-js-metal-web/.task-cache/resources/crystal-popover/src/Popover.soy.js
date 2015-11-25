'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-popover/src/Popover.soy", ['exports', 'metal/src/component/Component', 'metal/src/component/ComponentRegistry', 'metal/src/soy/SoyAop', 'metal/src/soy/SoyRenderer', 'metal/src/soy/SoyTemplates'], function (exports, _Component2, _ComponentRegistry, _SoyAop, _SoyRenderer, _SoyTemplates) {
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

  if (typeof Templates.Popover == 'undefined') {
    Templates.Popover = {};
  }

  Templates.Popover.content = function (opt_data, opt_ignored, opt_ijData) {
    var output = '';
    var positionClasses__soy3 = ['top', 'right', 'bottom', 'left'];
    var positionClass__soy4 = opt_data.position != null ? positionClasses__soy3[opt_data.position] : 'bottom';
    output += '<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="popover component ' + soy.$$escapeHtmlAttribute(positionClass__soy4) + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '" role="tooltip"><div class="arrow"></div>' + Templates.Popover.title(opt_data, null, opt_ijData) + Templates.Popover.innerContent(opt_data, null, opt_ijData) + '</div>';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };

  if (goog.DEBUG) {
    Templates.Popover.content.soyTemplateName = 'Templates.Popover.content';
  }

  Templates.Popover.title = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<h3 id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-title" class="popover-title' + soy.$$escapeHtmlAttribute(opt_data.title ? '' : ' hidden') + '">' + soy.$$escapeHtml(opt_data.title) + '</h3>');
  };

  if (goog.DEBUG) {
    Templates.Popover.title.soyTemplateName = 'Templates.Popover.title';
  }

  Templates.Popover.innerContent = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-innerContent" class="popover-content"><p>' + soy.$$escapeHtml(opt_data.content ? opt_data.content : '') + '</p></div>');
  };

  if (goog.DEBUG) {
    Templates.Popover.innerContent.soyTemplateName = 'Templates.Popover.innerContent';
  }

  Templates.Popover.content.params = ["id"];
  Templates.Popover.title.params = ["id", "title"];
  Templates.Popover.innerContent.params = ["content", "id"];

  var Popover = (function (_Component) {
    _inherits(Popover, _Component);

    function Popover() {
      _classCallCheck(this, Popover);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    Popover.setImpl = function setImpl(ctor) {
      _ComponentRegistry2.default.register(ctor, 'Popover');
    };

    return Popover;
  })(_Component3.default);

  Popover.RENDERER = _SoyRenderer2.default;
  Popover.setImpl(Popover);

  _SoyAop2.default.registerTemplates('Popover');

  exports.default = Popover;
});
//# sourceMappingURL=Popover.soy.js.map