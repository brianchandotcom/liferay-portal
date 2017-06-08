define("liferay-image-editor-web@1.0.0/ImageEditorUtils.es", ['exports', 'metal-promise/src/promise/Promise'], function (exports, _Promise) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var ImageEditorUtils = function () {
		/**
   * Constructor
   *
   * @param  {[type]} image       [description]
   * @param  {[type]} imageEditor [description]
   */

		function ImageEditorUtils(imageURL, element) {
			_classCallCheck(this, ImageEditorUtils);

			this.element_ = element;
			this.imageURL_ = imageURL;
			this.imageDOMNode_ = this.element_.querySelector('.lfr-image-editor-image-container img');
			this.previewCanvas_ = this.element_.querySelector('.lfr-image-editor-image-container canvas.image-preview');

			this.rawImageDataPromise_ = new _Promise.CancellablePromise(function (resolve, reject) {
				var bufferImage = new Image();

				bufferImage.onload = function () {
					var bufferCanvas = document.createElement('canvas');
					var bufferContext = bufferCanvas.getContext('2d');

					var rawImageWidth = bufferImage.width;
					var rawImageHeight = bufferImage.height;

					bufferCanvas.width = rawImageWidth;
					bufferCanvas.height = rawImageHeight;

					bufferContext.drawImage(bufferImage, 0, 0, rawImageWidth, rawImageHeight);

					var rawImageData = bufferContext.getImageData(0, 0, rawImageWidth, rawImageHeight);

					resolve(rawImageData);
				};

				bufferImage.src = imageURL;
			});
		}

		ImageEditorUtils.prototype.enablePreview = function enablePreview() {
			var previewContext = this.previewCanvas_.getContext('2d');

			this.previewCanvas_.height = this.imageDOMNode_.height;
			this.previewCanvas_.width = this.imageDOMNode_.width;

			previewContext.drawImage(this.imageDOMNode_, 0, 0, this.previewCanvas_.width, this.previewCanvas_.height);

			this.previewImageData_ = previewContext.getImageData(0, 0, this.previewCanvas_.width, this.previewCanvas_.height);
		};

		ImageEditorUtils.prototype.getImageData = function getImageData() {
			var _this = this;

			return new _Promise.CancellablePromise(function (resolve, reject) {
				_this.rawImageDataPromise_.then(function (rawImageData) {
					resolve({
						preview: _this.previewImageData_,
						raw: rawImageData,
						toURL: _this.imageData2DataURL_,
						url: _this.imageURL_
					});
				});
			});

			return this.imageDataPromise_;
		};

		ImageEditorUtils.prototype.preview = function preview(imageData) {
			var previewContext = this.previewCanvas_.getContext('2d');

			previewContext.putImageData(imageData, 0, 0);
		};

		ImageEditorUtils.prototype.imageData2DataURL_ = function imageData2DataURL_(imageData) {
			var canvas = document.createElement('canvas');
			canvas.width = imageData.width;
			canvas.height = imageData.height;

			var ctx = canvas.getContext('2d');
			ctx.putImageData(imageData, 0, 0);

			return canvas.toDataURL();
		};

		return ImageEditorUtils;
	}();

	exports.default = ImageEditorUtils;
});
//# sourceMappingURL=ImageEditorUtils.es.js.map