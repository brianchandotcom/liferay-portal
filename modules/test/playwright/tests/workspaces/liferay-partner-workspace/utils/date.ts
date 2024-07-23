/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IOption {
	[key: string]: Intl.DateTimeFormatOptions;
}

export const customFormatDate: IOption = {
	SHORT_MONTH: {
		day: '2-digit',
		month: 'short',
		timeZone: 'UTC',
		year: 'numeric',
	},
};

export function getDateCustomFormat(
	rawDate: string,
	format: Intl.DateTimeFormatOptions
) {
	const date = new Date(rawDate);

	return date?.toLocaleDateString('en-US', format);
}
