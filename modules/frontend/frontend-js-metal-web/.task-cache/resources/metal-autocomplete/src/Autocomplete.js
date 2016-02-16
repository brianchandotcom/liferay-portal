define("frontend-js-metal-web@1.0.0/metal-autocomplete/src/Autocomplete", ['exports', 'metal/src/metal', 'metal-debounce/src/debounce', 'metal-dom/src/all/dom', 'metal-promise/src/promise/Promise', 'metal-position/src/all/position', './AutocompleteBase', 'metal-soy/src/soy', 'metal-jquery-adapter/src/JQueryAdapter', './Autocomplete.soy', 'metal-list/src/List'], function (exports, _metal, _debounce, _dom, _Promise, _position, _AutocompleteBase2, _soy, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _debounce2 = _interopRequireDefault(_debounce);

	var _dom2 = _interopRequireDefault(_dom);

	var _AutocompleteBase3 = _interopRequireDefault(_AutocompleteBase2);

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

	var Autocomplete = function (_AutocompleteBase) {
		_inherits(Autocomplete, _AutocompleteBase);

		function Autocomplete() {
			_classCallCheck(this, Autocomplete);

			return _possibleConstructorReturn(this, _AutocompleteBase.apply(this, arguments));
		}

		Autocomplete.prototype.attached = function attached() {
			_AutocompleteBase.prototype.attached.call(this);
			this.on('click', function (event) {
				return event.stopPropagation();
			});
			this.eventHandler_.add(_dom2.default.on(this.inputElement, 'focus', this.handleInputFocus_.bind(this)));
			this.eventHandler_.add(_dom2.default.on(document, 'click', this.handleDocClick_.bind(this)));
			this.eventHandler_.add(_dom2.default.on(window, 'resize', (0, _debounce2.default)(this.handleWindowResize_.bind(this), 100)));
			if (this.visible) {
				this.align();
			}
		};

		Autocomplete.prototype.align = function align() {
			this.element.style.width = this.inputElement.offsetWidth + 'px';
			var position = _position.Align.align(this.element, this.inputElement, _position.Align.Bottom);

			_dom2.default.removeClasses(this.element, this.positionCss_);
			switch (position) {
				case _position.Align.Top:
				case _position.Align.TopLeft:
				case _position.Align.TopRight:
					this.positionCss_ = 'autocomplete-top';
					break;
				case _position.Align.Bottom:
				case _position.Align.BottomLeft:
				case _position.Align.BottomRight:
					this.positionCss_ = 'autocomplete-bottom';
					break;
				default:
					this.positionCss_ = null;

			}
			_dom2.default.addClasses(this.element, this.positionCss_);
		};

		Autocomplete.prototype.getList = function getList() {
			return this.components[this.id + '-list'];
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

		Autocomplete.prototype.handleWindowResize_ = function handleWindowResize_() {
			if (this.visible) {
				this.align();
			}
		};

		Autocomplete.prototype.request = function request(query) {
			var self = this;
			return _AutocompleteBase.prototype.request.call(this, query).then(function (data) {
				if (data) {
					data.forEach(self.assertItemObjectStructure_);
					self.getList().items = data;
				}
				self.visible = !!(data && data.length > 0);
			});
		};

		Autocomplete.prototype.onListItemSelected_ = function onListItemSelected_(item) {
			var selectedIndex = parseInt(item.getAttribute('data-index'), 10);
			this.emit('select', this.getList().items[selectedIndex]);
			this.visible = false;
		};

		Autocomplete.prototype.syncVisible = function syncVisible(visible) {
			_AutocompleteBase.prototype.syncVisible.call(this, visible);

			if (visible) {
				this.align();
			}
		};

		Autocomplete.prototype.assertItemObjectStructure_ = function assertItemObjectStructure_(item) {
			if (!_metal2.default.isObject(item)) {
				throw new _Promise.CancellablePromise.CancellationError('Autocomplete item must be an object');
			}
			if (!item.hasOwnProperty('textPrimary')) {
				throw new _Promise.CancellablePromise.CancellationError('Autocomplete item must be an object with \'textPrimary\' key');
			}
		};

		return Autocomplete;
	}(_AutocompleteBase3.default);

	Autocomplete.prototype.registerMetalComponent && Autocomplete.prototype.registerMetalComponent(Autocomplete, 'Autocomplete')


	/**
  * Attributes definition.
  * @type {!Object}
  * @static
  */
	Autocomplete.ATTRS = {
		/**
   * Function that converts a given item to the format that should be used by
   * the autocomplete.
   * @type {!function()}
   */
		format: {
			value: function value(item) {
				return _metal2.default.isString(item) ? {
					textPrimary: item
				} : item;
			}
		}
	};

	/**
  * The class that will be used as this component's renderer.
  * @type {!Function}
  * @static
  */
	Autocomplete.RENDERER = _soy.SoyRenderer;

	exports.default = Autocomplete;
	_JQueryAdapter2.default.register('autocomplete', Autocomplete);
});
//# sourceMappingURL=Autocomplete.js.map