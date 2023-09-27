/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const CARTS_PATH = '/carts';
const CART_ITEMS_PATH = '/cart-items';
const ITEMS_PATH = '/items';

const VERSION = 'v1.0';

function resolveItemsPath(basePath = '', cartId) {
	return `${basePath}${VERSION}${CARTS_PATH}/${cartId}${ITEMS_PATH}`;
}

function resolveCartItemsPath(basePath = '', itemId) {
	return `${basePath}${VERSION}${CART_ITEMS_PATH}/${itemId}`;
}

export default function CartItem(basePath) {
	return {
		createItemByCartId: (cartId, json) =>
			AJAX.POST(resolveItemsPath(basePath, cartId), json),

		deleteItemById: (itemId) =>
			AJAX.DELETE(resolveCartItemsPath(basePath, itemId)),

		getItemById: (itemId) =>
			AJAX.GET(resolveCartItemsPath(basePath, itemId)),

		getItemsByCartId: (cartId) =>
			AJAX.GET(resolveItemsPath(basePath, cartId)),

		replaceItemById: (itemId, json) =>
			AJAX.PUT(resolveCartItemsPath(basePath, itemId), json),

		updateItemById: (itemId, jsonProps) =>
			AJAX.PATCH(resolveCartItemsPath(basePath, itemId), jsonProps),
	};
}
