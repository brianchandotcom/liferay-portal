define("frontend-js-metal-web@1.0.6/metal-modal/src/Modal", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-events/src/events', './Modal.soy', 'metal-component/src/all/component', 'metal-soy/src/Soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _events, _Modal, _component, _Soy, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _dom2 = _interopRequireDefault(_dom);

	var _Modal2 = _interopRequireDefault(_Modal);

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

	var Modal = function (_Component) {
		_inherits(Modal, _Component);

		function Modal() {
			_classCallCheck(this, Modal);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		Modal.prototype.created = function created() {
			this.eventHandler_ = new _events.EventHandler();
		};

		Modal.prototype.attached = function attached() {
			this.autoFocus_(this.autoFocus);
		};

		Modal.prototype.autoFocus_ = function autoFocus_(autoFocusSelector) {
			if (this.inDocument && this.visible && autoFocusSelector) {
				var element = this.element.querySelector(autoFocusSelector);
				if (element) {
					element.focus();
				}
			}
		};

		Modal.prototype.detached = function detached() {
			_Component.prototype.detached.call(this);
			this.eventHandler_.removeAllListeners();
		};

		Modal.prototype.disposeInternal = function disposeInternal() {
			_dom2.default.exitDocument(this.overlayElement);
			this.unrestrictFocus_();
			_Component.prototype.disposeInternal.call(this);
		};

		Modal.prototype.handleDocumentFocus_ = function handleDocumentFocus_(event) {
			if (this.overlay && !this.element.contains(event.target)) {
				this.autoFocus_('.modal-dialog');
			}
		};

		Modal.prototype.handleKeyup_ = function handleKeyup_(event) {
			if (event.keyCode === 27) {
				this.hide();
			}
		};

		Modal.prototype.hide = function hide() {
			this.visible = false;
		};

		Modal.prototype.restrictFocus_ = function restrictFocus_() {
			if (!this.restrictFocusHandle_) {
				this.restrictFocusHandle_ = _dom2.default.on(document, 'focus', this.handleDocumentFocus_.bind(this), true);
			}
		};

		Modal.prototype.shiftFocusBack_ = function shiftFocusBack_() {
			if (this.lastFocusedElement_) {
				this.lastFocusedElement_.focus();
				this.lastFocusedElement_ = null;
			}
		};

		Modal.prototype.show = function show() {
			this.visible = true;
		};

		Modal.prototype.syncHideOnEscape = function syncHideOnEscape(hideOnEscape) {
			if (hideOnEscape) {
				this.eventHandler_.add(_dom2.default.on(document, 'keyup', this.handleKeyup_.bind(this)));
			} else {
				this.eventHandler_.removeAllListeners();
			}
		};

		Modal.prototype.syncOverlay = function syncOverlay(overlay) {
			var willShowOverlay = overlay && this.visible;
			_dom2.default[willShowOverlay ? 'enterDocument' : 'exitDocument'](this.overlayElement);
		};

		Modal.prototype.syncVisible = function syncVisible(visible) {
			this.element.style.display = visible ? 'block' : '';
			this.syncOverlay(this.overlay);
			if (this.visible) {
				this.lastFocusedElement_ = this.lastFocusedElement_ || document.activeElement;
				this.autoFocus_(this.autoFocus);
				this.restrictFocus_();
			} else {
				this.unrestrictFocus_();
				this.shiftFocusBack_();
			}
		};

		Modal.prototype.unrestrictFocus_ = function unrestrictFocus_() {
			if (this.restrictFocusHandle_) {
				this.restrictFocusHandle_.removeListener();
				this.restrictFocusHandle_ = null;
			}
		};

		Modal.prototype.valueOverlayElementFn_ = function valueOverlayElementFn_() {
			return _dom2.default.buildFragment('<div class="modal-backdrop fade in"></div>').firstChild;
		};

		return Modal;
	}(_component2.default);

	Modal.STATE = {
		/**
   * A selector for the element that should be automatically focused when the modal
   * becomes visible, or `false` if no auto focus should happen. Defaults to the
   * modal's close button.
   * @type {boolean|string}
   */
		autoFocus: {
			validator: function validator(val) {
				return val === false || _metal2.default.isString(val);
			},
			value: '.close'
		},

		/**
   * Content to be placed inside modal body.
   * @type {string|SanitizedHtml}
   */
		body: {
			isHtml: true
		},

		/**
   * Content to be placed inside modal footer.
   * @type {string|SanitizedHtml}
   */
		footer: {
			isHtml: true
		},

		/**
   * Content to be placed inside modal header.
   * @type {string|SanitizedHtml}
   */
		header: {
			isHtml: true
		},

		/**
   * Whether modal should hide on esc.
   * @type {boolean}
   * @default true
   */
		hideOnEscape: {
			validator: _metal2.default.isBoolean,
			value: true
		},

		/**
   * Whether overlay should be visible when modal is visible.
   * @type {boolean}
   * @default true
   */
		overlay: {
			validator: _metal2.default.isBoolean,
			value: true
		},

		/**
   * Element to be used as overlay.
   * @type {Element}
   */
		overlayElement: {
			initOnly: true,
			valueFn: 'valueOverlayElementFn_'
		},

		/**
   * The ARIA role to be used for this modal.
   * @type {string}
   * @default 'dialog'
   */
		role: {
			validator: _metal2.default.isString,
			value: 'dialog'
		}
	};

	_Soy2.default.register(Modal, _Modal2.default);

	exports.default = Modal;
	_JQueryAdapter2.default.register('modal', Modal);
});
//# sourceMappingURL=Modal.js.map