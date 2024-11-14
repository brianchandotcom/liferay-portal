/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const WAREHOUSES_PATH = '/warehouses';

const WAREHOUSE_RULES_PATH = '/warehouse-accounts';

const VERSION = 'v1.0';

function resolvePath(basePath = '', warehouseId = '', warehouseAccountId = '') {
	return `${basePath}${VERSION}${WAREHOUSES_PATH}/${warehouseId}${WAREHOUSE_RULES_PATH}/${warehouseAccountId}`;
}

export default function WarehouseAccount(basePath) {
	return {
		addWarehouseAccount: (warehouseId, json) =>
			AJAX.POST(resolvePath(basePath, warehouseId), json),
	};
}
