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
import classnames from 'classnames';
import React, {useContext, useState} from 'react';

import {PRODUCT_REMOVED_FROM_CART} from '../../utilities/eventsDefinitions';
import {liferayNavigate} from '../../utilities/index';
import {ALL} from '../add_to_cart/constants';
import MiniCartContext from './MiniCartContext';
import {REMOVE_ALL_ITEMS, VIEW_DETAILS} from './util/constants';

function CartItemsListActions() {
	const {
		CartResource,
		actionURLs,
		cartState,
		labels,
		setIsUpdating,
		updateCartModel,
	} = useContext(MiniCartContext);

	const {cartItems = [], id: orderId} = cartState;
	const {orderDetailURL} = actionURLs;

	const [isAsking, setIsAsking] = useState(false);

	const askConfirmation = () => setIsAsking(true);
	const cancel = () => setIsAsking(false);
	const flushCart = () => {
		setIsUpdating(true);

		CartResource.updateCartById(orderId, {cartItems: []})
			.then(() => updateCartModel({id: orderId}))
			.then(() => {
				setIsAsking(false);
				setIsUpdating(false);

				Liferay.fire(PRODUCT_REMOVED_FROM_CART, {skuId: ALL});
			});
	};

	return (
		<div className="mini-cart-header">
			<div className="mini-cart-header-block">
				<div className="mini-cart-header-resume">
					{cartItems.length > 0 && (
						<>
							<span className="items">{cartItems.length}</span>
							{` ${
								cartItems.length > 1
									? Liferay.Language.get('products')
									: Liferay.Language.get('product')
							}`}
						</>
					)}
				</div>

				<div className="mini-cart-header-actions">
					<span
						className={classnames({
							actions: true,
							hide: isAsking,
						})}
					>
						<ClayButton
							className="action"
							disabled={!cartItems.length}
							displayType="link"
							onClick={() => {
								liferayNavigate(orderDetailURL);
							}}
							small
						>
							{labels[VIEW_DETAILS]}
						</ClayButton>

						<ClayButton
							className="action text-danger"
							disabled={!cartItems.length}
							displayType="link"
							onClick={askConfirmation}
							small
						>
							{labels[REMOVE_ALL_ITEMS]}
						</ClayButton>
					</span>

					<div
						className={classnames({
							'confirmation-prompt': true,
							'hide': !isAsking,
						})}
					>
						<span>{Liferay.Language.get('are-you-sure')}</span>

						<span>
							<button
								className="btn btn-outline-success btn-sm"
								onClick={flushCart}
								type="button"
							>
								{Liferay.Language.get('yes')}
							</button>
							<button
								className="btn btn-outline-danger btn-sm"
								onClick={cancel}
								type="button"
							>
								{Liferay.Language.get('no')}
							</button>
						</span>
					</div>
				</div>
			</div>
		</div>
	);
}

export default CartItemsListActions;
