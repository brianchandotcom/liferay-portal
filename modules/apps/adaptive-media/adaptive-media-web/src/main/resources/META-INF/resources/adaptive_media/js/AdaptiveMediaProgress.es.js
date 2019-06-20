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

import 'clay-progress-bar';
import {PortletBase, fetch} from 'frontend-js-web';
import Soy from 'metal-soy';
import core from 'metal';

import templates from './AdaptiveMediaProgress.soy';

/**
 * Handles the actions of the configuration entry's progressbar.
 *
 * @abstract
 * @extends {PortletBase}
 */

class AdaptiveMediaProgress extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.id_ = this.namespace + 'AdaptRemaining' + this.uuid + 'Progress';

		this.updateProgressBar_(this.adaptedImages, this.totalImages);

		if (this.autoStartProgress) {
			this.startProgress();
		}
	}

	/**
	 * It starts checking the percentage of adapted images by
	 * doing ajax request continously.
	 *
	 * @param  {String} backgroundTaskUrl The background task
	 * that has to be invoked.
	 */
	startProgress(backgroundTaskUrl) {
		if (
			this.percentage_ >= 100 ||
			this.totalImages === 0 ||
			this.disabled
		) {
			return;
		}

		if (backgroundTaskUrl) {
			fetch(backgroundTaskUrl);
		}

		this.clearInterval_();

		this.intervalId_ = setInterval(
			this.getAdaptedImagesPercentage_.bind(this),
			this.intervalSpeed
		);

		this.showLoadingIndicator_ = true;

		this.emit('start', {uuid: this.uuid});
	}

	/**
	 * Clears the interval to stop sending ajax requests.
	 *
	 * @protected
	 */
	clearInterval_() {
		if (this.intervalId_) {
			clearInterval(this.intervalId_);
		}
	}

	/**
	 * Sends an ajax request to obtain the percentage of
	 * adapted images and updates the progressbar.
	 *
	 * @protected
	 */
	getAdaptedImagesPercentage_() {
		fetch(this.percentageUrl)
			.then(res => res.json())
			.then(json => {
				this.updateProgressBar_(json.adaptedImages, json.totalImages);

				if (this.percentage_ >= 100) {
					this.onProgressBarComplete_();
				}
			})
			.catch(() => {
				clearInterval(this.intervalId_);
			});
	}

	/**
	 * Stops sending ajax request and hides the loading icon.
	 *
	 * @protected
	 */
	onProgressBarComplete_() {
		this.clearInterval_();
		this.showLoadingIndicator_ = false;

		this.emit('finish', {uuid: this.uuid});
	}

	/**
	 * Updates the progressbar
	 *
	 * @param  {Number} progress progressbar value
	 * @protected
	 */
	updateProgressBar_(adaptedImages, totalImages) {
		this.percentage_ = Math.round((adaptedImages / totalImages) * 100) || 0;
		this.progressBarTooltip_ = this.tooltip
			? this.tooltip
			: adaptedImages + '/' + totalImages;
	}
}

/**
 * AdaptiveMedia State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
AdaptiveMediaProgress.STATE = {
	/**
	 * Number of adapted images in the platform.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	autoStartProgress: {
		validator: core.isBoolean,
		value: false
	},

	/**
	 * Number of adapted images in the platform.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	adaptedImages: {
		validator: core.isNumber
	},

	/**
	 * Indicates if the entry is disabled or not.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Boolean}
	 */
	disabled: {
		validator: core.isBoolean,
		value: false
	},

	/**
	 * The interval (in milliseconds) on how often
	 * we will check the percentage of adapted images.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	intervalSpeed: {
		validator: core.isNumber,
		value: 1000
	},

	/**
	 * Percentage of adapted images.
	 *
	 * @memberof AdaptiveMediaProgress
	 * @protected
	 * @type {Number}
	 */
	percentage_: {
		validator: core.isNumber
	},

	/**
	 * Url to the action that returns the percentage
	 * of adapted images.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {String}
	 */
	percentageUrl: {
		validator: core.isString
	},

	/**
	 * The progress bar tooltip. If AdaptiveMediaProgress.tooltip
	 * is defined, this will be used.
	 *
	 * @memberof AdaptiveMediaProgress
	 * @protected
	 * @type {String}
	 */
	progressBarTooltip_: {
		validator: core.isString
	},

	/**
	 * If the loading indicator has to be shown
	 * near the progress bar.
	 *
	 * @memberof AdaptiveMediaProgress
	 * @protected
	 * @type {Boolean}
	 */
	showLoadingIndicator_: {
		validator: core.isBoolean,
		value: false
	},

	/**
	 * The path to the SVG spritemap file containing the icons.
	 * @memberof AdaptiveMediaProgress
	 * @protected
	 * @type {String}
	 */
	spritemap: {
		validator: core.isString
	},

	/**
	 * Number of images present in the platform.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {Number}
	 */
	totalImages: {
		validator: core.isNumber
	},

	/**
	 * The tooltip text to show in the progress bar.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {String}
	 */
	tooltip: {
		validator: core.isString
	},

	/**
	 * Configuration entry's uuid.
	 *
	 * @instance
	 * @memberof AdaptiveMediaProgress
	 * @type {String}
	 */
	uuid: {
		validator: core.isString
	}
};

// Register component

Soy.register(AdaptiveMediaProgress, templates);

export default AdaptiveMediaProgress;
