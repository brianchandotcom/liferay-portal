import Component from 'metal-component';
import Slider from 'frontend-js-web/liferay/compat/slider/Slider.es';
import Soy from 'metal-soy';
import debounce from 'metal-debounce';
import { CancellablePromise } from 'metal-promise';
import { core } from 'metal';

import componentTemplates from './ContrastComponent.soy';
import controlsTemplates from './ContrastControls.soy';

/**
 * Contrast Component
 * @review
 */
class ContrastComponent extends Component {
	/**
	 * @inheritDoc
	 * @review
	 */
	attached() {
		// Debounced version of requestImageEditorPreview
		this.requestImageEditorPreview_ = debounce(this.requestImageEditorPreview, 50);

		this.cache_ = {};
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	detached() {
		this.cache_ = {};
	}

	/**
	 * Applies a contrast filter to the image.
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 * @review
	 */
	preview(imageData) {
		return this.process(imageData);
	}

	/**
	 * Applies a contrast filter to the image.
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 * @review
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
	 * @review
	 */
	requestPreview() {
		this.requestImageEditorPreview_();
	}

	/**
	 * Spawns the a webworker to do the image processing in a different thread.
	 * @param  {Object} message An object with the image and contrast value.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 * @review
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
 * @review
 * @static
 * @type {!Object}
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
	 * @review
	 * @type {Function}
	 */
	requestImageEditorPreview: {
		validator: core.isFunction
	}
};

Soy.register(ContrastComponent, componentTemplates);

export default ContrastComponent;