import { CancellablePromise } from 'metal-promise';

/**
 * Image Editor History Entry
 *
 * This class models a step in the image edition process. It stores the
 * Image data at a given point in time so it can be later recovered for
 * undo/redo purposes or other visualization needs.
 * @review
 */
class ImageEditorHistoryEntry {
	/**
	 * Constructor
	 * @review
	 */
	constructor(image) {
		this.dataPromise_ = new CancellablePromise((resolve, reject) => {
			// Preemtively fetch the imageData when all we have is the image url
			if (image.url && !image.data) {
				this.loadData_(image.url)
					.then((imageData) => resolve(imageData));
			}
			else {
				resolve(image.data);
			}
		});
	}

	/**
	 * Fetches an ImageData for a given image url
	 * @param  {String} imageURL The image url to load
	 * @protected
	 * @review
	 */
	loadData_(imageURL) {
		return new CancellablePromise((resolve, reject) => {
			let bufferImage = new Image();

			bufferImage.onload = () => {
				let bufferCanvas = document.createElement('canvas');
				let bufferContext = bufferCanvas.getContext('2d');

				let height = bufferImage.height;
				let width = bufferImage.width;

				bufferCanvas.width = width;
				bufferCanvas.height = height;

				bufferContext.drawImage(bufferImage, 0, 0, width, height);

				resolve(bufferContext.getImageData(0, 0, width, height));
			};

			bufferImage.src = imageURL;
		});
	}

	/**
	 * Fetches the stored ImageData of this history entry
	 * @return {CancellablePromise} A promise that will resolve with the stored ImageData value
	 * @review
	 */
	getImageData() {
		return this.dataPromise_;
	}
}

export default ImageEditorHistoryEntry;