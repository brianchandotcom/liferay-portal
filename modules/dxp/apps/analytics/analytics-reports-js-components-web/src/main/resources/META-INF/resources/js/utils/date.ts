/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * @returns a formatted data MMM DD 
 */

export function formatDate(date: Date) {
	const options: Intl.DateTimeFormatOptions = {
		day: 'numeric',
		month: 'short',
	};

	return date.toLocaleDateString('en-US', options).toLowerCase();
};