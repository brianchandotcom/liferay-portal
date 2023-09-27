/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
