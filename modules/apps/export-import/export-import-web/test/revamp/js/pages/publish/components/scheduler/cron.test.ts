/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fromCronExpression,
	toCronExpression,
	toWallClockDateTime,
	toZonedDate,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/cron';
import {
	IntervalUnit,
	LAST_WEEKDAY_ORDINAL,
	RepeatType,
	ScheduleValues,
	getInitialScheduleValues,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/types';

const START_DATE_TIME = '2026-07-20 15:30';

function decode(cronExpression: string) {
	return fromCronExpression(cronExpression, START_DATE_TIME);
}

function buildScheduleValues(
	partialScheduleValues: Partial<ScheduleValues>
): ScheduleValues {
	return {
		...getInitialScheduleValues('UTC'),
		enabled: true,
		startDateTime: START_DATE_TIME,
		...partialScheduleValues,
	};
}

describe('toCronExpression', () => {
	it('produces a one-time cron from the start date parts', () => {
		expect(
			toCronExpression(buildScheduleValues({unit: IntervalUnit.Never}))
		).toBe('0 30 15 20 7 ? 2026');
	});

	it('produces a daily cron', () => {
		expect(
			toCronExpression(buildScheduleValues({unit: IntervalUnit.Day}))
		).toBe('0 30 15 * * ? *');
	});

	it('produces a weekly cron with the selected days', () => {
		expect(
			toCronExpression(
				buildScheduleValues({
					unit: IntervalUnit.Week,
					weekDays: [2, 4],
				})
			)
		).toBe('0 30 15 ? * MON,WED *');
	});

	it('produces a monthly cron with the selected months and days', () => {
		expect(
			toCronExpression(
				buildScheduleValues({
					monthDays: [15],
					months: [1, 4, 7, 10],
					unit: IntervalUnit.Month,
				})
			)
		).toBe('0 30 15 15 1,4,7,10 ? *');
	});

	it('produces a monthly cron with several days of the month', () => {
		expect(
			toCronExpression(
				buildScheduleValues({
					monthDays: [1, 15],
					months: [],
					unit: IntervalUnit.Month,
				})
			)
		).toBe('0 30 15 1,15 * ? *');
	});

	it('produces a monthly cron with the selected ordinal weekday', () => {
		expect(
			toCronExpression(
				buildScheduleValues({
					repeatType: RepeatType.DayOfWeek,
					unit: IntervalUnit.Month,
					weekday: 5,
					weekdayOrdinal: '4',
				})
			)
		).toBe('0 30 15 ? * THU#4 *');
	});

	it('produces a monthly cron with the last weekday of the month', () => {
		expect(
			toCronExpression(
				buildScheduleValues({
					repeatType: RepeatType.DayOfWeek,
					unit: IntervalUnit.Month,
					weekday: 6,
					weekdayOrdinal: LAST_WEEKDAY_ORDINAL,
				})
			)
		).toBe('0 30 15 ? * FRIL *');
	});

	it('produces a yearly cron with the selected month and day', () => {
		expect(
			toCronExpression(
				buildScheduleValues({
					monthDays: [4],
					months: [7],
					unit: IntervalUnit.Year,
				})
			)
		).toBe('0 30 15 4 7 ? 2026/1');
	});

	it('produces a yearly cron with the selected ordinal weekday', () => {
		expect(
			toCronExpression(
				buildScheduleValues({
					months: [7],
					repeatType: RepeatType.DayOfWeek,
					unit: IntervalUnit.Year,
					weekday: 2,
					weekdayOrdinal: '1',
				})
			)
		).toBe('0 30 15 ? 7 MON#1 2026/1');
	});

	it('keeps the picked wall-clock time regardless of the time zone', () => {
		const scheduleValues = buildScheduleValues({
			unit: IntervalUnit.Never,
		});

		expect(
			toCronExpression({...scheduleValues, timeZoneId: 'Asia/Tokyo'})
		).toBe(
			toCronExpression({
				...scheduleValues,
				timeZoneId: 'America/New_York',
			})
		);
	});
});

describe('fromCronExpression', () => {
	it('parses a one-time cron', () => {
		expect(decode('0 30 15 20 7 ? 2026')).toEqual({
			unit: IntervalUnit.Never,
		});
	});

	it('parses a daily cron', () => {
		expect(decode('0 30 15 * * ? *')).toEqual({
			monthDays: [],
			months: [],
			unit: IntervalUnit.Day,
		});
	});

	it('parses a weekly cron with the selected days', () => {
		expect(decode('0 30 15 ? * MON,WED *')).toEqual({
			unit: IntervalUnit.Week,
			weekDays: [2, 4],
		});
	});

	it('expands the day step of a legacy weekly cron', () => {
		expect(decode('0 30 15 ? * MON,WED/2 *')).toEqual({
			unit: IntervalUnit.Week,
			weekDays: [2, 4, 6],
		});

		expect(decode('0 30 15 ? * MON/1 *')).toEqual({
			unit: IntervalUnit.Week,
			weekDays: [2, 3, 4, 5, 6, 7],
		});
	});

	it('expands ranges and names', () => {
		expect(decode('0 30 15 ? * MON-FRI *')).toEqual({
			unit: IntervalUnit.Week,
			weekDays: [2, 3, 4, 5, 6],
		});

		expect(decode('0 30 15 1-3 JAN,APR ? *')).toEqual({
			monthDays: [1, 2, 3],
			months: [1, 4],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Month,
		});
	});

	it('parses a monthly cron with the selected months and days', () => {
		expect(decode('0 30 15 15 * ? *')).toEqual({
			monthDays: [15],
			months: [],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Month,
		});

		expect(decode('0 30 15 1,15 1,4,7,10 ? *')).toEqual({
			monthDays: [1, 15],
			months: [1, 4, 7, 10],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Month,
		});
	});

	it('expands the month step of a legacy monthly cron', () => {
		expect(decode('0 30 15 15 1/3 ? *')).toEqual({
			monthDays: [15],
			months: [1, 4, 7, 10],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Month,
		});

		expect(decode('0 30 15 15 1/5 ? *')).toEqual({
			monthDays: [15],
			months: [1, 6, 11],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Month,
		});

		expect(decode('0 30 15 15 1/12 ? *')).toEqual({
			monthDays: [15],
			months: [1],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Month,
		});
	});

	it('expands the day step of a legacy daily cron', () => {
		expect(decode('0 30 15 1/10 * ? *')).toEqual({
			monthDays: [1, 11, 21, 31],
			months: [],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Month,
		});
	});

	it('parses a monthly cron with the selected ordinal weekday', () => {
		expect(decode('0 30 15 ? * THU#4 *')).toEqual({
			months: [],
			repeatType: RepeatType.DayOfWeek,
			unit: IntervalUnit.Month,
			weekday: 5,
			weekdayOrdinal: '4',
			yearInterval: 1,
		});

		expect(decode('0 30 15 ? 2,5,8,11 FRIL *')).toEqual({
			months: [2, 5, 8, 11],
			repeatType: RepeatType.DayOfWeek,
			unit: IntervalUnit.Month,
			weekday: 6,
			weekdayOrdinal: LAST_WEEKDAY_ORDINAL,
			yearInterval: 1,
		});
	});

	it('parses a yearly cron with the selected month and day', () => {
		expect(decode('0 30 15 4 7 ? 2026/1')).toEqual({
			monthDays: [4],
			months: [7],
			repeatType: RepeatType.DayOfMonth,
			unit: IntervalUnit.Year,
			yearInterval: 1,
		});
	});

	it('parses a yearly cron with the selected ordinal weekday', () => {
		expect(decode('0 30 15 ? 7 MON#1 2026/1')).toEqual({
			months: [7],
			repeatType: RepeatType.DayOfWeek,
			unit: IntervalUnit.Year,
			weekday: 2,
			weekdayOrdinal: '1',
			yearInterval: 1,
		});
	});

	it('keeps an expression the form cannot represent as a custom one', () => {
		[
			'not a cron',
			'0 30 15 L * ? *',
			'0 30 15 15W * ? *',
			'45 30 15 15 * ? *',
			'0 45 09 15 * ? *',
			'0 30 15 15 * ? 2026-2030',
		].forEach((cronExpression) => {
			expect(decode(cronExpression)).toEqual({
				cronExpression,
				unit: IntervalUnit.Custom,
			});
		});
	});
});

describe('custom cron expressions', () => {
	it('round trips any expression the form cannot represent', () => {
		[
			'0 30 15 L * ? *',
			'0 30 15 LW * ? *',
			'0 30 15 ? * 6L 2026-2030',
			'45 30 15 15 * ? *',
		].forEach((cronExpression) => {
			expect(
				toCronExpression(buildScheduleValues(decode(cronExpression)!))
			).toBe(cronExpression);
		});
	});
});

describe('cron round trip', () => {
	it('re-encodes every legacy expression to an equivalent cron', () => {
		const cronExpressions = [
			'0 30 15 * * ? *',
			'0 30 15 ? * MON,WED *',
			'0 30 15 15 * ? *',
			'0 30 15 1,15 1,4,7,10 ? *',
			'0 30 15 ? * THU#4 *',
			'0 30 15 4 7 ? 2026/1',
			'0 30 15 ? 7 MON#1 2026/1',
		];

		cronExpressions.forEach((cronExpression) => {
			expect(
				toCronExpression(
					buildScheduleValues(
						fromCronExpression(cronExpression, '2026-07-20 15:30')!
					)
				)
			).toBe(cronExpression);
		});
	});
});

describe('toWallClockDateTime', () => {
	it('formats the instant as a wall-clock date time in the time zone', () => {
		expect(
			toWallClockDateTime('2026-07-20T19:30:00.000Z', 'America/New_York')
		).toBe('2026-07-20 15:30');

		expect(toWallClockDateTime('2026-07-20T19:30:00.000Z', 'UTC')).toBe(
			'2026-07-20 19:30'
		);
	});
});

describe('toZonedDate', () => {
	it('interprets the wall-clock time in the given time zone', () => {
		expect(
			toZonedDate('2026-07-20 15:30', 'America/New_York').toISOString()
		).toBe('2026-07-20T19:30:00.000Z');

		expect(
			toZonedDate('2026-07-20 15:30', 'Asia/Tokyo').toISOString()
		).toBe('2026-07-20T06:30:00.000Z');

		expect(toZonedDate('2026-07-20 15:30', 'UTC').toISOString()).toBe(
			'2026-07-20T15:30:00.000Z'
		);
	});
});
