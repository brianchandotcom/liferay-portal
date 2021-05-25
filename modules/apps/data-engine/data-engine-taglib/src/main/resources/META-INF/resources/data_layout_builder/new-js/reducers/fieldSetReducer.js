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
import {
	FieldSetUtil,
	FieldSupport,
	SettingsContext,
} from 'dynamic-data-mapping-form-builder';

import {
	getDDMFormField,
	getDefaultDataLayout,
	getFieldSetDDMForm,
} from '../../js/utils/dataConverter.es';
import {normalizeDataLayoutRows} from '../../js/utils/normalizers.es';
import {EVENT_TYPES} from '../eventTypes';

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

			const newField = SettingsContext.updateField(
				props,
				fieldSetField,
				'label',
				fieldSetDDMForm.localizedTitle
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
		case EVENT_TYPES.FIELD_SET.UPDATE: {
			const {fieldTypes} = config;
			const {editingLanguageId, pages} = state;
			const {fieldSet} = action.payload;
			const {dataDefinitionFields, defaultDataLayout, id} = fieldSet;
			const fieldSetId = `${id}`;
			const visitor = new PagesVisitor(pages);
			const newPages = visitor.mapFields((field) => {
				if (field.ddmStructureId !== fieldSetId) {
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

				return {...field, nestedFields, rows};
			});

			return {pages: newPages};
		}
		default:
			return {};
	}
};
