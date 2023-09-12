/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getNumberOfWords, isTrackable} from '../utils/assets';
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
function getWebContentPayload({dataset}) {
	const payload = {
		articleId: dataset.analyticsAssetId.trim(),
	};

	if (dataset.analyticsAssetTitle) {
		Object.assign(payload, {title: dataset.analyticsAssetTitle.trim()});
	}

	return payload;
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
		isTrackable,
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
			.filter((element) => isTrackable(element));

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
