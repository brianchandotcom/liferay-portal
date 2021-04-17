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

import '../../utils/polyfills';

import '@testing-library/jest-dom/extend-expect';
import {
	act,
	cleanup,
	fireEvent,
	render,
	waitForElementToBeRemoved,
} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import ServiceProvider from '../../../src/main/resources/META-INF/resources/ServiceProvider/index';
import AccountSelector from '../../../src/main/resources/META-INF/resources/components/account_selector/AccountSelector';
import {accountTemplate, getAccounts} from '../../utils/fake_data/accounts';
import {getOrders} from '../../utils/fake_data/orders';

const ACCOUNTS_HEADLESS_API_ENDPOINT = ServiceProvider.AdminAccountAPI('v1')
	.baseURL;
const ORDERS_HEADLESS_API_ENDPOINT = ServiceProvider.AdminOrderAPI('v1')
	.baseURL;

describe('AccountSelector', () => {
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
		cleanup();
	});

	describe('When no account is selected', () => {
		let renderedComponent;

		beforeEach(() => {
			renderedComponent = render(
				<AccountSelector
					createNewOrderURL="/order-link"
					selectOrderURL="/test-url/{id}"
					setCurrentAccountURL="/account-selector/setCurrentAccounts"
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

		it('must display a placeholder if no account is selected"', () => {
			expect(
				renderedComponent.getByText('select-account-and-order')
			).toBeInTheDocument();
		});

		it('displays an account list', async () => {
			await act(async () => {
				fireEvent.click(
					renderedComponent.baseElement.querySelector(
						'.btn-account-selector'
					)
				);
			});

			await waitForElementToBeRemoved(() =>
				renderedComponent.queryByText(/loading/i)
			);

			const accountsList = renderedComponent.baseElement.querySelectorAll(
				'.accounts-list li'
			);
			const accountsListItem = accountsList[0];

			expect(accountsList.length).toBe(10);

			expect(accountsListItem.querySelector('img').src).toContain(
				'/test-logo-folder/test.jpg'
			);
		});

		it('must update the remote selected account when an account item is clicked', async () => {
			await act(async () => {
				fireEvent.click(
					renderedComponent.baseElement.querySelector(
						'.btn-account-selector'
					)
				);
			});

			await waitForElementToBeRemoved(() =>
				renderedComponent.queryByText(/loading/i)
			);

			const accountsListItem = renderedComponent.baseElement.querySelectorAll(
				'.accounts-list li'
			)[0];

			fetchMock.post(
				new RegExp('account-selector/setCurrentAccounts'),
				(url, params) => {
					expect(params.body.get('accountId')).toEqual(
						accountTemplate.id.toString()
					);

					expect(url.searchParams.get('groupId')).toBeTruthy();

					return 200;
				}
			);

			await act(async () => {
				fireEvent.click(accountsListItem.querySelector('button'));
			});
		});
	});

	describe('When account is selected', () => {
		let renderedComponent;

		beforeEach(() => {
			renderedComponent = render(
				<AccountSelector
					createNewOrderURL="/order-link"
					currentAccount={{
						id: 42332,
						name: 'My Account Name',
					}}
					selectOrderURL="/test-url/{id}"
					setCurrentAccountURL="/account-selector/setCurrentAccounts"
					spritemap="./assets/icons.svg"
				/>
			);
		});

		afterEach(() => {
			cleanup();
		});

		it('must display the orders search autocomplete component"', () => {
			expect(
				renderedComponent.getByPlaceholderText(/search-order/)
			).toBeInTheDocument();
		});

		it('must display the account name', () => {
			const currentAccountName = renderedComponent.container.querySelector(
				'.btn-account-selector .account-name .text-truncate'
			).innerHTML;
			expect(currentAccountName).toBe('My Account Name');
		});

		it('must display an order placeholder"', () => {
			const orderPlaceholder = renderedComponent.getByText(
				/no-order-selected/i
			);
			expect(orderPlaceholder).toBeInTheDocument();
		});

		it('displays an order list', async () => {
			await act(async () => {
				fireEvent.click(
					renderedComponent.baseElement.querySelector(
						'.btn-account-selector'
					)
				);
			});

			await waitForElementToBeRemoved(() =>
				renderedComponent.queryByText(/loading/i)
			);

			const orders = renderedComponent.baseElement.querySelectorAll(
				'.orders-table tbody tr'
			);
			const orderItem = renderedComponent.baseElement.querySelector(
				'.orders-table tbody tr'
			);

			expect(orders.length).toBe(10);

			expect(orderItem.querySelector('a').href).toContain('/test-url/');
		});
	});

	describe('When account and order are selected', () => {
		let renderedComponent;

		beforeEach(() => {
			renderedComponent = render(
				<AccountSelector
					createNewOrderURL="/order-link"
					currentAccount={{
						id: 42332,
						name: 'My Account Name',
					}}
					currentOrder={{
						orderId: 34234,
						workflowStatusInfo: {
							label_i18n: 'Completed',
						},
					}}
					selectOrderURL="/test-url/{id}"
					setCurrentAccountURL="/account-selector/setCurrentAccounts"
					spritemap="./assets/icons.svg"
				/>
			);
		});

		it('displays the current account name, order ID and order status localized label', () => {
			const button = renderedComponent.container.querySelector(
				'.btn-account-selector'
			);
			const currentAccountName = button.querySelector(
				'.account-name .text-truncate'
			).innerHTML;
			const currentOrderId = button.querySelector('.order-id').innerHTML;
			const currentOrderLabel = button.querySelector(
				'.order-label .label'
			).innerHTML;

			expect(currentAccountName).toBe('My Account Name');
			expect(currentOrderId).toBe('34234');
			expect(currentOrderLabel).toBe('Completed');
		});
	});
});
