'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-modal/src/Modal", ['exports', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/events/EventHandler', 'crystal-modal/src/Modal.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _core, _dom, _EventHandler, _Modal, _JQueryAdapter) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _EventHandler2 = _interopRequireDefault(_EventHandler);

	var _Modal2 = _interopRequireDefault(_Modal);

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

	var Modal = (function (_ModalBase) {
		_inherits(Modal, _ModalBase);

		function Modal(opt_config) {
			_classCallCheck(this, Modal);

			var _this = _possibleConstructorReturn(this, _ModalBase.call(this, opt_config));

			_this.eventHandler_ = new _EventHandler2.default();
			return _this;
		}

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
			_ModalBase.prototype.detached.call(this);

			this.eventHandler_.removeAllListeners();
		};

		Modal.prototype.disposeInternal = function disposeInternal() {
			_dom2.default.exitDocument(this.overlayElement);

			this.unrestrictFocus_();

			_ModalBase.prototype.disposeInternal.call(this);
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
			this.restrictFocusHandle_ = _dom2.default.on(document, 'focus', this.handleDocumentFocus_.bind(this), true);
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
				this.lastFocusedElement_ = document.activeElement;
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
			}
		};

		Modal.prototype.valueOverlayElementFn_ = function valueOverlayElementFn_() {
			return _dom2.default.buildFragment('<div class="modal-backdrop fade in"></div>').firstChild;
		};

		return Modal;
	})(_Modal2.default);

	Modal.ELEMENT_CLASSES = 'modal';
	Modal.ATTRS = {
		autoFocus: {
			validator: function validator(val) {
				return val === false || _core2.default.isString(val);
			},
			value: '.close'
		},
		body: {},
		footer: {},
		header: {},
		hideOnEscape: {
			validator: _core2.default.isBoolean,
			value: true
		},
		overlay: {
			validator: _core2.default.isBoolean,
			value: true
		},
		overlayElement: {
			initOnly: true,
			valueFn: 'valueOverlayElementFn_'
		},
		role: {
			validator: _core2.default.isString,
			value: 'dialog'
		}
	};

	_Modal2.default.setImpl(Modal);

	exports.default = Modal;

	_JQueryAdapter2.default.register('modal', Modal);
});
//# sourceMappingURL=Modal.js.map