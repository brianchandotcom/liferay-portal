/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function sortByStringLength(a, b) {
	if (a.length < b.length) {
		return -1;
	}

	if (a.length > b.length) {
		return 1;
	}

	return 0;
}
