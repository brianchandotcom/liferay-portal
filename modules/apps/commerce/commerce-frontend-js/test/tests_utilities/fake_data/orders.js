/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
