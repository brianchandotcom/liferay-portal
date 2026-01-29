/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Product, Sku} from '../types/product';

function calculateDiscountPercent(finalPrice: number, originalPrice: number) {
	if (originalPrice === 0) {
		return 0;
	}

	const discount = originalPrice - finalPrice;
	const discountPercent = (discount / originalPrice) * 100;

	return discountPercent.toFixed(2);
}

type SkuDetails = {
	available: boolean;
	discount: number;
	discountPercent: number;
	originalPrice: string;
} & Sku;

export function getSkuDetails(product: Product) {
	const [sku] = product.skus?.filter((sku) => sku.purchasable) || [];

	if (!sku) {
		return {
			available: false,
			discount: false,
			finalPrice: '0',
			originalPrice: '0',
		} as unknown as SkuDetails;
	}

	const finalPrice = sku?.price?.finalPrice;
	const originalPrice = sku?.price?.priceFormatted;

	return {
		...sku,
		availabilityEstimateName:
			sku.productConfiguration?.availabilityEstimateName,
		available: true,
		discount: finalPrice !== originalPrice,
		discountNumber: (sku.price?.promoPrice ?? 0) - (sku.price?.price ?? 0),
		discountPercent: calculateDiscountPercent(
			sku?.price?.promoPrice ?? 0,
			sku?.price?.price ?? 0
		),
		finalPrice,
		mfrPartNumber: sku.manufacturerPartNumber,
		originalPrice,
	} as unknown as SkuDetails;
}

export function handleImageError(event: React.ChangeEvent<HTMLImageElement>) {
	event.currentTarget.src = '/images/dxp_logo.svg';
}
