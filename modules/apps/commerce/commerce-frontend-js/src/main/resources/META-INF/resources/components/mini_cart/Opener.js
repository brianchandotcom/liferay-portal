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
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import MiniCartContext from './MiniCartContext';

function Opener({openCart}) {
	const {cartState, spritemap} = useContext(MiniCartContext),
		{cartItems = []} = cartState,
		numberOfItems = cartItems.length > 0 ? cartItems.length : 0;

	return (
		<button
			className={classnames(
				'mini-cart-opener',
				!!numberOfItems && 'has-badge'
			)}
			data-badge-count={numberOfItems}
			onClick={openCart}
		>
			<ClayIcon spritemap={spritemap} symbol={'shopping-cart'} />
		</button>
	);
}

Opener.propTypes = {
	openCart: PropTypes.func,
};

export default Opener;
