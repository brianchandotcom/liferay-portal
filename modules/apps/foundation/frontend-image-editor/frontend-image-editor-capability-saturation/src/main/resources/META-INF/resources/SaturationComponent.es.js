import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import core from 'metal/src/core';
import debounce from 'metal-debounce/src/debounce';
import { CancellablePromise } from 'metal-promise/src/promise/Promise';
import Slider from 'metal-slider/src/Slider';

import componentTemplates from './SaturationComponent.soy';
import controlsTemplates from './SaturationControls.soy';

/**
 * Saturation Component
 */
class SaturationComponent extends Component {
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
	 * Applies a saturation filter to the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	preview(imageData) {
		return this.process(imageData);
	}

	/**
	 * Applies a saturation filter to the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	process(imageData) {
		let saturationValue = this.components.slider.value;
		let promise = this.cache_[saturationValue];

		if (!promise) {
			promise = this.spawnWorker_({
				saturationValue: saturationValue,
				imageData: imageData
			});

			this.cache_[saturationValue] = promise;
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
	 * @param  {Object} message An object with the image and saturation value.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new CancellablePromise((resolve, reject) => {
			let workerURI = this.modulePath + '/SaturationWorker.js';
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
SaturationComponent.STATE = {
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
Soy.register(SaturationComponent, componentTemplates);

export default SaturationComponent;