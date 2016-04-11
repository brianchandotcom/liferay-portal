define("frontend-js-metal-web@1.0.6/metal-autocomplete/src/AutocompleteBase", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-promise/src/promise/Promise', 'metal-component/src/all/component', 'metal-events/src/events', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _Promise, _component, _events, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _dom2 = _interopRequireDefault(_dom);

	var _Promise2 = _interopRequireDefault(_Promise);

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

	var AutocompleteBase = function (_Component) {
		_inherits(AutocompleteBase, _Component);

		function AutocompleteBase() {
			_classCallCheck(this, AutocompleteBase);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		AutocompleteBase.prototype.created = function created() {
			this.eventHandler_ = new _events.EventHandler();
			this.on('select', this.select);
		};

		AutocompleteBase.prototype.attached = function attached() {
			if (this.inputElement) {
				this.eventHandler_.add(_dom2.default.on(this.inputElement, 'input', this.handleUserInput_.bind(this)));
			}
		};

		AutocompleteBase.prototype.detached = function detached() {
			this.eventHandler_.removeAllListeners();
		};

		AutocompleteBase.prototype.handleUserInput_ = function handleUserInput_() {
			this.request(this.inputElement.value);
		};

		AutocompleteBase.prototype.request = function request(query) {
			var self = this;

			if (this.pendingRequest) {
				this.pendingRequest.cancel('Cancelled by another request');
			}

			var deferredData = self.data(query);
			if (!_metal2.default.isPromise(deferredData)) {
				deferredData = _Promise2.default.resolve(deferredData);
			}

			this.pendingRequest = deferredData.then(function (data) {
				if (Array.isArray(data)) {
					return data.map(self.format.bind(self)).filter(function (val) {
						return _metal2.default.isDefAndNotNull(val);
					});
				}
			});

			return this.pendingRequest;
		};

		AutocompleteBase.prototype.setData_ = function setData_(val) {
			if (!_metal2.default.isFunction(val)) {
				return function () {
					return val;
				};
			}
			return val;
		};

		return AutocompleteBase;
	}(_component2.default);

	/**
  * AutocompleteBase state definition.
  * @type {!Object}
  * @static
  */
	AutocompleteBase.STATE = {
		/**
   * Function or array, which have to return the results from the query.
   * If function, it should return an `array` or a `Promise`. In case of
   * Promise, it should be resolved with an array containing the results.
   * @type {Array.<object>|function}
   */
		data: {
			setter: 'setData_'
		},

		/**
   * Function that formats each item of the data.
   * @type {function}
   * @default Identity function.
   */
		format: {
			value: _metal2.default.identityFunction,
			validator: _metal2.default.isFunction
		},

		/**
   * The element which will be used source for the data queries.
   * @type {DOMElement|string}
   */
		inputElement: {
			setter: _dom2.default.toElement
		},

		/**
   * Handles item selection. It will receive two parameters - the selected
   * value from the user and the current value from the input element.
   * @type {function}
   * @default
   *   function(selectedValue) {
   *	   this.inputElement.value = selectedValue;
   *	   this.inputElement.focus();
   *   }
   */
		select: {
			value: function value(selectedValue) {
				this.inputElement.value = selectedValue.text;
				this.inputElement.focus();
			},
			validator: _metal2.default.isFunction
		},

		/**
   * Indicates if the component is visible or not.
   * @type {boolean}
   */
		visible: {
			validator: _metal2.default.isBoolean,
			value: false
		}
	};

	exports.default = AutocompleteBase;
	_JQueryAdapter2.default.register('autocompleteBase', AutocompleteBase);
});
//# sourceMappingURL=AutocompleteBase.js.map