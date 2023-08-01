/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useReducer} from 'react';

import {
	LeftSidebarItemType,
	ObjectDefinitionNode,
	TAction,
	TState,
} from '../types';
import {objectFolderReducer} from './objectFolderReducer';

interface IFolderContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}

interface IFolderContextProviderProps
	extends React.HTMLAttributes<HTMLElement> {
	value: {};
}

const FolderContext = createContext({} as IFolderContextProps);

const initialState = {
	leftSidebarItems: [] as LeftSidebarItemType[],
	objectDefinitionNodes: [] as ObjectDefinitionNode[],
	objectDefinitions: [] as ObjectDefinition[],
	objectFolders: [] as ObjectFolder[],
	rightSidebarType: 'empty',
	selectedFolderERC: '',
} as TState;

export function FolderContextProvider({
	children,
	value,
}: IFolderContextProviderProps) {
	const [state, dispatch] = useReducer<React.Reducer<TState, TAction>>(
		objectFolderReducer,
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
