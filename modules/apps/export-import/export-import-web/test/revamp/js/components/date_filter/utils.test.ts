/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	LastRange,
	Range,
} from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/date_filter/types';
import {normalizeDateFilter} from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/date_filter/utils';

describe('normalizeDateFilter', () => {
	it('returns no dates for the show all range', () => {
		expect(normalizeDateFilter({range: Range.All})).toEqual({});
	});

	it('returns the absolute dates for the date range', () => {
		const endDate = '2000-07-28T00:00:00';
		const startDate = '2000-07-27T00:00:00';

		expect(
			normalizeDateFilter({endDate, range: Range.DateRange, startDate})
		).toEqual({
			endDate: new Date(endDate).toISOString(),
			startDate: new Date(startDate).toISOString(),
		});
	});

	it('returns only the start date for an open-ended date range', () => {
		const startDate = '2000-07-27T00:00:00';

		expect(
			normalizeDateFilter({
				endDate: '',
				range: Range.DateRange,
				startDate,
			})
		).toEqual({
			startDate: new Date(startDate).toISOString(),
		});
	});

	it('returns only the end date for an open-ended date range', () => {
		const endDate = '2000-07-27T00:00:00';

		expect(
			normalizeDateFilter({
				endDate,
				range: Range.DateRange,
				startDate: '',
			})
		).toEqual({
			endDate: new Date(endDate).toISOString(),
		});
	});

	it('resolves the modified last range to absolute dates', () => {
		const beforeTime = Date.now();

		const normalizedDateFilter = normalizeDateFilter({
			last: LastRange.H24,
			range: Range.Last,
		});

		const afterTime = Date.now();

		const startTime = new Date(normalizedDateFilter.startDate!).getTime();
		const endTime = new Date(normalizedDateFilter.endDate!).getTime();

		expect(endTime - startTime).toBe(24 * 60 * 60 * 1000);
		expect(endTime).toBeGreaterThanOrEqual(beforeTime);
		expect(endTime).toBeLessThanOrEqual(afterTime);
	});
});
