import Component from 'metal-component';
import Soy from 'metal-soy';
import {core} from 'metal';

import componentTemplates from './CropComponent.soy';
import controlsTemplates from './CropControls.soy';

/**
 * Creates a Crop component.
 */
class CropComponent extends Component {
	/**
	 * Applies the brightness filter to generate a final version of the image.
	 *
	 * @param  {Object} imageData The image representations.
	 * @return {Promise} A promise that resolves when the webworker
	 * finishes processing the image for preview.
	 */
	process(imageData) {
		const imageCanvas = this.getImageEditorCanvas();

		var horizontalRatio = imageData.width / imageCanvas.offsetWidth;
		var verticalRatio = imageData.height / imageCanvas.offsetHeight;

		const cropHandles = this.components[this.ref + 'CropHandles'];
		const selection = {
			height: cropHandles.element.offsetHeight,
			left: cropHandles.element.offsetLeft - imageCanvas.offsetLeft,
			top: cropHandles.element.offsetTop - imageCanvas.offsetTop,
			width: cropHandles.element.offsetWidth
		};

		const rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageData.width;
		rawCanvas.height = imageData.height;

		rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

		const canvas = document.createElement('canvas');
		const normalizedLeft = selection.left * horizontalRatio;
		const normalizedWidth = selection.width * horizontalRatio;
		const normalizedTop = selection.top * verticalRatio;
		const normalizedHeight = selection.height * verticalRatio;

		canvas.width = normalizedWidth;
		canvas.height = normalizedHeight;

		const context = canvas.getContext('2d');
		context.drawImage(
			rawCanvas,
			normalizedLeft,
			normalizedTop,
			normalizedWidth,
			normalizedHeight,
			0,
			0,
			normalizedWidth,
			normalizedHeight
		);

		cropHandles.dispose();

		return Promise.resolve(
			context.getImageData(0, 0, canvas.width, canvas.height)
		);
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
CropComponent.STATE = {
	/**
	 * Injected helper that retrieves the editor canvas.
	 *
	 * @type {Function}
	 */
	getImageEditorCanvas: {
		validator: core.isFunction
	}
};

Soy.register(CropComponent, componentTemplates);

export default CropComponent;
