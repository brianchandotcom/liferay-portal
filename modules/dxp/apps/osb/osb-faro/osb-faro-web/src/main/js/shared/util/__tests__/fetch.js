jest.mock('metal-ajax');

import Ajax from 'metal-ajax';
import fetch from '../fetch';
import Promise from 'metal-promise';

describe('fetch', () => {
	it('should return a Promise', () => {
		Ajax.request.mockReturnValue(Promise.resolve(1));

		const result = fetch().catch(jest.fn());

		expect(result instanceof Promise).toBe(true);
	});
});
