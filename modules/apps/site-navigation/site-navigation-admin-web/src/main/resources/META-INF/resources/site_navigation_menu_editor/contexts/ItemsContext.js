/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const [items, setItems] = useState(() => getFlatItems(initialItems));

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
