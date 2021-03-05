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

import {
	DEFAULT_ORDER_DETAILS_PORTLET_ID,
	ORDER_DETAILS_ENDPOINT,
	ORDER_UUID_PARAMETER,
} from './constants';

export function parseOptions(jsonString) {
	let options;

	try {
		options = JSON.parse(jsonString);
	}
	catch (ignore) {
		options = '';
	}

	return Array.isArray(options)
		? options.map(({value}) => `${value}`).join(', ')
		: options;
}

function generatedOrderDetailURL() {
	const baseURL = new URL(
		`${Liferay.ThemeDisplay.getCanonicalURL()}${ORDER_DETAILS_ENDPOINT}`
	);

	baseURL.searchParams.append('p_p_id', DEFAULT_ORDER_DETAILS_PORTLET_ID);
	baseURL.searchParams.append('p_p_lifecycle', '0');
	baseURL.searchParams.append(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_mvcRenderCommandName`,
		'/commerce_open_order_content/edit_commerce_order'
	);
	baseURL.searchParams.append(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_commerceOrderUuid`,
		'0'
	);

	return baseURL;
}

export function regenerateOrderDetailURL(orderDetailURL, orderUUID) {
	const originalURL = orderDetailURL
		? new URL(orderDetailURL)
		: generatedOrderDetailURL();

	originalURL.searchParams.set(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_${ORDER_UUID_PARAMETER}`,
		orderUUID
	);

	return originalURL.toString();
}

export function summaryDataMapper(summary) {
	return Object.keys(summary).reduce((values, key) => {
		const summaryItem = {value: summary[key]};

		switch (key) {
			case 'itemsQuantity':
				values.push({
					label: Liferay.Language.get('quantity'),
					...summaryItem,
				});
				break;
			case 'subtotalFormatted':
				values.push({
					label: Liferay.Language.get('subtotal'),
					...summaryItem,
				});
				break;
			case 'totalDiscountValueFormatted':
				values.push({
					label: Liferay.Language.get('order-discount'),
					...summaryItem,
				});
				break;
			case 'totalFormatted':
				values.push({
					label: Liferay.Language.get('total'),
					style: 'big',
					...summaryItem,
				});
				break;
			default:
				break;
		}

		return values;
	}, []);
}

export function hasErrors(cartItems) {
	return !!cartItems.find(({errorMessages}) => !!errorMessages);
}
