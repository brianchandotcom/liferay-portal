/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

import {
	isCompleteDateTime,
	isCompleteTime,
	toDateText,
	toTimeText,
	toWallClockDate,
	toZonedDate,
} from '../../../../utils/dateTime';
import {
	IntervalUnit,
	LAST_WEEKDAY_ORDINAL,
	MONTH_DAYS,
	RepeatType,
	ScheduleValues,
	WEEKDAYS,
} from './types';
import {MONTHS, getWeekdayName, hasEndDate} from './utils';

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
		if (!scheduleValues.cronExpression.trim()) {
			return null;
		}

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

	if (
		!scheduleValues.repeatOnTimeSynced &&
		!isCompleteTime(scheduleValues.repeatOnTime)
	) {
		return null;
	}

	const timeText = scheduleValues.repeatOnTimeSynced
		? startTimeText
		: toTimeText(
				toWallClockDate(
					`${scheduleValues.startDateTime.split(' ')[0]} ${
						scheduleValues.repeatOnTime
					}`
				),
				locale
			);

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

function getListText(labels: string[], locale: string): string {
	if (typeof Intl.ListFormat === 'function') {
		return new Intl.ListFormat(locale, {
			style: 'long',
			type: 'conjunction',
		}).format(labels);
	}

	return labels.join(', ');
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

function getMonthListText(months: number[], locale: string): string {
	return getListText(
		[...months]
			.sort((first, second) => first - second)
			.map((month) => MONTHS[month - 1].label),
		locale
	);
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

	if (scheduleValues.repeatType === RepeatType.DayOfWeek) {
		const weekdayOrdinalText = getWeekdayOrdinalProseText(
			scheduleValues.weekdayOrdinal
		);
		const weekdayText = getWeekdayName(scheduleValues.weekday, locale);

		if (everyMonth) {
			return sub(
				Liferay.Language.get(
					'the-process-repeats-every-month-on-the-x-x-at-x'
				),
				weekdayOrdinalText,
				weekdayText,
				timeText
			);
		}

		return sub(
			Liferay.Language.get('the-process-repeats-in-x-on-the-x-x-at-x'),
			monthListText,
			weekdayOrdinalText,
			weekdayText,
			timeText
		);
	}

	if (
		!scheduleValues.monthDays.length ||
		scheduleValues.monthDays.length === MONTH_DAYS.length
	) {
		if (everyMonth) {
			return sub(
				Liferay.Language.get('the-process-repeats-every-month-at-x'),
				timeText
			);
		}

		return sub(
			Liferay.Language.get('the-process-repeats-every-day-in-x-at-x'),
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
			Liferay.Language.get('the-process-repeats-every-month-on-x-at-x'),
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

function getRepeatText(
	scheduleValues: ScheduleValues,
	locale: string,
	timeText: string
): string {
	if (scheduleValues.unit === IntervalUnit.Day) {
		return sub(
			Liferay.Language.get('the-process-repeats-every-day-at-x'),
			timeText
		);
	}

	if (scheduleValues.unit === IntervalUnit.Week) {
		return sub(
			Liferay.Language.get('the-process-repeats-every-week-on-x-at-x'),
			getWeekdayListText(scheduleValues.weekdays, locale),
			timeText
		);
	}

	if (scheduleValues.unit === IntervalUnit.Year) {
		return getYearlyRepeatText(scheduleValues, locale, timeText);
	}

	return getMonthlyRepeatText(scheduleValues, locale, timeText);
}

function getWeekdayListText(weekdays: number[], locale: string): string {
	return getListText(
		WEEKDAYS.filter((weekday) => weekdays.includes(weekday)).map(
			(weekday) => getWeekdayName(weekday, locale)
		),
		locale
	);
}

function getWeekdayOrdinalProseText(weekdayOrdinal: string): string {
	if (weekdayOrdinal === '1') {
		return Liferay.Language.get('repeat-first');
	}

	if (weekdayOrdinal === '2') {
		return Liferay.Language.get('repeat-second');
	}

	if (weekdayOrdinal === '3') {
		return Liferay.Language.get('repeat-third');
	}

	if (weekdayOrdinal === '4') {
		return Liferay.Language.get('repeat-fourth');
	}

	if (weekdayOrdinal === LAST_WEEKDAY_ORDINAL) {
		return Liferay.Language.get('repeat-last');
	}

	return weekdayOrdinal;
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

	if (scheduleValues.repeatType === RepeatType.DayOfWeek) {
		const weekdayOrdinalText = getWeekdayOrdinalProseText(
			scheduleValues.weekdayOrdinal
		);
		const weekdayText = getWeekdayName(scheduleValues.weekday, locale);

		if (scheduleValues.yearInterval > 1) {
			return sub(
				Liferay.Language.get(
					'the-process-repeats-every-x-years-in-x-on-the-x-x-at-x'
				),
				new Intl.NumberFormat(locale).format(
					scheduleValues.yearInterval
				),
				monthListText,
				weekdayOrdinalText,
				weekdayText,
				timeText
			);
		}

		return sub(
			Liferay.Language.get(
				'the-process-repeats-every-year-in-x-on-the-x-x-at-x'
			),
			monthListText,
			weekdayOrdinalText,
			weekdayText,
			timeText
		);
	}

	const monthDayListText = getMonthDayListText(
		scheduleValues.monthDays.length ? scheduleValues.monthDays : [1],
		locale
	);

	if (scheduleValues.yearInterval > 1) {
		return sub(
			Liferay.Language.get(
				'the-process-repeats-every-x-years-in-x-on-x-at-x'
			),
			new Intl.NumberFormat(locale).format(scheduleValues.yearInterval),
			monthListText,
			monthDayListText,
			timeText
		);
	}

	return sub(
		Liferay.Language.get('the-process-repeats-every-year-in-x-on-x-at-x'),
		monthListText,
		monthDayListText,
		timeText
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
