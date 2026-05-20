/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	dateTimeRangeFilterImplementation,
	isWithinBounds,
} from '../../../../../src/main/resources/META-INF/resources/management_bar/controls/filters/implementation/DateTimeRangeFilter';

const {getOdataString, getSelectedItemsLabel} =
	dateTimeRangeFilterImplementation;

const fromDateTime = {day: 11, hour: 15, minute: 30, month: 5, year: 2026};
const toDateTime = {day: 11, hour: 17, minute: 45, month: 5, year: 2026};

function setTimeZone(timeZone: string) {
	globalThis.Liferay.ThemeDisplay.getTimeZone = () => timeZone;
}

describe('DateTimeRangeFilter.getOdataString', () => {
	beforeEach(() => {
		setTimeZone('UTC');
	});

	it('returns an empty string if no dates are selected', () => {
		const result = getOdataString({
			id: 'testField',
			selectedData: {},
		} as any);

		expect(result).toBe('');
	});

	it('generates a "ge" filter in UTC when only "from" is provided', () => {
		const result = getOdataString({
			id: 'testField',
			selectedData: {from: fromDateTime},
		} as any);

		expect(result).toBe('testField ge 2026-05-11T15:30:00Z');
	});

	it('generates an "le" filter in UTC when only "to" is provided', () => {
		const result = getOdataString({
			id: 'testField',
			selectedData: {to: toDateTime},
		} as any);

		expect(result).toBe('testField le 2026-05-11T17:45:00Z');
	});

	it('generates a "ge" and "le" filter when both ends are provided', () => {
		const result = getOdataString({
			id: 'testField',
			selectedData: {from: fromDateTime, to: toDateTime},
		} as any);

		expect(result).toBe(
			'testField ge 2026-05-11T15:30:00Z) and (testField le 2026-05-11T17:45:00Z'
		);
	});

	describe('honors the user configured timezone (not the browser TZ)', () => {
		it('converts wall-clock to UTC for America/Los_Angeles (PDT, -07:00)', () => {
			setTimeZone('America/Los_Angeles');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T22:30:00Z');
		});

		it('converts wall-clock to UTC for Asia/Tokyo (+09:00)', () => {
			setTimeZone('Asia/Tokyo');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T06:30:00Z');
		});

		it('honors DST for Europe/London in summer (BST, +01:00)', () => {
			setTimeZone('Europe/London');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T14:30:00Z');
		});

		it('falls back to UTC for an invalid timezone', () => {
			setTimeZone('Not/A_Real_Zone');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T15:30:00Z');
		});
	});
});

describe('DateTimeRangeFilter.isWithinBounds', () => {
	const noBound = {day: 0, hour: 0, minute: 0, month: 0, year: 0};

	const minBound = {day: 1, hour: 0, minute: 0, month: 1, year: 2026};
	const maxBound = {day: 31, hour: 23, minute: 59, month: 12, year: 2026};

	const beforeMin = {day: 31, hour: 23, minute: 59, month: 12, year: 2025};
	const insideBounds = {day: 15, hour: 12, minute: 0, month: 6, year: 2026};
	const afterMax = {day: 1, hour: 0, minute: 0, month: 1, year: 2027};

	describe('without an upper limit', () => {
		it('accepts a date after the lower bound', () => {
			expect(isWithinBounds(afterMax, minBound, noBound)).toBe(true);
		});

		it('rejects a date before the lower bound', () => {
			expect(isWithinBounds(beforeMin, minBound, noBound)).toBe(false);
		});
	});

	describe('without a lower limit', () => {
		it('accepts a date before the upper bound', () => {
			expect(isWithinBounds(beforeMin, noBound, maxBound)).toBe(true);
		});

		it('rejects a date after the upper bound', () => {
			expect(isWithinBounds(afterMax, noBound, maxBound)).toBe(false);
		});
	});

	describe('with both limits', () => {
		it('accepts a date inside the range', () => {
			expect(isWithinBounds(insideBounds, minBound, maxBound)).toBe(true);
		});

		it('rejects a date before the lower bound', () => {
			expect(isWithinBounds(beforeMin, minBound, maxBound)).toBe(false);
		});

		it('rejects a date after the upper bound', () => {
			expect(isWithinBounds(afterMax, minBound, maxBound)).toBe(false);
		});
	});
});

describe('DateTimeRangeFilter.getSelectedItemsLabel', () => {
	it('formats a from-and-to range', () => {
		const result = getSelectedItemsLabel({
			selectedData: {from: fromDateTime, to: toDateTime},
		} as any);

		expect(result).toBe('2026-05-11 15:30 - 2026-05-11 17:45');
	});

	it('returns an empty string when nothing is selected', () => {
		const result = getSelectedItemsLabel({
			selectedData: {},
		} as any);

		expect(result).toBe('');
	});
});
