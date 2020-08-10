import React, { createContext, useReducer } from 'react';

import { combineReducers } from 'redux';

import applyMiddleware from '../middleware/index.es';

import appReducer, { initialState as initialAppState } from '../reducers/app.es';
import areaReducer, {
	initialState as initialAreaState
} from '../reducers/area.es';

import { actions as appActions } from '../actions/app.es';
import { actions as areaActions } from '../actions/area.es';

export const StoreContext = createContext();

export function initializeActions(actions, dispatch) {
	return Object.keys(actions).reduce(
		(curriedActions, actionName) => ({
			...curriedActions,
			[actionName]: actions[actionName](dispatch)
		}),
		{}
	);
}

const reducers = combineReducers({
	app: appReducer,
	area: areaReducer
});

export function StoreProvider(props) {
	const [state, dispatch] = useReducer(
		reducers,
		Object.assign(
			{},
			{
				app: initialAppState,
				area: initialAreaState
			}
		)
	);

	const actions = initializeActions(
		Object.assign({}, appActions, areaActions),
		applyMiddleware(dispatch)
	);

	return (
		<StoreContext.Provider value={{ state, actions }}>
			{props.children}
		</StoreContext.Provider>
	);
}

export default StoreContext;
export const StoreConsumer = StoreContext.Consumer;
