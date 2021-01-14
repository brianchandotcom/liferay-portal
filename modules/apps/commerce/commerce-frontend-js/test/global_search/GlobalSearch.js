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

import '../utils/polyfills';

import '@testing-library/jest-dom/extend-expect';
import {
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import fetchMock from 'fetch-mock';
import React from 'react';

import ServiceProvider from '../../src/main/resources/META-INF/resources/ServiceProvider/index';
import GlobalSearch from '../../src/main/resources/META-INF/resources/components/global_search/GlobalSearch';
import {accountTemplate, getAccounts} from '../utils/fake_data/accounts';
import {getOrders, orderTemplate} from '../utils/fake_data/orders';
import {getProducts, productTemplate} from '../utils/fake_data/products';

const accountsEndpointRegexp = new RegExp(
	ServiceProvider.AdminAccountAPI('v1').baseURL
);

const ordersEndpointRegexp = new RegExp(
	ServiceProvider.AdminOrderAPI('v1').baseURL
);

const productsEndpointRegexp = new RegExp(
	ServiceProvider.DeliveryCatalogAPI('v1').getBaseURL(11111)
);

const query = 'test';

describe('Global Search', () => {
	describe('When responses are ok', () => {
		let renderedComponent;

		beforeEach(() => {
			fetchMock.mock(accountsEndpointRegexp, (url) => getAccounts(url));
			fetchMock.mock(ordersEndpointRegexp, (url) => getOrders(url));
			fetchMock.mock(productsEndpointRegexp, (url) => getProducts(url));

			renderedComponent = render(
				<GlobalSearch
					accountURLTemplate="/account-page/{id}"
					accountsSearchURLTemplate="/accounts?search={query}"
					channelId={11111}
					globalSearchURLTemplate="/global?search={query}"
					orderURLTemplate="/order-page/{id}"
					ordersSearchURLTemplate="/orders?search={query}"
					productURLTemplate="/product-page/{id}"
					productsSearchURLTemplate="/products?search={query}"
					spritemap="./assets/icons.svg"
				/>
			);
		});

		afterEach(() => {
			fetchMock.restore();
			cleanup();
		});

		describe('When input is empty', () => {
			it('must display the global search input', () => {
				expect(
					renderedComponent.getByPlaceholderText(/search/)
				).toBeInTheDocument();
			});
		});

		describe('When input is filled', () => {
			beforeEach(() => {
				const input = renderedComponent.getByPlaceholderText(/search/);

				fireEvent.change(input, {target: {value: query}});
			});

			it('must show 3 loaders', () => {
				expect(
					renderedComponent.baseElement.querySelectorAll(
						'.loading-animation'
					).length
				).toBe(3);
			});

			describe('after the results are loaded', () => {
				beforeEach(async () => {
					await waitForElement(() =>
						renderedComponent.getByText(
							`search-${query}-in-accounts`
						)
					);
					await waitForElement(() =>
						renderedComponent.getByText(
							`search-${query}-in-catalog`
						)
					);
					await waitForElement(() =>
						renderedComponent.getByText(`search-${query}-in-orders`)
					);
				});

				it('must format the URL templates replacing the placeholders with entity properties', () => {
					expect(
						renderedComponent.getByText(`search-${query}-in-orders`)
							.href
					).toContain(`/orders?search=${query}`);

					expect(
						renderedComponent.getByText(
							`search-${query}-in-accounts`
						).href
					).toContain(`/accounts?search=${query}`);

					expect(
						renderedComponent.getByText(
							`search-${query}-in-catalog`
						).href
					).toContain(`/products?search=${query}`);

					expect(
						renderedComponent.getByText(`more-global-results`).href
					).toContain(`/global?search=${query}`);
				});

				it('must show a product list', () => {
					const products = renderedComponent.baseElement.querySelectorAll(
						'.product-item'
					);
					const firstProduct = products[0];
					const firstProductThumbnail = firstProduct.querySelector(
						'img'
					);

					expect(products.length).toBe(4);

					expect(firstProductThumbnail.src).toContain(
						productTemplate.urlImage
					);

					expect(firstProductThumbnail.alt).toBe(
						productTemplate.name
					);

					expect(firstProduct.href).toContain(
						`/product-page/${productTemplate.id}`
					);

					expect(firstProduct.text).toBe(productTemplate.name);
				});

				it('must show an order list', () => {
					const orders = renderedComponent.baseElement.querySelectorAll(
						'.order-item'
					);
					const firstOrder = orders[0];

					expect(orders.length).toBe(4);

					expect(firstOrder.text).toContain(orderTemplate.id);

					expect(firstOrder.href).toContain(
						`/order-page/${orderTemplate.id}`
					);
				});

				it('must show an account list', () => {
					const accounts = renderedComponent.baseElement.querySelectorAll(
						'.account-item'
					);
					const firstAccount = accounts[0];
					const firstAccountThumbnail = firstAccount.querySelector(
						'img'
					);

					expect(accounts.length).toBe(4);

					expect(firstAccountThumbnail.src).toContain(
						accountTemplate.logoURL
					);

					expect(firstAccountThumbnail.alt).toBe(
						accountTemplate.name
					);

					expect(firstAccount.href).toContain(
						`/account-page/${accountTemplate.id}`
					);

					expect(firstAccount.text).toBe(accountTemplate.name);
				});
			});
		});
	});

	describe('When responses are not ok', () => {
		let renderedComponent;
		const toastErrorMessages = [];

		beforeEach(() => {
			window.Liferay.staticEnvTestUtils.print = (message) => {
				toastErrorMessages.push(message);
			};

			fetchMock.mock(accountsEndpointRegexp, () => {
				return Promise.reject({message: 'Error - accounts'});
			});
			fetchMock.mock(ordersEndpointRegexp, () => {
				return Promise.reject({message: 'Error - orders'});
			});
			fetchMock.mock(productsEndpointRegexp, () => {
				return Promise.reject({message: 'Error - products'});
			});

			renderedComponent = render(
				<GlobalSearch
					accountURLTemplate="/account-page/{id}"
					accountsSearchURLTemplate="/accounts?search={query}"
					channelId={11111}
					globalSearchURLTemplate="/global?search={query}"
					orderURLTemplate="/order-page/{id}"
					ordersSearchURLTemplate="/orders?search={query}"
					productURLTemplate="/product-page/{id}"
					productsSearchURLTemplate="/products?search={query}"
					spritemap="./assets/icons.svg"
				/>
			);
		});

		afterEach(() => {
			fetchMock.restore();
			cleanup();
		});

		describe('When input is filled', () => {
			beforeEach(() => {
				const input = renderedComponent.getByPlaceholderText(/search/);

				fireEvent.change(input, {target: {value: query}});
			});

			describe('after the results are loaded', () => {
				it('must show coherent messages', async () => {
					expect(
						await waitForElement(() =>
							renderedComponent.getByText(`no-orders-were-found`)
						)
					).toBeInTheDocument();

					expect(
						await waitForElement(() =>
							renderedComponent.getByText(
								`no-products-were-found`
							)
						)
					).toBeInTheDocument();

					expect(
						await waitForElement(() =>
							renderedComponent.getByText(
								`no-accounts-were-found`
							)
						)
					).toBeInTheDocument();

					expect(
						renderedComponent.getByText(`more-global-results`).href
					).toContain(`/global?search=${query}`);
				});

				it('must notify the user', () => {
					expect(toastErrorMessages).toContain('Error - accounts');
					expect(toastErrorMessages).toContain('Error - orders');
					expect(toastErrorMessages).toContain('Error - products');
				});
			});
		});
	});
});
