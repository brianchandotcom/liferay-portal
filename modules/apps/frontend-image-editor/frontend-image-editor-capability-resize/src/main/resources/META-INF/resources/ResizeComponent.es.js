import Component from 'metal-component';
import Soy from 'metal-soy';
import {CancellablePromise} from 'metal-promise';
import {core} from 'metal';

import componentTemplates from './ResizeComponent.soy';
import controlsTemplates from './ResizeControls.soy';

/**
 * Creates a Resize component.
 */
class ResizeComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.getImageEditorImageData().then(imageData => {
			this.imageWidth = imageData.width;
			this.imageHeight = imageData.height;

			this.imageRatio_ = this.imageWidth / this.imageHeight;

			this.imageHeightInput_ = this.element.querySelector(
				'#' + this.ref + 'Height'
			);
			this.imageWidthInput_ = this.element.querySelector(
				'#' + this.ref + 'Width'
			);

			this.lockProportions = true;
		});
	}

	/**
	 * Executes the resize operation to get the final version of the image.
	 *
	 * @param  {ImageData} imageData The image data representation of the image.
	 * @return {CancellablePromise} A promise that resolves with the resized
	 * image data representation.
	 */
	process(imageData) {
		return CancellablePromise.resolve(this.resizeImageData_(imageData));
	}

	/**
	 * Resizes image data to the user's selected width and height values.
	 *
	 * @param  {ImageData} imageData The original image data.
	 * @return {ImageData} The resized image data with the component width and
	 * height values specified by the user.
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
	 * Keeps the width/height ratio in sync when <code>lockProportions</code> is
	 * <code>true</code>.
	 *
	 * @param  {InputEvent} event The input event.
	 */
	syncDimensions(event) {
		let newValue = parseInt(event.delegateTarget.value, 10);

		if (event.delegateTarget === this.imageWidthInput_) {
			this.imageWidth = newValue;

			if (this.lockProportions) {
				this.imageHeight = parseInt(newValue / this.imageRatio_, 10);
				this.imageHeightInput_.value = this.imageHeight;
			}
		} else {
			this.imageHeight = newValue;

			if (this.lockProportions) {
				this.imageWidth = parseInt(newValue * this.imageRatio_, 10);
				this.imageWidthInput_.value = this.imageWidth;
			}
		}
	}

	/**
	 * Toggles the value of the <code>lockProportions</code> attribute. When
	 * enabled, changes in one of the dimensions cascades changes to the other
	 * to keep the original image ratio.
	 *
	 * @param  {MouseEvent} event
	 */
	toggleLockProportions(event) {
		this.lockProportions = !this.lockProportions;
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
ResizeComponent.STATE = {
	/**
	 * Injected helper that retrieves the editor image data.
	 *
	 * @type {Function}
	 */
	getImageEditorImageData: {
		validator: core.isFunction
	}
};

Soy.register(ResizeComponent, componentTemplates);

export default ResizeComponent;
