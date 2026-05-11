/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DateTimeRangeFilter from '../../../../../src/main/resources/META-INF/resources/management_bar/controls/filters/implementation/DateTimeRangeFilter';

const {getOdataString, getSelectedItemsLabel} = DateTimeRangeFilter;

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

	it('generates a "ge" filter with timezone offset when only "from" is provided', () => {
		const result = getOdataString({
			id: 'testField',
			selectedData: {from: fromDateTime},
		} as any);

		expect(result).toBe('testField ge 2026-05-11T15:30:00+00:00');
	});

	it('generates an "le" filter with timezone offset when only "to" is provided', () => {
		const result = getOdataString({
			id: 'testField',
			selectedData: {to: toDateTime},
		} as any);

		expect(result).toBe('testField le 2026-05-11T17:45:00+00:00');
	});

	it('generates a "ge" and "le" filter when both ends are provided', () => {
		const result = getOdataString({
			id: 'testField',
			selectedData: {from: fromDateTime, to: toDateTime},
		} as any);

		expect(result).toBe(
			'testField ge 2026-05-11T15:30:00+00:00) and (testField le 2026-05-11T17:45:00+00:00'
		);
	});

	describe('honors the user configured timezone (not the browser TZ)', () => {
		it('uses a negative offset for America/Los_Angeles (PDT)', () => {
			setTimeZone('America/Los_Angeles');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T15:30:00-07:00');
		});

		it('uses a positive offset for Asia/Tokyo', () => {
			setTimeZone('Asia/Tokyo');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T15:30:00+09:00');
		});

		it('honors DST for Europe/London in summer (BST, +01:00)', () => {
			setTimeZone('Europe/London');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T15:30:00+01:00');
		});

		it('falls back to +00:00 for an invalid timezone', () => {
			setTimeZone('Not/A_Real_Zone');

			const result = getOdataString({
				id: 'testField',
				selectedData: {from: fromDateTime},
			} as any);

			expect(result).toBe('testField ge 2026-05-11T15:30:00+00:00');
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
