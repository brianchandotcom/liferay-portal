import Component from 'metal-component';
import Slider from 'metal-slider';
import Soy from 'metal-soy';
import debounce from 'metal-debounce';
import { CancellablePromise } from 'metal-promise';
import { core } from 'metal';

import componentTemplates from './ContrastComponent.soy';
import controlsTemplates from './ContrastControls.soy';

/**
 * Contrast Component
 */
class ContrastComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		// Debounced version of requestImageEditorPreview
		this.requestImageEditorPreview_ = debounce(this.requestImageEditorPreview, 50);

		this.cache_ = {};
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this.cache_ = {};
	}

	/**
	 * Applies a contrast filter to the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	preview(imageData) {
		return this.process(imageData);
	}

	/**
	 * Applies a contrast filter to the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	process(imageData) {
		let contrastValue = this.components.slider.value;
		let promise = this.cache_[contrastValue];

		if (!promise) {
			promise = this.spawnWorker_({
				contrastValue: contrastValue,
				imageData: imageData
			});

			this.cache_[contrastValue] = promise;
		}

		return promise;
	}

	/**
	 * Notifies the editor that this component wants to generate
	 * a different preview version of the current image. It debounces
	 * the calls
	 */
	requestPreview() {
		this.requestImageEditorPreview_();
	}

	/**
	 * Spawns the a webworker to do the image processing in a different thread.
	 *
	 * @param  {Object} message An object with the image and contrast value.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new CancellablePromise((resolve, reject) => {
			let workerURI = this.modulePath + '/ContrastWorker.js';
			let processWorker = new Worker(workerURI);

			processWorker.onmessage = (event) => resolve(event.data);
			processWorker.postMessage(message);
		});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
ContrastComponent.STATE = {
	/**
	 * Path of this module
	 * @type {String}
	 */
	modulePath: {
		validator: core.isString
	},

	/**
	 * Injected method to notify the editor this component
	 * wants to generate a preview version of the image.
	 * @type {Function}
	 */
	requestImageEditorPreview: {
		validator: core.isFunction
	}
};

// Register component
Soy.register(ContrastComponent, componentTemplates);

export default ContrastComponent;