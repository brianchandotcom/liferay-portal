/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const CARTS_PATH = '/carts';
const CHANNELS_PATH = '/channels';

const VERSION = 'v1.0';

function resolveCartsPath(basePath = '', cartId) {
	return `${basePath}${VERSION}${CARTS_PATH}/${cartId}`;
}

function resolveChannelsPath(basePath = '', channelId) {
	return `${basePath}${VERSION}${CHANNELS_PATH}/${channelId}`;
}

function resolveCartsByAccountIdAndChannelIdPath(
	basePath = '',
	accountId,
	channelId,
	searchParams
) {
	const url = new URL(
		`${Liferay.ThemeDisplay.getPathContext()}${resolveChannelsPath(
			basePath,
			channelId
		)}/account/${accountId}${CARTS_PATH}`,
		Liferay.ThemeDisplay.getPortalURL()
	);

	if (searchParams) {
		Object.keys(searchParams).forEach((searchParamKey) => {
			url.searchParams.set(searchParamKey, searchParams[searchParamKey]);
		});
	}

	return url.pathname + url.search;
}

export default function Cart(basePath) {
	return {
		cartsByAccountIdAndChannelIdURL: (accountId, channelId) =>
			resolveCartsByAccountIdAndChannelIdPath(
				basePath,
				accountId,
				channelId
			),

		createCartByChannelId: (channelId, json) =>
			AJAX.POST(
				`${resolveChannelsPath(
					basePath,
					channelId
				)}${CARTS_PATH}?nestedFields=cartItems`,
				json
			),

		createCouponCodeByCartId: (cartId, json) =>
			AJAX.POST(
				`${resolveCartsPath(basePath, cartId)}/coupon-code`,
				json
			),

		deleteCartById: (cartId) =>
			AJAX.DELETE(resolveCartsPath(basePath, cartId)),

		getCartById: (cartId) => AJAX.GET(resolveCartsPath(basePath, cartId)),

		getCartByIdWithItems: (cartId) =>
			AJAX.GET(
				resolveCartsPath(basePath, cartId) + '?nestedFields=cartItems'
			),

		getCartsByAccountIdAndChannelId: (accountId, channelId, searchParams) =>
			AJAX.GET(
				resolveCartsByAccountIdAndChannelIdPath(
					basePath,
					accountId,
					channelId,
					searchParams
				)
			),

		replaceCartById: (cartId, json) =>
			AJAX.PUT(resolveCartsPath(basePath, cartId), json),

		updateCartById: (cartId, jsonProps) =>
			AJAX.PATCH(
				resolveCartsPath(basePath, cartId) + '?nestedFields=cartItems',
				jsonProps
			),
	};
}
