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
	FieldSupport,
	PagesVisitor,
	getDDMFormFieldSettingsContext,
} from 'data-engine-js-components-web';

import {getDataDefinitionField as getDataDefinitionFieldUtils} from './dataDefinition.es';
import {normalizeDataDefinition, normalizeDataLayout} from './normalizers.es';

export function getDDMFormField({
	dataDefinition,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	editingLanguageId = defaultLanguageId,
	fieldName,
	fieldTypes,
}) {
	const dataDefinitionField = getDataDefinitionFieldUtils(
		dataDefinition,
		fieldName
	);
	const {fieldType, nestedDataDefinitionFields} = dataDefinitionField;

	if (fieldType === 'ddm-text-html') {
		dataDefinitionField.fieldType = 'rich_text';
	}
	const settingsContext = getDDMFormFieldSettingsContext({
		dataDefinitionField,
		defaultLanguageId,
		editingLanguageId,
		fieldTypes,
	});

	const nestedFields = nestedDataDefinitionFields.map(({name: fieldName}) =>
		getDDMFormField({
			dataDefinition,
			defaultLanguageId,
			editingLanguageId,
			fieldName,
			fieldTypes,
		})
	);

	const ddmFormField = {nestedFields, settingsContext};
	const visitor = new PagesVisitor(settingsContext.pages);

	visitor.mapFields((field) => {
		const {fieldName, localizable, type, value} = field;
		if (type === 'options' && value) {
			ddmFormField[fieldName] = value[editingLanguageId];
		}
		else if (fieldName === 'name') {
			ddmFormField.fieldName = value;
		}
		else {
			ddmFormField[fieldName] = localizable
				? value[editingLanguageId] ?? value[defaultLanguageId]
				: value;
		}
	});
	if (!ddmFormField.instanceId) {
		ddmFormField.instanceId = FieldSupport.generateInstanceId();
	}

	return ddmFormField;
}

export function getDefaultDataLayout(dataDefinition) {
	const {dataDefinitionFields} = dataDefinition;

	return {
		dataLayoutPages: [
			{
				dataLayoutRows: dataDefinitionFields.map(({name}) => ({
					dataLayoutColumns: [
						{
							columnSize: 12,
							fieldNames: [name],
						},
					],
				})),
			},
		],
	};
}

/**
 * Converts a FieldSet from data-engine to form-builder data definition
 */
export function getFieldSetDDMForm({
	allowInvalidAvailableLocalesForProperty,
	editingLanguageId,
	fieldSet,
	fieldTypes,
}) {
	const {defaultDataLayout, defaultLanguageId} = fieldSet;

	const {dataLayoutPages} = normalizeDataLayout(
		defaultDataLayout,
		defaultLanguageId
	);

	const dataDefinition = allowInvalidAvailableLocalesForProperty
		? fieldSet
		: normalizeDataDefinition(fieldSet, defaultLanguageId);

	const pages = dataLayoutPages.map((dataLayoutPage) => ({
		rows: dataLayoutPage.dataLayoutRows.map((dataLayoutRow) => ({
			columns: dataLayoutRow.dataLayoutColumns.map(
				({columnSize, fieldNames}) => ({
					fields: fieldNames.map((fieldName) =>
						getDDMFormField({
							dataDefinition,
							defaultLanguageId,
							editingLanguageId,
							fieldName,
							fieldTypes,
						})
					),
					size: columnSize,
				})
			),
		})),
	}));

	const {description, id, name} = dataDefinition;

	return {
		description:
			description[editingLanguageId] ?? description[defaultLanguageId],
		id,
		localizedDescription: description,
		localizedTitle: name,
		pages,
		title: name[editingLanguageId] ?? name[defaultLanguageId],
	};
}
