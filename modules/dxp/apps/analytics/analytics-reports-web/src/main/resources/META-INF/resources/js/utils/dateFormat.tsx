/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * It generates a set of functions used to produce
 * internationalized date related content.
 */
interface OptionsI {
	day: 'numeric' | '2-digit' | undefined;
	month: 'numeric' | '2-digit' | 'short' | 'long' | 'narrow' | undefined;
	year: 'numeric' | '2-digit' | undefined;
}

export function generateDateFormatters(key: string) {

	/**
	 * Given 2 date objects it produces a user friendly date interval
	 *
	 * For 'en-US'
	 * [Date, Date] => '16 - Jun 21, 2020'
	 */
	function formatChartTitle([initialDate, finalDate]: Date[]) {
		const singleDayDateRange =
			finalDate.getTime() - initialDate.getTime() <= 1000 * 60 * 60 * 24;

		const dateFormatter = (
			date: Date,
			options: OptionsI = {
				day: 'numeric',
				month: 'short',
				year: 'numeric',
			}
		) => Intl.DateTimeFormat([key], options).format(date);

		const equalMonth = initialDate.getMonth() === finalDate.getMonth();
		const equalYear = initialDate.getFullYear() === finalDate.getFullYear();

		const initialDateOptions: OptionsI = {
			day: 'numeric',
			month: equalMonth && equalYear ? undefined : 'short',
			year: equalYear ? undefined : 'numeric',
		};

		if (singleDayDateRange) {
			return dateFormatter(finalDate);
		}

		return `${dateFormatter(
			initialDate,
			initialDateOptions
		)} - ${dateFormatter(finalDate)}`;
	}

	/**
	 * Given a date-like string it produces a internationalized long date
	 *
	 * For 'en-US'
	 * String => '06/17/2020'
	 */
	function formatLongDate(value: string) {
		return Intl.DateTimeFormat([key]).format(new Date(value));
	}

	/**
	 * Given a date-like string produces the day of the month
	 *
	 * For 'en-US'
	 * String => '16'
	 */
	function formatNumericDay(value: string) {
		return Intl.DateTimeFormat([key], {
			day: 'numeric',
		}).format(new Date(value));
	}

	/**
	 * Given a date-like string produces the hour of the day
	 *
	 * For 'en-US'
	 * String => '04 AM'
	 */
	function formatNumericHour(value: string) {
		return Intl.DateTimeFormat([key], {
			hour: 'numeric',
		}).format(new Date(value));
	}

	return {
		formatChartTitle,
		formatLongDate,
		formatNumericDay,
		formatNumericHour,
	};
}
