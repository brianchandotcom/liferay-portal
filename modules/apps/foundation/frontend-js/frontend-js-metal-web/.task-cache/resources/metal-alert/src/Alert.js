define("frontend-js-metal-web@1.0.6/metal-alert/src/Alert", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-anim/src/Anim', 'metal-component/src/all/component', 'metal-events/src/events', 'metal-soy/src/Soy', './Alert.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _Anim, _component, _events, _Soy, _Alert, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _Anim2 = _interopRequireDefault(_Anim);

	var _component2 = _interopRequireDefault(_component);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _Alert2 = _interopRequireDefault(_Alert);

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

	var Alert = function (_Component) {
		_inherits(Alert, _Component);

		function Alert() {
			_classCallCheck(this, Alert);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		Alert.prototype.created = function created() {
			this.eventHandler_ = new _events.EventHandler();
		};

		Alert.prototype.detached = function detached() {
			_Component.prototype.detached.call(this);
			this.eventHandler_.removeAllListeners();
			clearTimeout(this.delay_);
		};

		Alert.prototype.close = function close() {
			_dom2.default.once(this.element, 'animationend', this.dispose.bind(this));
			_dom2.default.once(this.element, 'transitionend', this.dispose.bind(this));
			this.eventHandler_.removeAllListeners();
			this.syncVisible(false);
		};

		Alert.prototype.handleDocClick_ = function handleDocClick_(event) {
			if (!this.element.contains(event.target)) {
				this.hide();
			}
		};

		Alert.prototype.hide = function hide() {
			this.visible = false;
		};

		Alert.prototype.hideCompletely_ = function hideCompletely_() {
			if (!this.isDisposed() && !this.visible) {
				_Component.prototype.syncVisible.call(this, false);
			}
		};

		Alert.prototype.toggle = function toggle() {
			this.visible = !this.visible;
		};

		Alert.prototype.show = function show() {
			this.visible = true;
		};

		Alert.prototype.syncDismissible = function syncDismissible(dismissible) {
			if (dismissible) {
				this.eventHandler_.add(_dom2.default.on(document, 'click', this.handleDocClick_.bind(this)));
			} else {
				this.eventHandler_.removeAllListeners();
			}
		};

		Alert.prototype.syncHideDelay = function syncHideDelay(hideDelay) {
			if (_metal.core.isNumber(hideDelay) && this.visible) {
				clearTimeout(this.delay_);
				this.delay_ = setTimeout(this.hide.bind(this), hideDelay);
			}
		};

		Alert.prototype.syncVisible = function syncVisible(visible, prevVisible) {
			var _this2 = this;

			var shouldAsync = false;
			if (!visible) {
				_dom2.default.once(this.element, 'animationend', this.hideCompletely_.bind(this));
				_dom2.default.once(this.element, 'transitionend', this.hideCompletely_.bind(this));
			} else if (_metal.core.isDef(prevVisible)) {
				shouldAsync = true;
				_Component.prototype.syncVisible.call(this, true);
			}

			var showOrHide = function showOrHide() {
				if (_this2.isDisposed()) {
					return;
				}

				_dom2.default.removeClasses(_this2.element, _this2.animClasses[visible ? 'hide' : 'show']);
				_dom2.default.addClasses(_this2.element, _this2.animClasses[visible ? 'show' : 'hide']);

				// Some browsers do not fire transitionend events when running in background
				// tab, see https://bugzilla.mozilla.org/show_bug.cgi?id=683696.
				_Anim2.default.emulateEnd(_this2.element);

				if (visible && _metal.core.isNumber(_this2.hideDelay)) {
					_this2.syncHideDelay(_this2.hideDelay);
				}
			};

			if (shouldAsync) {
				// We need to start the animation asynchronously because of the possible
				// previous call to `super.syncVisible`, which doesn't allow the show
				// animation to work as expected.
				setTimeout(showOrHide, 0);
			} else {
				showOrHide();
			}
		};

		return Alert;
	}(_component2.default);

	_Soy2.default.register(Alert, _Alert2.default);

	/**
  * Alert state definition.
  * @type {!Object}
  * @static
  */
	Alert.STATE = {
		/**
   * The CSS classes that should be added to the alert when being shown/hidden.
   * @type {!Object}
   */
		animClasses: {
			validator: _metal.core.isObject,
			value: {
				show: 'fade in',
				hide: 'fade'
			}
		},

		/**
   * The body content of the alert.
   * @type {string}
   */
		body: {
			isHtml: true
		},

		/**
   * Flag indicating if the alert should be dismissable (closeable).
   * @type {boolean}
   * @default true
   */
		dismissible: {
			validator: _metal.core.isBoolean,
			value: true
		},

		/**
   * The CSS classes that should be added to the alert.
   * @type {string}
   * @default 'alert-success'
   */
		elementClasses: {
			value: 'alert-success'
		},

		/**
   * Delay hiding the alert (ms).
   * @type {?number}
   */
		hideDelay: {},

		/**
   * Spinner indicating.
   * @type {boolean}
   * @default false
   */
		spinner: {
			value: false
		},

		/**
   * The CSS classes that should be added to the spinner.
   * @type {string}
   */
		spinnerClasses: {},

		/**
   * Spinner is marked as done.
   * @type {boolean}
   * @default false
   */
		spinnerDone: {
			value: false
		},

		/**
   * Flag indicating if the alert is visible or not.
   * @type {boolean}
   * @default false
   */
		visible: {
			value: false
		}
	};

	exports.default = Alert;
	_JQueryAdapter2.default.register('alert', Alert);
});
//# sourceMappingURL=Alert.js.map