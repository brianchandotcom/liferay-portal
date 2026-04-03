/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOpener} from 'frontend-js-web';

import {
	acceptAllCookies,
	declineAllCookies,
	getCookie,
	hasPreviouslyStoredConsent,
	setCookie,
	setUserConfigCookie,
	userConfigCookieName,
} from '../../js/CookiesUtil';

export default function ({
	consentRenewalPeriod,
	consentRenewalPeriodTimeUnit,
	dissentRenewalPeriod,
	dissentRenewalPeriodTimeUnit,
	namespace,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
	showButtons,
}) {
	const storeConsentCheckbox = document.getElementById(
		`${namespace}storeConsent`
	);

	if (storeConsentCheckbox !== null) {
		const notifyStoreConsentPreferenceUpdate = () =>
			getOpener().Liferay.fire('storeCookiesConsentPreferenceUpdate', {
				value: storeConsentCheckbox.checked,
			});

		storeConsentCheckbox.addEventListener(
			'change',
			notifyStoreConsentPreferenceUpdate
		);

		storeConsentCheckbox.removeAttribute('disabled');

		hasPreviouslyStoredConsent().then((storeConsent) => {
			storeConsentCheckbox.checked = storeConsent;
		});
	}

	const toggleSwitches = Array.from(
		document.querySelectorAll(
			`#${namespace}cookiesBannerConfigurationForm [data-cookie-key]`
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

		toggleSwitch.checked = toggleSwitch.dataset.prechecked;

		getCookie(userConfigCookieName).then((cookie) => {
			if (cookie) {
				getCookie(cookieKey).then((cookie) => {
					toggleSwitch.checked = cookie === 'true';
				});
			}
		});

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

		if (dissentRenewalPeriod === 0) {
			dissentRenewalPeriod = consentRenewalPeriod;
			dissentRenewalPeriodTimeUnit = consentRenewalPeriodTimeUnit;
		}

		acceptAllButton.addEventListener('click', () => {
			acceptAllCookies(
				consentRenewalPeriod,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames,
				storeConsentCheckbox?.checked,
				consentRenewalPeriodTimeUnit
			);

			setUserConfigCookie(
				consentRenewalPeriod,
				storeConsentCheckbox?.checked,
				consentRenewalPeriodTimeUnit
			);

			window.location.reload();
		});

		acceptSelectedButton.addEventListener('click', () => {
			toggleSwitches.forEach((toggleSwitch) => {
				let renewalPeriod = consentRenewalPeriod;
				let timeUnit = consentRenewalPeriodTimeUnit;

				if (!toggleSwitch.checked) {
					renewalPeriod = dissentRenewalPeriod;
					timeUnit = dissentRenewalPeriodTimeUnit;
				}

				setCookie(
					renewalPeriod,
					toggleSwitch.dataset.cookieKey,
					storeConsentCheckbox?.checked,
					timeUnit,
					toggleSwitch.checked ? 'true' : 'false'
				);
			});

			requiredConsentCookieTypeNames.forEach(
				(requiredConsentCookieTypeName) => {
					setCookie(
						consentRenewalPeriod,
						requiredConsentCookieTypeName,
						storeConsentCheckbox?.checked,
						consentRenewalPeriodTimeUnit,
						'true'
					);
				}
			);

			setUserConfigCookie(
				consentRenewalPeriod,
				storeConsentCheckbox?.checked,
				consentRenewalPeriodTimeUnit
			);

			window.location.reload();
		});

		useNecessaryCookiesOnlyButton.addEventListener('click', () => {
			declineAllCookies(
				consentRenewalPeriod,
				consentRenewalPeriodTimeUnit,
				dissentRenewalPeriod,
				dissentRenewalPeriodTimeUnit,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames,
				storeConsentCheckbox?.checked
			);

			setUserConfigCookie(
				consentRenewalPeriod,
				storeConsentCheckbox?.checked,
				consentRenewalPeriodTimeUnit
			);

			window.location.reload();
		});
	}
}
