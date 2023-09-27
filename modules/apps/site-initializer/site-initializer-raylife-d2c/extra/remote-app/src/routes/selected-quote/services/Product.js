/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-commerce-delivery-catalog';

export function getSku(channelId, productId) {
	return axios.get(
		`${DeliveryAPI}/v1.0/channels/${channelId}/products/${productId}/skus`
	);
}
