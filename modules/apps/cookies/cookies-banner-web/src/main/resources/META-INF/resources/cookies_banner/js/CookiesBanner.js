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

export default function ({configurationUrl, namespace}) {
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
		checkCookiesConsent(cookieBanner);

		const cookiePreferences = {};

		Liferay.on('cookiePreferenceUpdate', (event) => {
			cookiePreferences[event.key] = event.value;
		});

		acceptAllButton.addEventListener('click', () => {
			cookieBanner.style.display = 'none';

			acceptAllCookies();
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

							checkCookiesConsent(cookieBanner);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
					{
						displayType: 'secondary',
						label: Liferay.Language.get('accept-all'),
						onClick() {
							acceptAllCookies();

							checkCookiesConsent(cookieBanner);

							Liferay.Util.getOpener().Liferay.fire('closeModal');
						},
					},
					{
						label: Liferay.Language.get('decline-all'),
						onClick() {
							declineAllCookiesDecline();

							checkCookiesConsent(cookieBanner);

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

			declineAllCookiesDecline();
		});
	}
}

function checkCookiesConsent(cookieBanner) {
	if (
		getCookie('liferay.cookie.consent.functional') === 'accepted' ||
		getCookie('liferay.cookie.consent.functional') === 'decline' ||
		getCookie('liferay.cookie.consent.performance') === 'accepted' ||
		getCookie('liferay.cookie.consent.performance') === 'decline' ||
		getCookie('liferay.cookie.consent.personalization') === 'accepted' ||
		getCookie('liferay.cookie.consent.personalization') === 'decline'
	) {
		cookieBanner.style.display = 'none';
	}
	else {
		cookieBanner.style.display = 'block';
	}
}

function acceptAllCookies() {
	setCookie('liferay.cookie.consent.functional', 'accepted');
	setCookie('liferay.cookie.consent.performance', 'accepted');
	setCookie('liferay.cookie.consent.personalization', 'accepted');
}

function declineAllCookiesDecline() {
	setCookie('liferay.cookie.consent.functional', 'decline');
	setCookie('liferay.cookie.consent.performance', 'decline');
	setCookie('liferay.cookie.consent.personalization', 'decline');
}

function getCookie(name) {
	const cookieName = name + '=';
	const cookieSet = document.cookie.split(';');

	for (let i = 0; i < cookieSet.length; i++) {
		let c = cookieSet[i];

		while (c.charAt(0) === ' ') {
			c = c.substring(1, c.length);
		}

		if (c.indexOf(cookieName) === 0) {
			return c.substring(cookieName.length, c.length);
		}
	}

	return null;
}

function setCookie(name, value, days = 180) {
	const date = new Date();

	date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);

	const expires = '; expires=' + date.toUTCString();

	document.cookie = name + '=' + (value || '') + expires + '; path=/';
}
