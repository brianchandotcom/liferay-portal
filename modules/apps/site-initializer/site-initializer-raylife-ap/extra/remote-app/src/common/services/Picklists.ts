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

import {axios} from './liferay/api';

const headlessAPI = 'o/headless-admin-list-type/v1.0/list-type-definitions';

export function getPicklistId(picklistName: string) {
	return axios.get(
		`${headlessAPI}/?filter=contains(name, '${picklistName}')&fields=id`
	);
}

export function getPicklistByName(picklistName: string) {
	const result = getPicklistId(picklistName).then((response) => {
		const {
			data: {items},
		} = response;

		const picklistId = items[0].id;

		return axios.get(`${headlessAPI}/${picklistId}`);
	});

	return result;
}
