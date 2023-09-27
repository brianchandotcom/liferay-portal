/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isSubsetOf} from '../../src/main/resources/META-INF/resources/js/validators';

describe('validators', () => {
	describe('isSubsetOf()', () => {
		it('returns true if a set is subset of a superset', () => {
			const superset = ['a', 'b', 'c'];
			const checker = isSubsetOf(superset);

			expect(checker(['a', 'b'])).toBeTruthy();
			expect(checker(['b'])).toBeTruthy();
			expect(checker(['c', 'a'])).toBeTruthy();
			expect(checker(['a', 'b', 'c'])).toBeTruthy();
			expect(checker(['a', 'd'])).not.toBeTruthy();
			expect(checker(['a', 'b', 'c', null])).not.toBeTruthy();
		});
	});
});
