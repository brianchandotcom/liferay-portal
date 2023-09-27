/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const VERSION = 'v1.0';

function resolveSkusPath(
	basePath,
	channelId,
	productId,
	accountId,
	quantity,
	skuUnitOfMeasureKey
) {
	let path = `${basePath}${VERSION}/channels/${channelId}/products/${productId}/skus/by-sku-option`;

	if (accountId || quantity || skuUnitOfMeasureKey) {
		path += `?`;

		const params = new URLSearchParams();

		if (accountId) {
			params.append('accountId', accountId);
		}

		if (quantity) {
			params.append('quantity', quantity);
		}

		if (skuUnitOfMeasureKey) {
			params.append('skuUnitOfMeasureKey', skuUnitOfMeasureKey);
		}

		path += params.toString();
	}

	return path;
}

function resolveSkuPath(basePath, channelId, productId, skuId, accountId) {
	let path = `${basePath}${VERSION}/channels/${channelId}/products/${productId}/skus/${skuId}`;

	if (accountId) {
		path += `?`;

		const params = new URLSearchParams();

		if (accountId) {
			params.append('accountId', accountId);
		}

		path += params.toString();
	}

	return path;
}

export default function Sku(basePath) {
	return {
		getChannelProductSku: (channelId, productId, skuId, accountId) =>
			AJAX.GET(
				resolveSkuPath(basePath, channelId, productId, skuId, accountId)
			),
		postChannelProductSkuBySkuOption: (
			channelId,
			productId,
			accountId,
			quantity,
			skuUnitOfMeasureKey,
			...params
		) =>
			AJAX.POST(
				resolveSkusPath(
					basePath,
					channelId,
					productId,
					accountId,
					quantity,
					skuUnitOfMeasureKey
				),
				...params
			),
	};
}
