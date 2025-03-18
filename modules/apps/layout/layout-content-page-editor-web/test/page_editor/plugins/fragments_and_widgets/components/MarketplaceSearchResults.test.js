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

const mockOpenChange = jest.fn();

jest.mock('@liferay/layout-js-components-web', () => {
	const {MarketplaceContext} = jest.requireActual(
		'@liferay/marketplace-js-components-web'
	);

	return {
		...jest.requireActual('@liferay/layout-js-components-web'),
		MarketplaceModal: ({onOpenChange = mockOpenChange, trigger}) => (
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
			expect(screen.getByText('showing-x-x')).toBeInTheDocument();
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
			expect(screen.getAllByText('no-results-found')).toHaveLength(2);
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
		const {container} = renderMarketplaceSearchResults({});

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
			expect(screen.getAllByRole('menuitem').length).toBe(2);
			expect(screen.getByText('Product 1')).toBeInTheDocument();
			expect(screen.getByText('Product 2')).toBeInTheDocument();

			mockMarketplaceInstance.getProducts.mockResolvedValue({
				items: [getProduct(3), getProduct(4)],
				lastPage: 2,
				page: 2,
			});

			fireEvent.click(loadMoreResultsButton);

			expect(
				container.getElementsByClassName('loading-animation').length
			).toBe(1);
			expect(screen.getByText('Product 1')).toBeInTheDocument();
			expect(screen.getByText('Product 2')).toBeInTheDocument();
		});

		await waitFor(() => {
			expect(
				screen.queryByRole('button', {name: 'load-more-results'})
			).not.toBeInTheDocument();
			expect(mockMarketplaceInstance.getProducts).toHaveBeenCalledTimes(
				2
			);

			expect(screen.getAllByRole('menuitem').length).toBe(4);
			expect(screen.getByText('Product 1')).toBeInTheDocument();
			expect(screen.getByText('Product 2')).toBeInTheDocument();
			expect(screen.getByText('Product 3')).toBeInTheDocument();
			expect(screen.getByText('Product 4')).toBeInTheDocument();
		});
	});

	it('focuses the first item only on initial load and handles keyboard navigation', async () => {
		renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			expect(screen.getAllByRole('menubar').length).toBe(1);
			const menuItems = screen.getAllByRole('menuitem');
			expect(menuItems.length).toBe(2);
			expect(menuItems[0]).toHaveFocus();
			fireEvent.keyDown(menuItems[0], {code: 'ArrowDown'});
			expect(menuItems[1]).toHaveFocus();

			mockMarketplaceInstance.getProducts.mockResolvedValue({
				items: [getProduct(3), getProduct(4)],
				lastPage: 2,
				page: 2,
			});

			fireEvent.click(
				screen.getByRole('button', {name: 'load-more-results'})
			);
		});

		await waitFor(() => {
			const menuItems = screen.getAllByRole('menuitem');
			expect(menuItems.length).toBe(4);

			expect(menuItems[0]).not.toHaveFocus();
			expect(menuItems[1]).toHaveFocus();

			fireEvent.keyDown(menuItems[1], {code: 'ArrowDown'});
			expect(menuItems[2]).toHaveFocus();
			fireEvent.keyDown(menuItems[2], {code: 'ArrowDown'});
			expect(menuItems[3]).toHaveFocus();
			fireEvent.keyDown(menuItems[3], {code: 'ArrowUp'});
			expect(menuItems[2]).toHaveFocus();
		});
	});

	it('triggers modal on enter key press', async () => {
		renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			const menuItems = screen.getAllByRole('menuitem');
			fireEvent.keyDown(menuItems[0], {key: 'Enter'});
			expect(mockOpenChange).toHaveBeenCalledWith(true);
		});
	});

	it('triggers modal on space key press', async () => {
		renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			const menuItems = screen.getAllByRole('menuitem');
			fireEvent.keyDown(menuItems[0], {key: 'Space'});
			expect(mockOpenChange).toHaveBeenCalledWith(true);
		});
	});

	it('displays error message in console when API call fails', async () => {
		mockMarketplaceInstance.getProducts.mockRejectedValue(
			new Error('API Error!')
		);

		console.error = jest.fn();

		renderMarketplaceSearchResults({});

		fireEvent.click(
			screen.getByRole('button', {name: 'see-marketplace-results'})
		);

		await waitFor(() => {
			expect(console.error).toHaveBeenCalledWith(
				'Failed to fetch products:',
				expect.any(Error)
			);
		});

		console.error.mockRestore();
	});
});
