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
import React, {useCallback, useEffect, useState} from 'react';

import ServiceProvider from '../../ServiceProvider/index';
import {
	ADD_TO_ORDER,
	CHANGE_ACCOUNT,
	CHANGE_ORDER,
} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';
import MiniCartContext from './MiniCartContext';
import Opener from './Opener';
import {regenerateOrderDetailURL, resolveView} from './util/index';

import CartItemsList from './CartItemsList';
import Wrapper from './Wrapper';

const DEFAULT_CART_VIEW = {component: Wrapper},
	DEFAULT_CART_ITEMS_LIST_VIEW = {component: CartItemsList};

function MiniCart({
	cartActionURLs,
	cartItemsListView,
	cartView,
	displayDiscountLevels,
	orderId,
	spritemap,
	toggleable
}) {
	const AJAX = ServiceProvider.DeliveryCartAPI('v1');

	const [isOpen, setIsOpen] = useState(false),
		[isUpdating, setIsUpdating] = useState(false),
		[cartState, updateCartState] = useState({}),
		[actionURLs, setActionURLs] = useState(cartActionURLs),
		[CartView, setCartView] = useState(null);

	const closeCart = () => setIsOpen(false),
		openCart = () => setIsOpen(true),
		resetCartState = useCallback(() => updateCartState({}), [
			updateCartState,
		]);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const updateCartModel = ({orderId: cartId}) =>
		AJAX.getCartByIdWithItems(cartId)
			.then((model) => {
				if (orderId !== cartId) {
					const {orderUUID} = model,
						{checkoutURL, orderDetailURL} = actionURLs;

					setActionURLs({
						checkoutURL,
						orderDetailURL: regenerateOrderDetailURL(
							orderDetailURL,
							orderUUID
						),
					});
				}

				updateCartState({...cartState, ...model});
			})
			.catch(showErrorNotification);

	useEffect(() => {
		resolveView(cartView)
			.catch(() => resolveView(DEFAULT_CART_VIEW))
			.then(view => setCartView(() => view));
	}, [cartView, cartItemsListView]);

	useEffect(() => {
		Liferay.on(ADD_TO_ORDER, updateCartModel);
		Liferay.on(CHANGE_ORDER, updateCartModel);

		return () => {
			Liferay.detach(ADD_TO_ORDER, updateCartModel);
			Liferay.detach(CHANGE_ORDER, updateCartModel);
		};
	}, [updateCartModel]);

	useEffect(() => {
		if (orderId && orderId !== 0) {
			updateCartModel({orderId});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [orderId]);

	useEffect(() => {
		Liferay.on(CHANGE_ACCOUNT, resetCartState);

		return () => {
			Liferay.detach(CHANGE_ACCOUNT, resetCartState);
		};
	}, [resetCartState]);

	return (
		<MiniCartContext.Provider
			value={{
				AJAX,
				actionURLs,
				cartState,
				closeCart,
				displayDiscountLevels,
				isOpen,
				isUpdating,
				setIsUpdating,
				spritemap,
				toggleable,
				updateCartModel,
				updateCartState,
			}}
		>
			{!!CartView && (
				<div className={
					classnames('mini-cart', (!toggleable || isOpen) && 'is-open'
					)
				}>
					{toggleable && (
						<>
							<div
								className={'mini-cart-overlay'}
								onClick={() => setIsOpen(false)}
							/>

							<Opener openCart={openCart} />
						</>
					)}

					<CartView cartItemsListView={cartItemsListView} />
				</div>
			)}
		</MiniCartContext.Provider>
	);
}

MiniCart.defaultProps = {
	cartItemsListView: DEFAULT_CART_ITEMS_LIST_VIEW,
	cartView: DEFAULT_CART_VIEW,
	displayDiscountLevels: false,
	toggleable: true
};

MiniCart.propTypes = {
	cartActionURLs: PropTypes.shape({
		checkoutURL: PropTypes.string,
		orderDetailURL: PropTypes.string
	}).isRequired,
	cartItemsListView: PropTypes.oneOfType([
		PropTypes.shape({
			component: PropTypes.func
		}),
		PropTypes.shape({
			contentRendererModuleUrl: PropTypes.string
		})
	]),
	cartView: PropTypes.oneOfType([
		PropTypes.shape({
			component: PropTypes.func
		}),
		PropTypes.shape({
			contentRendererModuleUrl: PropTypes.string
		})
	]),
	displayDiscountLevels: PropTypes.bool,
	orderId: PropTypes.number,
	spritemap: PropTypes.string,
	toggleable: PropTypes.bool
};

export default MiniCart;
