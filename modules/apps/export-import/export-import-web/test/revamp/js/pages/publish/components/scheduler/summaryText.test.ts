/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getScheduleSummary} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/summary';
import {
	IntervalUnit,
	RepeatType,
	ScheduleValues,
	getInitialScheduleValues,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/types';

const LANGUAGE_KEYS: Record<string, string> = {
	'day': 'Day',
	'day-x': 'Day {0}',
	'days-x': 'Days {0}',
	'month': 'Month',
	'repeats-every-x': 'Repeats every {0}.',
	'repeats-every-x-in-x-on-the-x': 'Repeats every {0} in {1} on the {2}.',
	'repeats-every-x-in-x-on-x': 'Repeats every {0} in {1} on {2}.',
	'repeats-every-x-on-the-x': 'Repeats every {0} on the {1}.',
	'repeats-every-x-on-x': 'Repeats every {0} on {1}.',
	'repeats-in-x': 'Repeats in {0}.',
	'repeats-in-x-on-the-x': 'Repeats in {0} on the {1}.',
	'repeats-in-x-on-x': 'Repeats in {0} on {1}.',
	'starts-x-at-x-and-never-ends': 'Starts {0} at {1} and never ends.',
	'third': 'Third',
	'week': 'Week',
	'year': 'Year',
};

function buildScheduleValues(
	partialScheduleValues: Partial<ScheduleValues>
): ScheduleValues {
	return {
		...getInitialScheduleValues('UTC'),
		enabled: true,
		startDateTime: '2026-08-11 12:45',
		...partialScheduleValues,
	};
}

function getRepeatSentence(
	partialScheduleValues: Partial<ScheduleValues>
): string {
	const summary = getScheduleSummary(
		buildScheduleValues(partialScheduleValues)
	) as string;

	return summary.slice(0, summary.indexOf(' Starts'));
}

const getLanguageKey = Liferay.Language.get as jest.Mock;

describe('schedule summary wording', () => {
	beforeEach(() => {
		getLanguageKey.mockImplementation(
			(key: string) => LANGUAGE_KEYS[key] ?? key
		);
	});

	afterEach(() => {
		getLanguageKey.mockImplementation((key: string) => key);
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
			'Repeats in january, april, august, and december on the third Thursday.'
		);
	});

	it('says every month rather than listing every month', () => {
		expect(
			getRepeatSentence({
				monthDays: [],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe('Repeats every Month.');
	});

	it('collapses consecutive days into ranges', () => {
		expect(
			getRepeatSentence({
				monthDays: [1, 2, 3, 4, 5, 20],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe('Repeats every Month on Days 1-5 and 20.');
	});

	it('lists scattered days without repeating the word day', () => {
		expect(
			getRepeatSentence({
				monthDays: [1, 3, 5],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe('Repeats every Month on Days 1, 3, and 5.');
	});

	it('uses the singular day for a single day of the month', () => {
		expect(
			getRepeatSentence({
				monthDays: [15],
				months: [],
				unit: IntervalUnit.Month,
			})
		).toBe('Repeats every Month on Day 15.');
	});

	it('describes a yearly repetition', () => {
		expect(
			getRepeatSentence({
				monthDays: [4],
				months: [7],
				unit: IntervalUnit.Year,
			})
		).toBe('Repeats every Year in july on Day 4.');
	});
});
