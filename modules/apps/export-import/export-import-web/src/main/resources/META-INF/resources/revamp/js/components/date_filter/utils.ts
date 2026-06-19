/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dateUtils, sub} from 'frontend-js-web';

import {
	DateFilterValues,
	EditingState,
	LastRange,
	NormalizedDateFilter,
	Range,
} from './types';

export const RANGE_OPTIONS = [
	{
		label: Liferay.Language.get('show-all'),
		value: Range.All,
	},
	{
		label: Liferay.Language.get('date-range'),
		value: Range.DateRange,
	},
	{
		label: Liferay.Language.get('modified-last'),
		value: Range.Last,
	},
];

const MILLISECONDS_BY_LAST_RANGE: Record<LastRange, number> = {
	[LastRange.H12]: 12 * 60 * 60 * 1000,
	[LastRange.H24]: 24 * 60 * 60 * 1000,
	[LastRange.H48]: 48 * 60 * 60 * 1000,
	[LastRange.D7]: 7 * 24 * 60 * 60 * 1000,
};

export const LAST_RANGE_OPTIONS = [
	{
		label: sub(Liferay.Language.get('x-hours'), '12'),
		value: LastRange.H12,
	},
	{
		label: sub(Liferay.Language.get('x-hours'), '24'),
		value: LastRange.H24,
	},
	{
		label: sub(Liferay.Language.get('x-hours'), '48'),
		value: LastRange.H48,
	},
	{
		label: sub(Liferay.Language.get('x-days'), '7'),
		value: LastRange.D7,
	},
];

export function normalizeDateFilter(
	dateFilter: DateFilterValues
): NormalizedDateFilter {
	if (dateFilter.range === Range.Last) {
		const now = Date.now();

		return {
			endDate: new Date(now).toISOString(),
			startDate: new Date(
				now - MILLISECONDS_BY_LAST_RANGE[dateFilter.last]
			).toISOString(),
		};
	}

	if (dateFilter.range === Range.DateRange) {
		const {endDate, startDate} = dateFilter;

		return {
			...(endDate ? {endDate: new Date(endDate).toISOString()} : {}),
			...(startDate
				? {startDate: new Date(startDate).toISOString()}
				: {}),
		};
	}

	return {};
}

export function editingToDateFilter(editing: EditingState): DateFilterValues {
	const {endDate, last, range, startDate} = editing;

	if (range === Range.DateRange) {
		return {endDate, range: Range.DateRange, startDate};
	}

	if (range === Range.Last) {
		return {last, range: Range.Last};
	}

	return {range: Range.All};
}

export function getAppliedFilterSummary(applied: DateFilterValues): string {
	if (applied.range === Range.Last) {
		const option = LAST_RANGE_OPTIONS.find(
			(opt) => opt.value === applied.last
		);

		return `${Liferay.Language.get('modified-last')}: ${option?.label ?? ''}`;
	}

	if (applied.range === Range.DateRange) {
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
	if (editing.range !== applied.range) {
		return true;
	}

	if (applied.range === Range.Last && editing.range === Range.Last) {
		return editing.last !== applied.last;
	}

	if (
		applied.range === Range.DateRange &&
		editing.range === Range.DateRange
	) {
		return (
			editing.startDate !== applied.startDate ||
			editing.endDate !== applied.endDate
		);
	}

	return false;
}

export function getValidation(editing: EditingState): {
	errors: {endDate?: string; startDate?: string};
	isValid: boolean;
} {
	const errors: {endDate?: string; startDate?: string} = {};

	if (editing.range !== Range.DateRange) {
		return {errors, isValid: true};
	}

	const {endDate, startDate} = editing;

	if (!startDate && !endDate) {
		return {errors, isValid: false};
	}

	const isStartValid = !startDate || dateUtils.isValid(startDate);
	const isEndValid = !endDate || dateUtils.isValid(endDate);

	if (!isStartValid || !isEndValid) {
		return {errors, isValid: false};
	}

	const startDateObj = startDate ? new Date(startDate) : null;
	const endDateObj = endDate ? new Date(endDate) : null;

	if (startDateObj && startDateObj > new Date()) {
		errors.startDate = Liferay.Language.get(
			'dates-must-not-be-in-the-future'
		);
	}

	if (endDateObj && endDateObj > new Date()) {
		errors.endDate = Liferay.Language.get(
			'dates-must-not-be-in-the-future'
		);
	}

	if (startDateObj && endDateObj && startDateObj > endDateObj) {
		const rangeError = Liferay.Language.get('date-range-is-invalid');

		errors.startDate = rangeError;
		errors.endDate = rangeError;
	}

	return {
		errors,
		isValid: !Object.keys(errors).length,
	};
}
