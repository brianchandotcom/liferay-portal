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

import {addItem, updateItem} from './client.es';
import {getLocalizedValue} from './lang.es';
import {normalizeDataDefinition, normalizeDataLayout} from './normalizers.es';

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

export function containsFieldSet(dataDefinition, dataDefinitionId) {
	let hasFieldSet = false;

	forEachDataDefinitionField(dataDefinition, (dataDefinitionField) => {
		const {customProperties, fieldType} = dataDefinitionField;

		if (
			fieldType === 'fieldset' &&
			customProperties &&
			customProperties.ddmStructureId == dataDefinitionId
		) {
			hasFieldSet = true;
		}

		return hasFieldSet;
	});

	return hasFieldSet;
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

export function getDataDefinitionFieldSet(dataDefinitionFields, fieldSetId) {
	return dataDefinitionFields.find(
		({customProperties: {ddmStructureId}}) => ddmStructureId == fieldSetId
	);
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

export function saveDataDefinition({
	dataDefinition,
	dataDefinitionId,
	dataLayout,
	dataLayoutId,
}) {
	const {dataDefinitionFields, defaultLanguageId} = dataDefinition;

	const dataDefinitionFieldNames = dataDefinitionFields.map(({name}) => name);

	const normalizedDataDefinition = normalizeDataDefinition(
		dataDefinition,
		defaultLanguageId,
		false
	);
	const normalizedDataLayout = normalizeDataLayout(
		dataLayout,
		defaultLanguageId,
		dataDefinitionFieldNames
	);

	const updateDefinition = () =>
		updateItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`,
			normalizedDataDefinition
		);

	if (dataLayoutId) {
		return updateDefinition().then(() =>
			updateItem(
				`/o/data-engine/v2.0/data-layouts/${dataLayoutId}`,
				normalizedDataLayout
			)
		);
	}

	return updateDefinition().then(() =>
		addItem(
			`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-layouts`,
			normalizedDataLayout
		)
	);
}
