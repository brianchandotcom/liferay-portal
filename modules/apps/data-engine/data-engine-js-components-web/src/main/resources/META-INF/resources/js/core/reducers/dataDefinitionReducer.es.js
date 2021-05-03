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

import {DataConverter} from 'data-engine-taglib';
import {FieldSupport} from 'dynamic-data-mapping-form-builder';

import {PagesVisitor} from '../../utils/visitors.es';
import {EVENT_TYPES} from '../actions/eventTypes.es';

export default (state, action, config) => {
	switch (action.type) {
		case EVENT_TYPES.DATA_DEFINITION.ADD: {
			const {type} = action.payload;

			const {
				dataDefinition,
				defaultLanguageId,
				editingLanguageId,
				pages,
			} = state;

			const {
				fieldTypes,
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			const fieldType = fieldTypes.find(({name}) => name === type);

			const field = FieldSupport.createField(
				{defaultLanguageId, editingLanguageId, fieldNameGenerator},
				{fieldType}
			);

			const dataDefinitionField = DataConverter.getDataDefinitionField(
				field
			);

			return {
				dataDefinition: {
					...dataDefinition,
					dataDefinitionFields: [
						...dataDefinition.dataDefinitionFields,
						dataDefinitionField,
					],
				},
				focusedField: {
					...dataDefinitionField,
					settingsContext: field.settingsContext,
				},
			};
		}
		case EVENT_TYPES.DATA_DEFINITION.CHANGE: {
			const {
				dataDefinition,
				defaultLanguageId,
				editingLanguageId,
				focusedField,
			} = state;
			const {propertyName, propertyValue} = action.payload;

			const visitor = new PagesVisitor(
				focusedField.settingsContext.pages
			);

			const settingsContext = {
				...focusedField.settingsContext,
				pages: visitor.mapFields((field) => {
					const {fieldName} = field;

					if (fieldName === propertyName) {
						const localizedValue = {
							...field.localizedValue,
							[editingLanguageId ||
							defaultLanguageId]: propertyValue,
						};

						return {
							...field,
							localizedValue,
							value: propertyValue,
						};
					}

					return field;
				}),
			};

			const dataDefinitionField = {
				...DataConverter.getDataDefinitionField({
					nestedFields: focusedField.nestedFields,
					settingsContext,
				}),
				settingsContext,
			};

			return {
				dataDefinition: {
					...dataDefinition,
					dataDefinitionFields: dataDefinition.dataDefinitionFields.map(
						(field) => {
							if (field.name === dataDefinitionField.name) {
								return dataDefinitionField;
							}

							return field;
						}
					),
				},
				focusedField: dataDefinitionField,
			};
		}
		case EVENT_TYPES.DATA_DEFINITION.DELETE: {
			const {fieldName} = action.payload;
			const {dataDefinition} = state;

			return {
				dataDefinition: {
					...dataDefinition,
					dataDefinitionFields: dataDefinition.dataDefinitionFields.filter(
						(field) => field.name !== fieldName
					),
				},
			};
		}
		default:
			return state;
	}
};
