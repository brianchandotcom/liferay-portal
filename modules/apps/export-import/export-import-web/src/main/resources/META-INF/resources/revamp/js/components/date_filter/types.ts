/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum FilterType {
	All = 'all',
	Last = 'last',
	Range = 'dateRange',
}

export enum ModifiedLastType {
	H12 = '12h',
	H24 = '24h',
	H48 = '48h',
	D7 = '7d',
}

export type DateFilterValues =
	| {range: FilterType.All}
	| {last: ModifiedLastType; range: FilterType.Last}
	| {endDate: string; range: FilterType.Range; startDate: string};

export type EditingState = {
	filterType: FilterType;
	fromDate: string;
	modifiedLast: ModifiedLastType;
	toDate: string;
};

export type TouchedFields = {
	fromDate: boolean;
	toDate: boolean;
};

export const YEARS_OFFSET = 10;

export const DATE_FORMAT = 'yyyy-MM-dd';
