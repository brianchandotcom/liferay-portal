define("frontend-js-metal-web@1.0.0/metal-list/src/List", ['exports', 'metal-dom/src/all/dom', './List.soy.js', 'metal-jquery-adapter/src/JQueryAdapter', './ListItem.js'], function (exports, _dom, _ListSoy, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _ListSoy2 = _interopRequireDefault(_ListSoy);

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

	var List = function (_ListBase) {
		_inherits(List, _ListBase);

		/**
   * @inheritDoc
   */

		function List(opt_config) {
			_classCallCheck(this, List);

			return _possibleConstructorReturn(this, _ListBase.call(this, opt_config));
		}

		/**
   * Handles click event on the list. The function fires an
   * {@code itemSelected} event.
   * @param {!Event} event The native click event
   */


		List.prototype.handleClick = function handleClick(event) {
			var target = event.target;
			while (target) {
				if (_dom2.default.match(target, '.listitem')) {
					break;
				}
				target = target.parentNode;
			}
			this.emit('itemSelected', target);
		};

		return List;
	}(_ListSoy2.default);

	List.prototype.registerMetalComponent && List.prototype.registerMetalComponent(List, 'List')


	/**
  * Default list elementClasses.
  * @default list
  * @type {string}
  * @static
  */
	List.ELEMENT_CLASSES = 'list';

	/**
  * List attributes definition.
  * @type {!Object}
  * @static
  */
	List.ATTRS = {
		/**
   * The list items. Each is represented by an object that can have the following keys:
   *   - textPrimary: The item's main content.
   *   - textSecondary: (Optional) The item's help content.
   *   - icons: (Optional) A list of icon css classes to render on the right side.
   *   - iconsHtml: (Optional) A list of icon css classes to render on the right side.
   *   - avatar: (Optional) An object that specifies the avatar's content and, optionally, a css
   *       class it should use.
   * @type {!Array<!Object>}
   * @default []
   */
		items: {
			validator: Array.isArray,
			valueFn: function valueFn() {
				return [];
			}
		},

		/**
   * The list items as HTML to be added directly to the list.
   * @type {string}
   */
		itemsHtml: {}
	};

	exports.default = List;
	_JQueryAdapter2.default.register('list', List);
});
//# sourceMappingURL=List.js.map