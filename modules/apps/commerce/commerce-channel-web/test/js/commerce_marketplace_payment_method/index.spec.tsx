/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import mockFetch from 'jest-fetch-mock';
import React from 'react';
import {act} from 'react-dom/test-utils';

import '@testing-library/jest-dom/extend-expect';

import CommerceChannelAddPaymentMethod from '../../../src/main/resources/META-INF/resources/js/commerce_marketplace_payment_method';
import {marketplaceSettingsMock} from '../__mock__/marketplaceSettings';
import placedOrders from '../__mock__/placedOrderresponse';
import {productResponseMock} from '../__mock__/product';
import projectsMockResponse from '../__mock__/projetctMockResponse';

const cartResponseMock = {
	cartItems: [
		{
			id: 123,
		},
	],
	id: 123,
};

const marketplaceToken = {
	accessToken: 'abcdef',
	accessTokenExpirationTime: (new Date().getTime() + 60000).toString(),
};

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	createResourceURL: jest.fn((baseURL, params) => ({
		href: `${baseURL}?${new URLSearchParams(params).toString()}`,
		toString: () => `${baseURL}?${new URLSearchParams(params).toString()}`,
	})),
	fetch: mockFetch,
}));

describe('CommerceMarketplacePaymentMethod', () => {
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

		jest.useFakeTimers();
	});

	afterEach(() => {
		cleanup();

		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('renders unauthorized and click add button', () => {
		fetch.mockResponseOnce(JSON.stringify({authorized: false, data: null}));

		const {queryByRole, queryByText} = render(
			<CommerceChannelAddPaymentMethod />
		);
		const marketplaceIcon = queryByRole('presentation');

		expect(marketplaceIcon).toBeInTheDocument();
		expect(marketplaceIcon).toBeVisible();
		expect(marketplaceIcon).toHaveClass('lexicon-icon-marketplace');

		fireEvent.click(queryByText('add') as HTMLButtonElement);

		act(() => jest.runAllTimers());

		expect(queryByText('connection-with-marketplace-needed')).toBeTruthy();
		expect(queryByText('go-to-instance-settings')).toBeTruthy();
		expect(
			queryByText(
				'you-are-trying-to-add-a-new-payment-method-through-the-marketplace,-but-the-connection-has-not-been-established-yet'
			)
		).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(1);
	});

	it('renders modal connection with markeplace needed', () => {
		fetch.mockResponseOnce(JSON.stringify({authorized: false, data: null}));

		const {getByRole, queryByText} = render(
			<CommerceChannelAddPaymentMethod />
		);
		const marketplaceIcon = getByRole('presentation');

		fireEvent.click(queryByText('add') as HTMLButtonElement);

		act(() => jest.runAllTimers());

		expect(marketplaceIcon).toBeInTheDocument();
		expect(marketplaceIcon).toBeVisible();
		expect(marketplaceIcon).toHaveClass('lexicon-icon-marketplace');
		expect(queryByText('connection-with-marketplace-needed')).toBeTruthy();
		expect(queryByText('go-to-instance-settings')).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(1);
	});

	it('renders unauthorized and click on go to instance settings button', () => {
		fetch.mockResponseOnce(JSON.stringify({authorized: false, data: null}));

		const {queryByText} = render(<CommerceChannelAddPaymentMethod />);

		fireEvent.click(queryByText('add') as HTMLButtonElement);
		act(() => jest.runAllTimers());

		const instanceSettingsButton = queryByText('go-to-instance-settings');

		expect(instanceSettingsButton).toBeInTheDocument();
		expect(fetch.mock.calls.length).toBe(1);
	});

	it('renders aunthorized without apps', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(marketplaceSettingsMock))
			.mockResponseOnce(
				JSON.stringify({...productResponseMock, items: []})
			)
			.mockResponseOnce(JSON.stringify(placedOrders))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse));

		const {queryByText} = await render(<CommerceChannelAddPaymentMethod />);
		const addButton = queryByText('add');

		fireEvent.click(addButton as HTMLButtonElement);

		await act(async () => jest.runAllTimers());

		expect(queryByText('no-results-were-found')).toBeTruthy();
		expect(queryByText('no-products-were-found')).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(6);
	});

	it('renders aunthorized with apps and click install without cloud projects', async () => {
		fetch
			.mockResponseOnce(
				JSON.stringify({
					...marketplaceSettingsMock,
					data: {
						...marketplaceSettingsMock.data,
						settings: {
							...marketplaceSettingsMock.data.settings,
							cloudProject: null,
						},
					},
				})
			)
			.mockResponseOnce(JSON.stringify(productResponseMock))
			.mockResponseOnce(JSON.stringify(placedOrders));

		const {queryByText} = render(<CommerceChannelAddPaymentMethod />);
		const addButton = queryByText('add');

		fireEvent.click(addButton as HTMLButtonElement);

		await act(async () => jest.runAllTimers());

		act(() => expect(queryByText('install')).toBeTruthy());

		await act(async () =>
			fireEvent.click(queryByText('install') as HTMLButtonElement)
		);

		expect(queryByText('no-cloud-project-available')).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(3);
	});

	it('renders authorized with apps and click on app card', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(marketplaceSettingsMock))
			.mockResponseOnce(JSON.stringify(productResponseMock))
			.mockResponseOnce(JSON.stringify(placedOrders))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse));

		const {queryByText} = await render(<CommerceChannelAddPaymentMethod />);

		fireEvent.click(queryByText('add') as HTMLButtonElement);

		await act(async () => jest.runAllTimers());

		expect(queryByText(productResponseMock.items[0].name)).toBeTruthy();

		fireEvent.click(
			queryByText(productResponseMock.items[0].name) as HTMLButtonElement
		);

		expect(queryByText('back-to-list')).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(6);
	});

	it('renders authorized with apps and click on cancel button', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(marketplaceSettingsMock))
			.mockResponseOnce(JSON.stringify(productResponseMock))
			.mockResponseOnce(JSON.stringify(placedOrders))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse));

		const {queryByText} = await render(<CommerceChannelAddPaymentMethod />);

		fireEvent.click(queryByText('add') as HTMLButtonElement);
		await act(async () => jest.runAllTimers());

		expect(queryByText(productResponseMock.items[0].name)).toBeTruthy();

		await act(async () =>
			fireEvent.click(queryByText('install') as HTMLButtonElement)
		);
		await act(async () =>
			fireEvent.click(queryByText('cancel') as HTMLButtonElement)
		);

		expect(queryByText(productResponseMock.items[0].name)).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(7);
	});

	it('renders authorized with apps and click install failed', async () => {
		fetch
			.mockResponseOnce(JSON.stringify(marketplaceSettingsMock))
			.mockResponseOnce(JSON.stringify(productResponseMock))
			.mockResponseOnce(JSON.stringify(placedOrders))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(cartResponseMock))
			.mockRejectOnce(JSON.stringify({ok: false}));

		const {queryByText} = render(<CommerceChannelAddPaymentMethod />);

		fireEvent.click(queryByText('add') as HTMLButtonElement);

		await act(async () => jest.runAllTimers());

		fireEvent.click(queryByText('My App') as HTMLButtonElement);

		await act(async () =>
			fireEvent.click(queryByText('install') as HTMLButtonElement)
		);

		expect(queryByText('confirmation-required')).toBeTruthy();

		await act(async () =>
			fireEvent.click(queryByText('install') as HTMLButtonElement)
		);

		expect(queryByText('there-was-an-unknown-error')).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(9);
	});

	it('render no resources', async () => {
		const noResources = {
			...projectsMockResponse,
			rootProjectPlanUsage: {
				cpu: {
					free: 0,
					limit: 0,
					used: 0,
				},
				instance: {
					free: 0,
					limit: 0,
					used: 0,
				},
				memory: {
					free: 0,
					limit: 0,
					used: 0,
				},
			},
		};

		fetch
			.mockResponseOnce(JSON.stringify(marketplaceSettingsMock))
			.mockResponseOnce(JSON.stringify(productResponseMock))
			.mockResponseOnce(JSON.stringify(placedOrders))
			.mockResponseOnce(JSON.stringify(noResources))
			.mockResponseOnce(JSON.stringify(noResources))
			.mockResponseOnce(JSON.stringify(noResources))
			.mockResponseOnce(JSON.stringify(noResources))
			.mockResponseOnce(JSON.stringify(placedOrders))
			.mockResponseOnce(JSON.stringify(cartResponseMock))
			.mockResponseOnce(JSON.stringify(cartResponseMock))
			.mockResponseOnce(JSON.stringify({success: true}));

		const {queryByText} = render(<CommerceChannelAddPaymentMethod />);

		fireEvent.click(queryByText('add') as HTMLButtonElement);

		await act(async () => jest.runAllTimers());
		await act(async () => expect(queryByText('install')).toBeTruthy());
		await act(async () =>
			fireEvent.click(queryByText('install') as HTMLButtonElement)
		);

		expect(queryByText('insufficient-resources')).toBeTruthy();
		expect(fetch.mock.calls.length).toBe(7);
	});

	it('renders success on instalation', async () => {
		const placedOrderResponse = JSON.stringify({
			...placedOrders,
			items: placedOrders.items.map((item) => ({
				...item,
				customFields: {
					...item.customFields,
					'cloud-provisioning': '',
				},
				id: 999999,
				placedOrderItems: item.placedOrderItems.map((orderItem) => ({
					...orderItem,
					customFields: {
						...orderItem.customFields,
						'cloud-provisioning': '',
					},
					id: 999999,
					productId: 999999,
				})),
			})),
		});

		fetch
			.mockResponseOnce(JSON.stringify(marketplaceSettingsMock))
			.mockResponseOnce(JSON.stringify(productResponseMock))
			.mockResponseOnce(placedOrderResponse)
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(placedOrderResponse)
			.mockResponseOnce(JSON.stringify(cartResponseMock))
			.mockResponseOnce(JSON.stringify(cartResponseMock))
			.mockResponseOnce(JSON.stringify({success: true}));

		const {queryByText} = render(<CommerceChannelAddPaymentMethod />);

		fireEvent.click(queryByText('add') as HTMLButtonElement);

		await act(async () => jest.runAllTimers());

		fireEvent.click(queryByText('My App') as HTMLButtonElement);

		await act(() =>
			fireEvent.click(queryByText('install') as HTMLButtonElement)
		);
		await act(() =>
			fireEvent.click(queryByText('install') as HTMLButtonElement)
		);

		expect(queryByText('success')).toBeTruthy();
		expect(
			queryByText(
				'your-application-has-been-installed,-wait-a-few-moments-for-it-to-become-available'
			)
		).toBeTruthy();

		expect(fetch.mock.calls.length).toBe(10);
	});

	it('render disabled button on apps and storefront', async () => {
		const placedOrderResponse = {
			...placedOrders,
			items: placedOrders.items.map((item) => ({
				...item,

				id: 999999,
				placedOrderItems: item.placedOrderItems.map((orderItem) => ({
					...orderItem,
					id: 999999,
					productId: 999999,
				})),
			})),
		};

		fetch
			.mockResponseOnce(JSON.stringify(marketplaceSettingsMock))
			.mockResponseOnce(JSON.stringify(productResponseMock))
			.mockResponseOnce(JSON.stringify(placedOrderResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(projectsMockResponse))
			.mockResponseOnce(JSON.stringify(placedOrderResponse))
			.mockResponseOnce(JSON.stringify(cartResponseMock))
			.mockResponseOnce(JSON.stringify(cartResponseMock))
			.mockResponseOnce(JSON.stringify({ok: true}));

		const {queryByText} = render(<CommerceChannelAddPaymentMethod />);

		fireEvent.click(queryByText('add') as HTMLButtonElement);

		await act(async () => jest.runAllTimers());

		fireEvent.click(queryByText('My App') as HTMLButtonElement);

		await act(async () => jest.runAllTimers());

		expect(queryByText('install')).toBeDisabled();
		expect(fetch.mock.calls.length).toBe(7);
	});
});
