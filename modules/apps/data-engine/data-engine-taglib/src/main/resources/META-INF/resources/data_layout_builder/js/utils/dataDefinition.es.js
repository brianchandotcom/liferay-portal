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
