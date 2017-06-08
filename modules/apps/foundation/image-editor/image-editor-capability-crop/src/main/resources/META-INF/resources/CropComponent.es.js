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
		let cropHandles = this.components[this.key + 'CropHandles'];
		let selection = {
			top: cropHandles.element.offsetTop,
			left: cropHandles.element.offsetLeft,
			width: cropHandles.element.offsetWidth,
			height: cropHandles.element.offsetHeight
		};

		let image = this.getEditorImage();

		var horizontalRatio = imageData.raw.width / image.offsetWidth;
		var verticalRatio = imageData.raw.height / image.offsetHeight;

		let rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageData.raw.width;
		rawCanvas.height = imageData.raw.height;

		rawCanvas.getContext('2d').putImageData(imageData.raw, 0, 0);

		let canvas = document.createElement('canvas');
		canvas.width = selection.width * horizontalRatio;
		canvas.height = selection.height * verticalRatio;

		canvas.getContext('2d').drawImage(rawCanvas, selection.left * horizontalRatio, selection.top * verticalRatio, selection.width * horizontalRatio, selection.height * verticalRatio, 0, 0, selection.width * horizontalRatio, selection.height * verticalRatio);

		cropHandles.dispose();

		return CancellablePromise.resolve(canvas.toDataURL());
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
CropComponent.STATE = {
	/**
	 * Injected helper to get the editor image
	 * @type {Function}
	 */
	getEditorImage: {
		validator: core.isFunction
	}
};

// Register component
Soy.register(CropComponent, componentTemplates);

export default CropComponent;