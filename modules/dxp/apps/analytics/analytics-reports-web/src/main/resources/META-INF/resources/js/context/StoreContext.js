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

import React, {createContext, useCallback, useContext, useReducer} from 'react';

const INITIAL_STATE = {
	historicalWarning: false,
	publishedToday: false,
	warning: false,
};
const ADD_HISTORICAL_WARNING = 'add-historical-warning';
const ADD_WARNING = 'add-warning';

const noop = () => {};

export const StoreContext = createContext([INITIAL_STATE, noop]);

function reducer(state = INITIAL_STATE, action) {
	if (action.type === ADD_HISTORICAL_WARNING) {
		return state.historicalWarning
			? state
			: {...state, historicalWarning: true};
	}
	else if (action.type === ADD_WARNING) {
		return state.warning ? state : {...state, warning: true};
	}

	return state;
}

export function StoreContextProvider({children, value}) {
	const stateAndDispatch = useReducer(reducer, {...INITIAL_STATE, ...value});

	return (
		<StoreContext.Provider value={stateAndDispatch}>
			{children}
		</StoreContext.Provider>
	);
}

export function useHistoricalWarning() {
	const [state, dispatch] = useContext(StoreContext);

	const addHistoricalWarning = useCallback(() => {
		dispatch({
			type: ADD_HISTORICAL_WARNING,
		});
	}, [dispatch]);

	const hasHistoricalWarning = state.historicalWarning;

	return [hasHistoricalWarning, addHistoricalWarning];
}

export function useWarning() {
	const [state, dispatch] = useContext(StoreContext);

	const addWarning = useCallback(() => {
		dispatch({
			type: ADD_WARNING,
		});
	}, [dispatch]);

	const hasWarning = state.warning;

	return [hasWarning, addWarning];
}
