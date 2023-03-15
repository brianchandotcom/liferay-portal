import Constants from 'shared/util/constants';
import useStatefulPagination from '../useStatefulPagination';
import {createOrderIOMap} from 'shared/util/pagination';
import {Map, Set} from 'immutable';
import {renderHook} from '@testing-library/react-hooks';

const {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA} = Constants.pagination;

describe('useStatefulPagination', () => {
	it('should return default values', () => {
		const {result} = renderHook(() => useStatefulPagination());

		jest.runAllTimers();

		expect(result.current).toMatchSnapshot();
	});

	it('should set delta value on onDeltaChange and reset page', () => {
		const {result} = renderHook(() => useStatefulPagination());
		const newDelta = 10;
		const newPage = 2;

		jest.runAllTimers();
		expect(result.current.delta).toBe(DEFAULT_DELTA);

		result.current.onPageChange(newPage);

		jest.runAllTimers();
		expect(result.current.page).toBe(newPage);

		result.current.onDeltaChange(newDelta);

		jest.runAllTimers();
		expect(result.current.delta).toBe(newDelta);
		expect(result.current.page).toBe(DEFAULT_PAGE);
	});

	it('should set page value on onPageChange and page be reseted', () => {
		const {result} = renderHook(() => useStatefulPagination());
		const newPage = 2;

		jest.runAllTimers();
		expect(result.current.page).toBe(DEFAULT_PAGE);

		result.current.onPageChange(newPage);

		jest.runAllTimers();
		expect(result.current.page).toBe(newPage);

		result.current.resetPage();

		jest.runAllTimers();
		expect(result.current.page).toBe(DEFAULT_PAGE);
	});

	it('should set orderIOMap value on onOrderIOMapChange and page be reseted', () => {
		const {result} = renderHook(() =>
			useStatefulPagination(null, {
				initialOrderIOMap: createOrderIOMap('name')
			})
		);
		const newPage = 2;

		jest.runAllTimers();
		expect(result.current.orderIOMap.size).toBe(1);

		result.current.onPageChange(newPage);

		jest.runAllTimers();
		expect(result.current.page).toBe(newPage);

		result.current.onOrderIOMapChange(
			createOrderIOMap('dateModified', 'ASC')
		);

		jest.runAllTimers();
		expect(result.current.orderIOMap.size).toBe(1);
		expect(result.current.page).toBe(DEFAULT_PAGE);
	});

	it('should set query value on onQueryChange and reset page', () => {
		const {result} = renderHook(() => useStatefulPagination());
		const newQuery = 'test';
		const newPage = 2;

		jest.runAllTimers();
		expect(result.current.query).toBe('');

		result.current.onPageChange(newPage);

		jest.runAllTimers();
		expect(result.current.page).toBe(newPage);

		result.current.onQueryChange(newQuery);

		jest.runAllTimers();
		expect(result.current.query).toBe(newQuery);
		expect(result.current.page).toBe(DEFAULT_PAGE);
	});

	it('should set filterBy value on onFilterByChange and reset page', () => {
		const {result} = renderHook(() => useStatefulPagination());
		const newPage = 2;

		jest.runAllTimers();
		expect(result.current.filterBy.size).toBe(0);

		result.current.onPageChange(newPage);

		jest.runAllTimers();
		expect(result.current.page).toBe(newPage);

		result.current.onFilterByChange(
			Map({
				biz: Set(['buz'])
			})
		);

		jest.runAllTimers();
		expect(result.current.filterBy.size).toBe(1);
		expect(result.current.page).toBe(DEFAULT_PAGE);
	});
});
