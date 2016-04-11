define("frontend-js-metal-web@1.0.6/metal-list/src/ListItem", ['exports', 'metal/src/metal', 'metal-component/src/all/component', 'metal-soy/src/Soy', './ListItem.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _component, _Soy, _ListItem, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _component2 = _interopRequireDefault(_component);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _ListItem2 = _interopRequireDefault(_ListItem);

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

	var ListItem = function (_Component) {
		_inherits(ListItem, _Component);

		function ListItem() {
			_classCallCheck(this, ListItem);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		ListItem.prototype.setterItemFn_ = function setterItemFn_(item) {
			if (item.textPrimary && _metal2.default.isString(item.textPrimary)) {
				item.textPrimary = _Soy2.default.toIncDom(item.textPrimary);
			}
			if (item.textSecondary && _metal2.default.isString(item.textSecondary)) {
				item.textSecondary = _Soy2.default.toIncDom(item.textSecondary);
			}
			if (item.avatar && item.avatar.content && _metal2.default.isString(item.avatar.content)) {
				item.avatar.content = _Soy2.default.toIncDom(item.avatar.content);
			}
			if (Array.isArray(item.iconsHtml)) {
				item.iconsHtml = item.iconsHtml.map(_Soy2.default.toIncDom);
			}
			return item;
		};

		return ListItem;
	}(_component2.default);

	_Soy2.default.register(ListItem, _ListItem2.default);

	/**
  * List state definition.
  * @type {Object}
  * @static
  */
	ListItem.STATE = {
		/**
   * The item to be rendered.
   * @type {!Object}
   */
		item: {
			validator: _metal2.default.isObject,
			setter: 'setterItemFn_'
		},

		/**
   * The index of the item in the list.
   * @type {number}
   */
		index: {
			value: -1
		}
	};

	exports.default = ListItem;
	_JQueryAdapter2.default.register('listItem', ListItem);
});
//# sourceMappingURL=ListItem.js.map