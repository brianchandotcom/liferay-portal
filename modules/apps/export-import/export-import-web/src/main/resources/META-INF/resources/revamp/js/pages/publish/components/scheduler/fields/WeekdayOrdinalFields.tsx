/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import FieldSelectWithOption from '../../../../../components/forms/FieldSelectWithOption';
import {ScheduleValues, WEEKDAYS} from '../types';
import {WEEKDAY_ORDINAL_OPTIONS, getWeekdayName} from '../utils';

export default function WeekdayOrdinalFields({
	onChange,
	value,
}: {
	onChange: (scheduleValues: Partial<ScheduleValues>) => void;
	value: ScheduleValues;
}) {
	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	const weekdayOptions = WEEKDAYS.map((weekday) => ({
		label: getWeekdayName(weekday, locale),
		value: weekday,
	}));

	return (
		<>
			<ClayLayout.Col md={6} size={12}>
				<FieldSelectWithOption
					label={Liferay.Language.get('repeat-on')}
					name="publishScheduleWeekdayOrdinal"
					onChange={(event) =>
						onChange({weekdayOrdinal: event.target.value})
					}
					options={WEEKDAY_ORDINAL_OPTIONS}
					value={value.weekdayOrdinal}
				/>
			</ClayLayout.Col>

			<ClayLayout.Col md={6} size={12}>
				<FieldSelectWithOption
					label={Liferay.Language.get('weekday')}
					name="publishScheduleWeekday"
					onChange={(event) =>
						onChange({weekday: Number(event.target.value)})
					}
					options={weekdayOptions}
					value={String(value.weekday)}
				/>
			</ClayLayout.Col>
		</>
	);
}
