/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Returns first webContent element ancestor of given element.
 * @param {Object} element The DOM element
 * @returns {Object} The webContent element
 */
function getClosestAssetElement(element, assetType) {
	return closest(element, `[data-analytics-asset-type="${assetType}"]`);
}

function closest(element, selector) {
	if (element.closest) {
		return element.closest(selector);
	}
	if (!document.documentElement.contains(element)) {
		return null;
	}

	do {
		if (element.matches(selector)) {
			return element;
		}

		element = element.parentElement || element.parentNode;
	} while (element !== null && element.nodeType === 1);

	return null;
}

/**
 * Return all words from an element
 * @param {Object} element
 * @returns {number} the total of words
 */
function getNumberOfWords({innerText}) {
	const words = innerText.split(/\s+/).filter(Boolean);

	return innerText !== '' ? words.length : 0;
}

function isTrackable(element, customDatasetList) {
	const datasetList = customDatasetList || [
		'analyticsAssetId',
		'analyticsAssetTitle',
		'analyticsAssetType',
	];

	return (
		element && datasetList.every((dataset) => dataset in element.dataset)
	);
}

export {closest, getClosestAssetElement, getNumberOfWords, isTrackable};

/**
 * Polyfill for .matches in IE11
 */
if (!Element.prototype.matches) {
	Element.prototype.matches = Element.prototype.msMatchesSelector;
}
