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

import React from 'react';
import {TAction, TState} from './types';
interface IViewContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}
declare const ViewContext: React.Context<IViewContextProps>;
export declare enum TYPES {
	ADD_OBJECT_FIELDS = 'ADD_OBJECT_FIELDS',
	ADD_OBJECT_VIEW = 'ADD_OBJECT_VIEW',
	ADD_OBJECT_CUSTOM_VIEW_FIELD = 'ADD_OBJECT_CUSTOM_VIEW_FIELD',
	ADD_OBJECT_VIEW_COLUMN = 'ADD_OBJECT_VIEW_COLUMN',
	ADD_OBJECT_VIEW_SORT_COLUMN = 'ADD_OBJECT_VIEW_SORT_COLUMN',
	CHANGE_OBJECT_VIEW_NAME = 'CHANGE_OBJECT_VIEW_NAME',
	CHANGE_OBJECT_VIEW_COLUMN_ORDER = 'CHANGE_OBJECT_VIEW_COLUMN_ORDER',
	CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER = 'CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER',
	DELETE_OBJECT_VIEW_COLUMN = 'DELETE_OBJECT_VIEW_COLUMN',
	DELETE_OBJECT_VIEW_SORT_COLUMN = 'DELETE_OBJECT_VIEW_SORT_COLUMN',
	DELETE_OBJECT_CUSTOM_VIEW_FIELD = 'DELETE_OBJECT_CUSTOM_VIEW_FIELD',
	EDIT_OBJECT_VIEW_SORT_COLUMN_SORT_ORDER = 'EDIT_OBJECT_VIEW_SORT_COLUMN_SORT_ORDER',
	SET_OBJECT_VIEW_AS_DEFAULT = 'SET_OBJECT_VIEW_AS_DEFAULT',
}
declare const initialState: TState;
interface IViewContextProviderProps extends React.HTMLAttributes<HTMLElement> {
	value: {
		isFFObjectViewColumnAliasEnabled: boolean;
		isViewOnly: boolean;
		objectViewId: string;
	};
}
export declare function ViewContextProvider({
	children,
	value,
}: IViewContextProviderProps): JSX.Element;
export default ViewContext;
