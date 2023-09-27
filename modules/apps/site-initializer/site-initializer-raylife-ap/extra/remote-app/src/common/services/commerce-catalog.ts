/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const headlessAPI = '/o/headless-commerce-delivery-catalog/v1.0';

export async function getProductsByCategory(
	channelId: number,
	categoryId: number
) {
	return await axios.get(
		`${headlessAPI}/channels/${channelId}/products?nestedFields=categories&filter=(categoryIds/any(x:(x eq '${categoryId}')))`
	);
}

export async function getChannelId(channelName: string) {
	return await axios.get(
		`${headlessAPI}/channels?filter=contains(name, '${channelName}')&fields=id`
	);
}
