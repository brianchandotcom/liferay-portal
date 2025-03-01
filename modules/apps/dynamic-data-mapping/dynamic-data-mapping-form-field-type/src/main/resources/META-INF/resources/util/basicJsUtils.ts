/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * We always need to test the type before getting the length of keys
 * because Object.keys() also returns filled arrays from other types like strings.
 */
export function isEmptyObject(value: any) {
	return typeof value === 'object' && !Object.keys(value).length;
}
