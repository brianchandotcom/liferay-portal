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
import React, {useContext} from 'react';

import {liferayNavigate} from '../../utilities/index';
import MiniCartContext from './MiniCartContext';

function Wrapper() {
	const {
		CartViews,
		actionURLs,
		cartState,
		isOpen,
		requestQuoteEnabled,
	} = useContext(MiniCartContext);
	const {cartItems = []} = cartState;
	const {orderDetailURL} = actionURLs;

	return (
		<div className="mini-cart-wrapper">
			<CartViews.Header />

			<div className="mini-cart-wrapper-items">
				{isOpen && <CartViews.ItemsList />}
			</div>

			<CartViews.OrderButton />

			{requestQuoteEnabled && !!cartItems.length && (
				<div className="request-quote-wrapper">
					<ClayButton
						block={true}
						className="btn-md request-quote"
						displayType="secondary"
						onClick={() => {
							return liferayNavigate(orderDetailURL);
						}}
					>
						<span className="text-truncate-inline">
							<span className="text-truncate">
								{Liferay.Language.get('request-a-quote')}
							</span>
						</span>
					</ClayButton>
				</div>
			)}
		</div>
	);
}

export default Wrapper;
