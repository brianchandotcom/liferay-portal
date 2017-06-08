define("liferay-image-editor-capability-brightness@1.0.0/BrighntessComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/core', 'metal-promise/src/promise/Promise', 'metal-slider/src/Slider', './BrightnessComponent.soy', './BrightnessControls.soy'], function (exports, _Component2, _Soy, _core, _Promise, _Slider, _BrightnessComponent, _BrightnessControls) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _core2 = _interopRequireDefault(_core);

	var _Slider2 = _interopRequireDefault(_Slider);

	var _BrightnessComponent2 = _interopRequireDefault(_BrightnessComponent);

	var _BrightnessControls2 = _interopRequireDefault(_BrightnessControls);

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

	var BrightnessComponent = function (_Component) {
		_inherits(BrightnessComponent, _Component);

		function BrightnessComponent() {
			_classCallCheck(this, BrightnessComponent);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		BrightnessComponent.prototype.attached = function attached() {
			this.cache_ = {};
		};

		BrightnessComponent.prototype.detached = function detached() {
			this.cache_ = {};
		};

		BrightnessComponent.prototype.preview = function preview(imageData) {
			var brightnessValue = this.components.brightnessSlider.value;
			var promise = this.cache_[brightnessValue];

			if (!promise) {
				promise = this.spawnWorker_({
					brightnessValue: brightnessValue,
					imageData: imageData.preview
				});

				this.cache_[brightnessValue] = promise;
			}

			return promise;
		};

		BrightnessComponent.prototype.process = function process(imageData) {
			var _this2 = this;

			var brightnessValue = this.components.brightnessSlider.value;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				_this2.spawnWorker_({
					brightnessValue: brightnessValue,
					imageData: imageData.raw
				}).then(function (result) {
					return resolve(imageData.toURL(result));
				});
			});
		};

		BrightnessComponent.prototype.spawnWorker_ = function spawnWorker_(message) {
			var _this3 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				var workerURI = _this3.modulePath + '/BrightnessWorker.js';
				var processWorker = new Worker(workerURI);

				processWorker.onmessage = function (event) {
					return resolve(event.data);
				};
				processWorker.postMessage(message);
			});
		};

		return BrightnessComponent;
	}(_Component3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	BrightnessComponent.STATE = {
		/**
   * Path of this module
   * @type {Function}
   */
		modulePath: {
			validator: _core2.default.isString
		}
	};

	// Register component
	_Soy2.default.register(BrightnessComponent, _BrightnessComponent2.default);

	exports.default = BrightnessComponent;
});
//# sourceMappingURL=BrighntessComponent.es.js.map