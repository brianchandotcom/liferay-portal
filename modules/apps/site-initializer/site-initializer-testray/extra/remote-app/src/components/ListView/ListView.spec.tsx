/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {act, cleanup, render} from '@testing-library/react';
import {Mock, vi} from 'vitest';

import ListView from '.';
import PageWrapper from '../../test/PageWrapper';

describe('ListView', () => {
	let baseFetch: Mock;

	beforeAll(() => {
		vi.resetAllMocks();
		vi.useFakeTimers();
	});

	beforeEach(() => {
		baseFetch = vi.fn().mockImplementationOnce(() => ({
			items: [{email: 'testray@liferay.com', name: 'Testray User'}],
			lastPage: true,
			pageIndex: 1,
			pageSize: 20,
			totalCount: 100,
		}));

		vi.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		vi.clearAllTimers();
		vi.restoreAllMocks();
	});

	afterAll(() => {
		vi.useRealTimers();
	});

	it('render ListView with default props', async () => {
		const {asFragment} = render(
			<ListView
				resource="/users"
				tableProps={{
					columns: [{key: 'email', value: 'Email'}],
				}}
			/>,
			{
				wrapper: ({children}) => (
					<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
				),
			}
		);

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('render with items on list and actions', async () => {
		const {container, queryByText} = render(
			<ListView
				resource="/users"
				tableProps={{
					columns: [
						{key: 'name', value: 'Name'},
						{key: 'email', value: 'Email'},
					],
				}}
			/>,
			{
				wrapper: ({children}) => (
					<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
				),
			}
		);

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(container.querySelectorAll('thead tr')).toHaveLength(1);
		expect(container.querySelectorAll('tbody tr')).toHaveLength(1);
		expect(container.querySelector('table')).toBeTruthy();
		expect(queryByText('testray@liferay.com')).toBeTruthy();
		expect(queryByText('Testray User')).toBeTruthy();
	});

	it('render with empty state', async () => {
		baseFetch = vi.fn().mockImplementation(() => ({
			items: [],
			lastPage: false,
			pageIndex: 1,
			pageSize: 20,
			totalCount: 0,
		}));

		const {container, queryByText} = render(
			<ListView
				resource="/projects"
				tableProps={{
					columns: [
						{key: 'name', value: 'Name'},
						{key: 'email', value: 'Email'},
					],
				}}
			/>,
			{
				wrapper: ({children}) => (
					<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
				),
			}
		);

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(queryByText('No results found')).toBeTruthy();
		expect(container.querySelector('table')).toBeNull();
	});
});
