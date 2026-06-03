/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @returns a set containing `${key}=${value}` strings
 */
export function getCookies(): Set<string> {
	if (!document.cookie) {
		return new Set();
	}

	return new Set(document.cookie.split(';').map((item) => item.trim()));
}
