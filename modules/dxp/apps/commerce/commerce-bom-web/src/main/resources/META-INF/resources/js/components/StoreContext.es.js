/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {createContext, useReducer} from 'react';

import {actions as appActions} from '../actions/app.es';
import {actions as areaActions} from '../actions/area.es';
import {actions as folderActions} from '../actions/folder.es';
import applyMiddleware from '../middleware/index.es';
import appReducer, {initialState as initialAppState} from '../reducers/app.es';
import areaReducer, {
	initialState as initialAreaState,
} from '../reducers/area.es';
import folderReducer, {
	initialState as initialFolderState,
} from '../reducers/folder.es';
import {combineReducers} from './utilities/combineReducers.es';

export const StoreContext = createContext();

function initializeActions(actions, dispatch) {
	return Object.keys(actions).reduce(
		(curriedActions, actionName) => ({
			...curriedActions,
			[actionName]: actions[actionName](dispatch),
		}),
		{}
	);
}

const reducers = combineReducers({
	app: appReducer,
	area: areaReducer,
	folder: folderReducer,
});

export function StoreProvider(props) {
	const [state, dispatch] = useReducer(reducers, {
		app: initialAppState,
		area: initialAreaState,
		folder: initialFolderState,
	});

	const actions = initializeActions(
		{
			...appActions,
			...areaActions,
			...folderActions,
		},
		applyMiddleware(dispatch)
	);

	return (
		<StoreContext.Provider value={{actions, state}}>
			{props.children}
		</StoreContext.Provider>
	);
}

export const StoreConsumer = StoreContext.Consumer;
