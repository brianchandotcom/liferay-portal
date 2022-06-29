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

export const userConfigCookieName = 'USER_CONSENT_CONFIGURED';

export function acceptAllCookies(
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames
) {
	optionalConsentCookieTypeNames.forEach((optionalConsentCookieTypeName) => {
		setCookie(optionalConsentCookieTypeName, 'true');
	});

	requiredConsentCookieTypeNames.forEach((requiredConsentCookieTypeName) => {
		setCookie(requiredConsentCookieTypeName, 'true');
	});
}

export function declineAllCookies(
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames
) {
	optionalConsentCookieTypeNames.forEach((optionalConsentCookieTypeName) => {
		setCookie(optionalConsentCookieTypeName, 'false');
	});

	requiredConsentCookieTypeNames.forEach((requiredConsentCookieTypeName) => {
		setCookie(requiredConsentCookieTypeName, 'true');
	});
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

export function setCookie(name, value) {
	Liferay.Util.Cookie.set(name, value, Liferay.Util.Cookie.TYPES.NECESSARY);
}

export function setUserConfigCookie() {
	setCookie(userConfigCookieName, 'true');
}
