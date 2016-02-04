var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

define("frontend-js-metal-web@1.0.0/metal-clipboard/src/Clipboard", ['exports', 'metal/src/attribute/Attribute', 'metal/src/core', 'metal/src/dom/dom', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _Attribute3, _core, _dom, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Attribute4 = _interopRequireDefault(_Attribute3);

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

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

	var Clipboard = function (_Attribute) {
		_inherits(Clipboard, _Attribute);

		function Clipboard(opt_config) {
			_classCallCheck(this, Clipboard);

			var _this = _possibleConstructorReturn(this, _Attribute.call(this, opt_config));

			_this.listener_ = _dom2.default.on(_this.selector, 'click', function (e) {
				return _this.initialize(e);
			});
			return _this;
		}

		Clipboard.prototype.disposeInternal = function disposeInternal() {
			this.listener_.dispose();
			this.listener_ = null;

			if (this.clipboardAction_) {
				this.clipboardAction_.dispose();
				this.clipboardAction_ = null;
			}
		};

		Clipboard.prototype.initialize = function initialize(e) {
			if (this.clipboardAction_) {
				this.clipboardAction_ = null;
			}

			this.clipboardAction_ = new ClipboardAction({
				host: this,
				action: this.action(e.delegateTarget),
				target: this.target(e.delegateTarget),
				text: this.text(e.delegateTarget),
				trigger: e.delegateTarget
			});
		};

		return Clipboard;
	}(_Attribute4.default);

	Clipboard.prototype.registerMetalComponent && Clipboard.prototype.registerMetalComponent(Clipboard, 'Clipboard')
	Clipboard.ATTRS = {
		action: {
			validator: _core2.default.isFunction,
			value: function value(delegateTarget) {
				return delegateTarget.getAttribute('data-action');
			}
		},
		selector: {
			value: '[data-clipboard]',
			validator: _core2.default.isString
		},
		target: {
			validator: _core2.default.isFunction,
			value: function value(delegateTarget) {
				return document.querySelector(delegateTarget.getAttribute('data-target'));
			}
		},
		text: {
			validator: _core2.default.isFunction,
			value: function value(delegateTarget) {
				return delegateTarget.getAttribute('data-text');
			}
		}
	};

	var ClipboardAction = function (_Attribute2) {
		_inherits(ClipboardAction, _Attribute2);

		function ClipboardAction(opt_config) {
			_classCallCheck(this, ClipboardAction);

			var _this2 = _possibleConstructorReturn(this, _Attribute2.call(this, opt_config));

			if (_this2.text) {
				_this2.selectValue();
			} else if (_this2.target) {
				_this2.selectTarget();
			}

			return _this2;
		}

		ClipboardAction.prototype.clearSelection = function clearSelection() {
			if (this.target) {
				this.target.blur();
			}

			window.getSelection().removeAllRanges();
		};

		ClipboardAction.prototype.copyText = function copyText() {
			var succeeded = undefined;

			try {
				succeeded = document.execCommand(this.action);
			} catch (err) {
				succeeded = false;
			}

			this.handleResult(succeeded);
		};

		ClipboardAction.prototype.disposeInternal = function disposeInternal() {
			this.removeFakeElement();

			_Attribute2.prototype.disposeInternal.call(this);
		};

		ClipboardAction.prototype.handleResult = function handleResult(succeeded) {
			if (succeeded) {
				this.host.emit('success', {
					action: this.action,
					text: this.selectedText,
					trigger: this.trigger,
					clearSelection: this.clearSelection.bind(this)
				});
			} else {
				this.host.emit('error', {
					action: this.action,
					trigger: this.trigger,
					clearSelection: this.clearSelection.bind(this)
				});
			}
		};

		ClipboardAction.prototype.removeFakeElement = function removeFakeElement() {
			if (this.fake) {
				_dom2.default.exitDocument(this.fake);
			}

			if (this.removeFakeHandler) {
				this.removeFakeHandler.removeListener();
			}
		};

		ClipboardAction.prototype.selectTarget = function selectTarget() {
			if (this.target.nodeName === 'INPUT' || this.target.nodeName === 'TEXTAREA') {
				this.target.select();
				this.selectedText = this.target.value;
			} else {
				var range = document.createRange();
				var selection = window.getSelection();
				range.selectNodeContents(this.target);
				selection.addRange(range);
				this.selectedText = selection.toString();
			}

			this.copyText();
		};

		ClipboardAction.prototype.selectValue = function selectValue() {
			this.removeFakeElement();
			this.removeFakeHandler = _dom2.default.once(document, 'click', this.removeFakeElement.bind(this));
			this.fake = document.createElement('textarea');
			this.fake.style.position = 'fixed';
			this.fake.style.left = '-9999px';
			this.fake.setAttribute('readonly', '');
			this.fake.value = this.text;
			this.selectedText = this.text;

			_dom2.default.enterDocument(this.fake);

			this.fake.select();
			this.copyText();
		};

		return ClipboardAction;
	}(_Attribute4.default);

	ClipboardAction.prototype.registerMetalComponent && ClipboardAction.prototype.registerMetalComponent(ClipboardAction, 'ClipboardAction')
	ClipboardAction.ATTRS = {
		action: {
			value: 'copy',
			validator: function validator(val) {
				return val === 'copy' || val === 'cut';
			}
		},
		host: {
			validator: function validator(val) {
				return val instanceof Clipboard;
			}
		},
		selectedText: {
			validator: _core2.default.isString
		},
		target: {
			validator: _core2.default.isElement
		},
		text: {
			validator: _core2.default.isString
		},
		trigger: {
			validator: _core2.default.isElement
		}
	};
	exports.default = Clipboard;

	_JQueryAdapter2.default.register('clipboard', Clipboard);
});
//# sourceMappingURL=Clipboard.js.map