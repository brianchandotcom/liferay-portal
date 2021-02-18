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

import {DataLayoutBuilderActions} from 'data-engine-taglib';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

/**
 * Check if contains field inside the FormBuilder
 * @param {Object} dataLayoutBuilder
 * @param {Object} state
 */
export function containsFieldInsideFormBuilder(dataLayoutBuilder, {fieldName}) {
	const {pages} = dataLayoutBuilder.getStore();
	const visitor = new PagesVisitor(pages);

	return visitor.containsField(fieldName);
}

/**
 * Get data definition field
 * @param {Object} state
 */
export function getDataDefinitionField({dataDefinitionFields, fieldName}) {
	return dataDefinitionFields.find(({name}) => name === fieldName);
}

/**
 * Get data layout field
 * @param {Object} state
 */
export function getDataLayoutField({dataLayoutFields, fieldName}) {
	return dataLayoutFields[fieldName];
}

/**
 * Return the formatted state
 * @param {object} state
 */
export function getFormattedState({
	dataDefinition: {dataDefinitionFields, defaultLanguageId},
	dataLayout: {dataLayoutFields},
	editingLanguageId,
	focusedCustomObjectField: {name: focusedCustomObjectFieldName},
	focusedField: {fieldName},
}) {
	return {
		dataDefinitionFields,
		dataLayoutFields,
		defaultLanguageId,
		editingLanguageId,
		fieldName: fieldName || focusedCustomObjectFieldName,
	};
}

/**
 * Set propertyValue at object view level
 * @param {string} propertyName
 * @param {any} propertyValue
 */
export function setPropertyAtObjectViewLevel(propertyName, propertyValue) {
	return ({dataDefinitionFields, fieldName}, dispatch) => {
		dispatch({
			payload: {
				dataDefinitionFields: dataDefinitionFields.map((field) => {
					if (field.name === fieldName) {
						return {
							...field,
							[propertyName]: propertyValue,
						};
					}

					return field;
				}),
			},
			type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION_FIELDS,
		});
	};
}

/**
 * Set propertyValue at form view level
 * @param {string} propertyName
 * @param {any} propertyValue
 */
export function setPropertyAtFormViewLevel(propertyName, propertyValue) {
	return ({dataLayoutFields, fieldName}, dispatch) => {
		dispatch({
			payload: {
				dataLayoutFields: {
					...dataLayoutFields,
					[fieldName]: {
						...dataLayoutFields[fieldName],
						[propertyName]: propertyValue,
					},
				},
			},
			type: DataLayoutBuilderActions.UPDATE_DATA_LAYOUT_FIELDS,
		});
	};
}
