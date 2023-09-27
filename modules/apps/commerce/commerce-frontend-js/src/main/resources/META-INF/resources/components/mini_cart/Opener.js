/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import MiniCartContext from './MiniCartContext';

function Opener() {
	const {cartState, displayTotalItemsQuantity, openCart} = useContext(
		MiniCartContext
	);

	const {cartItems = [], summary = {}} = cartState;
	const {itemsQuantity: initialItemsQuantity} = summary;

	const [numberOfItems, setNumberOfItems] = useState(0);

	useEffect(() => {
		setNumberOfItems(initialItemsQuantity);
	}, [initialItemsQuantity, setNumberOfItems]);

	useEffect(() => {
		setNumberOfItems(
			displayTotalItemsQuantity && 'itemsQuantity' in summary
				? summary.itemsQuantity
				: cartItems.length
		);
	}, [cartItems, displayTotalItemsQuantity, summary, setNumberOfItems]);

	return (
		<button
			className={classnames({
				'has-badge': numberOfItems > 0,
				'mini-cart-opener': true,
			})}
			data-badge-count={numberOfItems}
			onClick={openCart}
		>
			<ClayIcon symbol="shopping-cart" />
		</button>
	);
}

Opener.propTypes = {
	openCart: PropTypes.func,
};

export default Opener;
