import {some} from '../iterable';

describe('iterable', () => {
	describe('some', () => {
		it('should return true if an item satifies the predicate', () => {
			const items = [1, 2, 3];

			expect(some(items, i => i > 2)).toBe(true);
		});

		it('should return false if no items satisfy the predicate', () => {
			const items = [1, 2, 3];

			expect(some(items, i => i > 4)).toBe(false);
		});
	});
});
