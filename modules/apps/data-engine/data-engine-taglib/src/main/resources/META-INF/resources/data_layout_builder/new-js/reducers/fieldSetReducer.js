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

import {
	FieldSetUtil,
	FieldSupport,
	SettingsContext,
} from 'dynamic-data-mapping-form-builder';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {EVENT_TYPES} from '../eventTypes';

export default (state, action, config) => {
	switch (action.type) {
		case EVENT_TYPES.FIELD_SET.UPDATE: {
			const {dataDefinitionId, editingDataDefinitionId} = state;
			const fieldSets = action.payload.fieldSets.filter(
				(fieldSet) =>
					fieldSet.id !== dataDefinitionId &&
					fieldSet.id !== editingDataDefinitionId
			);

			return {
				...state,
				fieldSets,
			};
		}
		case EVENT_TYPES.FIELD_SET.ADD: {
			const {
				fieldSet,
				indexes,
				parentFieldName,
				properties,
				rows,
				useFieldName,
			} = action.payload;
			const {
				availableLanguageIds,
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

			const visitor = new PagesVisitor(fieldSet.pages);
			const nestedFields = [];

			const props = {
				availableLanguageIds,
				defaultLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
			};

			visitor.mapFields((nestedField) => {
				nestedFields.push(
					SettingsContext.updateField(
						props,
						nestedField,
						'label',
						nestedField.label
					)
				);
			});

			let fieldSetField = FieldSetUtil.createFieldSet(
				{editingLanguageId, fieldTypes, ...props},
				{skipFieldNameGeneration: false, useFieldName},
				nestedFields
			);

			if (properties) {
				Object.keys(properties).forEach((key) => {
					fieldSetField = SettingsContext.updateField(
						props,
						fieldSetField,
						key,
						properties[key]
					);
				});
			}

			if (fieldSet.id) {
				fieldSetField = SettingsContext.updateField(
					props,
					fieldSetField,
					'ddmStructureId',
					fieldSet.id
				);
			}

			if (rows && rows.length) {
				fieldSetField = SettingsContext.updateField(
					props,
					fieldSetField,
					'rows',
					rows
				);
			}

			const newField = SettingsContext.updateField(
				props,
				fieldSetField,
				'label',
				fieldSet.localizedTitle
			);

			return FieldSupport.addField({
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
				indexes,
				newField,
				pages,
				parentFieldName,
			});
		}
		default:
			return state;
	}
};
