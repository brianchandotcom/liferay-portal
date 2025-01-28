/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format} from '../../../src/main/resources/META-INF/resources/utils/date_time/format';

describe('format', () => {
	it('formats date as "MM/dd/yyyy"', () => {
		const date = new Date(2025, 0, 1);
		const formattedDate = format(date, 'MM/dd/yyyy', 'en-US');

		expect(formattedDate).toBe('01/01/2025');
	});

	it('formats date as "yyyy-MM-dd"', () => {
		const date = new Date(2025, 0, 1);
		const formattedDate = format(date, 'yyyy-MM-dd', 'en-US');

		expect(formattedDate).toBe('2025-01-01');
	});

	it('formats date as "MMM dd, yyyy"', () => {
		const date = new Date(2025, 0, 1);
		const formattedDate = format(date, 'MMM dd, yyyy', 'en-US');

		expect(formattedDate).toBe('Jan 01, 2025');
	});

	it('formats date as "hh-mm"', () => {
		const date = new Date(2025, 0, 1, 14, 30);
		const formattedDate = format(date, 'hh-mm', 'en-US');

		expect(formattedDate).toBe('14-30');
	});

	it('formats date as "MMM d, h a"', () => {
		const date = new Date(2025, 0, 1, 14, 30);
		const formattedDate = format(date, 'MMM d, h a', 'en-US');

		expect(formattedDate).toBe('Jan 1, 2 PM');
	});

	it('formats date as "yyyy-MM-ddThh:mm:ssZ"', () => {
		const date = new Date(Date.UTC(2025, 0, 1, 14, 30, 45));
		const formattedDate = format(date, 'yyyy-MM-ddThh:mm:ssZ', 'en-US');

		expect(formattedDate).toBe('2025-01-01T14:30:45Z');
	});

	it('warns and default to "yyyy-MM-ddThh:mm:ssZ" if format is not found', () => {
		const date = new Date(2025, 0, 1);
		console.warn = jest.fn();
		const formattedDate = format(date, 'unknown-format', 'en-US');

		expect(console.warn).toHaveBeenCalledWith(
			"No formatter found for 'unknown-format'. Defaulting to use 'yyyy-MM-ddThh:mm:ssZ'."
		);

		expect(formattedDate).toBe('2025-01-01T00:00:00Z');
	});

	it('returns the date unchanged if no format is provided', () => {
		const date = new Date(2025, 0, 1);
		const formattedDate = format(date, '', 'en-US');

		expect(formattedDate).toBe(date);
	});
});
