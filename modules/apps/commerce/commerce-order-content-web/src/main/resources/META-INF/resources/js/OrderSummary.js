/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {commerceEvents} from 'commerce-frontend-js';

function updateSummaryValue(order) {
	const fieldElements = document.querySelectorAll(
		'[data-summary-field-name]'
	);

	fieldElements.forEach((fieldElement) => {
		const fieldName = fieldElement.getAttribute('data-summary-field-name');

		if (fieldName === 'couponCode') {
			fieldElement.innerHTML = order[fieldName] || '--';
		}
		else {
			fieldElement.innerHTML = order?.summary[fieldName] || '--';
		}
	});
}

export default function initOrderSummaryEvent({orderId}) {
	const handler = ({order}) => {
		if (order.id === parseInt(orderId, 10)) {
			updateSummaryValue(order);
		}
	};

	const {orderSummaryInitialized = false} =
		window.Liferay?.CommerceContext ?? {};

	if (!orderSummaryInitialized) {
		window.Liferay['CommerceContext']['orderSummaryInitialized'] = true;

		Liferay.on(commerceEvents.CART_UPDATED, handler);
	}

	return {
		dispose() {
			window.Liferay['CommerceContext']['orderSummaryInitialized'] =
				false;
			Liferay.detach(commerceEvents.CART_UPDATED, handler);
		},
	};
}
