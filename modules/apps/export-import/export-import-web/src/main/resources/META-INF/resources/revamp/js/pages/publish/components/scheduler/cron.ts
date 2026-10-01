/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	isCompleteDateTime,
	isCompleteTime,
	toDateTimeParts,
	toTimeParts,
} from '../../../../utils/dateTime';
import {
	IntervalUnit,
	LAST_WEEKDAY_ORDINAL,
	RepeatType,
	ScheduleValues,
	YEAR_INTERVALS,
} from './types';
import {WEEKDAY_ORDINAL_OPTIONS, getInitialScheduleValues} from './utils';

enum CronField {
	Second,
	Minute,
	Hour,
	DayOfMonth,
	Month,
	DayOfWeek,
	Year,
}

const DAY_OF_WEEK_NAMES = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];

const DEFAULT_WEEKDAY = 2;

const MONTH_NAMES = [
	'JAN',
	'FEB',
	'MAR',
	'APR',
	'MAY',
	'JUN',
	'JUL',
	'AUG',
	'SEP',
	'OCT',
	'NOV',
	'DEC',
];

const FIELD_BOUNDS = [
	{maximum: 59, minimum: 0, names: []},
	{maximum: 59, minimum: 0, names: []},
	{maximum: 23, minimum: 0, names: []},
	{maximum: 31, minimum: 1, names: []},
	{maximum: 12, minimum: 1, names: MONTH_NAMES},
	{maximum: 7, minimum: 1, names: DAY_OF_WEEK_NAMES},
	{maximum: 2099, minimum: 1970, names: []},
];

export function fromCronExpression(
	cronExpression: string,
	startDateTime: string
): Partial<ScheduleValues> {
	const canonicalCronExpression = toCanonicalCronExpression(cronExpression);

	if (canonicalCronExpression === null) {
		return {cronExpression, unit: IntervalUnit.Custom};
	}

	const scheduleValues = {
		...fromSupportedCronExpression(cronExpression),
		...toRepeatOnTimeFields(cronExpression, startDateTime),
	};

	if (
		toCanonicalCronExpression(
			toCronExpression({
				...getInitialScheduleValues('UTC'),
				startDateTime,
				...scheduleValues,
			})
		) !== canonicalCronExpression
	) {
		return {cronExpression, unit: IntervalUnit.Custom};
	}

	return {...scheduleValues, storedCronExpression: cronExpression};
}

export function toCronExpression(scheduleValues: ScheduleValues): string {
	if (scheduleValues.unit === IntervalUnit.Custom) {
		return scheduleValues.cronExpression;
	}

	const {day, hour, minute, month, year} = toDateTimeParts(
		scheduleValues.startDateTime
	);

	if (scheduleValues.unit === IntervalUnit.Never) {
		return `0 ${minute} ${hour} ${day} ${month} ? ${year}`;
	}

	const repeatOnTimeParts =
		scheduleValues.repeatOnTimeSynced ||
		!isCompleteTime(scheduleValues.repeatOnTime)
			? {hour, minute}
			: toTimeParts(scheduleValues.repeatOnTime);

	if (scheduleValues.unit === IntervalUnit.Week) {
		const weekdays = scheduleValues.weekdays.length
			? scheduleValues.weekdays
			: [DEFAULT_WEEKDAY];

		const dayOfWeek = [...weekdays]
			.sort((first, second) => first - second)
			.map((weekday) => DAY_OF_WEEK_NAMES[weekday - 1])
			.join(',');

		return `0 ${repeatOnTimeParts.minute} ${repeatOnTimeParts.hour} ? * ${dayOfWeek} *`;
	}

	if (scheduleValues.unit === IntervalUnit.Day) {
		return `0 ${repeatOnTimeParts.minute} ${repeatOnTimeParts.hour} * * ? *`;
	}

	if (scheduleValues.unit === IntervalUnit.Year) {
		const repeatMonth = scheduleValues.months[0] ?? 1;

		const yearField = `${year}/${
			scheduleValues.yearInterval > 0 ? scheduleValues.yearInterval : 1
		}`;

		if (scheduleValues.repeatType === RepeatType.DayOfWeek) {
			return `0 ${repeatOnTimeParts.minute} ${
				repeatOnTimeParts.hour
			} ? ${repeatMonth} ${toDayOfWeekExpression(scheduleValues)} ${yearField}`;
		}

		return `0 ${repeatOnTimeParts.minute} ${repeatOnTimeParts.hour} ${scheduleValues.monthDays[0] ?? 1} ${repeatMonth} ? ${yearField}`;
	}

	const monthsField = toNumberListField(scheduleValues.months, 12);

	if (scheduleValues.repeatType === RepeatType.DayOfWeek) {
		return `0 ${repeatOnTimeParts.minute} ${
			repeatOnTimeParts.hour
		} ? ${monthsField} ${toDayOfWeekExpression(scheduleValues)} *`;
	}

	return `0 ${repeatOnTimeParts.minute} ${repeatOnTimeParts.hour} ${toNumberListField(
		scheduleValues.monthDays,
		31
	)} ${monthsField} ? *`;
}

export function toCustomCronExpression(scheduleValues: ScheduleValues): string {
	if (
		scheduleValues.cronExpression ||
		!isCompleteDateTime(scheduleValues.startDateTime) ||
		!scheduleValues.storedCronExpression
	) {
		return scheduleValues.cronExpression;
	}

	const cronExpression = toCronExpression(scheduleValues);

	if (
		toCanonicalCronExpression(cronExpression) ===
		toCanonicalCronExpression(scheduleValues.storedCronExpression)
	) {
		return scheduleValues.storedCronExpression;
	}

	return cronExpression;
}

function fromDayOfWeekExpression(
	dayOfWeekExpression: string
): Partial<ScheduleValues> {
	let dayOfWeekAbbreviation = dayOfWeekExpression;
	let weekdayOrdinal = '1';

	if (dayOfWeekExpression.includes('#')) {
		[dayOfWeekAbbreviation, weekdayOrdinal] =
			dayOfWeekExpression.split('#');

		if (
			!WEEKDAY_ORDINAL_OPTIONS.some(({value}) => value === weekdayOrdinal)
		) {
			weekdayOrdinal = '1';
		}
	}
	else if (dayOfWeekExpression.endsWith('L')) {
		dayOfWeekAbbreviation = dayOfWeekExpression.slice(0, -1);
		weekdayOrdinal = LAST_WEEKDAY_ORDINAL;
	}

	return {
		repeatType: RepeatType.DayOfWeek,
		weekday:
			toFieldNumber(dayOfWeekAbbreviation, DAY_OF_WEEK_NAMES) ||
			DEFAULT_WEEKDAY,
		weekdayOrdinal,
	};
}

function fromSupportedCronExpression(
	cronExpression: string
): Partial<ScheduleValues> {
	const [, , , dayOfMonth, month, dayOfWeek, year] =
		toCronFields(cronExpression);

	if (year !== '*' && !year.includes('/')) {
		return {unit: IntervalUnit.Never};
	}

	const months = toFieldNumbers(month, CronField.Month) ?? [];

	let yearInterval = Number(year.split('/')[1]) || 1;

	if (!YEAR_INTERVALS.includes(yearInterval)) {
		yearInterval = 1;
	}

	if (dayOfWeek !== '?' && dayOfWeek !== '*') {
		if (isOrdinalDayOfWeek(dayOfWeek)) {
			return {
				...fromDayOfWeekExpression(dayOfWeek),
				months,
				unit: year.includes('/')
					? IntervalUnit.Year
					: IntervalUnit.Month,
				yearInterval,
			};
		}

		return {
			unit: IntervalUnit.Week,
			weekdays: toFieldNumbers(dayOfWeek, CronField.DayOfWeek) ?? [
				DEFAULT_WEEKDAY,
			],
		};
	}

	const monthDays = toFieldNumbers(dayOfMonth, CronField.DayOfMonth) ?? [];

	if (year.includes('/')) {
		return {
			monthDays,
			months,
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Year,
			yearInterval,
		};
	}

	if (!months.length && !monthDays.length) {
		return {monthDays, months, unit: IntervalUnit.Day};
	}

	return {
		monthDays,
		months,
		repeatType: RepeatType.DayOfMonth,
		unit: IntervalUnit.Month,
	};
}

function isOrdinalDayOfWeek(dayOfWeek: string): boolean {
	return dayOfWeek.includes('#') || /[A-Z0-9]L$/i.test(dayOfWeek);
}

function toCanonicalCronExpression(cronExpression: string): string | null {
	const fields = toCronFields(cronExpression);

	if (fields.length !== 7) {
		return null;
	}

	return fields.reduce((canonical: string | null, field, cronField) => {
		if (canonical === null) {
			return null;
		}

		if (cronField === CronField.DayOfWeek && isOrdinalDayOfWeek(field)) {
			return `${canonical} ${field}`;
		}

		const numbers = toFieldNumbers(field, cronField);

		if (numbers === null) {
			return null;
		}

		return `${canonical} ${numbers.join(',') || '*'}`;
	}, '');
}

function toCronFields(cronExpression: string): string[] {
	const fields = cronExpression.trim().toUpperCase().split(/\s+/);

	return fields.length === 6 ? [...fields, '*'] : fields;
}

function toDayOfWeekExpression(scheduleValues: ScheduleValues): string {
	const dayOfWeekAbbreviation = DAY_OF_WEEK_NAMES[scheduleValues.weekday - 1];

	if (scheduleValues.weekdayOrdinal === LAST_WEEKDAY_ORDINAL) {
		return `${dayOfWeekAbbreviation}L`;
	}

	return `${dayOfWeekAbbreviation}#${scheduleValues.weekdayOrdinal}`;
}

function toFieldNumber(value: string, names: string[]): number {
	const nameIndex = names.indexOf(value.toUpperCase());

	if (nameIndex >= 0) {
		return nameIndex + 1;
	}

	return Number(value);
}

function toFieldNumbers(field: string, cronField: CronField): number[] | null {
	if (field === '*' || field === '?') {
		return [];
	}

	const {maximum, minimum, names} = FIELD_BOUNDS[cronField];

	const numbers = new Set<number>();

	for (const term of field.split(',')) {
		const [range, stepString] = term.split('/');
		const [startString, endString] = range.split('-');

		const start = toFieldNumber(startString, names);
		const step = stepString ? Number(stepString) : 1;

		let end = start;

		if (endString !== undefined) {
			end = toFieldNumber(endString, names);
		}
		else if (stepString) {
			end = maximum;
		}

		if (
			!Number.isInteger(start) ||
			!Number.isInteger(end) ||
			!Number.isInteger(step) ||
			start < minimum ||
			end > maximum ||
			end < start ||
			step < 1
		) {
			return null;
		}

		for (let number = start; number <= end; number += step) {
			numbers.add(number);
		}
	}

	return [...numbers].sort((first, second) => first - second);
}

function toNumberListField(numbers: number[], maximum: number): string {
	if (!numbers.length || numbers.length === maximum) {
		return '*';
	}

	return [...numbers].sort((first, second) => first - second).join(',');
}

function toRepeatOnTimeFields(
	cronExpression: string,
	startDateTime: string
): Partial<ScheduleValues> {
	const [, minute, hour] = toCronFields(cronExpression);

	const hours = toFieldNumbers(hour, CronField.Hour);
	const minutes = toFieldNumbers(minute, CronField.Minute);

	if (hours?.length !== 1 || minutes?.length !== 1) {
		return {};
	}

	const repeatOnTime = `${String(hours[0]).padStart(2, '0')}:${String(
		minutes[0]
	).padStart(2, '0')}`;

	if (startDateTime.split(' ')[1] === repeatOnTime) {
		return {};
	}

	return {repeatOnTime, repeatOnTimeSynced: false};
}
