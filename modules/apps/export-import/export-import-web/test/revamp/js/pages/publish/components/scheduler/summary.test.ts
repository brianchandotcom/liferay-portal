/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toWallClockDateTime} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/cron';
import {getScheduleSummary} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/summary';
import {
	IntervalUnit,
	RepeatType,
	ScheduleValues,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/types';
import {getInitialScheduleValues} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/utils';

const DAY = 24 * 60 * 60 * 1000;

const START_DATE_TIME = toWallClockDateTime(
	new Date(Date.now() + DAY).toISOString(),
	'UTC'
);

const END_DATE_TIME = toWallClockDateTime(
	new Date(Date.now() + 2 * DAY).toISOString(),
	'UTC'
);

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

describe('getScheduleSummary', () => {
	it('returns null while the schedule is disabled or has no start date', () => {
		expect(
			getScheduleSummary(buildScheduleValues({enabled: false}))
		).toBeNull();

		expect(
			getScheduleSummary(buildScheduleValues({startDateTime: ''}))
		).toBeNull();
	});

	it('returns null while the start date is partially typed', () => {
		expect(
			getScheduleSummary(buildScheduleValues({startDateTime: '2026-08'}))
		).toBeNull();
	});

	it('returns null while the start date is in the past', () => {
		expect(
			getScheduleSummary(
				buildScheduleValues({startDateTime: '2020-01-01 09:30'})
			)
		).toBeNull();
	});

	it('describes a one time publication', () => {
		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Never}))
		).toBe('the-process-runs-once-on-x-at-x-and-does-not-repeat');
	});

	it('describes a repetition without an end date', () => {
		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Day}))
		).toBe(
			'the-process-repeats-every-x-at-x the-process-starts-on-x-at-x-and-never-ends'
		);
	});

	it('describes a repetition with an end date', () => {
		expect(
			getScheduleSummary(
				buildScheduleValues({
					endDateTime: END_DATE_TIME,
					neverEnd: false,
					unit: IntervalUnit.Day,
				})
			)
		).toBe(
			'the-process-repeats-every-x-at-x the-process-starts-on-x-at-x-and-ends-on-x-at-x'
		);
	});

	it('ignores the end date while never end is checked', () => {
		expect(
			getScheduleSummary(
				buildScheduleValues({
					endDateTime: END_DATE_TIME,
					neverEnd: true,
					unit: IntervalUnit.Day,
				})
			)
		).toBe(
			'the-process-repeats-every-x-at-x the-process-starts-on-x-at-x-and-never-ends'
		);
	});

	it('describes the repetition target per unit', () => {
		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Week}))
		).toBe(
			'the-process-repeats-every-x-on-x-at-x the-process-starts-on-x-at-x-and-never-ends'
		);

		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Month}))
		).toBe(
			'the-process-repeats-every-x-on-x-at-x the-process-starts-on-x-at-x-and-never-ends'
		);

		expect(
			getScheduleSummary(
				buildScheduleValues({
					repeatType: RepeatType.DayOfWeek,
					unit: IntervalUnit.Month,
				})
			)
		).toBe(
			'the-process-repeats-every-x-on-the-x-at-x the-process-starts-on-x-at-x-and-never-ends'
		);
	});
});

describe('schedule summary wording', () => {
	const LANGUAGE_KEYS: Record<string, string> = {
		'day-x': 'Day {0}',
		'days-x': 'Days {0}',
		'month': 'Month',
		'the-process-repeats-every-x-at-x':
			'The process repeats every {0} at {1}.',
		'the-process-repeats-every-x-in-x-on-the-x-at-x':
			'The process repeats every {0} in {1} on the {2} at {3}.',
		'the-process-repeats-every-x-in-x-on-x-at-x':
			'The process repeats every {0} in {1} on {2} at {3}.',
		'the-process-repeats-every-x-on-the-x-at-x':
			'The process repeats every {0} on the {1} at {2}.',
		'the-process-repeats-every-x-on-x-at-x':
			'The process repeats every {0} on {1} at {2}.',
		'the-process-repeats-in-x-at-x': 'The process repeats in {0} at {1}.',
		'the-process-repeats-in-x-on-the-x-at-x':
			'The process repeats in {0} on the {1} at {2}.',
		'the-process-repeats-in-x-on-x-at-x':
			'The process repeats in {0} on {1} at {2}.',
		'the-process-starts-on-x-at-x-and-never-ends':
			'The process starts on {0} at {1} and never ends.',
		'year': 'Year',
	};

	const START_TIME_TEXT = new Date(
		START_DATE_TIME.replace(' ', 'T')
	).toLocaleTimeString(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		hour: 'numeric',
		minute: '2-digit',
	});

	function getStartsSentence(
		partialScheduleValues: Partial<ScheduleValues>
	): string {
		const summary = getScheduleSummary(
			buildScheduleValues(partialScheduleValues)
		) as string;

		return summary.slice(summary.indexOf('The process starts'));
	}

	function getRepeatSentence(
		partialScheduleValues: Partial<ScheduleValues>
	): string {
		const summary = getScheduleSummary(
			buildScheduleValues(partialScheduleValues)
		) as string;

		return summary.slice(0, summary.indexOf(' The process starts'));
	}

	const getLanguageKey = Liferay.Language.get as jest.Mock;

	const defaultLanguageKeyImplementation =
		getLanguageKey.getMockImplementation();

	beforeEach(() => {
		getLanguageKey.mockImplementation(
			(key: string) => LANGUAGE_KEYS[key] ?? key
		);
	});

	afterEach(() => {
		getLanguageKey.mockImplementation(defaultLanguageKeyImplementation);
	});

	it('names the weekday in full', () => {
		expect(
			getRepeatSentence({
				months: [1, 4, 8, 12],
				repeatType: RepeatType.DayOfWeek,
				unit: IntervalUnit.Month,
				weekday: 5,
				weekdayOrdinal: '3',
			})
		).toBe(
			`The process repeats in january, april, august, and december on the third Thursday at ${START_TIME_TEXT}.`
		);
	});

	it('says every month rather than listing every month', () => {
		expect(
			getRepeatSentence({
				monthDays: [],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe(`The process repeats every Month at ${START_TIME_TEXT}.`);
	});

	it('lists a two day run as separate days', () => {
		expect(
			getRepeatSentence({
				monthDays: [1, 2, 20],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe(
			`The process repeats every Month on Days 1, 2, and 20 at ${START_TIME_TEXT}.`
		);
	});

	it('collapses consecutive days into ranges', () => {
		expect(
			getRepeatSentence({
				monthDays: [1, 2, 3, 4, 5, 20],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe(
			`The process repeats every Month on Days 1-5 and 20 at ${START_TIME_TEXT}.`
		);
	});

	it('lists scattered days without repeating the word day', () => {
		expect(
			getRepeatSentence({
				monthDays: [1, 3, 5],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe(
			`The process repeats every Month on Days 1, 3, and 5 at ${START_TIME_TEXT}.`
		);
	});

	it('uses the singular day for a single day of the month', () => {
		expect(
			getRepeatSentence({
				monthDays: [15],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe(
			`The process repeats every Month on Day 15 at ${START_TIME_TEXT}.`
		);
	});

	it('describes a yearly repetition', () => {
		expect(
			getRepeatSentence({
				monthDays: [4],
				months: [7],
				unit: IntervalUnit.Year,
			})
		).toBe(
			`The process repeats every Year in july on Day 4 at ${START_TIME_TEXT}.`
		);
	});

	it('shows the start date time in both sentences while the repeat time is synced', () => {
		expect(getRepeatSentence({unit: IntervalUnit.Week})).toBe(
			`The process repeats every week on Monday at ${START_TIME_TEXT}.`
		);

		expect(getStartsSentence({unit: IntervalUnit.Week})).toBe(
			`The process starts on ${new Date(
				START_DATE_TIME.replace(' ', 'T')
			).toLocaleDateString(
				Liferay.ThemeDisplay.getBCP47LanguageId()
			)} at ${START_TIME_TEXT} and never ends.`
		);
	});

	it('shows the independent repeat time in both sentences once the repeat time is unsynced', () => {
		const repeatTimeText = new Date(2026, 0, 1, 0, 0).toLocaleTimeString(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			{
				hour: 'numeric',
				minute: '2-digit',
			}
		);

		const unsyncedScheduleValues = {
			repeatOnTime: '00:00',
			repeatOnTimeSynced: false,
			unit: IntervalUnit.Week,
		};

		expect(getRepeatSentence(unsyncedScheduleValues)).toBe(
			`The process repeats every week on Monday at ${repeatTimeText}.`
		);

		expect(getStartsSentence(unsyncedScheduleValues)).toBe(
			`The process starts on ${new Date(
				START_DATE_TIME.replace(' ', 'T')
			).toLocaleDateString(
				Liferay.ThemeDisplay.getBCP47LanguageId()
			)} at ${repeatTimeText} and never ends.`
		);
	});
});
