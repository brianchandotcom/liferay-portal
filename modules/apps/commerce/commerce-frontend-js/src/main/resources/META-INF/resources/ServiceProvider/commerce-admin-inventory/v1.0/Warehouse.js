/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const WAREHOUSES_PATH = '/warehouses';

const VERSION = 'v1.0';

function resolvePath(basePath = '', warehouseId = '') {
	return `${basePath}${VERSION}${WAREHOUSES_PATH}/${warehouseId}`;
}

export default function Warehouse(basePath) {
	return {
		addWarehouse: (json) => AJAX.POST(resolvePath(basePath), json),
	};
}
