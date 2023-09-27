/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const WAREHOUSES_PATH = '/warehouses';

const WAREHOUSE_RULES_PATH = '/warehouse-channels';

const VERSION = 'v1.0';

function resolvePath(basePath = '', warehouseId = '', warehouseChannelId = '') {
	return `${basePath}${VERSION}${WAREHOUSES_PATH}/${warehouseId}${WAREHOUSE_RULES_PATH}/${warehouseChannelId}`;
}

export default function WarehouseChannel(basePath) {
	return {
		addWarehouseChannel: (warehouseId, json) =>
			AJAX.POST(resolvePath(basePath, warehouseId), json),
	};
}
