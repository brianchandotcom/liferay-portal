/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dateUtils, sub} from 'frontend-js-web';

import {
	DateFilterValues,
	FilterState,
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

export function mapEditingToFilterValues(
	editing: FilterState['editing']
): DateFilterValues {
	const {filterType, fromDate, modifiedLast, toDate} = editing;

	if (filterType === FilterType.Range) {
		return {filterType, fromDate, toDate};
	}

	if (filterType === FilterType.Last) {
		return {filterType, modifiedLast};
	}

	return {filterType: FilterType.All};
}

export function getAppliedFilterSummary(applied: DateFilterValues): string {
	if (applied.filterType === FilterType.Last) {
		const option = MODIFIED_LAST_OPTIONS.find(
			(opt) => opt.value === applied.modifiedLast
		);

		return `${Liferay.Language.get('modified-last')}: ${option?.label}`;
	}

	if (applied.filterType === FilterType.Range) {
		const {fromDate, toDate} = applied;

		if (fromDate && toDate) {
			return sub(Liferay.Language.get('date-range-x-to-x'), [
				fromDate,
				toDate,
			]);
		}

		if (fromDate) {
			return sub(Liferay.Language.get('date-range-after-x'), fromDate);
		}

		if (toDate) {
			return sub(Liferay.Language.get('date-range-before-x'), toDate);
		}
	}

	return '';
}

export function getIsDirty(
	editing: FilterState['editing'],
	applied: FilterState['applied']
): boolean {
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
}

export function getValidation(editing: FilterState['editing']): {
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
