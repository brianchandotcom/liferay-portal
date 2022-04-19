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
	const functionalToggle = document.getElementById(
		`${namespace}functionalToggle`
	);
	const performanceToggle = document.getElementById(
		`${namespace}performanceToggle`
	);
	const personalizationToggle = document.getElementById(
		`${namespace}personalizationToggle`
	);

	functionalToggle.addEventListener('click', () => {
		Liferay.Util.getOpener().Liferay.fire('cookiePreferenceUpdate', {
			key: 'CONSENT_TYPE_FUNCTIONAL',
			value: functionalToggle.checked ? 'true' : 'false',
		});
	});

	performanceToggle.addEventListener('click', () => {
		Liferay.Util.getOpener().Liferay.fire('cookiePreferenceUpdate', {
			key: 'CONSENT_TYPE_PERFORMANCE',
			value: performanceToggle.checked ? 'true' : 'false',
		});
	});

	personalizationToggle.addEventListener('click', () => {
		Liferay.Util.getOpener().Liferay.fire('cookiePreferenceUpdate', {
			key: 'CONSENT_TYPE_PERSONALIZATION',
			value: personalizationToggle.checked ? 'true' : 'false',
		});
	});

	functionalToggle.checked = getCookie('CONSENT_TYPE_FUNCTIONAL') === 'true';
	performanceToggle.checked =
		getCookie('CONSENT_TYPE_PERFORMANCE') === 'true';
	personalizationToggle.checked =
		getCookie('CONSENT_TYPE_PERSONALIZATION') === 'true';

	functionalToggle.removeAttribute('disabled');
	performanceToggle.removeAttribute('disabled');
	personalizationToggle.removeAttribute('disabled');

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
		setCookie(
			'CONSENT_TYPE_FUNCTIONAL',
			functionalToggle.checked ? 'true' : 'false'
		);
		setCookie(
			'CONSENT_TYPE_PERFORMANCE',
			performanceToggle.checked ? 'true' : 'false'
		);
		setCookie(
			'CONSENT_TYPE_PERSONALIZATION',
			personalizationToggle.checked ? 'true' : 'false'
		);

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
}

function declineAllCookies() {
	setCookie('CONSENT_TYPE_FUNCTIONAL', 'false');
	setCookie('CONSENT_TYPE_PERFORMANCE', 'false');
	setCookie('CONSENT_TYPE_PERSONALIZATION', 'false');
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
