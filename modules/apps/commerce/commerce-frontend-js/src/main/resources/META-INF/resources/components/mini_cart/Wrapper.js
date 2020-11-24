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

import React, {useContext} from 'react';

import DatasetDisplay from '../dataset_display/DatasetDisplay';
import MiniCartContext from './MiniCartContext';
import {ITEMS_LIST} from './util/constants';

function Wrapper() {
	const {CartViews, cartState, isOpen, spritemap} = useContext(
		MiniCartContext
	);

	const {cartItems = []} = cartState;

	return (
		<div className={'mini-cart-wrapper'}>
			<CartViews.Header />

			<div className={'mini-cart-wrapper-items'}>
				{isOpen && (
					<DatasetDisplay
						id={'cart-items-list-dataset-display'}
						items={cartItems}
						overrideEmptyResultView={true}
						showManagementBar={false}
						showPagination={false}
						sidePanelId={'sidePanelDisabled'}
						spritemap={spritemap}
						views={[{component: CartViews[ITEMS_LIST]}]}
					/>
				)}
			</div>

			<CartViews.OrderButton />
		</div>
	);
}

export default Wrapper;
