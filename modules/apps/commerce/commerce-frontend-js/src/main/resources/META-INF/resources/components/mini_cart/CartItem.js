/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classnames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {debouncePromise} from '../../utilities/debounce';
import {CART_PRODUCT_QUANTITY_CHANGED} from '../../utilities/eventsDefinitions';
import {getMultipleQuantity} from '../../utilities/quantities';
import Price from '../price/Price';
import QuantitySelector from '../quantity_selector/QuantitySelector';
import ItemInfoView from './CartItemViews/ItemInfoView';
import MiniCartContext from './MiniCartContext';
import {
	INITIAL_ITEM_STATE,
	PRODUCT_QUANTITY_NOT_VALID_ERROR,
	REMOVAL_CANCELING_TIMEOUT,
	REMOVAL_TIMEOUT,
	UNEXPECTED_ERROR,
} from './util/constants';
import {filterOptions, generateProductPageURL, hasOptions} from './util/index';

const CartResource = ServiceProvider.DeliveryCartAPI('v1');

const deboncedUpdateItemQuantity = debouncePromise(
	(cartItemId, quantity, invalid) => {
		if (invalid) {
			return Promise.reject(PRODUCT_QUANTITY_NOT_VALID_ERROR);
		}

		return CartResource.updateItemById(cartItemId, {
			quantity,
		}).catch((error) => {
			let errorMessage;

			if (error.message) {
				errorMessage = error.message;
			}
			else if (error.detail) {
				errorMessage = error.detail;
			}
			else {
				errorMessage = UNEXPECTED_ERROR;
			}

			throw errorMessage;
		});
	},
	1000
);

const isValidMedia = (adaptiveMediaImageHTMLTag) => {
	if (adaptiveMediaImageHTMLTag) {
		const testElement = window.document.createElement('div');

		testElement.innerHTML = adaptiveMediaImageHTMLTag;

		const imgElement = testElement.querySelector('img');
		const srcAttribute = imgElement.getAttribute('src');

		return !!srcAttribute;
	}

	return false;
};

function CartItem({
	adaptiveMediaImageHTMLTag,
	cartItems: childItems,
	errorMessages = [],
	id: cartItemId,
	index,
	name,
	options: rawOptions,
	price,
	productURLs,
	quantity: cartItemQuantity,
	settings,
	sku,
	skuId,
	updateCartItem,
	replacedSku,
	skuUnitOfMeasure,
	productId,
}) {
	const [itemState, setItemState] = useState(INITIAL_ITEM_STATE);
	const [selectorQuantity, setSelectorQuantity] = useState(cartItemQuantity);
	const hasChildItems = !!childItems?.length;
	const hasSkuUnitOfMeasure = !!skuUnitOfMeasure?.key;
	const isMounted = useIsMounted();
	const options = filterOptions(rawOptions);

	useEffect(() => {
		setSelectorQuantity(cartItemQuantity);
	}, [cartItemQuantity]);

	const {
		actionURLs,
		cartState,
		displayDiscountLevels,
		setEditedItem,
		setIsUpdating,
		updateCartModel,
	} = useContext(MiniCartContext);

	const productPageUrl = generateProductPageURL(
		actionURLs.siteDefaultURL,
		productURLs,
		actionURLs.productURLSeparator
	);

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

								updateCartModel({order: {id: cartState.id}});

								Liferay.fire(CART_PRODUCT_QUANTITY_CHANGED, {
									quantity: 0,
									skuId,
								});
							})
							.catch(() => {
								updateCartItem((cartItem) => ({
									...cartItem,
									errorMessages: [UNEXPECTED_ERROR],
								}));
							})
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

	const {isGettingRemoved, isRemovalCanceled, isRemoved} = itemState;

	const getClassName = (className) => {
		return classnames(className, {
			'mini-cart-item-alignment': true,
		});
	};

	return (
		<div
			className={classnames('mini-cart-item', {
				'align-items-start': hasChildItems,
				'is-removed': isRemoved,
			})}
		>
			<div className="mini-cart-item-details position-relative">
				<a
					className="h-100 mini-cart-item-anchor position-absolute w-100"
					data-senna-off="true"
					href={productPageUrl}
				>
					<span className="sr-only">
						{sub(Liferay.Language.get('go-to-x'), name)}
					</span>
				</a>

				{isValidMedia(adaptiveMediaImageHTMLTag) && (
					<div
						className="mini-cart-item-thumbnail"
						dangerouslySetInnerHTML={{
							__html: adaptiveMediaImageHTMLTag,
						}}
					/>
				)}

				<div
					className={classnames('mini-cart-item-info ml-3 w-100', {
						options: Boolean(options),
					})}
				>
					<ItemInfoView
						childItems={childItems}
						name={name}
						options={options}
						replacedSku={replacedSku}
						sku={sku}
					/>
				</div>
			</div>

			<div
				className={getClassName(
					'align-items-center d-flex mini-cart-item-quantity'
				)}
			>
				<QuantitySelector
					alignment={index > 0 ? 'top' : 'bottom'}
					allowedQuantities={settings.allowedQuantities}
					max={settings.maxQuantity}
					min={settings.minQuantity}
					onUpdate={({errors, value: newQuantity}) => {
						setSelectorQuantity(newQuantity);

						if (!errors.length) {
							setIsUpdating(true);
						}

						deboncedUpdateItemQuantity(
							cartItemId,
							newQuantity,
							!!errors.length
						)
							.then(() => {
								if (isMounted()) {
									setIsUpdating(false);

									updateCartModel({
										order: {id: cartState.id},
									});
								}
							})
							.catch((error) => {
								if (isMounted()) {
									setIsUpdating(false);

									if (error) {
										updateCartItem((cartItem) => ({
											...cartItem,
											errorMessages: [error],
										}));
									}
								}
							});
					}}
					quantity={selectorQuantity}
					step={
						skuUnitOfMeasure
							? getMultipleQuantity(
									skuUnitOfMeasure.incrementalOrderQuantity,
									settings.multipleQuantity,
									skuUnitOfMeasure.precision
								)
							: settings.multipleQuantity
					}
					{...settings}
					unitOfMeasure={skuUnitOfMeasure}
				/>

				<div className="ml-2">{skuUnitOfMeasure?.key}</div>
			</div>

			<div className={getClassName('mini-cart-item-price')}>
				<Price
					compact={true}
					displayDiscountLevels={displayDiscountLevels}
					price={price}
				/>
			</div>

			<div className={getClassName('mini-cart-item-actions')}>
				{hasOptions(rawOptions) || hasSkuUnitOfMeasure ? (
					<ClayDropDown
						closeOnClick
						trigger={
							<ClayButtonWithIcon
								aria-label={sub(
									Liferay.Language.get('actions-for-x'),
									name
								)}
								className="d-inline-flex"
								data-qa-id="cartItemActions"
								displayType="unstyled"
								symbol="ellipsis-v"
								title={sub(
									Liferay.Language.get('actions-for-x'),
									name
								)}
							/>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Item
								onClick={() =>
									setEditedItem({
										cartItemId,
										name,
										productId,
									})
								}
							>
								{Liferay.Language.get('edit')}
							</ClayDropDown.Item>

							<ClayDropDown.Item onClick={removeItem}>
								{Liferay.Language.get('delete')}
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				) : (
					<ClayButtonWithIcon
						aria-label={sub(Liferay.Language.get('delete-x'), name)}
						className="d-inline-flex"
						displayType="unstyled"
						onClick={removeItem}
						symbol="times-circle-full"
						title={sub(Liferay.Language.get('delete-x'), name)}
					/>
				)}
			</div>

			{!!errorMessages.length && (
				<div className="mini-cart-item-errors">
					<div className="row">
						<div className="col-auto">
							<ClayIcon symbol="exclamation-circle" />
						</div>

						<div className="col">
							{errorMessages.map((errorMessage) => (
								<div key={errorMessage}>{errorMessage}</div>
							))}
						</div>
					</div>
				</div>
			)}

			<div
				className={classnames({
					'active': isGettingRemoved,
					'canceled': isRemovalCanceled,
					'mini-cart-item-is-removing-wrapper': true,
				})}
			>
				<div className="mini-cart-item-is-removing">
					<span>
						{Liferay.Language.get('the-item-has-been-removed')}
					</span>

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
		</div>
	);
}

export default CartItem;
