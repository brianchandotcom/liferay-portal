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

import '../test_utilities/polyfills';

import '@testing-library/jest-dom/extend-expect';
import {
	cleanup,
	fireEvent,
	render,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import ServiceProvider from '../../src/main/resources/META-INF/resources/ServiceProvider/index';
import GlobalSearch from '../../src/main/resources/META-INF/resources/components/global_search/GlobalSearch';
import {
	accountTemplate,
	getAccounts,
} from '../test_utilities/fake_data/accounts';
import {getOrders} from '../test_utilities/fake_data/orders';

const ACCOUNTS_HEADLESS_API_ENDPOINT = ServiceProvider.AdminAccountAPI('v1')
	.baseURL;
const ORDERS_HEADLESS_API_ENDPOINT = ServiceProvider.AdminOrderAPI('v1')
	.baseURL;

describe('Global Search', () => {
	beforeEach(() => {
		const accountsEndpointRegexp = new RegExp(
			ACCOUNTS_HEADLESS_API_ENDPOINT
		);
		const ordersEndpointRegexp = new RegExp(ORDERS_HEADLESS_API_ENDPOINT);

		fetchMock.mock(accountsEndpointRegexp, (url) => getAccounts(url));
		fetchMock.mock(ordersEndpointRegexp, (url) => getOrders(url));
	});

	afterEach(() => {
		fetchMock.restore();
	});

	describe('When input is empty', () => {
		let renderedComponent;

		beforeEach(() => {
			renderedComponent = render(
				<GlobalSearch
					spritemap="./assets/icons.svg"
				/>
			);
		});

		afterEach(() => {
			cleanup();
		});

		it('must display the accounts search autocomplete component"', () => {
			expect(
				renderedComponent.getByPlaceholderText(/search/)
			).toBeInTheDocument();
		});
	});
});
