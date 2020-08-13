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

import React, {createContext, useContext, useReducer} from 'react';

import {actions} from '../actions/index';
import reducer, {initialState} from '../reducers/index';

export const StoreContext = createContext(null);

export function serializeActions(actions, dispatch) {
	return Object.keys(actions).reduce(
		(curriedActions, actionName) => ({
			...curriedActions,
			[actionName]: actions[actionName](dispatch),
		}),
		{}
	);
}

export function StoreProvider({children, ...stateProps}) {
	const [state, dispatch] = useReducer(reducer, {
		...initialState,
		...stateProps,
	});

	const serializedActions = serializeActions(actions, dispatch);

	return (
		<StoreContext.Provider value={{actions: serializedActions, state}}>
			{children}
		</StoreContext.Provider>
	);
}

export function useAppState() {
	return useContext(StoreContext);
}
