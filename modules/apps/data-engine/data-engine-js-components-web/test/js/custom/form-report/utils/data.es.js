/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
