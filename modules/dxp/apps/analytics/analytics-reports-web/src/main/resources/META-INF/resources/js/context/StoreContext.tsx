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

interface Action {
	type: 'ADD_WARNING' | null;
}

interface State {
	publishedToday: boolean;
	warning: boolean;
}

const ADD_WARNING = 'ADD_WARNING';

const INITIAL_STATE: State = {
	publishedToday: false,
	warning: false,
};

export const StoreDispatchContext = React.createContext<React.Dispatch<Action>>(
	() => {}
);

export const StoreStateContext = createContext<State>(INITIAL_STATE);

function reducer(state = INITIAL_STATE, action: Action) {
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

interface Props {
	children: React.ReactNode;
	value: object;
}

export function StoreContextProvider({children, value}: Props) {
	const [state, dispatch] = useReducer(reducer, {...INITIAL_STATE, ...value});

	return (
		<StoreDispatchContext.Provider value={dispatch}>
			<StoreStateContext.Provider value={state}>
				{children}
			</StoreStateContext.Provider>
		</StoreDispatchContext.Provider>
	);
}
