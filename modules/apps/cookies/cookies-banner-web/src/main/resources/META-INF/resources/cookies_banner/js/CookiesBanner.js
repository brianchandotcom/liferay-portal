/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import {checkConsent, getOpener} from 'frontend-js-web';

import {
	acceptAllCookies,
	declineAllCookies,
	getCookie,
	removeAllCookies,
	setCookie,
	setUserConfigCookie,
	userConfigCookieName,
	userConfigDateCookieName,
} from '../../js/CookiesUtil';

let openCookieConsentModal = () => {
	console.warn(
		'OpenCookieConsentModal was called, but cookie feature is not enabled'
	);
};

export default function ({
	configurationNamespace,
	configurationURL,
	consentRenewalPeriod = 12,
	dissentRenewalPeriod = 12,
	includeDeclineAllButton,
	modifiedDate = 0,
	namespace,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames,
	title,
}) {
	const acceptAllButton = document.getElementById(
		`${namespace}acceptAllButton`
	);
	const configurationButton = document.getElementById(
		`${namespace}configurationButton`
	);
	const declineAllButton = document.getElementById(
		`${namespace}declineAllButton`
	);
	const cookieBanner = document.querySelector('.cookies-banner');
	const editMode = document.body.classList.contains('has-edit-mode-menu');

	if (!editMode) {
		if (isCookiesPreferenceHandlingConfigurationModified(modifiedDate)) {
			removeAllCookies(
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);
		}

		const consentManager = document.getElementById(
			'_com_liferay_my_account_web_portlet_MyAccountPortlet_cookiesBannerConfigurationForm'
		);
		const productAnalyticsBanner = document.querySelector(
			'.product-analytics-banner'
		);

		if (
			consentManager ||
			(productAnalyticsBanner &&
				productAnalyticsBanner.style.display === 'block')
		) {
			cookieBanner.style.display = 'none';
		}
		else {
			setBannerVisibility(cookieBanner, modifiedDate);
		}

		const cookiePreferences = {};

		optionalConsentCookieTypeNames.forEach(
			(optionalConsentCookieTypeName) => {
				cookiePreferences[optionalConsentCookieTypeName] =
					getCookie(optionalConsentCookieTypeName) || 'false';
			}
		);

		requiredConsentCookieTypeNames.forEach(
			(requiredConsentCookieTypeName) => {
				cookiePreferences[requiredConsentCookieTypeName] =
					getCookie(requiredConsentCookieTypeName) || 'true';
			}
		);

		Liferay.on('cookiePreferenceUpdate', (event) => {
			cookiePreferences[event.key] = event.value;
		});

		acceptAllButton.addEventListener('click', () => {
			cookieBanner.style.display = 'none';

			acceptAllCookies(
				consentRenewalPeriod,
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);

			setUserConfigCookie(consentRenewalPeriod);
		});

		openCookieConsentModal = ({
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
						className: includeDeclineAllButton ? '' : 'd-none',
						displayType: 'secondary',
						label: Liferay.Language.get(
							'use-necessary-cookies-only'
						),
						onClick() {
							const renewalPeriod =
								dissentRenewalPeriod === 0
									? consentRenewalPeriod
									: dissentRenewalPeriod;

							declineAllCookies(
								renewalPeriod,
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							setUserConfigCookie(renewalPeriod);

							setBannerVisibility(cookieBanner);

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

							setUserConfigCookie(consentRenewalPeriod);

							setBannerVisibility(cookieBanner);

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

							setUserConfigCookie(consentRenewalPeriod);

							setBannerVisibility(cookieBanner);

							getOpener().Liferay.fire('closeModal');
						},
					},
				],
				displayType: 'primary',
				height: '70vh',
				id: 'cookiesBannerConfiguration',
				iframeBodyCssClass: '',
				onClose: onCloseFunction || undefined,
				size: 'lg',
				title: customTitle || title,
				url,
			});
		};

		configurationButton.addEventListener('click', () => {
			openCookieConsentModal({});
		});

		if (declineAllButton !== null) {
			declineAllButton.addEventListener('click', () => {
				cookieBanner.style.display = 'none';

				const renewalPeriod =
					dissentRenewalPeriod === 0
						? consentRenewalPeriod
						: dissentRenewalPeriod;

				declineAllCookies(
					renewalPeriod,
					optionalConsentCookieTypeNames,
					requiredConsentCookieTypeNames
				);

				setUserConfigCookie(renewalPeriod);
			});
		}
	}

	checkFloatingIcon(cookieBanner, namespace);
}

function checkCookieConsentForTypes(cookieTypes, modalOptions) {
	return new Promise((resolve, reject) => {
		if (isCookieTypesAccepted(cookieTypes)) {
			resolve();
		}
		else {
			openCookieConsentModal({
				alertDisplayType: modalOptions?.alertDisplayType || 'info',
				alertMessage: modalOptions?.alertMessage || null,
				customTitle: modalOptions?.customTitle || null,
				onCloseFunction: () =>
					isCookieTypesAccepted(cookieTypes) ? resolve() : reject(),
			});
		}
	});
}

function checkFloatingIcon(cookieBanner, namespace) {
	const floatingIconButton = document.getElementById(
		`${namespace}floatingIconButton`
	);

	if (!floatingIconButton) {
		return;
	}

	const toggleIconVisibility = () => {
		let isBannerVisible = false;

		if (cookieBanner) {
			isBannerVisible =
				cookieBanner.style.display !== 'none' &&
				!cookieBanner.classList.contains('d-none');
		}

		if (isBannerVisible) {
			floatingIconButton.classList.remove('d-inline-flex');
			floatingIconButton.classList.add('d-none');
		}
		else if (Liferay.FeatureFlags['LPD-75027']) {
			floatingIconButton.classList.remove('d-none');
			floatingIconButton.classList.add('d-inline-flex');
		}
	};

	toggleIconVisibility();

	if (cookieBanner) {
		const observer = new MutationObserver(() => {
			toggleIconVisibility();
		});

		observer.observe(cookieBanner, {
			attributeFilter: ['style', 'class'],
			attributes: true,
		});
	}

	floatingIconButton.addEventListener('click', () => {
		openCookieConsentModal({});
	});
}

function isCookieTypesAccepted(cookieTypes) {
	if (!Array.isArray(cookieTypes)) {
		cookieTypes = [cookieTypes];
	}

	return cookieTypes.every((cookieType) => checkConsent(cookieType));
}

function isCookiesPreferenceHandlingConfigurationModified(modifiedDate) {
	if (modifiedDate === 0) {
		return false;
	}

	const userConfigDateCookie = getCookie(userConfigDateCookieName);

	if (
		userConfigDateCookie === undefined ||
		userConfigDateCookie < modifiedDate
	) {
		return true;
	}

	return false;
}

function setBannerVisibility(cookieBanner, modifiedDate) {
	if (
		!isCookiesPreferenceHandlingConfigurationModified(modifiedDate) &&
		getCookie(userConfigCookieName)
	) {
		cookieBanner.style.display = 'none';
	}
	else {
		cookieBanner.style.display = 'block';
	}
}

export {checkCookieConsentForTypes, openCookieConsentModal};
