import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import async from 'metal/src/async/async';
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
		async.nextTick(() => {
			let image = this.getEditorImage();

			this.imageHeight = image.naturalHeight;
			this.imageWidth = image.naturalWidth;

			this.imageRatio_ = this.imageWidth / this.imageHeight;

			this.imageHeightInput_ = this.element.querySelector('#' + this.key + 'Height');
			this.imageWidthInput_ = this.element.querySelector('#' + this.key + 'Width');

			this.lockProportions = true;
		});
	}

	/**
	 * Executes the resize operation to get the final version of the image.
	 *
	 * @param  {Object} imageData An object with several image representations.
	 * @return {CancellablePromise} A promise that will resolve with the resized image URL representation.
	 */
	process(imageData) {
		let rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageData.raw.width;
		rawCanvas.height = imageData.raw.height;

		rawCanvas.getContext('2d').putImageData(imageData.raw, 0, 0);

		let canvas = document.createElement('canvas');
		canvas.width = this.imageWidth;
		canvas.height = this.imageHeight;

		canvas.getContext('2d').drawImage(rawCanvas, 0, 0, this.imageWidth, this.imageHeight);

		return CancellablePromise.resolve(canvas.toDataURL());
	}

	/**
	 * Keeps the width/height ratio when the lockProportions is set to true.
	 *
	 * @param  {InputEvent} event
	 */
	syncDimensions(event) {
		if (this.lockProportions) {
			let newValue = event.delegateTarget.value;

			if (event.delegateTarget === this.imageWidthInput_) {
				this.imageHeight = newValue / this.imageRatio_;
				this.imageWidth = newValue;

				this.imageHeightInput_.value = this.imageHeight;
			} else {
				this.imageHeight = newValue;
				this.imageWidth = newValue * this.imageRatio_;

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
	 * Injected helper to get the editor image
	 * @type {Function}
	 */
	getEditorImage: {
		validator: core.isFunction
	}
};

// Register component
Soy.register(ResizeComponent, componentTemplates);

export default ResizeComponent;