define("frontend-js-metal-web@1.0.0/metal-select/src/Select", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', './Select.soy', 'metal-jquery-adapter/src/JQueryAdapter', 'metal-dropdown/src/Dropdown'], function (exports, _metal, _dom, _Select, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _dom2 = _interopRequireDefault(_dom);

	var _Select2 = _interopRequireDefault(_Select);

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

	var Select = function (_SelectBase) {
		_inherits(Select, _SelectBase);

		function Select() {
			_classCallCheck(this, Select);

			return _possibleConstructorReturn(this, _SelectBase.apply(this, arguments));
		}

		Select.prototype.findItemIndex_ = function findItemIndex_(element) {
			var items = this.element.querySelectorAll('li');
			for (var i = 0; i < items.length; i++) {
				if (items.item(i) === element) {
					return i;
				}
			}
		};

		Select.prototype.focusIndex_ = function focusIndex_(index) {
			var option = this.element.querySelector('.select-option:nth-child(' + (index + 1) + ') a');
			if (option) {
				this.focusedIndex_ = index;
				option.focus();
			}
		};

		Select.prototype.getDropdown = function getDropdown() {
			return this.components[this.id + '-dropdown'];
		};

		Select.prototype.handleDropdownAttrsSynced_ = function handleDropdownAttrsSynced_(data) {
			if (this.openedWithKeyboard_) {
				// This is done on `attrsSynced` because the items need to have already
				// been made visible before we try focusing them.
				this.focusIndex_(0);
				this.openedWithKeyboard_ = false;
			} else if (data.changes.expanded) {
				this.focusedIndex_ = null;
			}
		};

		Select.prototype.handleItemClick_ = function handleItemClick_(event) {
			this.selectedIndex = this.findItemIndex_(event.delegateTarget);
			this.getDropdown().close();
			event.preventDefault();
		};

		Select.prototype.handleKeyDown_ = function handleKeyDown_(event) {
			if (this.getDropdown().expanded) {
				switch (event.keyCode) {
					case 27:
						this.getDropdown().close();
						break;
					case 38:
						this.focusedIndex_ = _metal2.default.isDefAndNotNull(this.focusedIndex_) ? this.focusedIndex_ : 1;
						this.focusIndex_(this.focusedIndex_ === 0 ? this.items.length - 1 : this.focusedIndex_ - 1);
						event.preventDefault();
						break;
					case 40:
						this.focusedIndex_ = _metal2.default.isDefAndNotNull(this.focusedIndex_) ? this.focusedIndex_ : -1;
						this.focusIndex_(this.focusedIndex_ === this.items.length - 1 ? 0 : this.focusedIndex_ + 1);
						event.preventDefault();
						break;
				}
			} else if ((event.keyCode === 13 || event.keyCode === 32) && _dom2.default.hasClass(event.target, 'dropdown-select')) {
				this.openedWithKeyboard_ = true;
				this.getDropdown().open();
				event.preventDefault();
				return;
			}
		};

		return Select;
	}(_Select2.default);

	Select.prototype.registerMetalComponent && Select.prototype.registerMetalComponent(Select, 'Select')


	/**
  * Attributes definition.
  * @type {!Object}
  * @static
  */
	Select.ATTRS = {
		/**
   * The CSS class used by the select menu arrow.
   * @type {string}
   * @default 'caret'
   */
		arrowClass: {
			value: 'caret'
		},

		/**
   * The CSS class used by the select menu button.
   * @type {string}
   * @default 'btn btn-default'
   */
		buttonClass: {
			validator: _metal2.default.isString,
			value: 'btn btn-default'
		},

		/**
   * The name of the hidden input field
   * @type {string}
   */
		hiddenInputName: {
			validator: _metal2.default.isString
		},

		/**
   * A list representing the select dropdown items. Can be either already a list
   * of objects specifying both name and value for each item, or just a list of
   * names, in which case the values will be the indexes where the names show up
   * on the list.
   * @type {!Array<string>|!Array<!{name: string, value: string}>}
   * @default []
   */
		items: {
			validator: function validator(val) {
				return val instanceof Array;
			},
			valueFn: function valueFn() {
				return [];
			}
		},

		/**
   * The label that should be used for the select menu when no item is
   * selected. If not set, the first item will be selected automatically.
   * @type {string}
   */
		label: {
			validator: _metal2.default.isString
		},

		/**
   * The index of the currently selected item, or -1 if none is selected.
   * @type {number}
   */
		selectedIndex: {
			validator: _metal2.default.isNumber,
			valueFn: function valueFn() {
				return this.label || !this.items.length ? -1 : 0;
			}
		}
	};

	/**
  * Default element classes.
  * @type {string}
  * @static
  */
	Select.ELEMENT_CLASSES = 'select';

	exports.default = Select;
	_JQueryAdapter2.default.register('select', Select);
});
//# sourceMappingURL=Select.js.map