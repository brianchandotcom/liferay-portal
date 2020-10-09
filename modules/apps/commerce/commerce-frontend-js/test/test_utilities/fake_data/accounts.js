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

export let accounts = [];

export const accountTemplate = {
	emailAddresses: ['email@test.com'],
	id: 999999,
	logoURL: '/test-logo-folder/test.jpg',
	name: 'TEST ORDER NAME',
};

export function generateFakeAccounts(total) {
	accounts = [accountTemplate];

	for (let i = 0; i < total - 1; i++) {
		accounts.push({
			...accountTemplate,
			emailAddresses: new Array(getRandomInt(0, 2)).map(
				faker.internet.email
			),
			id: i,
			name: faker.company.companyName(),
		});
	}

	return accounts;
}

export function getAccounts(url, itemsLength = getRandomInt(40, 60)) {
	if (!accounts.length) {
		generateFakeAccounts(itemsLength);
	}

	const {
		items,
		lastPage,
		page,
		pageSize,
		totalCount,
	} = processFakeRequestData(url, accounts, [accountTemplate]);

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
