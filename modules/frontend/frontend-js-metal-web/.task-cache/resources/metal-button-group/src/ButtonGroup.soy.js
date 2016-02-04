var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

define("frontend-js-metal-web@1.0.0/metal-button-group/src/ButtonGroup.soy", ['exports', 'metal/src/component/Component', 'metal/src/soy/SoyAop', 'metal/src/soy/SoyRenderer', 'metal/src/soy/SoyTemplates'], function (exports, _Component2, _SoyAop, _SoyRenderer, _SoyTemplates) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _Component3 = _interopRequireDefault(_Component2);

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

  if (typeof Templates.ButtonGroup == 'undefined') {
    Templates.ButtonGroup = {};
  }

  Templates.ButtonGroup.render = function (opt_data, opt_ignored, opt_ijData) {
    var output = '<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="btn-group component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '">';
    var buttonList8 = opt_data.buttons;
    var buttonListLen8 = buttonList8.length;

    for (var buttonIndex8 = 0; buttonIndex8 < buttonListLen8; buttonIndex8++) {
      var buttonData8 = buttonList8[buttonIndex8];
      var type__soy9 = buttonData8.type ? buttonData8.type : 'button';
      var cssClass__soy10 = buttonData8.cssClass ? buttonData8.cssClass : 'btn btn-default';
      output += '<button type="' + soy.$$escapeHtmlAttribute(type__soy9) + '" class="' + soy.$$escapeHtmlAttribute(cssClass__soy10) + soy.$$escapeHtmlAttribute(Templates.ButtonGroup.selectedClass({
        label: buttonData8.label,
        selected: opt_data.selected
      }, null, opt_ijData)) + '" data-index="' + soy.$$escapeHtmlAttribute(buttonIndex8) + '" data-onclick="handleClick_"><span class="btn-group-label">' + soy.$$escapeHtml(buttonData8.label ? buttonData8.label : '') + '</span>' + (buttonData8.icon ? '<span class="' + soy.$$escapeHtmlAttribute(buttonData8.icon) + '"></span>' : '') + '</button>';
    }

    output += '</div>';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };

  if (goog.DEBUG) {
    Templates.ButtonGroup.render.soyTemplateName = 'Templates.ButtonGroup.render';
  }

  Templates.ButtonGroup.selectedClass = function (opt_data, opt_ignored, opt_ijData) {
    var output = '';

    if (opt_data.selected) {
      var selectedValueList34 = opt_data.selected;
      var selectedValueListLen34 = selectedValueList34.length;

      for (var selectedValueIndex34 = 0; selectedValueIndex34 < selectedValueListLen34; selectedValueIndex34++) {
        var selectedValueData34 = selectedValueList34[selectedValueIndex34];
        output += selectedValueData34 == opt_data.label ? ' btn-group-selected' : '';
      }
    }

    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };

  if (goog.DEBUG) {
    Templates.ButtonGroup.selectedClass.soyTemplateName = 'Templates.ButtonGroup.selectedClass';
  }

  Templates.ButtonGroup.render.params = ["buttons", "id"];
  Templates.ButtonGroup.selectedClass.private = true;

  var ButtonGroup = function (_Component) {
    _inherits(ButtonGroup, _Component);

    function ButtonGroup() {
      _classCallCheck(this, ButtonGroup);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ButtonGroup;
  }(_Component3.default);

  ButtonGroup.prototype.registerMetalComponent && ButtonGroup.prototype.registerMetalComponent(ButtonGroup, 'ButtonGroup')
  ButtonGroup.RENDERER = _SoyRenderer2.default;

  _SoyAop2.default.registerTemplates('ButtonGroup');

  exports.default = ButtonGroup;
});
//# sourceMappingURL=ButtonGroup.soy.js.map