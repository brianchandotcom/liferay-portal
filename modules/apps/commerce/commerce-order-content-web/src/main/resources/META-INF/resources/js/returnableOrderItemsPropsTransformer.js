/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ServiceProvider from 'commerce-frontend-js/ServiceProvider/index';
import {createPortletURL, openToast} from 'frontend-js-web';

function returnableOrderItemsPropsTransformer({
	additionalProps: context,
	...props
}) {
	return {
		...props,
		onBulkActionItemClick: async ({
			selectedData: {items: commerceOrderItems},
		}) => {
			const CommerceReturnResource = ServiceProvider.ReturnAPI();

			const {
				accountEntryId,
				channelGroupId,
				channelId,
				channelName,
				commerceOrderId,
				commerceReturnId,
				redirect,
			} = context;

			const commerceReturn = {
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
						quantity,
						r_accountToCommerceReturnItems_accountEntryId: parseInt(
							accountEntryId,
							10
						),
						r_commerceOrderItemToCommerceReturnItems_commerceOrderItemId: commerceOrderItemId,
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
			};

			if (parseInt(commerceReturnId, 10)) {
				return CommerceReturnResource.updateItemById(
					commerceReturnId,
					commerceReturn
				)
					.then((response) => {
						window.top.location.href = createPortletURL(redirect, {
							commerceReturnId: response.id,
						});

						openToast({
							message: Liferay.Language.get(
								'your-request-completed-successfully'
							),
							type: 'success',
						});
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
			}

			return CommerceReturnResource.createItem(commerceReturn)
				.then((response) => {
					window.top.location.href = createPortletURL(redirect, {
						commerceReturnId: response.id,
					});

					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						type: 'success',
					});
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
	};
}

export default returnableOrderItemsPropsTransformer;
