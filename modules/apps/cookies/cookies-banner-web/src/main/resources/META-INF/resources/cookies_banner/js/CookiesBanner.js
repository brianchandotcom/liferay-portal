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

import {
	acceptAllCookies,
	declineAllCookies,
	getCookie,
	setCookie,
} from '../../js/CookiesUtil';

export default function ({
	configurationURL,
	includeDeclineAllButton,
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
	const editMode = layoutMode === 'edit';

	if (!editMode) {
		checkCookiesConsent(
			cookieBanner,
			optionalConsentCookieTypeNames,
			requiredConsentCookieTypeNames
		);

		const cookiePreferences = {};

		optionalConsentCookieTypeNames.forEach(
			(optionalConsentCookieTypeName) => {
				cookiePreferences[optionalConsentCookieTypeName] = Boolean(
					getCookie(optionalConsentCookieTypeName)
				).toString();
			}
		);

		Liferay.on('cookiePreferenceUpdate', (event) => {
			cookiePreferences[event.key] = event.value;
		});

		acceptAllButton.addEventListener('click', () => {
			cookieBanner.style.display = 'none';

			acceptAllCookies(
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);
		});

		configurationButton.addEventListener('click', () => {
			Liferay.Util.openModal({
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('confirm'),
						onClick() {
							Object.entries(cookiePreferences).forEach(
								([key, value]) => {
									setCookie(key, value);
								}
							);

							requiredConsentCookieTypeNames.forEach(
								(requiredConsentCookieTypeName) => {
									setCookie(
										requiredConsentCookieTypeName,
										'true'
									);
								}
							);

							checkCookiesConsent(
								cookieBanner,
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
					{
						displayType: 'secondary',
						label: Liferay.Language.get('accept-all'),
						onClick() {
							acceptAllCookies(
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							checkCookiesConsent(
								cookieBanner,
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
					{
						className: includeDeclineAllButton ? '' : 'd-none',
						displayType: 'secondary',
						label: Liferay.Language.get('decline-all'),
						onClick() {
							declineAllCookies(
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							checkCookiesConsent(
								cookieBanner,
								optionalConsentCookieTypeNames,
								requiredConsentCookieTypeNames
							);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
				],
				displayType: 'primary',
				height: '70vh',
				id: 'cookiesBannerConfiguration',
				size: 'lg',
				title,
				url: configurationURL,
			});
		});

		declineAllButton.addEventListener('click', () => {
			cookieBanner.style.display = 'none';

			declineAllCookies(
				optionalConsentCookieTypeNames,
				requiredConsentCookieTypeNames
			);
		});
	}
}

function checkCookiesConsent(
	cookieBanner,
	optionalConsentCookieTypeNames,
	requiredConsentCookieTypeNames
) {
	if (
		optionalConsentCookieTypeNames.every((optionalConsentCookieTypeName) =>
			getCookie(optionalConsentCookieTypeName)
		) &&
		requiredConsentCookieTypeNames.every((requiredConsentCookieTypeName) =>
			getCookie(requiredConsentCookieTypeName)
		)
	) {
		cookieBanner.style.display = 'none';
	}
	else {
		cookieBanner.style.display = 'block';
	}
}
