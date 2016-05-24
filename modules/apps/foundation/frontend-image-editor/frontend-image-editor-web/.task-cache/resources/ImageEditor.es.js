define("frontend-image-editor-web@1.0.0/ImageEditor.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/async/async', 'metal/src/core', 'metal-dom/src/dom', 'metal-promise/src/promise/Promise', 'metal-dropdown/src/Dropdown', './ImageEditorHistoryEntry.es', './ImageEditorLoading.es', './ImageEditor.soy'], function (exports, _Component2, _Soy, _async, _core, _dom, _Promise, _Dropdown, _ImageEditorHistoryEntry, _ImageEditorLoading, _ImageEditor) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _async2 = _interopRequireDefault(_async);

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
				_async2.default.nextTick(function () {
					_this.imageEditorReady = true;

					_this.syncImageData_(imageData);
				});
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

		ImageEditor.prototype.close_ = function close_() {
			Liferay.Util.getWindow().hide();
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

		ImageEditor.prototype.getImageEditorImageBlob = function getImageEditorImageBlob() {
			var _this3 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				_this3.getImageEditorImageData().then(function (imageData) {
					var canvas = document.createElement('canvas');
					canvas.width = imageData.width;
					canvas.height = imageData.height;

					canvas.getContext('2d').putImageData(imageData, 0, 0);

					if (canvas.toBlob) {
						canvas.toBlob(resolve, _this3.saveMimeType);
					} else {
						var data = atob(canvas.toDataURL(_this3.saveMimeType).split(',')[1]);
						var length = data.length;
						var bytes = new Uint8Array(length);

						for (var i = 0; i < length; i++) {
							bytes[i] = data.charCodeAt(i);
						}

						resolve(new Blob([bytes], { type: _this3.saveMimeType }));
					}
				});
			});
		};

		ImageEditor.prototype.getImageEditorImageData = function getImageEditorImageData() {
			return this.history_[this.historyIndex_].getImageData();
		};

		ImageEditor.prototype.normalizeCanvasMimeType_ = function normalizeCanvasMimeType_(mimeType) {
			return mimeType.replace('jpg', 'jpeg');
		};

		ImageEditor.prototype.notifySaveResult_ = function notifySaveResult_(result) {
			this.components.loading.show = false;

			if (result && result.success) {
				Liferay.Util.getOpener().Liferay.fire(this.saveEventName, {
					data: result
				});

				Liferay.Util.getWindow().hide();
			} else if (result.error) {
				this.showError_(result.error.message);
			}
		};

		ImageEditor.prototype.redo = function redo() {
			this.historyIndex_++;
			this.syncHistory_();
		};

		ImageEditor.prototype.requestImageEditorEdit = function requestImageEditorEdit(event) {
			var _this4 = this;

			var controls = this.imageEditorCapabilities.tools.reduce(function (prev, curr) {
				return prev.concat(curr.controls);
			}, []);

			var target = event.delegateTarget || event.currentTarget;
			var targetControl = target.getAttribute('data-control');
			var targetTool = target.getAttribute('data-tool');

			this.syncHistory_().then(function () {
				_this4.selectedControl = controls.filter(function (tool) {
					return tool.variant === targetControl;
				})[0];
				_this4.selectedTool = targetTool;
			});
		};

		ImageEditor.prototype.requestImageEditorPreview = function requestImageEditorPreview() {
			var _this5 = this;

			var selectedControl = this.components[this.id + '_selected_control_' + this.selectedControl.variant];

			this.history_[this.historyIndex_].getImageData().then(function (imageData) {
				return selectedControl.preview(imageData);
			}).then(function (imageData) {
				return _this5.syncImageData_(imageData);
			});

			this.components.loading.show = true;
		};

		ImageEditor.prototype.reset = function reset() {
			this.historyIndex_ = 0;
			this.history_.length = 1;
			this.syncHistory_();
		};

		ImageEditor.prototype.save_ = function save_(event) {
			var _this6 = this;

			if (!event.delegateTarget.disabled) {
				this.getImageEditorImageBlob().then(function (imageBlob) {
					return _this6.submitBlob_(imageBlob);
				}).then(function (result) {
					return _this6.notifySaveResult_(result);
				}).catch(function (error) {
					return _this6.showError_(error);
				});
			}
		};

		ImageEditor.prototype.setterSaveMimeTypeFn_ = function setterSaveMimeTypeFn_(saveMimeType) {
			if (!saveMimeType) {
				var imageExtensionRegex = /(?:.*:\/\/)?(?:[^\/])*[^.]*.([^?\/$]*)/;
				var imageExtension = this.image.match(imageExtensionRegex)[1];

				saveMimeType = this.normalizeCanvasMimeType_('image/' + imageExtension);
			}

			return saveMimeType;
		};

		ImageEditor.prototype.showError_ = function showError_(message) {
			var _this7 = this;

			this.components.loading.show = false;

			AUI().use('liferay-alert', function () {
				new Liferay.Alert({
					delay: {
						hide: 2000,
						show: 0
					},
					duration: 3000,
					icon: 'exclamation-circle',
					message: message.message,
					type: 'danger'
				}).render(_this7.element);
			});
		};

		ImageEditor.prototype.submitBlob_ = function submitBlob_(imageBlob) {
			var _this8 = this;

			var saveFileName = this.saveFileName;
			var saveParamName = this.saveParamName;

			var promise = new _Promise.CancellablePromise(function (resolve, reject) {
				var formData = new FormData();

				formData.append(saveParamName, imageBlob, saveFileName);

				var requestConfig = {
					contentType: false,
					data: formData,
					dataType: "json",
					processData: false,
					type: 'POST',
					url: _this8.saveURL
				};

				AUI.$.ajax(requestConfig).done(resolve).fail(function (jqXHR, status, error) {
					return reject(error);
				});
			});

			this.components.loading.show = true;

			return promise;
		};

		ImageEditor.prototype.syncHistory_ = function syncHistory_() {
			var _this9 = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				_this9.history_[_this9.historyIndex_].getImageData().then(function (imageData) {
					_this9.syncImageData_(imageData);

					_this9.history = {
						canRedo: _this9.historyIndex_ < _this9.history_.length - 1,
						canReset: _this9.history_.length > 1,
						canUndo: _this9.historyIndex_ > 0
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

			var boundingBox = _dom2.default.closest(this.element, '.portlet-layout');
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

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	ImageEditor.STATE = {
		/**
   * Indicates that the editor is ready for user interaction
   * @type {Object}
   */
		imageEditorReady: {
			validator: _core2.default.isBoolean,
			value: false
		},

		/**
   * Event to dispatch when the edition has been completed
   * @type {String}
   */
		saveEventName: {
			validator: _core2.default.isString
		},

		/**
   * Name of the saved image that should be sent
   * to the server for the save action
   * @type {String}
   */
		saveFileName: {
			validator: _core2.default.isString
		},

		/**
   * Mime type of the saved image. If not explicitly set,
   * the image mime type will be infered from the image url.
   * @type {String}
   */
		saveMimeType: {
			setter: 'setterSaveMimeTypeFn_',
			validator: _core2.default.isString
		},

		/**
   * Name of the param where the image should be sent
   * to the server for the save action
   * @type {String}
   */
		saveParamName: {
			validator: _core2.default.isString
		},

		/**
   * Url to save the image changes
   * @type {String}
   */
		saveURL: {
			validator: _core2.default.isString
		}
	};

	// Register component
	_Soy2.default.register(ImageEditor, _ImageEditor2.default);

	exports.default = ImageEditor;
});
//# sourceMappingURL=ImageEditor.es.js.map