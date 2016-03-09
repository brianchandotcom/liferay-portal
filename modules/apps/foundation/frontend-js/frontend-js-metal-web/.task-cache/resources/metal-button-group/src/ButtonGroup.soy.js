define("frontend-js-metal-web@1.0.0/metal-button-group/src/ButtonGroup.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/soy'], function (exports, _component, _soy) {
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
  // This file was automatically generated from ButtonGroup.soy.
  // Please don't edit this file by hand.

  /**
   * @fileoverview Templates in namespace Templates.ButtonGroup.
   */

  if (typeof Templates.ButtonGroup == 'undefined') {
    Templates.ButtonGroup = {};
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.ButtonGroup.render = function (opt_data, opt_ignored, opt_ijData) {
    var output = '<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="btn-group component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '">';
    var buttonList8 = opt_data.buttons;
    var buttonListLen8 = buttonList8.length;
    for (var buttonIndex8 = 0; buttonIndex8 < buttonListLen8; buttonIndex8++) {
      var buttonData8 = buttonList8[buttonIndex8];
      var type__soy9 = buttonData8.type ? buttonData8.type : 'button';
      var cssClass__soy10 = buttonData8.cssClass ? buttonData8.cssClass : 'btn btn-default';
      output += '<button type="' + soy.$$escapeHtmlAttribute(type__soy9) + '" class="' + soy.$$escapeHtmlAttribute(cssClass__soy10) + soy.$$escapeHtmlAttribute(Templates.ButtonGroup.selectedClass({ label: buttonData8.label, selected: opt_data.selected }, null, opt_ijData)) + '" data-index="' + soy.$$escapeHtmlAttribute(buttonIndex8) + '" data-onclick="handleClick_"><span class="btn-group-label">' + soy.$$escapeHtml(buttonData8.label ? buttonData8.label : '') + '</span>' + (buttonData8.icon ? '<span class="' + soy.$$escapeHtmlAttribute(buttonData8.icon) + '"></span>' : '') + '</button>';
    }
    output += '</div>';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };
  if (goog.DEBUG) {
    Templates.ButtonGroup.render.soyTemplateName = 'Templates.ButtonGroup.render';
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
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
  }(_component2.default);

  ButtonGroup.prototype.registerMetalComponent && ButtonGroup.prototype.registerMetalComponent(ButtonGroup, 'ButtonGroup')

  ButtonGroup.RENDERER = _soy.SoyRenderer;
  _soy.SoyAop.registerTemplates('ButtonGroup');
  exports.default = ButtonGroup;
});
//# sourceMappingURL=ButtonGroup.soy.js.map