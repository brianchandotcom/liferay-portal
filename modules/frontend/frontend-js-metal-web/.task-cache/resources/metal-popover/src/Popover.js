define("frontend-js-metal-web@1.0.0/metal-popover/src/Popover", ['exports', 'metal/src/metal', 'metal-tooltip/src/Tooltip', 'metal-jquery-adapter/src/JQueryAdapter', './Popover.soy'], function (exports, _metal, _Tooltip, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

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

	var Popover = function (_TooltipBase) {
		_inherits(Popover, _TooltipBase);

		function Popover() {
			_classCallCheck(this, Popover);

			return _possibleConstructorReturn(this, _TooltipBase.apply(this, arguments));
		}

		Popover.prototype.syncAlignElement = function syncAlignElement(alignElement) {
			_TooltipBase.prototype.syncAlignElement.call(this, alignElement);

			if (alignElement) {
				var dataContent = alignElement.getAttribute('data-content');
				if (dataContent) {
					this.content = dataContent;
				}
			}
		};

		Popover.prototype.syncVisible = function syncVisible(visible) {
			this.element.style.display = visible ? 'block' : '';
			_TooltipBase.prototype.syncVisible.call(this, visible);
		};

		return Popover;
	}(_Tooltip.TooltipBase);

	Popover.prototype.registerMetalComponent && Popover.prototype.registerMetalComponent(Popover, 'Popover')


	/**
  * Attributes definition.
  * @type {!Object}
  * @static
  */
	Popover.ATTRS = {
		content: {
			validator: _metal2.default.isString
		},

		/**
   * Trigger events used to bind handlers to show and hide popover.
   * @type {!Array<string>}
   * @default ['click', 'click']
   */
		triggerEvents: {
			validator: Array.isArray,
			value: ['click', 'click']
		}
	};

	/**
  * @inheritDoc
  * @see `Align` class.
  * @static
  */
	Popover.Align = _Tooltip.TooltipBase.Align;

	Popover.ELEMENT_CLASSES = 'popover';

	exports.default = Popover;
	_JQueryAdapter2.default.register('popover', Popover);
});
//# sourceMappingURL=Popover.js.map