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
