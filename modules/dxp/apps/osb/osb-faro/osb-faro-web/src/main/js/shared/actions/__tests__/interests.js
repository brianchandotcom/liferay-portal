import {isFSA} from 'flux-standard-action';
import {searchInterests} from '../interests';

describe('Interests', () => {
	describe('searchInterests', () => {
		it('should return an action', () => {
			const action = searchInterests({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
