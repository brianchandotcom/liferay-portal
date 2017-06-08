import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

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
	 * case the same saturationValue is requested again for the same image.
	 *
	 * @param  {Object} imageData An object with several image representations.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image for preview.
	 */
	preview(imageData) {
		let saturationValue = this.components.saturationSlider.value;
		let promise = this.cache_[saturationValue];

		if (!promise) {
			promise = this.spawnWorker_({
				saturationValue: saturationValue,
				imageData: imageData.preview
			});

			this.cache_[saturationValue] = promise;
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
		let saturationValue = this.components.saturationSlider.value;

		return new CancellablePromise((resolve, reject) => {
			this.spawnWorker_({
				saturationValue: saturationValue,
				imageData: imageData.raw
			})
				.then(result => resolve(imageData.toURL(result)));
		});
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

// Register component
Soy.register(SaturationComponent, componentTemplates);

export default SaturationComponent;