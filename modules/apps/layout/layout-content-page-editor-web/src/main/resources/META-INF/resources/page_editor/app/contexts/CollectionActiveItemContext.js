/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
