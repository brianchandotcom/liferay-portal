/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const DISCOUNTS_PATH = '/discounts';

const DISCOUNT_RULES_PATH = '/discount-skus';

const VERSION = 'v2.0';

function resolvePath(basePath = '', discountId = '', discountSkuId = '') {
	return `${basePath}${VERSION}${DISCOUNTS_PATH}/${discountId}${DISCOUNT_RULES_PATH}/${discountSkuId}`;
}

export default function DiscountSku(basePath) {
	return {
		addDiscountSku: (discountId, json) =>
			AJAX.POST(resolvePath(basePath, discountId), json),
	};
}
