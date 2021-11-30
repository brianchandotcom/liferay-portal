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

import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import getFlatItems from '../utils/getFlatItems';

export const ItemsContext = React.createContext([]);
export const SetItemsContext = React.createContext(() => {});

export function useItems() {
	return useContext(ItemsContext);
}
export function useSetItems() {
	return useContext(SetItemsContext);
}

export function ItemsProvider({children, initialItems}) {
	const [items, setItems] = useState(getFlatItems(initialItems));

	return (
		<SetItemsContext.Provider value={setItems}>
			<ItemsContext.Provider value={items}>
				{children}
			</ItemsContext.Provider>
		</SetItemsContext.Provider>
	);
}

ItemsProvider.propTypes = {
	initialItems: PropTypes.arrayOf(
		PropTypes.shape({
			children: PropTypes.array.isRequired,
			siteNavigationMenuItemId: PropTypes.string.isRequired,
		}).isRequired
	),
};
