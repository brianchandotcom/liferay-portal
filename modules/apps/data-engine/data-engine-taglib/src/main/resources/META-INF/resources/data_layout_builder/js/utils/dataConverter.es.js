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

import {FieldSupport, PagesVisitor} from 'data-engine-js-components-web';

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

function getDDMFormFieldSettingsContext({
	dataDefinitionField,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	editingLanguageId = defaultLanguageId,
	fieldTypes,
}) {
	const {settingsContext} = fieldTypes.find(({name}) => {
		return name === dataDefinitionField.fieldType;
	});

	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields((field) => {
			const {fieldName, localizable} = field;
			const propertyValue = _getDataDefinitionFieldPropertyValue(
				dataDefinitionField,
				_fromDDMFormToDataDefinitionPropertyName(fieldName)
			);

			const value = propertyValue ?? field.value;

			let localizedValue = {};

			if (localizable) {
				localizedValue = {...propertyValue};
			}

			if (Object.keys(localizedValue).length == 0) {
				localizedValue = {[defaultLanguageId]: ''};
			}

			let multiple = field.multiple;
			let options = field.options;

			if (
				field.type === 'select' &&
				field.fieldName === 'predefinedValue'
			) {
				multiple = dataDefinitionField.customProperties.multiple;
				options =
					dataDefinitionField.customProperties.options[
						editingLanguageId
					];
			}

			return {
				...field,
				defaultLanguageId,
				locale: defaultLanguageId,
				localizedValue,
				multiple,
				options,
				value,
			};
		}),
	};
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
 * Gets a data definition from a field
 *
 * @param {object} field - The field
 * @param {Object[]} field.nestedFields - The array containing all nested fields.
 * 										  It may be undefined
 * @param {object} field.settingsContext - The settings context of a field
 */
export function getDataDefinitionField({nestedFields = [], settingsContext}) {
	const nestedDataDefinitionFields = nestedFields.map((field) =>
		getDataDefinitionField(field)
	);
	const dataDefinition = {
		customProperties: {},
		nestedDataDefinitionFields,
	};
	const settingsContextVisitor = new PagesVisitor(settingsContext.pages);

	settingsContextVisitor.mapFields(
		({fieldName, localizable, localizedValue, value}) => {
			if (fieldName === 'predefinedValue') {
				fieldName = 'defaultValue';
			}
			else if (fieldName === 'type') {
				fieldName = 'fieldType';
			}

			const updatableHash = _isCustomProperty(fieldName)
				? dataDefinition.customProperties
				: dataDefinition;

			if (localizable) {
				updatableHash[fieldName] = localizedValue ?? {};
			}
			else {
				updatableHash[fieldName] = value;
			}
		},
		false
	);

	return dataDefinition;
}

export function getDataDefinitionFieldByFieldName({
	dataDefinition,
	editingLanguageId,
	fieldName,
	fieldTypes,
}) {
	const dataDefinitionField = dataDefinition.dataDefinitionFields.find(
		(field) => field.name === fieldName
	);

	const settingsContext = getDDMFormFieldSettingsContext({
		dataDefinitionField,
		editingLanguageId,
		fieldTypes,
	});

	return {
		...dataDefinitionField,
		editingLanguageId,
		settingsContext,
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

// private

function _fromDDMFormToDataDefinitionPropertyName(propertyName) {
	const map = {
		fieldName: 'name',
		nestedFields: 'nestedDataDefinitionFields',
		predefinedValue: 'defaultValue',
		type: 'fieldType',
	};

	return map[propertyName] || propertyName;
}

function _getDataDefinitionFieldPropertyValue(
	dataDefinitionField,
	propertyName
) {
	const {customProperties} = dataDefinitionField;

	if (customProperties && _isCustomProperty(propertyName)) {
		return customProperties[propertyName];
	}

	return dataDefinitionField[propertyName];
}

function _isCustomProperty(name) {
	return ![
		'defaultValue',
		'fieldType',
		'indexable',
		'indexType',
		'label',
		'localizable',
		'name',
		'readOnly',
		'repeatable',
		'required',
		'showLabel',
		'tip',
	].includes(name);
}

// For test purpose only

export default {
	_fromDDMFormToDataDefinitionPropertyName,
	_isCustomProperty,
};
