/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {ScheduleValues, WEEKDAYS} from '../types';
import {getWeekdayName} from '../utils';
import DatePartGrid from './DatePartGrid';

export default function WeeklyFields({
	onChange,
	value,
}: {
	onChange: (scheduleValues: Partial<ScheduleValues>) => void;
	value: ScheduleValues;
}) {
	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	return (
		<ClayLayout.Row>
			<ClayLayout.Col size={12}>
				<ClayForm.Group>
					<label>{Liferay.Language.get('repeat-on')}</label>

					<DatePartGrid
						getLabel={(weekday) => getWeekdayName(weekday, locale)}
						items={WEEKDAYS}
						onToggle={(weekdays) => onChange({weekdays})}
						selected={value.weekdays}
					/>
				</ClayForm.Group>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
}
