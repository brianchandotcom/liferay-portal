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
	PagesVisitor,
	SettingsContext,
	sectionAdded,
} from 'data-engine-js-components-web';

import {EVENT_TYPES} from '../eventTypes';
import {
	getDDMFormField,
	getDefaultDataLayout,
	getFieldSetDDMForm,
} from '../utils/dataConverter.es';
import {normalizeDataLayoutRows} from '../utils/normalizers.es';

export default (state, action, config) => {
	switch (action.type) {
		case EVENT_TYPES.FIELD_SET.UPDATE_LIST: {
			return {fieldSets: action.payload.fieldSets};
		}
		case EVENT_TYPES.FIELD_SET.ADD: {
			const {
				allowInvalidAvailableLocalesForProperty,
				fieldTypes,
				generateFieldNameUsingFieldLabel,
				getFieldNameGenerator,
			} = config;

			const {activePage, editingLanguageId, pages} = state;

			const {
				fieldSet,
				fieldName,
				indexes = {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: pages[activePage].rows.length,
				},
				parentFieldName,
				properties,
				useFieldName,
			} = action.payload;

			const fieldSetDDMForm = getFieldSetDDMForm({
				allowInvalidAvailableLocalesForProperty,
				editingLanguageId,
				fieldSet,
				fieldTypes,
			});

			const {dataLayoutPages} =
				fieldSet.defaultDataLayout || getDefaultDataLayout(fieldSet);

			const rows =
				fieldSet.id && normalizeDataLayoutRows(dataLayoutPages);

			const fieldNameGenerator = getFieldNameGenerator(
				pages,
				generateFieldNameUsingFieldLabel
			);

			const visitor = new PagesVisitor(fieldSetDDMForm.pages);
			const nestedFields = [];

			const {availableLanguageIds, defaultLanguageId} = fieldSet;

			const props = {
				availableLanguageIds,
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				fieldTypes,
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

			if (fieldSetDDMForm.id) {
				fieldSetField = SettingsContext.updateField(
					props,
					fieldSetField,
					'ddmStructureId',
					fieldSetDDMForm.id
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

			if (fieldName) {
				return sectionAdded(
					props,
					{
						...state,
						pages,
					},
					{
						data: {
							fieldName,
							parentFieldName,
						},
						indexes,
						newField: SettingsContext.updateField(
							props,
							fieldSetField,
							'label',
							fieldSet.name
						),
					}
				);
			}

			const newField = SettingsContext.updateField(
				props,
				fieldSetField,
				'label',
				fieldSetDDMForm.localizedTitle
			);

			const updatedPages = FieldSupport.addFieldToPage({
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				generateFieldNameUsingFieldLabel,
				indexes,
				newField,
				pages,
				parentFieldName,
			});

			return {
				activePage: indexes.pageIndex,
				focusedField: newField,
				pages: updatedPages,
			};
		}
		case EVENT_TYPES.FIELD_SET.UPDATE: {
			const {fieldTypes} = config;
			const {editingLanguageId, pages} = state;
			const {fieldSet} = action.payload;
			const {
				availableLanguageIds,
				dataDefinitionFields,
				defaultDataLayout,
				defaultLanguageId,
				id,
			} = fieldSet;
			const fieldSetId = `${id}`;
			const visitor = new PagesVisitor(pages);
			const newPages = visitor.mapFields((field) => {
				if (String(field.ddmStructureId) !== fieldSetId) {
					return field;
				}
				const nestedFields = dataDefinitionFields.map(({name}) => {
					return getDDMFormField({
						dataDefinition: fieldSet,
						editingLanguageId,
						fieldName: name,
						fieldTypes,
					});
				});
				const rows = normalizeDataLayoutRows(
					defaultDataLayout.dataLayoutPages
				);
				const props = {
					availableLanguageIds,
					defaultLanguageId,
					editingLanguageId,
				};

				let updatedFieldSet = SettingsContext.updateField(
					props,
					field,
					'label',
					fieldSet.name
				);

				if (rows && rows.length) {
					updatedFieldSet = SettingsContext.updateField(
						props,
						updatedFieldSet,
						'rows',
						rows
					);
				}

				return {...updatedFieldSet, nestedFields, rows};
			});

			return {pages: newPages};
		}
		default:
			return {};
	}
};
