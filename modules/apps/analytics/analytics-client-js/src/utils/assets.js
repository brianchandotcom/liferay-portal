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

/**
 * Returns first webContent element ancestor of given element.
 * @param {Object} element The DOM element
 * @returns {Object} The webContent element
 */
function getClosestAssetElement(element, assetType) {
	return closest(element, `[data-analytics-asset-type="${assetType}"]`);
}

function closest(element, selector) {
	let closest = null;

	if (element.closest) {
		closest = element.closest(selector);
	}
	if (!document.documentElement.contains(element)) {
		closest = null;
	}
	do {
		if (element.matches(selector)) {
			closest = element;
		}

		element = element.parentElement || element.parentNode;
	} while (element !== null && element.nodeType === 1);

	return closest;
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

export {closest, getClosestAssetElement, getNumberOfWords};

/**
 * Polyfill for .matches in IE11
 */
if (!Element.prototype.matches) {
	Element.prototype.matches = Element.prototype.msMatchesSelector;
}
