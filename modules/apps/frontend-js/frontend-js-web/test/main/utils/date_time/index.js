/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {default as dateTimeUtils} from '../../../../src/main/resources/META-INF/resources/main/utils/date_time/index';

describe('date_time utils', () => {
	it('returns the first day of the week for a given locale', () => {
		const usLocale = 'en-US';
		const firstDayOfWeekUS = dateTimeUtils.getFirstDayOfWeek(usLocale);

		expect(firstDayOfWeekUS).toBe(0);

		const frenchLocale = 'fr-FR';
		const firstDayOfWeekFR = dateTimeUtils.getFirstDayOfWeek(frenchLocale);

		expect(firstDayOfWeekFR).toBe(1);
	});

	it('returns short weekdays for a given locale', () => {
		const usLocale = 'en-US';
		const weekdaysShortUS = dateTimeUtils.getWeekdaysShort(usLocale);

		expect(weekdaysShortUS).toEqual([
			'Sun',
			'Mon',
			'Tue',
			'Wed',
			'Thu',
			'Fri',
			'Sat',
		]);

		const frenchLocale = 'fr-FR';
		const weekdaysShortFR = dateTimeUtils.getWeekdaysShort(frenchLocale);

		expect(weekdaysShortFR).toEqual([
			'dim.',
			'lun.',
			'mar.',
			'mer.',
			'jeu.',
			'ven.',
			'sam.',
		]);
	});

	it('returns long month names for a given locale', () => {
		const usLocale = 'en-US';
		const monthsLongUS = dateTimeUtils.getMonthsLong(usLocale);

		expect(monthsLongUS).toEqual([
			'January',
			'February',
			'March',
			'April',
			'May',
			'June',
			'July',
			'August',
			'September',
			'October',
			'November',
			'December',
		]);

		const frenchLocale = 'fr-FR';
		const monthsLongFR = dateTimeUtils.getMonthsLong(frenchLocale);

		expect(monthsLongFR).toEqual([
			'janvier',
			'février',
			'mars',
			'avril',
			'mai',
			'juin',
			'juillet',
			'août',
			'septembre',
			'octobre',
			'novembre',
			'décembre',
		]);
	});

	it('returns relative time from now', () => {
		const now = new Date();
		const tenMinutesAgo = new Date(now.getTime() - 10 * 60 * 1000);
		const result = dateTimeUtils.fromNow(tenMinutesAgo, 'en-US');

		expect(result).toBe('10 minutes ago');
	});

	it('subtracts days from a date', () => {
		const date = new Date(2025, 0, 10);
		const result = dateTimeUtils.subDays(date, 5);

		expect(result.getDate()).toBe(5);
	});

	it('subtracts months from a date', () => {
		const date = new Date(2025, 5, 10);
		const result = dateTimeUtils.subMonths(date, 3);

		expect(result.getMonth()).toBe(2);
	});

	it('checks if a date is valid', () => {
		const validDate = new Date();

		expect(dateTimeUtils.isValid(validDate)).toBe(true);

		const invalidDate = 'invalid date';

		expect(dateTimeUtils.isValid(invalidDate)).toBe(false);
	});

	it('checks if two dates are in the same month', () => {
		const date1 = new Date(2025, 0, 1);
		const date2 = new Date(2025, 0, 31);

		expect(dateTimeUtils.isSameMonth(date1, date2)).toBe(true);

		const date3 = new Date(2025, 0, 1);
		const date4 = new Date(2025, 1, 1);

		expect(dateTimeUtils.isSameMonth(date3, date4)).toBe(false);
	});

	it('checks if two dates are the same day', () => {
		const date1 = new Date(2025, 0, 1);
		const date2 = new Date(2025, 0, 1);

		expect(dateTimeUtils.isSameDay(date1, date2)).toBe(true);

		const date3 = new Date(2025, 0, 1);
		const date4 = new Date(2025, 0, 2);

		expect(dateTimeUtils.isSameDay(date3, date4)).toBe(false);
	});

	it('calculates days difference between two dates', () => {
		const startDate = new Date(2025, 0, 1);
		const secondDate = new Date(2025, 0, 10);

		expect(dateTimeUtils.getDaysDiff(startDate, secondDate)).toBe(9);
	});

	it('calculates months difference between two dates', () => {
		const startDate = new Date(2025, 0, 1);
		const secondDate = new Date(2025, 6, 1);

		expect(dateTimeUtils.getMonthDiff(startDate, secondDate)).toBe(6);
	});

	it('calculates months difference between two dates across different years', () => {
		const startDate = new Date(2024, 0, 1);
		const secondDate = new Date(2025, 6, 1);

		expect(dateTimeUtils.getMonthDiff(startDate, secondDate)).toBe(18);
	});

	it('calculates years difference between two dates', () => {
		const startDate = new Date(2020, 0, 1);
		const secondDate = new Date(2025, 0, 1);

		expect(dateTimeUtils.getYearDiff(startDate, secondDate)).toBe(5);
	});

	it('checks if two dates are in the same week', () => {
		const date1 = new Date(2025, 0, 1);
		const date2 = new Date(2025, 0, 3);

		expect(dateTimeUtils.isSameWeek(date1, date2)).toBe(true);

		const date3 = new Date(2025, 0, 1);
		const date4 = new Date(2025, 0, 8);

		expect(dateTimeUtils.isSameWeek(date3, date4)).toBe(false);
	});

	it('returns the start of the week for a given date', () => {
		const date = new Date(2025, 0, 8);
		const startOfWeek = dateTimeUtils.toStartOfWeek(date);

		expect(startOfWeek.getDay()).toBe(0);
	});

	it('returns the end of the week for a given date', () => {
		const date = new Date(2025, 0, 8);
		const endOfWeek = dateTimeUtils.toEndOfWeek(date);

		expect(endOfWeek.getDay()).toBe(6);
	});

	it('returns the start of the year for a given date', () => {
		const date = new Date(2025, 0, 8);
		const startOfYear = dateTimeUtils.toStartOfYear(date);

		expect(startOfYear.getMonth()).toBe(0);
		expect(startOfYear.getDate()).toBe(1);
	});

	it('returns the end of the year for a given date', () => {
		const date = new Date(2025, 0, 8);
		const endOfYear = dateTimeUtils.toEndOfYear(date);

		expect(endOfYear.getMonth()).toBe(11);
		expect(endOfYear.getDate()).toBe(31);
	});
});
