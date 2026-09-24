/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {
	isCompleteDateTime,
	isCompleteTime,
	toDateText,
	toTimeParts,
	toTimeText,
	toWallClockDate,
	toZonedDate,
} from '../../../../utils/dateTime';
import {
	IntervalUnit,
	MONTH_DAYS,
	RepeatType,
	ScheduleValues,
	WEEKDAYS,
} from './types';
import {
	MONTHS,
	getIntervalText,
	getWeekdayName,
	getWeekdayOrdinalProseText,
	hasEndDate,
	isRepeatingUnit,
} from './utils';

function toRepeatDate(startDate: Date, repeatOnTime: string): Date {
	const {hour, minute} = toTimeParts(repeatOnTime);

	const repeatDate = new Date(startDate);

	repeatDate.setUTCHours(hour, minute);

	return repeatDate;
}

function getListText(labels: string[], locale: string): string {
	if (typeof Intl.ListFormat === 'function') {
		return new Intl.ListFormat(locale, {
			style: 'long',
			type: 'conjunction',
		}).format(labels);
	}

	return labels.join(', ');
}

function getWeekdayListText(weekdays: number[], locale: string): string {
	return getListText(
		WEEKDAYS.filter((weekday) => weekdays.includes(weekday)).map(
			(weekday) => getWeekdayName(weekday, locale)
		),
		locale
	);
}

function getWeekdayOrdinalText(
	scheduleValues: ScheduleValues,
	locale: string
): string {
	return `${getWeekdayOrdinalProseText(scheduleValues.weekdayOrdinal)} ${getWeekdayName(scheduleValues.weekday, locale)}`;
}

function getMonthListText(months: number[], locale: string): string {
	return getListText(
		[...months]
			.sort((first, second) => first - second)
			.map((month) => MONTHS[month - 1].label),
		locale
	);
}

function toMonthDayRanges(monthDays: number[]): string[] {
	const sortedMonthDays = [...monthDays].sort(
		(first, second) => first - second
	);

	return sortedMonthDays
		.reduce((ranges: number[][], monthDay) => {
			const range = ranges[ranges.length - 1];

			if (range && monthDay === range[range.length - 1] + 1) {
				range.push(monthDay);
			}
			else {
				ranges.push([monthDay]);
			}

			return ranges;
		}, [])
		.flatMap((range) =>
			range.length > 2
				? [`${range[0]}-${range[range.length - 1]}`]
				: range.map(String)
		);
}

function getMonthDayListText(monthDays: number[], locale: string): string {
	if (monthDays.length === 1) {
		return sub(Liferay.Language.get('repeat-day-x'), String(monthDays[0]));
	}

	return sub(
		Liferay.Language.get('repeat-days-x'),
		getListText(toMonthDayRanges(monthDays), locale)
	);
}

function getUnitText(scheduleValues: ScheduleValues, locale: string): string {
	if (scheduleValues.unit === IntervalUnit.Day) {
		return Liferay.Language.get('repeat-unit-day');
	}

	if (scheduleValues.unit === IntervalUnit.Week) {
		return Liferay.Language.get('repeat-unit-week');
	}

	if (scheduleValues.unit === IntervalUnit.Month) {
		return Liferay.Language.get('repeat-unit-month');
	}

	if (scheduleValues.yearInterval > 1) {
		return getIntervalText(
			scheduleValues.yearInterval,
			IntervalUnit.Year,
			locale
		);
	}

	return Liferay.Language.get('repeat-unit-year');
}

function getMonthlyRepeatText(
	scheduleValues: ScheduleValues,
	locale: string,
	timeText: string
): string {
	const everyMonth =
		!scheduleValues.months.length ||
		scheduleValues.months.length === MONTHS.length;

	const monthListText = getMonthListText(scheduleValues.months, locale);
	const unitText = getUnitText(scheduleValues, locale);

	if (scheduleValues.repeatType === RepeatType.DayOfWeek) {
		const weekdayOrdinalText = getWeekdayOrdinalText(
			scheduleValues,
			locale
		);

		if (everyMonth) {
			return sub(
				Liferay.Language.get(
					'the-process-repeats-every-x-on-the-x-at-x'
				),
				unitText,
				weekdayOrdinalText,
				timeText
			);
		}

		return sub(
			Liferay.Language.get('the-process-repeats-in-x-on-the-x-at-x'),
			monthListText,
			weekdayOrdinalText,
			timeText
		);
	}

	if (
		!scheduleValues.monthDays.length ||
		scheduleValues.monthDays.length === MONTH_DAYS.length
	) {
		if (everyMonth) {
			return sub(
				Liferay.Language.get('the-process-repeats-every-x-at-x'),
				unitText,
				timeText
			);
		}

		return sub(
			Liferay.Language.get('the-process-repeats-in-x-at-x'),
			monthListText,
			timeText
		);
	}

	const monthDayListText = getMonthDayListText(
		scheduleValues.monthDays,
		locale
	);

	if (everyMonth) {
		return sub(
			Liferay.Language.get('the-process-repeats-every-x-on-x-at-x'),
			unitText,
			monthDayListText,
			timeText
		);
	}

	return sub(
		Liferay.Language.get('the-process-repeats-in-x-on-x-at-x'),
		monthListText,
		monthDayListText,
		timeText
	);
}

function getYearlyRepeatText(
	scheduleValues: ScheduleValues,
	locale: string,
	timeText: string
): string {
	const monthListText = getMonthListText(
		scheduleValues.months.length ? scheduleValues.months : [1],
		locale
	);
	const unitText = getUnitText(scheduleValues, locale);

	if (scheduleValues.repeatType === RepeatType.DayOfWeek) {
		return sub(
			Liferay.Language.get(
				'the-process-repeats-every-x-in-x-on-the-x-at-x'
			),
			unitText,
			monthListText,
			getWeekdayOrdinalText(scheduleValues, locale),
			timeText
		);
	}

	return sub(
		Liferay.Language.get('the-process-repeats-every-x-in-x-on-x-at-x'),
		unitText,
		monthListText,
		getMonthDayListText(
			scheduleValues.monthDays.length ? scheduleValues.monthDays : [1],
			locale
		),
		timeText
	);
}

function getRepeatText(
	scheduleValues: ScheduleValues,
	locale: string,
	timeText: string
): string {
	if (scheduleValues.unit === IntervalUnit.Day) {
		return sub(
			Liferay.Language.get('the-process-repeats-every-x-at-x'),
			getUnitText(scheduleValues, locale),
			timeText
		);
	}

	if (scheduleValues.unit === IntervalUnit.Week) {
		return sub(
			Liferay.Language.get('the-process-repeats-every-x-on-x-at-x'),
			getUnitText(scheduleValues, locale),
			getWeekdayListText(scheduleValues.weekdays, locale),
			timeText
		);
	}

	if (scheduleValues.unit === IntervalUnit.Year) {
		return getYearlyRepeatText(scheduleValues, locale, timeText);
	}

	return getMonthlyRepeatText(scheduleValues, locale, timeText);
}

export function getScheduleSummary(
	scheduleValues: ScheduleValues
): string | null {
	if (!scheduleValues.enabled || !scheduleValues.startDateTime) {
		return null;
	}

	if (!isCompleteDateTime(scheduleValues.startDateTime)) {
		return null;
	}

	const startDate = toWallClockDate(scheduleValues.startDateTime);

	if (
		toZonedDate(
			scheduleValues.startDateTime,
			scheduleValues.timeZoneId
		).getTime() < Date.now()
	) {
		return null;
	}

	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	const startDateText = toDateText(startDate, locale);

	const startTimeText = toTimeText(startDate, locale);

	if (scheduleValues.unit === IntervalUnit.Never) {
		return sub(
			Liferay.Language.get(
				'the-process-runs-once-on-x-at-x-and-does-not-repeat'
			),
			startDateText,
			startTimeText
		);
	}

	if (
		hasEndDate(scheduleValues) &&
		!isCompleteDateTime(scheduleValues.endDateTime)
	) {
		return null;
	}

	const endDate = hasEndDate(scheduleValues)
		? toWallClockDate(scheduleValues.endDateTime)
		: null;

	if (scheduleValues.unit === IntervalUnit.Custom) {
		return endDate
			? sub(
					Liferay.Language.get(
						'the-process-starts-on-x-at-x-and-ends-on-x-at-x'
					),
					startDateText,
					startTimeText,
					toDateText(endDate, locale),
					toTimeText(endDate, locale)
				)
			: sub(
					Liferay.Language.get(
						'the-process-starts-on-x-at-x-and-never-ends'
					),
					startDateText,
					startTimeText
				);
	}

	const repeatDate =
		isRepeatingUnit(scheduleValues.unit) &&
		!scheduleValues.repeatOnTimeSynced &&
		isCompleteTime(scheduleValues.repeatOnTime)
			? toRepeatDate(startDate, scheduleValues.repeatOnTime)
			: null;

	const timeText = repeatDate
		? toTimeText(repeatDate, locale)
		: startTimeText;

	const activeFromText = endDate
		? sub(
				Liferay.Language.get(
					'the-process-is-active-from-x-at-x-and-ends-on-x-at-x'
				),
				startDateText,
				startTimeText,
				toDateText(endDate, locale),
				toTimeText(endDate, locale)
			)
		: sub(
				Liferay.Language.get(
					'the-process-is-active-from-x-at-x-and-never-ends'
				),
				startDateText,
				startTimeText
			);

	return `${activeFromText} ${getRepeatText(scheduleValues, locale, timeText)}`;
}
