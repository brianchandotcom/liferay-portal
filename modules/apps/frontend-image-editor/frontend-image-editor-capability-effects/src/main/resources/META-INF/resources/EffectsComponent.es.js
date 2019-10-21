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

import {async, core} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';

import componentTemplates from './EffectsComponent.soy';

import './EffectsControls.soy';

/**
 * Creates an Effects component.
 */
class EffectsComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.cache_ = {};

		async.nextTick(() => {
			this.getImageEditorImageData()
				.then(imageData =>
					Promise.resolve(this.generateThumbnailImageData_(imageData))
				)
				.then(previewImageData =>
					this.generateThumbnails_(previewImageData)
				)
				.then(() => this.prefetchEffects_());
		});
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this.cache_ = {};
	}

	/**
	 * Returns <code>true</code> if the carousel can be scrolled to the right.
	 *
	 * @private
	 * @return {boolean} <code>True</code> if the carousel can be scrolled to
	 * the right; <code>false</code> otherwise.
	 */
	canScrollForward_() {
		const carousel = this.refs.carousel;
		const continer = this.refs.carouselContainer;
		const offset = Math.abs(parseInt(carousel.style.marginLeft || 0, 10));
		const viewportWidth = parseInt(continer.offsetWidth, 10);
		const maxContentWidth = parseInt(carousel.offsetWidth, 10);

		return offset + viewportWidth < maxContentWidth;
	}

	/**
	 * Generates a thumbnail for a given effect.
	 *
	 * @param  {String} effect The effect to generate the thumbnail for.
	 * @param  {ImageData} imageData The image data to which the effect is applied.
	 * @return {Promise} A promise that resolves when the thumbnail
	 * is generated.
	 */
	generateThumbnail_(effect, imageData) {
		const promise = this.spawnWorker_({
			effect,
			imageData
		});

		promise.then(imageData => {
			const canvas = this.element.querySelector(
				'#' + this.ref + effect + ' canvas'
			);
			canvas.getContext('2d').putImageData(imageData, 0, 0);
		});

		return promise;
	}

	/**
	 * Generates the complete set of thumbnails for the component effects.
	 *
	 * @param  {ImageData} imageData The thumbnail image data (small version).
	 * @return {Promise} A promise that resolves when the thumbnails
	 * are generated.
	 */
	generateThumbnails_(imageData) {
		return Promise.all(
			this.effects.map(effect =>
				this.generateThumbnail_(effect, imageData)
			)
		);
	}

	/**
	 * Generates a resized version of the image data to generate the thumbnails
	 * more efficiently.
	 *
	 * @param  {ImageData} imageData The original image data.
	 * @return {ImageData} The resized image data.
	 */
	generateThumbnailImageData_(imageData) {
		const thumbnailSize = this.thumbnailSize;
		const imageWidth = imageData.width;
		const imageHeight = imageData.height;

		const rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageWidth;
		rawCanvas.height = imageHeight;
		rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

		const commonSize = imageWidth > imageHeight ? imageHeight : imageWidth;

		const canvas = document.createElement('canvas');
		canvas.width = thumbnailSize;
		canvas.height = thumbnailSize;

		const context = canvas.getContext('2d');
		context.drawImage(
			rawCanvas,
			imageWidth - commonSize,
			imageHeight - commonSize,
			commonSize,
			commonSize,
			0,
			0,
			thumbnailSize,
			thumbnailSize
		);

		return context.getImageData(0, 0, thumbnailSize, thumbnailSize);
	}

	/**
	 * Prefetches all the effect results.
	 *
	 * @return {Promise} A promise that resolves when all the effects
	 * are prefetched.
	 */
	prefetchEffects_() {
		return new Promise(resolve => {
			if (!this.isDisposed()) {
				const missingEffects = this.effects.filter(
					effect => !this.cache_[effect]
				);

				if (!missingEffects.length) {
					resolve();
				} else {
					this.getImageEditorImageData()
						.then(imageData =>
							this.process(imageData, missingEffects[0])
						)
						.then(() => this.prefetchEffects_());
				}
			}
		});
	}

	/**
	 * Applies the selected effect to the image.
	 *
	 * @param  {ImageData} imageData The image data representation of the image.
	 * @return {Promise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	preview(imageData) {
		return this.process(imageData);
	}

	/**
	 * Notifies the editor that the component wants to generate a new preview of
	 * the current image.
	 *
	 * @param  {MouseEvent} event The mouse event.
	 */
	previewEffect(event) {
		this.currentEffect_ = event.delegateTarget.getAttribute('data-effect');
		this.requestImageEditorPreview();
	}

	/**
	 * Applies the selected effect to the image.
	 *
	 * @param  {ImageData} imageData The image data representation of the image.
	 * @param {String} effectName The effect to apply to the image.
	 * @return {Promise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	process(imageData, effectName) {
		const effect = effectName || this.currentEffect_;
		let promise = this.cache_[effect];

		if (!promise) {
			promise = this.spawnWorker_({
				effect,
				imageData
			});

			this.cache_[effect] = promise;
		}

		return promise;
	}

	/**
	 * Makes the carousel scroll left to reveal options of the visible area.
	 *
	 * @return void
	 */
	scrollLeft() {
		const carousel = this.refs.carousel;
		const itemWidth = this.refs.carouselFirstItem.offsetWidth || 0;
		const marginLeft = parseInt(carousel.style.marginLeft || 0, 10);

		if (marginLeft < 0) {
			const newMarginValue = Math.min(marginLeft + itemWidth, 0);

			this.carouselOffset = newMarginValue + 'px';
		}
	}

	/**
	 * Makes the carousel scroll right to reveal options of the visible area.
	 *
	 * @return void
	 */
	scrollRight() {
		if (this.canScrollForward_()) {
			const carousel = this.refs.carousel;
			const itemWidth = this.refs.carouselFirstItem.offsetWidth || 0;
			const marginLeft = parseInt(carousel.style.marginLeft || 0, 10);

			this.carouselOffset = marginLeft - itemWidth + 'px';
		}
	}

	/**
	 * Spawns a webworker to process the image in a different thread.
	 *
	 * @param  {String} workerURI The URI of the worker to spawn.
	 * @param  {Object} message The image and effect preset.
	 * @return {Promise} A promise that resolves when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new Promise(resolve => {
			const processWorker = new Worker(
				this.modulePath + '/EffectsWorker.js'
			);

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
EffectsComponent.STATE = {
	/**
	 * Offset in pixels (<code>px</code> postfix) for the carousel item.
	 *
	 * @type {String}
	 */
	carouselOffset: {
		validator: core.isString,
		value: '0'
	},

	/**
	 * Array of available effects.
	 *
	 * @type {Object}
	 */
	effects: {
		validator: core.isArray,
		value: [
			'none',
			'ruby',
			'absinthe',
			'chroma',
			'atari',
			'tripel',
			'ailis',
			'flatfoot',
			'pyrexia',
			'umbra',
			'rouge',
			'idyll',
			'glimmer',
			'elysium',
			'nucleus',
			'amber',
			'paella',
			'aureus',
			'expanse',
			'orchid'
		]
	},

	/**
	 * Injected helper that retrieves the editor image data.
	 *
	 * @type {Function}
	 */
	getImageEditorImageData: {
		validator: core.isFunction
	},

	/**
	 * Path of this module.
	 *
	 * @type {Function}
	 */
	modulePath: {
		validator: core.isString
	},

	/**
	 * Injected helper that retrieves the editor image data.
	 *
	 * @type {Function}
	 */
	requestImageEditorPreview: {
		validator: core.isFunction
	},

	/**
	 * Size of the thumbnails (size x size).
	 *
	 * @type {Number}
	 */
	thumbnailSize: {
		validator: core.isNumber,
		value: 55
	}
};

Soy.register(EffectsComponent, componentTemplates);

export default EffectsComponent;
