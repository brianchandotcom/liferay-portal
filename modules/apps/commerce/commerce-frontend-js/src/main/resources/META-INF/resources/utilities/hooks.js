/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';

import {GUEST_COMMERCE_ORDER_COOKIE_IDENTIFIER} from '../components/add_to_cart/constants';
import CommerceCookie from './cookies';
import {
	CURRENT_ACCOUNT_UPDATED,
	CURRENT_ORDER_UPDATED,
} from './eventsDefinitions';
import {getComponentByModuleUrl} from './modules';

export function useLiferayModule(
	moduleUrl,
	LoadingComponent = ClayLoadingIndicator
) {
	const [Component, setComponent] = useState(
		moduleUrl ? LoadingComponent : null
	);

	useEffect(() => {
		if (moduleUrl) {
			getComponentByModuleUrl(moduleUrl).then((module) => {
				setComponent(() => module);
			});
		}
	}, [moduleUrl]);

	return Component;
}

export function useCommerceAccount(initialCommerceAccount) {
	const [commerceAccount, setCommerceAccount] = useState(
		initialCommerceAccount
	);

	useEffect(() => {
		function handleAccountUpdate(account) {
			if (commerceAccount.id !== account.id) {
				setCommerceAccount(account);
			}
		}

		Liferay.on(CURRENT_ACCOUNT_UPDATED, handleAccountUpdate);

		return () => {
			Liferay.detach(CURRENT_ACCOUNT_UPDATED, handleAccountUpdate);
		};
	}, [commerceAccount]);

	return commerceAccount;
}

const orderCookie = new CommerceCookie(GUEST_COMMERCE_ORDER_COOKIE_IDENTIFIER);

export function useCommerceCart({guestOrderEnabled = false, initialCart}) {
	const [commerceCart, setCommerceCart] = useState(initialCart);

	useEffect(() => {
		function handleOrderUpdate({order}) {
			if (commerceCart.id !== order.id) {
				setCommerceCart(order);

				const {commerceChannelGroupId = 0} = Liferay.CommerceContext;

				if (
					commerceChannelGroupId &&
					guestOrderEnabled &&
					order.orderUUID &&
					order.createDate
				) {
					orderCookie.setValue(
						commerceChannelGroupId,
						`${order.orderUUID}|${order.createDate}`
					);
				}
			}
		}

		Liferay.on(CURRENT_ORDER_UPDATED, handleOrderUpdate);

		return () => {
			Liferay.detach(CURRENT_ORDER_UPDATED, handleOrderUpdate);
		};
	}, [commerceCart, guestOrderEnabled]);

	return commerceCart;
}
