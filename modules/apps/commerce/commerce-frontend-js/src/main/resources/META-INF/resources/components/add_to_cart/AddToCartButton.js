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
import {
	CP_INSTANCE_CHANGED,
	CURRENT_ORDER_UPDATED,
	PRODUCT_REMOVED_FROM_CART,
} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';
import {ALL} from './constants';

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
	const [currentCartId, setCurrentCartId] = useState(orderId);
	const [disabled, setDisabled] = useState(
		settings.disabled || !catalogItem.accountId
	);

	const add = () => {
		const toCartItem = {
			options: catalogItem.options,
			quantity,
			skuId: catalogItem.skuId,
		};

		return currentCartId
			? CartResource.createItemByCartId(
					currentCartId,
					toCartItem
			  ).then((item) =>
					Promise.resolve({...item, orderId: currentCartId})
			  )
			: CartResource.createCartByChannelId(channel.id, {
					accountId: catalogItem.accountId,
					cartItems: [toCartItem],
					currencyCode: channel.currencyCode,
			  }).then(({id}) => Promise.resolve({orderId: id}));
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
			CartResource.getItemsByCartId(currentCartId)
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

					if (cpInstance.stockQuantity > 0) {
						setDisabled(false);
					}
				}),
		[CartResource, catalogItem, currentCartId]
	);

	useEffect(() => {
		Liferay.on(PRODUCT_REMOVED_FROM_CART, remove);

		if (settings.willUpdate) {
			Liferay.on(CP_INSTANCE_CHANGED, reset);
		}

		return () => {
			Liferay.detach(PRODUCT_REMOVED_FROM_CART, remove);

			if (settings.willUpdate) {
				Liferay.detach(CP_INSTANCE_CHANGED, reset);
			}
		};
	}, [remove, reset, settings.willUpdate]);

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
				displayType={'primary'}
				onClick={() =>
					add()
						.then(({orderId}) => {
							const orderDidChange = orderId !== currentCartId;

							Liferay.fire(CURRENT_ORDER_UPDATED, {
								orderId: orderDidChange
									? orderId
									: currentCartId,
							});

							updateCatalogItem({...catalogItem, inCart: true});

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
	quantity: PropTypes.number,
	settings: PropTypes.shape({
		block: PropTypes.bool,
		disabled: PropTypes.bool,
		iconOnly: PropTypes.bool,
		willUpdate: PropTypes.bool,
	}),
	spritemap: PropTypes.string,
};

export default AddToCartButton;
