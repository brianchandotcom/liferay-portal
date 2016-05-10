define("frontend-image-editor-capability-rotate@1.0.0/RotateComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/core', 'metal-promise/src/promise/Promise', './RotateComponent.soy', './RotateControls.soy'], function (exports, _Component2, _Soy, _core, _Promise, _RotateComponent, _RotateControls) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _core2 = _interopRequireDefault(_core);

	var _RotateComponent2 = _interopRequireDefault(_RotateComponent);

	var _RotateControls2 = _interopRequireDefault(_RotateControls);

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

	var RotateComponent = function (_Component) {
		_inherits(RotateComponent, _Component);

		function RotateComponent() {
			_classCallCheck(this, RotateComponent);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		RotateComponent.prototype.attached = function attached() {
			this.cache_ = {};
			this.rotationAngle_ = 0;
		};

		RotateComponent.prototype.detached = function detached() {
			this.cache_ = {};
		};

		RotateComponent.prototype.preview = function preview(imageData) {
			return this.process(imageData);
		};

		RotateComponent.prototype.process = function process(imageData) {
			var promise = this.cache_[this.rotationAngle_];

			if (!promise) {
				promise = this.rotate_(imageData, this.rotationAngle_);

				this.cache_[this.rotationAngle_] = promise;
			}

			return promise;
		};

		RotateComponent.prototype.rotate_ = function rotate_(imageData, rotationAngle) {
			var cancellablePromise = new _Promise.CancellablePromise(function (resolve, reject) {
				var imageWidth = imageData.width;
				var imageHeight = imageData.height;

				var swapDimensions = rotationAngle / 90 % 2;

				var imageCanvas = document.createElement('canvas');
				imageCanvas.width = imageWidth;
				imageCanvas.height = imageHeight;
				imageCanvas.getContext('2d').putImageData(imageData, 0, 0);

				var offscreenCanvas = document.createElement('canvas');
				offscreenCanvas.width = swapDimensions ? imageHeight : imageWidth;
				offscreenCanvas.height = swapDimensions ? imageWidth : imageHeight;

				var offscreenContext = offscreenCanvas.getContext('2d');
				offscreenContext.save();
				offscreenContext.translate(offscreenCanvas.width / 2, offscreenCanvas.height / 2);
				offscreenContext.rotate(rotationAngle * Math.PI / 180);
				offscreenContext.drawImage(imageCanvas, -imageCanvas.width / 2, -imageCanvas.height / 2);
				offscreenContext.restore();

				resolve(offscreenContext.getImageData(0, 0, offscreenCanvas.width, offscreenCanvas.height));
			});

			return cancellablePromise;
		};

		RotateComponent.prototype.rotateLeft = function rotateLeft() {
			this.rotationAngle_ = (this.rotationAngle_ - 90) % 360;
			this.requestImageEditorPreview();
		};

		RotateComponent.prototype.rotateRight = function rotateRight() {
			this.rotationAngle_ = (this.rotationAngle_ + 90) % 360;
			this.requestImageEditorPreview();
		};

		return RotateComponent;
	}(_Component3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	RotateComponent.STATE = {
		/**
   * Path of this module
   * @type {Function}
   */
		modulePath: {
			validator: _core2.default.isString
		},

		/**
   * Injected method to notify the editor this component
   * wants to generate a preview version of the image.
   * @type {Function}
   */
		requestImageEditorPreview: {
			validator: _core2.default.isFunction
		}
	};

	// Register component
	_Soy2.default.register(RotateComponent, _RotateComponent2.default);

	exports.default = RotateComponent;
});
//# sourceMappingURL=RotateComponent.es.js.map