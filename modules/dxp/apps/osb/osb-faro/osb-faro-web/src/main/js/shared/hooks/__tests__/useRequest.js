import {renderHook} from '@testing-library/react-hooks';
import {useRequest} from 'shared/hooks';
import {waitFor} from '@testing-library/react';

const mockRequest = jest.fn(() => Promise.resolve('passed'));
const mockFailedRequest = jest.fn(() => Promise.reject('failed'));

describe('withRequest', () => {
	it('it should return a loading state until the the request completes', async () => {
		const {result} = renderHook(() =>
			useRequest({dataSourceFn: mockRequest})
		);

		expect(result.current.loading).toBeTrue();

		await waitFor(() => {
			expect(result.current.loading).toBe(false);
		});
	});

	it('it should return the data when the request completes', async () => {
		const {result} = renderHook(() =>
			useRequest({dataSourceFn: mockRequest})
		);

		await waitFor(() => {
			expect(result.current.data).toEqual('passed');
		});
	});

	it('it should return an error if the request failed', async () => {
		const {result} = renderHook(() =>
			useRequest({dataSourceFn: mockFailedRequest})
		);

		await waitFor(() => {
			expect(result.current.error).toBeTrue();
		});
	});

	it('it should return a refetch function to refire the request', async () => {
		const spy = jest.fn(() => Promise.resolve('passed'));

		const {result} = renderHook(() => useRequest({dataSourceFn: spy}));

		await waitFor(() => {
			result.current.refetch();

			expect(spy).toHaveBeenCalledTimes(2);
		});
	});
});
