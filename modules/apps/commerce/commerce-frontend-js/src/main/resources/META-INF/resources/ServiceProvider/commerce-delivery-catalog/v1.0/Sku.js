/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const VERSION = 'v1.0';

function resolveSkusPath(basePath, channelId, productId, accountId, quantity) {
	let path = `${basePath}${VERSION}/channels/${channelId}/products/${productId}/skus/by-sku-option`;

	if (accountId || quantity) {
		path += `?`;

		const params = new URLSearchParams();

		if (accountId) {
			params.append('accountId', accountId);
		}

		if (quantity) {
			params.append('quantity', quantity);
		}

		path += params.toString();
	}

	return path;
}

export default function Sku(basePath) {
	return {
		postChannelProductSkuBySkuOption: (
			channelId,
			productId,
			accountId,
			quantity,
			...params
		) =>
			AJAX.POST(
				resolveSkusPath(
					basePath,
					channelId,
					productId,
					accountId,
					quantity
				),
				...params
			),
	};
}
