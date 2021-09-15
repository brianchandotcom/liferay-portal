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

import React, {createContext, useReducer} from 'react';

import {TObjectField, TObjectLayout} from './types';

type TState = {
	objectLayout: TObjectLayout;
	objectFields: TObjectField[];
	objectLayoutId: string;
};

type TAction = {
	payload: {[key: string]: any};
	type: keyof typeof TYPES;
};

interface ILayoutContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}

const LayoutContext = createContext({} as ILayoutContextProps);

export const TYPES = {
	ADD_OBJECT_FIELDS: 'ADD_OBJECT_FIELDS',
	ADD_OBJECT_LAYOUT: 'ADD_OBJECT_LAYOUT',
	ADD_OBJECT_LAYOUT_BOX: 'ADD_OBJECT_LAYOUT_BOX',
	ADD_OBJECT_LAYOUT_FIELD: 'ADD_OBJECT_LAYOUT_FIELD',
	ADD_OBJECT_LAYOUT_TAB: 'ADD_OBJECT_LAYOUT_TAB',
	CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE: 'CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE',
	DELETE_OBJECT_LAYOUT_BOX: 'DELETE_OBJECT_LAYOUT_BOX',
	DELETE_OBJECT_LAYOUT_FIELD: 'DELETE_OBJECT_LAYOUT_FIELD',
	DELETE_OBJECT_LAYOUT_TAB: 'DELETE_OBJECT_LAYOUT_TAB',
} as const;

const initialState = {
	objectFields: [] as TObjectField[],
	objectLayout: {} as TObjectLayout,
} as TState;

type TResetObjectFieldProperties = (
	objectFields: TObjectField[]
) => TObjectField[];

const resetObjectFieldProperties: TResetObjectFieldProperties = (
	objectFields
) => {
	return objectFields.map((objectField) => {
		return {
			...objectField,
			inLayout: false,
		};
	});
};

type TGetObjectFieldIndex = (
	objectFields: TObjectField[],
	objectFieldId: number
) => number;

const getObjectFieldIndex: TGetObjectFieldIndex = (
	objectFields,
	objectFieldId
) => {
	const objectFieldIds = objectFields.map(({id}) => id);
	const objectFieldIndex = objectFieldIds.indexOf(objectFieldId);

	return objectFieldIndex;
};

const layoutReducer = (state: TState, action: TAction) => {
	switch (action.type) {
		case TYPES.ADD_OBJECT_LAYOUT: {
			const {objectLayout} = action.payload;

			return {
				...state,
				objectLayout,
			};
		}
		case TYPES.ADD_OBJECT_LAYOUT_TAB: {
			const {name} = action.payload;

			const newObjectLayoutTab = {
				name,
				objectLayoutBoxes: [],
				objectRelationshipId: 0,
				priority: state.objectLayout.objectLayoutTabs.length,
			};

			if (state.objectLayout.objectLayoutTabs.length) {
				return {
					...state,
					objectLayout: {
						...state.objectLayout,
						objectLayoutTabs: [
							...state.objectLayout.objectLayoutTabs,
							newObjectLayoutTab,
						],
					},
				};
			}

			return {
				...state,
				objectLayout: {
					...state.objectLayout,
					objectLayoutTabs: [newObjectLayoutTab],
				},
			};
		}
		case TYPES.ADD_OBJECT_LAYOUT_BOX: {
			const {name, tabIndex} = action.payload;

			const newState = {...state};

			newState.objectLayout.objectLayoutTabs[
				tabIndex
			].objectLayoutBoxes.push({
				collapsable: false,
				name,
				objectLayoutRows: [],
				priority: 0,
			});

			return newState;
		}
		case TYPES.ADD_OBJECT_FIELDS: {
			const {objectFields} = action.payload;

			return {
				...state,
				currentObjectFields: [...objectFields],
				objectFields,
			};
		}
		case TYPES.ADD_OBJECT_LAYOUT_FIELD: {
			const {boxIndex, objectFieldId, tabIndex} = action.payload;

			const newState = {...state};

			newState.objectLayout.objectLayoutTabs[tabIndex].objectLayoutBoxes[
				boxIndex
			].objectLayoutRows.push({
				objectLayoutColumns: [
					{
						objectFieldId,
						priority: 0,
					},
				],
				priority: 0,
			});

			newState.objectFields[
				getObjectFieldIndex(newState.objectFields, objectFieldId)
			].inLayout = true;

			return newState;
		}
		case TYPES.CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE: {
			const {boxIndex, collapsable, tabIndex} = action.payload;

			const newState = {...state};

			newState.objectLayout.objectLayoutTabs[tabIndex].objectLayoutBoxes[
				boxIndex
			].collapsable = collapsable;

			return newState;
		}
		case TYPES.DELETE_OBJECT_LAYOUT_BOX: {
			const {boxIndex, tabIndex} = action.payload;

			const newState = {...state};

			newState.objectLayout.objectLayoutTabs[
				tabIndex
			].objectLayoutBoxes.splice(boxIndex, 1);

			newState.objectFields = resetObjectFieldProperties(
				newState.objectFields
			);

			return newState;
		}
		case TYPES.DELETE_OBJECT_LAYOUT_FIELD: {
			const {
				boxIndex,
				objectFieldId,
				rowIndex,
				tabIndex,
			} = action.payload;

			const newState = {...state};

			// Delete a line because we only have one field per line.
			// In the future, we'll have drag and drop, so we'll just
			// need to implement field removal

			newState.objectLayout.objectLayoutTabs[tabIndex].objectLayoutBoxes[
				boxIndex
			].objectLayoutRows.splice(rowIndex, 1);

			const objectFieldIndex = getObjectFieldIndex(
				newState.objectFields,
				objectFieldId
			);

			newState.objectFields[objectFieldIndex].inLayout = false;

			return newState;
		}
		case TYPES.DELETE_OBJECT_LAYOUT_TAB: {
			const {tabIndex} = action.payload;

			const newState = {...state};

			newState.objectLayout.objectLayoutTabs.splice(tabIndex, 1);

			newState.objectFields = resetObjectFieldProperties(
				newState.objectFields
			);

			return newState;
		}
		default:
			return state;
	}
};

interface ILayoutContextProviderProps
	extends React.HTMLAttributes<HTMLElement> {
	value: {
		objectLayoutId: string;
	};
}

export const LayoutContextProvider: React.FC<ILayoutContextProviderProps> = ({
	children,
	value,
}) => {
	const [state, dispatch] = useReducer<React.Reducer<TState, TAction>>(
		layoutReducer,
		{
			...initialState,
			...value,
		}
	);

	return (
		<LayoutContext.Provider value={[state, dispatch]}>
			{children}
		</LayoutContext.Provider>
	);
};

export default LayoutContext;
