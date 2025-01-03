/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	closest,
	getClosestAssetElement,
	isTrackable,
	transformAssetTypeToSelector,
} from '../utils/assets';
import {DOCUMENT} from '../utils/constants';
import {onReady} from '../utils/events';

const applicationId = DOCUMENT;

export const documentType =
	'com.liferay.portal.kernel.repository.model.FileEntry';

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

	if (dataset.analyticsAssetSubtype) {
		Object.assign(payload, {subtype: dataset.analyticsAssetSubtype.trim()});
	}

	if (dataset.analyticsAssetTitle) {
		Object.assign(payload, {title: dataset.analyticsAssetTitle.trim()});
	}

	if (dataset.analyticsAssetType) {
		Object.assign(payload, {type: dataset.analyticsAssetType.trim()});
	}

	return payload;
}

/**
 * Sends information when user clicks on a Document.
 * @param {Object} The Analytics client instance
 */
function trackDocumentDownloaded(analytics) {
	const onClick = ({target}) => {
		if (isTrackable(target) && target.dataset.analyticsAssetAction === 'download') {
			analytics.send(
				'documentDownloaded',
				applicationId,
				getDocumentPayload(target)
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
function trackDocument(analytics, {eventId, isTrackable}) {
	const selector = transformAssetTypeToSelector(documentType);

	const stopTrackingOnReady = onReady(() => {
		Array.prototype.slice
			.call(document.querySelectorAll(selector))
			.filter((element) => isTrackable(element))
			.forEach((element) => {
				const payload = getDocumentPayload(element);

				analytics.send(eventId, applicationId, payload);
			});
	});

	return () => stopTrackingOnReady();
}

/**
 * Plugin function that registers listeners for Document events.
 * A link with action download should fire a documentImpressionMade event
 * on load page to the documentsFragment plugin.
 * @param {Object} analytics The Analytics client
 */
function documents(analytics) {
	const stopTrackingDocumentDownloaded = trackDocumentDownloaded(analytics);
	const stopTrackingDocumentImpressionMade = trackDocument(analytics, {
		eventId: 'documentImpressionMade',
		isTrackable: (element) =>
			(isTrackable(element) &&
				element.dataset.analyticsAssetAction === 'impression') ||
			(isTrackable(element) &&
				element.dataset.analyticsAssetAction === 'download'),
	});
	const stopTrackingDocumentPreviewed = trackDocument(analytics, {
		eventId: 'documentPreviewed',
		isTrackable: (element) =>
			isTrackable(element) &&
			element.dataset.analyticsAssetAction === 'view',
	});

	return () => {
		stopTrackingDocumentDownloaded();
		stopTrackingDocumentImpressionMade();
		stopTrackingDocumentPreviewed();
	};
}

export {documents};
export default documents;
