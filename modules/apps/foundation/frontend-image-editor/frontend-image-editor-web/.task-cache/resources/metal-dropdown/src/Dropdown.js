define(['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-position/src/all/position', 'metal-component/src/all/component', 'metal-events/src/events', 'metal-soy/src/Soy', './Dropdown.soy.js'], function (exports, _metal, _dom, _position, _component, _events, _Soy, _DropdownSoy) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _component2 = _interopRequireDefault(_component);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _DropdownSoy2 = _interopRequireDefault(_DropdownSoy);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	function _defineProperty(obj, key, value) {
		if (key in obj) {
			Object.defineProperty(obj, key, {
				value: value,
				enumerable: true,
				configurable: true,
				writable: true
			});
		} else {
			obj[key] = value;
		}

		return obj;
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

	var Dropdown = function (_Component) {
		_inherits(Dropdown, _Component);

		function Dropdown() {
			_classCallCheck(this, Dropdown);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		Dropdown.prototype.attached = function attached() {
			_Component.prototype.attached.call(this);
			this.eventHandler_.add(_dom2.default.on(document, 'click', this.handleDocClick_.bind(this)));
		};

		Dropdown.prototype.created = function created() {
			this.eventHandler_ = new _events.EventHandler();
		};

		Dropdown.prototype.detached = function detached() {
			_Component.prototype.detached.call(this);
			this.eventHandler_.removeAllListeners();
		};

		Dropdown.prototype.close = function close() {
			this.expanded = false;
		};

		Dropdown.prototype.isOpen = function isOpen() {
			return this.expanded;
		};

		Dropdown.prototype.handleDocClick_ = function handleDocClick_(event) {
			if (this.element.contains(event.target)) {
				return;
			}
			this.close();
		};

		Dropdown.prototype.open = function open() {
			this.expanded = true;
		};

		Dropdown.prototype.setterClassMapFn_ = function setterClassMapFn_(val) {
			return _metal.object.mixin(this.valueClassMapFn_(), val);
		};

		Dropdown.prototype.setterPositionFn_ = function setterPositionFn_(val) {
			if (_metal.core.isNumber(val)) {
				return val;
			}
			return val.toLowerCase() === 'up' ? _position.Align.TopLeft : _position.Align.BottomLeft;
		};

		Dropdown.prototype.syncExpanded = function syncExpanded(expanded) {
			if (expanded && this.alignElementSelector) {
				var alignElement = this.element.querySelector(this.alignElementSelector);
				if (alignElement) {
					var bodyElement = this.element.querySelector('.dropdown-menu');
					this.alignedPosition = _position.Align.align(bodyElement, alignElement, this.position);
				}
			}
		};

		Dropdown.prototype.toggle = function toggle() {
			this.expanded = !this.expanded;
		};

		Dropdown.prototype.validatePosition_ = function validatePosition_(position) {
			if (_position.Align.isValidPosition(position)) {
				return true;
			}
			switch (position.toLowerCase()) {
				case 'up':
				case 'down':
					return true;
				default:
					return false;
			}
		};

		Dropdown.prototype.valueBodyFn_ = function valueBodyFn_() {
			var dropdownMenu = this.element && this.element.querySelector('.dropdown-menu');
			return dropdownMenu ? dropdownMenu.innerHTML : '';
		};

		Dropdown.prototype.valueClassMapFn_ = function valueClassMapFn_() {
			var _ref;

			return _ref = {}, _defineProperty(_ref, _position.Align.TopLeft, 'dropup'), _defineProperty(_ref, _position.Align.TopCenter, 'dropup'), _defineProperty(_ref, _position.Align.TopRight, 'dropup'), _defineProperty(_ref, _position.Align.BottomLeft, 'dropdown'), _defineProperty(_ref, _position.Align.BottomCenter, 'dropdown'), _defineProperty(_ref, _position.Align.BottomRight, 'dropdown'), _defineProperty(_ref, _position.Align.RightCenter, 'dropright'), _defineProperty(_ref, _position.Align.LeftCenter, 'dropleft'), _ref;
		};

		Dropdown.prototype.valueHeaderFn_ = function valueHeaderFn_() {
			if (this.element) {
				var wrapper = document.createElement('div');
				for (var i = 0; i < this.element.childNodes.length; i++) {
					if (_dom2.default.hasClass(this.element.childNodes[i], 'dropdown-menu')) {
						break;
					}
					wrapper.appendChild(this.element.childNodes[i].cloneNode(true));
				}
				return wrapper.innerHTML;
			}
			return '';
		};

		return Dropdown;
	}(_component2.default);

	_Soy2.default.register(Dropdown, _DropdownSoy2.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	Dropdown.STATE = {
		/**
   * The current position of the tooltip after being aligned via `Align.align`.
   * @type {number}
   */
		alignedPosition: {
			validator: _position.Align.isValidPosition
		},

		/**
   * Optional selector for finding the element that the dropdown should be
   * aligned to. If given, the dropdown will automatically find the best position
   * to align, when the specified position doesn't work. Otherwise it will
   * always just follow the given position, even if it's not ideal.
   * @type {string}
   */
		alignElementSelector: {
			validator: _metal.core.isString
		},

		/**
   * The dropdown's body content.
   * @type {string}
   */
		body: {
			isHtml: true,
			valueFn: 'valueBodyFn_'
		},

		/**
   * A map from `Align` position constants to the CSS class that should be
   * added to the dropdown when it's aligned in that position.
   * @type {!Object}
   */
		classMap: {
			setter: 'setterClassMapFn_',
			validator: _metal.core.isObject,
			valueFn: 'valueClassMapFn_'
		},

		/**
   * The dropdown's header content.
   * @type {string}
   */
		header: {
			isHtml: true,
			valueFn: 'valueHeaderFn_'
		},

		/**
   * Flag indicating if the dropdown is expanded (open) or not.
   * @type {boolean}
   * @default false
   */
		expanded: {
			value: false
		},

		/**
   * The position of the dropdown (either 'up', 'down' or any of the position
   * constants available in `Align`).
   * @type {string|number}
   * @default Align.BottomLeft
   */
		position: {
			setter: 'setterPositionFn_',
			value: _position.Align.BottomLeft,
			validator: 'validatePosition_'
		},

		/**
   * Flag indicating if the position class (specified by `classMap` state)
   * should be added on the "dropdown-menu" element, instead of the main element.
   * @type {boolean}
   */
		positionClassOnMenu: {
			value: false
		}
	};

	exports.default = Dropdown;
});
//# sourceMappingURL=Dropdown.js.map