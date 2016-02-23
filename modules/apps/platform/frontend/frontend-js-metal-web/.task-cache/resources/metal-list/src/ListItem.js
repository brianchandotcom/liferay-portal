define("frontend-js-metal-web@1.0.0/metal-list/src/ListItem", ['exports', './ListItem.soy.js', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _ListItemSoy, _JQueryAdapter) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  var _ListItemSoy2 = _interopRequireDefault(_ListItemSoy);

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

  var ListItem = function (_ListItemBase) {
    _inherits(ListItem, _ListItemBase);

    function ListItem(opt_config) {
      _classCallCheck(this, ListItem);

      return _possibleConstructorReturn(this, _ListItemBase.call(this, opt_config));
    }

    return ListItem;
  }(_ListItemSoy2.default);

  ListItem.prototype.registerMetalComponent && ListItem.prototype.registerMetalComponent(ListItem, 'ListItem')


  /**
   * Default list elementClasses.
   * @default list
   * @type {String}
   * @static
   */
  ListItem.ELEMENT_CLASSES = 'listitem';

  /**
   * List attributes definition.
   * @type {Object}
   * @static
   */
  ListItem.ATTRS = {
    item: {},

    index: {
      value: -1
    }
  };

  exports.default = ListItem;
  _JQueryAdapter2.default.register('listItem', ListItem);
});
//# sourceMappingURL=ListItem.js.map