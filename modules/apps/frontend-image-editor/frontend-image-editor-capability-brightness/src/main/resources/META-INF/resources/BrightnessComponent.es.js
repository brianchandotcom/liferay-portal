import Component from 'metal-component';
import Slider from 'frontend-js-web/liferay/compat/slider/Slider.es';
import Soy from 'metal-soy';
import debounce from 'metal-debounce';
import {CancellablePromise} from 'metal-promise';
import {core} from 'metal';

import componentTemplates from './BrightnessComponent.soy';
import controlsTemplates from './BrightnessControls.soy';

/**
 * Creates a Brightness component.
 */
class BrightnessComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.requestImageEditorPreview_ = debounce(
			this.requestImageEditorPreview,
			50
		);

		this.cache_ = {};
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this.cache_ = {};
	}

	/**
	 * Applies a brightness filter to the image.
	 *
	 * @param  {ImageData} imageData The image data representation of the image.
	 * @return {CancellablePromise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	preview(imageData) {
		return this.process(imageData);
	}

	/**
	 * Applies a brightness filter to the image.
	 *
	 * @param  {ImageData} imageData The image data representation of the image.
	 * @return {CancellablePromise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	process(imageData) {
		let brightnessValue = this.components.slider.value;
		let promise = this.cache_[brightnessValue];

		if (!promise) {
			promise = this.spawnWorker_({
				brightnessValue: brightnessValue,
				imageData: imageData
			});

			this.cache_[brightnessValue] = promise;
		}

		return promise;
	}

	/**
	 * Notifies the editor that this component wants to generate
	 * a different preview version of the current image. It debounces
	 * the calls.
	 */
	requestPreview() {
		this.requestImageEditorPreview_();
	}

	/**
	 * Spawns a webworker to process the image in a different thread.
	 *
	 * @param  {Object} message The image and brightness value.
	 * @return {CancellablePromise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new CancellablePromise((resolve, reject) => {
			let workerURI = this.modulePath + '/BrightnessWorker.js';
			let processWorker = new Worker(workerURI);

			processWorker.onmessage = event => resolve(event.data);
			processWorker.postMessage(message);
		});
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
BrightnessComponent.STATE = {
	/**
	 * Path of this module.
	 *
	 * @type {String}
	 */
	modulePath: {
		validator: core.isString
	},

	/**
	 * Injected method that notifies the editor that the component wants to
	 * generate a preview version of the image.
	 *
	 * @type {Function}
	 */
	requestImageEditorPreview: {
		validator: core.isFunction
	}
};

Soy.register(BrightnessComponent, componentTemplates);

export default BrightnessComponent;
