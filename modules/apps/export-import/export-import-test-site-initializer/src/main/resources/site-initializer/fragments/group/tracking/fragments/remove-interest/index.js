/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function getCookie(name) {
	const cookieName = `${name}=`;

	for (let cookie of decodeURIComponent(document.cookie).split(';')) {
		cookie = cookie.trimStart();

		if (cookie.startsWith(cookieName)) {
			return cookie.substring(cookieName.length);
		}
	}

	return '';
}

function eraseCookie(name) {
	if (getCookie(name)) {
		document.cookie = `${name}=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT`;
	}
}

eraseCookie('Interest');
