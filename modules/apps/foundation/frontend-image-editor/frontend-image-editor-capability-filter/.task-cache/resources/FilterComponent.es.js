define("frontend-image-editor-capability-filter@1.0.0/FilterComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/async/async', 'metal/src/core', 'metal-promise/src/promise/Promise', './FilterComponent.soy', './FilterControls.soy'], function (exports, _Component2, _Soy, _async, _core, _Promise, _FilterComponent, _FilterControls) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _async2 = _interopRequireDefault(_async);

	var _core2 = _interopRequireDefault(_core);

	var _FilterComponent2 = _interopRequireDefault(_FilterComponent);

	var _FilterControls2 = _interopRequireDefault(_FilterControls);

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

	var FilterComponent = function (_Component) {
		_inherits(FilterComponent, _Component);

		function FilterComponent() {
			_classCallCheck(this, FilterComponent);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		FilterComponent.prototype.attached = function attached() {
			var _this2 = this;

			this.cache_ = {};

			_async2.default.nextTick(function () {
				_this2.getImageEditorImageData().then(function (imageData) {
					return _Promise.CancellablePromise.resolve(_this2.generateThumbnailImageData_(imageData));
				}).then(function (previewImageData) {
					return _this2.generateThumbnails_(previewImageData);
				}).then(function () {
					return _this2.prefetchFilters_();
				});
			});
		};

		FilterComponent.prototype.detached = function detached() {
			this.cache_ = {};
		};

		FilterComponent.prototype.generateThumbnail_ = function generateThumbnail_(filter, imageData) {
			var _this3 = this;

			var promise = this.spawnWorker_({
				filter: filter,
				imageData: imageData
			});

			promise.then(function (imageData) {
				var canvas = _this3.element.querySelector('#' + _this3.key + filter + ' canvas');
				canvas.getContext('2d').putImageData(imageData, 0, 0);
			});

			return promise;
		};

		FilterComponent.prototype.generateThumbnails_ = function generateThumbnails_(imageData) {
			var _this4 = this;

			return _Promise.CancellablePromise.all(this.filters.map(function (filter) {
				return _this4.generateThumbnail_(filter, imageData);
			}));
		};

		FilterComponent.prototype.generateThumbnailImageData_ = function generateThumbnailImageData_(imageData) {
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

		FilterComponent.prototype.prefetchFilters_ = function prefetchFilters_() {
			var _this5 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				if (!_this5.isDisposed()) {
					(function () {
						var missingFilters = _this5.filters.filter(function (filter) {
							return !_this5.cache_[filter];
						});

						if (!missingFilters.length) {
							resolve();
						} else {
							_this5.getImageEditorImageData().then(function (imageData) {
								return _this5.process(imageData, missingFilters[0]);
							}).then(function () {
								return _this5.prefetchFilters_();
							});
						}
					})();
				}
			});
		};

		FilterComponent.prototype.preview = function preview(imageData) {
			return this.process(imageData);
		};

		FilterComponent.prototype.previewFilter = function previewFilter(event) {
			this.currentFilter_ = event.delegateTarget.getAttribute('data-filter');
			this.requestImageEditorPreview();
		};

		FilterComponent.prototype.process = function process(imageData, filterName) {
			var filter = filterName || this.currentFilter_;
			var promise = this.cache_[filter];

			if (!promise) {
				promise = this.spawnWorker_({
					filter: filter,
					imageData: imageData
				});

				this.cache_[filter] = promise;
			}

			return promise;
		};

		FilterComponent.prototype.spawnWorker_ = function spawnWorker_(message) {
			var _this6 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				var processWorker = new Worker(_this6.modulePath + '/FilterWorker.js');

				processWorker.onmessage = function (event) {
					return resolve(event.data);
				};
				processWorker.postMessage(message);
			});
		};

		return FilterComponent;
	}(_Component3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	FilterComponent.STATE = {
		/**
   * Array of available filters
   * @type {Object}
   */
		filters: {
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
	_Soy2.default.register(FilterComponent, _FilterComponent2.default);

	exports.default = FilterComponent;
});
//# sourceMappingURL=FilterComponent.es.js.map