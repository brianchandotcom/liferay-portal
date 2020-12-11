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

/**
 * Normalize field
 * @param {Array} availableLanguageIds
 * @param {Object} field
 * @param {String?} defaultLanguageId
 * @description some fields are translated with the language of
 * themeDisplay.getDefaultLanguageId() which is not necessarily the language of dataDefinition,
 * so we need to normalize all fields so that they receive themeDisplay.getDefaultLanguageId()
 */

function normalizeField(
	availableLanguageIds,
	field,
	defaultLanguageId = themeDisplay.getDefaultLanguageId()
) {
	const toArray = (value) =>
		availableLanguageIds.reduce((accumulator, currentValue) => {
			accumulator[currentValue] =
				value[currentValue] || value[defaultLanguageId] || '';

			return accumulator;
		}, {});

	const toString = (value) =>
		availableLanguageIds.reduce((accumulator, currentValue) => {
			if (value[currentValue] && value[currentValue].length) {
				accumulator[currentValue] = value[currentValue];
			}
			else {
				accumulator[currentValue] = value[defaultLanguageId];
			}

			return accumulator;
		}, {});

	const {defaultValue, label, tip} = field;
	const {options, placeholder, tooltip} = field.customProperties;

	return {
		...field,
		...(defaultValue && {
			defaultValue: Array.isArray(defaultValue[defaultLanguageId])
				? toString(defaultValue)
				: toArray(defaultValue),
		}),
		...(label && {label: toArray(label)}),
		...(tip && {tip: toArray(tip)}),
		customProperties: {
			...field.customProperties,
			...(options && {
				options: toString(options),
			}),
			...(placeholder && {
				placeholder: toArray(placeholder),
			}),
			...(tooltip && {
				tooltip: toArray(tooltip),
			}),
		},
		nestedDataDefinitionFields: field.nestedDataDefinitionFields.length
			? field.nestedDataDefinitionFields.map((nestedField) => ({
					...nestedField,
					...normalizeField(
						availableLanguageIds,
						nestedField,
						defaultLanguageId
					),
			  }))
			: [],
	};
}

/**
 * Normalize Data Definition
 * @param {Object} dataDefinition
 * @param {String?} defaultLanguageId
 */

export function normalizeDataDefinition(
	dataDefinition,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	normalizeFieldset = true
) {
	return {
		...dataDefinition,
		dataDefinitionFields: dataDefinition.dataDefinitionFields.map(
			(field) => {
				if (field.fieldType === 'fieldset' && !normalizeFieldset) {
					return field;
				}

				return normalizeField(
					dataDefinition.availableLanguageIds,
					field,
					defaultLanguageId
				);
			}
		),
		name: dataDefinition.availableLanguageIds.reduce(
			(accumulator, currentValue) => {
				accumulator[currentValue] =
					dataDefinition.name[currentValue] ||
					dataDefinition.name[defaultLanguageId] ||
					'';

				return accumulator;
			},
			{}
		),
	};
}

/**
 * Normalize Data Layout
 * @param {Object} dataLayout
 * @param {String?} defaultLanguageId
 */

export function normalizeDataLayout(
	dataLayout,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	dataDefinitionFieldNames
) {
	const {dataLayoutFields = {}} = dataLayout;

	if (dataDefinitionFieldNames) {
		Object.keys(dataLayoutFields).forEach((field) => {
			if (!dataDefinitionFieldNames.includes(field)) {
				delete dataLayoutFields[field];
			}
		});
	}

	return {
		...dataLayout,
		dataLayoutFields,
		dataLayoutPages: dataLayout.dataLayoutPages.map((dataLayoutPage) => ({
			...dataLayoutPage,
			dataLayoutRows: (dataLayoutPage.dataLayoutRows || []).map(
				(dataLayoutRow) => ({
					...dataLayoutRow,
					dataLayoutColumns: (
						dataLayoutRow.dataLayoutColumns || []
					).map((dataLayoutColumn) => ({
						...dataLayoutColumn,
						fieldNames: dataLayoutColumn.fieldNames || [],
					})),
				})
			),
			description: {
				...dataLayout.description,
				[defaultLanguageId]:
					dataLayoutPage.description[defaultLanguageId] || '',
			},
			title: {
				...dataLayoutPage.title,
				[defaultLanguageId]:
					dataLayoutPage.title[defaultLanguageId] || '',
			},
		})),
		dataRules: dataLayout.dataRules.map((rule) => {
			delete rule.ruleEditedIndex;

			return rule;
		}),
	};
}

/**
 * Normalize data layout rows
 * @param {Array} dataLayoutPages
 */
export function normalizeDataLayoutRows(dataLayoutPages) {
	return dataLayoutPages[0].dataLayoutRows.map(({dataLayoutColumns}) => {
		return {
			columns: dataLayoutColumns.map(
				({columnSize: size, fieldNames: fields}) => ({
					fields,
					size,
				})
			),
		};
	});
}

/**
 * Normalize rule
 * @param {Object} dataRule
 */
export function normalizeRule(dataRule) {
	return {
		...dataRule,
		name: {
			en_US: dataRule.name,
		},
	};
}
