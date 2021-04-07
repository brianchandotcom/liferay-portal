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
import {FieldSupport} from 'dynamic-data-mapping-form-builder';

import {getDataDefinitionField as getDataDefinitionFieldUtils} from './dataDefinition.es';
import {
	normalizeDataDefinition,
	normalizeDataLayout,
	normalizeDataLayoutRows,
} from './normalizers.es';

export function getDataDefinitionFieldSet({
	allowInvalidAvailableLocalesForProperty,
	availableLanguageIds = [],
	defaultLanguageId,
	editingLanguageId,
	fieldSet,
	fieldTypes,
}) {
	if (!availableLanguageIds.includes(defaultLanguageId)) {
		availableLanguageIds = [...availableLanguageIds, defaultLanguageId];
	}

	const {dataLayoutPages} =
		fieldSet.defaultDataLayout || getDefaultDataLayout(fieldSet);

	const fieldSetDDMForm = getFieldSetDDMForm({
		allowInvalidAvailableLocalesForProperty,
		availableLanguageIds,
		editingLanguageId,
		fieldSet,
		fieldTypes,
	});

	return {
		fieldSet: fieldSetDDMForm,
		rows: fieldSet.id && normalizeDataLayoutRows(dataLayoutPages),
	};
}

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

	if (dataDefinitionField.fieldType === 'ddm-text-html') {
		dataDefinitionField.fieldType = 'rich_text';
	}

	const settingsContext = getDDMFormFieldSettingsContext({
		dataDefinitionField,
		defaultLanguageId,
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
					defaultLanguageId,
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
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	editingLanguageId = themeDisplay.getDefaultLanguageId(),
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

export function getDataDefinitionAndDataLayout({
	availableLanguageIds = [themeDisplay.getDefaultLanguageId()],
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	pages,
	paginationMode,
	rules = [],
}) {
	const fieldDefinitions = [];
	const pagesVisitor = new PagesVisitor(pages);

	const newPages = pagesVisitor.mapFields((field) => {
		fieldDefinitions.push(getDataDefinitionField(field));

		return field.fieldName;
	}, false);

	return {
		definition: {
			availableLanguageIds,
			dataDefinitionFields: fieldDefinitions,
			defaultLanguageId,
		},
		layout: {
			dataLayoutPages: newPages.map((page) => {
				const rows = page.rows.map((row) => {
					const columns = row.columns.map((column) => {
						return {
							columnSize: column.size,
							fieldNames: column.fields,
						};
					});

					return {
						dataLayoutColumns: columns,
					};
				});

				return {
					dataLayoutRows: rows,
					description: page.localizedDescription,
					title: page.localizedTitle,
				};
			}),
			dataRules: rules.map((rule) => {
				if (typeof rule.name === 'string') {
					rule.name = {
						[defaultLanguageId]: rule.name,
					};
				}

				delete rule.ruleEditedIndex;

				return rule;
			}),
			paginationMode,
		},
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
	editingLanguageId,
	fieldSet,
	fieldTypes,
}) {
	const {defaultDataLayout, defaultLanguageId} = fieldSet;

	const newDataDefinition = allowInvalidAvailableLocalesForProperty
		? fieldSet
		: normalizeDataDefinition(fieldSet, defaultLanguageId);

	const fieldSetDataLayout = normalizeDataLayout(
		defaultDataLayout,
		defaultLanguageId
	);

	return _getDDMForm({
		dataDefinition: newDataDefinition,
		dataLayout: fieldSetDataLayout,
		defaultLanguageId,
		editingLanguageId: editingLanguageId ?? defaultLanguageId,
		fieldTypes,
	});
}

export function getFormData({
	availableLanguageIds,
	availableLanguageIdsState,
	defaultLanguageId,
	layoutProvider,
}) {
	const {
		props: {defaultLanguageId: layoutDefaultLanguageId},
		state: {pages: layoutProviderPages, rules},
	} = layoutProvider;

	const pagesVisitor = new PagesVisitor(layoutProviderPages);

	const pages = pagesVisitor.mapFields(
		(field) => {
			const {settingsContext} = field;

			const settingsContextPagesVisitor = new PagesVisitor(
				settingsContext.pages
			);

			const newSettingsContext = {
				...settingsContext,
				pages: settingsContextPagesVisitor.mapFields(
					(settingsField) => {
						if (settingsField.type === 'options') {
							const {value} = settingsField;
							const newValue = {};

							Object.keys(value).forEach((locale) => {
								newValue[locale] = value[locale]?.filter(
									(localizedValue) =>
										localizedValue.value !== '' &&
										localizedValue.label !== ''
								);
							});

							if (!newValue[layoutDefaultLanguageId]) {
								newValue[layoutDefaultLanguageId] = [];
							}

							settingsField = {
								...settingsField,
								value: newValue,
							};
						}

						return settingsField;
					}
				),
			};

			return {
				...field,
				settingsContext: newSettingsContext,
			};
		},
		true,
		true
	);

	return getDataDefinitionAndDataLayout({
		availableLanguageIds: availableLanguageIdsState ?? availableLanguageIds,
		defaultLanguageId,
		pages,
		paginationMode: layoutProvider.getPaginationMode(),
		rules,
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
	defaultLanguageId: fieldSetDefaultLanguageId,
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
								defaultLanguageId: fieldSetDefaultLanguageId,
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
