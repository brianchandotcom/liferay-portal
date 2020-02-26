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

import {createContext} from 'react';

export const ADD_DATA_LIST_VIEW_FIELD = 'ADD_DATA_LIST_VIEW_FIELD';
export const REMOVE_DATA_LIST_VIEW_FIELD = 'REMOVE_DATA_LIST_VIEW_FIELD';
export const REMOVE_FILTER_VALUE = 'REMOVE_FILTER_VALUE';
export const UPDATE_DATA_DEFINITION = 'UPDATE_DATA_DEFINITION';
export const UPDATE_DATA_LIST_VIEW = 'UPDATE_DATA_LIST_VIEW';
export const UPDATE_DATA_LIST_VIEW_NAME = 'UPDATE_DATA_LIST_VIEW_NAME';
export const UPDATE_FIELD_TYPES = 'UPDATE_FIELD_TYPES';
export const UPDATE_FILTER_VALUE = 'UPDATE_FILTER_VALUE';
export const UPDATE_FOCUSED_COLUMN = 'UPDATE_FOCUSED_COLUMN';

export const initialState = {
	dataDefinition: {
		dataDefinitionFields: [],
	},
	dataListView: {
		appliedFilters: {},
		fieldNames: [],
		name: {
			en_US: '',
		},
	},
	fieldTypes: [],
	focusedColumn: null,
};

export const reducer = (state = initialState, action) => {
	switch (action.type) {
		case ADD_DATA_LIST_VIEW_FIELD: {
			const {dataListView} = state;
			const {fieldName, index} = action.payload;
			const {fieldNames} = dataListView;

			return {
				...state,
				dataListView: {
					...dataListView,
					fieldNames: fieldNames
						? [
								...fieldNames.slice(0, index),
								fieldName,
								...fieldNames.slice(index),
						  ]
						: [fieldName],
				},
			};
		}
		case REMOVE_DATA_LIST_VIEW_FIELD: {
			const {dataListView} = state;
			const {fieldName} = action.payload;

			return {
				...state,
				dataListView: {
					...dataListView,
					fieldNames: dataListView.fieldNames.filter(
						name => name != fieldName
					),
				},
				focusedColumn: null,
			};
		}
		case REMOVE_FILTER_VALUE: {
			const {fieldName} = action.payload;
			const {dataListView} = state;
			const appliedFilters = {...dataListView.appliedFilters};

			delete appliedFilters[fieldName];

			return {
				...state,
				dataListView: {
					...dataListView,
					appliedFilters,
				},
			};
		}
		case UPDATE_DATA_DEFINITION: {
			const {dataDefinition} = action.payload;

			return {
				...state,
				dataDefinition,
			};
		}
		case UPDATE_DATA_LIST_VIEW: {
			const {dataListView} = action.payload;

			return {
				...state,
				dataListView,
			};
		}
		case UPDATE_DATA_LIST_VIEW_NAME: {
			const {name} = action.payload;

			return {
				...state,
				dataListView: {
					...state.dataListView,
					name: {
						[themeDisplay.getLanguageId()]: name,
					},
				},
			};
		}
		case UPDATE_FIELD_TYPES: {
			const {fieldTypes} = action.payload;

			return {
				...state,
				fieldTypes,
			};
		}
		case UPDATE_FILTER_VALUE: {
			const {fieldName, value} = action.payload;

			return {
				...state,
				dataListView: {
					...state.dataListView,
					appliedFilters: {
						...state.dataListView.appliedFilters,
						[fieldName]: value,
					},
				},
			};
		}
		case UPDATE_FOCUSED_COLUMN: {
			const {fieldName} = action.payload;

			return {
				...state,
				focusedColumn: fieldName,
			};
		}
		default: {
			return state;
		}
	}
};

export default createContext();
