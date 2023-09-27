/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const headlessAPI = 'o/headless-commerce-delivery-catalog/v1.0';

export function getProducts() {
	const result = getChannelId('Raylife AP').then((response) => {
		const {
			data: {items},
		} = response;

		const channelId = items[0].id;

		return axios.get(
			`${headlessAPI}/channels/${channelId}/products?nestedFields=skus,catalog&fields=name,externalReferenceCode&page=1&pageSize=50`
		);
	});

	return result;
}

export function getChannelId(channelName: string) {
	return axios.get(
		`${headlessAPI}/channels?filter=contains(name, '${channelName}')&fields=id`
	);
}
