/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Product} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';
import Image from 'next/image';
import Link from 'next/link';

import {getSkuDetails, handleImageError} from '../../lib/product';
import {Badge} from '../ui/badge';
import {Card} from '../ui/card';

type ProductListViewProps = {
	product: Product;
};

export default function ProductListView({product}: ProductListViewProps) {
	const productPrice = getSkuDetails(product);

	return (
		<Card className="bg-product-card border-product-border cursor-pointer duration-300 group hover:shadow-sm overflow-hidden transition-all">
			<Link href={`/product/${product.urls?.en_US}`}>
				<div className="bg-white flex gap-4 items-center p-4">
					<div className="flex-shrink-0 h-20 relative w-20">
						<Image
							alt={product.name as string}
							className="h-full object-cover rounded-md w-full"
							height={48}
							onError={handleImageError}
							quality={100}
							src={product.urlImage as string}
							unoptimized
							width={48}
						/>
					</div>

					<div className="flex-1 space-y-2">
						<div className="flex items-start justify-between">
							<div className="space-y-1">
								<div className="flex gap-2 items-center text-sm">
									<Badge className="bg-slate-500 bg-success text-white">
										AVAILABLE
									</Badge>

									{product.productConfiguration
										?.availabilityEstimateName && (
										<p>
											Incoming Date:&nbsp;
											{
												product.productConfiguration
													?.availabilityEstimateName
											}
										</p>
									)}
								</div>

								<h3 className="font-medium group-hover:text-primary text-foreground transition-colors">
									{product.name}
								</h3>

								<p className="text-sm">
									{product.description ||
										'Short description duis semper tellus egestas ex porttitor scelerisque. Donec scelerisque suscipit massa, et pellentesque tortor cursus sed...'}
								</p>
							</div>

							<div className="text-right">
								<div className="font-semibold mb-2 text-foreground text-xl truncate">
									{productPrice.finalPrice}
								</div>
							</div>
						</div>

						<div className="flex items-center justify-between">
							<div className="space-y-1 text-sm">
								<div>SKU: {productPrice.sku}</div>

								{productPrice.gtin && (
									<div>GTIN: {productPrice.gtin}</div>
								)}

								{productPrice.mfrPartNumber && (
									<div>
										Mfr. Part Number:&nbsp;
										{productPrice.mfrPartNumber}
									</div>
								)}
							</div>
						</div>
					</div>
				</div>
			</Link>
		</Card>
	);
}
