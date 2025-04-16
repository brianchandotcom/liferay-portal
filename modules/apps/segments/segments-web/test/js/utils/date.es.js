/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {convertTimezoneToUTC} from '../../../src/main/resources/META-INF/resources/js/utils/date';

describe('convertTimezoneToUTC using diferent timezones', () => {
	it('return correct date for GMT 0', () => {
		const result = convertTimezoneToUTC(
			'Wed Apr 16 2025 12:00:00 GMT-0000'
		);
		expect(result.toISOString()).toBe('2025-04-16T12:00:00.000Z');
	});

	it('return correct date for GMT-3', () => {
		const result = convertTimezoneToUTC(
			'Wed Apr 16 2025 12:00:00 GMT-0300'
		);
		expect(result.toISOString()).toBe('2025-04-16T15:00:00.000Z');
	});

	it('return correct date for GMT+9', () => {
		const result = convertTimezoneToUTC(
			'Wed Apr 16 2025 12:00:00 GMT+0900'
		);
		expect(result.toISOString()).toBe('2025-04-16T03:00:00.000Z');
	});

	it('return correct date for GMT-5', () => {
		const result = convertTimezoneToUTC(
			'Wed Apr 16 2025 12:00:00 GMT-0500'
		);
		expect(result.toISOString()).toBe('2025-04-16T17:00:00.000Z');
	});

	it('return correct date for GMT+1', () => {
		const result = convertTimezoneToUTC(
			'Wed Apr 16 2025 12:00:00 GMT+0100'
		);
		expect(result.toISOString()).toBe('2025-04-16T11:00:00.000Z');
	});
});
