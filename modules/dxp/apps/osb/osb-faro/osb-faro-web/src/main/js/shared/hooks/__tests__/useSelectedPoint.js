import useSelectedPoint from '../useSelectedPoint';
import {renderHook} from '@testing-library/react-hooks';

describe('useSelectedPoint', () => {
	it('should not have value on the first render', () => {
		const {result} = renderHook(() => useSelectedPoint());

		jest.runAllTimers();

		expect(result.current.hasSelectedPoint).toBeFalse();
		expect(result.current.selectedPoint).toBeFalsy();
	});

	it('should return dispatched value', () => {
		const {result} = renderHook(() => useSelectedPoint());
		const newPoint = 1;

		jest.runAllTimers();

		result.current.onPointSelect(newPoint);

		jest.runAllTimers();

		expect(result.current.hasSelectedPoint).toBeTrue();
		expect(result.current.selectedPoint).toBe(newPoint);
	});
});
