/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
