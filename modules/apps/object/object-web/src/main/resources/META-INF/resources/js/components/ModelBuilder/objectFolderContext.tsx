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

import {TState} from './types';

interface IFolderContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}

interface IFolderContextProviderProps
	extends React.HTMLAttributes<HTMLElement> {
	value: {
		objectDefinitions: ObjectDefinition[];
	};
}

export enum TYPES {
	EDIT_OBJECT_DEFINITION = 'EDIT_OBJECT_DEFINITION',
}

export type TAction = {
	payload: {
		objectDefinition: ObjectDefinition[];
	};
	type: TYPES.EDIT_OBJECT_DEFINITION;
};

const FolderContext = createContext({} as IFolderContextProps);

const initialState = {
	objectDefinitions: {} as ObjectDefinition[],
} as TState;

const folderReducer = (state: TState, action: TAction) => {
	switch (action.type) {
		default:
			return state;
	}
};

export function FolderContextProvider({
	children,
	value,
}: IFolderContextProviderProps) {
	const [state, dispatch] = useReducer<React.Reducer<TState, TAction>>(
		folderReducer,
		{
			...initialState,
			...value,
		}
	);

	return (
		<FolderContext.Provider value={[state, dispatch]}>
			{children}
		</FolderContext.Provider>
	);
}

export function useFolderContext() {
	return useContext(FolderContext);
}
