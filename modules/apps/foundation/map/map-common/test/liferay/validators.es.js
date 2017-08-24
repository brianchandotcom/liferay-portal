/* eslint-disable require-jsdoc */
/* global assert */

'use strict';

import {isSubsetOf} from '../../src/main/resources/META-INF/resources/js/validators.es';

describe('validators', () => {
	describe('isSubsetOf()', () => {
		it('should return true if a set is subset of a superset', () => {
			const superset = ['a', 'b', 'c'];
			const checker = isSubsetOf(superset);
			assert(checker(['a', 'b']));
			assert(checker(['b']));
			assert(checker(['c', 'a']));
			assert(checker(['a', 'b', 'c']));
			assert(!checker(['a', 'd']));
			assert(!checker(['a', 'b', 'c', null]));
		});
	});
});