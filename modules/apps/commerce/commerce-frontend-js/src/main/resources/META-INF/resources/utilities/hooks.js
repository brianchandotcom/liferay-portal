/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';

import {
	CURRENT_ACCOUNT_UPDATED,
	CURRENT_ORDER_UPDATED,
} from './eventsDefinitions';
import {getComponentByModuleUrl} from './modules';

export function useLiferayModule(
	moduleUrl,
	LoadingComponent = ClayLoadingIndicator
) {
	const [Component, updateComponent] = useState(
		moduleUrl ? LoadingComponent : null
	);

	useEffect(() => {
		if (moduleUrl) {
			getComponentByModuleUrl(moduleUrl).then((module) => {
				updateComponent(() => module);
			});
		}
	}, [moduleUrl]);

	return Component;
}

export function usePersistentState(key, initialState = null) {
	const [persistentState, setPersistentState] = useState(
		() => JSON.parse(localStorage.getItem(key)) || initialState
	);
	useEffect(() => {
		try {
			if (
				typeof persistentState === 'undefined' ||
				persistentState === null
			) {
				localStorage.removeItem(key);
			}
			else {
				localStorage.setItem(key, JSON.stringify(persistentState));
			}
		}
		catch {
			return;
		}
	}, [key, persistentState]);

	return [persistentState, setPersistentState];
}

export function useCommerceAccount(initialCommerceAccount) {
	const [commerceAccount, updateCommerceAccount] = useState(
		initialCommerceAccount
	);

	useEffect(() => {
		function handleAccountUpdate(account) {
			if (commerceAccount.id !== account.id) {
				updateCommerceAccount(account);
			}
		}

		Liferay.on(CURRENT_ACCOUNT_UPDATED, handleAccountUpdate);

		return () => {
			Liferay.detach(CURRENT_ACCOUNT_UPDATED, handleAccountUpdate);
		};
	}, [commerceAccount]);

	return commerceAccount;
}

export function useCommerceCart(initialCart) {
	const [commerceCart, updateCommerceCart] = useState(initialCart);

	useEffect(() => {
		function handleOrderUpdate(order) {
			if (commerceCart.id !== order.id) {
				updateCommerceCart(order);
			}
		}

		Liferay.on(CURRENT_ORDER_UPDATED, handleOrderUpdate);

		return () => {
			Liferay.detach(CURRENT_ORDER_UPDATED, handleOrderUpdate);
		};
	}, [commerceCart]);

	return commerceCart;
}
