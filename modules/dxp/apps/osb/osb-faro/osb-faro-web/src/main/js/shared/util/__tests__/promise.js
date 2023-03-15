import Promise from 'metal-promise';
import {sequence} from '../promise';

describe('sequence', () => {
	it('should return the first rejected promise', () => {
		expect.assertions(1);

		const rejectVal = 'reject';

		const response = sequence([
			() => Promise.reject(rejectVal),
			() => Promise.resolve('resolve'),
			() => Promise.reject('dont reject')
		])();

		return expect(response).rejects.toBe(rejectVal);
	});

	it('should return the last resolved promise', () => {
		expect.assertions(1);

		const resolveVal = 'resolve';

		const response = sequence([
			() => Promise.resolve('test'),
			() => Promise.resolve(resolveVal)
		])();

		return expect(response).resolves.toBe(resolveVal);
	});
});
