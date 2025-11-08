/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOpener} from 'frontend-js-web';

import {
	acceptAllCookies,
	declineAllCookies,
	getCookie,
	productAnalyticsConfiguredCookieName,
	setCookie,
	setProductAnalyticsConfigCookie,
} from '../../js/CookiesUtil';

export default function ({
	consentRenewalPeriod,
	namespace,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
	showButtons,
}) {
	const toggleSwitches = Array.from(
		document.querySelectorAll(
			`#${namespace}productAnalyticsConsentPanelForm [data-cookie-key]`
		)
	);

	toggleSwitches.forEach((toggleSwitch) => {
		const cookieKey = toggleSwitch.dataset.cookieKey;

		const notifyCookiePreferenceUpdate = () =>
			getOpener().Liferay.fire('cookiePreferenceUpdate', {
				key: cookieKey,
				value: toggleSwitch.checked ? 'true' : 'false',
			});

		toggleSwitch.addEventListener('click', notifyCookiePreferenceUpdate);

		if (getCookie(productAnalyticsConfiguredCookieName)) {
			toggleSwitch.checked = getCookie(cookieKey) === 'true';
		}
		else {
			toggleSwitch.checked = toggleSwitch.dataset.prechecked === 'true';
		}

		notifyCookiePreferenceUpdate();

		toggleSwitch.removeAttribute('disabled');
	});

	if (showButtons) {
		const acceptAllButton = document.getElementById(
			`${namespace}acceptAllButton`
		);
		const acceptSelectedButton = document.getElementById(
			`${namespace}acceptSelectedButton`
		);
		const useNecessaryCookiesOnlyButton = document.getElementById(
			`${namespace}useNecessaryCookiesOnlyButton`
		);

		acceptAllButton.addEventListener('click', () => {
			acceptAllCookies(
				consentRenewalPeriod,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);

			setProductAnalyticsConfigCookie(consentRenewalPeriod);

			window.location.reload();
		});

		acceptSelectedButton.addEventListener('click', () => {
			toggleSwitches.forEach((toggleSwitch) => {
				setCookie(
					consentRenewalPeriod,
					toggleSwitch.dataset.cookieKey,
					toggleSwitch.checked ? 'true' : 'false'
				);
			});

			requiredConsentCookieTypeNames.forEach(
				(requiredConsentCookieTypeName) => {
					setCookie(
						consentRenewalPeriod,
						requiredConsentCookieTypeName,
						'true'
					);
				}
			);

			setProductAnalyticsConfigCookie(consentRenewalPeriod);

			window.location.reload();
		});

		useNecessaryCookiesOnlyButton.addEventListener('click', () => {
			declineAllCookies(
				consentRenewalPeriod,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);

			setProductAnalyticsConfigCookie(consentRenewalPeriod);

			window.location.reload();
		});
	}
}
