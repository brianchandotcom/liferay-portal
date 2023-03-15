import * as ArrayUtils from '../array';

describe('array utils', () => {
	describe('insertAtIndex', () => {
		it('inserts "eggs" between "cream cheese" and "muffins"', () => {
			expect(
				ArrayUtils.insertAtIndex(
					['bagels', 'cream cheese', 'muffins'],
					2,
					'eggs'
				)
			).toEqual(['bagels', 'cream cheese', 'eggs', 'muffins']);
		});
	});

	describe('removeAtIndex', () => {
		it('removes "muffins" from items', () => {
			expect(
				ArrayUtils.removeAtIndex(
					['bagels', 'cream cheese', 'muffins', 'eggs'],
					2
				)
			).toEqual(['bagels', 'cream cheese', 'eggs']);
		});
	});

	describe('moveItem', () => {
		it('returns a new array where "bagels" has been inserted between "muffins" and "toast"', () => {
			const original = ['bagels', 'cream cheese', 'muffins', 'toast'];

			const result = ArrayUtils.moveItem(original, 0, 2);

			expect(result).toEqual([
				'cream cheese',
				'muffins',
				'bagels',
				'toast'
			]);

			expect(result).not.toBe(original);
		});
	});

	describe('replaceAtIndex', () => {
		it('returns a new array where "bagels" has replaced "toast"', () => {
			expect(
				ArrayUtils.replaceAtIndex(
					['cream cheese', 'muffins', 'toast'],
					2,
					'bagels'
				)
			).toEqual(['cream cheese', 'muffins', 'bagels']);
		});
	});

	describe('replaceWithMultipleAtIndex', () => {
		it('should replace the item at the index and insert remaining items after the index', () => {
			expect(
				ArrayUtils.replaceWithMultipleAtIndex([4, 5, 6], [1, 2, 3], 1)
			).toEqual(expect.arrayContaining([1, 4, 5, 6, 3]));
		});
	});

	describe('getDifferences', () => {
		it('should return the difference between two arrays of numbers', () => {
			expect(
				ArrayUtils.getDifferences([1, 2, 3, 4, 5], [3, 4, 5, 6])
			).toEqual(expect.arrayContaining([1, 2, 6]));
		});

		it('should return the difference between two array of string', () => {
			expect(
				ArrayUtils.getDifferences(
					['banana', 'coconut', 'lime', 'melon'],
					['banana', 'coconut', 'apple']
				)
			).toEqual(expect.arrayContaining(['lime', 'melon', 'apple']));
		});
	});
});
