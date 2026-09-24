/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import {toWallClockDateTime} from '../../utils/dateTime';
import FieldDatePicker from '../forms/FieldDatePicker';
import {EditingState, YEARS_OFFSET} from './types';
import {getValidation} from './utils';

type Props = {
	editing: EditingState;
	errors: ReturnType<typeof getValidation>['errors'];
	handleUpdateFilter: (payload: Partial<EditingState>) => void;
	timeZoneId: string;
};

function getEndDefaultTime(date: string, timeZoneId: string): string {
	const [today, time] = toWallClockDateTime(
		new Date().toISOString(),
		timeZoneId
	).split(' ');

	return date === today ? time : '23:59';
}

const DateRangeFields = ({
	editing,
	errors,
	handleUpdateFilter,
	timeZoneId,
}: Props) => {
	const currentYear = new Date().getFullYear();

	return (
		<>
			<ClayLayout.ContentCol>
				<FieldDatePicker
					defaultTime="00:00"
					errorMessage={errors.startDate}
					formGroupProps={{className: 'mb-0'}}
					id="startDate"
					label={Liferay.Language.get('from')}
					name="startDate"
					onChange={(value) =>
						handleUpdateFilter({startDate: value as string})
					}
					time
					value={editing.startDate}
					years={{
						end: currentYear,
						start: currentYear - YEARS_OFFSET,
					}}
				/>
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol>
				<FieldDatePicker
					defaultTime={(date) => getEndDefaultTime(date, timeZoneId)}
					errorMessage={errors.endDate}
					formGroupProps={{className: 'mb-0'}}
					id="endDate"
					label={Liferay.Language.get('to[date-time]')}
					name="endDate"
					onChange={(value) =>
						handleUpdateFilter({endDate: value as string})
					}
					time
					value={editing.endDate}
					years={{
						end: currentYear,
						start: currentYear - YEARS_OFFSET,
					}}
				/>
			</ClayLayout.ContentCol>
		</>
	);
};

export default DateRangeFields;
