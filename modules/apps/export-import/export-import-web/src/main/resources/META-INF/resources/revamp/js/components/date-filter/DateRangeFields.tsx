/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import FieldDatePicker from '../forms/FieldDatePicker';
import {DATE_FORMAT, FilterAction, FilterState, YEARS_OFFSET} from './types';
import {getValidation} from './utils';

type Props = {
	dispatch: React.Dispatch<FilterAction>;
	editing: FilterState['editing'];
	errors: ReturnType<typeof getValidation>['errors'];
	touchedFields: FilterState['touchedFields'];
};

const DateRangeFields = ({dispatch, editing, errors, touchedFields}: Props) => {
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
					onBlur={() =>
						dispatch({
							payload: {fromDate: true},
							type: 'UPDATE_TOUCHED',
						})
					}
					onChange={(value) =>
						dispatch({
							payload: {fromDate: value as string},
							type: 'UPDATE_FILTER',
						})
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
					onBlur={() =>
						dispatch({
							payload: {toDate: true},
							type: 'UPDATE_TOUCHED',
						})
					}
					onChange={(value) =>
						dispatch({
							payload: {toDate: value as string},
							type: 'UPDATE_FILTER',
						})
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
