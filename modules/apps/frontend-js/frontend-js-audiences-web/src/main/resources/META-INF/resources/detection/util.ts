/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function checkTypes(value: any, types: string[], what: string) {
	const matches = types.some((type) =>
		type === 'Set' ? value instanceof Set : typeof value === type
	);

	if (!matches) {
		throw new Error(
			`${what} must be of type ${types
				.map((type) => `'${type}'`)
				.join(' or ')} but got: ${
				value instanceof Set ? 'Set' : typeof value
			}`
		);
	}
}
