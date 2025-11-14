/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function insertNodeAt(object, newKey, newValue, index) {
	const entries = Object.entries(object);
	entries.splice(index, 0, [newKey, newValue]);

	return Object.fromEntries(entries);
}

export {insertNodeAt};
