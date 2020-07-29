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

import React, {useContext, useReducer} from 'react';

import {CollectionItemContext, INITIAL_STATE} from './CollectionItemContext';
import {useActiveItemId} from './Controls';

const CollectionActiveItemDispatchContext = React.createContext(() => {});
const CollectionActiveItemStateContext = React.createContext(INITIAL_STATE);

export function CollectionActiveItemContextProvider({children}) {
	const [state, dispatch] = useReducer(
		(state, action) => (state !== action ? action : state),
		INITIAL_STATE
	);

	return (
		<CollectionActiveItemDispatchContext.Provider value={dispatch}>
			<CollectionActiveItemStateContext.Provider value={state}>
				{children}
			</CollectionActiveItemStateContext.Provider>
		</CollectionActiveItemDispatchContext.Provider>
	);
}

export function useSetCollectionActiveItemContext(itemId) {
	const activeItemId = useActiveItemId();
	const collectionContext = useContext(CollectionItemContext);
	const dispatch = useContext(CollectionActiveItemDispatchContext);

	if (itemId === activeItemId) {
		dispatch(collectionContext);
	}
}

export function useCollectionActiveItemContext() {
	return useContext(CollectionActiveItemStateContext);
}
