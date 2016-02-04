var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

define("frontend-js-metal-web@1.0.0/metal-list/src/List.soy", ['exports', 'metal/src/component/Component', 'metal/src/soy/SoyAop', 'metal/src/soy/SoyRenderer', 'metal/src/soy/SoyTemplates'], function (exports, _Component2, _SoyAop, _SoyRenderer, _SoyTemplates) {
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

  if (typeof Templates.List == 'undefined') {
    Templates.List = {};
  }

  Templates.List.render = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="list component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '">' + Templates.List.items(opt_data, null, opt_ijData) + '</div>');
  };

  if (goog.DEBUG) {
    Templates.List.render.soyTemplateName = 'Templates.List.render';
  }

  Templates.List.items = function (opt_data, opt_ignored, opt_ijData) {
    var output = '<ul id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-items" class="list-group" data-onclick="handleClick">';

    if (opt_data.itemsHtml != null) {
      output += soy.$$escapeHtml(opt_data.itemsHtml);
    } else {
      var itemList18 = opt_data.items;
      var itemListLen18 = itemList18.length;

      for (var itemIndex18 = 0; itemIndex18 < itemListLen18; itemIndex18++) {
        var itemData18 = itemList18[itemIndex18];
        output += Templates.ListItem.render({
          id: opt_data.id + '-items-' + itemIndex18,
          index: itemIndex18,
          item: itemData18
        }, null, opt_ijData);
      }
    }

    output += '</ul>';
    return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
  };

  if (goog.DEBUG) {
    Templates.List.items.soyTemplateName = 'Templates.List.items';
  }

  Templates.List.render.params = ["id"];
  Templates.List.items.params = ["id", "items", "itemsHtml"];

  var List = function (_Component) {
    _inherits(List, _Component);

    function List() {
      _classCallCheck(this, List);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return List;
  }(_Component3.default);

  List.prototype.registerMetalComponent && List.prototype.registerMetalComponent(List, 'List')
  List.RENDERER = _SoyRenderer2.default;

  _SoyAop2.default.registerTemplates('List');

  exports.default = List;
});
//# sourceMappingURL=List.soy.js.map