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

const PAYMENT_METHOD_GROUP_REL_PATH = '/payment-method-group-rels';

const PAYMENT_METHOD_GROUP_REL_ORDER_TYPES_PATH =
	'/payment-method-group-rel-order-types';

const VERSION = 'v1.0';

function resolvePath(
	basePath = '',
	paymentMethodGroupRelId = '',
	paymentMethodGroupRelOrderTypeId = ''
) {
	return `${basePath}${VERSION}${PAYMENT_METHOD_GROUP_REL_PATH}/${paymentMethodGroupRelId}${PAYMENT_METHOD_GROUP_REL_ORDER_TYPES_PATH}/${paymentMethodGroupRelOrderTypeId}`;
}

export default function PaymentMethodGroupRelOrderType(basePath) {
	return {
		addPaymentMethodGroupRelOrderType: (paymentMethodGroupRelId, json) =>
			AJAX.POST(resolvePath(basePath, paymentMethodGroupRelId), json),
	};
}
