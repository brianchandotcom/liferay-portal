/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint sort-keys: ["error", "asc", {caseSensitive: false}]*/

const getDateParts = (
	date: Date,
	locale: string = Liferay.ThemeDisplay.getBCP47LanguageId(),
	options: Intl.DateTimeFormatOptions = {}
) => {
	const intl = new Intl.DateTimeFormat(locale, {
		day: '2-digit',
		hour: '2-digit',
		hourCycle: 'h23',
		minute: '2-digit',
		month: '2-digit',
		second: '2-digit',
		year: 'numeric',
		...options,
	});

	const parts = intl.formatToParts(date);

	const year = parts.find((part) => part.type === 'year')?.value;
	const month = parts.find((part) => part.type === 'month')?.value;
	const day = parts.find((part) => part.type === 'day')?.value;
	const hour = parts.find((part) => part.type === 'hour')?.value;
	const minute = parts.find((part) => part.type === 'minute')?.value;
	const second = parts.find((part) => part.type === 'second')?.value;
	const dayPeriod = parts.find((part) => part.type === 'dayPeriod')?.value;

	return {
		day,
		dayPeriod,
		hour,
		minute,
		month,
		second,
		year,
	};
};

const FORMATTER_MAP = {
	'dd': (date: Date, locale: string) => {
		return getDateParts(date, locale).day;
	},
	'dd-yyyy': (date: Date, locale: string) => {
		const {day, year} = getDateParts(date, locale);

		return `${day}-${year}`;
	},
	'hh-mm': (date: Date, locale: string) => {
		const {hour, minute} = getDateParts(date, locale);

		return `${hour}-${minute}`;
	},
	'MM-dd-yyyy': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${month}-${day}-${year}`;
	},
	'MM/dd/yyyy': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${month}/${day}/${year}`;
	},
	'MMM': (date: Date, locale: string) => {
		return getDateParts(date, locale, {
			month: 'short',
		}).month;
	},
	'MMM d, h a': (date: Date, locale: string) => {
		const {day, dayPeriod, hour, month} = getDateParts(date, locale, {
			day: 'numeric',
			hour: 'numeric',
			hour12: true,
			month: 'short',
		});

		return `${month} ${day}, ${hour} ${dayPeriod}`;
	},

	'MMM dd': (date: Date, locale: string) => {
		const {day, month} = getDateParts(date, locale, {
			month: 'short',
		});

		return `${month} ${day}`;
	},
	'MMM dd, hh:mm': (date: Date, locale: string) => {
		const {day, hour, minute, month} = getDateParts(date, locale, {
			month: 'short',
		});

		return `${month} ${day}, ${hour}:${minute}`;
	},
	'MMM dd, hh:mm a': (date: Date, locale: string) => {
		const {day, dayPeriod, hour, minute, month} = getDateParts(
			date,
			locale,
			{
				month: 'short',
			}
		);

		return `${month} ${day}, ${hour}:${minute} ${dayPeriod}`;
	},
	'MMM dd, yyyy': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale, {
			month: 'short',
		});

		return `${month} ${day}, ${year}`;
	},
	'MMM-dd-yyyy': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale, {
			month: 'short',
		});

		return `${month}-${day}-${year}`;
	},
	'p': (date: Date, locale: string) => {
		return new Intl.DateTimeFormat(locale, {
			hour: 'numeric',
			minute: 'numeric',
		}).format(date);
	},
	'P': (date: Date, locale: string) => {
		return new Intl.DateTimeFormat(locale, {
			day: '2-digit',
			month: '2-digit',
			year: 'numeric',
		}).format(date);
	},
	'P p': (date: Date, locale: string) => {
		return new Intl.DateTimeFormat(locale, {
			day: '2-digit',
			hour: '2-digit',
			minute: '2-digit',
			month: '2-digit',
			year: 'numeric',
		}).format(date);
	},
	'PP': (date: Date, locale: string) => {
		return new Intl.DateTimeFormat(locale, {
			day: '2-digit',
			month: 'short',
			year: 'numeric',
		}).format(date);
	},
	'PP p': (date: Date, locale: string) => {
		return new Intl.DateTimeFormat(locale, {
			day: 'numeric',
			hour: 'numeric',
			minute: 'numeric',
			month: 'short',
			year: 'numeric',
		}).format(date);
	},
	'PPP p': (date: Date, locale: string) => {
		const day = new Intl.DateTimeFormat(locale, {
			day: 'numeric',
			month: 'long',
			year: 'numeric',
		}).format(date);

		const time = new Intl.DateTimeFormat(locale, {
			hour: 'numeric',
			minute: 'numeric',
		}).format(date);

		return `${day} ${time}`;
	},
	'yyyy': (date: Date, locale: string) => {
		return getDateParts(date, locale).year;
	},
	'yyyy MMM d': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale, {
			day: 'numeric',
			month: 'short',
		});

		return `${year} ${month} ${day}`;
	},
	'yyyy-MM-dd': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${year}-${month}-${day}`;
	},
	'yyyy-MM-dd hh:mm': (date: Date, locale: string) => {
		const {day, hour, minute, month, year} = getDateParts(date, locale);

		return `${year}-${month}-${day} ${hour}:${minute}`;
	},
	'yyyy-MM-ddThh:00': (date: Date, locale: string) => {
		const {day, hour, month, year} = getDateParts(date, locale);

		return `${year}-${month}-${day}T${hour}:00`;
	},
	'yyyy-MM-ddThh:mm:ssZ': (date: Date, locale: string) => {
		const {day, hour, minute, month, second, year} = getDateParts(
			date,
			locale
		);

		return `${year}-${month}-${day}T${hour}:${minute}:${second}Z`;
	},
	'yyyy/MM/dd': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${year}/${month}/${day}`;
	},
	'yyyyMMdd': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${year}${month}${day}`;
	},
	'yyyyMMddhhmmss': (date: Date, locale: string) => {
		const {day, hour, minute, month, second, year} = getDateParts(
			date,
			locale
		);

		return `${year}${month}${day}${hour}${minute}${second}`;
	},
};

function normalizeFormat(dateString: string) {
	return dateString
		.replaceAll('D', 'd')
		.replaceAll('Y', 'y')
		.replaceAll('H', 'h')
		.replaceAll('S', 's');
}

export function format(
	date: Date,
	format: keyof typeof FORMATTER_MAP,
	locale = Liferay.ThemeDisplay.getBCP47LanguageId()
) {
	if (!format) {
		return date;
	}

	let formatter =
		FORMATTER_MAP[normalizeFormat(format) as keyof typeof FORMATTER_MAP];

	if (!formatter) {
		console.warn(
			`No formatter found for '${format}'. Defaulting to use 'yyyy-MM-ddThh:mm:ssZ'.`
		);

		formatter = FORMATTER_MAP['yyyy-MM-ddThh:mm:ssZ'];
	}

	if (!(date instanceof Date)) {
		date = new Date(date);
	}

	return formatter(date, locale);
}
