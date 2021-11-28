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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {debouncePromise} from '../../utilities/debounce';
import {PRODUCT_REMOVED_FROM_CART} from '../../utilities/eventsDefinitions';
import Price from '../price/Price';
import QuantitySelector from '../quantity_selector/QuantitySelector';
import ItemInfoView from './CartItemViews/ItemInfoView';
import MiniCartContext from './MiniCartContext';
import {
	INITIAL_ITEM_STATE,
	REMOVAL_CANCELING_TIMEOUT,
	REMOVAL_ERRORS_TIMEOUT,
	REMOVAL_TIMEOUT,
} from './util/constants';
import {generateProductPageURL, parseOptions} from './util/index';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

const deboncedUpdateItemQuantity = debouncePromise(
	(cartItemId, quantity) =>
		CartResource.updateItemById(cartItemId, {
			quantity,
		}),
	300
);

function CartItem({
	adaptiveMediaImageHTMLTag,
	cartItems: childItems,
	errorMessages,
	id: cartItemId,
	name,
	options: rawOptions,
	price,
	productURLs,
	quantity: cartItemQuantity,
	settings,
	sku,
	skuId,
}) {
	const [itemState, setItemState] = useState(INITIAL_ITEM_STATE);
	const [selectorQuantity, setSelectorQuantity] = useState(cartItemQuantity);
	const isMounted = useIsMounted();
	const options = parseOptions(rawOptions);

	useEffect(() => {
		setSelectorQuantity(cartItemQuantity);
	}, [cartItemQuantity]);

	const {
		actionURLs,
		cartState,
		displayDiscountLevels,
		setIsUpdating,
		updateCartModel,
	} = useContext(MiniCartContext);

	const productPageUrl = generateProductPageURL(
		actionURLs.siteDefaultURL,
		productURLs,
		actionURLs.productURLSeparator
	);

	const showErrors = useCallback(() => {
		if (isMounted()) {
			setItemState({
				...INITIAL_ITEM_STATE,
				isShowingErrors: true,
				removalTimeoutRef: setTimeout(() => {
					if (isMounted()) {
						setItemState(INITIAL_ITEM_STATE);
					}
				}, REMOVAL_ERRORS_TIMEOUT),
			});
		}
	}, [isMounted]);

	const cancelRemoveItem = (event) => {
		event.stopPropagation();

		clearTimeout(itemState.removalTimeoutRef);

		setItemState({
			...INITIAL_ITEM_STATE,
			isRemovalCanceled: true,
			removalTimeoutRef: setTimeout(() => {
				if (isMounted()) {
					setIsUpdating(false);

					setItemState(INITIAL_ITEM_STATE);
				}
			}, REMOVAL_CANCELING_TIMEOUT),
		});
	};

	const removeItem = (event) => {
		event.stopPropagation();

		setItemState({
			...INITIAL_ITEM_STATE,
			isGettingRemoved: true,
			removalTimeoutRef: setTimeout(() => {
				if (!isMounted()) {
					return;
				}

				setIsUpdating(true);

				setItemState({
					...INITIAL_ITEM_STATE,
					isGettingRemoved: true,
					isRemoved: true,
					removalTimeoutRef: setTimeout(() => {
						CartResource.deleteItemById(cartItemId)
							.then(() => {
								if (!isMounted()) {
									return;
								}

								updateCartModel({id: cartState.id});

								Liferay.fire(PRODUCT_REMOVED_FROM_CART, {
									skuId,
								});
							})
							.catch(showErrors)
							.finally(() => {
								if (isMounted()) {
									setIsUpdating(false);
								}
							});
					}, REMOVAL_CANCELING_TIMEOUT),
				});
			}, REMOVAL_TIMEOUT),
		});
	};

	const {
		isGettingRemoved,
		isRemovalCanceled,
		isRemoved,
		isShowingErrors,
	} = itemState;

	return (
		<div
			className={classnames('mini-cart-item', {
				'is-removed': isRemoved,
			})}
		>
			<a className="mini-cart-item-anchor" href={productPageUrl}>
				{!!adaptiveMediaImageHTMLTag && (
					<div
						className="mini-cart-item-thumbnail"
						dangerouslySetInnerHTML={{
							__html: adaptiveMediaImageHTMLTag,
						}}
					/>
				)}

				<div
					className={classnames('mini-cart-item-info ml-3', {
						options: !!options,
					})}
				>
					<ItemInfoView
						childItems={childItems}
						name={name}
						options={options}
						sku={sku}
					/>
				</div>
			</a>

			<div className="mini-cart-item-quantity">
				<QuantitySelector
					onUpdate={(newQuantity) => {
						setSelectorQuantity(newQuantity);
						setIsUpdating(true);

						deboncedUpdateItemQuantity(cartItemId, newQuantity)
							.then(() => {
								if (isMounted()) {
									setIsUpdating(false);
									updateCartModel({id: cartState.id});
								}
							})
							.catch((...errors) => {
								setIsUpdating(false);
								showErrors(...errors);
							});
					}}
					quantity={selectorQuantity}
					{...settings}
				/>
			</div>

			<div className="mini-cart-item-price">
				<Price
					compact={true}
					displayDiscountLevels={displayDiscountLevels}
					price={price}
				/>
			</div>

			<div className="mini-cart-item-delete">
				<button
					className="btn btn-unstyled"
					onClick={removeItem}
					type="button"
				>
					<ClayIcon symbol="times-circle-full" />
				</button>
			</div>

			{(errorMessages || isShowingErrors) && (
				<div className="mini-cart-item-errors">
					<ClayIcon symbol="exclamation-circle" />

					<span>
						{Liferay.Language.get('an-unexpected-error-occurred')}
					</span>
				</div>
			)}

			<div
				className={classnames({
					'active': isGettingRemoved,
					'canceled': isRemovalCanceled,
					'mini-cart-item-removing': true,
				})}
			>
				<span>{Liferay.Language.get('the-item-has-been-removed')}</span>

				<span>
					<ClayButton
						displayType="link"
						onClick={cancelRemoveItem}
						small
						type="button"
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
