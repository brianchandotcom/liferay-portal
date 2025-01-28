/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {parse} from '../../../src/main/resources/META-INF/resources/utils/date_time/parse';

describe('parse', () => {
	it('parses date string "01/01/2025" as Date object', () => {
		const dateString = '01/01/2025';
		const parsedDate = parse(dateString, 'MM/dd/yyyy', 'en-US');

		expect(parsedDate).toEqual(new Date(2025, 0, 1));
	});

	it('parses date string "2025-01-01" as Date object', () => {
		const dateString = '2025-01-01';
		const parsedDate = parse(dateString, 'yyyy-MM-dd', 'en-US');

		expect(parsedDate).toEqual(new Date(2025, 0, 1));
	});

	it('parses date string "20250101143000" as Date object', () => {
		const dateString = '20250101143000';
		const parsedDate = parse(dateString, 'yyyyMMddhhmmss', 'en-US');

		expect(parsedDate).toEqual(new Date(2025, 0, 1, 14, 30, 0));
	});

	it('parses date string "2025/01/01" as Date object', () => {
		const dateString = '2025/01/01';
		const parsedDate = parse(dateString, 'yyyy/MM/dd', 'en-US');

		expect(parsedDate).toEqual(new Date(2025, 0, 1));
	});

	it('parses date string "01-01-2025" as Date object', () => {
		const dateString = '01-01-2025';
		const parsedDate = parse(dateString, 'MM-dd-yyyy', 'en-US');

		expect(parsedDate).toEqual(new Date(2025, 0, 1));
	});

	it('warns and return null if parser is not found', () => {
		const dateString = '01/01/2025';
		console.warn = jest.fn();
		const parsedDate = parse(dateString, 'unknown-format', 'en-US');

		expect(console.warn).toHaveBeenCalledWith(
			"No parser found for 'unknown-format'."
		);

		expect(parsedDate).toBeNull();
	});

	it('returns undefined if date string is empty', () => {
		const dateString = '';
		const parsedDate = parse(dateString, 'MM/dd/yyyy', 'en-US');

		expect(parsedDate).toBeUndefined();
	});

	it('returns the date string unchanged if no format is provided', () => {
		const dateString = '01/01/2025';
		const parsedDate = parse(dateString, '', 'en-US');

		expect(parsedDate).toBe(dateString);
	});
});
