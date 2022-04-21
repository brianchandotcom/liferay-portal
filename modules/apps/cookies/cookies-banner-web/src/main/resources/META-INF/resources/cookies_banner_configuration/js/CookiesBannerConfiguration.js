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

export default function ({namespace}) {
	const toggleSwitches = Array.from(
		document.querySelectorAll(
			`#${namespace}cookiesBannerConfigurationForm [data-cookie-key]`
		)
	);

	toggleSwitches.forEach((toggleSwitch) => {
		const cookieKey = toggleSwitch.dataset.cookieKey;

		toggleSwitch.addEventListener('click', () => {
			Liferay.Util.getOpener().Liferay.fire('cookiePreferenceUpdate', {
				key: cookieKey,
				value: toggleSwitch.checked ? 'true' : 'false',
			});
		});

		toggleSwitch.checked = getCookie(cookieKey) === 'true';

		toggleSwitch.removeAttribute('disabled');
	});

	const acceptAllButton = document.getElementById(
		`${namespace}acceptAllButton`
	);
	const confirmButton = document.getElementById(`${namespace}confirmButton`);
	const declineAllButton = document.getElementById(
		`${namespace}declineAllButton`
	);

	acceptAllButton.addEventListener('click', () => {
		acceptAllCookies();

		window.location.reload();
	});

	confirmButton.addEventListener('click', () => {
		toggleSwitches.forEach((toggleSwitch) => {
			setCookie(
				toggleSwitch.dataset.cookieKey,
				toggleSwitch.checked ? 'true' : 'false'
			);
		});

		setCookie('CONSENT_TYPE_STRICTLY_NECESSARY', 'true');

		window.location.reload();
	});

	declineAllButton.addEventListener('click', () => {
		declineAllCookies();

		window.location.reload();
	});
}

function acceptAllCookies() {
	setCookie('CONSENT_TYPE_FUNCTIONAL', 'true');
	setCookie('CONSENT_TYPE_PERFORMANCE', 'true');
	setCookie('CONSENT_TYPE_PERSONALIZATION', 'true');
	setCookie('CONSENT_TYPE_STRICTLY_NECESSARY', 'true');
}

function declineAllCookies() {
	setCookie('CONSENT_TYPE_FUNCTIONAL', 'false');
	setCookie('CONSENT_TYPE_PERFORMANCE', 'false');
	setCookie('CONSENT_TYPE_PERSONALIZATION', 'false');
	setCookie('CONSENT_TYPE_STRICTLY_NECESSARY', 'true');
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
