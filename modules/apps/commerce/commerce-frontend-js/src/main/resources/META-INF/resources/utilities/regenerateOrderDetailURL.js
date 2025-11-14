/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DEFAULT_ORDER_DETAILS_PORTLET_ID,
	ORDER_UUID_PARAMETER,
} from './constants';

export function regenerateOrderDetailURL(
	baseOrderDetailURL,
	orderId,
	orderUUID
) {
	if (!baseOrderDetailURL) {
		throw new Error(
			'Cannot generate a new Order Detail URL. Invalid "baseOrderDetailURL"'
		);
	}

	if (baseOrderDetailURL.includes(DEFAULT_ORDER_DETAILS_PORTLET_ID)) {
		if (!orderUUID) {
			throw new Error(
				'Cannot generate a new Order Detail URL. Invalid "orderUUID"'
			);
		}

		const orderDetailURL = new URL(baseOrderDetailURL);

		orderDetailURL.searchParams.append(
			`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_${ORDER_UUID_PARAMETER}`,
			orderUUID
		);

		return orderDetailURL.toString();
	}
	else {
		if (!orderId) {
			throw new Error(
				'Cannot generate a new Order Detail URL. Invalid "orderId"'
			);
		}

		return `${baseOrderDetailURL}${orderId}`;
	}
}
