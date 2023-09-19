/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ServiceProvider from '../../ServiceProvider/index';
import {CURRENT_ORDER_UPDATED} from '../../utilities/eventsDefinitions';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

function formatCartItem(
	cpInstance,
	namespace,
	skuOptions,
	skuOptionsNamespace
) {
	let optionsJSON = cpInstance.skuOptions || [];

	if (namespace && skuOptionsNamespace && namespace === skuOptionsNamespace) {
		optionsJSON = skuOptions;
	}
	else if (optionsJSON.length) {
		optionsJSON = optionsJSON.map((optionJSON) => ({
			...optionJSON,
			value: optionJSON.skuOptionValueKey || optionJSON.value,
		}));
	}

	return {
		options: JSON.stringify(optionsJSON),
		quantity: Number(
			Number(cpInstance.quantity).toFixed(
				cpInstance.skuUnitOfMeasure?.precision || 0
			)
		),
		replacedSkuId: cpInstance.replacedSkuId ?? 0,
		skuId: cpInstance.skuId,
		skuUnitOfMeasure: cpInstance.skuUnitOfMeasure,
	};
}

export async function addToCart(
	cpInstances,
	cartId,
	channel,
	accountId,
	orderTypeId,
	namespace,
	skuOptions,
	skuOptionsNamespace
) {
	if (!cartId) {
		const newCart = await CartResource.createCartByChannelId(channel.id, {
			accountId,
			cartItems: cpInstances.map((cpInstance) =>
				formatCartItem(
					cpInstance,
					namespace,
					skuOptions,
					skuOptionsNamespace
				)
			),
			currencyCode: channel.currencyCode,
			orderTypeId,
		});

		Liferay.fire(CURRENT_ORDER_UPDATED, {order: newCart});

		return newCart;
	}

	const fetchedCart = await CartResource.getCartByIdWithItems(cartId);

	const updatedCartItems = fetchedCart.cartItems;

	cpInstances.forEach((cpInstance) => {
		const includedCartItem = updatedCartItems.find((cartItem) => {
			const optionsJSON = JSON.parse(cartItem.options);

			let includedCartItem =
				cartItem.skuId === cpInstance.skuId &&
				cartItem.skuUnitOfMeasure?.key ===
					cpInstance.skuUnitOfMeasure?.key;

			if (includedCartItem) {
				optionsJSON.forEach((optionJSON) => {
					if (!includedCartItem) {
						return;
					}

					const currentSkuOption = cpInstance.skuOptions?.find(
						(skuOption) =>
							optionJSON.skuOptionKey === skuOption.skuOptionKey
					);

					// eslint-disable-next-line no-unused-expressions
					currentSkuOption
						? (includedCartItem =
								optionJSON.skuOptionValueKey ===
								currentSkuOption.skuOptionValueKey)
						: (includedCartItem = false);
				});
			}

			return includedCartItem;
		});

		if (includedCartItem) {
			includedCartItem.quantity = Number(
				Number(includedCartItem.quantity + cpInstance.quantity).toFixed(
					cpInstance.skuUnitOfMeasure?.precision || 0
				)
			);
		}
		else {
			updatedCartItems.push(
				formatCartItem(
					cpInstance,
					namespace,
					skuOptions,
					skuOptionsNamespace
				)
			);
		}
	});

	const updatedCart = await CartResource.updateCartById(cartId, {
		cartItems: updatedCartItems,
	});

	Liferay.fire(CURRENT_ORDER_UPDATED, {order: updatedCart});

	return updatedCart;
}
