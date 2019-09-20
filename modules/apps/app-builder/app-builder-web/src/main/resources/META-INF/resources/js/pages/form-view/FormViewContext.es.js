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

const FormViewContext = createContext();

const actions = {
	UPDATE_DATA_DEFINITION: 'UPDATE_DATA_DEFINITION',
	UPDATE_DATA_LAYOUT: 'UPDATE_DATA_LAYOUT',
	UPDATE_DATA_LAYOUT_NAME: 'UPDATE_DATA_LAYOUT_NAME',
	UPDATE_FIELD_TYPES: 'UPDATE_FIELD_TYPES',
	UPDATE_IDS: 'UPDATE_IDS',
	UPDATE_PAGES: 'UPDATE_PAGES'
};

const initialState = {
	dataDefinition: {
		dataDefinitionFields: []
	},
	dataDefinitionId: 0,
	dataLayout: {
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
			case actions.UPDATE_DATA_DEFINITION:
				return {
					...state,
					dataDefinition: {
						...state.dataDefinition,
						...action.dataDefinition
					}
				};
			case actions.UPDATE_DATA_LAYOUT:
				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						...action.dataLayout
					}
				};
			case actions.UPDATE_DATA_LAYOUT_NAME:
				return {
					...state,
					dataLayout: {
						...state.dataLayout,
						name: action.name
					}
				};
			case actions.UPDATE_FIELD_TYPES:
				return {
					...state,
					fieldTypes: action.fieldTypes
				};
			case actions.UPDATE_IDS: {
				const {dataDefinitionId, dataLayoutId} = action;

				return {
					...state,
					dataDefinitionId,
					dataLayoutId
				};
			}
			case actions.UPDATE_PAGES: {
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

export {actions, initialState, createReducer};
