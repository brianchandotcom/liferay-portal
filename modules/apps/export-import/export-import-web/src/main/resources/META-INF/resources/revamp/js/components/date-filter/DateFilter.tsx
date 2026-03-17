/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import React, {useMemo, useReducer} from 'react';

import FieldSelectWithOption from '../forms/FieldSelectWithOption';
import DateRangeFields from './DateRangeFields';
import ModifiedLastFields from './ModifiedLastFields';
import {INITIAL_STATE, filterReducer} from './reducer';
import {DateFilterValues, FilterType} from './types';
import {
	FILTER_OPTIONS,
	getAppliedFilterSummary,
	getIsDirty,
	getValidation,
	mapEditingToFilterValues,
} from './utils';

export default function DateFilter({
	itemsCount = 0,
	onApplyFilter,
}: {
	itemsCount?: number;
	onApplyFilter?: (filterValues: DateFilterValues) => void;
}) {
	const [state, dispatch] = useReducer(filterReducer, INITIAL_STATE);

	const {applied, editing, touchedFields} = state;

	const validation = useMemo(() => getValidation(editing), [editing]);

	const isDirty = useMemo(
		() => getIsDirty(editing, applied),
		[editing, applied]
	);

	const appliedFilterSummary = useMemo(
		() => getAppliedFilterSummary(applied),
		[applied]
	);

	const handleShowResults = () => {
		dispatch({type: 'SET_TOUCH_ALL'});

		if (validation.isValid) {
			dispatch({type: 'APPLY'});
			onApplyFilter?.(mapEditingToFilterValues(editing));
		}
	};

	return (
		<>
			<ClayLayout.ContentRow className="flex-column flex-lg-row" padded>
				<ClayLayout.ContentCol>
					<FieldSelectWithOption
						id="filterContentBy"
						label={Liferay.Language.get('filter-content-by')}
						name="filterContentBy"
						onChange={(event) =>
							dispatch({
								payload: {
									filterType: event.target
										.value as FilterType,
								},
								type: 'UPDATE_FILTER',
							})
						}
						options={FILTER_OPTIONS}
						value={editing.filterType}
					/>
				</ClayLayout.ContentCol>

				{editing.filterType === FilterType.Last && (
					<ModifiedLastFields
						dispatch={dispatch}
						value={editing.modifiedLast}
					/>
				)}

				{editing.filterType === FilterType.Range && (
					<DateRangeFields
						dispatch={dispatch}
						editing={editing}
						errors={validation.errors}
						touchedFields={touchedFields}
					/>
				)}

				<ClayLayout.ContentCol
					className="align-items-end d-flex justify-content-center"
					expand
				>
					{!(
						editing.filterType === FilterType.All &&
						applied.filterType === FilterType.All
					) && (
						<ClayButton
							disabled={isDirty ? !validation.isValid : true}
							displayType="secondary"
							onClick={handleShowResults}
							size="sm"
						>
							{Liferay.Language.get('show-results')}
						</ClayButton>
					)}
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{applied.filterType !== FilterType.All && (
				<ClayLayout.ContentRow padded>
					<ClayLayout.ContentCol expand>
						<ClayAlert
							actions={
								<ClayButton
									borderless
									onClick={() => {
										dispatch({type: 'RESET'});
										onApplyFilter?.({
											filterType: FilterType.All,
										});
									}}
									size="sm"
								>
									{Liferay.Language.get('clear-filters')}
								</ClayButton>
							}
							className="w-100"
							displayType="info"
							title={Liferay.Util.sub(
								Liferay.Language.get(
									'x-results-found-for-colon'
								),
								String(itemsCount)
							)}
							variant="inline"
						>
							{appliedFilterSummary}
						</ClayAlert>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			)}
		</>
	);
}
