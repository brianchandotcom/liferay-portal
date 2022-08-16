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

import {LiferayAdapt} from '../../../common/services/liferay/adapter';
import {axios} from '../../../common/services/liferay/api';

const headlessAPI = 'o/headless-commerce-delivery-catalog/v1.0';

/**
 * @returns {Promise<ProductQuote[]>)} Array of Product Quote
 */
export async function getProductQuotes() {
	const channelName = 'Raylife D2C Channel';

	const channel = await axios.get(
		`${headlessAPI}/channels?filter=name eq '${channelName}'`
	);

	const channelId = channel?.data?.items[0]?.id;

	const {data} = await axios.get(
		`${headlessAPI}/channels/${channelId}/products?nestedFields=skus,catalog&page=1&pageSize=50`
	);

	return LiferayAdapt.adaptToProductQuote(data.items);
}
