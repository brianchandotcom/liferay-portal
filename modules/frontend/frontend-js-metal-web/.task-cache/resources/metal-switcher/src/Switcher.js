define("frontend-js-metal-web@1.0.0/metal-switcher/src/Switcher", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', './Switcher.soy.js', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _SwitcherSoy, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

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


	/**
  * Default switcher elementClasses.
  * @default list
  * @type {string}
  * @static
  */
	Switcher.ELEMENT_CLASSES = 'switcher';

	/**
  * Switcher attributes definition.
  * @type {!Object}
  * @static
  */
	Switcher.ATTRS = {
		/**
   * Flag indicating if the switcher is currently checked or not.
   * @type {boolean}
   * @default false
   */
		checked: {
			validator: _metal2.default.isBoolean,
			value: false
		}
	};

	exports.default = Switcher;
	_JQueryAdapter2.default.register('switcher', Switcher);
});
//# sourceMappingURL=Switcher.js.map