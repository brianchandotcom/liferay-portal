define("frontend-js-metal-web@1.0.0/metal-dropdown/src/Dropdown", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-position/src/all/position', './Dropdown.soy', 'metal-events/src/events', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _position, _Dropdown, _events, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _dom2 = _interopRequireDefault(_dom);

	var _Dropdown2 = _interopRequireDefault(_Dropdown);

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

	var Dropdown = function (_DropdownBase) {
		_inherits(Dropdown, _DropdownBase);

		/**
   * @inheritDoc
   */

		function Dropdown(opt_config) {
			_classCallCheck(this, Dropdown);

			var _this = _possibleConstructorReturn(this, _DropdownBase.call(this, opt_config));

			_this.eventHandler_ = new _events.EventHandler();
			return _this;
		}

		/**
   * @inheritDoc
   */


		Dropdown.prototype.attached = function attached() {
			_DropdownBase.prototype.attached.call(this);
			this.eventHandler_.add(_dom2.default.on(document, 'click', this.handleDocClick_.bind(this)));
		};

		Dropdown.prototype.detached = function detached() {
			_DropdownBase.prototype.detached.call(this);
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

		Dropdown.prototype.syncExpanded = function syncExpanded(expanded) {
			if (expanded) {
				_dom2.default.addClasses(this.element, 'open');
				if (this.alignElementSelector) {
					var alignElement = this.element.querySelector(this.alignElementSelector);
					if (alignElement) {
						_position.Align.align(this.getSurfaceElement('body'), alignElement, Dropdown.POSITION_MAP[this.position]);
					}
				}
			} else {
				_dom2.default.removeClasses(this.element, 'open');
			}
		};

		Dropdown.prototype.syncPosition = function syncPosition(position, oldPosition) {
			if (oldPosition) {
				_dom2.default.removeClasses(this.element, 'drop' + oldPosition.toLowerCase());
			}
			_dom2.default.addClasses(this.element, 'drop' + position.toLowerCase());
		};

		Dropdown.prototype.toggle = function toggle() {
			this.expanded = !this.expanded;
		};

		Dropdown.prototype.validatePosition_ = function validatePosition_(position) {
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
	}(_Dropdown2.default);

	Dropdown.prototype.registerMetalComponent && Dropdown.prototype.registerMetalComponent(Dropdown, 'Dropdown')


	/**
  * Attrbutes definition.
  * @type {!Object}
  * @static
  */
	Dropdown.ATTRS = {
		/**
   * Optional selector for finding the element that the dropdown should be
   * aligned to. If given, the dropdown will automatically find the best position
   * to align, when the specified position doesn't work. Otherwise it will
   * always just follow the given position, even if it's not ideal.
   * @type {string}
   */
		alignElementSelector: {
			validator: _metal2.default.isString
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
   * The position of the dropdown (either 'up' or 'down').
   * @type {string}
   * @default 'down'
   */
		position: {
			value: 'down',
			validator: 'validatePosition_'
		}
	};

	/**
  * Default dropdown elementClasses.
  * @default dropdown
  * @type {string}
  * @static
  */
	Dropdown.ELEMENT_CLASSES = 'dropdown';

	/**
  * A map from the dropdown supported positions to `Align` positions.
  */
	Dropdown.POSITION_MAP = {
		down: _position.Align.BottomLeft,
		up: _position.Align.TopLeft
	};

	exports.default = Dropdown;
	_JQueryAdapter2.default.register('dropdown', Dropdown);
});
//# sourceMappingURL=Dropdown.js.map