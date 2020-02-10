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
import {clickEvent, onReady} from '../utils/events';

const applicationId = 'WebContent';

/**
 * Returns analytics payload with WebContent information.
 * @param {object} webContent The webContent DOM element
 * @return {object} The payload with webContent information
 */
function getWebContentPayload(webContent) {
	const {dataset} = webContent;

	let payload = {
		articleId: dataset.analyticsAssetId
	};

	if (dataset.analyticsAssetTitle) {
		payload = {
			...payload,
			title: dataset.analyticsAssetTitle
		};
	}

	return payload;
}

/**
 * Wether a WebContent is trackable or not.
 * @param {object} element The WebContent DOM element
 * @return {boolean} True if the element is trackable.
 */
function isTrackableWebContent(element) {
	return element && 'analyticsAssetId' in element.dataset;
}

/**
 * Sends information when user clicks on a Web Content.
 * @param {object} The Analytics client instance
 */
function trackWebContentClicked(analytics) {
	return clickEvent({
		analytics,
		applicationId,
		eventType: 'webContentClicked',
		getPayload: getWebContentPayload,
		isTrackable: isTrackableWebContent,
		type: 'web-content'
	});
}

/**
 * Sends information when user scrolls on a WebContent.
 * @param {object} The Analytics client instance
 */
function trackWebContentViewed(analytics) {
	const stopTrackingOnReady = onReady(() => {
		Array.prototype.slice
			.call(
				document.querySelectorAll(
					'[data-analytics-asset-type="web-content"]'
				)
			)
			.filter(element => isTrackableWebContent(element))
			.forEach(element => {
				const numberOfWords = getNumberOfWords(element);

				let payload = getWebContentPayload(element);

				payload = {
					...payload,
					numberOfWords
				};

				analytics.send('webContentViewed', applicationId, payload);
			});
	});

	return () => stopTrackingOnReady();
}

/**
 * Plugin function that registers listeners for Web Content events
 * @param {object} analytics The Analytics client
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
