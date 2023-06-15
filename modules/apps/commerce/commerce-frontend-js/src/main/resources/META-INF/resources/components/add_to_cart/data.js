/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ServiceProvider from '../../ServiceProvider/index';
import {CURRENT_ORDER_UPDATED} from '../../utilities/eventsDefinitions';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

function formatCartItem(cpInstance) {
	return {
		options: JSON.stringify(cpInstance.skuOptions || []),
		quantity: cpInstance.quantity,
		replacedSkuId: cpInstance.replacedSkuId ?? 0,
		skuId: cpInstance.skuId,
	};
}

export async function addToCart(
	cpInstances,
	cartId,
	channel,
	accountId,
	orderTypeId
) {
	if (!cartId) {
		const newCart = await CartResource.createCartByChannelId(channel.id, {
			accountId,
			cartItems: cpInstances.map(formatCartItem),
			currencyCode: channel.currencyCode,
			orderTypeId,
		});

		Liferay.fire(CURRENT_ORDER_UPDATED, {order: newCart});

		return newCart;
	}

	if (cpInstances.length === 1) {
		await CartResource.createItemByCartId(
			cartId,
			formatCartItem(cpInstances[0])
		);

		const updatedCart = await CartResource.getCartByIdWithItems(cartId);

		Liferay.fire(CURRENT_ORDER_UPDATED, {order: updatedCart});

		return updatedCart;
	}

	const fetchedCart = await CartResource.getCartByIdWithItems(cartId);

	const updatedCartItems = fetchedCart.cartItems;

	cpInstances.forEach((cpInstance) => {
		const includedCartItem = updatedCartItems.find((cartItem) => {
			return (
				cartItem.skuId === cpInstance.skuId &&
				cartItem.options === JSON.stringify(cpInstance.skuOptions)
			);
		});

		if (includedCartItem) {
			includedCartItem.quantity += cpInstance.quantity;
		}
		else {
			updatedCartItems.push(formatCartItem(cpInstance));
		}
	});

	const updatedCart = await CartResource.updateCartById(cartId, {
		cartItems: updatedCartItems,
	});

	Liferay.fire(CURRENT_ORDER_UPDATED, {order: updatedCart});

	return updatedCart;
}
