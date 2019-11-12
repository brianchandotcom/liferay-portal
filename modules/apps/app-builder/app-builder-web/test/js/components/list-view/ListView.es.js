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

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {HashRouter as Router} from 'react-router-dom';

import ListView from '../../../../src/main/resources/META-INF/resources/js/components/list-view/ListView.es';
import {
	ACTIONS,
	BODY,
	COLUMNS,
	EMPTY_STATE,
	ENDPOINT,
	RESPONSES
} from '../../constants.es';

describe('ListView', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders with empty state', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSES.NO_ITEMS));

		const {queryByText} = render(
			<Router>
				<ListView
					actions={ACTIONS}
					columns={COLUMNS}
					emptyState={EMPTY_STATE}
					endpoint={ENDPOINT}
				>
					{BODY}
				</ListView>
			</Router>
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(queryByText(EMPTY_STATE.title)).toBeTruthy();
		expect(queryByText(EMPTY_STATE.description)).toBeTruthy();
	});

	it('renders with 1 item', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSES.ONE_ITEM));

		const {container, queryAllByTestId} = render(
			<Router>
				<ListView
					actions={ACTIONS}
					columns={COLUMNS}
					emptyState={EMPTY_STATE}
					endpoint={ENDPOINT}
				>
					{BODY}
				</ListView>
			</Router>
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(queryAllByTestId('item').length).toBe(1);
		expect(container.querySelectorAll('li.page-item').length).toBe(0);
	});

	it('renders with 21 items and 2 pages', async () => {
		fetch.mockResponse(JSON.stringify(RESPONSES.TWENTY_ONE_ITEMS));

		const {container, queryAllByTestId, queryAllByText} = render(
			<Router>
				<ListView
					actions={ACTIONS}
					columns={COLUMNS}
					emptyState={EMPTY_STATE}
					endpoint={ENDPOINT}
				>
					{BODY}
				</ListView>
			</Router>
		);

		await waitForElementToBeRemoved(() => {
			return document.querySelector('span.loading-animation');
		});

		expect(queryAllByTestId('item').length).toBe(20);
		expect(container.querySelectorAll('li.page-item').length).toBe(4);
		expect(
			container.querySelector('li.page-item.active').firstElementChild
				.textContent
		).toBe('1');
		expect(queryAllByText('Showing 1 to 20 of 21').length).toBe(1);
	});
});
