/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	IntervalUnit,
	ScheduleValues,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/types';
import {
	getInitialScheduleValues,
	getScheduleValuesErrors,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/utils';

const EARLIER_END_DATE_TIME = '2099-06-14 08:00';

const START_DATE_TIME = '2099-06-15 08:00';

function buildScheduleValues(
	partialScheduleValues: Partial<ScheduleValues>
): ScheduleValues {
	return {
		...getInitialScheduleValues('UTC'),
		enabled: true,
		endDateTime: EARLIER_END_DATE_TIME,
		neverEnd: false,
		startDateTime: START_DATE_TIME,
		...partialScheduleValues,
	};
}

describe('getScheduleValuesErrors', () => {
	it('reports an end date that does not follow the start date', () => {
		expect(
			getScheduleValuesErrors(
				buildScheduleValues({unit: IntervalUnit.Day})
			).endDateTime
		).toBe('the-end-date-cannot-be-earlier-than-the-start-date');
	});

	it('reports an end date that is partially typed', () => {
		expect(
			getScheduleValuesErrors(
				buildScheduleValues({
					endDateTime: '2099-06',
					unit: IntervalUnit.Day,
				})
			).endDateTime
		).toBe('please-enter-a-valid-date');
	});

	it('leaves the end date unchecked while never end is checked', () => {
		expect(
			getScheduleValuesErrors(
				buildScheduleValues({neverEnd: true, unit: IntervalUnit.Day})
			).endDateTime
		).toBeUndefined();
	});

	it('leaves the end date of a one time schedule unchecked', () => {
		expect(
			getScheduleValuesErrors(
				buildScheduleValues({unit: IntervalUnit.Never})
			)
		).toEqual({});
	});

	it('leaves the repeat at time unchecked while it is synced', () => {
		expect(
			getScheduleValuesErrors(
				buildScheduleValues({
					neverEnd: true,
					repeatOnTime: '',
					repeatOnTimeSynced: true,
					unit: IntervalUnit.Day,
				})
			)
		).toEqual({});
	});

	it('reports a repeat at time that is partially typed', () => {
		expect(
			getScheduleValuesErrors(
				buildScheduleValues({
					repeatOnTime: '10:',
					repeatOnTimeSynced: false,
					unit: IntervalUnit.Day,
				})
			).repeatOnTime
		).toBe('please-enter-a-valid-time');
	});

	it('requires the repeat at time once the sync is unchecked', () => {
		expect(
			getScheduleValuesErrors(
				buildScheduleValues({
					repeatOnTime: '',
					repeatOnTimeSynced: false,
					unit: IntervalUnit.Week,
				})
			).repeatOnTime
		).toBe('this-field-is-required');
	});
});
