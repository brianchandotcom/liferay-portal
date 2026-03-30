/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Image from 'next/image';
import Link from 'next/link';

import {Product} from '../../types/product';
import {getSkuDetails, handleImageError} from '../../utils/product';
import {Badge} from '../ui/badge';
import {Card} from '../ui/card';

type ProductGridViewProps = {
	product: Product;
};

export default function ProductGridView({product}: ProductGridViewProps) {
	const skuDetails = getSkuDetails(product);

	return (
		<Card className="bg-product-card bg-white border-product-border cursor-pointer duration-300 group hover:shadow-lg overflow-hidden relative transition-all">
			<Link href={`/product/${product.urls?.en_US}`}>
				<div className="relative">
					<Image
						alt={product.name}
						className="duration-300 group-hover:scale-105 h-48 object-cover transition-transform w-full"
						height={120}
						onError={handleImageError}
						quality={100}
						src={product.urlImage}
						width={120}
					/>

					{skuDetails.available && (
						<Badge className="absolute bg-slate-500 left-3 text-white top-3">
							AVAILABLE
						</Badge>
					)}
				</div>

				<div className="p-4 space-y-3">
					<h3 className="font-medium group-hover:text-primary text-foreground transition-colors truncate">
						{product.name}
					</h3>

					<div className="flex gap-2 items-center">
						{skuDetails.discount && (
							<span className="line-through text-sm">
								{skuDetails.originalPrice}
							</span>
						)}

						<span className="font-semibold text-lg">
							{skuDetails.price.finalPrice}
						</span>

						{skuDetails.price?.discount && (
							<Badge className="text-xs" variant="destructive">
								-{skuDetails.discountPercent}%
							</Badge>
						)}
					</div>
				</div>
			</Link>
		</Card>
	);
}
