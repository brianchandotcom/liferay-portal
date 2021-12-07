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

import toDataArray from '../../../../../src/main/resources/META-INF/resources/js/custom/form-report/utils/data';

describe('toDataArray', () => {
	it('sorts array from the highest to lower count values ', () => {
		const sorted = toDataArray(
			{
				label1: {index: 0, value: 'Label 1'},
				label2: {index: 1, value: 'Label 2'},
			},
			{
				label1: 2,
				label2: 4,
			}
		);

		const [first, second] = sorted;

		expect(first.label === 'Label 2').toBeTruthy();
		expect(second.label === 'Label 1').toBeTruthy();
		expect(first.count > second.count).toBeTruthy();
	});
});
