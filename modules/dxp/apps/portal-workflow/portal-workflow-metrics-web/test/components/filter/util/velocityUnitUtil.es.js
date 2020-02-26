/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
