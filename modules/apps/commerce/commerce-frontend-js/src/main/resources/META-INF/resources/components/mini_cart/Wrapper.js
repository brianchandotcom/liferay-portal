/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import MiniCartContext from './MiniCartContext';
import {hasPriceOnApplication} from './util/index';

function Wrapper() {
	const {CartViews, cartState, isOpen, requestQuoteEnabled} = useContext(
		MiniCartContext
	);
	const {cartItems = []} = cartState;
	const cartHasPriceOnApplicationItems = hasPriceOnApplication(cartItems);

	return (
		<div className="mini-cart-wrapper">
			<CartViews.Header />

			<div className="mini-cart-wrapper-items">
				{isOpen && (
					<CartViews.ItemsList
						showPriceOnApplicationInfo={
							cartHasPriceOnApplicationItems
						}
					/>
				)}
			</div>

			<CartViews.OrderButton
				disabled={!cartItems.length || cartHasPriceOnApplicationItems}
			/>

			{(requestQuoteEnabled || cartHasPriceOnApplicationItems) &&
				!!cartItems.length && <CartViews.RequestQuoteButton />}
		</div>
	);
}

export default Wrapper;
