/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const VERSION = 'v1.0';

function resolveProductOptionValuesPath(
	basePath,
	channelId,
	productId,
	productOptionId,
	accountId,
	productOptionValueId,
	skuId,
	page,
	pageSize
) {
	let path = `${basePath}${VERSION}/channels/${channelId}/products/${productId}/product-options/${productOptionId}/product-option-values`;

	if (accountId || productOptionValueId || skuId || page || pageSize) {
		path += `?`;

		const params = new URLSearchParams();

		if (accountId) {
			params.append('accountId', accountId);
		}

		if (productOptionValueId) {
			params.append('productOptionValueId', productOptionValueId);
		}

		if (skuId) {
			params.append('skuId', skuId);
		}

		if (page) {
			params.append('page', page);
		}

		if (pageSize) {
			params.append('pageSize', pageSize);
		}

		path += params.toString();
	}

	return path;
}

export default function ProductOptionValue(basePath) {
	return {
		getChannelProductProductOptionProductOptionValues: (
			channelId,
			productId,
			productOptionId,
			accountId,
			productOptionValueId,
			skuId,
			page,
			pageSize,
			...params
		) =>
			AJAX.GET(
				resolveProductOptionValuesPath(
					basePath,
					channelId,
					productId,
					productOptionId,
					accountId,
					productOptionValueId,
					skuId,
					page,
					pageSize
				),
				...params
			),
	};
}
