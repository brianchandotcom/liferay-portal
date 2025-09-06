/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {checkConsent, getOpener} from 'frontend-js-web';

import {
	acceptAllCookies,
	declineAllCookies,
	getCookie,
	productAnalyticsConfiguredCookieName,
	setCookie,
	setProductAnalyticsConfigCookie,
} from '../../js/CookiesUtil';

export default function ({
	namespace,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
}) {
	const acceptAllButton = document.getElementById(
		`${namespace}acceptAllButton`
	);
	const customizeButton = document.getElementById(
		`${namespace}customizeButton`
	);
	const declineAllButton = document.getElementById(
		`${namespace}declineAllButton`
	);
	const productAnalyticsBanner = document.querySelector(
		'.product-analytics-banner'
	);
	const editMode = document.body.classList.contains('has-edit-mode-menu');

	if (!editMode) {
		setBannerVisibility(productAnalyticsBanner);

		const cookiePreferences = {};

		optionalConsentCookieTypeNames.forEach(
			(optionalConsentCookieTypeName) => {
				cookiePreferences[optionalConsentCookieTypeName] =
					getCookie(optionalConsentCookieTypeName) || 'false';
			}
		);

		Liferay.on('cookiePreferenceUpdate', (event) => {
			cookiePreferences[event.key] = event.value;
		});

		acceptAllButton.addEventListener('click', () => {
			productAnalyticsBanner.style.display = 'none';

			acceptAllCookies(
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);

			setProductAnalyticsConfigCookie();
		});

		if (declineAllButton !== null) {
			declineAllButton.addEventListener('click', () => {
				productAnalyticsBanner.style.display = 'none';

				declineAllCookies(
					optionalConsentCookieTypeNames,
					requiredConsentCookieTypeNames
				);

				setProductAnalyticsConfigCookie();
			});
		}
	}
}

function checkProductAnalyticsConsentForTypes(cookieTypes, modalOptions) {
	return new Promise((resolve, reject) => {
		if (isCookieTypesAccepted(cookieTypes)) {
			resolve();
		}
	});
}

function isCookieTypesAccepted(cookieTypes) {
	if (!Array.isArray(cookieTypes)) {
		cookieTypes = [cookieTypes];
	}

	return cookieTypes.every((cookieType) => checkConsent(cookieType));
}

function setBannerVisibility(productAnalyticsBanner) {
	if (getCookie(productAnalyticsConfiguredCookieName)) {
		productAnalyticsBanner.style.display = 'none';
	}
	else {
		productAnalyticsBanner.style.display = 'block';
	}
}

export {checkProductAnalyticsConsentForTypes};
