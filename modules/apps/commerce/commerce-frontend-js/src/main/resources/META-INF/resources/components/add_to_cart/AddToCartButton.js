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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import CommerceCookie from '../../utilities/cookies';
import {
	CP_INSTANCE_CHANGED,
	CURRENT_ORDER_UPDATED,
	PRODUCT_REMOVED_FROM_CART,
} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';
import {ALL, GUEST_COMMERCE_ORDER_COOKIE_IDENTIFIER} from './constants';

const orderCookie = new CommerceCookie(GUEST_COMMERCE_ORDER_COOKIE_IDENTIFIER);

function AddToCartButton({
	channel,
	cpInstance,
	orderId,
	quantity,
	settings,
	spritemap,
}) {
	const CartResource = useMemo(
		() => ServiceProvider.DeliveryCartAPI('v1'),
		[]
	);

	const [catalogItem, updateCatalogItem] = useState(cpInstance);
	const [activeOrder, setActiveOrder] = useState({id: orderId});
	const [disabled, setDisabled] = useState(
		settings.disabled || !catalogItem.accountId
	);

	const add = () => {
		const toCartItem = {
			options: catalogItem.options,
			quantity,
			skuId: catalogItem.skuId,
		};

		return activeOrder.id
			? CartResource.createItemByCartId(
					activeOrder.id,
					toCartItem
			  ).then(() => Promise.resolve(activeOrder))
			: CartResource.createCartByChannelId(channel.id, {
					accountId: catalogItem.accountId,
					cartItems: [toCartItem],
					currencyCode: channel.currencyCode,
			  });
	};

	const remove = useCallback(
		({skuId: removedSkuId}) => {
			if (removedSkuId === catalogItem.skuId || removedSkuId === ALL) {
				updateCatalogItem({...catalogItem, inCart: false});
			}
		},
		[catalogItem]
	);

	const reset = useCallback(
		({cpInstance}) =>
			CartResource.getItemsByCartId(activeOrder.id)
				.then(({items}) =>
					Promise.resolve(
						Boolean(
							items.find(({skuId}) => cpInstance.skuId === skuId)
						)
					)
				)
				.catch(() => Promise.resolve(false))
				.then((inCart) => {
					updateCatalogItem({
						...catalogItem,
						...cpInstance,
						inCart,
					});

					const isPurchasable =
						cpInstance.purchasable &&
						(cpInstance.backOrderAllowed ||
							cpInstance.stockQuantity > 0);

					setDisabled(!isPurchasable);
				}),
		[activeOrder, CartResource, catalogItem]
	);

	const changeOrder = useCallback(
		(order) => {
			if (order.id !== activeOrder.id) {
				setActiveOrder((current) => ({
					...current,
					...order,
				}));
			}
		},
		[activeOrder.id]
	);

	useEffect(() => {
		Liferay.on(CURRENT_ORDER_UPDATED, changeOrder);
		Liferay.on(PRODUCT_REMOVED_FROM_CART, remove);

		if (settings.namespace) {
			Liferay.on(`${settings.namespace}${CP_INSTANCE_CHANGED}`, reset);
		}

		return () => {
			Liferay.detach(CURRENT_ORDER_UPDATED, changeOrder);
			Liferay.detach(PRODUCT_REMOVED_FROM_CART, remove);

			if (settings.namespace) {
				Liferay.detach(
					`${settings.namespace}${CP_INSTANCE_CHANGED}`,
					reset
				);
			}
		};
	}, [changeOrder, remove, reset, settings.namespace]);

	return (
		<>
			<ClayButton
				block={settings.iconOnly ? false : settings.block}
				className={classnames({
					'btn-add-to-cart': true,
					'btn-lg': !settings.block,
					'icon-only': settings.iconOnly,
					'is-added': catalogItem.inCart,
				})}
				disabled={disabled}
				displayType="primary"
				onClick={() =>
					add()
						.then((order) => {
							const orderDidChange = order.id !== activeOrder.id;

							Liferay.fire(
								CURRENT_ORDER_UPDATED,
								orderDidChange ? {...order} : {...activeOrder}
							);

							updateCatalogItem({...catalogItem, inCart: true});

							if (orderDidChange) {
								orderCookie.setValue(
									channel.id,
									order.orderUUID
								);

								setActiveOrder(order);
							}
						})
						.catch(showErrorNotification)
				}
			>
				{!settings.iconOnly && (
					<span className="text-truncate-inline">
						<span className="text-truncate">
							{Liferay.Language.get('add-to-cart')}
						</span>
					</span>
				)}

				<span className="cart-icon">
					<ClayIcon spritemap={spritemap} symbol="shopping-cart" />
				</span>
			</ClayButton>
		</>
	);
}

AddToCartButton.defaultProps = {
	cpInstance: {
		accountId: null,
		inCart: false,
		options: '[]',
		stockQuantity: 1,
	},
	orderId: 0,
	quantity: 1,
	settings: {
		block: false,
		iconOnly: false,
		withQuantity: false,
	},
};

AddToCartButton.propTypes = {
	channel: PropTypes.shape({

		/**
		 * The currency is currently always
		 * one and the same per single channel
		 */
		currencyCode: PropTypes.string.isRequired,
		id: PropTypes.number.isRequired,
	}),
	cpInstance: PropTypes.shape({
		accountId: PropTypes.number,
		inCart: PropTypes.bool,
		options: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
		skuId: PropTypes.number.isRequired,
		stockQuantity: PropTypes.oneOfType([
			PropTypes.string,
			PropTypes.number,
		]),
	}).isRequired,
	orderId: PropTypes.number,
	orderUUID: PropTypes.number,
	quantity: PropTypes.number,
	settings: PropTypes.shape({
		block: PropTypes.bool,
		disabled: PropTypes.bool,
		iconOnly: PropTypes.bool,
		namespace: PropTypes.string,
	}),
	spritemap: PropTypes.string,
};

export default AddToCartButton;
