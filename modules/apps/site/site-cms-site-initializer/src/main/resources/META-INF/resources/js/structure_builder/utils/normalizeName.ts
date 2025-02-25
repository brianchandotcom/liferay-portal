/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function normalizeName(name: string) {

	// Remove special characters

	let nextName = name.replace(/[^A-Z0-9]/gi, '');

	// Capitalize first letter

	nextName = nextName.charAt(0).toUpperCase() + nextName.slice(1);

	return nextName;
}
