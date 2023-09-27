/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
