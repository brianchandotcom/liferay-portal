/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {getOpener} from 'frontend-js-web';

import {
	acceptAllCookies,
	declineAllCookies,
	getCookie,
	productAnalyticsConfiguredDateCookieName,
	removeAllCookies,
	setCookie,
	setProductAnalyticsConfigCookie,
} from '../../js/CookiesUtil';

let openProductAnalyticsConsentModal = () => {
	console.warn(
		'OpenProductAnalyticsConsentModal was called, but product analytics feature is not enabled'
	);
};

export default function ({
	configurationNamespace,
	configurationURL,
	consentRenewalPeriod = 12,
	lastModified = 0,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
	title,
}) {
	const editMode = document.body.classList.contains('has-edit-mode-menu');

	if (!editMode) {
		if (isProductAnalyticsConfigurationModified(lastModified)) {
			removeAllCookies(
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);
		}

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

		openProductAnalyticsConsentModal = ({
			alertDisplayType,
			alertMessage,
			customTitle,
			onCloseFunction,
		}) => {
			let url = configurationURL;

			if (alertDisplayType) {
				url = `${url}&_${configurationNamespace}_alertDisplayType=${alertDisplayType}`;
			}

			if (alertMessage) {
				url = `${url}&_${configurationNamespace}_alertMessage=${alertMessage}`;
			}

			openModal({
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get(
							'use-necessary-cookies-only'
						),
						onClick() {
							declineAllCookies(
								consentRenewalPeriod,
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							setProductAnalyticsConfigCookie(
								consentRenewalPeriod,
								lastModified
							);

							getOpener().Liferay.fire('closeModal');
						},
					},
					{
						displayType: 'secondary',
						label: Liferay.Language.get('accept-selected'),
						onClick() {
							Object.entries(cookiePreferences).forEach(
								([key, value]) => {
									setCookie(consentRenewalPeriod, key, value);
								}
							);

							requiredConsentCookieTypeNames.forEach(
								(requiredConsentCookieTypeName) => {
									setCookie(
										consentRenewalPeriod,
										requiredConsentCookieTypeName,
										'true'
									);
								}
							);

							setProductAnalyticsConfigCookie(
								consentRenewalPeriod,
								lastModified
							);

							getOpener().Liferay.fire('closeModal');
						},
					},
					{
						displayType: 'secondary',
						label: Liferay.Language.get('accept-all'),
						onClick() {
							acceptAllCookies(
								consentRenewalPeriod,
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							setProductAnalyticsConfigCookie(
								consentRenewalPeriod,
								lastModified
							);

							getOpener().Liferay.fire('closeModal');
						},
					},
				],
				displayType: 'primary',
				height: '70vh',
				id: 'productAnalyticsConsentPanel',
				iframeBodyCssClass: '',
				onClose: onCloseFunction || undefined,
				size: 'lg',
				title: customTitle || title,
				url,
			});
		};

		Liferay.on('customizeCookies', openProductAnalyticsConsentModal);

		Liferay.on('acceptAllCookies', () => {
			acceptAllCookies(
				consentRenewalPeriod,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);

			setProductAnalyticsConfigCookie(consentRenewalPeriod, lastModified);
		});

		Liferay.on('declineAllCookies', () => {
			declineAllCookies(
				consentRenewalPeriod,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);

			setProductAnalyticsConfigCookie(consentRenewalPeriod, lastModified);
		});
	}
}

function isProductAnalyticsConfigurationModified(lastModified) {
	const productAnalyticsConfiguredDateCookie = getCookie(
		productAnalyticsConfiguredDateCookieName
	);

	if (
		productAnalyticsConfiguredDateCookie === undefined ||
		(lastModified === '0' && productAnalyticsConfiguredDateCookie > 0) ||
		productAnalyticsConfiguredDateCookie < lastModified
	) {
		return true;
	}

	return false;
}
