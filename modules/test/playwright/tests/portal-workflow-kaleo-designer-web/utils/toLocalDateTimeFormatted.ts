/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export function toLocalDateTimeFormatted(
	dateUTC: string,
	language: string,
	userTimezone: string
) {
	const date = new Date(dateUTC);

	const options = {
		day: '2-digit',
		hour: '2-digit',
		minute: '2-digit',
		month: 'short',
		timeZone: userTimezone,
		year: 'numeric',
	} as const;

	const timeZone = new Intl.DateTimeFormat(language, options);

	return timeZone.format(date);
}
