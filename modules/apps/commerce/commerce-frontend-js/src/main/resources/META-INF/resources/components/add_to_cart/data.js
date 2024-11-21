/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

import ServiceProvider from '../../ServiceProvider/index';
import {CURRENT_ORDER_UPDATED} from '../../utilities/eventsDefinitions';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

export function formatCartItem(
	cpInstance,
	namespace,
	skuOptions,
	skuOptionsNamespace
) {
	let optionsJSON = cpInstance.skuOptions || [];

	if (namespace === skuOptionsNamespace) {
		optionsJSON = skuOptions.map((skuOption) => ({
			...skuOption,
			skuId: skuOption.skuId ? String(skuOption.skuId) : null,
		}));
	}
	else if (optionsJSON.length) {
		optionsJSON = optionsJSON.map((optionJSON) => ({
			...optionJSON,
			key: optionJSON.skuOptionKey || optionJSON.key,
			value: optionJSON.skuOptionValueKey || optionJSON.value,
		}));
	}

	if (cpInstance.skuUnitOfMeasure) {
		cpInstance.skuUnitOfMeasure = {
			incrementalOrderQuantity:
				cpInstance.skuUnitOfMeasure.incrementalOrderQuantity,
			key: cpInstance.skuUnitOfMeasure.key,
			precision: cpInstance.skuUnitOfMeasure.precision,
		};
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

	if (cpInstances.length === 1) {
		await CartResource.createItemByCartId(
			cartId,
			formatCartItem(
				cpInstances[0],
				namespace,
				skuOptions,
				skuOptionsNamespace
			)
		);

		const fetchedCart = await CartResource.getCartByIdWithItems(cartId);

		Liferay.fire(CURRENT_ORDER_UPDATED, {order: fetchedCart});

		return fetchedCart;
	}

	const fetchedCart = await CartResource.getCartByIdWithItems(cartId);

	const removedItems = [];

	const updatedCartItems = fetchedCart.cartItems.filter((cartItem) => {
		const isRemovedFromCatalog = !!cartItem?.errorMessages?.length;

		if (isRemovedFromCatalog) {
			removedItems.push(cartItem);

			return false;
		}

		return true;
	});

	cpInstances.forEach((cpInstance) => {
		const includedCartItem = updatedCartItems.find((cartItem) => {
			const optionsJSON = JSON.parse(cartItem.options);

			let includedCartItem =
				cartItem.skuId === cpInstance.skuId &&
				cartItem.skuUnitOfMeasure?.key ===
					cpInstance.skuUnitOfMeasure?.key;

			if (includedCartItem) {
				optionsJSON.forEach((option) => {
					if (!includedCartItem) {
						return;
					}

					const currentSkuOption = cpInstance.skuOptions?.find(
						(skuOption) =>
							option.skuOptionKey === skuOption.skuOptionKey
					);

					// eslint-disable-next-line no-unused-expressions
					currentSkuOption
						? (includedCartItem = Array.isArray(option.value)
								? !option.value.length
								: option.value === currentSkuOption.value ||
									option.skuOptionValueKey ===
										currentSkuOption.skuOptionValueKey)
						: (includedCartItem = false);
				});
			}

			return includedCartItem;
		});

		if (
			includedCartItem &&
			!Liferay.CommerceContext.showSeparateOrderItems
		) {
			includedCartItem.quantity =
				parseFloat(includedCartItem.quantity) +
				parseFloat(cpInstance.quantity);

			includedCartItem.quantity = Number(
				includedCartItem.quantity.toFixed(
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

	if (removedItems.length) {
		openModal({
			bodyHTML: `
				<div>
					<p>${Liferay.Language.get('the-following-products-are-no-longer-available-and-were-removed-from-the-cart')}</p>
					<p>
						<ul>
							${removedItems.map(({name}) => `<li>${name}</li>`).join('')}
						</ul>
					</p>
				</div>
			`,
			buttons: [
				{
					displayType: 'warning',
					label: Liferay.Language.get('ok'),
					onClick: ({processClose}) => {
						processClose();
					},
					type: 'button',
				},
			],
			status: 'warning',
			title: Liferay.Language.get('cart-updated'),
		});
	}

	Liferay.fire(CURRENT_ORDER_UPDATED, {order: updatedCart});

	return updatedCart;
}
