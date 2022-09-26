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

import Projects from '..';
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {Route} from 'react-router-dom';
import {Mock, vi} from 'vitest';

import PageWrapper from '../../../test/PageWrapper';

describe('Projects', () => {
	let baseFetch: Mock;

	beforeAll(() => {
		vi.resetAllMocks();
		vi.useFakeTimers();
	});

	beforeEach(() => {
		baseFetch = vi.fn().mockImplementationOnce(() => ({
			items: [
				{description: 'DXP Version', id: 1, name: 'Liferay Portal 7.4'},
			],
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

	it('render projects with success', async () => {
		const {asFragment} = render(<Projects />, {
			wrapper: ({children}) => (
				<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
			),
		});

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('check project permissions to open new project page', async () => {
		baseFetch = vi.fn().mockImplementationOnce(() => ({
			actions: {
				create: {},
			},
			items: [
				{
					description: 'DXP Version',
					id: 1,
					name: 'Liferay Portal 7.4',
				},
			],
			lastPage: true,
			pageIndex: 1,
			pageSize: 20,
			totalCount: 100,
		}));

		const {container, queryByText} = render(<Projects />, {
			wrapper: ({children}) => (
				<PageWrapper
					clearCache
					customRoutes={
						<Route
							element={<div>Project Form</div>}
							path="/project/create"
						/>
					}
					fetcher={baseFetch}
				>
					{children}
				</PageWrapper>
			),
		});

		await act(async () => {
			await vi.runAllTimers();
		});

		const plusButton = container.querySelector('.lexicon-icon-plus')
			?.parentElement;

		expect(plusButton).toBeTruthy();

		fireEvent.click(plusButton as HTMLButtonElement);

		expect(queryByText('Project Form')).toBeTruthy();
	});
});
