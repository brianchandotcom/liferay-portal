/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import FieldTimePicker from '../../../../../components/forms/FieldTimePicker';
import {isCompleteDateTime} from '../../../../../utils/dateTime';
import {ScheduleValues} from '../types';

export default function RepeatAtFields({
	errorMessage,
	onBlur,
	onChange,
	value,
}: {
	errorMessage?: string;
	onBlur?: () => void;
	onChange: (scheduleValues: Partial<ScheduleValues>) => void;
	value: ScheduleValues;
}) {
	const startTime = isCompleteDateTime(value.startDateTime)
		? value.startDateTime.split(' ')[1]
		: '';

	return (
		<>
			<ClayLayout.Row>
				<ClayLayout.Col md={6} size={12}>
					<FieldTimePicker
						disabled={value.repeatOnTimeSynced}
						errorMessage={errorMessage}
						id="publishScheduleRepeatOnTime"
						label={Liferay.Language.get('repeat-at')}
						name="publishScheduleRepeatOnTime"
						onBlur={onBlur}
						onChange={(repeatOnTime) => onChange({repeatOnTime})}
						required={!value.repeatOnTimeSynced}
						value={
							value.repeatOnTimeSynced
								? startTime
								: value.repeatOnTime
						}
					/>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<ClayCheckbox
				checked={value.repeatOnTimeSynced}
				label={Liferay.Language.get('sync-with-start-date-time')}
				onChange={() =>
					onChange(
						value.repeatOnTimeSynced
							? {
									repeatOnTime: startTime,
									repeatOnTimeSynced: false,
								}
							: {repeatOnTimeSynced: true}
					)
				}
			/>
		</>
	);
}
