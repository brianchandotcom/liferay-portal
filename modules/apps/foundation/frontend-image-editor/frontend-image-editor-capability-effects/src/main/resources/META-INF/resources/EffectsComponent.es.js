import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import async from 'metal/src/async/async';
import core from 'metal/src/core';
import { CancellablePromise } from 'metal-promise/src/promise/Promise';

import componentTemplates from './EffectsComponent.soy';
import controlsTemplates from './EffectsControls.soy';

/**
 * Effects Component
 */
class EffectsComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.cache_ = {};

		async.nextTick(() => {
			this.getImageEditorImageData()
				.then((imageData) => CancellablePromise.resolve(this.generateThumbnailImageData_(imageData)))
				.then((previewImageData) => this.generateThumbnails_(previewImageData))
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
	 * Generates a specific thumbnail for a given effect.
	 *
	 * @param  {String} effect The effect to generate the thumbnail for.
	 * @param  {ImageData} imageData The image data to apply the effect to.
	 * @return {CancellablePromise} A promise to be fullfilled when the
	 * thumbnail has been generated.
	 */
	generateThumbnail_(effect, imageData) {
		let promise = this.spawnWorker_({
			effect: effect,
			imageData: imageData
		});

		promise.then((imageData) => {
			let canvas = this.element.querySelector('#' + this.ref + effect + ' canvas');
			canvas.getContext('2d').putImageData(imageData, 0, 0);
		});

		return promise;
	}

	/**
	 * Generates the complete set of thumbnails for the component effects.
	 *
	 * @param  {ImageData} imageData The thumbnail image data (small version)
	 * @return {CancellablePromise} A promise to be fullfilled when all thumbnails
	 * have been generated.
	 */
	generateThumbnails_(imageData) {
		return CancellablePromise.all(
			this.effects.map(effect => this.generateThumbnail_(effect, imageData))
		);
	}

	/**
	 * Generates a resized version of the image data to generate the
	 * thumbnails more efficiently.
	 *
	 * @param  {ImageData} imageData The original image data
	 * @return {ImageData} The resized image data
	 */
	generateThumbnailImageData_(imageData) {
		let thumbnailSize = this.thumbnailSize;
		let imageWidth = imageData.width;
		let imageHeight = imageData.height;

		let rawCanvas = document.createElement('canvas');
		rawCanvas.width = imageWidth;
		rawCanvas.height = imageHeight;
		rawCanvas.getContext('2d').putImageData(imageData, 0, 0);

		let commonSize = imageWidth > imageHeight ? imageHeight : imageWidth;

		let canvas = document.createElement('canvas');
		canvas.width = thumbnailSize;
		canvas.height = thumbnailSize;

		let context = canvas.getContext('2d');
		context.drawImage(rawCanvas, imageWidth - commonSize, imageHeight - commonSize, commonSize, commonSize, 0, 0, thumbnailSize, thumbnailSize);

		return context.getImageData(0, 0, thumbnailSize, thumbnailSize);
	}

	/**
	 * Starts optimistically prefetching all the effect results.
	 *
	 * @return {CancellablePromise} A promise to be fullfilled when all
	 * the effects have been prefetched
	 */
	prefetchEffects_() {
		return new CancellablePromise((resolve, reject) => {
			if (!this.isDisposed()) {
				let missingEffects = this.effects.filter((effect) => !this.cache_[effect]);

				if (!missingEffects.length) {
					resolve();
				} else {
					this.getImageEditorImageData()
						.then((imageData) => this.process(imageData, missingEffects[0]))
						.then(() => this.prefetchEffects_());
				}
			}
		});
	}

	/**
	 * Applies the selected effect to the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	preview(imageData) {
		return this.process(imageData);
	}

	/**
	 * Notifies the editor that the component wants to generate a new
	 * preview of the current image.
	 *
	 * @param  {MouseEvent} event
	 */
	previewEffect(event) {
		this.currentEffect_ = event.delegateTarget.getAttribute('data-effect');
		this.requestImageEditorPreview();
	}

	/**
	 * Applies the selected effect to the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @param {String} effectName The effect to apply to the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	process(imageData, effectName) {
		let effect = effectName || this.currentEffect_;
		let promise = this.cache_[effect];

		if (!promise) {
			promise = this.spawnWorker_({
				effect: effect,
				imageData: imageData
			});

			this.cache_[effect] = promise;
		}

		return promise;
	}

	/**
	 * Makes the caousel scroll left to reveal options off the visible area
	 * 
	 * @return void
	 */ 
	scrollLeft() {
		const carousel = this.getCarouselElement();
		if (!carousel) {
			return;
		}
		const itemWidth = this.getCarouselItemElement().offsetWidth || 0;
		const marginLeft = parseInt(carousel.style.marginLeft || 0, 10);
		if (marginLeft < 0) {
			const newMarginValue = Math.min(marginLeft + itemWidth, 0);
			carousel.style.marginLeft =  newMarginValue + 'px';
		}
	}

	/**
	 * Makes the caousel scroll right to reveal options off the visible area
	 * 
	 * @return void
	 */ 
	scrollRight() {
		const carousel = this.getCarouselElement();
		if (!carousel) {
			return;
		}
		if (!this.canScrollForward()) {
			return;
		}
		const itemWidth = this.getCarouselItemElement().offsetWidth || 0;
		const marginLeft = parseInt(carousel.style.marginLeft || 0, 10);
		carousel.style.marginLeft = (marginLeft - itemWidth) + 'px';
	}

	/**
	 * Returns the carousel element with lazy search
	 * 
	 * @return {DOMElement} element that incorporates content of the carousel
	 */ 
	getCarouselElement() {
		if (!this.cache_.carouselElm) {
			this.cache_.carouselElm = this.element.querySelector('#' + this.ref + '_carousel');
		}
		return this.cache_.carouselElm;
	}

	/**
	 * Returns the first carousel item element with lazy search
	 * 
	 * @return {DOMElement} first child element of the carousel item. 
	 * This is mainly fetched to get informations about the items the carousel
	 * takes in.
	 */ 
	getCarouselItemElement() {
		if (!this.cache_.carouselItemElm) {
			const carousel = this.getCarouselElement() || {};
			this.cache_.carouselItemElm = carousel.querySelector('li.item') || {};
		}
		return this.cache_.carouselItemElm;
	}

	/**
	 * Returns the parent element of the carousel with lazy search
	 * 
	 * @return {DOMElement} element that incorporates the carousel
	 */ 
	getCarouselContainerElement() { 
		if (!this.cache_.carouselContainerElm) {
			const carousel = this.getCarouselElement();
			this.cache_.carouselContainerElm = carousel.parentNode || carousel.parentElement;
		}
		return this.cache_.carouselContainerElm;
	}

	/**
	 * Returns whether the carousel can be scrolled towards the right
	 *
	 * @return {boolean} returns true if the carousel can be scrolled, false otherwise
	 */
	canScrollForward() {
		const carousel = this.getCarouselElement();
		const continer = this.getCarouselContainerElement();
		const offset = Math.abs(parseInt(carousel.style.marginLeft || 0, 10));
		const viewportWidth = parseInt(continer.offsetWidth, 10);
		const maxContentWidth = parseInt(carousel.offsetWidth, 10);
		return offset + viewportWidth < maxContentWidth;
	}

	/**
	 * Spawns the a webworker to do the image processing in a different thread.
	 *
	 * @param  {String} workerURI URI of the worker to spawn.
	 * @param  {Object} message An object with the image and effect preset.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new CancellablePromise((resolve, reject) => {
			let processWorker = new Worker(this.modulePath + '/EffectsWorker.js');

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
EffectsComponent.STATE = {
	/**
	 * Array of available effects
	 * @type {Object}
	 */
	effects: {
		validator: core.isArray,
		value: ['none', 'ruby', 'absinthe', 'chroma', 'atari', 'tripel', 'ailis', 'flatfoot', 'pyrexia', 'umbra', 'rouge', 'idyll', 'glimmer', 'elysium', 'nucleus', 'amber', 'paella', 'aureus', 'expanse', 'orchid']
	},

	/**
	 * Injected helper to get the editor image data
	 * @type {Function}
	 */
	getImageEditorImageData: {
		validator: core.isFunction
	},

	/**
	 * Path of this module
	 * @type {Function}
	 */
	modulePath: {
		validator: core.isString
	},

	/**
	 * Injected helper to get the editor image data
	 * @type {Function}
	 */
	requestImageEditorPreview: {
		validator: core.isFunction
	},

	/**
	 * Size of the thumbnails. (size x size)
	 * @type {Number}
	 */
	thumbnailSize: {
		validator: core.isNumber,
		value: 55
	}
};

// Register component
Soy.register(EffectsComponent, componentTemplates);

export default EffectsComponent;