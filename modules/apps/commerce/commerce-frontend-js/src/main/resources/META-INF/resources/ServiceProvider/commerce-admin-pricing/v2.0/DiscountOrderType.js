/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const DISCOUNTS_PATH = '/discounts';

const DISCOUNT_ORDER_TYPE_PATH = '/discount-order-types';

const VERSION = 'v2.0';

function resolvePath(basePath = '', discountId = '', discountOrderTypeId = '') {
	return `${basePath}${VERSION}${DISCOUNTS_PATH}/${discountId}${DISCOUNT_ORDER_TYPE_PATH}/${discountOrderTypeId}`;
}

export default function DiscountOrderType(basePath) {
	return {
		addDiscountOrderType: (discountId, json) =>
			AJAX.POST(resolvePath(basePath, discountId), json),
	};
}
