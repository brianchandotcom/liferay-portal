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

const ADD_WARNING = 'ADD_WARNING';

const INITIAL_STATE = {
	publishedToday: false,
	warning: false,
};

const noop = () => {};

export const StoreDispatchContext = React.createContext(() => {});
export const StoreStateContext = createContext([INITIAL_STATE, noop]);

function reducer(state = INITIAL_STATE, action) {
	let nextState = state;

	switch (action.type) {
		case ADD_WARNING:
			nextState = state.warning ? state : {...state, warning: true};
			break;
		default:
			return state;
	}

	return nextState;
}

export function StoreContextProvider({children, value}) {
	const [state, dispatch] = useReducer(reducer, {...INITIAL_STATE, ...value});

	return (
		<StoreDispatchContext.Provider value={dispatch}>
			<StoreStateContext.Provider value={state}>
				{children}
			</StoreStateContext.Provider>
		</StoreDispatchContext.Provider>
	);
}
