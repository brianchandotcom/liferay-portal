/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum IntervalUnit {
	Custom = 'custom',
	Day = 'day',
	Month = 'month',
	Never = 'never',
	Week = 'week',
	Year = 'year',
}

export enum RepeatType {
	DayOfMonth = 'day-of-month',
	DayOfWeek = 'day-of-week',
}

export const LAST_WEEKDAY_ORDINAL = 'last';

export const WEEKDAYS = [2, 3, 4, 5, 6, 7, 1];

export const MONTHS = [
	{label: Liferay.Language.get('january'), value: 0},
	{label: Liferay.Language.get('february'), value: 1},
	{label: Liferay.Language.get('march'), value: 2},
	{label: Liferay.Language.get('april'), value: 3},
	{label: Liferay.Language.get('may'), value: 4},
	{label: Liferay.Language.get('june'), value: 5},
	{label: Liferay.Language.get('july'), value: 6},
	{label: Liferay.Language.get('august'), value: 7},
	{label: Liferay.Language.get('september'), value: 8},
	{label: Liferay.Language.get('october'), value: 9},
	{label: Liferay.Language.get('november'), value: 10},
	{label: Liferay.Language.get('december'), value: 11},
];

export const REPEAT_OPTIONS = [
	{label: Liferay.Language.get('never'), value: IntervalUnit.Never},
	{label: Liferay.Language.get('daily'), value: IntervalUnit.Day},
	{label: Liferay.Language.get('weekly'), value: IntervalUnit.Week},
	{label: Liferay.Language.get('monthly'), value: IntervalUnit.Month},
	{label: Liferay.Language.get('yearly'), value: IntervalUnit.Year},
	{label: Liferay.Language.get('custom'), value: IntervalUnit.Custom},
];

export const REPEAT_TYPE_OPTIONS = [
	{label: Liferay.Language.get('day-of-month'), value: RepeatType.DayOfMonth},
	{label: Liferay.Language.get('day-of-week'), value: RepeatType.DayOfWeek},
];

export const WEEKDAY_ORDINAL_OPTIONS = [
	{label: Liferay.Language.get('first'), value: '1'},
	{label: Liferay.Language.get('second'), value: '2'},
	{label: Liferay.Language.get('third'), value: '3'},
	{label: Liferay.Language.get('fourth'), value: '4'},
	{label: Liferay.Language.get('last'), value: LAST_WEEKDAY_ORDINAL},
];

export const MONTH_DAYS = Array.from({length: 31}, (_, index) => index + 1);

export const YEAR_INTERVALS = Array.from({length: 10}, (_, index) => index + 1);

export const DATE_TIME_FORMAT = 'yyyy-MM-dd';

export function getIntervalText(
	interval: number,
	unit: IntervalUnit,
	locale: string
): string {
	return new Intl.NumberFormat(locale, {
		style: 'unit',
		unit,
		unitDisplay: 'long',
	} as Intl.NumberFormatOptions).format(interval);
}

const FIRST_SUNDAY_OF_JANUARY_2026 = 4;

export function getWeekdayName(weekday: number, locale: string): string {
	return new Date(
		2026,
		0,
		FIRST_SUNDAY_OF_JANUARY_2026 + weekday - 1
	).toLocaleDateString(locale, {weekday: 'long'});
}

export type TimeZoneOption = {
	label: string;
	value: string;
};

export type ScheduleValues = {
	cronExpression: string;
	enabled: boolean;
	endDateTime: string;
	monthDays: number[];
	months: number[];
	neverEnd: boolean;
	repeatType: RepeatType;
	startDateTime: string;
	timeZoneId: string;
	unit: IntervalUnit;
	weekDays: number[];
	weekday: number;
	weekdayOrdinal: string;
	yearInterval: number;
};

export function getInitialScheduleValues(
	timeZoneId: string,
	enabled = false
): ScheduleValues {
	return {
		cronExpression: '',
		enabled,
		endDateTime: '',
		monthDays: [1],
		months: [],
		neverEnd: true,
		repeatType: RepeatType.DayOfMonth,
		startDateTime: '',
		timeZoneId,
		unit: IntervalUnit.Never,
		weekDays: [2],
		weekday: 2,
		weekdayOrdinal: '1',
		yearInterval: 1,
	};
}
