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

export const guestUserConfigCookieName = 'GUEST_USER_CONSENT_CONFIGURED';
export const userConfigCookieName = 'USER_CONSENT_CONFIGURED';
export const userConfigDateCookieName = 'USER_CONSENT_CONFIGURED_DATE';

export function acceptAllCookies(
	consentRenewalPeriod,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
	storeConsent,
	timeUnit
) {
	optionalConsentCookieTypeNames.forEach((optionalConsentCookieTypeName) => {
		setCookie(
			consentRenewalPeriod,
			optionalConsentCookieTypeName,
			storeConsent,
			timeUnit,
			'true'
		);
	});

	requiredConsentCookieTypeNames.forEach((requiredConsentCookieTypeName) => {
		setCookie(
			consentRenewalPeriod,
			requiredConsentCookieTypeName,
			storeConsent,
			timeUnit,
			'true'
		);
	});
}

export function declineAllCookies(
	consentRenewalPeriod,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
	storeConsent,
	timeUnit
) {
	optionalConsentCookieTypeNames.forEach((optionalConsentCookieTypeName) => {
		setCookie(
			consentRenewalPeriod,
			optionalConsentCookieTypeName,
			storeConsent,
			timeUnit,
			'false'
		);
	});

	requiredConsentCookieTypeNames.forEach((requiredConsentCookieTypeName) => {
		setCookie(
			consentRenewalPeriod,
			requiredConsentCookieTypeName,
			storeConsent,
			timeUnit,
			'true'
		);
	});
}

export function deleteStoredCookies() {
	if (
		Liferay.FeatureFlags['LPD-75032'] &&
		Liferay.ThemeDisplay.isSignedIn()
	) {
		fetch('/o/cookies/v1.0/cookies-consent-preferences/', {
			headers: HEADERS,
			method: 'DELETE',
		});
	}
}

function deleteStoredCookie(name) {
	fetch(`/o/cookies/v1.0/cookies-consent-preferences/by-name/${name}`, {
		headers: HEADERS,
		method: 'DELETE',
	});
}

async function fetchStoredCookie(name) {
	return await fetch(
		`/o/cookies/v1.0/cookies-consent-preferences/by-name/${name}`,
		{
			headers: HEADERS,
			method: 'GET',
		}
	)
		.then((response) => {
			if (response.status === 200) {
				return response.json();
			}
		})
		.then((jsonObject) => {
			if (jsonObject === undefined) {
				return undefined;
			}

			const {expirationDate, name, value} = jsonObject;

			const expirationDateObject = new Date(expirationDate);

			const expirationInSeconds = Math.floor(
				(expirationDateObject.getTime() - Date.now()) / 1000
			);

			if (expirationInSeconds > 0) {
				setCookieUtil(name, value, COOKIE_TYPES.NECESSARY, {
					'max-age': expirationInSeconds,
					'path': themeDisplay.getPathContext() || '/',
				});

				return getCookieUtil(name, COOKIE_TYPES.NECESSARY);
			}
			else {
				deleteStoredCookie(name);

				return undefined;
			}
		});
}

export async function getCookie(name) {
	const cookie = getCookieUtil(name, COOKIE_TYPES.NECESSARY);

	if (
		cookie !== undefined ||
		!Liferay.FeatureFlags['LPD-75032'] ||
		!Liferay.ThemeDisplay.isSignedIn()
	) {
		return cookie;
	}

	return await fetchStoredCookie(name);
}

export function hasGuestUserConfigCookie() {
	const cookie = getCookieUtil(
		guestUserConfigCookieName,
		COOKIE_TYPES.NECESSARY
	);

	if (cookie !== undefined) {
		removeCookieUtil(guestUserConfigCookieName);

		return true;
	}

	return false;
}

export async function hasPreviouslyStoredConsent() {
	return await fetch(
		`/o/cookies/v1.0/cookies-consent-preferences/by-name/${userConfigCookieName}`,
		{
			headers: HEADERS,
			method: 'GET',
		}
	)
		.then((response) => {
			if (response.status === 200) {
				return response.json();
			}

			return false;
		})
		.then((jsonObject) => {
			if (jsonObject !== undefined && jsonObject !== false) {
				return true;
			}

			return false;
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

	removeCookieUtil(userConfigDateCookieName);
}

export function setCookie(
	consentRenewalPeriod,
	name,
	storeConsent = false,
	timeUnit,
	value
) {
	const secondsInDay = 60 * 60 * 24;

	let maxAge = secondsInDay * 365 * (consentRenewalPeriod / 12);
	const timeUnitLowerCase = (timeUnit || 'months').toLowerCase();

	if (timeUnitLowerCase === 'days') {
		maxAge = secondsInDay * consentRenewalPeriod;
	}
	else if (timeUnitLowerCase === 'weeks') {
		maxAge = secondsInDay * 7 * consentRenewalPeriod;
	}

	setCookieUtil(name, value, COOKIE_TYPES.NECESSARY, {
		'max-age': Math.floor(maxAge),
		'path': themeDisplay.getPathContext() || '/',
	});

	if (
		Liferay.FeatureFlags['LPD-75032'] &&
		Liferay.ThemeDisplay.isSignedIn() &&
		storeConsent
	) {
		const expirationDate = new Date();
		expirationDate.setSeconds(
			expirationDate.getSeconds() + Math.floor(maxAge)
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

export function setUserConfigCookie(
	consentRenewalPeriod,
	storeConsent,
	timeUnit
) {
	if (
		Liferay.FeatureFlags['LPD-75032'] &&
		!Liferay.ThemeDisplay.isSignedIn()
	) {
		setCookie(
			consentRenewalPeriod,
			guestUserConfigCookieName,
			false,
			timeUnit,
			'true'
		);
	}

	setCookie(
		consentRenewalPeriod,
		userConfigCookieName,
		storeConsent,
		timeUnit,
		'true'
	);

	setCookie(
		consentRenewalPeriod,
		userConfigDateCookieName,
		storeConsent,
		timeUnit,
		new Date().getTime()
	);

	if (!storeConsent) {
		deleteStoredCookies();
	}

	getOpener()?.Liferay.fire('cookieBannerSetCookie');
}
