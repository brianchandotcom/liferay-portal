/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createPortletURL, fetch, openToast} from 'frontend-js-web';

function returnableOrderItemsPropsTransformer({
	additionalProps: context,
	...props
}) {
	return {
		...props,
		onBulkActionItemClick: async ({
			action: {
				data: {method},
				href: createReturnAPIEndpoint,
			},
			selectedData: {items: commerceOrderItems},
		}) => {
			const {
				accountEntryId,
				channelGroupId,
				channelId,
				channelName,
				commerceOrderId,
				redirect,
			} = context;

			const commerceReturn = JSON.stringify({
				channelGroupId: parseInt(channelGroupId, 10),
				channelId: parseInt(channelId, 10),
				channelName,
				commerceReturnToCommerceReturnItems: commerceOrderItems.map(
					({
						id: commerceOrderItemId,
						price: {finalPrice: amount},
						quantity,
					}) => ({
						amount,
						commerceOrderItemId,
						quantity,
					})
				),
				r_accountToCommerceReturns_accountEntryId: parseInt(
					accountEntryId,
					10
				),
				r_commerceOrderToCommerceReturns_commerceOrderId: parseInt(
					commerceOrderId,
					10
				),
			});

			fetch(createReturnAPIEndpoint, {
				body: commerceReturn,
				headers: {
					'content-type': 'application/json',
				},
				method,
			})
				.then((response) => response.json())
				.then((response) => {
					window.top.location.href = createPortletURL(redirect, {
						commerceReturnId: response.id,
					});
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'danger',
					});
				});
		},
	};
}

export default returnableOrderItemsPropsTransformer;
