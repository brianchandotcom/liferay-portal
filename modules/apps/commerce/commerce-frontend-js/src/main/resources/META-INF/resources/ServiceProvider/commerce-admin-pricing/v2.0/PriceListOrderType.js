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

export default (basePath) => ({
	addPriceListOrderType: (priceListId, json) =>
		AJAX.POST(resolvePath(basePath, priceListId), json),
});
