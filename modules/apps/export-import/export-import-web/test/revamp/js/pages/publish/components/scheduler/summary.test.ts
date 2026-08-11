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

function buildScheduleValues(
	partialScheduleValues: Partial<ScheduleValues>
): ScheduleValues {
	return {
		...getInitialScheduleValues('UTC'),
		enabled: true,
		startDateTime: '2026-08-22 09:30',
		...partialScheduleValues,
	};
}

describe('getScheduleSummary', () => {
	it('returns null while the schedule is disabled or incomplete', () => {
		expect(
			getScheduleSummary(buildScheduleValues({enabled: false}))
		).toBeNull();

		expect(
			getScheduleSummary(buildScheduleValues({startDateTime: ''}))
		).toBeNull();
	});

	it('describes a one-time publication', () => {
		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Never}))
		).toBe('publishes-once-on-x-at-x-and-does-not-repeat');
	});

	it('describes a repetition without an end date', () => {
		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Day}))
		).toBe('repeats-every-x starts-x-at-x-and-never-ends');
	});

	it('describes a repetition with an end date', () => {
		expect(
			getScheduleSummary(
				buildScheduleValues({
					endDateTime: '2026-10-20 09:30',
					neverEnd: false,
					unit: IntervalUnit.Day,
				})
			)
		).toBe('repeats-every-x starts-x-at-x-and-ends-x-at-x');
	});

	it('ignores the end date while never end is checked', () => {
		expect(
			getScheduleSummary(
				buildScheduleValues({
					endDateTime: '2026-10-20 09:30',
					neverEnd: true,
					unit: IntervalUnit.Day,
				})
			)
		).toBe('repeats-every-x starts-x-at-x-and-never-ends');
	});

	it('describes the repetition target per unit', () => {
		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Week}))
		).toBe('repeats-every-x-on-x starts-x-at-x-and-never-ends');

		expect(
			getScheduleSummary(buildScheduleValues({unit: IntervalUnit.Month}))
		).toBe('repeats-every-x-on-x starts-x-at-x-and-never-ends');

		expect(
			getScheduleSummary(
				buildScheduleValues({
					repeatType: RepeatType.DayOfWeek,
					unit: IntervalUnit.Month,
				})
			)
		).toBe('repeats-every-x-on-the-x starts-x-at-x-and-never-ends');
	});
});
