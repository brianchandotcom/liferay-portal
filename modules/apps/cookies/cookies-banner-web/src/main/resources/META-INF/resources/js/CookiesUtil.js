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
	return Liferay.Util.Cookie.get(name, Liferay.Util.Cookie.TYPES.NECESSARY);
}

export function setCookie(name, value) {
	Liferay.Util.Cookie.set(name, value, Liferay.Util.Cookie.TYPES.NECESSARY);
}

export function setUserConfigCookie() {
	setCookie(userConfigCookieName, 'true');
}
