import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import core from 'metal/src/core';
import { CancellablePromise } from 'metal-promise/src/promise/Promise';

import componentTemplates from './ResizeComponent.soy';
import controlsTemplates from './ResizeControls.soy';

/**
 * Resize Component
 */
class ResizeComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.getImageEditorImageData()
			.then((imageData) => {
				this.imageWidth = imageData.width;
				this.imageHeight = imageData.height;

				this.imageRatio_ = this.imageWidth / this.imageHeight;

				this.imageHeightInput_ = this.element.querySelector('#' + this.key + 'Height');
				this.imageWidthInput_ = this.element.querySelector('#' + this.key + 'Width');

				this.lockProportions = true;
			});
	}

	/**
	 * Executes the resize operation to get the final version of the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve with the
	 * resized image data representation.
	 */
	process(imageData) {
		return CancellablePromise.resolve(this.resizeImageData_(imageData));
	}

	/**
	 * Resizes a given ImageData to the user selected width and height values.
	 *
	 * @param  {ImageData} imageData The original ImageData
	 * @return {ImageData} Resized ImageData to the component width and
	 * height user selected values.
	 */
	resizeImageData_(imageData) {
		let rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageData.width;
		rawCanvas.height = imageData.height;

		rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

		let canvas = document.createElement('canvas');
		canvas.width = this.imageWidth;
		canvas.height = this.imageHeight;

		let context = canvas.getContext('2d');
		context.drawImage(rawCanvas, 0, 0, this.imageWidth, this.imageHeight);

		return context.getImageData(0, 0, this.imageWidth, this.imageHeight);
	}

	/**
	 * Keeps the width/height ratio when the lockProportions is set to true.
	 *
	 * @param  {InputEvent} event
	 */
	syncDimensions(event) {
		let newValue = parseInt(event.delegateTarget.value, 10);

		if (event.delegateTarget === this.imageWidthInput_) {
			this.imageWidth = newValue;

			if (this.lockProportions) {
				this.imageHeight = parseInt((newValue / this.imageRatio_), 10);
				this.imageHeightInput_.value = this.imageHeight;
			}
		} else {
			this.imageHeight = newValue;

			if (this.lockProportions) {
				this.imageWidth = parseInt((newValue * this.imageRatio_), 10);
				this.imageWidthInput_.value = this.imageWidth;
			}
		}
	}

	/**
	 * Toggles the value of the lockProportions attribute. When enabled, changes
	 * in one of the dimensions will cascade changes to the other in order to keep
	 * the original image ratio.
	 *
	 * @param  {MouseEvent} event
	 */
	toggleLockProportions(event) {
		this.lockProportions = !this.lockProportions;
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
ResizeComponent.STATE = {
	/**
	 * Injected helper to get the editor image data
	 * @type {Function}
	 */
	getImageEditorImageData: {
		validator: core.isFunction
	}
};

// Register component
Soy.register(ResizeComponent, componentTemplates);

export default ResizeComponent;