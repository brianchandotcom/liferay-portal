/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRODUCT_CONFIGURATION_LISTS_PATH = '/product-configuration-lists';

const PRODUCT_CONFIGURATION_LIST_RULES_PATH =
	'/product-configuration-list-order-types';

const VERSION = 'v1.0';

function resolvePath(
	basePath = '',
	productConfigurationListId = '',
	productConfigurationListOrderTypeId = ''
) {
	return `${basePath}${VERSION}${PRODUCT_CONFIGURATION_LISTS_PATH}/${productConfigurationListId}${PRODUCT_CONFIGURATION_LIST_RULES_PATH}/${productConfigurationListOrderTypeId}`;
}

export default function ProductConfigurationListChannel(basePath) {
	return {
		addProductConfigurationListOrderType: (
			productConfigurationListId,
			json
		) => AJAX.POST(resolvePath(basePath, productConfigurationListId), json),
	};
}
