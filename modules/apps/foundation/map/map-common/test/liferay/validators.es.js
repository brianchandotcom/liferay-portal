import {isSubsetOf} from '../../src/main/resources/META-INF/resources/js/validators.es';

describe('validators', () => {
	describe('isSubsetOf()', () => {
		it('should return true if a set is subset of a superset', () => {
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
