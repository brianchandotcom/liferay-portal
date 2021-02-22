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

import {FieldSupport} from 'dynamic-data-mapping-form-builder';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {getDataDefinitionField as getDataDefinitionFieldUtils} from './dataDefinition.es';
import {
	normalizeDataDefinition,
	normalizeDataLayout,
	normalizeDataLayoutRows,
} from './normalizers.es';

export function getDataDefinitionFieldSet({
	allowInvalidAvailableLocalesForProperty,
	availableLanguageIds,
	defaultLanguageId,
	editingLanguageId,
	fieldSet,
	fieldTypes,
}) {
	const dataLayoutPages = (
		fieldSet.defaultDataLayout || getDefaultDataLayout(fieldSet)
	).dataLayoutPages;

	if (!availableLanguageIds.includes(defaultLanguageId)) {
		availableLanguageIds = [...availableLanguageIds, defaultLanguageId];
	}

	return {
		fieldSet: getFieldSetDDMForm({
			allowInvalidAvailableLocalesForProperty,
			availableLanguageIds,
			editingLanguageId,
			fieldSet,
			fieldTypes,
		}),
		...(fieldSet.id && {
			rows: normalizeDataLayoutRows(dataLayoutPages),
		}),
	};
}

export function getDDMFormField({
	dataDefinition,
	editingLanguageId = themeDisplay.getDefaultLanguageId(),
	fieldName,
	fieldTypes,
}) {
	const dataDefinitionField = getDataDefinitionFieldUtils(
		dataDefinition,
		fieldName
	);

	if (dataDefinitionField.fieldType === 'ddm-text-html') {
		dataDefinitionField.fieldType = 'rich_text';
	}

	const settingsContext = getDDMFormFieldSettingsContext({
		dataDefinitionField,
		defaultLanguageId: dataDefinition.defaultLanguageId,
		editingLanguageId,
		fieldTypes,
	});

	const ddmFormField = {
		nestedFields: dataDefinitionField.nestedDataDefinitionFields,
		settingsContext,
	};
	const visitor = new PagesVisitor(settingsContext.pages);

	visitor.mapFields((field) => {
		const {fieldName} = field;
		let {value} = field;

		if (fieldName === 'options' && value) {
			value = value[editingLanguageId];
		}
		else if (fieldName === 'name') {
			ddmFormField.fieldName = value;
		}

		ddmFormField[fieldName] = value;
	});

	if (ddmFormField.nestedFields.length > 0) {
		ddmFormField.nestedFields = ddmFormField.nestedFields.map(
			(nestedField) =>
				getDDMFormField({
					dataDefinition,
					editingLanguageId,
					fieldName: nestedField.name,
					fieldTypes,
				})
		);
	}

	if (!ddmFormField.instanceId) {
		ddmFormField.instanceId = FieldSupport.generateInstanceId(8);
	}

	return ddmFormField;
}

export function getDDMFormFieldSettingsContext({
	dataDefinitionField,
	fieldTypes,
	editingLanguageId = themeDisplay.getDefaultLanguageId(),
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
}) {
	const settingsContext = _getSettingsContext(
		dataDefinitionField.fieldType,
		fieldTypes
	);
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields((field) => {
			const {fieldName, localizable} = field;
			const propertyValue = _getDataDefinitionFieldPropertyValue(
				dataDefinitionField,
				_fromDDMFormToDataDefinitionPropertyName(fieldName)
			);

			let value = propertyValue ?? field.value;

			if (localizable && propertyValue && fieldName !== 'label') {
				value =
					propertyValue[editingLanguageId] ||
					propertyValue[defaultLanguageId];
			}

			let localizedValue = {};

			if (localizable) {
				localizedValue = {...propertyValue};
			}

			if (Object.keys(localizedValue).length == 0) {
				localizedValue = {[defaultLanguageId]: ''};
			}

			let options = field.options;

			if (
				field.type === 'select' &&
				field.fieldName === 'predefinedValue'
			) {
				options =
					dataDefinitionField.customProperties.options[
						editingLanguageId
					];
			}

			return {
				...field,
				localizedValue,
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
		({dataType, fieldName, localizable, localizedValue, value}) => {
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
				updatableHash[
					fieldName
				] = _getDataDefinitionFieldFormattedValue(dataType, value);
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

export function getDDMSettingsContextWithVisualProperties({
	dataDefinitionField,
	editingLanguageId,
	fieldTypes,
}) {
	const {pages} = getDDMFormFieldSettingsContext({
		dataDefinitionField,
		editingLanguageId,
		fieldTypes,
	});
	const visitor = new PagesVisitor(pages);

	const fieldProperties = {};

	visitor.mapFields(
		({fieldName, localizable, localizedValue, value, visualProperty}) => {
			if (visualProperty) {
				fieldProperties[fieldName] = localizable
					? localizedValue
					: value;
			}
		}
	);

	return fieldProperties;
}

/**
 * Converts a FieldSet from data-engine to form-builder data definition
 */
export function getFieldSetDDMForm({
	allowInvalidAvailableLocalesForProperty,
	availableLanguageIds,
	editingLanguageId,
	fieldSet,
	fieldTypes,
}) {
	const {defaultDataLayout, defaultLanguageId} = fieldSet;

	let newDataDefinition = {
		availableLanguageIds,
		...fieldSet,
	};

	if (!allowInvalidAvailableLocalesForProperty) {
		newDataDefinition = normalizeDataDefinition(
			newDataDefinition,
			defaultLanguageId
		);
	}

	const fieldSetDataLayout = normalizeDataLayout(
		defaultDataLayout,
		defaultLanguageId
	);

	return _getDDMForm({
		dataDefinition: newDataDefinition,
		dataLayout: fieldSetDataLayout,
		editingLanguageId,
		fieldTypes,
	});
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

function _getDataDefinitionFieldFormattedValue(dataType, value) {
	if (dataType === 'json' && typeof value !== 'string') {
		return JSON.stringify(value);
	}

	return value;
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

function _getDDMForm({
	dataDefinition,
	dataLayout = getDefaultDataLayout(dataDefinition),
	editingLanguageId,
	fieldTypes,
}) {
	const {defaultLanguageId, name} = dataDefinition;

	return {
		description: dataDefinition.description[editingLanguageId],
		id: dataDefinition.id,
		localizedDescription: dataDefinition.description,
		localizedTitle: name,
		pages: dataLayout.dataLayoutPages.map((dataLayoutPage) => ({
			rows: dataLayoutPage.dataLayoutRows.map((dataLayoutRow) => ({
				columns: dataLayoutRow.dataLayoutColumns.map(
					({columnSize, fieldNames}) => ({
						fields: fieldNames.map((fieldName) =>
							getDDMFormField({
								dataDefinition,
								editingLanguageId,
								fieldName,
								fieldTypes,
							})
						),
						size: columnSize,
					})
				),
			})),
		})),
		title: name[editingLanguageId] || name[defaultLanguageId],
	};
}

function _getSettingsContext(fieldType, fieldTypes) {
	const {settingsContext} = fieldTypes.find(({name}) => {
		return name === fieldType;
	});

	return settingsContext;
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
	_getDataDefinitionFieldFormattedValue,
	_isCustomProperty,
};
