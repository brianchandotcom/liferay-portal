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

import getJsModule from '../../../utilities/modules';
import {DISCOUNT_LEVEL_PREFIX, ORDER_UUID_PARAMETER} from './constants';

export function isNonnull(...values) {
	return !!values.find((value) => parseFloat(value) > 0);
}

export function collectDiscountLevels(price) {
	return Object.keys(price).reduce((levels, key) => {
		if (key.startsWith(DISCOUNT_LEVEL_PREFIX)) {
			levels.push(price[key].toFixed(2));
		}

		return levels;
	}, []);
}

export function parseOptions(stringifiedJSON) {
	let options;

	try {
		options = JSON.parse(stringifiedJSON);
	}
	catch (ignore) {
		options = '';
	}

	return Array.isArray(options)
		? options.map(({value}) => `${value}`).join(', ')
		: options;
}

export function regenerateOrderDetailURL(orderDetailURL, orderUUID) {
	const originalURL = new URL(orderDetailURL);

	originalURL.searchParams.set(ORDER_UUID_PARAMETER, orderUUID);

	return originalURL.toString();
}

export function resolveView({component, contentRendererModuleUrl}) {
	if (component) {
		return Promise.resolve((props) => component(props));
	}

	return getJsModule(contentRendererModuleUrl);
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
