/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IOption {
	[key: string]: Intl.DateTimeFormatOptions;
}

export const customFormatDate: IOption = {
	DATE_AND_TIME: {
		day: '2-digit',
		hour: 'numeric',
		minute: 'numeric',
		month: '2-digit',
		timeZone: 'UTC',
		year: 'numeric',
	},
};

export const customFormatDateYY: IOption = {
	DATE_AND_TIME: {
		day: 'numeric',
		month: 'short',
		timeZone: 'UTC',
		year: '2-digit',
	},
};

export const customFormatDateYYYY: IOption = {
	DATE_AND_TIME: {
		day: 'numeric',
		month: 'short',
		timeZone: 'UTC',
		year: 'numeric',
	},
};

export const customFormatDateTimeYY: IOption = {
	DATE_AND_TIME: {
		day: 'numeric',
		hour: 'numeric',
		minute: 'numeric',
		month: 'short',
		second: 'numeric',
		timeZone: 'UTC',
		year: '2-digit',
	},
};

export const customFormatDateTimeYYYY: IOption = {
	DATE_AND_TIME: {
		day: 'numeric',
		hour: 'numeric',
		minute: 'numeric',
		month: 'short',
		second: 'numeric',
		timeZone: 'UTC',
		year: 'numeric',
	},
};

export function getDateCustomFormat(
	rawDate: string,
	locale: string,
	format: Intl.DateTimeFormatOptions
) {
	const date = new Date(rawDate);

	return date?.toLocaleDateString(locale, format);
}

export function checkSameDate(dateFromApi: string, date2: string) {
	const firstDate = new Date(dateFromApi);
	const secondDate = new Date(date2);

	if (
		firstDate.getUTCDate() === secondDate.getDate() &&
		firstDate.getUTCMonth() === secondDate.getMonth() &&
		firstDate.getUTCFullYear() === secondDate.getFullYear() &&
		firstDate.getUTCHours() === secondDate.getHours() &&
		firstDate.getUTCMinutes() === secondDate.getMinutes()
	) {
		return true;
	}
	else {
		return false;
	}
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
