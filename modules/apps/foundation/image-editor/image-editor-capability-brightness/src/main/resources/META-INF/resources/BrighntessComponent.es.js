import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import core from 'metal/src/core';
import { CancellablePromise } from 'metal-promise/src/promise/Promise';
import Slider from 'metal-slider/src/Slider';

import componentTemplates from './BrightnessComponent.soy';
import controlsTemplates from './BrightnessControls.soy';

/**
 * Brightness Component
 */
class BrightnessComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.cache_ = {};
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this.cache_ = {};
	}

	/**
	 * Applies the brighntess filter to generate a final version of the
	 * image. It caches intermediate results to avoid processing again in
	 * case the same brightnessValue is requested again for the same image.
	 *
	 * @param  {Object} imageData An object with several image representations.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image for preview.
	 */
	preview(imageData) {
		let brightnessValue = this.components.brightnessSlider.value;
		let promise = this.cache_[brightnessValue];

		if (!promise) {
			promise = this.spawnWorker_({
				brightnessValue: brightnessValue,
				imageData: imageData.preview
			});

			this.cache_[brightnessValue] = promise;
		}

		return promise;
	}

	/**
	 * Applies the brighntess filter to generate a final
	 * version of the image.
	 *
	 * @param  {Object} imageData An object with several image representations.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image for preview.
	 */
	process(imageData) {
		let brightnessValue = this.components.brightnessSlider.value;

		return new CancellablePromise((resolve, reject) => {
			this.spawnWorker_({
				brightnessValue: brightnessValue,
				imageData: imageData.raw
			})
				.then(result => resolve(imageData.toURL(result)));
		});
	}

	/**
	 * Spawns the a webworker to do the image processing in a different thread.
	 *
	 * @param  {Object} message An object with the image and brightness value.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new CancellablePromise((resolve, reject) => {
			let workerURI = this.modulePath + '/BrightnessWorker.js';
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
BrightnessComponent.STATE = {
	/**
	 * Path of this module
	 * @type {Function}
	 */
	modulePath: {
		validator: core.isString
	}
};

// Register component
Soy.register(BrightnessComponent, componentTemplates);

export default BrightnessComponent;