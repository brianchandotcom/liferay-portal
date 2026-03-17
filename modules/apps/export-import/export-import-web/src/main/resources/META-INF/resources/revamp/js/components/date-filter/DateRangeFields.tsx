/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import FieldDatePicker from '../forms/FieldDatePicker';
import {DATE_FORMAT, FilterState, YEARS_OFFSET} from './types';
import {getValidation} from './utils';

type Props = {
	editing: FilterState['editing'];
	errors: ReturnType<typeof getValidation>['errors'];
	handleUpdateFilter: (payload: Partial<FilterState['editing']>) => void;
	handleUpdateTouched: (
		payload: Partial<FilterState['touchedFields']>
	) => void;
	touchedFields: FilterState['touchedFields'];
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
						touchedFields.fromDate ? errors.fromDate : undefined
					}
					id="fromDate"
					label={Liferay.Language.get('from')}
					name="fromDate"
					onBlur={() => handleUpdateTouched({fromDate: true})}
					onChange={(value) =>
						handleUpdateFilter({fromDate: value as string})
					}
					placeholder={`${DATE_FORMAT} HH:MM`.toUpperCase()}
					time
					value={editing.fromDate}
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
						touchedFields.toDate ? errors.toDate : undefined
					}
					id="toDate"
					label={Liferay.Language.get('to')}
					name="toDate"
					onBlur={() => handleUpdateTouched({toDate: true})}
					onChange={(value) =>
						handleUpdateFilter({toDate: value as string})
					}
					placeholder={`${DATE_FORMAT} HH:MM`.toUpperCase()}
					time
					value={editing.toDate}
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
