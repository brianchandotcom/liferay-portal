var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol ? "symbol" : typeof obj; };

define("frontend-js-metal-web@1.0.0/metal-autocomplete/src/Autocomplete", ['exports', './AutocompleteBase', 'metal-promise/src/promise/Promise', 'metal/src/core', 'metal/src/dom/dom', 'metal-position/src/Align', 'metal-list/src/List', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _AutocompleteBase2, _Promise, _core, _dom, _Align, _List, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _AutocompleteBase3 = _interopRequireDefault(_AutocompleteBase2);

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _Align2 = _interopRequireDefault(_Align);

	var _List2 = _interopRequireDefault(_List);

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

	var Autocomplete = function (_AutocompleteBase) {
		_inherits(Autocomplete, _AutocompleteBase);

		function Autocomplete(opt_config) {
			_classCallCheck(this, Autocomplete);

			var _this = _possibleConstructorReturn(this, _AutocompleteBase.call(this, opt_config));

			_this.once('render', _this.handleRender_);

			return _this;
		}

		Autocomplete.prototype.attached = function attached() {
			_AutocompleteBase.prototype.attached.call(this);

			this.list.attach(this.element);
			this.on('click', this.genericStopPropagation_);
			this.eventHandler_.add(_dom2.default.on(this.inputElement, 'focus', this.handleInputFocus_.bind(this)));
			this.eventHandler_.add(_dom2.default.on(document, 'click', this.handleDocClick_.bind(this)));

			if (this.visible) {
				this.align();
			}
		};

		Autocomplete.prototype.detached = function detached() {
			_AutocompleteBase.prototype.detached.call(this);

			this.list.detach();
		};

		Autocomplete.prototype.align = function align() {
			this.element.style.width = this.inputElement.offsetWidth + 'px';

			_Align2.default.align(this.element, this.inputElement, _Align2.default.Bottom);
		};

		Autocomplete.prototype.handleDocClick_ = function handleDocClick_() {
			if (document.activeElement === this.inputElement) {
				return;
			}

			this.visible = false;
		};

		Autocomplete.prototype.handleInputFocus_ = function handleInputFocus_() {
			this.request(this.inputElement.value);
		};

		Autocomplete.prototype.handleRender_ = function handleRender_() {
			this.list = new _List2.default().render(this.element);
			this.list.on('itemSelected', this.onListItemSelected_.bind(this));
		};

		Autocomplete.prototype.request = function request(query) {
			var self = this;
			return _AutocompleteBase.prototype.request.call(this, query).then(function (data) {
				if (data) {
					data.forEach(self.assertItemObjectStructure_);
					self.list.items = data;
				}

				self.visible = !!(data && data.length > 0);
			});
		};

		Autocomplete.prototype.onListItemSelected_ = function onListItemSelected_(item) {
			var selectedIndex = parseInt(item.getAttribute('data-index'), 10);
			this.emit('select', this.list.items[selectedIndex]);
			this.visible = false;
		};

		Autocomplete.prototype.genericStopPropagation_ = function genericStopPropagation_(event) {
			event.stopPropagation();
		};

		Autocomplete.prototype.syncVisible = function syncVisible(visible) {
			_AutocompleteBase.prototype.syncVisible.call(this, visible);

			if (visible) {
				this.align();
			}
		};

		Autocomplete.prototype.assertItemObjectStructure_ = function assertItemObjectStructure_(item) {
			if (!_core2.default.isObject(item)) {
				throw new _Promise.CancellablePromise.CancellationError('Autocomplete item must be an object');
			}

			if (!item.hasOwnProperty('textPrimary')) {
				throw new _Promise.CancellablePromise.CancellationError('Autocomplete item must be an object with \'textPrimary\' key');
			}
		};

		return Autocomplete;
	}(_AutocompleteBase3.default);

	Autocomplete.prototype.registerMetalComponent && Autocomplete.prototype.registerMetalComponent(Autocomplete, 'Autocomplete')
	Autocomplete.ATTRS = {
		format: {
			value: function value(item) {
				return _core2.default.isString(item) ? {
					textPrimary: item
				} : item;
			}
		}
	};
	Autocomplete.ELEMENT_CLASSES = 'autocomplete autocomplete-list';
	exports.default = Autocomplete;

	_JQueryAdapter2.default.register('autocomplete', Autocomplete);
});
//# sourceMappingURL=Autocomplete.js.map