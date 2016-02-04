var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

define("frontend-js-metal-web@1.0.0/metal-switcher/src/Switcher", ['exports', 'metal/src/core', 'metal/src/dom/dom', './Switcher.soy.js', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _core, _dom, _SwitcherSoy, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _SwitcherSoy2 = _interopRequireDefault(_SwitcherSoy);

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

	var Switcher = function (_SwitcherBase) {
		_inherits(Switcher, _SwitcherBase);

		function Switcher() {
			_classCallCheck(this, Switcher);

			return _possibleConstructorReturn(this, _SwitcherBase.apply(this, arguments));
		}

		Switcher.prototype.attached = function attached() {
			this.on('click', this.handleClick);
		};

		Switcher.prototype.handleClick = function handleClick() {
			this.checked = !this.checked;
		};

		Switcher.prototype.syncChecked = function syncChecked(checked) {
			_dom2.default[checked ? 'addClasses' : 'removeClasses'](this.element, 'switcher-on');
		};

		return Switcher;
	}(_SwitcherSoy2.default);

	Switcher.prototype.registerMetalComponent && Switcher.prototype.registerMetalComponent(Switcher, 'Switcher')
	Switcher.ELEMENT_CLASSES = 'switcher';
	Switcher.ATTRS = {
		checked: {
			validator: _core2.default.isBoolean,
			value: false
		}
	};
	exports.default = Switcher;

	_JQueryAdapter2.default.register('switcher', Switcher);
});
//# sourceMappingURL=Switcher.js.map