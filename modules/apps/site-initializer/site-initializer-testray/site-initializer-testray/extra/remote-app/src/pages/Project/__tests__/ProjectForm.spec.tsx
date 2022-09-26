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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {Mock, SpyInstance, vi} from 'vitest';

import PageWrapper from '../../../test/PageWrapper';
import ProjectForm from '../ProjectForm';

// const fetch = window.fetch as FetchMock;

describe('ProjectForm', () => {
	let baseFetch: Mock;
	let fetchSpy: SpyInstance;

	beforeAll(() => {
		vi.resetAllMocks();
		vi.useFakeTimers();
		fetchSpy = vi.spyOn(window, 'fetch');
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

	it('render ProjectForm with success', async () => {
		const {asFragment} = render(<ProjectForm />, {
			wrapper: ({children}) => (
				<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
			),
		});

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('Fill ProjectForm inputs and hit actions', async () => {
		fetchSpy.mockResolvedValueOnce({
			json: async () => ({success: true}),
			ok: true,
		});

		const {container, queryByText} = render(<ProjectForm />, {
			wrapper: ({children}) => (
				<PageWrapper fetcher={baseFetch}>{children}</PageWrapper>
			),
		});

		const descriptionInput = container.querySelector('#description');
		const nameInput = container.querySelector('#name');
		const saveButton = queryByText('Save');

		expect(descriptionInput).toBeEmptyDOMElement();
		expect(nameInput).toBeEmptyDOMElement();

		await act(async () => {
			await fireEvent.click(saveButton as HTMLButtonElement);
		});

		expect(queryByText('name is a required field')).toBeTruthy();

		fireEvent.input(nameInput as HTMLInputElement, {
			target: {
				value: 'Liferay 7.4',
			},
		});

		fireEvent.input(descriptionInput as HTMLInputElement, {
			target: {
				value: 'Liferay 7.4 Description',
			},
		});

		await act(async () => {
			await fireEvent.click(saveButton as HTMLButtonElement);
		});

		expect(queryByText('name is a required field')).toBeFalsy();
		expect(fetchSpy.mock.calls).toHaveLength(1);
		expect(fetchSpy.mock.calls[0][1]).toMatchObject({
			body: JSON.stringify({
				description: 'Liferay 7.4 Description',
				name: 'Liferay 7.4',
			}),
			method: 'POST',
		});
	});
});
