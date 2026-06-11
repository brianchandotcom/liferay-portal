/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @returns a set containing `${key}=${value}` or just `${value}` strings
 */
export function getRequestParameters(): Set<string> {
	const set = new Set<string>();

	for (const [key, value] of new URLSearchParams(window.location.search)) {
		if (key === '') {
			set.add(value);
		}
		else {
			set.add(`${key}=${value}`);
		}
	}

	return set;
}
