/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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

export type FilterState = {
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

export type FilterAction =
	| {payload: Partial<FilterState['editing']>; type: 'UPDATE_FILTER'}
	| {payload: Partial<FilterState['touchedFields']>; type: 'UPDATE_TOUCHED'}
	| {type: 'SET_TOUCH_ALL'}
	| {type: 'APPLY'}
	| {type: 'RESET'};

export const YEARS_OFFSET = 10;

export const DATE_FORMAT = 'yyyy-MM-dd';
