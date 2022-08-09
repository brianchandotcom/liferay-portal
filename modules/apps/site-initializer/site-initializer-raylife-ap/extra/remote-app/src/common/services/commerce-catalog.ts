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
