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

import {defaultLanguageId} from '../../utils/locale';
import {
	TAction,
	TLabelValueObject,
	TName,
	TObjectField,
	TObjectView,
	TObjectViewColumn,
	TObjectViewFilterColumn,
	TObjectViewSortColumn,
	TState,
	TWorkflowStatus,
} from './types';
interface IViewContextProps extends Array<TState | Function> {
	0: typeof initialState;
	1: React.Dispatch<React.ReducerAction<React.Reducer<TState, TAction>>>;
}

interface TInitialFilterColumn extends TObjectViewFilterColumn {
	json: string;
	valueSummary: string;
}

const ViewContext = createContext({} as IViewContextProps);

export const METADATA = [
	{
		businessType: 'Author',
		checked: false,
		filtered: true,
		id: 1,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {[defaultLanguageId]: Liferay.Language.get('author')},
		listTypeDefinitionId: true,
		name: 'creator',
		required: false,
		type: 'metadata',
	},
	{
		businessType: 'Creation Date',
		checked: false,
		filtered: true,
		id: 2,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {[defaultLanguageId]: Liferay.Language.get('creation-date')},
		listTypeDefinitionId: true,
		name: 'dateCreated',
		required: false,
		type: 'metadata',
	},
	{
		businessType: 'Modified Date',
		checked: false,
		filtered: true,
		id: 3,
		indexed: true,
		indexedAsKeyword: true,
		indexedLanguageId: '',
		label: {[defaultLanguageId]: Liferay.Language.get('modified-date')},
		listTypeDefinitionId: true,
		name: 'dateModified',
		required: false,
		type: 'metadata',
	},
	{
		businessType: 'Workflow Status',
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
		name: 'status',
		required: false,
		type: 'metadata',
	},
	{
		businessType: 'Id',
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
	ADD_OBJECT_VIEW_SORT_COLUMN = 'ADD_OBJECT_VIEW_SORT_COLUMN',
	ADD_OBJECT_VIEW_FILTER_COLUMN = 'ADD_OBJECT_VIEW_FILTER_COLUMN',
	CHANGE_OBJECT_VIEW_NAME = 'CHANGE_OBJECT_VIEW_NAME',
	CHANGE_OBJECT_VIEW_COLUMN_ORDER = 'CHANGE_OBJECT_VIEW_COLUMN_ORDER',
	CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER = 'CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER',
	DELETE_OBJECT_VIEW_COLUMN = 'DELETE_OBJECT_VIEW_COLUMN',
	DELETE_OBJECT_VIEW_SORT_COLUMN = 'DELETE_OBJECT_VIEW_SORT_COLUMN',
	DELETE_OBJECT_VIEW_FILTER_COLUMN = 'DELETE_OBJECT_VIEW_FILTER_COLUMN',
	DELETE_OBJECT_CUSTOM_VIEW_FIELD = 'DELETE_OBJECT_CUSTOM_VIEW_FIELD',
	EDIT_OBJECT_VIEW_COLUMN_LABEL = 'EDIT_OBJECT_VIEW_COLUMN_LABEL',
	EDIT_OBJECT_VIEW_FILTER_COLUMN = 'EDIT_OBJECT_VIEW_FILTER_COLUMN',
	EDIT_OBJECT_VIEW_SORT_COLUMN_SORT_ORDER = 'EDIT_OBJECT_VIEW_SORT_COLUMN_SORT_ORDER',
	SET_OBJECT_VIEW_AS_DEFAULT = 'SET_OBJECT_VIEW_AS_DEFAULT',
}

const initialState = {
	objectFields: [] as TObjectField[],
	objectView: {} as TObjectView,
} as TState;

const handleChangeColumnOrder = (
	draggedIndex: number,
	targetIndex: number,
	columns: TObjectViewSortColumn[]
) => {
	const dragged = columns[draggedIndex];

	columns.splice(draggedIndex, 1);
	columns.splice(targetIndex, 0, dragged);

	const newColumn = columns.map((sortColumn, index) => {
		return {
			...sortColumn,
			priority: index,
		};
	});

	return newColumn;
};

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
			const {checkedItems, filteredItems} = action.payload;

			const {objectView} = state;

			const newObjectViewColumns = checkedItems.map(
				(viewColumn: TObjectViewColumn, index: number) => {
					return {
						...viewColumn,
						priority: index,
					};
				}
			);

			const newObjectView = {
				...objectView,
				objectViewColumns: newObjectViewColumns,
			};

			return {
				...state,
				objectFields: filteredItems,
				objectView: newObjectView,
			};
		}
		case TYPES.ADD_OBJECT_VIEW_FILTER_COLUMN: {
			const {filterType, objectFieldName, valueList} = action.payload;

			const labels: TName[] = [];
			let objectFieldBusinessType;
			const {objectFields} = state;

			objectFields.forEach((objectField: TObjectField) => {
				if (objectField.name === objectFieldName) {
					labels.push(objectField.label);
					objectField.hasFilter = true;
					objectFieldBusinessType = objectField.businessType;
				}
			});

			const [label] = labels;

			let filterTypeValue = filterType || null;

			if (valueList.length === 0) {
				filterTypeValue = null;
			}

			const newFilterColumnItem: TObjectViewFilterColumn = {
				definition: filterTypeValue && {
					[filterTypeValue]: valueList.map(
						(item: {label: string; value: string}) => item.value
					),
				},
				fieldLabel: label[defaultLanguageId],
				filterBy: label[defaultLanguageId],
				filterType: filterTypeValue,
				label,
				objectFieldBusinessType,
				objectFieldName,
				valueList: filterTypeValue ? valueList : [],
			};

			const objectView = {...state.objectView};

			let newObjectView;

			const {objectViewFilterColumns} = state.objectView;

			if (!objectViewFilterColumns) {
				const filterColumns: TObjectViewFilterColumn[] = [];

				filterColumns.push(newFilterColumnItem);

				newObjectView = {
					...objectView,
					objectViewFilterColumns: filterColumns,
				};
			}
			else {
				objectViewFilterColumns.push(newFilterColumnItem);

				newObjectView = {
					...objectView,
					objectViewFilterColumns,
				};
			}

			return {
				...state,
				objectFields,
				objectView: newObjectView,
			};
		}
		case TYPES.ADD_OBJECT_VIEW_SORT_COLUMN: {
			const {
				objectFieldName,
				objectFields,
				objectViewSortColumns,
				selectedObjetSort,
			} = action.payload;

			const objectView = {...state.objectView};
			const objectViewColumns = objectView.objectViewColumns;

			objectViewColumns.forEach((viewColumn) => {
				if (viewColumn.objectFieldName === objectFieldName) {
					viewColumn.defaultSort = true;
				}
			});

			const labels: TName[] = [];
			objectFields.forEach((objectField: TObjectField) => {
				if (objectField.name === objectFieldName) {
					labels.push(objectField.label);
				}
			});
			const [label] = labels;

			const newSortColumnItem: TObjectViewSortColumn = {
				fieldLabel: label[defaultLanguageId],
				label,
				objectFieldName,
				sortOrder: selectedObjetSort.value,
			};

			if (!objectViewSortColumns) {
				const sortColumn: TObjectViewSortColumn[] = [];

				sortColumn.push(newSortColumnItem);

				const newSortColumn = sortColumn.map((sortColumn, index) => {
					return {
						...sortColumn,
						priority: index,
					};
				});

				const newObjectView = {
					...objectView,
					objectViewSortColumns: newSortColumn,
				};

				return {
					...state,
					objectView: newObjectView,
				};
			}

			objectViewSortColumns.push(newSortColumnItem);

			const newSortColumn = objectViewSortColumns.map(
				(sortColumn: TObjectViewSortColumn, index: number) => {
					return {
						...sortColumn,
						priority: index,
					};
				}
			);

			const newObjectView = {
				...objectView,
				objectViewSortColumns: newSortColumn,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.ADD_OBJECT_FIELDS: {
			const {objectFields, objectView} = action.payload;

			const {
				objectViewColumns,
				objectViewFilterColumns,
				objectViewSortColumns,
			} = objectView;

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

			if (!Liferay.FeatureFlags['LPS-154872']) {
				METADATA.map((field) => {
					newObjectFields.push(field);
				});
			}

			objectFieldsWithCheck.map((field: TObjectField) => {
				newObjectFields.push(field);
			});

			newObjectFields.forEach((field) => {
				objectViewColumns.forEach(
					(column: {objectFieldName: string}) => {
						if (column.objectFieldName === field.name) {
							field.checked = true;
						}
					}
				);

				const existingFilter = objectViewFilterColumns.find(
					(filter: {objectFieldName: string}) => {
						if (filter.objectFieldName === field.name) {
							return filter;
						}
					}
				);

				field.hasFilter = existingFilter;
			});

			const newObjectViewColumns: TObjectViewColumn[] = [];
			const newObjectViewSortColumns: TObjectViewSortColumn[] = [];

			objectViewColumns.forEach((viewColumn: TObjectViewColumn) => {
				newObjectFields.forEach((objectField: TObjectField) => {
					if (objectField.name === viewColumn.objectFieldName) {
						newObjectViewColumns.push({
							...viewColumn,
							defaultSort: false,
							fieldLabel: objectField.label[defaultLanguageId],
							label: viewColumn.label,
						});
					}
				});
			});

			objectViewSortColumns.forEach((sortColumn: TObjectViewColumn) => {
				newObjectFields.forEach((objectField: TObjectField) => {
					if (objectField.name === sortColumn.objectFieldName) {
						newObjectViewSortColumns.push({
							...sortColumn,
							fieldLabel: objectField.label[defaultLanguageId],
						});
					}
				});
			});

			newObjectViewSortColumns.forEach(
				(sortColumn: TObjectViewSortColumn) => {
					newObjectViewColumns.forEach(
						(viewColumn: TObjectViewColumn) => {
							if (
								sortColumn.objectFieldName ===
								viewColumn.objectFieldName
							) {
								viewColumn.defaultSort = true;
							}
						}
					);
				}
			);

			const newObjectViewFilterColumns = objectViewFilterColumns.map(
				(filterColumn: TInitialFilterColumn) => {
					const definition =
						filterColumn.json && JSON.parse(filterColumn.json);
					const filterType = filterColumn.filterType;
					const objectFieldName = filterColumn.objectFieldName;
					const objectField = newObjectFields.find(
						(field: TObjectField) => {
							if (field.name === objectFieldName) {
								return field;
							}
						}
					);
					const valueList = [];
					let valueSummary = filterColumn.valueSummary?.split(',');

					valueSummary = valueSummary?.map((item) => item.trim());

					if (valueSummary && filterType) {
						for (
							let i = 0;
							i < definition[filterType].length;
							i++
						) {
							valueList.push({
								label: valueSummary[i],
								value: definition[filterType][i],
							});
						}
					}

					return {
						...filterColumn,
						definition,
						fieldLabel: objectField?.label[defaultLanguageId],
						filterBy: objectFieldName,
						filterType,
						objectFieldBusinessType: objectField?.businessType,
						valueList,
					};
				}
			);

			const newObjectView = {
				...objectView,
				objectViewColumns: newObjectViewColumns,
				objectViewFilterColumns: newObjectViewFilterColumns,
				objectViewSortColumns: newObjectViewSortColumns,
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

			const newViewColumn = handleChangeColumnOrder(
				draggedIndex,
				targetIndex,
				viewColumns
			);

			const newObjectView = {
				...state.objectView,
				objectViewColumns: newViewColumn,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER: {
			const {draggedIndex, targetIndex} = action.payload;

			const newState = {...state};

			const sortColumns = newState.objectView.objectViewSortColumns;

			const newSortColumn = handleChangeColumnOrder(
				draggedIndex,
				targetIndex,
				sortColumns
			);

			const newObjectView = {
				...state.objectView,
				objectViewSortColumns: newSortColumn,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.DELETE_OBJECT_VIEW_COLUMN: {
			const {objectFieldName} = action.payload;

			const newState = {...state};

			const objectFields = newState.objectFields;

			const viewColumn = newState.objectView?.objectViewColumns.filter(
				(viewColumn) => viewColumn.objectFieldName !== objectFieldName
			);

			const newViewColumn = viewColumn.map((viewColumn, index) => {
				return {
					...viewColumn,
					priority: index,
				};
			});

			objectFields.forEach((field) => {
				if (objectFieldName === field.name) {
					field.checked = false;
				}
			});

			const newObjectView = {
				...state.objectView,
				objectViewColumns: newViewColumn,
			};

			return {
				...state,
				objectFields,
				objectView: newObjectView,
			};
		}
		case TYPES.DELETE_OBJECT_VIEW_FILTER_COLUMN: {
			const {objectFieldName} = action.payload;

			const {objectViewFilterColumns} = state.objectView;
			const {objectFields} = state;

			objectFields.forEach((objectField) => {
				if (objectField.name === objectFieldName) {
					objectField.hasFilter = false;
				}
			});

			const filterColumns = objectViewFilterColumns.filter(
				(filterColumn) =>
					filterColumn.objectFieldName !== objectFieldName
			);

			const newObjectView = {
				...state.objectView,
				objectViewFilterColumns: filterColumns,
			};

			return {
				...state,
				objectFields,
				objectView: newObjectView,
			};
		}
		case TYPES.DELETE_OBJECT_VIEW_SORT_COLUMN: {
			const {objectFieldName} = action.payload;

			const newState = {...state};

			const objectViewColumns = newState.objectView.objectViewColumns;

			objectViewColumns.forEach((viewColumn) => {
				if (viewColumn.objectFieldName === objectFieldName) {
					viewColumn.defaultSort = false;
				}
			});

			const sortColumn = newState.objectView?.objectViewSortColumns.filter(
				(sortColumn) => sortColumn.objectFieldName !== objectFieldName
			);

			const newSortColumn = sortColumn.map((sortColumn, index) => {
				return {
					...sortColumn,
					priority: index,
				};
			});

			const newObjectView = {
				...state.objectView,
				objectViewColumns,
				objectViewSortColumns: newSortColumn,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.EDIT_OBJECT_VIEW_COLUMN_LABEL: {
			const {editingObjectFieldName, translations} = action.payload;

			const {objectViewColumns} = state.objectView;

			const newObjectViewColumns = objectViewColumns.map((viewColumn) => {
				if (viewColumn.objectFieldName === editingObjectFieldName) {
					return {
						...viewColumn,
						label: translations,
					};
				}

				return viewColumn;
			});

			const newObjectView = {
				...state.objectView,
				objectViewColumns: newObjectViewColumns,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.EDIT_OBJECT_VIEW_FILTER_COLUMN: {
			const {filterType, objectFieldName, valueList} = action.payload;

			const {objectViewFilterColumns} = state.objectView;

			let filterTypeValue = filterType || null;

			if (valueList.length === 0) {
				filterTypeValue = null;
			}

			const newObjectFilterColumns = objectViewFilterColumns.map(
				(filterColumn) => {
					if (filterColumn.objectFieldName === objectFieldName) {
						return {
							...filterColumn,
							definition: filterTypeValue && {
								[filterTypeValue]: valueList.map(
									(item: TLabelValueObject) => item.value
								),
							},
							filterType: filterTypeValue,
							valueList: filterTypeValue ? valueList : [],
						};
					}
					else {
						return filterColumn;
					}
				}
			);

			const newObjectView = {
				...state.objectView,
				objectViewFilterColumns: newObjectFilterColumns,
			};

			return {
				...state,
				objectView: newObjectView,
			};
		}
		case TYPES.EDIT_OBJECT_VIEW_SORT_COLUMN_SORT_ORDER: {
			const {editingObjectFieldName, selectedObjectSort} = action.payload;

			const objectView = {...state.objectView};

			const objectViewSortColumns = objectView.objectViewSortColumns;

			const newObjectViewSortColumns = objectViewSortColumns.map(
				(sortColumn) => {
					if (sortColumn.objectFieldName === editingObjectFieldName) {
						return {
							...sortColumn,
							sortOrder: selectedObjectSort,
						};
					}
					else {
						return sortColumn;
					}
				}
			);

			const newObjectView = {
				...objectView,
				objectViewSortColumns: newObjectViewSortColumns,
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
		workflowStatusJSONArray: TWorkflowStatus[];
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
