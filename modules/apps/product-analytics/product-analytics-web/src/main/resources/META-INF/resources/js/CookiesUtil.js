/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	COOKIE_TYPES,
	getCookie as getCookieUtil,
	getOpener,
	removeCookie as removeCookieUtil,
	setCookie as setCookieUtil,
} from 'frontend-js-web';

export const productAnalyticsConfiguredCookieName =
	'PRODUCT_ANALYTICS_CONFIGURED';
export const productAnalyticsConfiguredDateCookieName =
	'PRODUCT_ANALYTICS_CONFIGURED_DATE';
export const userConfigCookieName = 'USER_CONSENT_CONFIGURED';

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

export function setCookie(consentRenewalPeriod, name, value) {
	setCookieUtil(name, value, COOKIE_TYPES.NECESSARY, {
		'max-age': 60 * 60 * 24 * 365 * (consentRenewalPeriod / 12),
		'path': themeDisplay.getPathContext() || '/',
	});
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

	removeCookieUtil(productAnalyticsConfiguredCookieName);
	removeCookieUtil(productAnalyticsConfiguredDateCookieName);
}

export function setProductAnalyticsConfigCookie(
	consentRenewalPeriod,
	lastModified
) {
	setCookie(
		consentRenewalPeriod,
		productAnalyticsConfiguredCookieName,
		'true'
	);

	setCookie(
		consentRenewalPeriod,
		productAnalyticsConfiguredDateCookieName,
		lastModified
	);

	getOpener()?.Liferay.fire('productAnalyticsBannerSetCookie');
}

export function setUserConfigCookie(consentRenewalPeriod) {
	setCookie(consentRenewalPeriod, userConfigCookieName, 'true');

	getOpener()?.Liferay.fire('cookieBannerSetCookie');
}
