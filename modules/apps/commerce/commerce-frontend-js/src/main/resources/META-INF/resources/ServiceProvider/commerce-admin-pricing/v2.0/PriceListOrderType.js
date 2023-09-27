/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRICE_LISTS_PATH = '/price-lists';

const PRICE_LIST_ORDER_TYPE_PATH = '/price-list-order-types';

const VERSION = 'v2.0';

function resolvePath(
	basePath = '',
	priceListId = '',
	priceListOrderTypeId = ''
) {
	return `${basePath}${VERSION}${PRICE_LISTS_PATH}/${priceListId}${PRICE_LIST_ORDER_TYPE_PATH}/${priceListOrderTypeId}`;
}

export default function PriceListOrderType(basePath) {
	return {
		addPriceListOrderType: (priceListId, json) =>
			AJAX.POST(resolvePath(basePath, priceListId), json),
	};
}
