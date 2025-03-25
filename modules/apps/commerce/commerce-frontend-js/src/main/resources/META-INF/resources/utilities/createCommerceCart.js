/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {createPortletURL} from 'frontend-js-web';

import ServiceProvider from '../ServiceProvider/index';
import {resetCommerceCurrency} from '../components/currency_selector/util';
import {
	DEFAULT_ORDER_DETAILS_PORTLET_ID,
	ORDER_ID_PARAMETER,
} from '../components/mini_cart/util/constants';
import {liferayNavigate} from './index';
import {selectOrderType} from './modals/selectOrderType';

const DeliveryCartAPI = ServiceProvider.DeliveryCartAPI('v1');

export function createCommerceCart({
	accountId,
	currencyCode,
	commerceChannelId,
	onCancel = () => {},
	onCreate = () => {},
	orderDetailURL,
	orderTypes = [],
}) {
	const onBeforeCreate =
		orderTypes.length > 1 ? selectOrderType : () => Promise.resolve(null);

	return onBeforeCreate(orderTypes)
		.then((orderTypeId = null) =>
			DeliveryCartAPI.createCartByChannelId(commerceChannelId, {
				accountId,
				currencyCode,
				...(orderTypeId ? {orderTypeId} : {}),
			})
		)
		.then(({id: cartId = null}) => {
			if (cartId) {
				resetCommerceCurrency();

				onCreate();

				const redirectURL = orderDetailURL.includes(
					DEFAULT_ORDER_DETAILS_PORTLET_ID
				)
					? createPortletURL(orderDetailURL, {
							[ORDER_ID_PARAMETER]: cartId,
						})
					: `${orderDetailURL}${cartId}`;

				return liferayNavigate(redirectURL);
			}
		})
		.catch(({message, title}) => {
			onCancel();

			if (message !== 'cancel') {
				openToast({
					message:
						title ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'danger',
				});
			}
		});
}

export default function onCreateCommerceCart({additionalProps, orderTypes}) {
	const handler = () => createCommerceCart({...additionalProps, orderTypes});

	Liferay.on('createCommerceCart', handler);

	return {
		dispose: () => {
			Liferay.detach('createCommerceCart', handler);
		},
	};
}
