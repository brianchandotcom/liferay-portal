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
import {TObjectField, TObjectLayout, TObjectRelationship} from './types';
declare type TState = {
	isViewOnly: boolean;
	objectFieldTypes: ObjectFieldType[];
	objectFields: TObjectField[];
	objectLayout: TObjectLayout;
	objectLayoutId: string;
	objectRelationships: TObjectRelationship[];
};
declare type TAction = {
	payload: {
		[key: string]: any;
	};
	type: keyof typeof TYPES;
};
interface ILayoutContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}
declare const LayoutContext: React.Context<ILayoutContextProps>;
export declare enum TYPES {
	ADD_OBJECT_FIELDS = 'ADD_OBJECT_FIELDS',
	ADD_OBJECT_LAYOUT = 'ADD_OBJECT_LAYOUT',
	ADD_OBJECT_LAYOUT_BOX = 'ADD_OBJECT_LAYOUT_BOX',
	ADD_OBJECT_LAYOUT_FIELD = 'ADD_OBJECT_LAYOUT_FIELD',
	ADD_OBJECT_LAYOUT_TAB = 'ADD_OBJECT_LAYOUT_TAB',
	ADD_OBJECT_RELATIONSHIPS = 'ADD_OBJECT_RELATIONSHIPS',
	CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE = 'CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE',
	CHANGE_OBJECT_LAYOUT_NAME = 'CHANGE_OBJECT_LAYOUT_NAME',
	DELETE_OBJECT_LAYOUT_BOX = 'DELETE_OBJECT_LAYOUT_BOX',
	DELETE_OBJECT_LAYOUT_FIELD = 'DELETE_OBJECT_LAYOUT_FIELD',
	DELETE_OBJECT_LAYOUT_TAB = 'DELETE_OBJECT_LAYOUT_TAB',
	SET_OBJECT_LAYOUT_AS_DEFAULT = 'SET_OBJECT_LAYOUT_AS_DEFAULT',
}
declare const initialState: TState;
interface ILayoutContextProviderProps
	extends React.HTMLAttributes<HTMLElement> {
	value: {
		isViewOnly: boolean;
		objectFieldTypes: ObjectFieldType[];
		objectLayoutId: string;
	};
}
export declare function LayoutContextProvider({
	children,
	value,
}: ILayoutContextProviderProps): JSX.Element;
export default LayoutContext;
