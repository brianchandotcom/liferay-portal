define("frontend-image-editor-capability-effects@1.0.0/EffectsComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/async/async', 'metal/src/core', 'metal-promise/src/promise/Promise', './EffectsComponent.soy', './EffectsControls.soy'], function (exports, _Component2, _Soy, _async, _core, _Promise, _EffectsComponent, _EffectsControls) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _async2 = _interopRequireDefault(_async);

	var _core2 = _interopRequireDefault(_core);

	var _EffectsComponent2 = _interopRequireDefault(_EffectsComponent);

	var _EffectsControls2 = _interopRequireDefault(_EffectsControls);

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

	var EffectsComponent = function (_Component) {
		_inherits(EffectsComponent, _Component);

		function EffectsComponent() {
			_classCallCheck(this, EffectsComponent);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		EffectsComponent.prototype.attached = function attached() {
			var _this2 = this;

			this.cache_ = {};

			_async2.default.nextTick(function () {
				_this2.getImageEditorImageData().then(function (imageData) {
					return _Promise.CancellablePromise.resolve(_this2.generateThumbnailImageData_(imageData));
				}).then(function (previewImageData) {
					return _this2.generateThumbnails_(previewImageData);
				}).then(function () {
					return _this2.prefetchEffects_();
				});
			});
		};

		EffectsComponent.prototype.detached = function detached() {
			this.cache_ = {};
		};

		EffectsComponent.prototype.generateThumbnail_ = function generateThumbnail_(effect, imageData) {
			var _this3 = this;

			var promise = this.spawnWorker_({
				effect: effect,
				imageData: imageData
			});

			promise.then(function (imageData) {
				var canvas = _this3.element.querySelector('#' + _this3.key + effect + ' canvas');
				canvas.getContext('2d').putImageData(imageData, 0, 0);
			});

			return promise;
		};

		EffectsComponent.prototype.generateThumbnails_ = function generateThumbnails_(imageData) {
			var _this4 = this;

			return _Promise.CancellablePromise.all(this.effects.map(function (effect) {
				return _this4.generateThumbnail_(effect, imageData);
			}));
		};

		EffectsComponent.prototype.generateThumbnailImageData_ = function generateThumbnailImageData_(imageData) {
			var thumbnailSize = this.thumbnailSize;
			var imageWidth = imageData.width;
			var imageHeight = imageData.height;

			var rawCanvas = document.createElement('canvas');
			rawCanvas.width = imageWidth;
			rawCanvas.height = imageHeight;
			rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

			var commonSize = imageWidth > imageHeight ? imageHeight : imageWidth;

			var canvas = document.createElement('canvas');
			canvas.width = thumbnailSize;
			canvas.height = thumbnailSize;

			var context = canvas.getContext('2d');
			context.drawImage(rawCanvas, imageWidth - commonSize, imageHeight - commonSize, commonSize, commonSize, 0, 0, thumbnailSize, thumbnailSize);

			return context.getImageData(0, 0, thumbnailSize, thumbnailSize);
		};

		EffectsComponent.prototype.prefetchEffects_ = function prefetchEffects_() {
			var _this5 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				if (!_this5.isDisposed()) {
					(function () {
						var missingEffects = _this5.effects.filter(function (effect) {
							return !_this5.cache_[effect];
						});

						if (!missingEffects.length) {
							resolve();
						} else {
							_this5.getImageEditorImageData().then(function (imageData) {
								return _this5.process(imageData, missingEffects[0]);
							}).then(function () {
								return _this5.prefetchEffects_();
							});
						}
					})();
				}
			});
		};

		EffectsComponent.prototype.preview = function preview(imageData) {
			return this.process(imageData);
		};

		EffectsComponent.prototype.previewEffect = function previewEffect(event) {
			this.currentEffect_ = event.delegateTarget.getAttribute('data-effect');
			this.requestImageEditorPreview();
		};

		EffectsComponent.prototype.process = function process(imageData, effectName) {
			var effect = effectName || this.currentEffect_;
			var promise = this.cache_[effect];

			if (!promise) {
				promise = this.spawnWorker_({
					effect: effect,
					imageData: imageData
				});

				this.cache_[effect] = promise;
			}

			return promise;
		};

		EffectsComponent.prototype.spawnWorker_ = function spawnWorker_(message) {
			var _this6 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				var processWorker = new Worker(_this6.modulePath + '/EffectsWorker.js');

				processWorker.onmessage = function (event) {
					return resolve(event.data);
				};
				processWorker.postMessage(message);
			});
		};

		return EffectsComponent;
	}(_Component3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	EffectsComponent.STATE = {
		/**
   * Array of available effects
   * @type {Object}
   */
		effects: {
			validator: _core2.default.isArray,
			value: ['none', 'ruby', 'absinthe', 'chroma', 'atari', 'tripel', 'ailis', 'flatfoot', 'pyrexia', 'umbra', 'rouge', 'idyll', 'glimmer', 'elysium', 'nucleus', 'amber', 'paella', 'aureus', 'expanse', 'orchid']
		},

		/**
   * Injected helper to get the editor image data
   * @type {Function}
   */
		getImageEditorImageData: {
			validator: _core2.default.isFunction
		},

		/**
   * Path of this module
   * @type {Function}
   */
		modulePath: {
			validator: _core2.default.isString
		},

		/**
   * Injected helper to get the editor image data
   * @type {Function}
   */
		requestImageEditorPreview: {
			validator: _core2.default.isFunction
		},

		/**
   * Size of the thumbnails. (size x size)
   * @type {Number}
   */
		thumbnailSize: {
			validator: _core2.default.isNumber,
			value: 55
		}
	};

	// Register component
	_Soy2.default.register(EffectsComponent, _EffectsComponent2.default);

	exports.default = EffectsComponent;
});
//# sourceMappingURL=EffectsComponent.es.js.map