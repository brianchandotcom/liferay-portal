import {
	generateFieldName,
	normalizeFieldName,
	updateFieldValidationProperty
} from './fields.es';
import {getField} from '../util/fields.es';
import {updateSettingsContextProperty} from './settings.es';

const shouldAutoGenerateName = focusedField => {
	const {fieldName, label} = focusedField;

	return fieldName.indexOf(normalizeFieldName(label)) === 0;
};

export const updateFocusedFieldName = (state, editingLanguageId, focusedField, value) => {
	const {fieldName, label} = focusedField;
	const normalizedFieldName = normalizeFieldName(value);

	const {pages} = state;
	let newFieldName;

	if (normalizedFieldName !== '') {
		newFieldName = generateFieldName(pages, value, fieldName);
	}
	else {
		newFieldName = generateFieldName(pages, label, fieldName);
	}

	if (newFieldName) {
		let {settingsContext} = focusedField;

		settingsContext = {
			...settingsContext,
			pages: updateFieldValidationProperty(settingsContext.pages, fieldName, 'fieldName', newFieldName)
		};

		focusedField = {
			...focusedField,
			fieldName: newFieldName,
			settingsContext: updateSettingsContextProperty(editingLanguageId, settingsContext, 'name', newFieldName)
		};
	}

	return focusedField;
};

export const updateFocusedFieldDataType = (editingLanguageId, focusedField, value) => {
	let {settingsContext} = focusedField;

	settingsContext = {
		...settingsContext,
		pages: updateFieldValidationProperty(settingsContext.pages, focusedField.fieldName, 'dataType', value)
	};

	return {
		...focusedField,
		dataType: value,
		settingsContext: updateSettingsContextProperty(editingLanguageId, settingsContext, 'dataType', value)
	};
};

export const updateFocusedFieldLabel = (state, editingLanguageId, focusedField, value) => {
	let {fieldName, settingsContext} = focusedField;

	if (shouldAutoGenerateName(focusedField)) {
		const updates = updateFocusedFieldName(state, editingLanguageId, focusedField, value);

		fieldName = updates.fieldName;
		settingsContext = updates.settingsContext;
	}

	return {
		...focusedField,
		fieldName,
		label: value,
		settingsContext: updateSettingsContextProperty(editingLanguageId, settingsContext, 'label', value)
	};
};

export const updateFocusedFieldProperty = (editingLanguageId, focusedField, propertyName, propertyValue) => {
	return {
		...focusedField,
		[propertyName]: propertyValue,
		settingsContext: updateSettingsContextProperty(editingLanguageId, focusedField.settingsContext, propertyName, propertyValue)
	};
};

export const updateFocusedFieldOptions = (editingLanguageId, focusedField, value) => {
	const options = value[editingLanguageId];

	const settingsContext = updateSettingsContextProperty(editingLanguageId, focusedField.settingsContext, 'options', value);

	const predefinedValue = getField(settingsContext.pages, 'predefinedValue');

	if (predefinedValue) {
		const {value} = predefinedValue;

		if (value && Array.isArray(value)) {
			predefinedValue.value = value.filter(
				currentValue => options.some(
					option => option.value === currentValue
				)
			);
		}
	}

	return {
		...focusedField,
		options,
		predefinedValue: predefinedValue.value,
		settingsContext: updateSettingsContextProperty(
			editingLanguageId,
			settingsContext,
			'predefinedValue',
			predefinedValue.value
		)
	};
};

export const updateFocusedField = (state, editingLanguageId, fieldName, value) => {
	let {focusedField} = state;

	if (fieldName === 'dataType') {
		focusedField = {
			...focusedField,
			...updateFocusedFieldDataType(editingLanguageId, focusedField, value)
		};
	}
	else if (fieldName === 'label') {
		focusedField = {
			...focusedField,
			...updateFocusedFieldLabel(state, editingLanguageId, focusedField, value)
		};
	}
	else if (fieldName === 'name') {
		focusedField = {
			...focusedField,
			...updateFocusedFieldName(state, editingLanguageId, focusedField, value)
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
			...updateFocusedFieldProperty(editingLanguageId, focusedField, fieldName, value)
		};
	}

	return focusedField;
};