/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const PLACED_ORDERS_PATH = '/placed-orders';
const CHANNELS_PATH = '/channels';

const VERSION = 'v1.0';

function resolvePlacedOrdersPath(basePath = '', placedOrderId) {
	return `${basePath}${VERSION}${PLACED_ORDERS_PATH}/${placedOrderId}`;
}

function resolveChannelsPath(basePath = '', channelId) {
	return `${basePath}${VERSION}${CHANNELS_PATH}/${channelId}`;
}

function resolvePlacedOrdersByAccountIdAndChannelIdPath(
	basePath = '',
	accountId,
	channelId,
	searchParams
) {
	const url = new URL(
		`${Liferay.ThemeDisplay.getPathContext()}${resolveChannelsPath(
			basePath,
			channelId
		)}/account/${accountId}${PLACED_ORDERS_PATH}`,
		Liferay.ThemeDisplay.getPortalURL()
	);

	if (searchParams) {
		Object.keys(searchParams).forEach((searchParamKey) => {
			url.searchParams.set(searchParamKey, searchParams[searchParamKey]);
		});
	}

	return url.pathname + url.search;
}

export default function PlacedOrder(basePath) {
	return {
		executeOrderTransitionsById: (placedOrderId, json) =>
			AJAX.POST(
				resolvePlacedOrdersPath(basePath, placedOrderId) +
					'/order-transitions',
				json
			),

		getOrderTransitionsById: (placedOrderId) =>
			AJAX.GET(
				resolvePlacedOrdersPath(basePath, placedOrderId) +
					'/order-transitions'
			),

		getPlacedOrderById: (placedOrderId) =>
			AJAX.GET(resolvePlacedOrdersPath(basePath, placedOrderId)),

		placedOrdersByAccountIdAndChannelIdURL: (accountId, channelId) =>
			resolvePlacedOrdersByAccountIdAndChannelIdPath(
				basePath,
				accountId,
				channelId
			),

		updatePlacedOrderById: (placedOrderId, json) =>
			AJAX.PATCH(
				resolvePlacedOrdersPath(basePath, placedOrderId) +
					'?nestedFields=placedOrderItems',
				json
			),
	};
}
