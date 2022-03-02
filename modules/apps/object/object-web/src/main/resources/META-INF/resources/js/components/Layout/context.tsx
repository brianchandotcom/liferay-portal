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

import {
	findObjectFieldIndex,
	findObjectLayoutRowIndex,
} from '../../utils/layout';
import {BoxesVisitor} from '../../utils/visitor';
import {TObjectField, TObjectLayout, TObjectRelationship} from './types';

type TState = {
	isViewOnly: boolean;
	objectFieldTypes: ObjectFieldType[];
	objectFields: TObjectField[];
	objectLayout: TObjectLayout;
	objectLayoutId: string;
	objectRelationships: TObjectRelationship[];
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

export enum TYPES {
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

const initialState = {
	objectFields: [] as TObjectField[],
	objectLayout: {} as TObjectLayout,
	objectRelationships: [] as TObjectRelationship[],
} as TState;

const layoutReducer = (state: TState, action: TAction) => {
	switch (action.type) {
		case TYPES.ADD_OBJECT_LAYOUT: {
			const {objectLayout} = action.payload;

			return {
				...state,
				objectLayout,
			};
		}
		case TYPES.ADD_OBJECT_RELATIONSHIPS: {
			const {objectRelationships} = action.payload;

			return {
				...state,
				objectRelationships,
			};
		}
		case TYPES.ADD_OBJECT_LAYOUT_TAB: {
			const {name, objectRelationshipId} = action.payload;

			const newState = {...state};

			const newObjectLayoutTab = {
				name,
				objectLayoutBoxes: [],
				objectRelationshipId,
				priority: 0,
			};

			if (objectRelationshipId) {
				newState.objectRelationships[
					findObjectFieldIndex(
						newState.objectRelationships,
						objectRelationshipId
					)
				].inLayout = true;
			}

			if (state.objectLayout.objectLayoutTabs.length) {
				return {
					...newState,
					objectLayout: {
						...newState.objectLayout,
						objectLayoutTabs: [
							...newState.objectLayout.objectLayoutTabs,
							newObjectLayoutTab,
						],
					},
				};
			}

			return {
				...newState,
				objectLayout: {
					...newState.objectLayout,
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
				objectFields,
			};
		}
		case TYPES.ADD_OBJECT_LAYOUT_FIELD: {
			const {
				boxIndex,
				objectFieldId,
				objectFieldSize,
				tabIndex,
			} = action.payload;

			const newState = {...state};

			const newField = {
				objectFieldId,
				priority: 0,
				size: objectFieldSize,
			};

			const objectLayoutBox =
				newState.objectLayout.objectLayoutTabs[tabIndex]
					.objectLayoutBoxes[boxIndex];

			const objectLayoutRowIndex = findObjectLayoutRowIndex(
				objectLayoutBox.objectLayoutRows,
				objectFieldSize
			);

			if (objectLayoutRowIndex > -1) {
				objectLayoutBox.objectLayoutRows[
					objectLayoutRowIndex
				].objectLayoutColumns.push(newField);
			}
			else {
				objectLayoutBox.objectLayoutRows.push({
					objectLayoutColumns: [newField],
					priority: 0,
				});
			}

			newState.objectFields[
				findObjectFieldIndex(newState.objectFields, objectFieldId)
			].inLayout = true;

			return newState;
		}
		case TYPES.CHANGE_OBJECT_LAYOUT_NAME: {
			const {name} = action.payload;

			return {
				...state,
				objectLayout: {
					...state.objectLayout,
					name,
				},
			};
		}
		case TYPES.SET_OBJECT_LAYOUT_AS_DEFAULT: {
			const {checked} = action.payload;

			return {
				...state,
				objectLayout: {
					...state.objectLayout,
					defaultObjectLayout: checked,
				},
			};
		}
		case TYPES.CHANGE_OBJECT_LAYOUT_BOX_ATTRIBUTE: {
			type TObjectLayoutBoxAttribute = {
				key: keyof {collapsable: boolean};
				value: any;
			};

			const {attribute, boxIndex, tabIndex} = action.payload;
			const {key, value}: TObjectLayoutBoxAttribute = attribute;

			const newState = {...state};

			const objectLayoutBox =
				newState.objectLayout.objectLayoutTabs[tabIndex]
					.objectLayoutBoxes[boxIndex];

			objectLayoutBox[key] = value;

			return newState;
		}
		case TYPES.DELETE_OBJECT_LAYOUT_BOX: {
			const {boxIndex, tabIndex} = action.payload;

			const newState = {...state};

			// Change object field inLayout attribute to false to be visible when add field again.

			const objectFieldIds = newState.objectFields.map(({id}) => id);
			const visitor = new BoxesVisitor(
				newState.objectLayout.objectLayoutTabs[tabIndex]
			);

			visitor.mapFields((field) => {
				const objectIndex = objectFieldIds.indexOf(field.objectFieldId);
				newState.objectFields[objectIndex].inLayout = false;
			});

			// Delete object layout box

			newState.objectLayout.objectLayoutTabs[
				tabIndex
			].objectLayoutBoxes.splice(boxIndex, 1);

			return newState;
		}
		case TYPES.DELETE_OBJECT_LAYOUT_FIELD: {
			const {
				boxIndex,
				columnIndex,
				objectFieldId,
				rowIndex,
				tabIndex,
			} = action.payload;

			const newState = {...state};

			const objectLayoutBox =
				newState.objectLayout.objectLayoutTabs[tabIndex]
					.objectLayoutBoxes[boxIndex];

			const objectLayoutRow = objectLayoutBox.objectLayoutRows[rowIndex];

			objectLayoutRow.objectLayoutColumns.splice(columnIndex, 1);

			if (!objectLayoutRow.objectLayoutColumns.length) {
				objectLayoutBox.objectLayoutRows.splice(rowIndex, 1);
			}

			const objectFieldIndex = findObjectFieldIndex(
				newState.objectFields,
				objectFieldId
			);

			newState.objectFields[objectFieldIndex].inLayout = false;

			return newState;
		}
		case TYPES.DELETE_OBJECT_LAYOUT_TAB: {
			const {tabIndex} = action.payload;

			const newState = {...state};

			const objectRelationshipId =
				newState.objectLayout.objectLayoutTabs[tabIndex]
					.objectRelationshipId;

			if (objectRelationshipId) {
				const objectRelationshipIds = newState.objectRelationships.map(
					({id}) => id
				);
				const objectRelationshipIndex = objectRelationshipIds.indexOf(
					objectRelationshipId
				);

				newState.objectRelationships[
					objectRelationshipIndex
				].inLayout = false;
			}

			// Change object field inLayout attribute to false to be visible when add field again.

			const objectFieldIds = newState.objectFields.map(({id}) => id);
			const visitor = new BoxesVisitor(
				newState.objectLayout.objectLayoutTabs[tabIndex]
			);

			visitor.mapFields((field) => {
				if (field.objectFieldId) {
					const objectFieldIndex = objectFieldIds.indexOf(
						field.objectFieldId
					);
					newState.objectFields[objectFieldIndex].inLayout = false;
				}
			});

			// Delete object layout tab

			newState.objectLayout.objectLayoutTabs.splice(tabIndex, 1);

			return newState;
		}
		default:
			return state;
	}
};

interface ILayoutContextProviderProps
	extends React.HTMLAttributes<HTMLElement> {
	value: {
		isViewOnly: boolean;
		objectFieldTypes: ObjectFieldType[];
		objectLayoutId: string;
	};
}

export function LayoutContextProvider({
	children,
	value,
}: ILayoutContextProviderProps) {
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
}

export default LayoutContext;
