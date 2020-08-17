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
	CHANNELS_PATH = '/channels';

const VERSION = 'v1.0';

function resolveCartsPath(basePath = '', cartId) {
	return `${basePath}${VERSION}${CARTS_PATH}/${cartId}`;
}

function resolveChannelsPath(basePath = '', channelId) {
	return `${basePath}${VERSION}${CHANNELS_PATH}/${channelId}${CARTS_PATH}`;
}

export default (basePath) => ({
	createCartByChannelId: (channelId, json) =>
		AJAX.POST(resolveChannelsPath(basePath, channelId), json),

	createCouponCodeByCartId: (cartId, json) =>
		AJAX.POST(`${resolveCartsPath(basePath, cartId)}/coupon-code`, json),

	deleteCartById: (cartId) => AJAX.DELETE(resolveCartsPath(basePath, cartId)),

	getCartById: (cartId) => AJAX.GET(resolveCartsPath(basePath, cartId)),

	getCartByIdWithItems: (cartId) =>
		AJAX.GET(
			resolveCartsPath(basePath, cartId) + '?nestedFields=cartItems'
		),

	getCartsByChannelId: (channelId) =>
		AJAX.GET(resolveChannelsPath(basePath, channelId)),

	replaceCartById: (cartId, json) =>
		AJAX.PUT(resolveCartsPath(basePath, cartId), json),

	updateCartById: (cartId, jsonProps) =>
		AJAX.PATCH(resolveCartsPath(basePath, cartId), jsonProps),
});
