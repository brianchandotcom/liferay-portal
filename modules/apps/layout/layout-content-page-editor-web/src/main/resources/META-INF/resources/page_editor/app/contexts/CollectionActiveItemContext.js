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

import React, {useContext, useState} from 'react';

import {ITEM_TYPES} from '../config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {CollectionItemContext, INITIAL_STATE} from './CollectionItemContext';
import {
	useActiveItemId,
	useActiveItemType,
	useIsActive,
} from './ControlsContext';
import {useSelectorCallback} from './StoreContext';

const CollectionActiveItemDispatchContext = React.createContext(() => {});
const CollectionActiveItemStateContext = React.createContext(INITIAL_STATE);

export function CollectionActiveItemContextProvider({children}) {
	const [state, setState] = useState(INITIAL_STATE);

	return (
		<CollectionActiveItemDispatchContext.Provider value={setState}>
			<CollectionActiveItemStateContext.Provider value={state}>
				{children}
			</CollectionActiveItemStateContext.Provider>
		</CollectionActiveItemDispatchContext.Provider>
	);
}

export function useSetCollectionActiveItemContext(itemId) {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();
	const isActive = useIsActive();
	const collectionContext = useContext(CollectionItemContext);
	const setState = useContext(CollectionActiveItemDispatchContext);

	const item = useSelectorCallback(
		(state) => state.layoutData.items[itemId],
		[itemId]
	);

	if (
		isActive(itemId) ||
		(item &&
			item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
			activeItemType === ITEM_TYPES.editable &&
			activeItemId &&
			activeItemId.startsWith(item.config.fragmentEntryLinkId))
	) {
		setState(collectionContext);
	}
}

export function useCollectionActiveItemContext() {
	return useContext(CollectionActiveItemStateContext);
}
