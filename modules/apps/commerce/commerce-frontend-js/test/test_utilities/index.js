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

export function getRandomInt(min, max) {
	min = Math.ceil(min);
	max = Math.floor(max);

	return Math.floor(Math.random() * (max - min)) + min;
}

export function processFakeRequestData(url, items, queriedItems) {
	const page = url.searchParams.get('page') || 1;
	const pageSize = url.searchParams.get('pageSize') || 10;
	const query = url.searchParams.get('query');
	const lastPage = Math.ceil(items.length - 1 / pageSize);

	const itemsToBeReturned = [];

	if (query) {
		itemsToBeReturned.push(...queriedItems);
	}
	else {
		const startIndex = page * pageSize - pageSize;

		const endIndex =
			startIndex + pageSize >= items.length
				? items.length
				: startIndex + pageSize;

		itemsToBeReturned.push(...items.slice(startIndex, endIndex));
	}

	return {
		items: itemsToBeReturned,
		lastPage,
		page,
		pageSize,
		totalCount: query ? queriedItems.length : items.length,
	};
}
