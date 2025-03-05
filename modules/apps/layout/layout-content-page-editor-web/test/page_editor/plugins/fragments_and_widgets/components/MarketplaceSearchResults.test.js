/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {
	MarketplaceRest,
	useMarketplaceConfiguration,
} from '@liferay/marketplace-js-components-web';

import MarketplaceSearchResults from '../../../../../src/main/resources/META-INF/resources/page_editor/plugins/fragments_and_widgets/components/MarketplaceSearchResults';

global.Liferay = {
	FeatureFlags: {'LPD-34938': true},
	Language: {get: (key) => key},
	ThemeDisplay: {getPathThemeImages: jest.fn()},
};

jest.mock('@liferay/marketplace-js-components-web', () => {
	const actual = jest.requireActual('@liferay/marketplace-js-components-web');
	const mockGetProducts = {
		getProducts: jest.fn(),
	};
	const mockMarketplaceRest = jest.fn(() => mockGetProducts);
	mockMarketplaceRest.getBaseResourceURL = jest.fn(() => 'mocked-base-url');

	return {
		...actual,
		MarketplaceContext: {
			Provider: ({children, value}) => (
				<actual.MarketplaceContext.Provider value={value}>
					{children}
				</actual.MarketplaceContext.Provider>
			),
		},
		MarketplaceRest: mockMarketplaceRest,
		useMarketplaceConfiguration: jest.fn(),
	};
});

jest.mock('@liferay/layout-js-components-web', () => {
	const {MarketplaceContext} = jest.requireActual(
		'@liferay/marketplace-js-components-web'
	);

	return {
		...jest.requireActual('@liferay/layout-js-components-web'),
		MarketplaceModal: ({onOpenChange, trigger}) => (
			<MarketplaceContext.Provider
				value={{
					modal: {onOpenChange},
					setProduct: jest.fn(),
					setView: jest.fn(),
				}}
			>
				{trigger}
			</MarketplaceContext.Provider>
		),
	};
});

const mockMarketplaceConfiguration = {
	authorized: true,
	data: {},
	loading: false,
};

const getProduct = (id) => ({
	catalogName: `Catalog ${id}`,
	id,
	name: `Product ${id}`,
	urlImage: `urlImage${id}`,
});

const mockProducts = {
	items: [getProduct(1), getProduct(2)],
	lastPage: 2,
	page: 1,
};

const components = ({searchValue = 'test'}) => (
	<MarketplaceSearchResults searchValue={searchValue} />
);

function renderMarketplaceSearchResults({
	searchValue = 'test',
	viewMarketplace = true,
}) {
	return render(components({searchValue, viewMarketplace}));
}

describe('MarketplaceSearchResults', () => {
	let mockMarketplaceInstance;

	beforeEach(() => {
		useMarketplaceConfiguration.mockReturnValue(
			mockMarketplaceConfiguration
		);
		mockMarketplaceInstance = new MarketplaceRest();
		mockMarketplaceInstance.getProducts.mockResolvedValue(mockProducts);
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	it('renders "see marketplace results" button when not showing marketplace results', () => {
		renderMarketplaceSearchResults({});

		expect(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		).toBeInTheDocument();
	});

	it('does not render "see marketplace results" if not connected to marketplace', () => {
		mockMarketplaceConfiguration.authorized = false;

		renderMarketplaceSearchResults({});

		expect(
			screen.queryByRole('button', {name: 'see-marketplace-results'})
		).not.toBeInTheDocument();

		mockMarketplaceConfiguration.authorized = true;
	});

	it('fetches and displays marketplace results when button is clicked', async () => {
		const {container} = renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			expect(mockMarketplaceInstance.getProducts).toHaveBeenCalled();
			expect(
				screen.getByText('showing-results-from-marketplace')
			).toBeInTheDocument();
			expect(screen.getAllByTitle(`x-details`).length).toBe(2);

			const expectProduct = (index) => {
				expect(
					screen.getByText(`Product ${index}`)
				).toBeInTheDocument();
				expect(
					screen.getByText(`Catalog ${index}`)
				).toBeInTheDocument();
				const imageElements = screen.getAllByRole('img');
				const urlImage = imageElements.find(
					(image) => image.getAttribute('src') === `urlImage${index}`
				);
				expect(urlImage).toBeInTheDocument();
			};

			expectProduct(1);
			expectProduct(2);

			expect(
				container.getElementsByClassName('lexicon-icon-angle-right')
					.length
			).toBe(2);
		});
	});

	it('hides marketplace search results when searchValue changes', async () => {
		const {rerender} = renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			expect(
				screen.getByText('showing-results-from-marketplace')
			).toBeInTheDocument();
		});

		rerender(components({searchValue: 'test2'}));

		expect(
			screen.queryByText('showing-results-from-marketplace')
		).not.toBeInTheDocument();
	});

	it('displays empty state when no results are found', async () => {
		const emptyProducts = {items: [], lastPage: 1, page: 1};
		mockMarketplaceInstance.getProducts.mockResolvedValueOnce(
			emptyProducts
		);

		renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			expect(screen.getByText('no-results-found')).toBeInTheDocument();
		});
	});

	it('displays loading indicator while fetching results', async () => {
		const {container} = renderMarketplaceSearchResults({loading: true});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			expect(
				container.getElementsByClassName('loading-animation').length
			).toBe(1);
		});
	});

	it('handles "load more results" functionality', async () => {
		renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			const loadMoreResultsButton = screen.getByRole('button', {
				name: 'load-more-results',
			});
			expect(loadMoreResultsButton).toBeInTheDocument();
			expect(mockMarketplaceInstance.getProducts).toHaveBeenCalledTimes(
				1
			);
			mockMarketplaceInstance.getProducts.mockResolvedValue({
				items: [getProduct(3), getProduct(4)],
				lastPage: 2,
				page: 2,
			});
			fireEvent.click(loadMoreResultsButton);
		});

		await waitFor(() => {
			expect(
				screen.queryByRole('button', {name: 'load-more-results'})
			).not.toBeInTheDocument();
			expect(mockMarketplaceInstance.getProducts).toHaveBeenCalledTimes(
				2
			);
		});
	});
});
