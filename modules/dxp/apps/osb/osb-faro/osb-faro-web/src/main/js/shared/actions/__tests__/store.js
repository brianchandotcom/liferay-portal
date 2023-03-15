import {actionTypes, clearStore} from '../store';
import {isFSA} from 'flux-standard-action';

describe('store', () => {
	describe('clearStore', () => {
		it('should return a clearStore action', () => {
			const action = clearStore();

			expect(isFSA(action)).toBeTrue();
			expect(action.type).toBe(actionTypes.CLEAR_STORE);
		});
	});
});
