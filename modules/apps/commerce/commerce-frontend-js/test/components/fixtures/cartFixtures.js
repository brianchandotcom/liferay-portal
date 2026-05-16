/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ZERO_PRICE = {
	currency: 'USD',
	discount: 0,
	discountFormatted: '$ 0.00',
	discountPercentage: '0.00',
	discountPercentageLevel1: 0,
	discountPercentageLevel2: 0,
	discountPercentageLevel3: 0,
	discountPercentageLevel4: 0,
	finalPrice: 10,
	finalPriceFormatted: '$ 10.00',
	price: 10,
	priceFormatted: '$ 10.00',
	promoPrice: 10,
	promoPriceFormatted: '$ 10.00',
};

const BASE_SETTINGS = {
	maxQuantity: 9999,
	minQuantity: 1,
	multipleQuantity: 1,
};

export function mockCartItem(overrides = {}) {
	const {price, settings, ...rest} = overrides;

	return {
		adaptiveMediaImageHTMLTag: '',
		cartItems: [],
		id: 42,
		name: 'Sample Product',
		options: '[]',
		price: {...ZERO_PRICE, ...price},
		productId: 100,
		productURLs: {en_US: 'sample-product'},
		quantity: 1,
		settings: {...BASE_SETTINGS, ...settings},
		sku: 'SAMPLE-001',
		skuId: 200,
		thumbnail: '',
		...rest,
	};
}

export function mockCartItemWithTierPrice(overrides = {}) {
	return mockCartItem({
		price: {
			finalPrice: 6,
			finalPriceFormatted: '$ 6.00',
			price: 10,
			priceFormatted: '$ 10.00',
			promoPrice: 6,
			promoPriceFormatted: '$ 6.00',
		},
		quantity: 10,
		...overrides,
	});
}

export function mockCartItemWithBulkPrice(overrides = {}) {
	return mockCartItem({
		price: {
			finalPrice: 5,
			finalPriceFormatted: '$ 5.00',
			price: 10,
			priceFormatted: '$ 10.00',
			promoPrice: 5,
			promoPriceFormatted: '$ 5.00',
		},
		quantity: 20,
		settings: {...BASE_SETTINGS, multipleQuantity: 5},
		...overrides,
	});
}
