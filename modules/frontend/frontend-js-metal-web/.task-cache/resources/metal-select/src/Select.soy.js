var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

define("frontend-js-metal-web@1.0.0/metal-select/src/Select.soy", ['exports', 'metal/src/component/Component', 'metal/src/soy/SoyAop', 'metal/src/soy/SoyRenderer', 'metal/src/soy/SoyTemplates'], function (exports, _Component2, _SoyAop, _SoyRenderer, _SoyTemplates) {
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

  if (typeof Templates.Select == 'undefined') {
    Templates.Select = {};
  }

  Templates.Select.render = function (opt_data, opt_ignored, opt_ijData) {
    var output = '<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="select component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '" data-onkeydown="handleKeyDown_">';
    var currSelectedIndex__soy8 = opt_data.selectedIndex != null ? opt_data.selectedIndex : opt_data.label || opt_data.items.length == 0 ? -1 : 0;
    output += '<input type="hidden" name="' + soy.$$escapeHtmlAttribute(opt_data.hiddenInputName ? opt_data.hiddenInputName : '') + '" value="' + soy.$$escapeHtmlAttribute(currSelectedIndex__soy8 == -1 ? '' : opt_data.items[currSelectedIndex__soy8]) + '" />';
    var param14 = '';
    var itemList15 = opt_data.items;
    var itemListLen15 = itemList15.length;

    for (var itemIndex15 = 0; itemIndex15 < itemListLen15; itemIndex15++) {
      var itemData15 = itemList15[itemIndex15];
      param14 += '<li data-onclick="' + soy.$$escapeHtmlAttribute(opt_data.id) + ':handleItemClick_" class="select-option' + soy.$$escapeHtmlAttribute(currSelectedIndex__soy8 == itemIndex15 ? ' selected' : '') + '"><a href="javascript:;">' + soy.$$escapeHtml(itemData15) + '</a></li>';
    }

    output += soy.$$escapeHtml(Templates.Dropdown.render({
      body: soydata.VERY_UNSAFE.$$ordainSanitizedHtmlForInternalBlocks(param14),
      events: {
        attrsSynced: opt_data.id + ':handleDropdownAttrsSynced_'
      },
      header: soydata.VERY_UNSAFE.$$ordainSanitizedHtmlForInternalBlocks('<button class="' + soy.$$escapeHtmlAttribute(opt_data.buttonClass) + ' dropdown-select" type="button" data-onclick="toggle">' + soy.$$escapeHtml(currSelectedIndex__soy8 == -1 ? opt_data.label : opt_data.items[currSelectedIndex__soy8]) + ' <span class="' + soy.$$escapeHtmlAttribute(opt_data.arrowClass ? opt_data.arrowClass : 'caret') + '"></span></button>'),
      id: opt_data.id + '-dropdown'
    }, null, opt_ijData));
    output += '</div>';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };

  if (goog.DEBUG) {
    Templates.Select.render.soyTemplateName = 'Templates.Select.render';
  }

  Templates.Select.render.params = ["arrowClass", "buttonClass", "hiddenInputName", "id", "items", "label", "selectedIndex"];

  var Select = function (_Component) {
    _inherits(Select, _Component);

    function Select() {
      _classCallCheck(this, Select);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return Select;
  }(_Component3.default);

  Select.prototype.registerMetalComponent && Select.prototype.registerMetalComponent(Select, 'Select')
  Select.RENDERER = _SoyRenderer2.default;

  _SoyAop2.default.registerTemplates('Select');

  exports.default = Select;
});
//# sourceMappingURL=Select.soy.js.map