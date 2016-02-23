define("frontend-js-metal-web@1.0.0/metal-list/src/List.soy", ['exports', 'metal-component/src/all/component', 'metal-soy/src/soy'], function (exports, _component, _soy) {
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
  // This file was automatically generated from List.soy.
  // Please don't edit this file by hand.

  /**
   * @fileoverview Templates in namespace Templates.List.
   */

  if (typeof Templates.List == 'undefined') {
    Templates.List = {};
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.List.render = function (opt_data, opt_ignored, opt_ijData) {
    return soydata.VERY_UNSAFE.ordainSanitizedHtml('<div id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '" class="list component' + soy.$$escapeHtmlAttribute(opt_data.elementClasses ? ' ' + opt_data.elementClasses : '') + '">' + Templates.List.items(opt_data, null, opt_ijData) + '</div>');
  };
  if (goog.DEBUG) {
    Templates.List.render.soyTemplateName = 'Templates.List.render';
  }

  /**
   * @param {Object.<string, *>=} opt_data
   * @param {(null|undefined)=} opt_ignored
   * @param {Object.<string, *>=} opt_ijData
   * @return {!soydata.SanitizedHtml}
   * @suppress {checkTypes}
   */
  Templates.List.items = function (opt_data, opt_ignored, opt_ijData) {
    var output = '<ul id="' + soy.$$escapeHtmlAttribute(opt_data.id) + '-items" class="list-group" data-onclick="handleClick">';
    if (opt_data.itemsHtml != null) {
      output += soy.$$escapeHtml(opt_data.itemsHtml);
    } else {
      var itemList18 = opt_data.items;
      var itemListLen18 = itemList18.length;
      for (var itemIndex18 = 0; itemIndex18 < itemListLen18; itemIndex18++) {
        var itemData18 = itemList18[itemIndex18];
        output += Templates.ListItem.render({ id: opt_data.id + '-items-' + itemIndex18, index: itemIndex18, item: itemData18 }, null, opt_ijData);
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
  }(_component2.default);

  List.prototype.registerMetalComponent && List.prototype.registerMetalComponent(List, 'List')

  List.RENDERER = _soy.SoyRenderer;
  _soy.SoyAop.registerTemplates('List');
  exports.default = List;
});
//# sourceMappingURL=List.soy.js.map