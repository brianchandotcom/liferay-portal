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

const getItemsMap = (items) => {
	const map = new Map();

	items.forEach((item) => {
		map.set(item.siteNavigationMenuItemId, item);
	});

	return map;
};

export const ItemsContext = React.createContext([]);
export const SetItemsContext = React.createContext(() => {});

export const useItems = () => useContext(ItemsContext);
export const useItemsMap = () => getItemsMap(useContext(ItemsContext));
export const useSetItems = () => useContext(SetItemsContext);

export const ItemsProvider = ({children, initialItems}) => {
	const [items, setItems] = useState(initialItems);

	return (
		<SetItemsContext.Provider value={setItems}>
			<ItemsContext.Provider value={items}>
				{children}
			</ItemsContext.Provider>
		</SetItemsContext.Provider>
	);
};

ItemsProvider.propTypes = {
	initialItems: PropTypes.arrayOf(
		PropTypes.shape({
			children: PropTypes.array.isRequired,
			siteNavigationMenuItemId: PropTypes.string.isRequired,
		}).isRequired
	),
};
