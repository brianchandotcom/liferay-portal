/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function subDays(date: Date, days: number) {
	date.setDate(date.getDate() - days);

	return date;
}

function subMonths(date: Date, months: number) {
	date.setMonth(date.getMonth() - months);

	return date;
}

function isValid(date: any) {
	return !isNaN((date instanceof Date ? date : new Date(date)).getTime());
}

const getDateParts = (date: Date, locale: string) => {
	const intl = new Intl.DateTimeFormat(locale, {
		day: '2-digit',
		month: '2-digit',
		year: 'numeric',
	});

	const parts = intl.formatToParts(date);

	const year = parts.find((part) => part.type === 'year')?.value;
	const month = parts.find((part) => part.type === 'month')?.value;
	const day = parts.find((part) => part.type === 'day')?.value;

	return {
		day,
		month,
		year,
	};
};

const FORMATTER_MAP = {
	'MM/dd/yyyy': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${month}/${day}/${year}`;
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
			second: '2-digit',
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
	'p': (date: Date, locale: string) => {
		return new Intl.DateTimeFormat(locale, {
			hour: 'numeric',
			minute: 'numeric',
		}).format(date);
	},
	'yyyy/MM/dd': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${year}/${month}/${day}`;
	},
	'yyyy-MM-dd': (date: Date, locale: string) => {
		const {day, month, year} = getDateParts(date, locale);

		return `${year}-${month}-${day}`;
	},
};

function format(
	date: Date,
	format: keyof typeof FORMATTER_MAP,
	locale = Liferay.ThemeDisplay.getBCP47LanguageId()
) {
	const formatter = FORMATTER_MAP[format];

	if (!formatter) {

		// eslint-disable-next-line no-console
		console.log(`No formatter found for ${format}`);
	}

	if (!(date instanceof Date)) {
		date = new Date(date);
	}

	return formatter(date, locale);
}

const PARSER_MAP = {
	'MM/dd/yyyy': (dateString: string) => {
		const [month, day, year] = dateString.split('/');

		return new Date(Number(year), Number(month) - 1, Number(day));
	},
	'P': (dateString: string, locale: string) => {
		const dateFormat = new Intl.DateTimeFormat(locale, {
			day: '2-digit',
			month: '2-digit',
			year: 'numeric',
		})
			.format(new Date(2000, 0, 2))
			.replace('2000', 'yyyy')
			.replace('01', 'MM')
			.replace('02', 'dd');

		const day = dateString.substr(dateFormat.indexOf('d'), 2);
		const month = dateString.substr(dateFormat.indexOf('M'), 2);
		const year = dateString.substr(dateFormat.indexOf('y'), 4);

		return new Date(Number(year), Number(month) - 1, Number(day));
	},
	'yyyy/MM/dd': (dateString: string) => {
		const [year, month, day] = dateString.split('/');

		return new Date(Number(year), Number(month) - 1, Number(day));
	},
	'yyyy-MM-dd': (dateString: string) => {
		const [year, month, day] = dateString.split('-');

		return new Date(Number(year), Number(month) - 1, Number(day));
	},
};

function parse(
	date: string,
	format: keyof typeof PARSER_MAP,
	locale = Liferay.ThemeDisplay.getBCP47LanguageId()
) {
	const parser = PARSER_MAP[format];

	if (!parser) {

		// eslint-disable-next-line no-console
		console.log(`No parser found for ${format}`);
	}

	return parser(date, locale);
}

export default {
	format,
	isValid,
	parse,
	subDays,
	subMonths,
};
