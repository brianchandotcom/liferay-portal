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
import {
	BoxType,
	TName,
	TObjectField,
	TObjectLayout,
	TObjectRelationship,
} from './types';
declare type TState = {
	enabledCategorization: boolean;
	isViewOnly: boolean;
	objectFieldTypes: ObjectFieldType[];
	objectFields: TObjectField[];
	objectLayout: TObjectLayout;
	objectLayoutId: string;
	objectRelationships: TObjectRelationship[];
};
declare type TAction =
	| {
			payload: {
				enabledCategorization: boolean;
				objectLayout: TObjectLayout;
				objectRelationships: TObjectRelationship[];
			};
			type: TYPES.ADD_OBJECT_LAYOUT;
	  }
	| {
			payload: {
				name: TName;
				objectRelationshipId: number;
			};
			type: TYPES.ADD_OBJECT_LAYOUT_TAB;
	  }
	| {
			payload: {
				name: TName;
				tabIndex?: number;
				type: BoxType;
			};
			type: TYPES.ADD_OBJECT_LAYOUT_BOX;
	  }
	| {
			payload: {
				objectFields: TObjectField[];
			};
			type: TYPES.ADD_OBJECT_FIELDS;
	  }
	| {
			payload: {
				boxIndex: number;
				objectFieldName: string;
				objectFieldSize: number;
				tabIndex: number;
			};
			type: TYPES.ADD_OBJECT_LAYOUT_FIELD;
	  }
	| {
			payload: {
				name: TName;
			};
			type: TYPES.CHANGE_OBJECT_LAYOUT_NAME;
	  }
	| {
			payload: {
				checked: boolean;
			};
			type: TYPES.SET_OBJECT_LAYOUT_AS_DEFAULT;
	  }
	| {
			payload: {
				attribute: {
					key: 'collapsable';
					value: boolean;
				};
				boxIndex: number;
				tabIndex: number;
			};
			type: TYPES.CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE;
	  }
	| {
			payload: {
				boxIndex: number;
				tabIndex: number;
			};
			type: TYPES.DELETE_OBJECT_LAYOUT_BOX;
	  }
	| {
			payload: {
				boxIndex: number;
				columnIndex: number;
				objectFieldName: string;
				rowIndex: number;
				tabIndex: number;
			};
			type: TYPES.DELETE_OBJECT_LAYOUT_FIELD;
	  }
	| {
			payload: {
				tabIndex: number;
			};
			type: TYPES.DELETE_OBJECT_LAYOUT_TAB;
	  };
interface ILayoutContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}
export declare enum TYPES {
	ADD_OBJECT_FIELDS = 'ADD_OBJECT_FIELDS',
	ADD_OBJECT_LAYOUT = 'ADD_OBJECT_LAYOUT',
	ADD_OBJECT_LAYOUT_BOX = 'ADD_OBJECT_LAYOUT_BOX',
	ADD_OBJECT_LAYOUT_FIELD = 'ADD_OBJECT_LAYOUT_FIELD',
	ADD_OBJECT_LAYOUT_TAB = 'ADD_OBJECT_LAYOUT_TAB',
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
export declare function useLayoutContext(): ILayoutContextProps;
export {};
