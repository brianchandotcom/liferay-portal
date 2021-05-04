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

import getPageSpeedProgress from '../../../src/main/resources/META-INF/resources/js/utils/getPageSpeedProgress';

describe('getPageSpeedProgress', () => {
	it('starts at 0', () => {
		expect(getPageSpeedProgress(0)).toBe(0);
	});

	it('reaches 60 at 8 seconds', () => {
		expect(getPageSpeedProgress(7)).toBeLessThanOrEqual(60);
		expect(getPageSpeedProgress(9)).toBeGreaterThanOrEqual(60);
	});

	it('reaches 99 at 30 seconds', () => {
		expect(getPageSpeedProgress(29)).toBeLessThanOrEqual(99);
		expect(getPageSpeedProgress(31)).toBeGreaterThanOrEqual(99);
	});

	it('never reaches 100', () => {
		expect(getPageSpeedProgress(Number.MAX_VALUE)).toBeLessThan(100);
	});
});
