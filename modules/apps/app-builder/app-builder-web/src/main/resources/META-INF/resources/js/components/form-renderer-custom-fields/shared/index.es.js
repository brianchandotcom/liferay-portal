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
 * Check if exists any value inside dataLayoutFields
 * @param {object} state
 * @returns {boolean} Returns a boolean true, if Label is at FormViewLevel
 */

export function isLabelAtFormViewLevel({dataLayoutFields, fieldName}) {
	const dataLayoutField = dataLayoutFields[fieldName];

	return !!Object.values(dataLayoutField?.label || {}).filter(Boolean).length;
}

/**
 * Set propertyValue at object view level
 * @param {any} propertyValue
 */

export function setPropertyAtObjectViewLevel(propertyValue) {
	return ({dataDefinitionFields, fieldName, propertyName}, dispatch) => {
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
 * @param {any} propertyValue
 */

export function setPropertyAtFormViewLevel(propertyValue) {
	return ({dataLayoutFields, fieldName, propertyName}, dispatch) => {
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
