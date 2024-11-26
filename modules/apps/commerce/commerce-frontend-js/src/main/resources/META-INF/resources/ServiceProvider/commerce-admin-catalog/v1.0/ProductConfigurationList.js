/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PRODUCT_CONFIGURATION_LISTS_PATH = '/product-configuration-lists';

const VERSION = 'v1.0';

function resolvePath(basePath = '', productConfigurationListId = '') {
	return `${basePath}${VERSION}${PRODUCT_CONFIGURATION_LISTS_PATH}/${productConfigurationListId}`;
}

export default function ProductConfigurationList(basePath) {
	return {
		addProductConfigurationList: (json) =>
			AJAX.POST(`${resolvePath(basePath)}`, json),
	};
}
