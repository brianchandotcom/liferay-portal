import React from 'react';
import {act, renderHook} from '@testing-library/react-hooks';
import {LifecycleContextProvider, useLifecycle} from '../LifecycleContext';

jest.unmock('react-dom');

const wrapper = ({children}: {children: React.ReactNode}) => (
	<LifecycleContextProvider>{children}</LifecycleContextProvider>
);

describe('LifecycleContext', () => {
	it('should expose empty filters by default', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		expect(result.current.filters).toEqual({
			countryFilter: '',
			filterString: '',
			industryFilter: ''
		});
	});

	it('should update a single filter and recompute filterString', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		act(() => result.current.updateFilters({industryFilter: 'Tech'}));

		expect(result.current.filters).toEqual({
			countryFilter: '',
			filterString: "industry eq 'Tech'",
			industryFilter: 'Tech'
		});
	});

	it('should merge partial updates without clearing untouched filters', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		act(() => result.current.updateFilters({industryFilter: 'Tech'}));
		act(() => result.current.updateFilters({countryFilter: 'US'}));

		expect(result.current.filters.filterString).toBe(
			"country eq 'US' and industry eq 'Tech'"
		);
	});

	it('should reset filters to their initial values', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		act(() =>
			result.current.updateFilters({
				countryFilter: 'US',
				industryFilter: 'Tech'
			})
		);
		act(() => result.current.resetFilters());

		expect(result.current.filters).toEqual({
			countryFilter: '',
			filterString: '',
			industryFilter: ''
		});
	});
});
