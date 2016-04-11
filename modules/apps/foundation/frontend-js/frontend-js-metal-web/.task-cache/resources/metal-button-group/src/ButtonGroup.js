define("frontend-js-metal-web@1.0.6/metal-button-group/src/ButtonGroup", ['exports', 'metal/src/metal', 'metal-component/src/all/component', 'metal-soy/src/Soy', './ButtonGroup.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _component, _Soy, _ButtonGroup, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _component2 = _interopRequireDefault(_component);

	var _Soy2 = _interopRequireDefault(_Soy);

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

	var ButtonGroup = function (_Component) {
		_inherits(ButtonGroup, _Component);

		function ButtonGroup() {
			_classCallCheck(this, ButtonGroup);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

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

		return ButtonGroup;
	}(_component2.default);

	_Soy2.default.register(ButtonGroup, _ButtonGroup2.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	ButtonGroup.STATE = {
		/**
   * Configuration for the buttons that should be rendered in this group.
   * Each button config should be given as an object. Supported options are:
   * label, type and cssClass.
   * @type {!Array<!Object>}
   * @default []
   */
		buttons: {
			validator: function validator(val) {
				return val instanceof Array;
			},
			valueFn: function valueFn() {
				return [];
			}
		},

		/**
   * The minimum number of buttons that need to be selected at a time. If the
   * minimum number of buttons is not already initially selected, this will
   * automaticaly select the first `minSelected` buttons.
   * @type {number}
   * @default 0
   */
		minSelected: {
			validator: _metal2.default.isNumber,
			value: 0,
			writeOnce: true
		},

		/**
   * An array with the labels of the buttons that should be selected.
   * @type {!Array<string>}
   */
		selected: {
			setter: 'setterSelectedFn_',
			validator: Array.isArray,
			valueFn: function valueFn() {
				return [];
			}
		}
	};

	/**
  * The CSS class added to selected buttons.
  * @type {string}
  * @static
  */
	ButtonGroup.SELECTED_CLASS = 'btn-group-selected';

	exports.default = ButtonGroup;
	_JQueryAdapter2.default.register('buttonGroup', ButtonGroup);
});
//# sourceMappingURL=ButtonGroup.js.map