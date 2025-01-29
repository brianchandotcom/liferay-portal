/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

import {MarketplaceProduct} from '../core/MarketplaceProduct';
import {Product} from '../types';
import {sanitizeHTML} from '../util';

export type ProductCardProps = {
	children?: ReactNode;
	onClick: (product: Product) => void;
	product: Product;
};

export function ProductCard({children, onClick, product}: ProductCardProps) {
	const marketplaceProduct = new MarketplaceProduct(product);

	const categories = marketplaceProduct.getAppCategories();

	return (
		<div className="border-radius-medium d-flex flex-column justify-content-between marketplace-search-results-card mb-0 text-dark text-decoration-none">
			<div
				className="d-flex flex-column font-size-paragraph-small h-100 justify-content-between marketplace-search-results-card-content"
				onClick={() => onClick(product)}
			>
				<div>
					<div className="align-items-center card-image-title-container d-flex mb-4">
						<div className="image-container mr-2 rounded">
							<img
								className="marketplace-search-results-card-image object-fit-contain"
								draggable={false}
								src={marketplaceProduct.productImage}
							/>
						</div>

						<div>
							<div className="marketplace-search-results-card-title">
								{product?.name}
							</div>

							<small className="marketplace-search-results-card-subtitle">
								{product?.catalogName}
							</small>
						</div>
					</div>

					<span
						className="marketplace-search-results-card-description"
						dangerouslySetInnerHTML={{
							__html: sanitizeHTML(product?.description),
						}}
					/>
				</div>

				<div>
					<span className="font-weight-bold">
						{marketplaceProduct.getPriceModel()}
					</span>

					<div className="d-flex marketplace-search-results-card-category my-2">
						{!!categories?.length && (
							<>
								<span className="marketplace-search-results-card-tags">
									{categories[0]?.name}
								</span>

								{categories.length > 1 && (
									<span className="marketplace-search-results-card-tags">
										{`+ ${categories.length - 1}`}
									</span>
								)}
							</>
						)}
					</div>
				</div>
			</div>

			{children}
		</div>
	);
}
