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

export const COOKIE_TYPES = {
	FUNCTIONAL: 'CONSENT_TYPE_FUNCTIONAL',
	NECESSARY: 'CONSENT_TYPE_NECESSARY',
	PERFORMANCE: 'CONSENT_TYPE_PERFORMANCE',
	PERSONALIZATION: 'CONSENT_TYPE_PERSONALIZATION',
};

const generateCookie = (name, value, options = {}) => {
	let cookie = `${name}=${value}`;

	for (const [key, value] of Object.entries(options)) {
		if (key === 'secure') {
			cookie += value ? '; secure' : '';
		}

		cookie += `; ${key}=${value}`;
	}

	return cookie.trim();
};

function checkConsent(type) {
	return type === COOKIE_TYPES.NECESSARY || getCookie(type) === 'true';
}

/**
 * Gets the current value for a specific cookie
 *
 * @param {string} name
 * @returns {(string|undefined)} Cookie value as a string, undefined if not present
 */
export function getCookie(name) {
	return document.cookie
		.split('; ')
		.find((v) => v.startsWith(`${name}=`))
		?.split('=')[1];
}

/**
 * Removes a cookie by expiring it
 *
 * @param {string} name
 */
export function removeCookie(name) {
	document.cookie = generateCookie(name, '', {'max-age': 0});
}

/**
 * @typedef {Object} CookieOptions
 * @property {string} domain Domain that must be in the URL for the Cookie to be sent
 * @property {number|string} expires Exact time that the Cookie should expire
 * @property {number|string} max-age How long until the Cookie expires, in seconds
 * @property {string} path Path that must be in the URL for the Cookie to be sent
 * @property {string} samesite Prevents the browser from sending this cookie along with cross-site requests
 * @property {boolean} secure Whether the cookie should only be transmitted over https
 */

/**
 * Stores a cookie to send with every HTTP request to the
 * server, taking user consent into account when relevant.
 *
 * @param {string} name
 * @param {string} value
 * @param {string} type Type of consent the cookie requires, from {@link COOKIE_TYPES}
 * @param {CookieOptions} options
 * @returns {boolean} Boolean representing whether the cookie has been stored or not
 */
export function setCookie(name, value, type, options) {
	if (checkConsent(type)) {
		document.cookie = generateCookie(name, value, options);

		return true;
	}

	return false;
}

export default {
	TYPES: COOKIE_TYPES,
	get: getCookie,
	remove: removeCookie,
	set: setCookie,
};
