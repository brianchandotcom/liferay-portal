/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRODUCTS_PATH = '/products';

const VERSION = 'v1.0';

function resolveProductsPath(basePath = '', productId = '') {
	return `${basePath}${VERSION}${PRODUCTS_PATH}/${productId}`;
}

export default function Product(basePath) {
	return {
		createProduct: (json) => AJAX.POST(resolveProductsPath(basePath), json),

		getProductById: (productId) =>
			AJAX.GET(resolveProductsPath(basePath, productId)),
	};
}
