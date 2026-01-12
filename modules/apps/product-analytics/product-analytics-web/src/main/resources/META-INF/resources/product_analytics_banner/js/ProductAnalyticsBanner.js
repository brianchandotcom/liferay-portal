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
	productAnalyticsConfiguredDateCookieName,
	removeAllCookies,
	setCookie,
	setProductAnalyticsConfigCookie,
	userConfigCookieName,
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
	namespace,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
	title,
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
		if (isProductAnalyticsConfigurationModified(lastModified)) {
			removeAllCookies(
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);
		}

		setBannerVisibility(lastModified, productAnalyticsBanner);

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
				consentRenewalPeriod,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);

			setProductAnalyticsConfigCookie(consentRenewalPeriod, lastModified);
			setBannerVisibility(lastModified, productAnalyticsBanner);
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

							setBannerVisibility(
								lastModified,
								productAnalyticsBanner
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

							setBannerVisibility(
								lastModified,
								productAnalyticsBanner
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

							setBannerVisibility(
								lastModified,
								productAnalyticsBanner
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

		customizeButton.addEventListener('click', () => {
			openProductAnalyticsConsentModal({});
		});

		if (declineAllButton !== null) {
			declineAllButton.addEventListener('click', () => {
				productAnalyticsBanner.style.display = 'none';

				declineAllCookies(
					consentRenewalPeriod,
					optionalConsentCookieTypeNames,
					requiredConsentCookieTypeNames
				);

				setProductAnalyticsConfigCookie(
					consentRenewalPeriod,
					lastModified
				);
				setBannerVisibility(lastModified, productAnalyticsBanner);
			});
		}
	}
}

function checkProductAnalyticsConsentForTypes(cookieTypes, modalOptions) {
	return new Promise((resolve, reject) => {
		if (isCookieTypesAccepted(cookieTypes)) {
			resolve();
		}
		else {
			openProductAnalyticsConsentModal({
				alertDisplayType: modalOptions?.alertDisplayType || 'info',
				alertMessage: modalOptions?.alertMessage || null,
				customTitle: modalOptions?.customTitle || null,
				onCloseFunction: () =>
					isCookieTypesAccepted(cookieTypes) ? resolve() : reject(),
			});
		}
	});
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

function isCookieTypesAccepted(cookieTypes) {
	if (!Array.isArray(cookieTypes)) {
		cookieTypes = [cookieTypes];
	}

	return cookieTypes.every((cookieType) => checkConsent(cookieType));
}

function setBannerVisibility(lastModified, productAnalyticsBanner) {
	const cookieBanner = document.querySelector('.cookies-banner');
	const productAnalytics = document.getElementById(
		'_com_liferay_my_account_web_portlet_MyAccountPortlet_productAnalyticsConsentPanelForm'
	);

	if (
		(!isProductAnalyticsConfigurationModified(lastModified) &&
			getCookie(productAnalyticsConfiguredCookieName)) ||
		productAnalytics
	) {
		productAnalyticsBanner.style.display = 'none';

		if (cookieBanner) {
			const cookieManager = document.getElementById(
				'_com_liferay_my_account_web_portlet_MyAccountPortlet_cookiesBannerConfigurationForm'
			);

			if (cookieManager || getCookie(userConfigCookieName)) {
				cookieBanner.style.display = 'none';
			}
			else {
				cookieBanner.style.display = 'block';
			}
		}
	}
	else {
		productAnalyticsBanner.style.display = 'block';

		if (cookieBanner) {
			cookieBanner.style.display = 'none';
		}
	}
}

export {checkProductAnalyticsConsentForTypes, openProductAnalyticsConsentModal};
