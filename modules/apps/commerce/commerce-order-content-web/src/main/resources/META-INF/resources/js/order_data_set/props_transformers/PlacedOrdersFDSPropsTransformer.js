/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';

import {openOrderNameModal} from './util';

const DeliveryOrderAPI = CommerceServiceProvider.DeliveryOrderAPI('v1');

const PlacedOrdersFDSPropsTransformer = (props) => ({
	...props,
	onActionDropdownItemClick: ({
		action: {
			data: {id: actionId},
		},
		itemData: {accountId, id: orderId, name: orderName},
	}) => {
		if (actionId === 'rename') {
			openOrderNameModal({
				dataSetId: props.id,
				isOpen: false,
				orderId,
				orderName,
			});
		}

		if (actionId === 'reorder') {
			DeliveryOrderAPI.getOrderTransitionsById(orderId)
				.then(({items: cartTransitions}) => {
					const isReorderAvailable = cartTransitions.find(
						({name}) => name === 'reorder'
					);

					if (isReorderAvailable) {
						DeliveryOrderAPI.executeOrderTransitionsById(orderId, {
							name: 'reorder',
						}).then(({orderId}) => {
							window.location.href = `${props?.additionalProps.orderDetailURL}${orderId}`;
						});
					}
					else {
						return Promise.reject();
					}
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

		if (actionId === 'return') {
			Liferay.fire(`${props.namespace || ''}makeReturn`, {
				accountId,
				orderId,
			});
		}
	},
});

export default PlacedOrdersFDSPropsTransformer;
