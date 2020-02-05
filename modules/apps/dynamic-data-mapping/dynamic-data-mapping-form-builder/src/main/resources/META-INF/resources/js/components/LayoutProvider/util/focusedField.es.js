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

import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';

import {updateFieldValidationProperty} from './fields.es';
import {updateSettingsContextProperty} from './settings.es';

const shouldAutoGenerateName = (
	defaultLanguageId,
	editingLanguageId,
	focusedField
) => {
	const {fieldName, label} = focusedField;

	return (
		defaultLanguageId === editingLanguageId &&
		fieldName.indexOf(normalizeFieldName(label)) === 0
	);
};

export const updateFocusedFieldName = (
	editingLanguageId,
	fieldNameGenerator,
	focusedField,
	value
) => {
	const {fieldName, label} = focusedField;
	const normalizedFieldName = normalizeFieldName(value);

	let newFieldName;

	if (normalizedFieldName !== '') {
		newFieldName = fieldNameGenerator(value, fieldName);
	}
	else {
		newFieldName = fieldNameGenerator(label, fieldName);
	}

	if (newFieldName) {
		let {settingsContext} = focusedField;

		settingsContext = {
			...settingsContext,
			pages: updateFieldValidationProperty(
				settingsContext.pages,
				fieldName,
				'fieldName',
				newFieldName
			)
		};

		focusedField = {
			...focusedField,
			fieldName: newFieldName,
			settingsContext: updateSettingsContextProperty(
				editingLanguageId,
				settingsContext,
				'name',
				newFieldName
			)
		};
	}

	return focusedField;
};

export const updateFocusedFieldDataType = (
	editingLanguageId,
	focusedField,
	value
) => {
	let {settingsContext} = focusedField;

	settingsContext = {
		...settingsContext,
		pages: updateFieldValidationProperty(
			settingsContext.pages,
			focusedField.fieldName,
			'dataType',
			value
		)
	};

	return {
		...focusedField,
		dataType: value,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			settingsContext,
			'dataType',
			value
		)
	};
};

export const updateFocusedFieldLabel = (
	defaultLanguageId,
	editingLanguageId,
	fieldNameGenerator,
	focusedField,
	value
) => {
	let {fieldName, settingsContext} = focusedField;

	if (
		shouldAutoGenerateName(
			defaultLanguageId,
			editingLanguageId,
			focusedField
		)
	) {
		const updates = updateFocusedFieldName(
			editingLanguageId,
			fieldNameGenerator,
			focusedField,
			value
		);

		fieldName = updates.fieldName;
		settingsContext = updates.settingsContext;
	}

	return {
		...focusedField,
		fieldName,
		label: value,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			settingsContext,
			'label',
			value
		)
	};
};

export const updateFocusedFieldProperty = (
	editingLanguageId,
	focusedField,
	propertyName,
	propertyValue
) => {
	return {
		...focusedField,
		[propertyName]: propertyValue,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			focusedField.settingsContext,
			propertyName,
			propertyValue
		)
	};
};

export const updateFocusedFieldOptions = (
	editingLanguageId,
	focusedField,
	value
) => {
	const options = value[editingLanguageId];

	return {
		...focusedField,
		options,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			focusedField.settingsContext,
			'options',
			value
		)
	};
};

export const updateFocusedField = (
	{defaultLanguageId, editingLanguageId, fieldNameGenerator},
	state,
	fieldName,
	value
) => {
	let {focusedField} = state;

	if (fieldName === 'dataType') {
		focusedField = {
			...focusedField,
			...updateFocusedFieldDataType(
				editingLanguageId,
				focusedField,
				value
			)
		};
	}
	else if (fieldName === 'label') {
		focusedField = {
			...focusedField,
			...updateFocusedFieldLabel(
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				focusedField,
				value
			)
		};
	}
	else if (fieldName === 'name') {
		focusedField = {
			...focusedField,
			...updateFocusedFieldName(
				editingLanguageId,
				fieldNameGenerator,
				focusedField,
				value
			)
		};
	}
	else if (fieldName === 'options') {
		focusedField = {
			...focusedField,
			...updateFocusedFieldOptions(editingLanguageId, focusedField, value)
		};
	}
	else {
		focusedField = {
			...focusedField,
			...updateFocusedFieldProperty(
				editingLanguageId,
				focusedField,
				fieldName,
				value
			)
		};
	}

	return focusedField;
};
