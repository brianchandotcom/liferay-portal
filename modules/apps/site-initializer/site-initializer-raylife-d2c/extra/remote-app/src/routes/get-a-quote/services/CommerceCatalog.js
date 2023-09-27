/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		`${headlessAPI}/channels/${channelId}/products`
	);

	return LiferayAdapt.adaptToProductQuote(channelId, data.items);
}
