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
	namespace,
	optionalCookieNames,
	requiredCookieNames,
	showButtons,
}) {
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

	if (showButtons) {
		const acceptAllButton = document.getElementById(
			`${namespace}acceptAllButton`
		);
		const confirmButton = document.getElementById(
			`${namespace}confirmButton`
		);
		const declineAllButton = document.getElementById(
			`${namespace}declineAllButton`
		);

		acceptAllButton.addEventListener('click', () => {
			acceptAllCookies(optionalCookieNames, requiredCookieNames);

			window.location.reload();
		});

		confirmButton.addEventListener('click', () => {
			toggleSwitches.forEach((toggleSwitch) => {
				setCookie(
					toggleSwitch.dataset.cookieKey,
					toggleSwitch.checked ? 'true' : 'false'
				);
			});

			requiredCookieNames.forEach((requiredCookie) => {
				setCookie(requiredCookie, 'true');
			});

			window.location.reload();
		});

		declineAllButton.addEventListener('click', () => {
			declineAllCookies(optionalCookieNames, requiredCookieNames);

			window.location.reload();
		});
	}
}
