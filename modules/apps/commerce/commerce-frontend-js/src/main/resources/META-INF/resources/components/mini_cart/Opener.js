/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {
	OPEN_MINICART_FOR_EDITING,
	OPEN_MINI_CART,
} from '../../utilities/eventsDefinitions';
import MiniCartContext from './MiniCartContext';
import {hasOptions} from './util/index';

function Opener() {
	const {cartState, displayTotalItemsQuantity, openCart, setEditedItem} =
		useContext(MiniCartContext);

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

	const openMiniCartForEditing = useCallback(
		({dataSetId, orderItemId}) => {
			const cartItem = cartItems.find(
				(cartItem) => cartItem.id === orderItemId
			);

			if (
				cartItem &&
				(hasOptions(cartItem.options) || cartItem.skuUnitOfMeasure)
			) {
				setEditedItem({
					cartItemId: orderItemId,
					dataSetId,
					name: cartItem.name,
					productId: cartItem.productId,
				});

				openCart();
			}
		},
		[cartItems, openCart, setEditedItem]
	);

	useEffect(() => {
		Liferay.on(OPEN_MINICART_FOR_EDITING, openMiniCartForEditing);
		Liferay.on(OPEN_MINI_CART, openCart);

		return () => {
			Liferay.detach(OPEN_MINICART_FOR_EDITING, openMiniCartForEditing);
			Liferay.detach(OPEN_MINI_CART, openCart);
		};
	}, [openCart, openMiniCartForEditing]);

	return (
		<button
			className={classnames({
				'has-badge': numberOfItems > 0,
				'mini-cart-opener': true,
			})}
			data-badge-count={numberOfItems}
			data-qa-id="miniCartButton"
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
