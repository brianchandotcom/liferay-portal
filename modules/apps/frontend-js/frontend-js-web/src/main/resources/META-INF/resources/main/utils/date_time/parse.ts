/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const PARSER_MAP = {
	'MM/dd/yyyy': (dateString: string) => {
		const [month, day, year] = dateString.split('/');

		return new Date(Number(year), Number(month) - 1, Number(day));
	},
	'MM-dd-yyyy': (dateString: string) => {
		const [month, day, year] = dateString.split('-');

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

		const day = dateString.substring(
			dateFormat.indexOf('d'),
			dateFormat.lastIndexOf('d') + 1
		);
		const month = dateString.substring(
			dateFormat.indexOf('M'),
			dateFormat.lastIndexOf('M') + 1
		);
		const year = dateString.substring(
			dateFormat.indexOf('y'),
			dateFormat.lastIndexOf('y') + 1
		);

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
	'yyyyMMddhhmmss': (dateString: string) => {
		const year = dateString.substring(0, 4);
		const month = dateString.substring(4, 6);
		const day = dateString.substring(6, 8);
		const hours = dateString.substring(8, 10);
		const minutes = dateString.substring(10, 12);
		const seconds = dateString.substring(12, 14);

		return new Date(
			Number(year),
			Number(month) - 1,
			Number(day),
			Number(hours),
			Number(minutes),
			Number(seconds)
		);
	},
};

export function parse(
	date: string,
	format: keyof typeof PARSER_MAP,
	locale = Liferay.ThemeDisplay.getBCP47LanguageId()
) {
	if (!date) {
		return;
	}

	if (!format) {
		return date;
	}

	const parser = PARSER_MAP[format];

	if (!parser) {
		console.warn(`No parser found for '${format}'.`);

		return null;
	}

	return parser(date, locale);
}
