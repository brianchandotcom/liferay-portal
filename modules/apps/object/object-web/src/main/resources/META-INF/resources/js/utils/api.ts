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

import {fetch} from 'frontend-js-web';

import {HEADERS} from './constants';

export async function fetchJSON<T>(input: RequestInfo, init?: RequestInit) {
	const result = await fetch(input, {
		headers: HEADERS,
		method: 'GET',
		...init,
	});

	return (await result.json()) as T;
}

async function fetchList<T>(url: string) {
	const {items} = await fetchJSON<{items: T[]}>(url);

	return items;
}

export async function getObjectDefinitions() {
	return await fetchList<ObjectDefinition>(
		'/o/object-admin/v1.0/object-definitions?page=-1'
	);
}

export async function getObjectFields(objectDefinitionId: number) {
	return await fetchList<ObjectField>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-fields`
	);
}

export async function getObjectRelationships(objectDefinitionId: number) {
	return await fetchList<ObjectRelationship>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-relationships`
	);
}

export async function getPickLists() {
	return await fetchList<PickList>(
		'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1'
	);
}

export async function getPickListItems(pickListId: number) {
	return await fetchList<PickListItem>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}/list-type-entries`
	);
}
