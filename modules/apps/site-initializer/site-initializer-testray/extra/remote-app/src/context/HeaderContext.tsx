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

import {createContext, useReducer} from 'react';

import {ActionMap} from '../types';

type Tabs = {
	active: boolean;
	path: string;
	title: string;
};

type HeaderTitle = {
	category: string;
	title: string;
};

type InitialState = {
	tabs: Tabs[];
	title: HeaderTitle;
};

export const initialState: InitialState = {
	tabs: [],
	title: {
		category: 'PROJECT',
		title: 'Project Directory',
	},
};

export const HeaderContext = createContext<
	[InitialState, (param: AppActions) => void]
>([initialState, () => null]);

export enum HeaderTypes {
	SET_RESET_HEADER = 'SET_RESET_HEADER',
	SET_TABS = 'SET_TABS',
	SET_TITLE = 'SET_TITLE',
}

export type HeaderActionsPayload = {
	[HeaderTypes.SET_RESET_HEADER]: null;
	[HeaderTypes.SET_TABS]: Tabs[];
	[HeaderTypes.SET_TITLE]: HeaderTitle;
};

export type AppActions = ActionMap<HeaderActionsPayload>[keyof ActionMap<
	HeaderActionsPayload
>];

const reducer = (state: InitialState, action: AppActions): InitialState => {
	switch (action.type) {
		case HeaderTypes.SET_TABS: {
			return {
				...state,
				tabs: action.payload,
			};
		}

		case HeaderTypes.SET_TITLE: {
			return {
				...state,
				title: action.payload,
			};
		}

		case HeaderTypes.SET_RESET_HEADER: {
			return initialState;
		}

		default:
			return state;
	}
};

const HeaderContextProvider: React.FC = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<HeaderContext.Provider value={[state, dispatch]}>
			{children}
		</HeaderContext.Provider>
	);
};

export default HeaderContextProvider;
