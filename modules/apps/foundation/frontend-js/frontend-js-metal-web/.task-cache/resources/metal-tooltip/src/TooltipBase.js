define("frontend-js-metal-web@1.0.6/metal-tooltip/src/TooltipBase", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-position/src/all/position', 'metal-component/src/all/component', 'metal-events/src/events', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _position, _component, _events, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _dom2 = _interopRequireDefault(_dom);

	var _component2 = _interopRequireDefault(_component);

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

	var TooltipBase = function (_Component) {
		_inherits(TooltipBase, _Component);

		function TooltipBase() {
			_classCallCheck(this, TooltipBase);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		TooltipBase.prototype.attached = function attached() {
			this.align();
			this.syncTriggerEvents(this.triggerEvents);
		};

		TooltipBase.prototype.created = function created() {
			this.eventHandler_ = new _events.EventHandler();
		};

		TooltipBase.prototype.detached = function detached() {
			this.eventHandler_.removeAllListeners();
		};

		TooltipBase.prototype.disposeInternal = function disposeInternal() {
			_Component.prototype.disposeInternal.call(this);
			clearTimeout(this.delay_);
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
				var dataTitle = alignElement.getAttribute('data-title');
				if (dataTitle) {
					this.title = dataTitle;
				}
				if (this.inDocument) {
					this.alignedPosition = TooltipBase.Align.align(this.element, alignElement, this.position);
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

		return TooltipBase;
	}(_component2.default);

	/**
  * @inheritDoc
  * @see `Align` class.
  * @static
  */
	TooltipBase.Align = _position.Align;

	/**
  * TooltipBase state definition.
  * @type {!Object}
  * @static
  */
	TooltipBase.STATE = {
		/**
   * The current position of the tooltip after being aligned via `Align.align`.
   * @type {number}
   */
		alignedPosition: {
			validator: TooltipBase.Align.isValidPosition
		},

		/**
   * Element to align tooltip with.
   * @type {Element}
   */
		alignElement: {
			setter: _dom2.default.toElement
		},

		/**
   * Delay showing and hiding the tooltip (ms).
   * @type {!Array<number>}
   * @default [ 500, 250 ]
   */
		delay: {
			validator: Array.isArray,
			value: [500, 250]
		},

		/**
   * Trigger events used to bind handlers to show and hide tooltip.
   * @type {!Array<string>}
   * @default ['mouseenter', 'mouseleave']
   */
		triggerEvents: {
			validator: Array.isArray,
			value: ['mouseenter', 'mouseleave']
		},

		/**
   * If a selector is provided, tooltip objects will be delegated to the
   * specified targets by setting the `alignElement`.
   * @type {?string}
   */
		selector: {
			validator: _metal2.default.isString
		},

		/**
   * The position to try alignment. If not possible the best position will be
   * found.
   * @type {number}
   * @default Align.Bottom
   */
		position: {
			validator: TooltipBase.Align.isValidPosition,
			value: TooltipBase.Align.Bottom
		},

		/**
   * Content to be placed inside tooltip.
   * @type {string}
   */
		title: {
			isHtml: true
		}
	};

	/**
  * CSS classes used for each align position.
  * @type {!Array}
  * @static
  */
	TooltipBase.PositionClasses = ['top', 'right', 'bottom', 'left'];

	exports.default = TooltipBase;
	_JQueryAdapter2.default.register('tooltipBase', TooltipBase);
});
//# sourceMappingURL=TooltipBase.js.map