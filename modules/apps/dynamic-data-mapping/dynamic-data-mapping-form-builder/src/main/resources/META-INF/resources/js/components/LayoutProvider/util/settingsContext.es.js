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
	PagesVisitor,
	generateInstanceId,
	normalizeFieldName,
} from 'dynamic-data-mapping-form-renderer';

import {getDefaultFieldName} from '../../../util/fieldSupport.es';
import {updateFieldValidationProperty} from './fields.es';

export const getSettingsContextProperty = (
	settingsContext,
	propertyName,
	propertyType = 'value'
) => {
	let propertyValue;
	const visitor = new PagesVisitor(settingsContext.pages);

	visitor.mapFields((field) => {
		if (propertyName === field.fieldName) {
			propertyValue = field[propertyType];
		}
	});

	return propertyValue;
};

export const setFieldReferenceErrorMessage = (
	settingsContext,
	propertyName,
	displayErrors = true,
	shouldUpdateValue = false
) => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields((field) => {
			if (propertyName === field.fieldName) {
				field = {
					...field,
					displayErrors,
					errorMessage: Liferay.Language.get(
						'this-reference-is-already-being-used'
					),
					shouldUpdateValue,
					valid: !displayErrors,
				};
			}

			return field;
		}),
	};
};

export const updateSettingsContextProperty = (
	editingLanguageId,
	settingsContext,
	propertyName,
	propertyValue
) => {
	const visitor = new PagesVisitor(settingsContext.pages);
	const isLocalizablePropertyValue = typeof propertyValue === 'object';
	const isLocalizableLabel =
		propertyName === 'label' && isLocalizablePropertyValue;

	return {
		...settingsContext,
		pages: visitor.mapFields((field) => {
			if (propertyName === field.fieldName) {
				let value = propertyValue;

				if (isLocalizableLabel) {
					value = propertyValue[editingLanguageId];
				}

				field = {
					...field,
					value,
				};

				if (field.localizable) {
					if (isLocalizableLabel) {
						field.localizedValue = {
							...propertyValue,
						};
					}

					field.localizedValue = {
						...field.localizedValue,
						[editingLanguageId]: value,
					};
				}
			}

			return field;
		}),
	};
};

export const updateSettingsContextInstanceId = ({settingsContext}) => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields((field) => {
			const newField = {
				...field,
				instanceId: generateInstanceId(8),
			};

			return newField;
		}),
	};
};

export const updateFieldName = (
	editingLanguageId,
	fieldNameGenerator,
	focusedField,
	value
) => {
	const {fieldName} = focusedField;
	const normalizedFieldName = normalizeFieldName(value);

	let newFieldName;

	if (normalizedFieldName !== '') {
		newFieldName = fieldNameGenerator(value, fieldName);
	}
	else {
		newFieldName = fieldNameGenerator(getDefaultFieldName(), fieldName);
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
			),
		};

		focusedField = {
			...focusedField,
			fieldName: newFieldName,
			name: newFieldName,
			settingsContext: updateSettingsContextProperty(
				editingLanguageId,
				settingsContext,
				'name',
				newFieldName
			),
		};
	}

	return focusedField;
};

export const updateFieldReference = (
	focusedField,
	invalid = false,
	shouldUpdateValue = false
) => {
	const {settingsContext} = focusedField;

	focusedField = {
		...focusedField,
		settingsContext: setFieldReferenceErrorMessage(
			settingsContext,
			'fieldReference',
			invalid,
			shouldUpdateValue
		),
	};

	return focusedField;
};

export const updateFieldDataType = (editingLanguageId, focusedField, value) => {
	let {settingsContext} = focusedField;

	settingsContext = {
		...settingsContext,
		pages: updateFieldValidationProperty(
			settingsContext.pages,
			focusedField.fieldName,
			'dataType',
			value
		),
	};

	return {
		...focusedField,
		dataType: value,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			settingsContext,
			'dataType',
			value
		),
	};
};

export const updateFieldLabel = (
	defaultLanguageId,
	editingLanguageId,
	fieldNameGenerator,
	focusedField,
	generateFieldNameUsingFieldLabel,
	value
) => {
	let {fieldName, settingsContext} = focusedField;
	let label = value;

	if (
		generateFieldNameUsingFieldLabel &&
		defaultLanguageId === editingLanguageId
	) {
		const updates = updateFieldName(
			editingLanguageId,
			fieldNameGenerator,
			focusedField,
			value
		);

		fieldName = updates.fieldName;
		settingsContext = updates.settingsContext;
	}

	if (typeof value === 'object') {
		label = value[editingLanguageId] || value[defaultLanguageId];
	}

	return {
		...focusedField,
		fieldName,
		label,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			settingsContext,
			'label',
			value
		),
	};
};

export const updateFieldProperty = (
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
		),
	};
};

export const updateFieldOptions = (editingLanguageId, focusedField, value) => {
	const options = value[editingLanguageId];

	return {
		...focusedField,
		options,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			focusedField.settingsContext,
			'options',
			value
		),
	};
};

export const updateField = (
	{
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		generateFieldNameUsingFieldLabel,
	},
	field,
	propertyName,
	propertyValue
) => {
	if (propertyName === 'dataType') {
		field = {
			...field,
			...updateFieldDataType(editingLanguageId, field, propertyValue),
		};
	}
	else if (propertyName === 'label') {
		field = {
			...field,
			...updateFieldLabel(
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				field,
				generateFieldNameUsingFieldLabel,
				propertyValue
			),
		};
	}
	else if (propertyName === 'name') {
		field = {
			...field,
			...updateFieldName(
				editingLanguageId,
				fieldNameGenerator,
				field,
				propertyValue
			),
		};
	}
	else if (propertyName === 'options') {
		field = {
			...field,
			...updateFieldOptions(editingLanguageId, field, propertyValue),
		};
	}
	else {
		field = {
			...field,
			...updateFieldProperty(
				editingLanguageId,
				field,
				propertyName,
				propertyValue
			),
		};
	}

	return field;
};
