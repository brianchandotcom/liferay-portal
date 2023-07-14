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
