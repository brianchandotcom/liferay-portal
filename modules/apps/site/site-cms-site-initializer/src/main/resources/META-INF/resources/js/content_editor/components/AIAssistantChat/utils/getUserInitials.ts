/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getInitials(name = '') {
	const nameArray = name.split(' ', 3);

	return nameArray
		.reduce((acc, val) => acc + val.substring(0, 1), '')
		.toUpperCase();
}
