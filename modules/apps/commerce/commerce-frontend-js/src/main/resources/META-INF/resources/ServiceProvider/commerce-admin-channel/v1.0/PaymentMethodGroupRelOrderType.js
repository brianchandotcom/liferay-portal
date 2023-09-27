/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
