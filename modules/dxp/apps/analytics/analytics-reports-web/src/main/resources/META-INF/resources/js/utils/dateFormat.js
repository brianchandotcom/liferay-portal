/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

/**
 * It generates a set of functions used to produce
 * internationalized date related content.
 */
export const generateDateFormatters = (key) => {

	/**
	 * Given 2 date objects it produces a user friendly date interval
	 *
	 * For 'en-US'
	 * [Date, Date] => '16 - Jun 21, 2020'
	 */
	function formatChartTitle([initialDate, finalDate]) {
		const singleDayDateRange =
			finalDate - initialDate <= 1000 * 60 * 60 * 24;

		const dateFormatter = (
			date,
			options = {
				day: 'numeric',
				month: 'short',
				year: 'numeric',
			}
		) => Intl.DateTimeFormat([key], options).format(date);

		const equalMonth = initialDate.getMonth() === finalDate.getMonth();
		const equalYear = initialDate.getYear() === finalDate.getYear();

		const initialDateOptions = {
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
	function formatLongDate(value) {
		return Intl.DateTimeFormat([key]).format(new Date(value));
	}

	/**
	 * Given a date-like string produces the day of the month
	 *
	 * For 'en-US'
	 * String => '16'
	 */
	function formatNumericDay(value) {
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
	function formatNumericHour(value) {
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
};
