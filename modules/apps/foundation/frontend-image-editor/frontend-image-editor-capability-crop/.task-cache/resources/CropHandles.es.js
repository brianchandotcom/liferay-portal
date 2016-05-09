define("frontend-image-editor-capability-crop@1.0.0/CropHandles.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/async/async', 'metal/src/core', 'metal-dom/src/dom', 'metal-drag-drop/src/Drag', 'metal-position/src/Position', './CropHandles.soy'], function (exports, _Component2, _Soy, _async, _core, _dom, _Drag, _Position, _CropHandles) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _async2 = _interopRequireDefault(_async);

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _Drag2 = _interopRequireDefault(_Drag);

	var _Position2 = _interopRequireDefault(_Position);

	var _CropHandles2 = _interopRequireDefault(_CropHandles);

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

	var CropHandles = function (_Component) {
		_inherits(CropHandles, _Component);

		function CropHandles() {
			_classCallCheck(this, CropHandles);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		CropHandles.prototype.attached = function attached() {
			var _this2 = this;

			this.parentNode_ = this.element.parentNode;

			this.resizer = this.element.querySelector('.resize-handle');

			this.selectionBorderWidth_ = parseInt(this.element.style.borderWidth, 10);

			this.croppedPreview_ = this.element.querySelector('.cropped-image-preview');

			this.croppedPreviewContext_ = this.croppedPreview_.getContext('2d');

			_async2.default.nextTick(function () {
				var canvas = _this2.getImageEditorCanvas();

				_this2.setSelectionInitialStyle_();

				_this2.initializeDrags_();

				_dom2.default.removeClasses(_this2.element, 'hide');
				_dom2.default.append(canvas.parentElement, _this2.element);
			});
		};

		CropHandles.prototype.detached = function detached() {
			var canvas = this.getImageEditorCanvas();

			canvas.style.opacity = 1;
		};

		CropHandles.prototype.bindDrags_ = function bindDrags_() {
			this.resizer.addEventListener('mousedown', function (event) {
				return event.stopPropagation();
			});

			this.bindSelectionDrag_();
			this.bindSizeDrag_();
		};

		CropHandles.prototype.bindSelectionDrag_ = function bindSelectionDrag_() {
			var _this3 = this;

			var canvas = this.getImageEditorCanvas();

			this.selectionDrag_.on(_Drag2.default.Events.DRAG, function (data, event) {
				var left = data.relativeX - canvas.offsetLeft + _this3.selectionBorderWidth_;
				var top = data.relativeY - canvas.offsetTop + _this3.selectionBorderWidth_;

				_this3.element.style.left = left + 'px';
				_this3.element.style.top = top + 'px';

				_this3.croppedPreviewContext_.drawImage(canvas, left, top, _this3.croppedPreview_.width, _this3.croppedPreview_.height, 0, 0, _this3.croppedPreview_.width, _this3.croppedPreview_.height);
			});
		};

		CropHandles.prototype.bindSizeDrag_ = function bindSizeDrag_() {
			var _this4 = this;

			var canvas = this.getImageEditorCanvas();

			this.sizeDrag_.on(_Drag2.default.Events.DRAG, function (data, event) {
				var width = data.relativeX + _this4.resizer.offsetWidth / 2;
				var height = data.relativeY + _this4.resizer.offsetHeight / 2;

				_this4.element.style.width = width + _this4.selectionBorderWidth_ * 2 + 'px';
				_this4.element.style.height = height + _this4.selectionBorderWidth_ * 2 + 'px';

				_this4.croppedPreview_.width = width;
				_this4.croppedPreview_.height = height;

				_this4.croppedPreviewContext_.drawImage(canvas, _this4.element.offsetLeft - canvas.offsetLeft + _this4.selectionBorderWidth_, _this4.element.offsetTop - canvas.offsetTop + _this4.selectionBorderWidth_, width, height, 0, 0, width, height);
				_this4.croppedPreview_.style.width = width + 'px';
				_this4.croppedPreview_.style.height = height + 'px';
			});
		};

		CropHandles.prototype.getSizeDragConstrain_ = function getSizeDragConstrain_(region) {
			var canvas = this.getImageEditorCanvas();

			var constrain = _Position2.default.getRegion(canvas);

			var selection = _Position2.default.getRegion(this.element);

			constrain.left = selection.left + this.resizer.offsetWidth + this.selectionBorderWidth_ * 2;
			constrain.top = selection.top + this.resizer.offsetHeight + this.selectionBorderWidth_ * 2;
			constrain.width = constrain.right - constrain.left;
			constrain.height = constrain.bottom - constrain.top;
			constrain.right += this.resizer.offsetWidth / 2 - this.selectionBorderWidth_;
			constrain.bottom += this.resizer.offsetHeight / 2 - this.selectionBorderWidth_;

			if (region.left < constrain.left) {
				region.left = constrain.left;
			} else if (region.right > constrain.right) {
				region.left -= region.right - constrain.right;
			}

			if (region.top < constrain.top) {
				region.top = constrain.top;
			} else if (region.bottom > constrain.bottom) {
				region.top -= region.bottom - constrain.bottom;
			}

			region.right = region.left + region.width;
			region.bottom = region.top + region.height;
		};

		CropHandles.prototype.initializeDrags_ = function initializeDrags_() {
			var canvas = this.getImageEditorCanvas();

			this.selectionDrag_ = new _Drag2.default({
				constrain: canvas,
				handles: this.element,
				sources: this.element
			});

			this.sizeDrag_ = new _Drag2.default({
				constrain: this.getSizeDragConstrain_.bind(this),
				handles: this.resizer,
				sources: this.resizer
			});

			this.bindDrags_();
		};

		CropHandles.prototype.setSelectionInitialStyle_ = function setSelectionInitialStyle_() {
			var canvas = this.getImageEditorCanvas();

			canvas.style.opacity = 0.5;

			this.element.style.width = canvas.offsetWidth + 'px';
			this.element.style.height = canvas.offsetHeight + 'px';
			this.element.style.left = canvas.offsetLeft + 'px';
			this.element.style.top = canvas.offsetTop + 'px';

			this.croppedPreview_.width = canvas.offsetWidth;
			this.croppedPreview_.height = canvas.offsetHeight;

			this.croppedPreviewContext_.drawImage(canvas, this.selectionBorderWidth_, this.selectionBorderWidth_, canvas.width - this.selectionBorderWidth_ * 2, canvas.height - this.selectionBorderWidth_ * 2, 0, 0, canvas.width - this.selectionBorderWidth_ * 2, canvas.height - this.selectionBorderWidth_ * 2);

			this.croppedPreview_.style.width = this.croppedPreview_.width + 'px';
			this.croppedPreview_.style.height = this.croppedPreview_.height + 'px';
		};

		return CropHandles;
	}(_Component3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	CropHandles.STATE = {
		/**
   * Injected helper to get the editor canvas element
   * @type {Function}
   */
		getImageEditorCanvas: {
			validator: _core2.default.isFunction
		}
	};

	// Register component
	_Soy2.default.register(CropHandles, _CropHandles2.default);

	exports.default = CropHandles;
});
//# sourceMappingURL=CropHandles.es.js.map