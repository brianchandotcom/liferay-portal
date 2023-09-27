/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRODUCT_GROUPS_PATH = '/product-groups';

const VERSION = 'v1.0';

function resolvePath(basePath = '', productGroupId = '') {
	return `${basePath}${VERSION}${PRODUCT_GROUPS_PATH}/${productGroupId}`;
}

export default function ProductGroups(basePath) {
	return {
		addProductGroup: (json) => AJAX.POST(`${resolvePath(basePath)}`, json),

		addProductToProductGroup: (id, json) =>
			AJAX.POST(
				`${resolvePath(basePath, id)}/product-group-products`,
				json
			),
	};
}
