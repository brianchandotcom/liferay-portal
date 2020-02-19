/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	convertUTCDateToLocalDate,
	getTimezoneOffsetHour
} from '../../src/utils/date';

describe('convertUTCDateToLocalDate()', () => {
	it('returns the converted local date', () => {
		const date = new Date('2020-01-01T00:00:00.000Z');

		expect(convertUTCDateToLocalDate(date)).toEqual(
			new Date('2020-01-01T00:00:00.000Z')
		);
	});
});

describe('getTimezoneOffsetHour()', () => {
	it('returns the timezone offset hour', () => {
		const date = new Date('2020-01-01T00:00:00.000Z');

		expect(getTimezoneOffsetHour(date)).toBe('+00:00');
	});
});
