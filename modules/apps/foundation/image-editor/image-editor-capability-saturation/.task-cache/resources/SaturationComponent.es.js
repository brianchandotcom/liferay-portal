define("liferay-image-editor-capability-saturation@1.0.0/SaturationComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal-promise/src/promise/Promise', 'metal-slider/src/Slider', './SaturationComponent.soy', './SaturationControls.soy'], function (exports, _Component2, _Soy, _Promise, _Slider, _SaturationComponent, _SaturationControls) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _Slider2 = _interopRequireDefault(_Slider);

	var _SaturationComponent2 = _interopRequireDefault(_SaturationComponent);

	var _SaturationControls2 = _interopRequireDefault(_SaturationControls);

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

	var SaturationComponent = function (_Component) {
		_inherits(SaturationComponent, _Component);

		function SaturationComponent() {
			_classCallCheck(this, SaturationComponent);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		SaturationComponent.prototype.attached = function attached() {
			this.cache_ = {};
		};

		SaturationComponent.prototype.detached = function detached() {
			this.cache_ = {};
		};

		SaturationComponent.prototype.preview = function preview(imageData) {
			var saturationValue = this.components.saturationSlider.value;
			var promise = this.cache_[saturationValue];

			if (!promise) {
				promise = this.spawnWorker_({
					saturationValue: saturationValue,
					imageData: imageData.preview
				});

				this.cache_[saturationValue] = promise;
			}

			return promise;
		};

		SaturationComponent.prototype.process = function process(imageData) {
			var _this2 = this;

			var saturationValue = this.components.saturationSlider.value;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				_this2.spawnWorker_({
					saturationValue: saturationValue,
					imageData: imageData.raw
				}).then(function (result) {
					return resolve(imageData.toURL(result));
				});
			});
		};

		SaturationComponent.prototype.spawnWorker_ = function spawnWorker_(message) {
			var _this3 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				var workerURI = _this3.modulePath + '/SaturationWorker.js';
				var processWorker = new Worker(workerURI);

				processWorker.onmessage = function (event) {
					return resolve(event.data);
				};
				processWorker.postMessage(message);
			});
		};

		return SaturationComponent;
	}(_Component3.default);

	// Register component
	_Soy2.default.register(SaturationComponent, _SaturationComponent2.default);

	exports.default = SaturationComponent;
});
//# sourceMappingURL=SaturationComponent.es.js.map