/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ORDERS_PATH = '/orders';

const VERSION = 'v1.0';

function resolveCatalogPath(basePath = '') {
	return `${basePath}${VERSION}${ORDERS_PATH}`;
}

export default function Order(basePath) {
	return {
		baseURL: resolveCatalogPath(basePath),
		getOrders: (...params) =>
			AJAX.GET(resolveCatalogPath(basePath), ...params),
	};
}
