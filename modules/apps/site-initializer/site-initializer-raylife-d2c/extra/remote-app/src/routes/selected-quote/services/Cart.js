/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-commerce-delivery-cart';

export function getPaymentMethods(orderId) {
	return axios.get(`${DeliveryAPI}/v1.0/carts/${orderId}/payment-methods`);
}

export function getPaymentMethodURL(orderId, callbackURL) {
	return axios.get(
		`${DeliveryAPI}/v1.0/carts/${orderId}/payment-url?callbackURL=${callbackURL}`
	);
}

export function checkoutOrder(orderId) {
	return axios.post(`${DeliveryAPI}/v1.0/carts/${orderId}/checkout`);
}
