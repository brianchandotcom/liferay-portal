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

import {closest, getClosestAssetElement} from '../utils/assets';
import {DEBOUNCE} from '../utils/constants';
import {debounce} from '../utils/debounce';
import {clickEvent, onReady} from '../utils/events';
import {ScrollTracker} from '../utils/scroll';

const applicationId = 'Custom';

/**
 * Returns analytics payload with Custom Asset information.
 * @param {object} custom The custom asset DOM element
 * @return {object} The payload with custom information
 */
function getCustomAssetPayload({dataset}) {
	const {
		analyticsAssetCategory: category,
		analyticsAssetId: assetId,
		analyticsAssetTitle: title
	} = dataset;

	return {
		assetId,
		category,
		title
	};
}

/**
 * Wether a Custom Asset is trackable or not.
 * @param {object} element The Custom DOM element
 * @return {boolean} True if the element is trackable.
 */
function isTrackableCustomAsset(element) {
	return element && 'analyticsAssetId' in element.dataset;
}

/**
 * Sends information when user clicks on a Custom Asset.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetDownloaded(analytics) {
	const onClick = ({target}) => {
		const actionElement = closest(
			target,
			'[data-analytics-asset-action="download"]'
		);

		const customAssetElement = getClosestAssetElement(target, 'custom');

		if (actionElement && isTrackableCustomAsset(customAssetElement)) {
			analytics.send(
				'assetDownloaded',
				applicationId,
				getCustomAssetPayload(customAssetElement)
			);
		}
	};

	document.addEventListener('click', onClick);

	return () => document.removeEventListener('click', onClick);
}

/**
 * Sends information about Custom Asset scroll actions.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetScroll(analytics, customAssetElements) {
	const scrollSessionId = new Date().toISOString();
	const scrollTracker = new ScrollTracker();

	const onScroll = debounce(() => {
		customAssetElements.forEach(element => {
			scrollTracker.onDepthReached(depth => {
				analytics.send('assetDepthReached', applicationId, {
					...getCustomAssetPayload(element),
					depth,
					sessionId: scrollSessionId
				});
			}, element);
		});
	}, DEBOUNCE);

	document.addEventListener('scroll', onScroll);

	return () => {
		document.removeEventListener('scroll', onScroll);
	};
}

/**
 * Adds an event listener for a Custom Asset submission and sends information when that
 * event happens.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetSubmitted(analytics) {
	const onSubmit = event => {
		const {target} = event;
		const customAssetElement = getClosestAssetElement(target, 'custom');

		if (
			!isTrackableCustomAsset(customAssetElement) ||
			(isTrackableCustomAsset(customAssetElement) &&
				(target.tagName !== 'FORM' || event.defaultPrevented))
		) {
			return;
		}

		analytics.send(
			'assetSubmitted',
			applicationId,
			getCustomAssetPayload(customAssetElement)
		);
	};

	document.addEventListener('submit', onSubmit);

	return () => document.removeEventListener('submit', onSubmit);
}

/**
 * Sends information when user scrolls on a Custom asset.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetViewed(analytics) {
	const customAssetElements = [];
	const stopTrackingOnReady = onReady(() => {
		Array.prototype.slice
			.call(
				document.querySelectorAll(
					'[data-analytics-asset-type="custom"]'
				)
			)
			.filter(element => isTrackableCustomAsset(element))
			.forEach(element => {
				const formEnabled =
					element.getElementsByTagName('form').length > 0;
				const payload = {
					...getCustomAssetPayload(element),
					formEnabled
				};

				customAssetElements.push(element);

				analytics.send('assetViewed', applicationId, payload);
			});
	});

	const stopTrackingCustomAssetScroll = trackCustomAssetScroll(
		analytics,
		customAssetElements
	);

	return () => {
		stopTrackingCustomAssetScroll();
		stopTrackingOnReady();
	};
}

/**
 * Sends information when user clicks on a Custom Asset.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetClick(analytics) {
	return clickEvent({
		analytics,
		applicationId,
		eventType: 'assetClicked',
		getPayload: getCustomAssetPayload,
		isTrackable: isTrackableCustomAsset,
		type: 'custom'
	});
}

/**
 * Plugin function that registers listeners for Custom Asset events
 * @param {object} analytics The Analytics client
 */
function custom(analytics) {
	const stopTrackingClicked = trackCustomAssetClick(analytics);
	const stopTrackingDownloaded = trackCustomAssetDownloaded(analytics);
	const stopTrackingSubmitted = trackCustomAssetSubmitted(analytics);
	const stopTrackingViewed = trackCustomAssetViewed(analytics);

	return () => {
		stopTrackingClicked();
		stopTrackingDownloaded();
		stopTrackingSubmitted();
		stopTrackingViewed();
	};
}

export {custom};
export default custom;
