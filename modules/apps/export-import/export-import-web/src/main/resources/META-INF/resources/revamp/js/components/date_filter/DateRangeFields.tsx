/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import FieldDatePicker from '../forms/FieldDatePicker';
import {DATE_FORMAT, EditingState, TouchedFields, YEARS_OFFSET} from './types';
import {getValidation} from './utils';

type Props = {
	editing: EditingState;
	errors: ReturnType<typeof getValidation>['errors'];
	handleUpdateFilter: (payload: Partial<EditingState>) => void;
	handleUpdateTouched: (payload: Partial<TouchedFields>) => void;
	touchedFields: TouchedFields;
};

const DateRangeFields = ({
	editing,
	errors,
	handleUpdateFilter,
	handleUpdateTouched,
	touchedFields,
}: Props) => {
	const currentYear = new Date().getFullYear();

	return (
		<>
			<ClayLayout.ContentCol>
				<FieldDatePicker
					dateFormat={DATE_FORMAT}
					errorMessage={
						touchedFields.startDate ? errors.startDate : undefined
					}
					id="startDate"
					label={Liferay.Language.get('from')}
					name="startDate"
					onBlur={() => handleUpdateTouched({startDate: true})}
					onChange={(value) =>
						handleUpdateFilter({startDate: value as string})
					}
					placeholder={`${DATE_FORMAT} HH:MM`.toUpperCase()}
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
					dateFormat={DATE_FORMAT}
					errorMessage={
						touchedFields.endDate ? errors.endDate : undefined
					}
					id="endDate"
					label={Liferay.Language.get('to')}
					name="endDate"
					onBlur={() => handleUpdateTouched({endDate: true})}
					onChange={(value) =>
						handleUpdateFilter({endDate: value as string})
					}
					placeholder={`${DATE_FORMAT} HH:MM`.toUpperCase()}
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
