import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import async from 'metal/src/async/async';
import core from 'metal/src/core';
import { CancellablePromise } from 'metal-promise/src/promise/Promise';

import componentTemplates from './FilterComponent.soy';
import controlsTemplates from './FilterControls.soy';

/**
 * Filter Component
 */
class FilterComponent extends Component {
	/**
	 * @inheritDoc
	 */
	attached() {
		this.cache_ = {};

		async.nextTick(() => {
			this.getImageEditorImageData()
				.then((imageData) => CancellablePromise.resolve(this.generateThumbnailImageData_(imageData)))
				.then((previewImageData) => this.generateThumbnails_(previewImageData))
				.then(() => this.prefetchFilters_());
		});
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		this.cache_ = {};
	}

	/**
	 * Generates a specific thumbnail for a given filter.
	 *
	 * @param  {String} filter The filter to generate the thumbnail for.
	 * @param  {ImageData} imageData The image data to apply the filter to.
	 * @return {CancellablePromise} A promise to be fullfilled when the
	 * thumbnail has been generated.
	 */
	generateThumbnail_(filter, imageData) {
		let promise = this.spawnWorker_({
			filter: filter,
			imageData: imageData
		});

		promise.then((imageData) => {
			let canvas = this.element.querySelector('#' + this.key + filter + ' canvas');
			canvas.getContext('2d').putImageData(imageData, 0, 0);
		});

		return promise;
	}

	/**
	 * Generates the complete set of thumbnails for the component filters.
	 *
	 * @param  {ImageData} imageData The thumbnail image data (small version)
	 * @return {CancellablePromise} A promise to be fullfilled when all thumbnails
	 * have been generated.
	 */
	generateThumbnails_(imageData) {
		return CancellablePromise.all(
			this.filters.map(filter => this.generateThumbnail_(filter, imageData))
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
	 * Starts optimistically prefetching all the filter results.
	 *
	 * @return {CancellablePromise} A promise to be fullfilled when all
	 * the filters have been prefetched
	 */
	prefetchFilters_() {
		return new CancellablePromise((resolve, reject) => {
			if (!this.isDisposed()) {
				let missingFilters = this.filters.filter((filter) => !this.cache_[filter]);

				if (!missingFilters.length) {
					resolve();
				} else {
					this.getImageEditorImageData()
						.then((imageData) => this.process(imageData, missingFilters[0]))
						.then(() => this.prefetchFilters_());
				}
			}
		});
	}

	/**
	 * Applies the selected filter to the image.
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
	previewFilter(event) {
		this.currentFilter_ = event.delegateTarget.getAttribute('data-filter');
		this.requestImageEditorPreview();
	}

	/**
	 * Applies the selected filter to the image.
	 *
	 * @param  {ImageData} imageData ImageData representation of the image.
	 * @param {String} filterName The filter to apply to the image.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	process(imageData, filterName) {
		let filter = filterName || this.currentFilter_;
		let promise = this.cache_[filter];

		if (!promise) {
			promise = this.spawnWorker_({
				filter: filter,
				imageData: imageData
			});

			this.cache_[filter] = promise;
		}

		return promise;
	}

	/**
	 * Spawns the a webworker to do the image processing in a different thread.
	 *
	 * @param  {String} workerURI URI of the worker to spawn.
	 * @param  {Object} message An object with the image and filter preset.
	 * @return {CancellablePromise} A promise that will resolve when the webworker
	 * finishes processing the image.
	 */
	spawnWorker_(message) {
		return new CancellablePromise((resolve, reject) => {
			let processWorker = new Worker(this.modulePath + '/FilterWorker.js');

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
FilterComponent.STATE = {
	/**
	 * Array of available filters
	 * @type {Object}
	 */
	filters: {
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
Soy.register(FilterComponent, componentTemplates);

export default FilterComponent;