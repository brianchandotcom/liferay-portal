import {isFSA} from 'flux-standard-action';
import {setBackURL} from '../settings';

describe('Settings Actions', () => {
	describe('setBackURL', () => {
		it('should return an action', () => {
			const action = setBackURL('123');

			expect(isFSA(action)).toBe(true);
		});
	});
});
