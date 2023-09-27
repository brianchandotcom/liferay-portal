/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {updateFieldValidationProperty} from '../core/utils/fields';
import {generateInstanceId, getDefaultFieldName} from './fieldSupport';
import {normalizeFieldName} from './fields.es';
import {PagesVisitor} from './visitors.es';

export function getSettingsContextProperty(
	settingsContext,
	propertyName,
	propertyType = 'value'
) {
	let propertyValue;
	const visitor = new PagesVisitor(settingsContext.pages);

	visitor.mapFields((field) => {
		if (propertyName === field.fieldName) {
			propertyValue = field[propertyType];
		}
	});

	return propertyValue;
}

export function setFieldReferenceErrorMessage(
	settingsContext,
	propertyName,
	displayErrors = true,
	shouldUpdateValue = false
) {
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
}

export function updateSettingsContextProperty(
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	editingLanguageId,
	settingsContext,
	propertyName,
	propertyValue
) {
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
					value =
						propertyValue[editingLanguageId] ||
						propertyValue[defaultLanguageId];
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
				}

				field.localizedValue = {
					...(field.localizedValue ?? {}),
					[editingLanguageId]: value,
				};
			}

			return field;
		}),
	};
}

export function updateSettingsContextInstanceId({settingsContext}) {
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields((field) => {
			const newField = {
				...field,
				instanceId: generateInstanceId(),
			};

			return newField;
		}),
	};
}

export function updateFieldName(
	defaultLanguageId,
	editingLanguageId,
	fieldNameGenerator,
	focusedField,
	value
) {
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
				defaultLanguageId,
				editingLanguageId,
				settingsContext,
				'name',
				newFieldName
			),
		};
	}

	return focusedField;
}

export function updateFieldReference(
	focusedField,
	invalid = false,
	shouldUpdateValue = false
) {
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
}

export function updateFieldDataType(
	defaultLanguageId,
	editingLanguageId,
	focusedField,
	value
) {
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
			defaultLanguageId,
			editingLanguageId,
			settingsContext,
			'dataType',
			value
		),
	};
}

export function updateFieldLabel(
	defaultLanguageId,
	editingLanguageId,
	fieldNameGenerator,
	focusedField,
	generateFieldNameUsingFieldLabel,
	value
) {
	let {fieldName, settingsContext} = focusedField;
	let label = value;

	if (
		generateFieldNameUsingFieldLabel &&
		defaultLanguageId === editingLanguageId
	) {
		const updates = updateFieldName(
			defaultLanguageId,
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
			defaultLanguageId,
			editingLanguageId,
			settingsContext,
			'label',
			value
		),
	};
}

const isLocalizedObjectValue = ({localizable, value}) => {
	return typeof value === 'object' && localizable;
};

const getValueLocalized = (
	localizable,
	value,
	defaultLanguageId,
	editingLanguageId
) => {
	if (value === null) {
		return value;
	}

	if (
		isLocalizedObjectValue({localizable, value}) &&
		value[editingLanguageId] !== undefined
	) {
		return value[editingLanguageId];
	}
	else if (
		isLocalizedObjectValue({localizable, value}) &&
		value[defaultLanguageId]
	) {
		return value[defaultLanguageId];
	}

	return value;
};

export function updateFieldProperty(
	defaultLanguageId,
	editingLanguageId,
	focusedField,
	propertyName,
	propertyValue
) {
	return {
		...focusedField,
		[propertyName]: getValueLocalized(
			focusedField.localizable,
			propertyValue,
			defaultLanguageId,
			editingLanguageId
		),
		settingsContext: updateSettingsContextProperty(
			defaultLanguageId,
			editingLanguageId,
			focusedField.settingsContext,
			propertyName,
			propertyValue
		),
	};
}

export function updateFieldOptions(
	defaultLanguageId,
	editingLanguageId,
	focusedField,
	value
) {
	const options = value[editingLanguageId];

	return {
		...focusedField,
		options,
		settingsContext: updateSettingsContextProperty(
			defaultLanguageId,
			editingLanguageId,
			focusedField.settingsContext,
			'options',
			value
		),
	};
}

export function updateField(
	{
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		generateFieldNameUsingFieldLabel,
	},
	field,
	propertyName,
	propertyValue
) {
	if (propertyName === 'dataType') {
		field = {
			...field,
			...updateFieldDataType(
				defaultLanguageId,
				editingLanguageId,
				field,
				propertyValue
			),
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
				defaultLanguageId,
				editingLanguageId,
				fieldNameGenerator,
				field,
				propertyValue
			),
		};
	}
	else if (propertyName === 'numericInputMask') {
		field = {
			...field,
			...propertyValue,
		};
	}
	else if (propertyName === 'options') {
		field = {
			...field,
			...updateFieldOptions(
				defaultLanguageId,
				editingLanguageId,
				field,
				propertyValue
			),
		};
	}
	else {
		field = {
			...field,
			...updateFieldProperty(
				defaultLanguageId,
				editingLanguageId,
				field,
				propertyName,
				propertyValue
			),
		};
	}

	return field;
}
