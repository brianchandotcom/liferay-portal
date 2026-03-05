/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {dateUtils} from 'frontend-js-web';
import React, {useState} from 'react';

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

export default function DataSelectionStep() {
	const [filterType, setFilterType] = useState('');
	const [fromDate, setFromDate] = useState('');
	const [modifiedLast, setModifiedLast] = useState('');
	const [toDate, setToDate] = useState('');

	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

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
		<>
			<ClayLayout.Sheet>
				<ClayLayout.ContentRow
					className="flex-column flex-lg-row"
					padded
				>
					<ClayLayout.ContentCol>
						<ClayForm.Group>
							<label htmlFor="filterContentBy">
								{Liferay.Language.get('filter-content-by')}
							</label>

							<ClaySelectWithOption
								id="filterContentBy"
								onChange={(event) =>
									setFilterType(event.target.value)
								}
								options={FILTER_OPTIONS}
								value={filterType}
							/>
						</ClayForm.Group>
					</ClayLayout.ContentCol>

					{filterType === 'last' && (
						<ClayLayout.ContentCol>
							<ClayForm.Group>
								<label htmlFor="modifiedLast">
									{Liferay.Language.get('modified-last')}
								</label>

								<ClaySelectWithOption
									id="modifiedLast"
									onChange={(event) =>
										setModifiedLast(event.target.value)
									}
									options={MODIFIED_LAST_OPTIONS}
									value={modifiedLast}
								/>
							</ClayForm.Group>
						</ClayLayout.ContentCol>
					)}

					{filterType === 'range' && (
						<>
							<ClayLayout.ContentCol>
								<ClayForm.Group>
									<label htmlFor="fromDate">
										{Liferay.Language.get('from')}
									</label>

									<ClayDatePicker
										dateFormat={DATE_FORMAT}
										firstDayOfWeek={dateUtils.getFirstDayOfWeek(
											locale
										)}
										id="fromDate"
										months={dateUtils.getMonthsLong(locale)}
										onChange={(value) =>
											setFromDate(value as string)
										}
										placeholder={DATE_FORMAT.toUpperCase()}
										value={fromDate}
										weekdaysShort={dateUtils.getWeekdaysShort(
											locale
										)}
										years={{
											end: new Date().getFullYear() + 10,
											start:
												new Date().getFullYear() - 10,
										}}
									/>
								</ClayForm.Group>
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol>
								<ClayForm.Group>
									<label htmlFor="toDate">
										{Liferay.Language.get('to')}
									</label>

									<ClayDatePicker
										dateFormat={DATE_FORMAT}
										firstDayOfWeek={dateUtils.getFirstDayOfWeek(
											locale
										)}
										id="toDate"
										months={dateUtils.getMonthsLong(locale)}
										onChange={(value) =>
											setToDate(value as string)
										}
										placeholder={DATE_FORMAT.toUpperCase()}
										value={toDate}
										weekdaysShort={dateUtils.getWeekdaysShort(
											locale
										)}
										years={{
											end: new Date().getFullYear() + 10,
											start:
												new Date().getFullYear() - 10,
										}}
									/>
								</ClayForm.Group>
							</ClayLayout.ContentCol>
						</>
					)}

					<ClayLayout.ContentCol
						className="align-items-end justify-content-center"
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
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				{Liferay.Language.get('portlets')}
			</ClayLayout.Sheet>
		</>
	);
}
