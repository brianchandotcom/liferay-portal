/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

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
		this.dataPromise_ = new Promise(resolve => {
			// Preemtively fetch the imageData when all we have is the image url

			if (image.url && !image.data) {
				this.loadData_(image.url).then(imageData => resolve(imageData));
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
		return new Promise(resolve => {
			const bufferImage = new Image();

			bufferImage.onload = () => {
				const bufferCanvas = document.createElement('canvas');
				const bufferContext = bufferCanvas.getContext('2d');

				const height = bufferImage.height;
				const width = bufferImage.width;

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
	 * @return {Promise} A promise that will resolve with the stored ImageData value
	 * @review
	 */
	getImageData() {
		return this.dataPromise_;
	}
}

export default ImageEditorHistoryEntry;
