/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {dateUtils} from 'frontend-js-web';
import React, {useMemo, useReducer} from 'react';

import FieldDatePicker from './forms/FieldDatePicker';
import FieldSelectWithOption from './forms/FieldSelectWithOption';

export enum FilterType {
	All = 'all',
	Last = 'last',
	Range = 'range',
}

export enum ModifiedLastType {
	H12 = '12h',
	H24 = '24h',
	H48 = '48h',
	D7 = '7d',
}

export type DateFilterValues =
	| {filterType: FilterType.All}
	| {filterType: FilterType.Last; modifiedLast: ModifiedLastType}
	| {filterType: FilterType.Range; fromDate: string; toDate: string};

const FILTER_OPTIONS = [
	{
		label: Liferay.Language.get('show-all'),
		value: FilterType.All,
	},
	{
		label: Liferay.Language.get('date-range'),
		value: FilterType.Range,
	},
	{
		label: Liferay.Language.get('modified-last'),
		value: FilterType.Last,
	},
];

const MODIFIED_LAST_OPTIONS = [
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-hours'), '12'),
		value: ModifiedLastType.H12,
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-hours'), '24'),
		value: ModifiedLastType.H24,
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-hours'), '48'),
		value: ModifiedLastType.H48,
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-days'), '7'),
		value: ModifiedLastType.D7,
	},
];

const DATE_FORMAT = 'yyyy-MM-dd';

type State = {
	applied: DateFilterValues;
	editing: {
		filterType: FilterType;
		fromDate: string;
		modifiedLast: ModifiedLastType;
		toDate: string;
	};
	touchedFields: {
		fromDate: boolean;
		toDate: boolean;
	};
};

function mapEditingToFilterValues(editing: State['editing']): DateFilterValues {
	const {filterType, fromDate, modifiedLast, toDate} = editing;

	if (filterType === FilterType.Range) {
		return {filterType, fromDate, toDate};
	}

	if (filterType === FilterType.Last) {
		return {filterType, modifiedLast};
	}

	return {filterType: FilterType.All};
}

type Action =
	| {payload: Partial<State['editing']>; type: 'UPDATE_FILTER'}
	| {payload: Partial<State['touchedFields']>; type: 'UPDATE_TOUCHED'}
	| {type: 'SET_TOUCH_ALL'}
	| {type: 'APPLY'}
	| {type: 'RESET'};

const INITIAL_STATE: State = {
	applied: {filterType: FilterType.All},
	editing: {
		filterType: FilterType.All,
		fromDate: '',
		modifiedLast: ModifiedLastType.H12,
		toDate: '',
	},
	touchedFields: {
		fromDate: false,
		toDate: false,
	},
};

function filterReducer(state: State, action: Action): State {
	switch (action.type) {
		case 'UPDATE_FILTER':
			return {
				...state,
				editing: {...state.editing, ...action.payload},
			};
		case 'UPDATE_TOUCHED':
			return {
				...state,
				touchedFields: {...state.touchedFields, ...action.payload},
			};
		case 'SET_TOUCH_ALL':
			return {
				...state,
				touchedFields: {fromDate: true, toDate: true},
			};
		case 'APPLY':
			return {
				...state,
				applied: mapEditingToFilterValues(state.editing),
			};
		case 'RESET':
			return INITIAL_STATE;
		default:
			return state;
	}
}

export default function DateFilter({
	itemsCount = 0,
	onApplyFilter,
}: {
	itemsCount?: number;
	onApplyFilter?: (filterValues: DateFilterValues) => void;
}) {
	const [state, dispatch] = useReducer(filterReducer, INITIAL_STATE);

	const {applied, editing, touchedFields} = state;

	const validation = useMemo(() => {
		const errors: {fromDate?: string; toDate?: string} = {};
		let isValid = true;

		if (editing.filterType === FilterType.Range) {
			const {fromDate, toDate} = editing;

			if (!fromDate && !toDate) {
				isValid = false;
			}

			const isFromValid = !fromDate || dateUtils.isValid(fromDate);
			const isToValid = !toDate || dateUtils.isValid(toDate);

			if (!isFromValid || !isToValid) {
				isValid = false;
			}
			else {
				const fromDateObj = fromDate ? new Date(fromDate) : null;
				const now = new Date();
				const toDateObj = toDate ? new Date(toDate) : null;

				if (fromDateObj && fromDateObj > now) {
					errors.fromDate = Liferay.Language.get(
						'dates-must-not-be-in-the-future'
					);
					isValid = false;
				}

				if (toDateObj && toDateObj > now) {
					errors.toDate = Liferay.Language.get(
						'dates-must-not-be-in-the-future'
					);
					isValid = false;
				}

				if (fromDateObj && toDateObj && fromDateObj > toDateObj) {
					errors.fromDate = Liferay.Language.get(
						'date-range-is-invalid'
					);
					errors.toDate = Liferay.Language.get(
						'date-range-is-invalid'
					);
					isValid = false;
				}
			}
		}

		return {errors, isValid};
	}, [editing]);

	const isDirty = useMemo(() => {
		if (editing.filterType !== applied.filterType) {
			return true;
		}

		if (
			applied.filterType === FilterType.Last &&
			editing.filterType === FilterType.Last
		) {
			return editing.modifiedLast !== applied.modifiedLast;
		}

		if (
			applied.filterType === FilterType.Range &&
			editing.filterType === FilterType.Range
		) {
			return (
				editing.fromDate !== applied.fromDate ||
				editing.toDate !== applied.toDate
			);
		}

		return false;
	}, [editing, applied]);

	const appliedFilterSummary = useMemo(() => {
		if (applied.filterType === FilterType.Last) {
			const option = MODIFIED_LAST_OPTIONS.find(
				(opt) => opt.value === applied.modifiedLast
			);

			return `${Liferay.Language.get('modified-last')}: ${option?.label}`;
		}

		if (applied.filterType === FilterType.Range) {
			const {fromDate, toDate} = applied;

			if (fromDate && toDate) {
				return Liferay.Util.sub(
					Liferay.Language.get('date-range-x-to-x'),
					[fromDate, toDate]
				);
			}

			if (fromDate) {
				return Liferay.Util.sub(
					Liferay.Language.get('date-range-after-x'),
					fromDate
				);
			}

			if (toDate) {
				return Liferay.Util.sub(
					Liferay.Language.get('date-range-before-x'),
					toDate
				);
			}
		}

		return '';
	}, [applied]);

	const handleShowResults = () => {
		dispatch({type: 'SET_TOUCH_ALL'});

		if (validation.isValid) {
			dispatch({type: 'APPLY'});
			onApplyFilter?.(mapEditingToFilterValues(editing));
		}
	};

	const currentYear = new Date().getFullYear();
	const fromDateYear = editing.fromDate
		? new Date(editing.fromDate).getFullYear()
		: currentYear - 10;
	const toDateYear = editing.toDate
		? new Date(editing.toDate).getFullYear()
		: currentYear;

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
					<ClayLayout.ContentCol>
						<FieldSelectWithOption
							id="modifiedLast"
							label={Liferay.Language.get('modified-last')}
							name="modifiedLast"
							onChange={(event) =>
								dispatch({
									payload: {
										modifiedLast: event.target
											.value as ModifiedLastType,
									},
									type: 'UPDATE_FILTER',
								})
							}
							options={MODIFIED_LAST_OPTIONS}
							value={editing.modifiedLast}
						/>
					</ClayLayout.ContentCol>
				)}

				{editing.filterType === FilterType.Range && (
					<>
						<ClayLayout.ContentCol>
							<FieldDatePicker
								dateFormat={DATE_FORMAT}
								errorMessage={
									touchedFields.fromDate
										? validation.errors.fromDate
										: undefined
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
									end: toDateYear,
									start: currentYear - 10,
								}}
							/>
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							<FieldDatePicker
								dateFormat={DATE_FORMAT}
								errorMessage={
									touchedFields.toDate
										? validation.errors.toDate
										: undefined
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
									start: fromDateYear,
								}}
							/>
						</ClayLayout.ContentCol>
					</>
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
