import {fetchCurrentUser} from '../users';
import {isFSA} from 'flux-standard-action';

describe('User Actions', () => {
	describe('fetchCurrentUser', () => {
		it('should return an action', () => {
			const action = fetchCurrentUser('123');

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
