define("frontend-js-metal-web@1.0.6/metal-switcher/src/Switcher", ['exports', 'metal/src/metal', './Switcher.soy', 'metal-component/src/all/component', 'metal-soy/src/Soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _Switcher, _component, _Soy, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _Switcher2 = _interopRequireDefault(_Switcher);

	var _component2 = _interopRequireDefault(_component);

	var _Soy2 = _interopRequireDefault(_Soy);

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

	var Switcher = function (_Component) {
		_inherits(Switcher, _Component);

		function Switcher() {
			_classCallCheck(this, Switcher);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		Switcher.prototype.handleClick = function handleClick() {
			this.checked = !this.checked;
		};

		return Switcher;
	}(_component2.default);

	/**
  * Switcher state definition.
  * @type {!Object}
  * @static
  */
	Switcher.STATE = {
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
	_Soy2.default.register(Switcher, _Switcher2.default);

	exports.default = Switcher;
	_JQueryAdapter2.default.register('switcher', Switcher);
});
//# sourceMappingURL=Switcher.js.map