/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	COOKIE_TYPES,
	fetch,
	getCookie as getCookieUtil,
	getOpener,
	removeCookie as removeCookieUtil,
	setCookie as setCookieUtil,
} from 'frontend-js-web';

const HEADERS = new Headers({
	'content-type': 'application/json',
});

export const userConfigCookieName = 'USER_CONSENT_CONFIGURED';
export const userConfigDateCookieName = 'USER_CONSENT_CONFIGURED_DATE';

export function acceptAllCookies(
	consentRenewalPeriod,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames
) {
	optionalConsentCookieTypeNames.forEach((optionalConsentCookieTypeName) => {
		setCookie(consentRenewalPeriod, optionalConsentCookieTypeName, 'true');
	});

	requiredConsentCookieTypeNames.forEach((requiredConsentCookieTypeName) => {
		setCookie(consentRenewalPeriod, requiredConsentCookieTypeName, 'true');
	});
}

export function declineAllCookies(
	consentRenewalPeriod,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames
) {
	optionalConsentCookieTypeNames.forEach((optionalConsentCookieTypeName) => {
		setCookie(consentRenewalPeriod, optionalConsentCookieTypeName, 'false');
	});

	requiredConsentCookieTypeNames.forEach((requiredConsentCookieTypeName) => {
		setCookie(consentRenewalPeriod, requiredConsentCookieTypeName, 'true');
	});
}

export function getCookie(name) {
	return getCookieUtil(name, COOKIE_TYPES.NECESSARY);
}

export function removeAllCookies(
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames
) {
	optionalConsentCookieTypeNames.forEach((optionalConsentCookieTypeName) => {
		removeCookieUtil(optionalConsentCookieTypeName);
	});

	requiredConsentCookieTypeNames.forEach((requiredConsentCookieTypeName) => {
		removeCookieUtil(requiredConsentCookieTypeName);
	});

	removeCookieUtil(userConfigDateCookieName);
}

export function setCookie(consentRenewalPeriod, name, value) {
	const expirationInSeconds =
		60 * 60 * 24 * 365 * (consentRenewalPeriod / 12);

	setCookieUtil(name, value, COOKIE_TYPES.NECESSARY, {
		'max-age': expirationInSeconds,
		'path': themeDisplay.getPathContext() || '/',
	});

	if (Liferay.FeatureFlags['LPD-75032']) {
		const expirationDate = new Date();
		expirationDate.setSeconds(
			expirationDate.getSeconds() + expirationInSeconds
		);

		const data = {
			domain: themeDisplay.getPortalURL(),
			expirationDate,
			name,
			userId: themeDisplay.getUserId(),
			value,
		};

		fetch('/o/cookies/v1.0/cookies-consent-preferences', {
			body: JSON.stringify(data),
			headers: HEADERS,
			method: 'PUT',
		});
	}
}

export function setUserConfigCookie(consentRenewalPeriod) {
	setCookie(consentRenewalPeriod, userConfigCookieName, 'true');

	setCookie(
		consentRenewalPeriod,
		userConfigDateCookieName,
		new Date().getTime()
	);

	getOpener()?.Liferay.fire('cookieBannerSetCookie');
}
