import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import core from 'metal/src/core';
import { CancellablePromise } from 'metal-promise/src/promise/Promise';

import componentTemplates from './CropComponent.soy';
import controlsTemplates from './CropControls.soy';

/**
 * Crop Component
 */
class CropComponent extends Component {
	/**
	 * Applies the brighntess filter to generate a final
	 * version of the image.
	 *
	 * @param  {Object} imageData An object with several image representations.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image for preview.
	 */
	process(imageData) {
		let imageCanvas = this.getImageEditorCanvas();

		var horizontalRatio = imageData.width / imageCanvas.offsetWidth;
		var verticalRatio = imageData.height / imageCanvas.offsetHeight;

		let cropHandles = this.components[this.key + 'CropHandles'];
		let selection = {
			height: cropHandles.element.offsetHeight,
			left: cropHandles.element.offsetLeft - imageCanvas.offsetLeft,
			top: cropHandles.element.offsetTop - imageCanvas.offsetTop,
			width: cropHandles.element.offsetWidth
		};

		let rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageData.width;
		rawCanvas.height = imageData.height;

		rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

		let canvas = document.createElement('canvas');
		let normalizedLeft = selection.left * horizontalRatio;
		let normalizedWidth = selection.width * horizontalRatio;
		let normalizedTop = selection.top * verticalRatio;
		let normalizedHeight = selection.height * verticalRatio;

		canvas.width = normalizedWidth;
		canvas.height = normalizedHeight;

		let context = canvas.getContext('2d');
		context.drawImage(rawCanvas, normalizedLeft, normalizedTop, normalizedWidth, normalizedHeight, 0, 0, normalizedWidth, normalizedHeight);

		cropHandles.dispose();

		return CancellablePromise.resolve(context.getImageData(0, 0, canvas.width, canvas.height));
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
CropComponent.STATE = {
	/**
	 * Injected helper to get the editor canvas
	 * @type {Function}
	 */
	getImageEditorCanvas: {
		validator: core.isFunction
	}
};

// Register component
Soy.register(CropComponent, componentTemplates);

export default CropComponent;