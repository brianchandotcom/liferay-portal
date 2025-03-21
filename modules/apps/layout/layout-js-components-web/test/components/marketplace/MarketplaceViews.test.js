/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {MarketplaceView} from '@liferay/marketplace-js-components-web';

import MarketplaceViews from '../../../src/main/resources/META-INF/resources/js/components/marketplace/MarketplaceViews';

const mockUseMarketplaceContext = {
	marketplaceRest: {
		checkoutCart: jest.fn(() => Promise.resolve()),
		createCart: jest.fn(() => Promise.resolve({id: 'test-cart-id'})),
		fetchMarketplace: jest.fn(() =>
			Promise.resolve({blob: () => new Blob(['test'])})
		),
	},
	modal: {onOpenChange: jest.fn()},
	product: {
		attachments: [{src: 'http://example.com/test.zip'}],
		name: 'Test Product',
	},
	setProduct: jest.fn(),
	setView: jest.fn(),
	view: MarketplaceView.PRODUCTS,
};

jest.mock('@liferay/marketplace-js-components-web', () => {
	const actualModule = jest.requireActual(
		'@liferay/marketplace-js-components-web'
	);

	return {
		...actualModule,
		Marketplace: {
			Products: ({children}) => {
				return (
					<div data-testid="mock-marketplace-products">
						{children({
							attachments: [{src: 'http://example.com/test.zip'}],
							name: 'Test Product',
						})}
					</div>
				);
			},
			Storefront: ({primaryButton}) => (
				<div data-testid="mock-marketplace-storefront">
					{primaryButton}
				</div>
			),
		},
		useMarketplaceContext: jest.fn(() => mockUseMarketplaceContext),
	};
});

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/components/import/importZipFile',
	() => jest.fn(() => Promise.resolve())
);

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/components/marketplace/InstallFragmentModal',
	() => ({
		InstallFragmentModalBody: () => (
			<div data-testid="mock-install-fragment-modal-body">
				Install Fragment Modal Body
			</div>
		),
	})
);
const mockProps = {
	fragmentPortletNamespace: 'testNamespace',
	fragmentsImportURL: '/testImportURL',
};

const renderComponent = (props = mockProps) =>
	render(<MarketplaceViews {...props} />);

describe('MarketplaceViews', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('renders products view correctly', () => {
		renderComponent();

		expect(
			screen.getByTestId('mock-marketplace-products')
		).toBeInTheDocument();

		expect(screen.getByText('install')).toBeInTheDocument();
	});

	it('handles product installation', async () => {
		renderComponent();

		userEvent.click(screen.getByText('install'));

		await waitFor(() => {
			expect(mockUseMarketplaceContext.setView).toHaveBeenCalledWith(
				MarketplaceView.PURCHASE
			);

			expect(mockUseMarketplaceContext.setProduct).toHaveBeenCalled();

			expect(
				mockUseMarketplaceContext.marketplaceRest.createCart
			).toHaveBeenCalled();

			expect(
				mockUseMarketplaceContext.marketplaceRest.checkoutCart
			).toHaveBeenCalled();

			expect(
				require('../../../src/main/resources/META-INF/resources/js/components/import/importZipFile')
			).toHaveBeenCalled();

			expect(
				require('frontend-js-components-web').openToast
			).toHaveBeenCalledWith(expect.objectContaining({type: 'success'}));
		});
	});

	it('renders storefront view correctly', async () => {
		require('@liferay/marketplace-js-components-web').useMarketplaceContext.mockReturnValue(
			{
				...mockUseMarketplaceContext,
				view: MarketplaceView.STOREFRONT,
			}
		);

		renderComponent();

		expect(
			screen.getByTestId('mock-marketplace-storefront')
		).toBeInTheDocument();

		userEvent.click(screen.getByText('install'));

		await waitFor(() => {
			expect(
				require('../../../src/main/resources/META-INF/resources/js/components/import/importZipFile')
			).toHaveBeenCalled();
		});
	});

	it('renders purchase view correctly', () => {
		require('@liferay/marketplace-js-components-web').useMarketplaceContext.mockReturnValue(
			{
				...mockUseMarketplaceContext,
				view: MarketplaceView.PURCHASE,
			}
		);

		renderComponent();

		expect(
			screen.getByTestId('mock-install-fragment-modal-body')
		).toBeInTheDocument();
	});

	it('handles installation error', async () => {
		const mockContext = {
			...mockUseMarketplaceContext,
			marketplaceRest: {
				checkoutCart: jest.fn(() => Promise.resolve()),
				createCart: jest.fn(() =>
					Promise.reject(new Error('Installation failed'))
				),
				fetchMarketplace: jest.fn(() =>
					Promise.resolve({blob: () => new Blob(['test'])})
				),
			},
		};
		require('@liferay/marketplace-js-components-web').useMarketplaceContext.mockReturnValue(
			mockContext
		);

		renderComponent();

		userEvent.click(screen.getByText('install'));

		await waitFor(() => {
			expect(
				require('frontend-js-components-web').openToast
			).toHaveBeenCalledWith(expect.objectContaining({type: 'danger'}));

			expect(mockContext.modal.onOpenChange).toHaveBeenCalledWith(false);
		});
	});
});
