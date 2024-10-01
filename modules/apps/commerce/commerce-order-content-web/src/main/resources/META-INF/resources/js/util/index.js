/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {isObject} from 'frontend-js-web';
import React from 'react';

const EDITABLE_FIELDS = [
	'billingAddress',
	'paymentMethod',
	'purchaseOrderDocument',
	'requestedDeliveryDate',
	'shippingAddress',
	'shippingMethod',
];
export const PAYMENT_METHOD_TYPE_OFFLINE = 2;

export function formatValue(value, type) {
	if (type === 'address' && value) {
		try {
			const address = isObject(value)
				? value
				: JSON.parse(Liferay.Util.unescapeHTML(value));

			return (
				<>
					<p className="mb-1">{address.name}</p>

					<p className="mb-1">{`${address.street1}, ${address.zip}, ${address.city}`}</p>

					<p>{`${address.region ? `${address.region}, ` : ''}${address.country}`}</p>
				</>
			);
		}
		catch (error) {
			console.error(error);

			return null;
		}
	}
	else if (type === 'date' && value) {
		const date = new Intl.DateTimeFormat(
			Liferay.ThemeDisplay.getBCP47LanguageId(),
			{dateStyle: 'short'}
		).format(new Date(value));

		return <p>{date}</p>;
	}

	return <p>{value}</p>;
}

export function isEditable(field, isOpen) {
	if (EDITABLE_FIELDS.includes(field) && !isOpen) {
		return false;
	}

	return true;
}

export async function getOrder(isOpenOrder, order = null, orderId) {
	if (order) {
		return Promise.resolve(order);
	}

	return isOpenOrder
		? CommerceServiceProvider.DeliveryCartAPI('v1').getCartById(orderId)
		: CommerceServiceProvider.DeliveryOrderAPI('v1').getPlacedOrderById(
				orderId
			);
}
