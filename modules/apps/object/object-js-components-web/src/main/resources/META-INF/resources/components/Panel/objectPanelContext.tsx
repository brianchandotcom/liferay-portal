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
