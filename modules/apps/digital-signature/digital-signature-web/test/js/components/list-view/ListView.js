/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {waitFor, waitForElementToBeRemoved} from '@testing-library/dom';
import {act, fireEvent, render} from '@testing-library/react';
import {fetch as frontendJsFetch} from 'frontend-js-web';
import React from 'react';
import {MemoryRouter} from 'react-router';

import ListView from '../../../../src/main/resources/META-INF/resources/js/components/list-view/ListView';
import {
	ACTIONS,
	COLUMNS,
	EMPTY_STATE,
	ENDPOINT,
	RESPONSES,
} from '../../constants';

const BODY = (item) => ({
	...item,
	name: item.name,
});

const customFetch = async ({data, endpoint, method = 'GET'}) => {
	const response = await frontendJsFetch(endpoint, {
		body: JSON.stringify(data),
		method,
	});

	return response.json();
};

const ListViewWrapper = ({initialEntries, ...props}) => (
	<MemoryRouter initialEntries={initialEntries}>
		<ListView
			columns={COLUMNS}
			customFetch={customFetch}
			emptyState={EMPTY_STATE}
			endpoint={ENDPOINT}
			{...props}
		>
			{BODY}
		</ListView>
	</MemoryRouter>
);

describe('ListView', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders with empty state', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSES.NO_ITEMS));

		const {queryAllByText, queryByText} = render(<ListViewWrapper />);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(queryAllByText(/Item/).length).toBe(0);

		expect(queryByText(EMPTY_STATE.title)).toBeTruthy();
		expect(queryByText(EMPTY_STATE.description)).toBeTruthy();
	});

	it('renders with 1 item', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSES.ONE_ITEM));

		const {container, queryAllByText} = render(
			<ListViewWrapper actions={ACTIONS} />
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(queryAllByText(/Item/).length).toBe(1);
		expect(container.querySelectorAll('li.page-item').length).toBe(0);
	});

	it('renders with 21 items and 2 pages', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSES.TWENTY_ONE_ITEMS));

		const {container, queryAllByText} = render(
			<ListViewWrapper actions={ACTIONS} />
		);

		await waitForElementToBeRemoved(() => {
			return document.querySelector('span.loading-animation');
		});

		expect(queryAllByText(/Item/).length).toBe(20);
		expect(container.querySelectorAll('li.page-item').length).toBe(4);
		expect(
			container.querySelector('li.page-item.active').firstElementChild
				.textContent
		).toBe('1');
		expect(queryAllByText('Showing 1 to 20 of 21').length).toBe(1);
	});

	it('current page is greater than total pages', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSES.ONE_ITEM));

		const {container, queryAllByText} = render(
			<ListViewWrapper actions={ACTIONS} initialEntries={['/?page=2']} />
		);

		await waitForElementToBeRemoved(() => {
			return document.querySelector('span.loading-animation');
		});

		await waitFor(() => expect(queryAllByText(/Item/).length).toBe(1));

		expect(container.querySelectorAll('li.page-item').length).toBe(0);
	});

	it('calls actions promises', async () => {
		const refreshAction = jest.fn().mockResolvedValue(true);
		const nonRefreshAction = jest.fn().mockResolvedValue(false);
		fetch.mockResponse(JSON.stringify(RESPONSES.ONE_ITEM));

		const actions = [
			{
				name: 'Action without action',
			},
			{
				action: refreshAction,
				name: 'Action that forces refresh',
			},
			{
				action: nonRefreshAction,
				name: "Action that doesn't refresh",
			},
		];

		const {container, findAllByRole, queryByPlaceholderText} = render(
			<ListViewWrapper actions={actions} />
		);

		await waitForElementToBeRemoved(() => {
			return document.querySelector('span.loading-animation');
		});

		let buttons = await findAllByRole('menuitem', {hidden: true});
		const refreshButton = buttons[buttons.length - 2];

		await act(async () => {
			fireEvent.click(refreshButton);
		});

		expect(refreshAction.mock.calls.length).toBe(1);

		buttons = await findAllByRole('menuitem', {hidden: true});
		const nonRefreshButton = buttons[buttons.length - 1];
		fireEvent.click(nonRefreshButton);

		expect(nonRefreshAction.mock.calls.length).toBe(1);
		expect(fetch.mock.calls.length).toEqual(2);

		const input = queryByPlaceholderText('search...');

		await act(async () => {
			await fireEvent.change(input, {target: {value: 'value'}});
		});

		expect(input.value).toBe('value');
		expect(container.querySelector('.subnav-tbar')).toBeFalsy();

		const submit = container.querySelector('button[type="submit"]');

		await act(async () => {
			await fireEvent.click(submit);
		});

		expect(container.querySelector('.subnav-tbar')).toBeTruthy();
	});
});
