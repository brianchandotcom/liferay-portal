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

import ServiceProvider from '../../ServiceProvider/index';
import {CURRENT_ORDER_UPDATED} from '../../utilities/eventsDefinitions';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

function formatCartItem(cpInstance) {
	return {
		options: JSON.stringify(cpInstance.options || []),
		quantity: cpInstance.quantity,
		skuId: cpInstance.skuId,
	};
}

export async function addToCart(cpInstances, cartId, channel, accountId) {
	if (!cartId) {
		const newCart = await CartResource.createCartByChannelId(channel.id, {
			accountId,
			cartItems: cpInstances.map(formatCartItem),
			currencyCode: channel.currencyCode,
		});

		Liferay.fire(CURRENT_ORDER_UPDATED, {id: newCart.id});

		return newCart;
	}

	if (cpInstances.length === 1) {
		await CartResource.createItemByCartId(
			cartId,
			formatCartItem(cpInstances[0])
		);

		Liferay.fire(CURRENT_ORDER_UPDATED, {id: cartId});

		return;
	}

	const fetchedCart = await CartResource.getCartById(cartId);
	const updatedCartItems = fetchedCart.cartItems;

	cpInstances.forEach((cpInstance) => {
		const includedCartItem = updatedCartItems.find((cartItem) => {
			return (
				cartItem.skuId === cpInstance.skuId &&
				cartItem.options === cpInstance.options
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

	Liferay.fire(CURRENT_ORDER_UPDATED, updatedCart);

	return updatedCart;
}
