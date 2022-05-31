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

import {getNumberOfWords} from '../utils/assets';
import {WEB_CONTENT} from '../utils/constants';
import {debounce} from '../utils/debounce';
import {clickEvent, onEvents, onReady} from '../utils/events';
import {isPartiallyInViewport} from '../utils/scroll';

const applicationId = WEB_CONTENT;

/**
 * Returns analytics payload with WebContent information.
 * @param {Object} webContent The webContent DOM element
 * @returns {Object} The payload with webContent information
 */
function getWebContentPayload(webContent) {
	const {dataset} = webContent;

	const payload = {
		articleId: dataset.analyticsAssetId,
	};

	if (dataset.analyticsAssetTitle) {
		Object.assign(payload, {title: dataset.analyticsAssetTitle});
	}

	return payload;
}

/**
 * Wether a WebContent is trackable or not.
 * @param {Object} element The WebContent DOM element
 * @returns {boolean} True if the element is trackable.
 */
function isTrackableWebContent(element) {
	return element && 'analyticsAssetId' in element.dataset;
}

/**
 * Sends information when user clicks on a Web Content.
 * @param {Object} The Analytics client instance
 */
function trackWebContentClicked(analytics) {
	return clickEvent({
		analytics,
		applicationId,
		eventType: 'webContentClicked',
		getPayload: getWebContentPayload,
		isTrackable: isTrackableWebContent,
		type: 'web-content',
	});
}

/**
 * Sends information the first time a WebContent enters into the viewport.
 * @param {Object} The Analytics client instance
 */
function trackWebContentViewed(analytics) {
	const markViewedElements = debounce(() => {
		const elements = Array.prototype.slice
			.call(
				document.querySelectorAll(
					'[data-analytics-asset-type="web-content"]:not([data-analytics-asset-viewed="true"]'
				)
			)
			.filter((element) => isTrackableWebContent(element));

		elements.forEach((element) => {
			if (isPartiallyInViewport(element)) {
				const payload = getWebContentPayload(element);

				Object.assign(payload, {
					numberOfWords: getNumberOfWords(element),
				});

				element.dataset.analyticsAssetViewed = true;

				analytics.send('webContentViewed', applicationId, payload);
			}
		});
	}, 250);

	const stopTrackingOnReady = onReady(markViewedElements);
	const stopTrackingEvents = onEvents(
		['scroll', 'resize', 'orientationchange'],
		markViewedElements
	);

	return () => {
		stopTrackingEvents();
		stopTrackingOnReady();
	};
}

/**
 * Plugin function that registers listeners for Web Content events
 * @param {Object} analytics The Analytics client
 */
function webContent(analytics) {
	const stopTrackingWebContentClicked = trackWebContentClicked(analytics);
	const stopTrackingWebContentViewed = trackWebContentViewed(analytics);

	return () => {
		stopTrackingWebContentClicked();
		stopTrackingWebContentViewed();
	};
}

export {webContent};
export default webContent;
