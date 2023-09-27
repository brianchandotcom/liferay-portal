/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizedValue} from './lang.es';

export function forEachDataDefinitionField(
	dataDefinition = {dataDefinitionFields: []},
	fn
) {
	const {dataDefinitionFields = []} = dataDefinition;

	for (let i = 0; i < dataDefinitionFields.length; i++) {
		const field = dataDefinitionFields[i];

		if (fn(field)) {
			return true;
		}

		if (
			forEachDataDefinitionField(
				{
					dataDefinitionFields:
						field.nestedDataDefinitionFields || [],
				},
				fn
			)
		) {
			return true;
		}
	}

	return false;
}

export function getDataDefinitionField(
	dataDefinition = {dataDefinitionFields: []},
	fieldName
) {
	let field = null;

	forEachDataDefinitionField(dataDefinition, (currentField) => {
		if (currentField.name === fieldName) {
			field = currentField;

			return true;
		}

		return false;
	});

	return field;
}

export function getFieldLabel(dataDefinition, fieldName) {
	const field = getDataDefinitionField(dataDefinition, fieldName);

	if (field) {
		return getLocalizedValue(dataDefinition.defaultLanguageId, field.label);
	}

	return fieldName;
}

export function getOptionLabel(
	options = {},
	value,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	languageId = themeDisplay.getLanguageId()
) {
	const getLabel = (languageId) => {
		if (options[languageId]) {
			return options[languageId].find((option) => option.value === value)
				?.label;
		}
	};

	return getLabel(languageId) || getLabel(defaultLanguageId) || value;
}
