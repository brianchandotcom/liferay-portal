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

import {PagesVisitor} from 'data-engine-js-components-web';
import {DataLayoutBuilderActions} from 'data-engine-taglib';

/**
 * Check if contains field inside the FormBuilder
 * @param {object} dataLayoutBuilder
 * @param {object} state
 */
export function containsFieldInsideFormBuilder(dataLayoutBuilder, {fieldName}) {
	const {
		pages,
	} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider.state;
	const visitor = new PagesVisitor(pages);

	return visitor.containsField(fieldName);
}

function getFieldName({focusedCustomObjectField, focusedField}) {
	const {name: focusedCustomObjectFieldName} = focusedCustomObjectField;
	const {fieldName: focusedFieldName} = focusedField;

	return focusedFieldName || focusedCustomObjectFieldName;
}

/**
 * Get data definition field
 * @param {object} state
 */
export function getDataDefinitionField({
	dataDefinition: {dataDefinitionFields},
	...state
}) {
	const fieldName = getFieldName(state);

	return dataDefinitionFields.find(({name}) => name === fieldName);
}

/**
 * Return the formatted state
 * @param {object} state
 */
export function getFormattedState(state) {
	const {
		dataDefinition: {
			availableLanguageIds,
			dataDefinitionFields,
			defaultLanguageId,
		},
		dataLayout: {dataLayoutFields},
		editingLanguageId,
	} = state;

	const fieldName = getFieldName(state);
	const dataDefinitionField = getDataDefinitionField(state);

	const getLabel = (dataLayoutField) => {
		if (Object.values(dataLayoutField?.label || {}).length) {
			return dataLayoutField.label;
		}

		return dataDefinitionField.label;
	};

	return {
		availableLanguageIds,
		dataDefinitionField,
		dataDefinitionFields,
		dataLayoutField: {
			...dataLayoutFields[fieldName],
			label: getLabel(dataLayoutFields[fieldName]),
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
