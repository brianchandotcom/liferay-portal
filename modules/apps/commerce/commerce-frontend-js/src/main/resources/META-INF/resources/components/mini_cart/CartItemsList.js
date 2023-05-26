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

import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';

import CartQuickAdd from './CartQuickAdd';
import MiniCartContext from './MiniCartContext';
import {ADD_PRODUCT} from './util/constants';

export default function CartItemsList() {
	const {
		CartViews,
		cartState,
		isUpdating,
		labels,
		setCartState,
		summaryDataMapper,
	} = useContext(MiniCartContext);

	const {accountId, cartItems = [], summary = {}} = cartState;

	return (
		<div className="mini-cart-items-list">
			<CartViews.ItemsListActions />

			{accountId ? <CartQuickAdd /> : null}

			{cartItems.length ? (
				<>
					<div className="mini-cart-cart-items">
						{cartItems.map((currentCartItem, index) => {
							const updateCartItem = (callback) => {
								const updatedCartItem = callback(
									currentCartItem
								);

								setCartState((cartState) => ({
									...cartState,
									cartItems: cartItems.map((cartItem) =>
										cartItem.id === currentCartItem.id
											? updatedCartItem
											: cartItem
									),
								}));
							};

							return (
								<CartViews.Item
									index={index}
									key={currentCartItem.id}
									updateCartItem={updateCartItem}
									{...currentCartItem}
								/>
							);
						})}
					</div>

					<CartViews.Summary
						dataMapper={summaryDataMapper}
						isLoading={isUpdating}
						summaryData={summary}
					/>
				</>
			) : (
				<div className="empty-cart">
					<div className="empty-cart-icon mb-3">
						<ClayIcon symbol="shopping-cart" />
					</div>

					<p className="empty-cart-label">{labels[ADD_PRODUCT]}</p>
				</div>
			)}
		</div>
	);
}
