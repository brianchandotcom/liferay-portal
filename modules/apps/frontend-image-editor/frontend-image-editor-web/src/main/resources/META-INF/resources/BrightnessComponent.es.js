/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {debounce} from 'frontend-js-web';

import './BrightnessSlider.es';

import {core} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';

import componentTemplates from './BrightnessComponent.soy';

import './BrightnessControls.soy';

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
	 * @return {Promise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	preview(imageData) {
		return this.process(imageData);
	}

	/**
	 * Applies a brightness filter to the image.
	 *
	 * @param  {ImageData} imageData The image data representation of the image.
	 * @return {Promise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	process(imageData) {
		const brightnessValue = this.components.slider.value;
		let promise = this.cache_[brightnessValue];

		if (!promise) {
			promise = this.spawnWorker_({
				brightnessValue,
				imageData,
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
	 * @return {Promise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new Promise((resolve) => {
			const workerURI = this.modulePath + '/BrightnessWorker.js';
			const processWorker = new Worker(workerURI);
			processWorker.onmessage = (event) => resolve(event.data);
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
		validator: core.isString,
	},

	/**
	 * Injected method that notifies the editor that the component wants to
	 * generate a preview version of the image.
	 *
	 * @type {Function}
	 */
	requestImageEditorPreview: {
		validator: core.isFunction,
	},
};

Soy.register(BrightnessComponent, componentTemplates);

export default BrightnessComponent;
