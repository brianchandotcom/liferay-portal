/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Call a global function registered on the`window` object by Liferay.provide
 */
export function callWindowGlobalFunction(name: string) {
	const globalFunction = window[name as keyof typeof window];

	if (typeof globalFunction === 'function') {
		globalFunction();
	}
}
