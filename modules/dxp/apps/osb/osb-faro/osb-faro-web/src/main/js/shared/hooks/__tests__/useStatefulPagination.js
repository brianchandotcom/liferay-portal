import Constants from 'shared/util/constants';
import React from 'react';
import {createOrderIOMap} from 'shared/util/pagination';
import {Map, Set} from 'immutable';
import {render} from '@testing-library/react';
import {useStatefulPagination} from 'shared/hooks/useStatefulPagination';

const {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA} = Constants.pagination;

jest.unmock('react-dom');

describe('useStatefulPagination', () => {
	it('should return default values', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		render(<Component />);

		expect(result).toMatchSnapshot();
	});

	it('should set delta value on onDeltaChange and reset page', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		render(<Component />);

		const newDelta = 10;
		const newPage = 2;

		jest.runAllTimers();

		expect(result.delta).toBe(DEFAULT_DELTA);

		result.onPageChange(newPage);

		jest.runAllTimers();

		expect(result.page).toBe(newPage);

		result.onDeltaChange(newDelta);

		jest.runAllTimers();

		expect(result.delta).toBe(newDelta);
		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('should set page value on onPageChange and page be reseted', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		render(<Component />);

		const newPage = 2;

		jest.runAllTimers();

		expect(result.page).toBe(DEFAULT_PAGE);

		result.onPageChange(newPage);

		jest.runAllTimers();

		expect(result.page).toBe(newPage);

		result.resetPage();

		jest.runAllTimers();

		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('should set orderIOMap value on onOrderIOMapChange and page be reseted', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination(null, {
				initialOrderIOMap: createOrderIOMap('name')
			});

			return null;
		};

		render(<Component />);

		const newPage = 2;

		jest.runAllTimers();

		expect(result.orderIOMap.size).toBe(1);

		result.onPageChange(newPage);

		jest.runAllTimers();

		expect(result.page).toBe(newPage);

		result.onOrderIOMapChange(createOrderIOMap('dateModified', 'ASC'));

		jest.runAllTimers();

		expect(result.orderIOMap.size).toBe(1);
		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('should set query value on onQueryChange and reset page', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		render(<Component />);

		const newQuery = 'test';
		const newPage = 2;

		jest.runAllTimers();

		expect(result.query).toBe('');

		result.onPageChange(newPage);

		jest.runAllTimers();

		expect(result.page).toBe(newPage);

		result.onQueryChange(newQuery);

		jest.runAllTimers();

		expect(result.query).toBe(newQuery);
		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('should set filterBy value on onFilterByChange and reset page', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		render(<Component />);

		const newPage = 2;

		jest.runAllTimers();

		expect(result.filterBy.size).toBe(0);

		result.onPageChange(newPage);

		jest.runAllTimers();

		expect(result.page).toBe(newPage);

		result.onFilterByChange(
			Map({
				biz: Set(['buz'])
			})
		);

		jest.runAllTimers();

		expect(result.filterBy.size).toBe(1);
		expect(result.page).toBe(DEFAULT_PAGE);
	});
});
