'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-dropdown/src/Dropdown", ['exports', 'metal/src/dom/dom', 'metal/src/events/EventHandler', 'crystal-dropdown/src/Dropdown.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _dom, _EventHandler, _Dropdown, _JQueryAdapter) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _EventHandler2 = _interopRequireDefault(_EventHandler);

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

	var Dropdown = (function (_DropdownBase) {
		_inherits(Dropdown, _DropdownBase);

		function Dropdown(opt_config) {
			_classCallCheck(this, Dropdown);

			var _this = _possibleConstructorReturn(this, _DropdownBase.call(this, opt_config));

			_this.eventHandler_ = new _EventHandler2.default();
			return _this;
		}

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

		return Dropdown;
	})(_Dropdown2.default);

	Dropdown.prototype.registerMetalComponent && Dropdown.prototype.registerMetalComponent(Dropdown, 'Dropdown')
	Dropdown.ATTRS = {
		body: {},
		header: {},
		expanded: {
			value: false
		},
		position: {
			value: 'down',
			validator: 'validatePosition_'
		}
	};
	Dropdown.ELEMENT_CLASSES = 'dropdown';
	exports.default = Dropdown;

	_JQueryAdapter2.default.register('dropdown', Dropdown);
});
//# sourceMappingURL=Dropdown.js.map