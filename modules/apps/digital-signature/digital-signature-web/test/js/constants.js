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

const createItems = (size) => {
	const items = [];

	for (let i = 0; i < size; i++) {
		items.push({
			dateCreated: '2021-05-26T11:26:54.262Z',
			dateModified: '2021-05-26T11:26:54.262Z',
			id: i + 1,
			name: `Item ${i + 1}`,
		});
	}

	return items;
};

export const ACTIONS = [
	{
		action: () => {},
		name: 'Delete',
	},
];

export const COLUMNS = [
	{
		key: 'name',
		value: 'Name',
	},
	{
		key: 'dateCreated',
		value: 'Created Date',
	},
	{
		key: 'dateModified',
		value: 'Modified Date',
	},
];

export const EMPTY_STATE = {
	description: 'description',
	title: 'title',
};

export const ENDPOINT = '/endpoint';

export const ITEMS = {
	MANY: (size) => createItems(size),
	ONE: createItems(1),
	TWENTY: createItems(20),
};

export const RESPONSES = {
	MANY_ITEMS: (size) => {
		const items = ITEMS.MANY(size);

		return {
			items,
			lastPage: 1,
			page: 1,
			pageSize: 20,
			totalCount: items.length,
		};
	},
	NO_ITEMS: {
		lastPage: 1,
		page: 1,
		pageSize: 20,
		totalCount: 0,
	},
	ONE_ITEM: {
		items: ITEMS.ONE,
		lastPage: 1,
		page: 1,
		pageSize: 20,
		totalCount: ITEMS.ONE.length,
	},
	TWENTY_ONE_ITEMS: {
		items: ITEMS.TWENTY,
		lastPage: 2,
		page: 1,
		pageSize: 20,
		totalCount: ITEMS.TWENTY.length + 1,
	},
};

export const FILTERS = [
	{
		items: [
			{label: 'Product Menu', value: 'productMenu'},
			{label: 'Standalone', value: 'standalone'},
			{label: 'Widget', value: 'widget'},
		],
		key: 'deploymentTypes',
		multiple: true,
		name: 'deployment-type',
	},
	{
		items: [
			{label: 'Deployed', value: 'true'},
			{label: 'Undeployed', value: 'false'},
		],
		key: 'active',
		name: 'status',
	},
];
