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
