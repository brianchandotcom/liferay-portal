/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DateFilterValues, LastRange, Range} from '../components/date_filter';
import {DateFilter} from '../types/exportImportProcess';
import {toWallClockDateTime} from './dateTime';

const LAST_RANGES: Array<{hours: number; lastRange: LastRange}> = [
	{hours: 12, lastRange: LastRange.H12},
	{hours: 24, lastRange: LastRange.H24},
	{hours: 48, lastRange: LastRange.H48},
	{hours: 168, lastRange: LastRange.D7},
];

function toLastRange(hours: number): LastRange | null {
	if (!(hours > 0)) {
		return null;
	}

	const [closestLastRange] = [...LAST_RANGES].sort(
		(first, second) =>
			Math.abs(first.hours - hours) - Math.abs(second.hours - hours)
	);

	return closestLastRange.lastRange;
}

function toDateTime(
	isoDateTime: string | undefined,
	timeZoneId: string
): string {
	if (!isoDateTime || Number.isNaN(Date.parse(isoDateTime))) {
		return '';
	}

	return toWallClockDateTime(isoDateTime, timeZoneId);
}

export function toDateFilterValues(
	dateFilter: DateFilter | undefined,
	timeZoneId: string
): DateFilterValues {
	if (dateFilter?.type === 'DateRange') {
		return {
			endDate: toDateTime(dateFilter.endDate, timeZoneId),
			range: Range.DateRange,
			startDate: toDateTime(dateFilter.startDate, timeZoneId),
		};
	}

	if (dateFilter?.type === 'LastHours') {
		const lastRange = toLastRange(dateFilter.hours);

		if (lastRange) {
			return {last: lastRange, range: Range.Last};
		}
	}

	if (dateFilter?.type === 'FromLastPublishDate') {
		return {range: Range.FromLastPublishDate};
	}

	return {range: Range.All};
}
