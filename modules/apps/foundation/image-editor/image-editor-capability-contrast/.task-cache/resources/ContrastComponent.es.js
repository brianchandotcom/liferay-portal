define("liferay-image-editor-capability-contrast@1.0.0/ContrastComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal-promise/src/promise/Promise', 'metal-slider/src/Slider', './ContrastComponent.soy', './ContrastControls.soy'], function (exports, _Component2, _Soy, _Promise, _Slider, _ContrastComponent, _ContrastControls) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _Slider2 = _interopRequireDefault(_Slider);

	var _ContrastComponent2 = _interopRequireDefault(_ContrastComponent);

	var _ContrastControls2 = _interopRequireDefault(_ContrastControls);

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

	var ContrastComponent = function (_Component) {
		_inherits(ContrastComponent, _Component);

		function ContrastComponent() {
			_classCallCheck(this, ContrastComponent);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		ContrastComponent.prototype.attached = function attached() {
			this.cache_ = {};
		};

		ContrastComponent.prototype.detached = function detached() {
			this.cache_ = {};
		};

		ContrastComponent.prototype.preview = function preview(imageData) {
			var contrastValue = this.components.contrastSlider.value;
			var promise = this.cache_[contrastValue];

			if (!promise) {
				promise = this.spawnWorker_({
					contrastValue: contrastValue,
					imageData: imageData.preview
				});

				this.cache_[contrastValue] = promise;
			}

			return promise;
		};

		ContrastComponent.prototype.process = function process(imageData) {
			var _this2 = this;

			var contrastValue = this.components.contrastSlider.value;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				_this2.spawnWorker_({
					contrastValue: contrastValue,
					imageData: imageData.raw
				}).then(function (result) {
					return resolve(imageData.toURL(result));
				});
			});
		};

		ContrastComponent.prototype.spawnWorker_ = function spawnWorker_(message) {
			var _this3 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				var workerURI = _this3.modulePath + '/ContrastWorker.js';
				var processWorker = new Worker(workerURI);

				processWorker.onmessage = function (event) {
					return resolve(event.data);
				};
				processWorker.postMessage(message);
			});
		};

		return ContrastComponent;
	}(_Component3.default);

	// Register component
	_Soy2.default.register(ContrastComponent, _ContrastComponent2.default);

	exports.default = ContrastComponent;
});
//# sourceMappingURL=ContrastComponent.es.js.map