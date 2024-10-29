/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IOption {
	[key: string]: Intl.DateTimeFormatOptions;
}

export const customFormatDate: IOption = {
	DATE_AND_TIME: {
		day: 'numeric',
		hour: 'numeric',
		minute: 'numeric',
		month: 'numeric',
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

export function getUTCHourAndDateFormat(date: string, locale: string) {
	return new Date(date)
		.toLocaleDateString(locale, {
			day: 'numeric',
			hour: 'numeric',
			hour12: true,
			minute: '2-digit',
			month: 'numeric',
			timeZone: 'UTC',
			year: '2-digit',
		})
		.replace(',', '');
}

export function getDateFormatted(value: Date, locale: string) {
	return new Intl.DateTimeFormat(locale, {
		day: 'numeric',
		month: 'short',
		year: 'numeric',
	}).format(new Date(value));
}

export function setFutureDate(day: number) {
	const now = new Date().getTime();

	const fiveDaysFromNow = now + 1000 * 60 * 60 * 24 * day;

	return new Date(fiveDaysFromNow).toISOString();
}
