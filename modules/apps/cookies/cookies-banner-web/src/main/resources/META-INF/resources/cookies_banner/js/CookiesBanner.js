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
	configurationUrl,
	namespace,
	optionalCookies,
	requiredCookies,
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
		checkCookiesConsent(cookieBanner, optionalCookies);

		const cookiePreferences = {};

		optionalCookies.forEach((optionalCookie) => {
			cookiePreferences[optionalCookie] = Boolean(
				getCookie(optionalCookie)
			).toString();
		});

		Liferay.on('cookiePreferenceUpdate', (event) => {
			cookiePreferences[event.key] = event.value;
		});

		acceptAllButton.addEventListener('click', () => {
			cookieBanner.style.display = 'none';

			acceptAllCookies(optionalCookies, requiredCookies);
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

							requiredCookies.forEach((requiredCookie) => {
								setCookie(requiredCookie, 'true');
							});

							checkCookiesConsent(cookieBanner, optionalCookies);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
					{
						displayType: 'secondary',
						label: Liferay.Language.get('accept-all'),
						onClick() {
							acceptAllCookies(optionalCookies, requiredCookies);

							checkCookiesConsent(cookieBanner, optionalCookies);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
					{
						label: Liferay.Language.get('decline-all'),
						onClick() {
							declineAllCookies(optionalCookies, requiredCookies);

							checkCookiesConsent(cookieBanner, optionalCookies);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
				],
				displayType: 'primary',
				height: '70vh',
				id: 'cookiesBannerConfiguration',
				size: 'lg',
				title: Liferay.Language.get('cookies-configuration'),
				url: configurationUrl,
			});
		});

		declineAllButton.addEventListener('click', () => {
			cookieBanner.style.display = 'none';

			declineAllCookies(optionalCookies, requiredCookies);
		});
	}
}

function checkCookiesConsent(cookieBanner, optionalCookies) {
	if (
		optionalCookies.some(
			(optionalCookie) =>
				getCookie(optionalCookie) === 'true' ||
				getCookie(optionalCookie) === 'false'
		)
	) {
		cookieBanner.style.display = 'none';
	}
	else {
		cookieBanner.style.display = 'block';
	}
}
