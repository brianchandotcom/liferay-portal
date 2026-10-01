/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {MONTH_DAYS, RepeatType, ScheduleValues} from '../types';
import {
	MONTHS,
	MONTH_VALUES,
	getSelectedMonthDays,
	getSelectedMonths,
} from '../utils';
import DatePartGrid from './DatePartGrid';
import WeekdayOrdinalFields from './WeekdayOrdinalFields';

export default function MonthlyFields({
	onChange,
	value,
}: {
	onChange: (scheduleValues: Partial<ScheduleValues>) => void;
	value: ScheduleValues;
}) {
	return (
		<>
			<ClayLayout.Row>
				<ClayLayout.Col size={12}>
					<ClayForm.Group>
						<label>{Liferay.Language.get('repeat-on-month')}</label>

						<DatePartGrid
							className="month-grid"
							getLabel={(month) => MONTHS[month - 1].label}
							items={MONTH_VALUES}
							onToggle={(months) => onChange({months})}
							selected={getSelectedMonths(value)}
						/>
					</ClayForm.Group>
				</ClayLayout.Col>
			</ClayLayout.Row>

			{value.repeatType === RepeatType.DayOfWeek ? (
				<ClayLayout.Row>
					<WeekdayOrdinalFields onChange={onChange} value={value} />
				</ClayLayout.Row>
			) : (
				<ClayLayout.Row>
					<ClayLayout.Col size={12}>
						<ClayForm.Group>
							<label>{Liferay.Language.get('repeat-on')}</label>

							<DatePartGrid
								getLabel={String}
								items={MONTH_DAYS}
								onToggle={(monthDays) => onChange({monthDays})}
								selected={getSelectedMonthDays(value)}
							/>
						</ClayForm.Group>
					</ClayLayout.Col>
				</ClayLayout.Row>
			)}
		</>
	);
}
