/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import mockFetch from 'jest-fetch-mock';

import {
	MarketplaceRest,
	safeJSONParse,
} from '../../../src/main/resources/META-INF/resources/js/core/MarketplaceRest';
import {
	Cart,
	MarketplaceConfiguration,
} from '../../../src/main/resources/META-INF/resources/js/types';
import product from '../__mock__/product';

const baseResourceURL = 'http://localhost:8080';

const marketplaceConfiguration: MarketplaceConfiguration = {
	serviceURL: 'https://backend.marketplace.liferay.com',
	settings: {
		account: {id: 123, name: 'Liferay Labs'},
		channelId: 123,
		cloudProject: 'exte5a2marketplace-extuat',
		references: {fragmentsFilter: '', paymentMethodFilter: ''},
		siteId: 123,
		userAccount: {id: 123, name: 'Ray'},
	},
	url: 'https://marketplace.liferay.com',
};

const marketplaceRest = new MarketplaceRest(
	baseResourceURL,
	marketplaceConfiguration
);

const marketplaceToken = {
	accessToken: 'abcdef',
	accessTokenExpirationTime: (new Date().getTime() + 60000).toString(),
};

jest.mock('frontend-js-web', () => {
	const actual = jest.requireActual('frontend-js-web');

	return {
		...actual,
		createResourceURL: jest.fn((baseURL, params) => ({
			href: `${baseURL}?${new URLSearchParams(params).toString()}`,
			toString: () =>
				`${baseURL}?${new URLSearchParams(params).toString()}`,
		})),
		fetch: mockFetch,
		openToast: jest.fn(),
		sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
	};
});

describe('MarketplaceRest', () => {
	let getItemFn: jest.Mock<WindowSessionStorage['sessionStorage']['getItem']>;
	let setItemFn: jest.Mock<WindowSessionStorage['sessionStorage']['setItem']>;

	beforeEach(() => {
		getItemFn = jest
			.fn()
			.mockImplementation(() => JSON.stringify(marketplaceToken));
		setItemFn = jest.fn();

		globalThis.Liferay.Util.SessionStorage = {
			TYPES: {
				NECESSARY: 'Necessary',
			},
			getItem: getItemFn,
			setItem: setItemFn,
		};
	});

	it('fetch marketplace token without cache', async () => {
		const {fetch} = require('frontend-js-web');

		fetch.mockResponseOnce(JSON.stringify(marketplaceToken));

		getItemFn = jest.fn().mockImplementation(() => null);

		globalThis.Liferay.Util.SessionStorage.getItem = getItemFn;

		const marketplaceAuthorization =
			await marketplaceRest.getMarketplaceToken();

		expect(fetch).toBeCalledTimes(1);
		expect(getItemFn).toBeCalledTimes(1);
		expect(setItemFn).toBeCalledTimes(1);
		expect(marketplaceAuthorization).toMatchObject(marketplaceToken);

		const [firstSetItemCall] = setItemFn.mock.calls;

		expect(firstSetItemCall[0]).toBe('@marketplace/token');
		expect(firstSetItemCall[1]).toBe(JSON.stringify(marketplaceToken));
		expect(firstSetItemCall[2]).toBe(
			globalThis.Liferay.Util.SessionStorage.TYPES.NECESSARY
		);
	});

	it('fetch marketplace token from cache', async () => {
		const marketplaceAuthorization =
			await marketplaceRest.getMarketplaceToken();

		expect(fetch).toBeCalledTimes(0);
		expect(getItemFn).toBeCalledTimes(1);
		expect(setItemFn).toBeCalledTimes(0);
		expect(marketplaceAuthorization).toMatchObject(marketplaceToken);
	});

	it('fetch marketplace products', async () => {
		const {fetch} = require('frontend-js-web');

		const productResponse = {
			items: [],
			page: 1,
			pageSize: 20,
			totalCount: 0,
		};

		fetch.mockResponseOnce(JSON.stringify(productResponse));

		const response = await marketplaceRest.getProducts();
		const [fetchURL, fetchParams] = fetch.mock.calls[0];

		expect(fetch).toBeCalledTimes(1);
		expect(fetchParams.guestOperation).toBe(true);
		expect(fetchURL).toBe(
			`${marketplaceConfiguration.url}/o/headless-commerce-delivery-catalog/v1.0/channels/${marketplaceConfiguration.settings.channelId}/products?`
		);
		expect(response).toMatchObject(productResponse);
	});

	it('fetch marketplace service', async () => {
		const {fetch} = require('frontend-js-web');

		const mockResponse = {success: true};

		fetch.mockResponseOnce(JSON.stringify(mockResponse));

		const response =
			await marketplaceRest.fetchMarketplaceService('/dxp/test');

		expect(fetch).toBeCalledTimes(1);
		expect(response).toMatchObject(mockResponse);

		const [fetchURL] = fetch.mock.calls[0];

		expect(fetchURL).toBe(
			`${marketplaceConfiguration.serviceURL}/dxp/test`
		);
	});

	it('fetch console provisioning', async () => {
		const cart = {
			cartItems: [{id: 456}],
			id: 123,
		} as Cart;

		const {fetch} = require('frontend-js-web');

		const mockResponse = {success: true};

		fetch.mockResponseOnce(JSON.stringify(mockResponse));

		await marketplaceRest.consoleProvisioningOrder(cart);

		const [fetchURL, fetchParams] = fetch.mock.calls[0];

		expect(fetchURL).toBe(
			`${marketplaceConfiguration.serviceURL}/dxp/provisioning/${cart.id}`
		);
		expect(fetchParams.body).toMatch(
			JSON.stringify({
				orderItemId: 456,
				projectId: marketplaceConfiguration.settings.cloudProject,
			})
		);
		expect(fetchParams.method).toBe('POST');
	});

	it('fetch base resource url', async () => {
		const baseURL = MarketplaceRest.getBaseResourceURL();

		expect(baseURL).toBe(
			`/group/guest/~/control_panel/manage?p_p_id=${globalThis.Liferay.PortletKeys.INSTANCE_SETTINGS}`
		);
	});

	it('fetch Marketplace without guest operation', async () => {
		const {fetch} = require('frontend-js-web');

		const mockResponse = {success: true};

		fetch.mockResponseOnce(JSON.stringify(mockResponse));

		const response = await marketplaceRest.fetchMarketplace('/dxp/test');

		const [fetchURL, fetchCall] = fetch.mock.calls[0];

		expect(fetch).toHaveBeenCalled();
		expect(fetchCall.headers['Authorization']).toBe(
			`Bearer ${marketplaceToken.accessToken}`
		);
		expect(fetchURL).toBe(`${marketplaceConfiguration.url}/dxp/test`);
		expect(response).toEqual(mockResponse);
	});

	it('fetch Marketplace with guest operation', async () => {
		const {fetch} = require('frontend-js-web');

		const mockResponse = {success: true};

		fetch.mockResponseOnce(JSON.stringify(mockResponse));

		const response = await marketplaceRest.fetchMarketplace('/dxp/test', {
			guestOperation: true,
		});

		const [fetchURL, fetchParams] = fetch.mock.calls[0];

		expect(fetch).toHaveBeenCalled();
		expect(fetchParams.guestOperation).toBe(true);
		expect(fetchURL).toBe(`${marketplaceConfiguration.url}/dxp/test`);

		expect(response).toEqual(mockResponse);
	});

	it('fetch create cart', async () => {
		const {fetch} = require('frontend-js-web');

		const mockCart = {
			cartItems: [
				{
					id: 456,
				},
			],
			id: 123,
		};

		fetch
			.mockResponseOnce(JSON.stringify(mockCart))
			.mockResponseOnce(JSON.stringify(mockCart));

		const response = await marketplaceRest.createCart(product);

		const [fetchURL, fetchCall] = fetch.mock.calls[0];
		const [fetchURL2, fetchCall2] = fetch.mock.calls[1];

		expect(fetchURL).toBe(
			`${marketplaceConfiguration.url}/o/headless-commerce-delivery-cart/v1.0/channels/${marketplaceConfiguration.settings.channelId}/carts?nestedFields=cartItems`
		);
		expect(fetchCall.headers['Authorization']).toBe(
			`Bearer ${marketplaceToken.accessToken}`
		);
		expect(fetchCall.body).toContain(product.skus[0].id);
		expect(fetchURL2).toBe(
			`${marketplaceConfiguration.url}/o/headless-commerce-delivery-cart/v1.0/carts/${mockCart.id}/checkout?nestedFields=cartItems`
		);
		expect(fetchCall2.headers['Authorization']).toBe(
			`Bearer ${marketplaceToken.accessToken}`
		);
		expect(response).toMatchObject(mockCart);
	});

	it('testing safe json parse', () => {
		const jsonString = '{"test": "success"}';
		const safeJson = safeJSONParse(jsonString);

		expect(safeJson).toMatchObject({test: 'success'});

		const invalidJsonString = '{"test": "test"';
		const invalidSafeJson = safeJSONParse(invalidJsonString);

		expect(invalidSafeJson).toBeNull();
	});

	it('throws an error when fetch fails with a non-OK response', async () => {
		const {fetch} = require('frontend-js-web');

		fetch.mockResponse(JSON.stringify({message: 'Error occurred'}), {
			status: 401,
		});

		await expect(
			marketplaceRest.fetchMarketplace('/dxp/test')
		).rejects.toThrowError('An error occurred while fetching the data.');

		expect(fetch).toHaveBeenCalledTimes(1);

		getItemFn = jest.fn().mockImplementation(() => null);

		globalThis.Liferay.Util.SessionStorage.getItem = getItemFn;

		await expect(
			marketplaceRest.getMarketplaceToken()
		).rejects.toThrowError('An error occurred while fetching the data');

		const [fetchURL] = fetch.mock.calls[1];

		expect(fetchURL).toContain(
			encodeURIComponent('/marketplace_settings/get_authorization')
		);

		expect(fetch).toHaveBeenCalledTimes(2);
	});

	it('fetching placed orders', async () => {
		const {fetch} = require('frontend-js-web');

		const mockResponse = {success: true};

		fetch.mockResponseOnce(JSON.stringify(mockResponse));

		const searchParams = 'id=123';

		const response = await marketplaceRest.getPlacedOrders(
			new URLSearchParams(searchParams)
		);

		const [fetchURL] = fetch.mock.calls[0];

		expect(fetchURL).toBe(
			`${marketplaceConfiguration.url}/o/headless-commerce-delivery-order/v1.0/channels/123/accounts/123/placed-orders?${searchParams}`
		);
		expect(response).toMatchObject(mockResponse);
	});

	it('fetching placed orders without search parameters', async () => {
		const {fetch} = require('frontend-js-web');

		const mockResponse = {success: true};

		fetch.mockResponseOnce(JSON.stringify(mockResponse));

		const responseWithoutParams = await marketplaceRest.getPlacedOrders();
		const [fetchURL] = fetch.mock.calls[0];

		expect(fetchURL).toBe(
			`${marketplaceConfiguration.url}/o/headless-commerce-delivery-order/v1.0/channels/123/accounts/123/placed-orders?`
		);
		expect(responseWithoutParams).toMatchObject(mockResponse);
	});

	it('fetching project usage', async () => {
		const {fetch} = require('frontend-js-web');

		const mockResponse = {success: true};

		fetch.mockResponseOnce(JSON.stringify(mockResponse));

		const response = await marketplaceRest.getProjectUsage();

		expect(response).toMatchObject(mockResponse);
	});
});
