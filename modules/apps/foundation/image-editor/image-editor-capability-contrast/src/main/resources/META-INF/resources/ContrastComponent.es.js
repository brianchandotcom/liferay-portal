import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import { CancellablePromise } from 'metal-promise/src/promise/Promise';
import Slider from 'metal-slider/src/Slider';

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
	 * case the same contrastValue is requested again for the same image.
	 *
	 * @param  {Object} imageData An object with several image representations.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image for preview.
	 */
	preview(imageData) {
		let contrastValue = this.components.contrastSlider.value;
		let promise = this.cache_[contrastValue];

		if (!promise) {
			promise = this.spawnWorker_({
				contrastValue: contrastValue,
				imageData: imageData.preview
			});

			this.cache_[contrastValue] = promise;
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
		let contrastValue = this.components.contrastSlider.value;

		return new CancellablePromise((resolve, reject) => {
			this.spawnWorker_({
				contrastValue: contrastValue,
				imageData: imageData.raw
			})
				.then(result => resolve(imageData.toURL(result)));
		});
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

// Register component
Soy.register(ContrastComponent, componentTemplates);

export default ContrastComponent;