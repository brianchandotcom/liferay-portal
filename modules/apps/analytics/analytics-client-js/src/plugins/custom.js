/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {closest, getClosestAssetElement, isTrackable} from '../utils/assets';
import {CUSTOM, DEBOUNCE} from '../utils/constants';
import {debounce} from '../utils/debounce';
import {clickEvent, onReady} from '../utils/events';
import {ScrollTracker} from '../utils/scroll';

const applicationId = CUSTOM;

/**
 * Returns analytics payload with Custom Asset information.
 * @param {Object} custom The custom asset DOM element
 * @returns {Object} The payload with custom information
 */
function getCustomAssetPayload({dataset}) {
	const payload = {
		assetId: dataset.analyticsAssetId.trim(),
		category: dataset.analyticsAssetCategory,
	};

	if (dataset.analyticsAssetTitle) {
		Object.assign(payload, {title: dataset.analyticsAssetTitle.trim()});
	}

	return payload;
}

/**
 * Sends information when user clicks on a Custom Asset.
 * @param {Object} analytics The Analytics client instance
 */
function trackCustomAssetDownloaded(analytics) {
	const onClick = ({target}) => {
		const actionElement = closest(
			target,
			'[data-analytics-asset-action="download"]'
		);

		const customAssetElement = getClosestAssetElement(target, 'custom');

		if (
			actionElement &&
			isTrackable(customAssetElement, [
				'analyticsAssetId',
				'analyticsAssetType',
			])
		) {
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
 * @param {Object} analytics The Analytics client instance
 */
function trackCustomAssetScroll(analytics, customAssetElements) {
	const scrollSessionId = new Date().toISOString();
	const scrollTracker = new ScrollTracker();

	const onScroll = debounce(() => {
		customAssetElements.forEach((element) => {
			scrollTracker.onDepthReached((depth) => {
				const payload = getCustomAssetPayload(element);
				Object.assign(payload, {depth, sessionId: scrollSessionId});

				analytics.send('assetDepthReached', applicationId, payload);
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
 * @param {Object} analytics The Analytics client instance
 */
function trackCustomAssetSubmitted(analytics) {
	const onSubmit = (event) => {
		const {target} = event;
		const customAssetElement = getClosestAssetElement(target, 'custom');

		if (
			!isTrackable(customAssetElement) ||
			(isTrackable(customAssetElement) &&
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
 * @param {Object} analytics The Analytics client instance
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
			.filter((element) => isTrackable(element))
			.forEach((element) => {
				const formEnabled = !!element.getElementsByTagName('form')
					.length;

				const payload = getCustomAssetPayload(element);
				Object.assign(payload, {formEnabled});

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
 * @param {Object} analytics The Analytics client instance
 */
function trackCustomAssetClick(analytics) {
	return clickEvent({
		analytics,
		applicationId,
		eventType: 'assetClicked',
		getPayload: getCustomAssetPayload,
		isTrackable,
		type: 'custom',
	});
}

/**
 * Plugin function that registers listeners for Custom Asset events
 * @param {Object} analytics The Analytics client
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
