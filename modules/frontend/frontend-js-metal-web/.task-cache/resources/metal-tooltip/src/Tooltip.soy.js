define("frontend-js-metal-web@1.0.0/metal-tooltip/src/Tooltip.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/soy'], function (exports, _component, _soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _component2 = _interopRequireDefault(_component);

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

  var Templates = _soy.SoyTemplates.get();
  // This file was automatically generated from Tooltip.soy.
  // Please don't edit this file by hand.

  /**
   * @fileoverview Templates in namespace Templates.Tooltip.
   */

  if (typeof Templates.Tooltip == 'undefined') {
    Templates.Tooltip = {};
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.Tooltip.render = function (opt_data, opt_ignored, opt_ijData) {
    var output = '';
    var positionClasses__soy3 = ['top', 'right', 'bottom', 'left'];
    var positionClass__soy4 = opt_data.position != null ? positionClasses__soy3[opt_data.position] : 'bottom';
    output += '<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="tooltip component ' + soy.$$escapeHtmlAttribute(positionClass__soy4) + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '" role="tooltip"><div class="tooltip-arrow"></div>' + Templates.Tooltip.inner(opt_data, null, opt_ijData) + '</div>';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };
  if (goog.DEBUG) {
    Templates.Tooltip.render.soyTemplateName = 'Templates.Tooltip.render';
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.Tooltip.inner = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<section id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-inner" class="tooltip-inner">' + soy.$$escapeHtml(opt_data.title ? opt_data.title : '') + '</section>');
  };
  if (goog.DEBUG) {
    Templates.Tooltip.inner.soyTemplateName = 'Templates.Tooltip.inner';
  }

  Templates.Tooltip.render.params = ["id"];
  Templates.Tooltip.inner.params = ["title", "id"];

  var Tooltip = function (_Component) {
    _inherits(Tooltip, _Component);

    function Tooltip() {
      _classCallCheck(this, Tooltip);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Tooltip;
  }(_component2.default);

  Tooltip.prototype.registerMetalComponent && Tooltip.prototype.registerMetalComponent(Tooltip, 'Tooltip')

  Tooltip.RENDERER = _soy.SoyRenderer;
  _soy.SoyAop.registerTemplates('Tooltip');
  exports.default = Tooltip;
});
//# sourceMappingURL=Tooltip.soy.js.map