import {isModifyingKey} from 'source/util/dom.es';

describe('DOM Utilities', () => {
	describe('isModifyingKey', () => {
		it('identity modifying keys', () => {
			expect(isModifyingKey(65)).toEqual(true);
			expect(isModifyingKey(18)).toEqual(false);
		});
	});
});
