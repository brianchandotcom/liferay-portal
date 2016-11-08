define("frontend-image-editor-web@1.0.7/ImageEditorHistoryEntry.es", ['exports', 'metal-promise/src/promise/Promise'], function (exports, _Promise) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var ImageEditorHistoryEntry = function () {
		/**
   * Constructor
   */

		function ImageEditorHistoryEntry(image) {
			var _this = this;

			_classCallCheck(this, ImageEditorHistoryEntry);

			this.dataPromise_ = new _Promise.CancellablePromise(function (resolve, reject) {
				// Preemtively fetch the imageData when all we have is the image url
				if (image.url && !image.data) {
					_this.loadData_(image.url).then(function (imageData) {
						return resolve(imageData);
					});
				} else {
					resolve(image.data);
				}
			});
		}

		/**
   * Fetches an ImageData for a given image url
   *
   * @param  {String} imageURL The image url to load
   * @protected
   */


		ImageEditorHistoryEntry.prototype.loadData_ = function loadData_(imageURL) {
			return new _Promise.CancellablePromise(function (resolve, reject) {
				var bufferImage = new Image();

				bufferImage.onload = function () {
					var bufferCanvas = document.createElement('canvas');
					var bufferContext = bufferCanvas.getContext('2d');

					var height = bufferImage.height;
					var width = bufferImage.width;

					bufferCanvas.width = width;
					bufferCanvas.height = height;

					bufferContext.drawImage(bufferImage, 0, 0, width, height);

					resolve(bufferContext.getImageData(0, 0, width, height));
				};

				bufferImage.src = imageURL;
			});
		};

		ImageEditorHistoryEntry.prototype.getImageData = function getImageData() {
			return this.dataPromise_;
		};

		return ImageEditorHistoryEntry;
	}();

	exports.default = ImageEditorHistoryEntry;
});
//# sourceMappingURL=ImageEditorHistoryEntry.es.js.map