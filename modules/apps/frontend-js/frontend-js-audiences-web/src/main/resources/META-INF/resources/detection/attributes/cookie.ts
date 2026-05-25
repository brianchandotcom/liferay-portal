/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getCookie(name: string): string {
	const cookies = document.cookie.split('; ');

	for (const cookie of cookies) {
		const i = cookie.indexOf('=');

		if (i === -1) {
			continue;
		}

		if (cookie.slice(0, i) === name) {
			return decodeURIComponent(cookie.slice(i + 1));
		}
	}

	return '';
}
