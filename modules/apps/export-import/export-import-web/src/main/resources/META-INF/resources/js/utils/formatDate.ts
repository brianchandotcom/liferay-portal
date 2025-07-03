/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function formatDate(date: string): string {
	console.log('Formatting date:', date);
	const dateObject = new Date(date);

	const options: Intl.DateTimeFormatOptions = {
		year: 'numeric',
		month: 'short',
		day: 'numeric',
		hour: 'numeric',
		minute: 'numeric',
		second: 'numeric',
		hour12: true,
	};

	const formattedDate = new Intl.DateTimeFormat('en-US', options).format(
		dateObject
	);

	return formattedDate;
}
