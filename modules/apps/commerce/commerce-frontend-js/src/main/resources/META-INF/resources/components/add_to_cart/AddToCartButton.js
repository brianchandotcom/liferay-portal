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
import React, {useCallback, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	CP_INSTANCE_CHANGED,
	CURRENT_ORDER_UPDATED,
	PRODUCT_REMOVED_FROM_CART,
} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';

function AddToCartButton({
	channel,
	cpInstance,
	orderId,
	quantity,
	settings,
	spritemap,
}) {
	const CartResource = ServiceProvider.DeliveryCartAPI('v1');

	const [catalogItem, updateCatalogItem] = useState(cpInstance);
	const [currentCartId, setCurrentCartId] = useState(orderId);

	const add = () => {
		const toCartItem = {
			options: catalogItem.options,
			quantity,
			skuId: catalogItem.skuId,
		};

		return currentCartId
			? CartResource.createItemByCartId(currentCartId, toCartItem)
			: CartResource.createCartByChannelId(channel.id, {
					accountId: catalogItem.accountId,
					cartItems: [toCartItem],
					currencyCode: channel.currencyCode,
			  }).then(({id}) => Promise.resolve(id));
	};

	const remove = useCallback(
		({skuId: removedSkuId}) => {
			if (removedSkuId === catalogItem.skuId) {
				updateCatalogItem({...catalogItem, isInCart: false});
			}
		},
		[catalogItem]
	);

	const reset = useCallback(
		(cpInstance) =>
			CartResource.getItemById(cpInstance.cpInstanceId)
				.then(() =>
					updateCatalogItem({
						...catalogItem,
						...cpInstance,
						isInCart: true,
					})
				)
				.catch(() =>
					updateCatalogItem({
						...catalogItem,
						...cpInstance,
						isInCart: false,
					})
				),
		[CartResource, catalogItem]
	);

	useEffect(() => {
		Liferay.on(CP_INSTANCE_CHANGED, reset);
		Liferay.on(PRODUCT_REMOVED_FROM_CART, remove);

		return () => {
			Liferay.detach(CP_INSTANCE_CHANGED, reset);
			Liferay.detach(PRODUCT_REMOVED_FROM_CART, remove);
		};
	}, [remove, reset]);

	return (
		<>
			<ClayButton
				block={settings.iconOnly ? false : settings.block}
				className={classnames({
					'btn-add-to-cart': true,
					'btn-lg': !settings.block,
					'icon-only': settings.iconOnly,
					'is-added': catalogItem.isInCart,
				})}
				disabled={settings.disabled || !catalogItem.accountId}
				displayType={'primary'}
				onClick={() =>
					add()
						.then((orderId) => {
							const orderDidChange =
								orderId && orderId !== currentCartId;

							Liferay.fire(CURRENT_ORDER_UPDATED, {
								orderId: orderDidChange
									? orderId
									: currentCartId,
							});

							updateCatalogItem({...catalogItem, isInCart: true});

							if (orderDidChange) {
								setCurrentCartId(orderId);
							}
						})
						.catch(showErrorNotification)
				}
			>
				{!settings.iconOnly && (
					<span className={'text-truncate-inline'}>
						<span className={'text-truncate'}>
							{Liferay.Language.get('add-to-cart')}
						</span>
					</span>
				)}

				<span className={'cart-icon'}>
					<ClayIcon spritemap={spritemap} symbol={'shopping-cart'} />
				</span>
			</ClayButton>
		</>
	);
}

AddToCartButton.defaultProps = {
	cpInstance: {
		accountId: null,
		isInCart: false,
		options: '[]',
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
		isInCart: PropTypes.bool,
		options: PropTypes.oneOfType([PropTypes.string, PropTypes.array]),
		quantity: PropTypes.number,
		skuId: PropTypes.number.isRequired,
	}).isRequired,
	orderId: PropTypes.number,
	quantity: PropTypes.number,
	settings: PropTypes.shape({
		block: PropTypes.bool,
		disabled: PropTypes.bool,
		iconOnly: PropTypes.bool,
	}),
	spritemap: PropTypes.string,
};

export default AddToCartButton;
