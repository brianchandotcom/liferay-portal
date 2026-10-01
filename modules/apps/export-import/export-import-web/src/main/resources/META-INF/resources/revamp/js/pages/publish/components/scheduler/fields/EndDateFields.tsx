/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import FieldDatePicker from '../../../../../components/forms/FieldDatePicker';
import {ScheduleValues} from '../types';

export default function EndDateFields({
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
	const currentYear = new Date().getFullYear();

	return (
		<>
			<ClayLayout.Row>
				<ClayLayout.Col md={6} size={12}>
					<FieldDatePicker
						defaultTime="23:59"
						disabled={value.neverEnd}
						errorMessage={errorMessage}
						id="publishScheduleEndDateTime"
						label={Liferay.Language.get('end-date')}
						name="publishScheduleEndDateTime"
						onBlur={onBlur}
						onChange={(endDateTime) =>
							onChange({endDateTime: endDateTime as string})
						}
						required={!value.neverEnd}
						time
						value={value.endDateTime}
						years={{end: currentYear + 10, start: currentYear}}
					/>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<ClayCheckbox
				checked={value.neverEnd}
				label={Liferay.Language.get('never-end')}
				onChange={() => onChange({neverEnd: !value.neverEnd})}
			/>
		</>
	);
}
