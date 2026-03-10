/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import React, {useState} from 'react';

import FieldDatePicker from './forms/FieldDatePicker';
import FieldSelectWithOption from './forms/FieldSelectWithOption';

const FILTER_OPTIONS = [
	{
		label: Liferay.Language.get('show-all'),
		value: 'all',
	},
	{
		label: Liferay.Language.get('date-range'),
		value: 'range',
	},
	{
		label: Liferay.Language.get('modified-last'),
		value: 'last',
	},
];

const MODIFIED_LAST_OPTIONS = [
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-hours'), '12'),
		value: '12h',
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-hours'), '24'),
		value: '24h',
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-hours'), '48'),
		value: '48h',
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-days'), '7'),
		value: '7d',
	},
];

const DATE_FORMAT = 'yyyy-MM-dd';

export default function DateFilter() {
	const [filterType, setFilterType] = useState('all');
	const [fromDate, setFromDate] = useState('');
	const [modifiedLast, setModifiedLast] = useState('12h');
	const [toDate, setToDate] = useState('');

	const handleShowResults = () => {

		// eslint-disable-next-line no-console
		console.log('Filtering by:', {
			filterType,
			fromDate,
			modifiedLast,
			toDate,
		});
	};

	return (
		<ClayLayout.ContentRow className="flex-column flex-lg-row" padded>
			<ClayLayout.ContentCol>
				<FieldSelectWithOption
					id="filterContentBy"
					label={Liferay.Language.get('filter-content-by')}
					name="filterContentBy"
					onChange={(event) => setFilterType(event.target.value)}
					options={FILTER_OPTIONS}
					value={filterType}
				/>
			</ClayLayout.ContentCol>

			{filterType === 'last' && (
				<ClayLayout.ContentCol>
					<FieldSelectWithOption
						id="modifiedLast"
						label={Liferay.Language.get('modified-last')}
						name="modifiedLast"
						onChange={(event) =>
							setModifiedLast(event.target.value)
						}
						options={MODIFIED_LAST_OPTIONS}
						value={modifiedLast}
					/>
				</ClayLayout.ContentCol>
			)}

			{filterType === 'range' && (
				<>
					<ClayLayout.ContentCol>
						<FieldDatePicker
							dateFormat={DATE_FORMAT}
							id="fromDate"
							label={Liferay.Language.get('from')}
							name="fromDate"
							onChange={(value) => setFromDate(value)}
							placeholder={`${DATE_FORMAT} HH:MM`.toUpperCase()}
							time
							value={fromDate}
							years={{
								end: new Date().getFullYear(),
								start: new Date().getFullYear() - 10,
							}}
						/>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol>
						<FieldDatePicker
							dateFormat={DATE_FORMAT}
							id="toDate"
							label={Liferay.Language.get('to')}
							name="toDate"
							onChange={(value) => setToDate(value)}
							placeholder={`${DATE_FORMAT} HH:MM`.toUpperCase()}
							time
							value={toDate}
							years={{
								end: new Date().getFullYear(),
								start: new Date().getFullYear() - 10,
							}}
						/>
					</ClayLayout.ContentCol>
				</>
			)}

			<ClayLayout.ContentCol
				className="align-items-end d-flex justify-content-center"
				expand
			>
				<ClayButton
					displayType="secondary"
					onClick={handleShowResults}
					size="sm"
				>
					{Liferay.Language.get('show-results')}
				</ClayButton>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
}
