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

const CARTS_PATH = '/carts',
	CART_ITEMS_PATH = '/cart-items',
	ITEMS_PATH = '/items';

const VERSION = 'v1.0';

function resolveItemsPath(basePath = '', cartId) {
	return `${basePath}${VERSION}${CARTS_PATH}/${cartId}${ITEMS_PATH}`;
}

function resolveCartItemsPath(basePath = '', itemId) {
	return `${basePath}${VERSION}${CART_ITEMS_PATH}/${itemId}`;
}

export default (basePath) => ({
	createItemByCartId: (cartId, json) =>
		AJAX.POST(resolveItemsPath(basePath, cartId), json),

	deleteItemById: (itemId) =>
		AJAX.DELETE(resolveCartItemsPath(basePath, itemId)),

	getItemById: (itemId) => AJAX.GET(resolveCartItemsPath(basePath, itemId)),

	getItemsByCartId: (cartId) => AJAX.GET(resolveItemsPath(basePath, cartId)),

	replaceItemById: (itemId, json) =>
		AJAX.PUT(resolveCartItemsPath(basePath, itemId), json),

	updateItemById: (itemId, jsonProps) =>
		AJAX.PATCH(resolveCartItemsPath(basePath, itemId), jsonProps),
});
