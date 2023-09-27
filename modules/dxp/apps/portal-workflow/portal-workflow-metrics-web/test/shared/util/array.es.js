/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {compareArrays} from '../../../src/main/resources/META-INF/resources/js/shared/util/array.es';

const array = [1, 2, 3];
test('Compare the same array', () => {
	const result = compareArrays(array, array);

	expect(result).toBeTruthy();
});

test('Compare differents arrays', () => {
	const result = compareArrays(array, [3, 2, 1]);

	expect(result).toBeFalsy();
});

test('Compare an empty array and a valid array', () => {
	const result = compareArrays(array, []);

	expect(result).toBeFalsy();
});
