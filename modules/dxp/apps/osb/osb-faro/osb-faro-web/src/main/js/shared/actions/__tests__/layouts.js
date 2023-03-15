import {fetchLayout} from '../layouts';
import {isFSA} from 'flux-standard-action';

describe('Layout Actions', () => {
	describe('fetchLayout', () => {
		it('should return an action', () => {
			const action = fetchLayout({contactsEntityId: '123', type: 1});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
