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

export function acceptAllCookies() {
	setCookie('CONSENT_TYPE_FUNCTIONAL', 'true');
	setCookie('CONSENT_TYPE_PERFORMANCE', 'true');
	setCookie('CONSENT_TYPE_PERSONALIZATION', 'true');
	setCookie('CONSENT_TYPE_STRICTLY_NECESSARY', 'true');
}

export function declineAllCookies() {
	setCookie('CONSENT_TYPE_FUNCTIONAL', 'false');
	setCookie('CONSENT_TYPE_PERFORMANCE', 'false');
	setCookie('CONSENT_TYPE_PERSONALIZATION', 'false');
	setCookie('CONSENT_TYPE_STRICTLY_NECESSARY', 'true');
}

export function getCookie(name) {
	const cookieName = name + '=';
	const cookieSet = document.cookie.split(';');

	for (let i = 0; i < cookieSet.length; i++) {
		let c = cookieSet[i];

		while (c.charAt(0) === ' ') {
			c = c.substring(1, c.length);
		}

		if (c.indexOf(cookieName) === 0) {
			return c.substring(cookieName.length, c.length);
		}
	}

	return null;
}

export function setCookie(name, value, days = 180) {
	const date = new Date();

	date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);

	const expires = '; expires=' + date.toUTCString();

	document.cookie = name + '=' + (value || '') + expires + '; path=/';
}
