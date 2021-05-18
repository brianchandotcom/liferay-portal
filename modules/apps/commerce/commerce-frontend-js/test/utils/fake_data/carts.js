/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const CART_ITEMS = [
	{
		customFields: {},
		id: 43912,
		name: 'Premium Brake Pads',
		options: 'null',
		parentCartItemId: 0,
		price: {
			currency: 'US Dollar',
			discount: 0.0,
			discountFormatted: '$ 0.00',
			discountPercentage: '0.00',
			discountPercentageLevel1: 0.0,
			discountPercentageLevel2: 0.0,
			discountPercentageLevel3: 0.0,
			discountPercentageLevel4: 0.0,
			finalPrice: 26.1,
			finalPriceFormatted: '$ 26.10',
			price: 29.0,
			priceFormatted: '$ 29.00',
			promoPrice: 26.1,
			promoPriceFormatted: '$ 26.10',
		},
		productId: 42182,
		quantity: 10,
		settings: {
			maxQuantity: 10000,
			minQuantity: 10,
			multipleQuantity: 5,
		},
		sku: 'MIN93019',
		skuId: 42185,
		subscription: false,
		thumbnail:
			'/o/commerce-media/products/42182/premium-brake-pads/42204/Minium_ProductImage_83.png?download=false',
	},
	{
		customFields: {},
		id: 43913,
		name: 'Brake Pads',
		options: 'null',
		parentCartItemId: 0,
		price: {
			currency: 'US Dollar',
			discount: 0.0,
			discountFormatted: '$ 0.00',
			discountPercentage: '0.00',
			discountPercentageLevel1: 0.0,
			discountPercentageLevel2: 0.0,
			discountPercentageLevel3: 0.0,
			discountPercentageLevel4: 0.0,
			finalPrice: 18.9,
			finalPriceFormatted: '$ 18.90',
			price: 21.0,
			priceFormatted: '$ 21.00',
			promoPrice: 18.9,
			promoPriceFormatted: '$ 18.90',
		},
		productId: 42144,
		quantity: 5,
		settings: {
			maxQuantity: 10000,
			minQuantity: 3,
			multipleQuantity: 5,
		},
		sku: 'MIN93018',
		skuId: 42147,
		subscription: false,
		thumbnail:
			'/o/commerce-media/products/42144/brake-pads/42166/Minium_ProductImage_83.png?download=false',
	},
];

export const getMockedCart = (withItems = false) => ({
	account: 'Adm',
	accountId: 43574,
	author: 'Test Test',
	billingAddressId: 0,
	cartItems: withItems ? [...CART_ITEMS] : [],
	couponCode: '',
	createDate: '2021-04-29T14:42:45Z',
	customFields: {},
	id: 43620,
	modifiedDate: '2021-05-18T11:18:48Z',
	orderStatusInfo: {
		code: 2,
		label: 'open',
		label_i18n: 'Open',
	},
	orderUUID: '36f5d609-2a4e-ca2b-ea02-738a17e4d93e',
	paymentMethod: '',
	paymentStatus: 1,
	paymentStatusInfo: {
		code: 1,
		label: 'pending',
		label_i18n: 'Pending',
	},
	paymentStatusLabel: 'pending',
	printedNote: '',
	purchaseOrderNumber: '',
	shippingAddressId: 0,
	status: 'approved',
	summary: {
		currency: 'US Dollar',
		itemsQuantity: withItems ? 10 : 0,
		shippingDiscountPercentages: ['0.00', '0.00', '0.00', '0.00'],
		shippingDiscountValue: 0.0,
		shippingDiscountValueFormatted: '$ 0.00',
		shippingValue: 0.0,
		shippingValueFormatted: '$ 0.00',
		shippingValueWithTaxAmount: 0.0,
		shippingValueWithTaxAmountFormatted: '$ 0.00',
		subtotal: 261.0,
		subtotalDiscountPercentages: ['0.00', '0.00', '0.00', '0.00'],
		subtotalDiscountValue: 0.0,
		subtotalDiscountValueFormatted: '$ 0.00',
		subtotalFormatted: '$ 261.00',
		taxValue: 0.0,
		taxValueFormatted: '$ 0.00',
		total: 261.0,
		totalDiscountPercentages: ['0.00', '0.00', '0.00', '0.00'],
		totalDiscountValue: 0.0,
		totalDiscountValueFormatted: '$ 0.00',
		totalFormatted: '$ 261.00',
	},
	workflowStatusInfo: {
		code: 0,
		label: 'approved',
		label_i18n: 'Approved',
	},
});
