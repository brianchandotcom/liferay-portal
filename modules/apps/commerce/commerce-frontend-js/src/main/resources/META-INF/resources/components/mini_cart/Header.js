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

import MiniCartContext from './MiniCartContext';
import {ORDER_IS_EMPTY, YOUR_ORDER} from './util/constants';

function Header() {
	const {cartState, closeCart, labels, spritemap, toggleable} = useContext(
			MiniCartContext
		),
		{cartItems = []} = cartState,
		numberOfItems = cartItems.length > 0 ? cartItems.length : 0;

	return (
		<div className={'mini-cart-header'}>
			<div className={'mini-cart-header-block'}>
				<div className={'mini-cart-header-title'}>
					<h3>
						{!numberOfItems
							? labels[ORDER_IS_EMPTY]
							: labels[YOUR_ORDER]}
					</h3>
				</div>
				{toggleable && (
					<button className={'mini-cart-close'} onClick={closeCart}>
						<ClayIcon spritemap={spritemap} symbol={'times'} />
					</button>
				)}
			</div>
		</div>
	);
}

export default Header;
