/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ORDER_TYPES_PATH = '/order-types';

const VERSION = 'v1.0';

function resolvePath(basePath = '', orderTypeId = '') {
	return `${basePath}${VERSION}${ORDER_TYPES_PATH}/${orderTypeId}`;
}

export default function OrderType(basePath) {
	return {
		addOrderType: (json) => AJAX.POST(resolvePath(basePath), json),
	};
}
