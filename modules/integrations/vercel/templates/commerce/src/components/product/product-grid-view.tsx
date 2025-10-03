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

type ProductGridViewProps = {
	product: Product;
};

export default function ProductGridView({product}: ProductGridViewProps) {
	const productPrice = getSkuDetails(product);

	return (
		<Card className="bg-product-card bg-white border-product-border cursor-pointer duration-300 group hover:shadow-lg overflow-hidden relative transition-all">
			<Link href={`/product/${product.urls?.en_US}`}>
				<div className="relative">
					<Image
						alt={product.name as string}
						className="duration-300 group-hover:scale-105 h-48 object-cover transition-transform w-full"
						height={48}
						onError={handleImageError}
						quality={100}
						src={product.urlImage as string}
						unoptimized
						width={48}
					/>

					{productPrice.available && (
						<Badge className="absolute bg-slate-500 bg-success left-3 text-white top-3">
							AVAILABLE
						</Badge>
					)}
				</div>

				<div className="p-4 space-y-3">
					<div className="text-sm">
						{product.externalReferenceCode}
					</div>

					<h3 className="font-medium group-hover:text-primary text-foreground transition-colors truncate">
						{product.name}
					</h3>

					<div className="flex gap-2 items-center">
						{productPrice.discount && (
							<span className="line-through text-sm">
								{productPrice.originalPrice}
							</span>
						)}

						<span className="font-semibold text-lg">
							{productPrice.finalPrice}
						</span>

						{productPrice.discount && (
							<Badge className="text-xs" variant="destructive">
								-{productPrice.discountPercent}%
							</Badge>
						)}
					</div>
				</div>
			</Link>
		</Card>
	);
}
