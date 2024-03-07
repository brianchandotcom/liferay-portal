/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @param {boolean?} normalizeFieldset
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
	defaultLanguageId = themeDisplay.getDefaultLanguageId()
) {
	const {dataLayoutPages, dataRules, description} = dataLayout;

	return {
		...dataLayout,
		dataLayoutPages: dataLayoutPages.map((dataLayoutPage) => ({
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
				...description,
				[defaultLanguageId]:
					dataLayoutPage.description[defaultLanguageId] || '',
			},
			title: {
				...dataLayoutPage.title,
				[defaultLanguageId]:
					dataLayoutPage.title[defaultLanguageId] || '',
			},
		})),
		dataRules: dataRules.map((rule) => {
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
