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
	const toggleFunctional = document.getElementById(
		`${namespace}toggleFunctional`
	);
	const togglePerformance = document.getElementById(
		`${namespace}togglePerformance`
	);
	const togglePersonalization = document.getElementById(
		`${namespace}togglePersonalization`
	);

	toggleFunctional.addEventListener('click', () => {
		Liferay.Util.getOpener().Liferay.fire('cookiePreferenceUpdate', {
			key: 'liferay.cookie.consent.functional',
			value: toggleFunctional.checked ? 'accepted' : 'declined',
		});
	});

	togglePerformance.addEventListener('click', () => {
		Liferay.Util.getOpener().Liferay.fire('cookiePreferenceUpdate', {
			key: 'liferay.cookie.consent.performance',
			value: togglePerformance.checked ? 'accepted' : 'declined',
		});
	});

	togglePersonalization.addEventListener('click', () => {
		Liferay.Util.getOpener().Liferay.fire('cookiePreferenceUpdate', {
			key: 'liferay.cookie.consent.personalization',
			value: togglePersonalization.checked ? 'accepted' : 'declined',
		});
	});

	toggleFunctional.checked =
		getCookie('liferay.cookie.consent.functional') === 'accepted';
	togglePerformance.checked =
		getCookie('liferay.cookie.consent.performance') === 'accepted';
	togglePersonalization.checked =
		getCookie('liferay.cookie.consent.personalization') === 'accepted';

	toggleFunctional.removeAttribute('disabled');
	togglePerformance.removeAttribute('disabled');
	togglePersonalization.removeAttribute('disabled');

	const buttonAcceptAll = document.getElementById(
		`${namespace}buttonAcceptAll`
	);
	const buttonConfirm = document.getElementById(`${namespace}buttonConfirm`);
	const buttonDeclineAll = document.getElementById(
		`${namespace}buttonDeclineAll`
	);

	buttonAcceptAll.addEventListener('click', () => {
		cookiesAcceptAll();

		window.location.reload();
	});

	buttonDeclineAll.addEventListener('click', () => {
		cookiesDeclineAll();

		window.location.reload();
	});

	buttonConfirm.addEventListener('click', () => {
		setCookie(
			'liferay.cookie.consent.functional',
			toggleFunctional.checked ? 'accepted' : 'decline'
		);
		setCookie(
			'liferay.cookie.consent.performance',
			togglePerformance.checked ? 'accepted' : 'decline'
		);
		setCookie(
			'liferay.cookie.consent.personalization',
			togglePersonalization.checked ? 'accepted' : 'decline'
		);

		window.location.reload();
	});
}

function cookiesAcceptAll() {
	setCookie('liferay.cookie.consent.functional', 'accepted');
	setCookie('liferay.cookie.consent.performance', 'accepted');
	setCookie('liferay.cookie.consent.personalization', 'accepted');
}

function cookiesDeclineAll() {
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
