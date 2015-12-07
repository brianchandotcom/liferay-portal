'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-popover/src/Popover", ['exports', 'metal/src/core', 'crystal-tooltip/src/TooltipBase', 'metal-jquery-adapter/src/JQueryAdapter', 'crystal-popover/src/Popover.soy'], function (exports, _core, _TooltipBase2, _JQueryAdapter) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _TooltipBase3 = _interopRequireDefault(_TooltipBase2);

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

	var Popover = (function (_TooltipBase) {
		_inherits(Popover, _TooltipBase);

		function Popover() {
			_classCallCheck(this, Popover);

			return _possibleConstructorReturn(this, _TooltipBase.apply(this, arguments));
		}

		Popover.prototype.syncVisible = function syncVisible(visible) {
			this.element.style.display = visible ? 'block' : '';

			_TooltipBase.prototype.syncVisible.call(this, visible);
		};

		return Popover;
	})(_TooltipBase3.default);

	Popover.prototype.registerMetalComponent && Popover.prototype.registerMetalComponent(Popover, 'Popover')
	Popover.ATTRS = {
		title: {
			validator: _core2.default.isString
		},
		triggerEvents: {
			validator: Array.isArray,
			value: ['click', 'click']
		}
	};
	Popover.Align = _TooltipBase3.default.Align;
	Popover.ELEMENT_CLASSES = 'popover';
	exports.default = Popover;

	_JQueryAdapter2.default.register('popover', Popover);
});
//# sourceMappingURL=Popover.js.map