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
	TAction,
	TObjectField,
	TObjectView,
	TObjectViewColumn,
	TState,
} from './types';
interface IViewContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}

const ViewContext = createContext({} as IViewContextProps);

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const METADATAS = [
	{
		checked: false,
		filtered: true,
		id: 1,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {[defaultLanguageId]: Liferay.Language.get('author')},
		listTypeDefinitionId: true,
		name: 'author',
		required: false,
		type: 'metadata',
	},
	{
		checked: false,
		filtered: true,
		id: 2,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {[defaultLanguageId]: Liferay.Language.get('creation-date')},
		listTypeDefinitionId: true,
		name: 'creationDate',
		required: false,
		type: 'metadata',
	},
	{
		checked: false,
		filtered: true,
		id: 3,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {[defaultLanguageId]: Liferay.Language.get('modified-date')},
		listTypeDefinitionId: true,
		name: 'modifiedDate',
		required: false,
		type: 'metadata',
	},
	{
		checked: false,
		filtered: true,
		id: 4,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {
			[defaultLanguageId]: Liferay.Language.get(
				'workflow-status[object]'
			),
		},
		listTypeDefinitionId: true,
		name: 'workflowStatus',
		required: false,
		type: 'metadata',
	},
	{
		checked: false,
		filtered: true,
		id: 5,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {[defaultLanguageId]: Liferay.Language.get('id')},
		listTypeDefinitionId: true,
		name: 'id',
		required: false,
		type: 'metadata',
	},
];

export enum TYPES {
	ADD_OBJECT_FIELDS = 'ADD_OBJECT_FIELDS',
	ADD_OBJECT_VIEW = 'ADD_OBJECT_VIEW',
	ADD_OBJECT_CUSTOM_VIEW_FIELD = 'ADD_OBJECT_CUSTOM_VIEW_FIELD',
	ADD_OBJECT_VIEW_COLUMN = 'ADD_OBJECT_VIEW_COLUMN',
	CHANGE_OBJECT_VIEW_NAME = 'CHANGE_OBJECT_VIEW_NAME',
	CHANGE_OBJECT_VIEW_COLUMN_ORDER = 'CHANGE_OBJECT_VIEW_COLUMN_ORDER',
	DELETE_OBJECT_VIEW_COLUMN = 'DELETE_OBJECT_VIEW_COLUMN',
	DELETE_OBJECT_CUSTOM_VIEW_FIELD = 'DELETE_OBJECT_CUSTOM_VIEW_FIELD',
	SET_OBJECT_VIEW_AS_DEFAULT = 'SET_OBJECT_VIEW_AS_DEFAULT',
}

const initialState = {
	objectFields: [] as TObjectField[],
	objectView: {} as TObjectView,
} as TState;

const viewReducer = (state: TState, action: TAction) => {
	switch (action.type) {
		case TYPES.ADD_OBJECT_VIEW: {
			const {objectView} = action.payload;

			return {
				...state,
				objectView,
			};
		}
		case TYPES.ADD_OBJECT_VIEW_COLUMN: {
			const {filteredItems} = action.payload;

			const viewColumn: TObjectViewColumn[] = [];

			filteredItems.map((field: TObjectField, index: number) => {
				if (field.checked === true) {
					viewColumn.push({
						label: field.label[defaultLanguageId],
						objectFieldName: field.name,
						priority: index,
					});
				}
			});

			const newViewColumn = viewColumn.map((viewColumn, index) => {
				return {
					...viewColumn,
					priority: index,
				};
			});

			const newObjectView = {
				...state.objectView,
				objectViewColumns: newViewColumn,
			};

			return {
				...state,
				objectFields: filteredItems,
				objectView: newObjectView,
			};
		}
		case TYPES.ADD_OBJECT_FIELDS: {
			const {objectFields, objectView} = action.payload;

			const {objectViewColumns} = objectView;

			const objectFieldsWithCheck = objectFields.map(
				(field: TObjectField) => {
					return {
						...field,
						checked: false,
						filtered: true,
					};
				}
			);

			const newObjectFields: TObjectField[] = [];

			METADATAS.map((field) => {
				newObjectFields.push(field);
			});

			objectFieldsWithCheck.map((field: TObjectField) => {
				newObjectFields.push(field);
			});

			newObjectFields.forEach((field) => {
				objectViewColumns.forEach(
					(column: {objectFieldName: string}) => {
						if (
							column.objectFieldName ===
							field.label[defaultLanguageId]
						) {
							field.checked = true;
						}
					}
				);
			});

			const newObjectViewColumns: TObjectViewColumn[] = [];

			objectViewColumns.forEach((viewColumn: TObjectViewColumn) => {
				newObjectFields.forEach((objectField: TObjectField) => {
					if (objectField.name === viewColumn.objectFieldName) {
						newObjectViewColumns.push({
							...viewColumn,
							label: objectField.label[defaultLanguageId],
						});
					}
				});
			});

			const newObjectView = {
				...objectView,
				objectViewColumns: newObjectViewColumns,
			};

			return {
				...state,
				objectFields: newObjectFields,
				objectView: newObjectView,
			};
		}
		case TYPES.CHANGE_OBJECT_VIEW_NAME: {
			const {newName} = action.payload;

			const newObjectView = {
				...state.objectView,
				name: {
					[defaultLanguageId]: newName,
				},
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.CHANGE_OBJECT_VIEW_COLUMN_ORDER: {
			const {draggedIndex, targetIndex} = action.payload;

			const newState = {...state};

			const viewColumns = newState.objectView.objectViewColumns;

			const dragged = viewColumns[draggedIndex];

			viewColumns.splice(draggedIndex, 1);
			viewColumns.splice(targetIndex, 0, dragged);

			const newViewColumn = viewColumns.map((viewColumn, index) => {
				return {
					...viewColumn,
					priority: index,
				};
			});

			const newObjectView = {
				...state.objectView,
				objectViewColumns: newViewColumn,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.DELETE_OBJECT_VIEW_COLUMN: {
			const {objectFieldName} = action.payload;

			const newState = {...state};

			const viewColumn = newState.objectView?.objectViewColumns.filter(
				(viewColumn) => viewColumn.objectFieldName !== objectFieldName
			);

			const newViewColumn = viewColumn.map((viewColumn, index) => {
				return {
					...viewColumn,
					priority: index,
				};
			});

			const newObjectView = {
				...state.objectView,
				objectViewColumns: newViewColumn,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.SET_OBJECT_VIEW_AS_DEFAULT: {
			const {checked} = action.payload;

			const newObjectView = {
				...state.objectView,
				defaultObjectView: checked,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		default:
			return state;
	}
};

interface IViewContextProviderProps extends React.HTMLAttributes<HTMLElement> {
	value: {
		isViewOnly: boolean;
		objectViewId: string;
	};
}

export function ViewContextProvider({
	children,
	value,
}: IViewContextProviderProps) {
	const [state, dispatch] = useReducer<React.Reducer<TState, TAction>>(
		viewReducer,
		{
			...initialState,
			...value,
		}
	);

	return (
		<ViewContext.Provider value={[state, dispatch]}>
			{children}
		</ViewContext.Provider>
	);
}

export default ViewContext;
