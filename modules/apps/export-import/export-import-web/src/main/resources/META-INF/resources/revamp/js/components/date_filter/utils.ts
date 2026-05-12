/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dateUtils, sub} from 'frontend-js-web';

import {
	DateFilterValues,
	EditingState,
	FilterType,
	ModifiedLastType,
} from './types';

export const FILTER_OPTIONS = [
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

export const HOURS_BY_MODIFIED_LAST: Record<ModifiedLastType, number> = {
	[ModifiedLastType.H12]: 12,
	[ModifiedLastType.H24]: 24,
	[ModifiedLastType.H48]: 48,
	[ModifiedLastType.D7]: 24 * 7,
};

export const MODIFIED_LAST_OPTIONS = [
	{
		label: sub(Liferay.Language.get('x-hours'), '12'),
		value: ModifiedLastType.H12,
	},
	{
		label: sub(Liferay.Language.get('x-hours'), '24'),
		value: ModifiedLastType.H24,
	},
	{
		label: sub(Liferay.Language.get('x-hours'), '48'),
		value: ModifiedLastType.H48,
	},
	{
		label: sub(Liferay.Language.get('x-days'), '7'),
		value: ModifiedLastType.D7,
	},
];

export function editingToDateFilter(editing: EditingState): DateFilterValues {
	const {filterType, fromDate, modifiedLast, toDate} = editing;

	if (filterType === FilterType.Range) {
		return {endDate: toDate, range: FilterType.Range, startDate: fromDate};
	}

	if (filterType === FilterType.Last) {
		return {last: modifiedLast, range: FilterType.Last};
	}

	return {range: FilterType.All};
}

export function getAppliedFilterSummary(applied: DateFilterValues): string {
	if (applied.range === FilterType.Last) {
		const option = MODIFIED_LAST_OPTIONS.find(
			(opt) => opt.value === applied.last
		);

		return `${Liferay.Language.get('modified-last')}: ${option?.label ?? ''}`;
	}

	if (applied.range === FilterType.Range) {
		const {endDate, startDate} = applied;

		if (startDate && endDate) {
			return sub(Liferay.Language.get('date-range-x-to-x'), [
				startDate,
				endDate,
			]);
		}

		if (startDate) {
			return sub(Liferay.Language.get('date-range-after-x'), startDate);
		}

		if (endDate) {
			return sub(Liferay.Language.get('date-range-before-x'), endDate);
		}
	}

	return '';
}

export function getIsDirty(
	editing: EditingState,
	applied: DateFilterValues
): boolean {
	if (editing.filterType !== applied.range) {
		return true;
	}

	if (
		applied.range === FilterType.Last &&
		editing.filterType === FilterType.Last
	) {
		return editing.modifiedLast !== applied.last;
	}

	if (
		applied.range === FilterType.Range &&
		editing.filterType === FilterType.Range
	) {
		return (
			editing.fromDate !== applied.startDate ||
			editing.toDate !== applied.endDate
		);
	}

	return false;
}

export function getValidation(editing: EditingState): {
	errors: {fromDate?: string; toDate?: string};
	isValid: boolean;
} {
	const errors: {fromDate?: string; toDate?: string} = {};

	if (editing.filterType !== FilterType.Range) {
		return {errors, isValid: true};
	}

	const {fromDate, toDate} = editing;

	if (!fromDate && !toDate) {
		return {errors, isValid: false};
	}

	const isFromValid = !fromDate || dateUtils.isValid(fromDate);
	const isToValid = !toDate || dateUtils.isValid(toDate);

	if (!isFromValid || !isToValid) {
		return {errors, isValid: false};
	}

	const fromDateObj = fromDate ? new Date(fromDate) : null;
	const toDateObj = toDate ? new Date(toDate) : null;

	if (fromDateObj && fromDateObj > new Date()) {
		errors.fromDate = Liferay.Language.get(
			'dates-must-not-be-in-the-future'
		);
	}

	if (toDateObj && toDateObj > new Date()) {
		errors.toDate = Liferay.Language.get('dates-must-not-be-in-the-future');
	}

	if (fromDateObj && toDateObj && fromDateObj > toDateObj) {
		const rangeError = Liferay.Language.get('date-range-is-invalid');

		errors.fromDate = rangeError;
		errors.toDate = rangeError;
	}

	return {
		errors,
		isValid: !Object.keys(errors).length,
	};
}
