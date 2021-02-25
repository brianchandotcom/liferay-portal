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
 * Return the formatted state
 * @param {object} state
 */
export function getFormattedState({
	dataDefinition: {dataDefinitionFields, defaultLanguageId},
	dataLayout: {dataLayoutFields},
	editingLanguageId,
	focusedCustomObjectField: {name: focusedCustomObjectFieldName},
	focusedField: {fieldName: focusedFieldName},
}) {
	const fieldName = focusedFieldName || focusedCustomObjectFieldName;

	const dataDefinitionField = dataDefinitionFields.find(
		({name}) => name === fieldName
	);

	const dataLayoutField = dataLayoutFields[fieldName];

	return {
		dataDefinitionField,
		dataDefinitionFields,
		dataLayoutField: {
			...dataLayoutField,
			label: Object.values(dataLayoutField?.label || {}).length
				? dataLayoutField.label
				: dataDefinitionField.label,
		},
		dataLayoutFields,
		defaultLanguageId,
		editingLanguageId,
		fieldName,
	};
}

/**
 * Set propertyValue at object view level
 * @param {string} propertyName
 * @param {any} propertyValue
 */
export function setPropertyAtStructureLevel(propertyName, propertyValue) {
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
export function setPropertyAtViewLevel(propertyName, propertyValue) {
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
