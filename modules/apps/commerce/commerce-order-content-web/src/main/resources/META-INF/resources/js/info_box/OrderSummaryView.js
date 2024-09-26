/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	SummaryComponent as Summary,
} from 'commerce-frontend-js';
import React, {useEffect, useState} from 'react';

const orderSummaryDataMapper = (order) => {
	return [
		{
			label: Liferay.Language.get('subtotal'),
			value: order?.summary?.subtotalFormatted ?? '--',
		},
		{
			label: Liferay.Language.get('subtotal-discount'),
			value: order?.summary?.subtotalDiscountValueFormatted ?? '--',
		},
		{
			label: Liferay.Language.get('total-discount'),
			value: order?.summary?.totalDiscountValueFormatted ?? '--',
		},
		{
			label: Liferay.Language.get('promotion-code'),
			value: order.couponCode || '--',
		},
		{
			label: Liferay.Language.get('tax'),
			value: order?.summary?.taxValueFormatted ?? '--',
		},
		{
			label: Liferay.Language.get('delivery'),
			value: order?.summary?.shippingValueFormatted ?? '--',
		},
		{
			label: Liferay.Language.get('delivery-discount'),
			value: order?.summary?.shippingDiscountValueFormatted ?? '--',
		},
		{
			style: 'divider',
		},
		{
			label: Liferay.Language.get('total'),
			style: 'big',
			value: order?.summary?.totalFormatted,
		},
	];
};

const OrderSummaryView = ({elementId, isOpen, label, namespace, orderId}) => {
	const [orderSummary, setOrderSummary] = useState(null);

	useEffect(() => {
		const getOrder = isOpen
			? CommerceServiceProvider.DeliveryCartAPI('v1').getCartById
			: CommerceServiceProvider.DeliveryCartAPI('V1').getPlacedOrderById;

		getOrder(orderId).then((order) => {
			setOrderSummary(order);
		});
	}, [isOpen, orderId]);

	return (
		<div className={namespace + 'info-box'} id={elementId}>
			{label ? (
				<div className="align-items-center d-flex">
					<div className="h5 info-box-label m-0">{label}</div>
				</div>
			) : null}

			<div>
				{orderSummary ? (
					<Summary
						dataMapper={orderSummaryDataMapper}
						summaryData={orderSummary}
					/>
				) : null}
			</div>
		</div>
	);
};

export default OrderSummaryView;
