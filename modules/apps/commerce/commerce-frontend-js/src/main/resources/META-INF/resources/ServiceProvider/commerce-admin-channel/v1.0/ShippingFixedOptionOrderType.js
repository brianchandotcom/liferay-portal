/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const SHIPPING_FIXED_OPTION_PATH = '/shipping-fixed-options';

const SHIPPING_FIXED_OPTION_ORDER_TYPES_PATH =
	'/shipping-fixed-option-order-types';

const VERSION = 'v1.0';

function resolvePath(
	basePath = '',
	shippingFixedOptionId = '',
	shippingFixedOptionOrderTypeId = ''
) {
	return `${basePath}${VERSION}${SHIPPING_FIXED_OPTION_PATH}/${shippingFixedOptionId}${SHIPPING_FIXED_OPTION_ORDER_TYPES_PATH}/${shippingFixedOptionOrderTypeId}`;
}

export default function ShippingFixedOptionOrderType(basePath) {
	return {
		addShippingFixedOptionOrderType: (shippingFixedOptionId, json) =>
			AJAX.POST(resolvePath(basePath, shippingFixedOptionId), json),
	};
}
