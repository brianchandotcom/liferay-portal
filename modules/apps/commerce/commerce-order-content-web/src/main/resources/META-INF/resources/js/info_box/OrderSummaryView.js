/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	SummaryComponent as Summary,
	commerceEvents,
} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

import {getOrder} from '../util';

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

	const onStatusChange = useCallback(
		({order = null}) => {
			getOrder(isOpen, order, orderId)
				.then((order) => {
					setOrderSummary(order);
				})
				.catch((error) => {
					openToast({
						message:
							error.message ||
							Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						type: 'danger',
					});
				});
		},
		[isOpen, orderId]
	);

	useEffect(() => {
		onStatusChange({order: null});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		Liferay.on(commerceEvents.CART_UPDATED, onStatusChange);
		Liferay.on(commerceEvents.ORDER_INFORMATION_ALTERED, onStatusChange);

		return () => {
			Liferay.detach(commerceEvents.CART_UPDATED, onStatusChange);
			Liferay.detach(
				commerceEvents.ORDER_INFORMATION_ALTERED,
				onStatusChange
			);
		};
	}, [onStatusChange]);

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
