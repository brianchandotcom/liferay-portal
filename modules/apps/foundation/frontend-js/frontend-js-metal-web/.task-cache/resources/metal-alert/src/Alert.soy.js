define("frontend-js-metal-web@1.0.0/metal-alert/src/Alert.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/soy'], function (exports, _component, _soy) {
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
  // This file was automatically generated from Alert.soy.
  // Please don't edit this file by hand.

  /**
   * @fileoverview Templates in namespace Templates.Alert.
   */

  if (typeof Templates.Alert == 'undefined') {
    Templates.Alert = {};
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.Alert.render = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="alert alert-dismissible component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '" role="alert">' + (opt_data.spinner ? '<span class="alert-spinner' + soy.$$escapeHtmlAttribute(opt_data.spinnerClasses ? ' ' + opt_data.spinnerClasses : '') + '"></span>' : '') + '<span class="alert-body">' + (opt_data.body ? soy.$$escapeHtml(opt_data.body) : '') + '</span>' + (opt_data.dismissible ? '<button type="button" class="close" aria-label="Close" data-onclick="toggle"><span aria-hidden="true">×</span></button>' : '') + '</div>');
  };
  if (goog.DEBUG) {
    Templates.Alert.render.soyTemplateName = 'Templates.Alert.render';
  }

  Templates.Alert.render.params = ["body", "dismissible", "id", "spinner", "spinnerClasses"];

  var Alert = function (_Component) {
    _inherits(Alert, _Component);

    function Alert() {
      _classCallCheck(this, Alert);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Alert;
  }(_component2.default);

  Alert.prototype.registerMetalComponent && Alert.prototype.registerMetalComponent(Alert, 'Alert')

  Alert.RENDERER = _soy.SoyRenderer;
  _soy.SoyAop.registerTemplates('Alert');
  exports.default = Alert;
});
//# sourceMappingURL=Alert.soy.js.map