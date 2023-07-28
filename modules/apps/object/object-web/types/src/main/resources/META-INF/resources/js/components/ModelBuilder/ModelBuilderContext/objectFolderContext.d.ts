/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TAction, TState} from '../types';
interface IFolderContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}
interface IFolderContextProviderProps
	extends React.HTMLAttributes<HTMLElement> {
	value: {};
}
declare const initialState: TState;
export declare function FolderContextProvider({
	children,
	value,
}: IFolderContextProviderProps): JSX.Element;
export declare function useFolderContext(): IFolderContextProps;
export {};
