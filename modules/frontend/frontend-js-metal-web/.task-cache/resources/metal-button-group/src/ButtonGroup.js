var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

define("frontend-js-metal-web@1.0.0/metal-button-group/src/ButtonGroup", ['exports', 'metal/src/core', 'metal/src/dom/dom', './ButtonGroup.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _core, _dom, _ButtonGroup, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _ButtonGroup2 = _interopRequireDefault(_ButtonGroup);

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

	var ButtonGroup = function (_ButtonGroupBase) {
		_inherits(ButtonGroup, _ButtonGroupBase);

		function ButtonGroup(opt_config) {
			_classCallCheck(this, ButtonGroup);

			var _this = _possibleConstructorReturn(this, _ButtonGroupBase.call(this, opt_config));

			_this.buttonElements_ = null;

			_this.on('selectedChanged', _this.defaultSelectedChanged_, true);

			return _this;
		}

		ButtonGroup.prototype.defaultSelectedChanged_ = function defaultSelectedChanged_(event) {
			for (var i = 0; i < this.buttonElements_.length; i++) {
				if (event.newVal.indexOf(this.buttons[i].label) !== -1) {
					_dom2.default.addClasses(this.buttonElements_[i], ButtonGroup.SELECTED_CLASS);
				} else {
					_dom2.default.removeClasses(this.buttonElements_[i], ButtonGroup.SELECTED_CLASS);
				}
			}
		};

		ButtonGroup.prototype.handleClick_ = function handleClick_(event) {
			var button = event.delegateTarget;
			var index = button.getAttribute('data-index');
			var selectedIndex = this.selected.indexOf(this.buttons[index].label);

			if (selectedIndex === -1) {
				this.selected.push(this.buttons[index].label);
				this.selected = this.selected;
			} else if (this.selected.length > this.minSelected) {
				this.selected.splice(selectedIndex, 1);
				this.selected = this.selected;
			}
		};

		ButtonGroup.prototype.setterSelectedFn_ = function setterSelectedFn_(selected) {
			var minSelected = Math.min(this.minSelected, this.buttons.length);
			var i = 0;

			while (selected.length < minSelected) {
				if (selected.indexOf(this.buttons[i].label) === -1) {
					selected.push(this.buttons[i].label);
				}

				i++;
			}

			return selected;
		};

		ButtonGroup.prototype.syncButtons = function syncButtons() {
			this.buttonElements_ = this.element.querySelectorAll('button');
		};

		return ButtonGroup;
	}(_ButtonGroup2.default);

	ButtonGroup.prototype.registerMetalComponent && ButtonGroup.prototype.registerMetalComponent(ButtonGroup, 'ButtonGroup')
	ButtonGroup.ATTRS = {
		buttons: {
			validator: function validator(val) {
				return val instanceof Array;
			},
			valueFn: function valueFn() {
				return [];
			}
		},
		minSelected: {
			validator: _core2.default.isNumber,
			value: 0,
			writeOnce: true
		},
		selected: {
			setter: 'setterSelectedFn_',
			validator: Array.isArray,
			valueFn: function valueFn() {
				return [];
			}
		}
	};
	ButtonGroup.ELEMENT_CLASSES = 'btn-group';
	ButtonGroup.SELECTED_CLASS = 'btn-group-selected';
	exports.default = ButtonGroup;

	_JQueryAdapter2.default.register('buttonGroup', ButtonGroup);
});
//# sourceMappingURL=ButtonGroup.js.map