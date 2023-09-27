/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useReducer} from 'react';

export enum TYPES {
	CHANGE_PANEL_EXPANDED = 'CHANGE_PANEL_EXPANDED',
}

type TState = {
	expanded: boolean;
};

type TAction = {
	payload: {expanded: boolean};
	type: TYPES.CHANGE_PANEL_EXPANDED;
};

type TDispatch = React.Dispatch<
	React.ReducerAction<React.Reducer<TState, TAction>>
>;

const initialState = {
	expanded: true,
};

interface IPanelContextProps extends Array<TState | TDispatch> {
	0: typeof initialState;
	1: TDispatch;
}

export const PanelContext = createContext({} as IPanelContextProps);

const reducer = (state: TState, action: TAction) => {
	switch (action.type) {
		case TYPES.CHANGE_PANEL_EXPANDED: {
			const {expanded} = action.payload;

			return {
				...state,
				expanded,
			};
		}
		default:
			return state;
	}
};

export function PanelContextProvider({
	children,
}: React.HTMLAttributes<HTMLElement>) {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<PanelContext.Provider value={[state, dispatch]}>
			{children}
		</PanelContext.Provider>
	);
}

export function usePanelContext() {
	return useContext(PanelContext);
}
