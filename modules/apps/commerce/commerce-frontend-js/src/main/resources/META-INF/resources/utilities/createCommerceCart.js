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
	accountId = null,
	cartItems = [],
	commerceChannelId = null,
	currencyCode = null,
	onCancel = () => {},
	onCreate = () => {},
	orderDetailURL = null,
	orderTypes: externalOrderTypes = [],
	skipRedirect = false,
}) {
	const orderTypes = externalOrderTypes.length
		? externalOrderTypes
		: Liferay?.CommerceContext?.orderTypes;

	const onBeforeCreate =
		orderTypes.length > 1 ? selectOrderType : () => Promise.resolve(null);

	return onBeforeCreate(orderTypes)
		.then((orderTypeId = null) => {
			const channelId =
				commerceChannelId ?? Liferay.CommerceContext.commerceChannelId;

			return DeliveryCartAPI.createCartByChannelId(channelId, {
				accountId:
					accountId ?? Liferay?.CommerceContext?.account?.accountId,
				cartItems,
				currencyCode:
					currencyCode ??
					Liferay?.CommerceContext?.currency?.currencyCode,
				...(orderTypeId ? {orderTypeId} : {}),
			});
		})
		.then((cart) => {
			if (cart.id) {
				resetCommerceCurrency();

				onCreate();

				if (orderDetailURL && !skipRedirect) {
					const redirectURL = orderDetailURL.includes(
						DEFAULT_ORDER_DETAILS_PORTLET_ID
					)
						? createPortletURL(orderDetailURL, {
								[ORDER_ID_PARAMETER]: cart.id,
							})
						: `${orderDetailURL}${cart.id}`;

					return liferayNavigate(redirectURL);
				}
			}

			return Promise.resolve(cart);
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
