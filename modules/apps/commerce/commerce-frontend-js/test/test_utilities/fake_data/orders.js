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

import {getRandomInt, processFakeRequestData} from '../index';

export let orders = [];

const fakeStatus = [
	{
		label: 'open',
		label_i18n: 'Open',
	},
	{
		label: 'pending',
		label_i18n: 'Pending',
	},
	{
		label: 'completed',
		label_i18n: 'Completed',
	},
];

export const orderTemplate = {
	id: 99999,
	modifiedDate: '2020-01-01T00:00:00Z',
	orderStatusInfo: {
		label: 'test-status',
		label_i18n: 'Test Status',
	},
};

export function generateFakeOrders(total) {
	orders = [orderTemplate];

	for (let i = 0; i < total - 1; i++) {
		orders.push({
			...orderTemplate,
			id: i,
			orderStatusInfo: fakeStatus[getRandomInt(0, 2)],
		});
	}

	return orders;
}

export function getOrders(url, itemsLength = getRandomInt(40, 60)) {
	if (!orders.length) {
		generateFakeOrders(itemsLength);
	}

	const {
		items,
		lastPage,
		page,
		pageSize,
		totalCount,
	} = processFakeRequestData(url, orders, [orderTemplate]);

	return {
		actions: {},
		facets: [],
		items,
		lastPage,
		page,
		pageSize,
		totalCount,
	};
}
