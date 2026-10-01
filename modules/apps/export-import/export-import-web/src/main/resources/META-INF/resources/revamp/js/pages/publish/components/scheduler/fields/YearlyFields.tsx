/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import FieldSelectWithOption from '../../../../../components/forms/FieldSelectWithOption';
import {
	IntervalUnit,
	MONTH_DAYS,
	RepeatType,
	ScheduleValues,
	YEAR_INTERVALS,
} from '../types';
import {
	MONTHS,
	MONTH_MAX_DAYS,
	getIntervalText,
	getSelectedMonthDays,
	getSelectedMonths,
} from '../utils';
import WeekdayOrdinalFields from './WeekdayOrdinalFields';

export default function YearlyFields({
	onChange,
	value,
}: {
	onChange: (scheduleValues: Partial<ScheduleValues>) => void;
	value: ScheduleValues;
}) {
	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	const selectedMonthDay = getSelectedMonthDays(value)[0];
	const selectedMonth = getSelectedMonths(value)[0];

	const monthDayOptions = MONTH_DAYS.slice(
		0,
		MONTH_MAX_DAYS[selectedMonth - 1]
	).map((monthDay) => ({
		label: sub(Liferay.Language.get('day-x'), String(monthDay)),
		value: monthDay,
	}));

	const yearIntervalOptions = YEAR_INTERVALS.map((yearInterval) => ({
		label: getIntervalText(yearInterval, IntervalUnit.Year, locale),
		value: yearInterval,
	}));

	return (
		<>
			{value.repeatType === RepeatType.DayOfWeek ? (
				<ClayLayout.Row>
					<WeekdayOrdinalFields onChange={onChange} value={value} />

					<ClayLayout.Col md={6} size={12}>
						<FieldSelectWithOption
							label={Liferay.Language.get('repeat-on-month')}
							name="publishScheduleRepeatOnMonth"
							onChange={(event) =>
								onChange({months: [Number(event.target.value)]})
							}
							options={MONTHS}
							value={String(selectedMonth)}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			) : (
				<ClayLayout.Row>
					<ClayLayout.Col md={6} size={12}>
						<FieldSelectWithOption
							label={Liferay.Language.get('repeat-on-day')}
							name="publishScheduleRepeatOnDay"
							onChange={(event) =>
								onChange({
									monthDays: [Number(event.target.value)],
								})
							}
							options={monthDayOptions}
							value={String(selectedMonthDay)}
						/>
					</ClayLayout.Col>

					<ClayLayout.Col md={6} size={12}>
						<FieldSelectWithOption
							label={Liferay.Language.get('repeat-on-month')}
							name="publishScheduleRepeatOnMonth"
							onChange={(event) => {
								const month = Number(event.target.value);

								onChange({
									monthDays: [
										Math.min(
											selectedMonthDay,
											MONTH_MAX_DAYS[month - 1]
										),
									],
									months: [month],
								});
							}}
							options={MONTHS}
							value={String(selectedMonth)}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			)}

			<ClayLayout.Row>
				<ClayLayout.Col md={6} size={12}>
					<FieldSelectWithOption
						label={Liferay.Language.get('repeat-every')}
						name="publishScheduleRepeatEvery"
						onChange={(event) =>
							onChange({yearInterval: Number(event.target.value)})
						}
						options={yearIntervalOptions}
						value={String(value.yearInterval)}
					/>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</>
	);
}
