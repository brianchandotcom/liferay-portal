/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	rightSidebarType: 'objectRelationshipDetails',
	selectedDefinitionNode: {
		active: false,
		dbTableName: 'P_Address_',
		defaultLanguageId: 'en_US',
		hasUpdateObjectDefinitionPermission: false,
		label: {en_US: 'Postal Address'},
		modifiable: true,
		name: 'Address',
		objectFields: [{}],
		pluralLabel: {en_US: 'Postal Addresses'},
		scope: 'company',
		status: {
			label: 'Approved',
		},
		system: false,
	},
	selectedObjectRelationship: {
		deletionType: 'Prevent',
		label: {en_US: 'External Reference Code'},
		name: 'erc',
		objectDefinitionName2: 'User',
		type: 'manyToMany',
	},
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
