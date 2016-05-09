define("frontend-image-editor-capability-crop@1.0.0/CropComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/core', 'metal-promise/src/promise/Promise', './CropComponent.soy', './CropControls.soy'], function (exports, _Component2, _Soy, _core, _Promise, _CropComponent, _CropControls) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _core2 = _interopRequireDefault(_core);

	var _CropComponent2 = _interopRequireDefault(_CropComponent);

	var _CropControls2 = _interopRequireDefault(_CropControls);

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

	var CropComponent = function (_Component) {
		_inherits(CropComponent, _Component);

		function CropComponent() {
			_classCallCheck(this, CropComponent);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		CropComponent.prototype.process = function process(imageData) {
			var imageCanvas = this.getImageEditorCanvas();

			var horizontalRatio = imageData.width / imageCanvas.offsetWidth;
			var verticalRatio = imageData.height / imageCanvas.offsetHeight;

			var cropHandles = this.components[this.key + 'CropHandles'];
			var selection = {
				height: cropHandles.element.offsetHeight,
				left: cropHandles.element.offsetLeft - imageCanvas.offsetLeft,
				top: cropHandles.element.offsetTop - imageCanvas.offsetTop,
				width: cropHandles.element.offsetWidth
			};

			var rawCanvas = document.createElement('canvas');
			rawCanvas.width = imageData.width;
			rawCanvas.height = imageData.height;

			rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

			var canvas = document.createElement('canvas');
			var normalizedLeft = selection.left * horizontalRatio;
			var normalizedWidth = selection.width * horizontalRatio;
			var normalizedTop = selection.top * verticalRatio;
			var normalizedHeight = selection.height * verticalRatio;

			canvas.width = normalizedWidth;
			canvas.height = normalizedHeight;

			var context = canvas.getContext('2d');
			context.drawImage(rawCanvas, normalizedLeft, normalizedTop, normalizedWidth, normalizedHeight, 0, 0, normalizedWidth, normalizedHeight);

			cropHandles.dispose();

			return _Promise.CancellablePromise.resolve(context.getImageData(0, 0, canvas.width, canvas.height));
		};

		return CropComponent;
	}(_Component3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	CropComponent.STATE = {
		/**
   * Injected helper to get the editor canvas
   * @type {Function}
   */
		getImageEditorCanvas: {
			validator: _core2.default.isFunction
		}
	};

	// Register component
	_Soy2.default.register(CropComponent, _CropComponent2.default);

	exports.default = CropComponent;
});
//# sourceMappingURL=CropComponent.es.js.map