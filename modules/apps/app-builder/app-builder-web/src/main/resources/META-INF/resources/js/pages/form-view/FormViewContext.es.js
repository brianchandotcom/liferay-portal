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
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';
import {containsField} from '../../utils/dataLayoutVisitor.es';
import {
	UPDATE_DATA_DEFINITION,
	UPDATE_DATA_LAYOUT,
	UPDATE_DATA_LAYOUT_NAME,
	UPDATE_FIELD_TYPES,
	UPDATE_IDS,
	UPDATE_PAGES
} from './actions.es';

const FormViewContext = createContext();

const initialState = {
	dataDefinition: {
		dataDefinitionFields: [],
		name: {}
	},
	dataDefinitionId: 0,
	dataLayout: {
		dataLayoutPages: [],
		name: {}
	},
	dataLayoutId: 0,
	fieldTypes: []
};

const setDataDefinitionFields = (
	dataLayoutBuilder,
	dataDefinition,
	dataLayout
) => {
	const {dataDefinitionFields} = dataDefinition;
	const {dataLayoutPages} = dataLayout;

	const {pages} = dataLayoutBuilder.getStore();
	const visitor = new PagesVisitor(pages);

	const newFields = [];

	visitor.mapFields(field => {
		const definitionField = dataLayoutBuilder.getDefinitionField(field);

		newFields.push(definitionField);
	});

	return newFields.concat(
		dataDefinitionFields.filter(
			field => !containsField(dataLayoutPages, field.name)
		)
	);
};

const setDataLayout = dataLayoutBuilder => {
	const {pages} = dataLayoutBuilder.getStore();
	const {layout} = dataLayoutBuilder.getDefinitionAndLayout(pages);

	return layout;
};

const createReducer = dataLayoutBuilder => {
	return (state = initialState, action) => {
		switch (action.type) {
			case UPDATE_DATA_DEFINITION: {
				const {dataDefinition} = action.payload;

				return {
					...state,
					dataDefinition: {
						...state.dataDefinition,
						...dataDefinition
					}
				};
			}
			case UPDATE_DATA_LAYOUT: {
				const {dataLayout} = action.payload;

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						...dataLayout
					}
				};
			}
			case UPDATE_DATA_LAYOUT_NAME: {
				const {name} = action.payload;

				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						name
					}
				};
			}
			case UPDATE_FIELD_TYPES: {
				const {fieldTypes} = action.payload;

				return {
					...state,
					fieldTypes
				};
			}
			case UPDATE_IDS: {
				const {dataDefinitionId, dataLayoutId} = action.payload;

				return {
					...state,
					dataDefinitionId,
					dataLayoutId
				};
			}
			case UPDATE_PAGES: {
				const {dataDefinition, dataLayout} = state;

				return {
					...state,
					dataDefinition: {
						...dataDefinition,
						dataDefinitionFields: setDataDefinitionFields(
							dataLayoutBuilder,
							dataDefinition,
							dataLayout
						)
					},
					dataLayout: {
						...dataLayout,
						...setDataLayout(dataLayoutBuilder)
					}
				};
			}
			default:
				return state;
		}
	};
};

export default FormViewContext;

export {initialState, createReducer};
