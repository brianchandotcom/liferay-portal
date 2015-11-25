'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-tooltip/src/TooltipBase", ['exports', 'metal/src/core', 'metal/src/dom/dom', 'metal-position/src/Align', 'metal/src/component/Component', 'metal/src/events/EventHandler', 'metal/src/soy/SoyRenderer', 'metal-jquery-adapter/src/JQueryAdapter', 'metal/src/dom/events'], function (exports, _core, _dom, _Align, _Component2, _EventHandler, _SoyRenderer, _JQueryAdapter) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _Align2 = _interopRequireDefault(_Align);

	var _Component3 = _interopRequireDefault(_Component2);

	var _EventHandler2 = _interopRequireDefault(_EventHandler);

	var _SoyRenderer2 = _interopRequireDefault(_SoyRenderer);

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

	var TooltipBase = (function (_Component) {
		_inherits(TooltipBase, _Component);

		function TooltipBase(opt_config) {
			_classCallCheck(this, TooltipBase);

			var _this = _possibleConstructorReturn(this, _Component.call(this, opt_config));

			_this.eventHandler_ = new _EventHandler2.default();
			return _this;
		}

		TooltipBase.prototype.attached = function attached() {
			this.align();
			this.syncTriggerEvents(this.triggerEvents);
		};

		TooltipBase.prototype.detached = function detached() {
			this.eventHandler_.removeAllListeners();
		};

		TooltipBase.prototype.align = function align(opt_alignElement) {
			this.syncAlignElement(opt_alignElement || this.alignElement);
		};

		TooltipBase.prototype.callAsync_ = function callAsync_(fn, delay) {
			clearTimeout(this.delay_);
			this.delay_ = setTimeout(fn.bind(this), delay);
		};

		TooltipBase.prototype.handleHide = function handleHide(event) {
			var delegateTarget = event.delegateTarget;
			var interactingWithDifferentTarget = delegateTarget && delegateTarget !== this.alignElement;
			this.callAsync_(function () {
				if (this.locked_) {
					return;
				}

				if (interactingWithDifferentTarget) {
					this.alignElement = delegateTarget;
				} else {
					this.visible = false;
					this.syncVisible(false);
				}
			}, this.delay[1]);
		};

		TooltipBase.prototype.handleShow = function handleShow(event) {
			var delegateTarget = event.delegateTarget;

			_Component.prototype.syncVisible.call(this, true);

			this.callAsync_(function () {
				this.alignElement = delegateTarget;
				this.visible = true;
			}, this.delay[0]);
		};

		TooltipBase.prototype.handleToggle = function handleToggle(event) {
			if (this.visible) {
				this.handleHide(event);
			} else {
				this.handleShow(event);
			}
		};

		TooltipBase.prototype.lock = function lock() {
			this.locked_ = true;
		};

		TooltipBase.prototype.unlock = function unlock(event) {
			this.locked_ = false;
			this.handleHide(event);
		};

		TooltipBase.prototype.syncAlignElement = function syncAlignElement(alignElement, prevAlignElement) {
			if (prevAlignElement) {
				alignElement.removeAttribute('aria-describedby');
			}

			if (alignElement) {
				if (this.visible) {
					alignElement.setAttribute('aria-describedby', this.id);
				} else {
					alignElement.removeAttribute('aria-describedby');
				}

				if (this.inDocument) {
					var finalPosition = TooltipBase.Align.align(this.element, alignElement, this.position);
					this.updatePositionCSS(finalPosition);
				}
			}
		};

		TooltipBase.prototype.syncPosition = function syncPosition() {
			this.syncAlignElement(this.alignElement);
		};

		TooltipBase.prototype.syncSelector = function syncSelector() {
			this.syncTriggerEvents(this.triggerEvents);
		};

		TooltipBase.prototype.syncTriggerEvents = function syncTriggerEvents(triggerEvents) {
			if (!this.inDocument) {
				return;
			}

			this.eventHandler_.removeAllListeners();
			var selector = this.selector;

			if (!selector) {
				return;
			}

			this.eventHandler_.add(this.on('mouseenter', this.lock), this.on('mouseleave', this.unlock));

			if (triggerEvents[0] === triggerEvents[1]) {
				this.eventHandler_.add(_dom2.default.delegate(document, triggerEvents[0], selector, this.handleToggle.bind(this)));
			} else {
				this.eventHandler_.add(_dom2.default.delegate(document, triggerEvents[0], selector, this.handleShow.bind(this)), _dom2.default.delegate(document, triggerEvents[1], selector, this.handleHide.bind(this)));
			}
		};

		TooltipBase.prototype.syncVisible = function syncVisible() {
			this.align();
		};

		TooltipBase.prototype.updatePositionCSS = function updatePositionCSS(position) {
			_dom2.default.removeClasses(this.element, TooltipBase.PositionClasses.join(' '));

			_dom2.default.addClasses(this.element, TooltipBase.PositionClasses[position]);
		};

		return TooltipBase;
	})(_Component3.default);

	TooltipBase.Align = _Align2.default;
	TooltipBase.ATTRS = {
		alignElement: {
			setter: _dom2.default.toElement
		},
		delay: {
			validator: Array.isArray,
			value: [500, 250]
		},
		triggerEvents: {
			validator: Array.isArray,
			value: ['mouseenter', 'mouseleave']
		},
		selector: {
			validator: _core2.default.isString
		},
		content: {},
		position: {
			validator: TooltipBase.Align.isValidPosition,
			value: TooltipBase.Align.Bottom
		}
	};
	TooltipBase.PositionClasses = ['top', 'right', 'bottom', 'left'];
	TooltipBase.RENDERER = _SoyRenderer2.default;
	exports.default = TooltipBase;

	_JQueryAdapter2.default.register('tooltipBase', TooltipBase);
});
//# sourceMappingURL=TooltipBase.js.map