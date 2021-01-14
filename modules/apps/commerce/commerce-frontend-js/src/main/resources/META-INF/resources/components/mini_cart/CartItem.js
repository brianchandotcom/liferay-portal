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
import React, {useCallback, useContext, useState} from 'react';

import {PRODUCT_REMOVED_FROM_CART} from '../../utilities/eventsDefinitions';
import QuantitySelector from '../quantity_selector/QuantitySelector';
import ItemInfoView from './CartItemViews/ItemInfoView';
import ItemPriceView from './CartItemViews/ItemPriceView';
import MiniCartContext from './MiniCartContext';
import {parseOptions} from './util/index';

const REMOVAL_TIMEOUT = 2000,
	REMOVAL_CANCELING_TIMEOUT = 700,
	REMOVAL_ERRORS_TIMEOUT = 4000,
	INITIAL_ITEM_STATE = {
		isGettingRemoved: false,
		isRemovalCanceled: false,
		isRemoved: false,
		isShowingErrors: false,
		previousQuantity: null,
		removalTimeoutRef: null,
	};

function CartItem({item: cartItem}) {
	const {
		cartItems: childItems,
		errorMessages,
		id: cartItemId,
		name,
		options: rawOptions,
		price,
		quantity,
		settings,
		sku,
		skuId,
		thumbnail,
	} = cartItem;

	const {
		CartResource,
		cartState,
		displayDiscountLevels,
		setIsUpdating,
		spritemap,
		updateCartModel,
	} = useContext(MiniCartContext);

	const {id: orderId} = cartState;
	const [itemState, setItemState] = useState(INITIAL_ITEM_STATE);
	const [itemQuantity, setItemQuantity] = useState(quantity);
	const [itemPrice, updateItemPrice] = useState(price);

	const options = parseOptions(rawOptions);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const showErrors = () => {
		setIsUpdating(false);

		setItemState({
			...INITIAL_ITEM_STATE,
			isShowingErrors: true,
			removalTimeoutRef: setTimeout(() => {
				setItemState(INITIAL_ITEM_STATE);
			}, REMOVAL_ERRORS_TIMEOUT),
		});

		return Promise.resolve();
	};

	const cancelRemoveItem = () => {
		clearTimeout(itemState.removalTimeoutRef);

		setItemState({
			...INITIAL_ITEM_STATE,
			isRemovalCanceled: true,
			removalTimeoutRef: setTimeout(() => {
				setIsUpdating(false);

				setItemState(INITIAL_ITEM_STATE);
			}, REMOVAL_CANCELING_TIMEOUT),
		});
	};

	const removeItem = () => {
		setItemState({
			...INITIAL_ITEM_STATE,
			isGettingRemoved: true,
			removalTimeoutRef: setTimeout(() => {
				setIsUpdating(true);

				setItemState({
					...INITIAL_ITEM_STATE,
					isGettingRemoved: true,
					isRemoved: true,
					removalTimeoutRef: setTimeout(() => {
						CartResource.deleteItemById(cartItemId)
							.then(() => updateCartModel({orderId}))
							.then(() => {
								setIsUpdating(false);
								Liferay.fire(PRODUCT_REMOVED_FROM_CART, {
									skuId,
								});
							})
							.catch(showErrors);
					}, REMOVAL_CANCELING_TIMEOUT),
				});
			}, REMOVAL_TIMEOUT),
		});
	};

	const updateItemQuantity = useCallback(
		(quantity) => {
			if (quantity !== itemQuantity) {
				setIsUpdating(true);

				CartResource.updateItemById(cartItemId, {
					...cartItem,
					quantity,
				})
					.then(({quantity: updatedQuantity, ...updatedItem}) => {
						setItemQuantity(updatedQuantity);

						return Promise.resolve(updatedItem);
					})
					.then(({price: updatedPrice}) => {
						const {price: currentPriceValue} = itemPrice;
						const {price: updatedPriceValue} = updatedPrice;

						/**
						 * The unit price of an item may change based
						 * on the change of its quantity
						 * @type {boolean}
						 */

						const priceValueChanged =
							!currentPriceValue ||
							currentPriceValue !== updatedPriceValue;

						if (priceValueChanged) {
							return updateItemPrice(updatedPrice);
						}

						return Promise.resolve();
					})
					.then(() => updateCartModel({orderId}))
					.then(() => setIsUpdating(false))
					.catch(showErrors);
			}

			return Promise.resolve();
		}, // eslint-disable-next-line react-hooks/exhaustive-deps
		[CartResource, cartItem, cartItemId, orderId]
	);

	const {
		isGettingRemoved,
		isRemovalCanceled,
		isRemoved,
		isShowingErrors,
	} = itemState;

	return (
		<div
			className={classnames('mini-cart-item', isRemoved && 'is-removed')}
		>
			{!!thumbnail && (
				<div
					className={'mini-cart-item-thumbnail'}
					style={{backgroundImage: `url(${thumbnail})`}}
				/>
			)}

			<div
				className={classnames(
					'mini-cart-item-info',
					!!options && 'has-options'
				)}
			>
				<ItemInfoView
					childItems={childItems}
					name={name}
					options={options}
					sku={sku}
				/>
			</div>

			<div className={'mini-cart-item-quantity'}>
				<QuantitySelector
					onUpdate={updateItemQuantity}
					quantity={quantity}
					spritemap={spritemap}
					{...settings}
				/>
			</div>

			<div className={'mini-cart-item-price'}>
				<ItemPriceView
					displayDiscountLevels={displayDiscountLevels}
					price={itemPrice}
				/>
			</div>

			<div className={'mini-cart-item-delete'}>
				<button
					className={'btn btn-unstyled'}
					onClick={removeItem}
					type={'button'}
				>
					<ClayIcon
						spritemap={spritemap}
						symbol={'times-circle-full'}
					/>
				</button>
			</div>

			{errorMessages && (
				<div className={'mini-cart-item-errors'}>
					<ClayIcon
						spritemap={spritemap}
						symbol={'exclamation-circle'}
					/>

					<span>{errorMessages}</span>
				</div>
			)}

			{isShowingErrors && (
				<div className={'mini-cart-item-errors'}>
					<ClayIcon
						spritemap={spritemap}
						symbol={'exclamation-circle'}
					/>

					<span>
						{Liferay.Language.get('an-unexpected-error-occurred')}
					</span>
				</div>
			)}

			<div
				className={classnames(
					'mini-cart-item-removing',
					isGettingRemoved && 'active',
					isRemovalCanceled && 'canceled'
				)}
			>
				<span>{Liferay.Language.get('the-item-has-been-removed')}</span>
				<span>
					<ClayButton
						displayType={'link'}
						href={'#'}
						onClick={cancelRemoveItem}
						small
						type={'button'}
					>
						{Liferay.Language.get('undo')}
					</ClayButton>
				</span>
			</div>
		</div>
	);
}

CartItem.propTypes = {
	item: PropTypes.object,
};

export default CartItem;
