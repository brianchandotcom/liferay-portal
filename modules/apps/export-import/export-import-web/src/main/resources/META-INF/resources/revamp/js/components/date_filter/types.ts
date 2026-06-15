/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum Range {
	All = 'all',
	DateRange = 'dateRange',
	Last = 'last',
}

export enum LastRange {
	H12 = '12h',
	H24 = '24h',
	H48 = '48h',
	D7 = '7d',
}

export type DateFilterValues =
	| {range: Range.All}
	| {last: LastRange; range: Range.Last}
	| {endDate: string; range: Range.DateRange; startDate: string};

export type NormalizedDateFilter = {
	endDate?: string;
	startDate?: string;
};

export type EditingState = {
	endDate: string;
	last: LastRange;
	range: Range;
	startDate: string;
};

export type TouchedFields = {
	endDate: boolean;
	startDate: boolean;
};

export const YEARS_OFFSET = 10;

export const DATE_FORMAT = 'yyyy-MM-dd';
