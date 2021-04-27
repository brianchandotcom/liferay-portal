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

export function regenerateOrderDetailURL(orderUUID, siteDefaultURL) {
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

export function hasErrors(cartItems) {
	return !!cartItems.find(({errorMessages}) => !!errorMessages);
}
