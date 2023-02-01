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

import {openToast, sub} from 'frontend-js-web';

import {
	DEFAULT_ORDER_DETAILS_PORTLET_ID,
	ORDER_DETAILS_ENDPOINT,
	ORDER_UUID_PARAMETER,
} from './constants';

export function parseOptions(jsonString) {
	let options;

	try {
		options = JSON.parse(jsonString) || '';
	}
	catch (ignore) {
		options = '';
	}

	return Array.isArray(options)
		? options.map(({value}) => `${value}`).join(', ')
		: options;
}

export function regenerateOrderDetailURL(orderUUID, siteDefaultURL) {
	if (!orderUUID || !siteDefaultURL) {
		throw new Error(
			`Cannot generate a new Order Detail URL. Invalid "${
				siteDefaultURL ? 'orderUUID' : 'siteDefaultURL'
			}"`
		);
	}

	const orderDetailURL = new URL(
		`${siteDefaultURL}${ORDER_DETAILS_ENDPOINT}`
	);

	orderDetailURL.searchParams.append(
		'p_p_id',
		DEFAULT_ORDER_DETAILS_PORTLET_ID
	);
	orderDetailURL.searchParams.append('p_p_lifecycle', '0');
	orderDetailURL.searchParams.append(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_mvcRenderCommandName`,
		'/commerce_open_order_content/edit_commerce_order'
	);

	orderDetailURL.searchParams.append(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_${ORDER_UUID_PARAMETER}`,
		orderUUID
	);

	return orderDetailURL.toString();
}

export function generateProductPageURL(
	baseURL,
	productRelativeURLs,
	productURLSeparator
) {
	const actualLang = themeDisplay.getLanguageId();
	let productLocalizedURL = productRelativeURLs[actualLang];

	if (!productLocalizedURL) {
		const defaultLang = themeDisplay.getDefaultLanguageId();
		productLocalizedURL = productRelativeURLs[defaultLang];
	}

	return [baseURL, productURLSeparator, productLocalizedURL]
		.map((url) => url.replace(/^\//, '').replace(/\/$/, ''))
		.join('/');
}

export function summaryDataMapper({
	itemsQuantity,
	subtotalDiscountValueFormatted,
	subtotalFormatted,
	totalDiscountValueFormatted,
	totalFormatted,
}) {
	return [
		{
			label: Liferay.Language.get('quantity'),
			value: itemsQuantity,
		},
		{
			label: Liferay.Language.get('subtotal'),
			value: subtotalFormatted,
		},
		{
			label: Liferay.Language.get('subtotal-discount'),
			value: subtotalDiscountValueFormatted,
		},
		{
			label: Liferay.Language.get('order-discount'),
			value: totalDiscountValueFormatted,
		},
		{
			label: Liferay.Language.get('total'),
			style: 'big',
			value: totalFormatted,
		},
	];
}

export function hasErrors(cartItems) {
	return cartItems.some(({errorMessages}) => Boolean(errorMessages?.length));
}

export function getCorrectedQuantity(product, sku, cartItems, parentProduct) {
	const {
		allowedOrderQuantities,
		maxOrderQuantity,
		minOrderQuantity,
		multipleOrderQuantity,
	} = parentProduct
		? parentProduct.productConfiguration
		: product.productConfiguration;

	let quantity;

	if (parentProduct) {
		quantity = minOrderQuantity;
	}

	if (!allowedOrderQuantities.length) {
		quantity = minOrderQuantity;
	}

	const existingItem = cartItems.find(
		(item) =>
			item.productId === product.productId ||
			item.productId === parentProduct.productId
	);

	const lastAllowedQuantity =
		allowedOrderQuantities[allowedOrderQuantities.length - 1];

	if (existingItem) {
		const nextAllowedQuantity = allowedOrderQuantities.find(
			(allowedQuantity) => allowedQuantity > existingItem.quantity
		);

		allowedOrderQuantities.forEach((allowedQuantity) => {
			if (allowedQuantity > existingItem.quantity) {
				quantity = nextAllowedQuantity - existingItem.quantity;
			}
		});

		if (existingItem.quantity >= lastAllowedQuantity) {
			quantity = 0;
		}

		if (existingItem.quantity + quantity > maxOrderQuantity) {
			quantity = 0;
		}
	}
	else {
		quantity = allowedOrderQuantities.find(
			(quantity) =>
				quantity > minOrderQuantity &&
				quantity % multipleOrderQuantity === 0
		);
	}

	if (multipleOrderQuantity > 1 && quantity % multipleOrderQuantity !== 0) {
		quantity = 0;
	}

	if (quantity === 0) {
		openToast({
			message: sub(
				Liferay.Language.get('the-maximum-allowed-quantity-for-x-is-x'),
				sku,
				lastAllowedQuantity
			),
			type: 'danger',
		});
	}

	return quantity;
}
