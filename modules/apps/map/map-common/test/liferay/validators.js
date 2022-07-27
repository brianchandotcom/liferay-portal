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
