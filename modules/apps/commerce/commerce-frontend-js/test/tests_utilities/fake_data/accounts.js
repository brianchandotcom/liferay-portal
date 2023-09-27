/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import faker from 'faker';

import {getRandomInt, processFakeRequestData} from '../index';

export let accounts = [];

export const accountTemplate = {
	emailAddresses: ['email@test.com'],
	id: 42332,
	logoURL: '/test-logo-folder/test.jpg',
	name: 'TEST ACCOUNT NAME',
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
