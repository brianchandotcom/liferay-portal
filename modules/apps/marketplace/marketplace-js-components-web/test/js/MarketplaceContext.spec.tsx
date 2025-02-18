/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import mockFetch from 'jest-fetch-mock';
import React from 'react';
import {act} from 'react-dom/test-utils';

import {
	MarketplaceContextProvider,
	MarketplaceView,
	useMarketplaceContext,
} from '../../src/main/resources/META-INF/resources/js/MarketplaceContext';
import {useMarketplaceConfiguration} from '../../src/main/resources/META-INF/resources/js/hooks/useMarketplaceConfiguration';
import productResponse from './__mock__/productResponse';

jest.mock(
	'../../src/main/resources/META-INF/resources/js/hooks/useMarketplaceConfiguration'
);

jest.mock('frontend-js-web', () => {
	const actual = jest.requireActual('frontend-js-web');

	return {
		...actual,
		fetch: mockFetch,
	};
});

jest.mock(
	'../../src/main/resources/META-INF/resources/js/core/MarketplaceRest',
	() => ({
		MarketplaceRest: jest.fn().mockImplementation(() => ({
			getProducts: jest.fn().mockResolvedValue({items: []}),
			setProductsResponse: jest.fn(),
			settings: {
				references: {
					fragmentsFilter: 'fragment filter',
					paymentMethodFilter: 'payment method filter',
				},
			},
		})),
	})
);

const baseResourceURL = 'localhost:8080/marketplace.liferay.com';

const Children: React.FC<any> = () => {
	const context = useMarketplaceContext();

	return (
		<div>
			<p>
				{context.marketplaceRest.settings.references?.fragmentsFilter}
			</p>

			<p>
				{
					context.marketplaceRest.settings.references
						?.paymentMethodFilter
				}
			</p>

			<p data-testid="authorized">
				{`Authorized: ${context.marketplaceConfiguration.authorized ? 'true' : 'false'}`}
			</p>

			<p data-testid="view">{context.view}</p>

			<button onClick={() => context.setView(MarketplaceView.PURCHASE)}>
				Change View
			</button>
		</div>
	);
};

describe('MarketplaceContext', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	afterEach(() => {
		cleanup();

		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	beforeEach(() => {
		cleanup();

		jest.useFakeTimers();
	});

	(useMarketplaceConfiguration as jest.Mock).mockReturnValue({
		authorized: true,
		data: productResponse,
		loading: false,
	});

	const marketplaceConfiguration =
		useMarketplaceConfiguration('baseResourceURL');

	it('testing Marketplace Context Provider with custom filter', async () => {
		const {queryByText} = render(
			<MarketplaceContextProvider
				baseResourceURL={baseResourceURL}
				settings={{
					productFilter: 'all',
					productFilterCustom: 'custom filter',
				}}
			>
				<Children />
			</MarketplaceContextProvider>
		);

		await act(async () => jest.runAllTimers());

		expect(queryByText('custom filter')).toBeTruthy;
	});

	it('testing Marketplace Context Provider with fragments filter', async () => {
		const {
			MarketplaceRest,
		} = require('../../src/main/resources/META-INF/resources/js/core/MarketplaceRest');

		MarketplaceRest.mockImplementation(() => ({
			getProducts: jest.fn().mockResolvedValue({items: ['fragments']}),
			setProductsResponse: jest.fn(),
			settings: {
				references: {
					fragmentsFilter: 'fragment filter',
				},
			},
		}));

		const {queryByText} = render(
			<MarketplaceContextProvider
				baseResourceURL={baseResourceURL}
				settings={{
					productFilter: 'fragments',
					productFilterCustom: '',
				}}
			>
				<Children />
			</MarketplaceContextProvider>
		);

		await act(async () => jest.runAllTimers());

		expect(queryByText('fragment filter')).toBeTruthy();
	});

	it('testing Marketplace Context Provider with payment method filter', async () => {
		const {
			MarketplaceRest,
		} = require('../../src/main/resources/META-INF/resources/js/core/MarketplaceRest');

		MarketplaceRest.mockImplementation(() => ({
			getProducts: jest.fn().mockResolvedValue({items: ['payments']}),
			setProductsResponse: jest.fn(),
			settings: {
				productFilterCustom: 'payments',
				references: {
					paymentMethodFilter: 'payment method filter',
				},
			},
		}));

		const {queryByText} = render(
			<MarketplaceContextProvider
				baseResourceURL={baseResourceURL}
				settings={{
					productFilter: 'payments',
					productFilterCustom: '',
				}}
			>
				<Children />
			</MarketplaceContextProvider>
		);

		await act(async () => jest.runAllTimers());

		expect(queryByText('payment method filter')).toBeTruthy();
	});

	it('testing Marketplace Context Provider without custom filter', async () => {
		const {
			MarketplaceRest,
		} = require('../../src/main/resources/META-INF/resources/js/core/MarketplaceRest');

		MarketplaceRest.mockImplementation(() => ({
			getProducts: jest.fn().mockResolvedValue({items: ['']}),
			setProductsResponse: jest.fn(),
			settings: {
				references: {},
			},
		}));

		const {queryByText} = render(
			<MarketplaceContextProvider
				baseResourceURL={baseResourceURL}
				settings={{
					productFilter: 'all',
					productFilterCustom: '',
				}}
			>
				<Children />
			</MarketplaceContextProvider>
		);
		await act(async () => jest.runAllTimers());

		expect(queryByText('fragment filter')).toBeFalsy();
		expect(queryByText('payment method filter')).toBeFalsy();
	});

	it('will handle API error correctly', async () => {
		const {
			MarketplaceRest,
		} = require('../../src/main/resources/META-INF/resources/js/core/MarketplaceRest');

		MarketplaceRest.mockImplementation(() => ({
			getProducts: jest.fn().mockRejectedValue(new Error('API Error')),
			setProductsResponse: jest.fn(),
			settings: {
				productFilterCustom: 'custom filter',
				references: {},
			},
		}));

		const consoleErrorMock = jest
			.spyOn(console, 'error')
			.mockImplementation(() => {});

		render(
			<MarketplaceContextProvider
				baseResourceURL={baseResourceURL}
				settings={{
					productFilter: 'all',
					productFilterCustom: 'custom filter',
				}}
			>
				<Children />
			</MarketplaceContextProvider>
		);

		await act(async () => jest.runAllTimers());

		expect(consoleErrorMock).toHaveBeenCalled();

		consoleErrorMock.mockRestore();

		jest.resetModules();
	});

	it('testing Marketplace Context Provider when not authorized', async () => {
		marketplaceConfiguration.authorized = false;

		const {queryByText} = render(
			<MarketplaceContextProvider
				baseResourceURL={baseResourceURL}
				settings={{
					productFilter: 'all',
					productFilterCustom: 'custom filter',
				}}
			>
				<Children />
			</MarketplaceContextProvider>
		);
		await act(async () => jest.runAllTimers());

		expect(queryByText('Authorized: false')).toBeTruthy();
	});
});
