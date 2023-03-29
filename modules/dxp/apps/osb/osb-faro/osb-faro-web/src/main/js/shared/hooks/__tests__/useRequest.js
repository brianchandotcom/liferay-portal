import Promise from 'metal-promise';
import useRequest from '../useRequest';
import {renderHook} from '@testing-library/react-hooks';

const mockRequest = jest.fn(() => Promise.resolve('passed'));
const mockFailedRequest = jest.fn(() => Promise.reject('failed'));

describe('withRequest', () => {
	it('it should return a loading state until the the request completes', () => {
		const {result} = renderHook(() =>
			useRequest({dataSourceFn: mockRequest})
		);

		expect(result.current.loading).toBeTrue();

		jest.runAllTimers();

		expect(result.current.loading).toBeFalse();
	});

	it('it should return the data when the request completes', () => {
		const {result} = renderHook(() =>
			useRequest({dataSourceFn: mockRequest})
		);

		jest.runAllTimers();

		expect(result.current.data).toEqual('passed');
	});

	it('it should return an error if the request failed', () => {
		const {result} = renderHook(() =>
			useRequest({dataSourceFn: mockFailedRequest})
		);

		jest.runAllTimers();

		expect(result.current.error).toBeTrue();
	});

	it('it should return a refetch function to refire the request', () => {
		const spy = jest.fn(() => Promise.resolve('passed'));

		const {result} = renderHook(() => useRequest({dataSourceFn: spy}));

		jest.runAllTimers();

		result.current.refetch();

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledTimes(2);
	});
});
