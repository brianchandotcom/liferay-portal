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

function Header() {
	const {cartState, closeCart, spritemap} = useContext(MiniCartContext),
		{cartItems = []} = cartState,
		numberOfItems = cartItems.length > 0 ? cartItems.length : 0;

	return (
		<div className={'mini-cart-header'}>
			<div className={'mini-cart-header-block'}>
				<div className={'mini-cart-header-title'}>
					<h3>
						{!numberOfItems
							? Liferay.Language.get('your-order-is-empty')
							: Liferay.Language.get('your-order')}
					</h3>
				</div>
				<button className={'mini-cart-close'} onClick={closeCart}>
					<ClayIcon spritemap={spritemap} symbol={'times'} />
				</button>
			</div>
		</div>
	);
}

export default Header;
