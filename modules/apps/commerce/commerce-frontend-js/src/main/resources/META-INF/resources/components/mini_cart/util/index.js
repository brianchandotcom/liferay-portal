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
	MAXIMUM_ALLOWED_QUANTITY_NOT_VALID_ERROR,
	MAXIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR,
	MINIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR,
	ORDER_DETAILS_ENDPOINT,
	ORDER_UUID_PARAMETER,
	PRODUCT_MULTIPLE_OF_QUANTITY_NOT_VALID_ERROR,
	PRODUCT_QUANTITY_NOT_VALID_ERROR,
} from './constants';

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

	if (parentProduct || !allowedOrderQuantities.length) {
		quantity = minOrderQuantity;
	}

	const existingItem = cartItems.find(
		(item) =>
			item.productId === product.productId || item.sku === product.sku
	);

	const lastAllowedQuantity =
		allowedOrderQuantities[allowedOrderQuantities.length - 1];

	if (existingItem) {
		if (allowedOrderQuantities.length) {
			const nextAllowedQuantity = allowedOrderQuantities.find(
				(allowedQuantity) => {
					if (multipleOrderQuantity > 1) {
						return (
							allowedQuantity > existingItem.quantity &&
							allowedQuantity % multipleOrderQuantity === 0
						);
					}

					return allowedQuantity > existingItem.quantity;
				}
			);

			allowedOrderQuantities.forEach((allowedQuantity) => {
				if (allowedQuantity > existingItem.quantity) {
					quantity = nextAllowedQuantity - existingItem.quantity;
				}
			});

			if (multipleOrderQuantity > 1 && !nextAllowedQuantity) {
				openToast({
					message: sub(PRODUCT_QUANTITY_NOT_VALID_ERROR),
					type: 'danger',
				});

				return 0;
			}

			if (existingItem.quantity >= lastAllowedQuantity) {
				quantity = 0;
			}
		}
		else if (existingItem.quantity >= multipleOrderQuantity) {
			quantity = multipleOrderQuantity;
		}

		if (existingItem.quantity + quantity > maxOrderQuantity) {
			if (multipleOrderQuantity > 1) {
				openToast({
					message: sub(
						MAXIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR,
						maxOrderQuantity
					),
					type: 'danger',
				});

				return 0;
			}
			else {
				openToast({
					message: sub(
						MAXIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR,
						maxOrderQuantity
					),
					type: 'danger',
				});

				return 0;
			}
		}
	}
	else if (allowedOrderQuantities.length) {
		quantity = allowedOrderQuantities.find(
			(quantity) =>
				quantity >= minOrderQuantity &&
				quantity % multipleOrderQuantity === 0
		);

		if (maxOrderQuantity < allowedOrderQuantities[0]) {
			openToast({
				message: sub(
					MAXIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR,
					maxOrderQuantity
				),
				type: 'danger',
			});

			return 0;
		}

		if (minOrderQuantity > lastAllowedQuantity) {
			openToast({
				message: sub(
					MINIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR,
					minOrderQuantity
				),
				type: 'danger',
			});

			return 0;
		}
	}
	else if (multipleOrderQuantity > minOrderQuantity) {
		quantity = multipleOrderQuantity;

		if (multipleOrderQuantity > maxOrderQuantity) {
			openToast({
				message: sub(
					MAXIMUM_PRODUCT_QUANTITY_NOT_VALID_ERROR,
					maxOrderQuantity
				),
				type: 'danger',
			});

			return 0;
		}
	}
	else if (multipleOrderQuantity < minOrderQuantity) {
		quantity = multipleOrderQuantity;

		while (quantity < minOrderQuantity) {
			quantity += multipleOrderQuantity;
		}
	}

	if (multipleOrderQuantity > 1 && quantity % multipleOrderQuantity !== 0) {
		openToast({
			message: sub(
				PRODUCT_MULTIPLE_OF_QUANTITY_NOT_VALID_ERROR,
				multipleOrderQuantity
			),
			type: 'danger',
		});

		return 0;
	}

	if (quantity === 0) {
		openToast({
			message: sub(
				MAXIMUM_ALLOWED_QUANTITY_NOT_VALID_ERROR,
				sku,
				lastAllowedQuantity > 1 ? lastAllowedQuantity : maxOrderQuantity
			),
			type: 'danger',
		});
	}

	return quantity;
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

export function hasErrors(cartItems) {
	return cartItems.some(({errorMessages}) => Boolean(errorMessages?.length));
}

export function hasPriceOnApplication(cartItems) {
	return cartItems.some(({price}) => price.priceOnApplication);
}

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
