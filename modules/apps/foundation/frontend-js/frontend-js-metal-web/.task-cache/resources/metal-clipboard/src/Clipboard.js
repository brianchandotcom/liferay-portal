define("frontend-js-metal-web@1.0.6/metal-clipboard/src/Clipboard", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-state/src/State', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _State3, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _dom2 = _interopRequireDefault(_dom);

	var _State4 = _interopRequireDefault(_State3);

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

	var Clipboard = function (_State) {
		_inherits(Clipboard, _State);

		/**
   * Delegates a click event to the passed selector.
   */

		function Clipboard(opt_config) {
			_classCallCheck(this, Clipboard);

			var _this = _possibleConstructorReturn(this, _State.call(this, opt_config));

			_this.listener_ = _dom2.default.on(_this.selector, 'click', function (e) {
				return _this.initialize(e);
			});
			return _this;
		}

		/**
   * @inheritDoc
   */


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
	}(_State4.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	Clipboard.STATE = {
		/**
   * A function that returns the name of the clipboard action that should be done
   * when for the given element (either 'copy' or 'cut').
   * @type {!function(!Element)}
   */
		action: {
			validator: _metal2.default.isFunction,
			value: function value(delegateTarget) {
				return delegateTarget.getAttribute('data-action');
			}
		},

		/**
   * The selector for all elements that should be listened for clipboard actions.
   * @type {string}
   */
		selector: {
			value: '[data-clipboard]',
			validator: _metal2.default.isString
		},

		/**
   * A function that returns an element that has the content to be copied to the
   * clipboard.
   * @type {!function(!Element)}
   */
		target: {
			validator: _metal2.default.isFunction,
			value: function value(delegateTarget) {
				return document.querySelector(delegateTarget.getAttribute('data-target'));
			}
		},

		/**
   * A function that returns the text to be copied to the clipboard.
   * @type {!function(!Element)}
   */
		text: {
			validator: _metal2.default.isFunction,
			value: function value(delegateTarget) {
				return delegateTarget.getAttribute('data-text');
			}
		}
	};

	/**
  * ClipboardAction component.
  */

	var ClipboardAction = function (_State2) {
		_inherits(ClipboardAction, _State2);

		/**
   * Initializes selection either from a `text` or `target` state.
   */

		function ClipboardAction(opt_config) {
			_classCallCheck(this, ClipboardAction);

			var _this2 = _possibleConstructorReturn(this, _State2.call(this, opt_config));

			if (_this2.text) {
				_this2.selectValue();
			} else if (_this2.target) {
				_this2.selectTarget();
			}
			return _this2;
		}

		/**
   * Removes current selection and focus from `target` element.
   */


		ClipboardAction.prototype.clearSelection = function clearSelection() {
			if (this.target) {
				this.target.blur();
			}

			window.getSelection().removeAllRanges();
		};

		ClipboardAction.prototype.copyText = function copyText() {
			var succeeded = void 0;

			try {
				succeeded = document.execCommand(this.action);
			} catch (err) {
				succeeded = false;
			}

			this.handleResult(succeeded);
		};

		ClipboardAction.prototype.disposeInternal = function disposeInternal() {
			this.removeFakeElement();
			_State2.prototype.disposeInternal.call(this);
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
	}(_State4.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	ClipboardAction.STATE = {
		/**
   * The action to be performed (either 'copy' or 'cut').
   * @type {string}
   * @default 'copy'
   */
		action: {
			value: 'copy',
			validator: function validator(val) {
				return val === 'copy' || val === 'cut';
			}
		},

		/**
   * A reference to the `Clipboard` base class.
   * @type {!Clipboard}
   */
		host: {
			validator: function validator(val) {
				return val instanceof Clipboard;
			}
		},

		/**
   * The text that is current selected.
   * @type {string}
   */
		selectedText: {
			validator: _metal2.default.isString
		},

		/**
   * The ID of an element that will be have its content copied.
   * @type {Element}
   */
		target: {
			validator: _metal2.default.isElement
		},

		/**
   * The text to be copied.
   * @type {string}
   */
		text: {
			validator: _metal2.default.isString
		},

		/**
   * The element that when clicked initiates a clipboard action.
   * @type {!Element}
   */
		trigger: {
			validator: _metal2.default.isElement
		}
	};

	exports.default = Clipboard;
	_JQueryAdapter2.default.register('clipboard', Clipboard);
});
//# sourceMappingURL=Clipboard.js.map