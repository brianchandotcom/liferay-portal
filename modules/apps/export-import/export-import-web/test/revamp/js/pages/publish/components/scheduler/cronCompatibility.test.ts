/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fromCronExpression,
	toCronExpression,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/cron';
import {
	IntervalUnit,
	LAST_WEEKDAY_ORDINAL,
	RepeatType,
	ScheduleValues,
	getInitialScheduleValues,
} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/types';

const START = '2026-07-20 15:30';

function build(partial: Partial<ScheduleValues>): ScheduleValues {
	return {
		...getInitialScheduleValues('UTC'),
		enabled: true,
		startDateTime: START,
		...partial,
	};
}

// Every state the form's controls can produce.

function everyUIState(): ScheduleValues[] {
	const states: ScheduleValues[] = [
		build({unit: IntervalUnit.Never}),
		build({unit: IntervalUnit.Day}),
	];

	// Weekly: every non-empty subset of weekdays is reachable by toggling.

	[[2], [2, 4], [1, 7], [2, 3, 4, 5, 6], [1, 2, 3, 4, 5, 6, 7]].forEach(
		(weekDays) => states.push(build({unit: IntervalUnit.Week, weekDays}))
	);

	// Monthly, day of month: any month subset x any day subset.

	[[], [1], [1, 4, 7, 10], [2, 4, 6, 8, 10, 12], [1, 3, 6, 9, 12]].forEach(
		(months) =>
			[[1], [1, 15], [1, 3, 5, 7, 9], [31], []].forEach((monthDays) =>
				states.push(
					build({monthDays, months, unit: IntervalUnit.Month})
				)
			)
	);

	// Monthly, ordinal weekday: 4 ordinals + last, x 7 weekdays.

	['1', '2', '3', '4', LAST_WEEKDAY_ORDINAL].forEach((weekdayOrdinal) =>
		[1, 2, 5, 7].forEach((weekday) =>
			states.push(
				build({
					months: [3, 6, 9, 12],
					repeatType: RepeatType.DayOfWeek,
					unit: IntervalUnit.Month,
					weekday,
					weekdayOrdinal,
				})
			)
		)
	);

	// Yearly: single month, day or ordinal weekday, interval 1-10.

	[1, 2, 10].forEach((yearInterval) =>
		[1, 7, 12].forEach((month) => {
			states.push(
				build({
					monthDays: [15],
					months: [month],
					unit: IntervalUnit.Year,
					yearInterval,
				})
			);
			states.push(
				build({
					months: [month],
					repeatType: RepeatType.DayOfWeek,
					unit: IntervalUnit.Year,
					weekday: 5,
					weekdayOrdinal: '2',
					yearInterval,
				})
			);
		})
	);

	return states;
}

describe('cron to UI is total', () => {
	it('round trips every expression, through the form or as a custom one', () => {
		const broken: string[] = [];

		[
			'0 30 15 * * ? *',
			'0 30 15 ? * MON,WED *',
			'0 30 15 ? * MON-FRI *',
			'0 30 15 ? * MON/1 *',
			'0 30 15 15 * ? *',
			'0 30 15 1,15 1,4,7,10 ? *',
			'0 30 15 15 1/3 ? *',
			'0 30 15 15 1/5 ? *',
			'0 30 15 15 1/12 ? *',
			'0 30 15 1/10 * ? *',
			'0 30 15 1-3 JAN,APR ? *',
			'0 30 15 ? * THU#4 *',
			'0 30 15 ? 2,5,8,11 FRIL *',
			'0 30 15 4 7 ? 2026/1',
			'0 30 15 ? 7 MON#1 2026/1',
			'0 30 15 20 7 ? 2026',
			'0 30 15 L * ? *',
			'0 30 15 LW * ? *',
			'0 30 15 15W * ? *',
			'0 30 15 ? * 6L 2026-2030',
			'45 30 15 15 * ? *',
			'0 45 09 15 * ? *',
			'0 30 15 15 * ? 2026-2030',
			'0 30 15 ? * 2,6 *',
			'not a cron',
		].forEach((cronExpression) => {
			const decoded = fromCronExpression(cronExpression, START);

			if (decoded === null) {
				broken.push(`${cronExpression}  ->  null (unrepresentable)`);

				return;
			}

			const reencoded = toCronExpression({
				...build({}),
				...decoded,
			});

			if (decoded.unit === IntervalUnit.Custom) {
				if (reencoded !== cronExpression) {
					broken.push(`${cronExpression}  ->  ${reencoded} (custom)`);
				}

				return;
			}

			const redecoded = fromCronExpression(reencoded, START);

			if (toCronExpression({...build({}), ...redecoded}) !== reencoded) {
				broken.push(`${cronExpression}  ->  ${reencoded} (unstable)`);
			}
		});

		expect(broken).toEqual([]);
	});
});

describe('UI to cron is faithful', () => {
	it('re-reads every reachable form state from the cron it produces', () => {
		const broken: string[] = [];

		everyUIState().forEach((scheduleValues) => {
			const cronExpression = toCronExpression(scheduleValues);

			const decoded = fromCronExpression(cronExpression, START);

			if (decoded?.unit === IntervalUnit.Custom) {
				broken.push(
					`${cronExpression}  <- ${scheduleValues.unit} fell to Custom`
				);

				return;
			}

			const reencoded = toCronExpression({...scheduleValues, ...decoded});

			if (reencoded !== cronExpression) {
				broken.push(`${cronExpression}  ->  ${reencoded}`);
			}
		});

		expect(broken).toEqual([]);
	});
});
