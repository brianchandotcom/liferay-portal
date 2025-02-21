/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function setCookie(name, value, expirationDays) {
	const expirationDate = new Date();

	expirationDate.setTime(
		expirationDate.getTime() + expirationDays * 24 * 60 * 60 * 1000
	);

	const expires = 'expires=' + expirationDate.toUTCString();

	document.cookie = `${name}=${value};${expires};path=/;secure`;
}

setCookie('Interest', 'Products', 1);
