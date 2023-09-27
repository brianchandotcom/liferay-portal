/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getTimezoneOffsetHour} from '../utils/date';

/**
 * Generates a local helper function to fetch information from DOM elements
 * @param {string} selector A CSS selector query string
 * @param {string} attribute The element attribute to get
 * @returns {string} Value of the specified attribute
 */
function getAttribute(selector, attribute) {
	const tag = document.querySelector(selector) || {};

	return tag[attribute] || '';
}

/**
 * Updates context with general page information
 * @param {Object} request Request object to alter
 * @returns {Object} The updated request object
 */
function meta(request) {
	Object.assign(request.context, {
		canonicalUrl: getAttribute('link[rel=canonical]', 'href'),
		contentLanguageId: getAttribute('html', 'lang'),
		description: getAttribute('meta[name="description"]', 'content'),
		keywords: getAttribute('meta[name="keywords"]', 'content'),
		languageId: navigator.language,
		referrer: document.referrer,
		timezoneOffset: getTimezoneOffsetHour(),
		title: getAttribute('title', 'textContent'),
		url: location.href,
		userAgent: navigator.userAgent,
	});

	return request;
}

export {meta};
export default meta;
