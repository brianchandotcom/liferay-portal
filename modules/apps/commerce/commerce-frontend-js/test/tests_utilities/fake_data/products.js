/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import faker from 'faker';

import {getRandomInt, processFakeRequestData} from '../index';

export let products = [];

export const productTemplate = {
	id: 999999,
	name: 'TEST PRODUCT NAME',
	productId: 999999,
	urlImage: '/test-logo-folder/test.jpg',
};

export function generateFakeProducts(total) {
	products = [productTemplate];

	for (let i = 0; i < total - 1; i++) {
		products.push({
			...productTemplate,
			id: i,
			name: faker.commerce.productName(),
			productId: i,
			urlImage: faker.image.business(),
		});
	}

	return products;
}

export function getProducts(url, itemsLength = getRandomInt(40, 60)) {
	if (!products.length) {
		generateFakeProducts(itemsLength);
	}

	const {
		items,
		lastPage,
		page,
		pageSize,
		totalCount,
	} = processFakeRequestData(url, products, [productTemplate]);

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
