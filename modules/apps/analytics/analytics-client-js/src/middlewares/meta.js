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

import {getTimezoneOffsetHour} from '../utils/date';

/**
 * Generates a local helper function to fetch information from DOM elements
 * @param {string} selector A CSS selector query string
 * @param {string} attribute The element attribute to get
 * @return {string} Value of the specified attribute
 */
function getAttribute(selector, attribute) {
	const tag = document.querySelector(selector) || {};

	return tag[attribute] || '';
}

/**
 * Updates context with general page information
 * @param {object} request Request object to alter
 * @return {object} The updated request object
 */
function meta(request) {
	request.context = {
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
		...request.context
	};

	return request;
}

export {meta};
export default meta;
