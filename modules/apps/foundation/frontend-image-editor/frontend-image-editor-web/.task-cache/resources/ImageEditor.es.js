define("frontend-image-editor-web@1.0.0/ImageEditor.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/core', 'metal-dom/src/dom', 'metal-promise/src/promise/Promise', 'metal-dropdown/src/Dropdown', './ImageEditorHistoryEntry.es', './ImageEditorLoading.es', './ImageEditor.soy'], function (exports, _Component2, _Soy, _core, _dom, _Promise, _Dropdown, _ImageEditorHistoryEntry, _ImageEditorLoading, _ImageEditor) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _Dropdown2 = _interopRequireDefault(_Dropdown);

	var _ImageEditorHistoryEntry2 = _interopRequireDefault(_ImageEditorHistoryEntry);

	var _ImageEditorLoading2 = _interopRequireDefault(_ImageEditorLoading);

	var _ImageEditor2 = _interopRequireDefault(_ImageEditor);

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

	var ImageEditor = function (_Component) {
		_inherits(ImageEditor, _Component);

		/**
   * @inheritDoc
   */

		function ImageEditor(opt_config) {
			_classCallCheck(this, ImageEditor);

			var _this = _possibleConstructorReturn(this, _Component.call(this, opt_config));

			/**
   	 * This index points to the current state in the history.
   	 *
    * @type {Number}
    * @protected
    */
			_this.historyIndex_ = 0;

			/**
    * History of the different image states during edition. Every
    * entry entry represents a change to the image on top of the
    * previous one.
    * - History entries are objects with
    *     - url (optional): the url representing the image
    *     - data: the ImageData object of the image
   	 *
    * @type {Array.<Object>}
    * @protected
    */
			_this.history_ = [new _ImageEditorHistoryEntry2.default({
				url: _this.image
			})];

			// Polyfill svg usage for lexicon icons
			svg4everybody({
				polyfill: true
			});

			// Load the first entry imageData and render it on the app.
			_this.history_[0].getImageData().then(function (imageData) {
				return _this.syncImageData_(imageData);
			});
			return _this;
		}

		/**
   * Accepts the current changes applied by the active control and creates
   * a new entry in the history stack. Doing this will wipe out any
   * stale redo states.
   */


		ImageEditor.prototype.accept = function accept() {
			var _this2 = this;

			var selectedControl = this.components[this.id + '_selected_control_' + this.selectedControl.variant];

			this.history_[this.historyIndex_].getImageData().then(function (imageData) {
				return selectedControl.process(imageData);
			}).then(function (imageData) {
				return _this2.createHistoryEntry_(imageData);
			}).then(function () {
				return _this2.syncHistory_();
			}).then(function () {
				_this2.selectedControl = null;
				_this2.selectedTool = null;
			});
		};

		ImageEditor.prototype.createHistoryEntry_ = function createHistoryEntry_(imageData) {
			// Push new state and discard stale redo states
			this.historyIndex_++;
			this.history_.length = this.historyIndex_ + 1;
			this.history_[this.historyIndex_] = new _ImageEditorHistoryEntry2.default({ data: imageData });

			return _Promise.CancellablePromise.resolve();
		};

		ImageEditor.prototype.discard = function discard() {
			this.selectedControl = null;
			this.selectedTool = null;
			this.syncHistory_();
		};

		ImageEditor.prototype.getImageEditorCanvas = function getImageEditorCanvas() {
			return this.element.querySelector('.lfr-image-editor-image-container canvas');
		};

		ImageEditor.prototype.getImageEditorImageData = function getImageEditorImageData() {
			return this.history_[this.historyIndex_].getImageData();
		};

		ImageEditor.prototype.redo = function redo() {
			this.historyIndex_++;
			this.syncHistory_();
		};

		ImageEditor.prototype.requestImageEditorEdit = function requestImageEditorEdit(event) {
			var _this3 = this;

			var controls = this.imageEditorCapabilities.tools.reduce(function (prev, curr) {
				return prev.concat(curr.controls);
			}, []);

			var target = event.delegateTarget || event.currentTarget;
			var targetControl = target.getAttribute('data-control');
			var targetTool = target.getAttribute('data-tool');

			this.syncHistory_().then(function () {
				_this3.selectedControl = controls.filter(function (tool) {
					return tool.variant === targetControl;
				})[0];
				_this3.selectedTool = targetTool;
			});
		};

		ImageEditor.prototype.requestImageEditorPreview = function requestImageEditorPreview() {
			var _this4 = this;

			var selectedControl = this.components[this.id + '_selected_control_' + this.selectedControl.variant];

			this.history_[this.historyIndex_].getImageData().then(function (imageData) {
				return selectedControl.preview(imageData);
			}).then(function (imageData) {
				return _this4.syncImageData_(imageData);
			});

			this.components.loading.show = true;
		};

		ImageEditor.prototype.reset = function reset() {
			this.historyIndex_ = 0;
			this.history_.length = 1;
			this.syncHistory_();
		};

		ImageEditor.prototype.syncHistory_ = function syncHistory_() {
			var _this5 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				_this5.history_[_this5.historyIndex_].getImageData().then(function (imageData) {
					_this5.syncImageData_(imageData);

					_this5.history = {
						canRedo: _this5.historyIndex_ < _this5.history_.length - 1,
						canReset: _this5.history_.length > 1,
						canUndo: _this5.historyIndex_ > 0
					};

					resolve();
				});
			});
		};

		ImageEditor.prototype.syncImageData_ = function syncImageData_(imageData) {
			var width = imageData.width;
			var height = imageData.height;

			var aspectRatio = width / height;

			var offscreenCanvas = document.createElement('canvas');
			offscreenCanvas.width = width;
			offscreenCanvas.height = height;

			var offscreenContext = offscreenCanvas.getContext('2d');
			offscreenContext.clearRect(0, 0, width, height);
			offscreenContext.putImageData(imageData, 0, 0);

			var canvas = this.getImageEditorCanvas();

			var boundingBox = _dom2.default.closest(this.element, '#main-content');
			var availableWidth = boundingBox.offsetWidth;
			var availableHeight = boundingBox.offsetHeight - 142 - 40;
			var availableAspectRatio = availableWidth / availableHeight;

			if (availableAspectRatio > 1) {
				canvas.height = availableHeight;
				canvas.width = aspectRatio * availableHeight;
			} else {
				canvas.width = availableWidth;
				canvas.height = availableWidth / aspectRatio;
			}

			var context = canvas.getContext('2d');
			context.clearRect(0, 0, canvas.width, canvas.height);
			context.drawImage(offscreenCanvas, 0, 0, width, height, 0, 0, canvas.width, canvas.height);

			canvas.style.width = canvas.width + 'px';
			canvas.style.height = canvas.height + 'px';

			this.components.loading.show = false;
		};

		ImageEditor.prototype.undo = function undo() {
			this.historyIndex_--;
			this.syncHistory_();
		};

		return ImageEditor;
	}(_Component3.default);

	// Register component
	_Soy2.default.register(ImageEditor, _ImageEditor2.default);

	exports.default = ImageEditor;
});
//# sourceMappingURL=ImageEditor.es.js.map