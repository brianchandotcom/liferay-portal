/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

import {useMarketplaceContext} from '../MarketplaceContext';
import ProductPurchaseHeader from '../components/ProductPurchase/Header';
import {MarketplaceProduct} from '../core/MarketplaceProduct';

type MarketplacePurchaseProps = {
	children: ReactNode;
	rightTitle: string;
};

export function MarketplacePurchase({
	children,
	rightTitle,
}: MarketplacePurchaseProps) {
	const {product} = useMarketplaceContext();

	const marketplaceProduct = new MarketplaceProduct(product);

	const {LATEST_VERSION} = marketplaceProduct.specificationValues;

	return (
		<div className="marketplace-purchase">
			<div className="bg-light border d-flex flex-column m-4 rounded-lg">
				<ProductPurchaseHeader
					image={product?.urlImage}
					rightNode={
						<div className="align-items-end d-flex flex-column price-text">
							<strong className="mr-1">
								{marketplaceProduct.getPrice()}
							</strong>

							<small className="px-2">
								{marketplaceProduct.getProductResourceLabel()}
							</small>
						</div>
					}
					subsectionTitleLeft={Liferay.Language.get('project-name')}
					subsectionTitleRight={rightTitle}
					subtitle={
						LATEST_VERSION
							? `${LATEST_VERSION} ${Liferay.Language.get('by')} ${marketplaceProduct.catalogName} `
							: marketplaceProduct.catalogName
					}
					title={product.name}
				/>
			</div>

			{children}
		</div>
	);
}
