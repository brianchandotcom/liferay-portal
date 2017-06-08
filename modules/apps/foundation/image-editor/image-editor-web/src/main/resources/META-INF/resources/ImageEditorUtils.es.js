import { CancellablePromise } from 'metal-promise/src/promise/Promise';

/**
 * ImageEditorUtils
 */
class ImageEditorUtils {
	/**
	 * Constructor
	 *
	 * @param  {[type]} image       [description]
	 * @param  {[type]} imageEditor [description]
	 */
	constructor(imageURL, element) {
		this.element_ = element;
		this.imageURL_ = imageURL;
		this.imageDOMNode_ = this.element_.querySelector('.lfr-image-editor-image-container img');
		this.previewCanvas_ = this.element_.querySelector('.lfr-image-editor-image-container canvas.image-preview');

		this.rawImageDataPromise_ = new CancellablePromise((resolve, reject) => {
			let bufferImage = new Image();

			bufferImage.onload = () => {
				let bufferCanvas = document.createElement('canvas');
	            let bufferContext = bufferCanvas.getContext('2d');

	            let rawImageWidth = bufferImage.width;
	            let rawImageHeight = bufferImage.height;

				bufferCanvas.width = rawImageWidth;
				bufferCanvas.height = rawImageHeight;

				bufferContext.drawImage(bufferImage, 0, 0, rawImageWidth, rawImageHeight);

				let rawImageData = bufferContext.getImageData(0, 0, rawImageWidth, rawImageHeight);

				resolve(rawImageData);
			};

			bufferImage.src = imageURL;
		});
	}

	enablePreview() {
		let previewContext = this.previewCanvas_.getContext('2d');

		this.previewCanvas_.height = this.imageDOMNode_.height;
		this.previewCanvas_.width = this.imageDOMNode_.width;

		previewContext.drawImage(this.imageDOMNode_, 0, 0, this.previewCanvas_.width, this.previewCanvas_.height);

		this.previewImageData_ = previewContext.getImageData(0, 0, this.previewCanvas_.width, this.previewCanvas_.height);
	}

	/**
	 * [getImageData description]
	 *
	 * @return {CancellablePromise} [description]
	 */
	getImageData() {
		return new CancellablePromise((resolve, reject) => {
			this.rawImageDataPromise_.then((rawImageData) => {
				resolve({
					preview: this.previewImageData_,
					raw: rawImageData,
					toURL: this.imageData2DataURL_,
					url: this.imageURL_
				});
			});
		});

		return this.imageDataPromise_;
	}

	/**
	 * [renderPreview description]
	 * @param  {[type]} imageData [description]
	 * @return {[type]}           [description]
	 */
	preview(imageData) {
		let previewContext = this.previewCanvas_.getContext('2d');

		previewContext.putImageData(imageData, 0, 0);
	}

	imageData2DataURL_(imageData) {
		let canvas = document.createElement('canvas');
		canvas.width = imageData.width;
		canvas.height = imageData.height;

		let ctx = canvas.getContext('2d');
		ctx.putImageData(imageData, 0, 0);

		return canvas.toDataURL();
	}
}

export default ImageEditorUtils;