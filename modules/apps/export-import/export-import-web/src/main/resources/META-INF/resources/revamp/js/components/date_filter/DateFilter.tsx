/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import FieldSelectWithOption from '../forms/FieldSelectWithOption';
import DateRangeFields from './DateRangeFields';
import ModifiedLastFields from './ModifiedLastFields';
import {
	DateFilterValues,
	EditingState,
	FilterType,
	ModifiedLastType,
	TouchedFields,
} from './types';
import {
	FILTER_OPTIONS,
	getAppliedFilterSummary,
	getIsDirty,
	getValidation,
	mapEditingToFilterValues,
} from './utils';

const INITIAL_EDITING: EditingState = {
	filterType: FilterType.All,
	fromDate: '',
	modifiedLast: ModifiedLastType.H12,
	toDate: '',
};

const INITIAL_TOUCHED: TouchedFields = {
	fromDate: false,
	toDate: false,
};

export default function DateFilter({
	appliedValue = {filterType: FilterType.All} as DateFilterValues,
	itemsCount = 0,
	onApplyFilter,
}: {
	appliedValue?: DateFilterValues;
	itemsCount?: number;
	onApplyFilter?: (filterValues: DateFilterValues) => void;
}) {
	const [editing, setEditing] = useState<EditingState>(INITIAL_EDITING);
	const [touchedFields, setTouchedFields] =
		useState<TouchedFields>(INITIAL_TOUCHED);

	const validation = useMemo(() => getValidation(editing), [editing]);

	const isDirty = useMemo(
		() => getIsDirty(editing, appliedValue),
		[editing, appliedValue]
	);

	const appliedFilterSummary = useMemo(
		() => getAppliedFilterSummary(appliedValue),
		[appliedValue]
	);

	const updateFilter = (patch: Partial<EditingState>) => {
		setEditing((prev) => ({...prev, ...patch}));
	};

	const updateTouched = (patch: Partial<TouchedFields>) => {
		setTouchedFields((prev) => ({...prev, ...patch}));
	};

	const handleShowResults = () => {
		setTouchedFields({fromDate: true, toDate: true});

		if (validation.isValid) {
			onApplyFilter?.(mapEditingToFilterValues(editing));
		}
	};

	const handleClearFilters = () => {
		setEditing(INITIAL_EDITING);
		setTouchedFields(INITIAL_TOUCHED);
		onApplyFilter?.({filterType: FilterType.All});
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
							updateFilter({
								filterType: event.target.value as FilterType,
							})
						}
						options={FILTER_OPTIONS}
						value={editing.filterType}
					/>
				</ClayLayout.ContentCol>

				{editing.filterType === FilterType.Last && (
					<ModifiedLastFields
						handleUpdateFilter={updateFilter}
						value={editing.modifiedLast}
					/>
				)}

				{editing.filterType === FilterType.Range && (
					<DateRangeFields
						editing={editing}
						errors={validation.errors}
						handleUpdateFilter={updateFilter}
						handleUpdateTouched={updateTouched}
						touchedFields={touchedFields}
					/>
				)}

				<ClayLayout.ContentCol
					className="align-items-end d-flex justify-content-center"
					expand
				>
					{!(
						editing.filterType === FilterType.All &&
						appliedValue.filterType === FilterType.All
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

			{appliedValue.filterType !== FilterType.All && (
				<ClayLayout.ContentRow padded>
					<ClayLayout.ContentCol expand>
						<ClayAlert
							actions={
								<ClayButton
									borderless
									onClick={handleClearFilters}
									size="sm"
								>
									{Liferay.Language.get('clear-filters')}
								</ClayButton>
							}
							className="w-100"
							displayType="info"
							title={sub(
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
