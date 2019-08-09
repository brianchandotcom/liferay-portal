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

const createItems = size => {
	const items = [];

	for (let i = 0; i < size; i++) {
		items.push({
			dateCreated: '01/01/2019',
			dateModified: '01/02/2019',
			id: i + 1,
			name: `Name ${i + 1}`
		});
	}

	return items;
};

export const ACTIONS = [
	{
		callback: () => {},
		name: 'Delete'
	}
];

export const COLUMNS = [
	{
		name: 'Name'
	},
	{
		dateCreated: 'Created Date'
	},
	{
		dateModified: 'Modified Date'
	}
];

export const EMPTY_STATE = {
	description: 'description',
	title: 'title'
};

export const ENDPOINT = '/endpoint';

export const FORMATTER = items => items;

export const ITEMS = {
	ONE: createItems(1),
	TWENTY: createItems(20)
};

export const RESPONSES = {
	NO_ITEMS: {
		items: [],
		lastPage: 1,
		page: 1,
		pageSize: 20,
		totalCount: 0
	},
	ONE_ITEM: {
		items: ITEMS.ONE,
		lastPage: 1,
		page: 1,
		pageSize: 20,
		totalCount: ITEMS.ONE.length
	},
	TWENTY_ONE_ITEMS: {
		items: ITEMS.TWENTY,
		lastPage: 2,
		page: 1,
		pageSize: 20,
		totalCount: ITEMS.TWENTY.length + 1
	}
};
