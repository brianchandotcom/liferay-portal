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

import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	CP_INSTANCE_CHANGED,
	PRODUCT_REMOVED_FROM_CART,
} from '../../utilities/eventsDefinitions';
import {useCommerceAccount, useCommerceCart} from '../../utilities/hooks';
import {getProductMinQuantity} from '../../utilities/quantities';
import QuantitySelector from '../quantity_selector/QuantitySelector';
import AddToCartButton from './AddToCartButton';
import {ALL} from './constants';

function getMinQuantity(settings) {
	if (settings?.quantityDetails?.allowedQuantities?.length) {
		return Math.min(...settings.quantityDetails.allowedQuantities);
	}

	return getProductMinQuantity(
		settings?.quantityDetails?.minQuantity,
		settings?.quantityDetails?.multipleQuantity
	);
}

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

function AddToCart({
	accountId: initialAccountId,
	cartId: initialCartId,
	cartUUID: initialCartUUID,
	channel,
	cpInstance: initialCpInstance,
	disabled: initialDisabled,
	settings,
}) {
	const cart = useCommerceCart(
		{
			UUID: initialCartUUID,
			id: initialCartId,
		},
		channel.groupId
	);
	const account = useCommerceAccount({id: initialAccountId});
	const [cpInstance, setCpInstance] = useState({
		...initialCpInstance,
		quantity: getMinQuantity(settings),
	});

	const buttonDisabled = useMemo(() => {
		if (
			initialDisabled ||
			!account?.id ||
			cpInstance.purchasable === false ||
			!cpInstance.quantity
		) {
			return true;
		}

		return false;
	}, [account, cpInstance, initialDisabled]);

	useEffect(() => {
		setCpInstance({
			...initialCpInstance,
			quantity: getMinQuantity(settings),
		});
	}, [initialCpInstance, settings]);

	const reset = useCallback(
		({cpInstance: incomingCpInstance}) => {
			CartResource.getItemsByCartId(cart.id)
				.then(({items}) =>
					items.some(({skuId}) => incomingCpInstance.skuId === skuId)
				)
				.catch(() => false)
				.then((inCart) => {
					setCpInstance((cpInstance) => ({
						...cpInstance,
						backOrderAllowed: incomingCpInstance.backOrderAllowed,
						inCart,
						options: Array.isArray(incomingCpInstance.options)
							? incomingCpInstance.options
							: JSON.parse(incomingCpInstance.options),
						purchasable: incomingCpInstance.purchasable,
						skuId: incomingCpInstance.skuId,
						stockQuantity: incomingCpInstance.stockQuantity,
					}));
				});
		},
		[cart.id]
	);

	useEffect(() => {
		function remove({skuId: removedSkuId}) {
			setCpInstance((cpInstance) => ({
				...cpInstance,
				inCart:
					removedSkuId === cpInstance.skuId || removedSkuId === ALL
						? false
						: cpInstance.inCart,
			}));
		}

		Liferay.on(PRODUCT_REMOVED_FROM_CART, remove);

		if (settings.namespace) {
			Liferay.on(`${settings.namespace}${CP_INSTANCE_CHANGED}`, reset);
		}

		return () => {
			Liferay.detach(PRODUCT_REMOVED_FROM_CART, remove);

			if (settings.namespace) {
				Liferay.detach(
					`${settings.namespace}${CP_INSTANCE_CHANGED}`,
					reset
				);
			}
		};
	}, [reset, settings.namespace]);

	const spaceDirection = settings.inline ? 'ml' : 'mt';
	const spacer = settings.size === 'sm' ? 1 : 3;

	return (
		<div
			className={classnames({
				'add-to-cart-wrapper': true,
				'align-items-center':
					settings.alignment === 'full-width' ||
					settings.alignment === 'center',
				'd-flex': true,
				'flex-column': !settings.inline,
			})}
		>
			<QuantitySelector
				{...settings.quantityDetails}
				disabled={initialDisabled || !account?.id}
				onUpdate={(quantity) =>
					setCpInstance({...cpInstance, quantity})
				}
				quantity={cpInstance.quantity}
				size={settings.size}
			/>

			<AddToCartButton
				accountId={account.id}
				cartId={cart.id}
				channel={channel}
				className={`${spaceDirection}-${spacer}`}
				cpInstances={[cpInstance]}
				disabled={buttonDisabled}
				onAdd={() => {
					setCpInstance({...cpInstance, inCart: true});
				}}
				settings={settings}
			/>
		</div>
	);
}

AddToCart.propTypes = {
	accountId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	cartId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	cpInstance: PropTypes.shape({
		options: PropTypes.array,
		skuId: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
			.isRequired,
	}),
	disabled: PropTypes.bool,
	settings: PropTypes.shape({
		alignment: PropTypes.oneOf(['center', 'left', 'right', 'full-width']),
		inline: PropTypes.bool,
		namespace: PropTypes.string,
		quantityDetails: PropTypes.shape({
			allowedQuantities: PropTypes.arrayOf(PropTypes.number),
			maxQuantity: PropTypes.number,
			minQuantity: PropTypes.number,
			multipleQuantity: PropTypes.number,
		}),
		size: PropTypes.oneOf(['sm', 'md', 'lg']),
	}),
};

export default AddToCart;
