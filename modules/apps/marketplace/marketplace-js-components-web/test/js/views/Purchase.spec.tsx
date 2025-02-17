/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React from 'react';

import {MarketplaceContext} from '../../../src/main/resources/META-INF/resources/js/MarketplaceContext';
import {MarketplaceProduct} from '../../../src/main/resources/META-INF/resources/js/core/MarketplaceProduct';
import {MarketplacePurchase} from '../../../src/main/resources/META-INF/resources/js/views/Purchase';
import product from '../__mock__/product';

const marketplaceProduct = new MarketplaceProduct(product);

const {LATEST_VERSION} = marketplaceProduct.specificationValues;

describe('MarketplacePurchase', () => {
	it('rendering components with its props', async () => {
		const {queryByText} = render(
			<MarketplaceContext.Provider value={marketplaceProduct as any}>
				<MarketplacePurchase rightTitle="Right Title">
					children
				</MarketplacePurchase>
			</MarketplaceContext.Provider>
		);

		expect(queryByText('0CPUs, 0GB RAM')).toBeInTheDocument();
		expect(queryByText('children')).toBeInTheDocument();
		expect(queryByText('Free')).toBeInTheDocument();
		expect(queryByText('project-name')).toBeInTheDocument();
		expect(queryByText('Right Title')).toBeInTheDocument();
		expect(
			queryByText(`${LATEST_VERSION} by ${product.catalogName}`)
		).toBeInTheDocument();
		expect(queryByText(product.name)).toBeInTheDocument();
	});

	it('rendering component without product specifications', () => {
		product.productSpecifications = [];

		const productWihtoutSpecifications = new MarketplaceProduct(
			product
		);

		const {queryByText} = render(
			<MarketplaceContext.Provider
				value={productWihtoutSpecifications as any}
			>
				<MarketplacePurchase rightTitle="Right Title">
					children
				</MarketplacePurchase>
			</MarketplaceContext.Provider>
		);

		expect(
			queryByText(`${LATEST_VERSION} by ${product.catalogName}`)
		).toBeFalsy();
	});
});
