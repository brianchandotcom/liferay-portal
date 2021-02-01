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

const PRODUCT_GROUPS_PATH = '/product-groups';

const VERSION = 'v1.0';

function resolvePath(basePath = '', productGroupId = '') {
	return `${basePath}${VERSION}${PRODUCT_GROUPS_PATH}/${productGroupId}`;
}

export default (basePath) => ({
	addProductGroup: (json) => AJAX.POST(`${resolvePath(basePath)}`, json),

	addProductToProductGroup: (id, json) =>
		AJAX.POST(`${resolvePath(basePath, id)}/product-group-products`, json),
});
