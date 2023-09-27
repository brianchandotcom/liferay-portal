/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ORDER_TYPE_PATH = '/order-types';

const ORDER_TYPE_RULES_PATH = '/order-type-channels';

const VERSION = 'v1.0';

function resolvePath(basePath = '', orderTypeId = '', orderTypeChannelId = '') {
	return `${basePath}${VERSION}${ORDER_TYPE_PATH}/${orderTypeId}${ORDER_TYPE_RULES_PATH}/${orderTypeChannelId}`;
}

export default function OrderTypeChannel(basePath) {
	return {
		addOrderTypeChannel: (orderTypeId, json) =>
			AJAX.POST(resolvePath(basePath, orderTypeId), json),
	};
}
