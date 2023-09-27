/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getVelocityUnits} from '../../../../src/main/resources/META-INF/resources/js/components/filter/util/velocityUnitUtil.es';

describe('The velocityUnitUtil should', () => {
	test('Return an array when dateEnd and dateStarte have 1 week interval', () => {
		const expectedValue = [
			{
				active: true,
				defaultVelocityUnit: true,
				key: 'Days',
				name: 'inst-day',
			},
			{key: 'Weeks', name: 'inst-week'},
		];
		const velocityUnits = getVelocityUnits({
			dateEnd: new Date('12/31/2019'),
			dateStart: new Date('12/23/2019'),
		});

		expect(velocityUnits).toEqual(expectedValue);
	});

	test('Return an array when dateEnd and dateStarte have 1 week interval', () => {
		const expectedValue = [
			{
				active: true,
				defaultVelocityUnit: true,
				key: 'Years',
				name: 'inst-year',
			},
		];
		const velocityUnits = getVelocityUnits({
			dateEnd: new Date('12/31/2019'),
			dateStart: new Date('31/23/2018'),
		});

		expect(velocityUnits).toEqual(expectedValue);
	});
});
