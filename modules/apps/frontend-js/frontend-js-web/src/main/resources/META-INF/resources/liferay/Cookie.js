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

const CONSENT_TYPES = {
	FUNCTIONAL: 'CONSENT_TYPE_FUNCTIONAL',
	NECESSARY: 'CONSENT_TYPE_NECESSARY',
	PERFORMANCE: 'CONSENT_TYPE_PERFORMANCE',
	PERSONALIZATION: 'CONSENT_TYPE_PERSONALIZATION',
};

const generateCookie = (name, value, {secure, ...options}) =>
	`${name}=${value}; ${Object.entries(options)
		.map(([key, value]) => {
			return `${key}=${value}`;
		})
		.join('; ')} ${secure ? 'secure' : ''}`;

const Cookie = {
	_checkConsent(type) {
		return type === CONSENT_TYPES.NECESSARY || this.get(type) === 'true';
	},

	/**
	 * Gets the current value for a specific cookie
	 *
	 * @param {string} name
	 * @returns {(string|undefined)} Cookie value as a string, undefined if not present
	 */
	get(name) {
		return document.cookie
			.split('; ')
			.find((v) => v.startsWith(`${name}=`))
			?.split('=')[1];
	},

	/**
	 * Removes a cookie by expiring it
	 *
	 * @param {string} name
	 */
	remove(name) {
		document.cookie = generateCookie(name, '', 0);
	},

	/**
	 * @typedef {Object} CookieOptions
	 * @property {string} path Path that must be in the URL for the Cookie to be sent
	 * @property {string} domain Domain that must be in the URL for the Cookie to be sent
	 * @property {number|string} max-age How long until the Cookie expires, in seconds
	 * @property {number|string} expires Exact time that the Cookie should expire
	 * @property {boolean} secure Whether the cookie should only be transmitted over https
	 * @property {string} samesite Prevents the browser from sending this cookie along with cross-site requests
	 */

	/**
	 * Stores a cookie to send with every HTTP request to the
	 * server, taking user consent into account when relevant.
	 *
	 * @param {string} name
	 * @param {string} value
	 * @param {string} type Type of consent the cookie requires, from {@link Cookie.types}. If not present, we assume {@link name} is a config cookie
	 * @param {CookieOptions} options
	 * @returns {boolean} Boolean representing whether the cookie has been stored or not
	 */
	set(name, value, type, options) {
		if (this._checkConsent(type)) {
			document.cookie = generateCookie(name, value, options);

			return true;
		}

		return false;
	},

	types: CONSENT_TYPES,
};

export default Cookie;
