/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {closest, getClosestAssetElement, isTrackable} from '../utils/assets';
import {DOCUMENT} from '../utils/constants';
import {onReady} from '../utils/events';

const applicationId = DOCUMENT;

/**
 * Returns analytics payload with Document information.
 * @param {Object} documentElement The document DOM element
 * @returns {Object} The payload with document information
 */
function getDocumentPayload({dataset}) {
	const payload = {
		fileEntryId: dataset.analyticsAssetId.trim(),
		fileEntryVersion: dataset.analyticsAssetVersion,
	};

	if (dataset.analyticsAssetTitle) {
		Object.assign(payload, {title: dataset.analyticsAssetTitle.trim()});
	}

	return payload;
}

/**
 * Sends information when user clicks on a Document.
 * @param {Object} The Analytics client instance
 */
function trackDocumentDownloaded(analytics) {
	const onClick = ({target}) => {
		const actionElement = closest(
			target,
			'[data-analytics-asset-action="download"]'
		);

		const documentElement = getClosestAssetElement(target, 'document');

		if (actionElement && isTrackable(documentElement)) {
			analytics.send(
				'documentDownloaded',
				applicationId,
				getDocumentPayload(documentElement)
			);
		}
	};

	document.addEventListener('click', onClick);

	return () => document.removeEventListener('click', onClick);
}

/**
 * Sends information when user scrolls on a Document.
 * @param {Object} The Analytics client instance
 */
function trackDocumentPreviewed(analytics) {
	const stopTrackingOnReady = onReady(() => {
		Array.prototype.slice
			.call(
				document.querySelectorAll(
					'[data-analytics-asset-action="preview"]'
				)
			)
			.filter((element) => isTrackable(element))
			.forEach((element) => {
				const payload = getDocumentPayload(element);

				analytics.send('documentPreviewed', applicationId, payload);
			});
	});

	return () => stopTrackingOnReady();
}

/**
 * Plugin function that registers listeners for Document events
 * @param {Object} analytics The Analytics client
 */
function documents(analytics) {
	const stopTrackingDocumentDownloaded = trackDocumentDownloaded(analytics);
	const stopTrackingDocumentPreviewed = trackDocumentPreviewed(analytics);

	return () => {
		stopTrackingDocumentDownloaded();
		stopTrackingDocumentPreviewed();
	};
}

export {documents};
export default documents;
