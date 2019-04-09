import * as FormSupport from '../../Form/FormSupport.es';
import {updateFocusedField} from '../util/focusedField.es';
import {updateRulesFieldName} from '../util/rules.es';

export const updatePages = (pages, oldFieldProperties, newFieldProperties) => {
	const {fieldName} = oldFieldProperties;

	return FormSupport.updateField(
		pages,
		fieldName,
		newFieldProperties
	);
};

export const updateRules = (rules, oldFieldProperties, newFieldProperties) => {
	const {fieldName} = oldFieldProperties;
	const newFieldName = newFieldProperties.fieldName;

	return updateRulesFieldName(rules, fieldName, newFieldName);
};

export const updateField = (state, defaultLanguageId, editingLanguageId, fieldName, fieldValue) => {
	const {focusedField, pages, rules} = state;
	const updatedFocusedField = updateFocusedField(state, defaultLanguageId, editingLanguageId, fieldName, fieldValue);

	return {
		focusedField: updatedFocusedField,
		pages: updatePages(pages, focusedField, updatedFocusedField),
		rules: updateRules(rules, focusedField, updatedFocusedField)
	};
};

export const handleFieldEdited = (state, defaultLanguageId, editingLanguageId, event) => {
	const {propertyName, propertyValue} = event;
	let newState = {};

	if (propertyName !== 'name' || propertyValue !== '') {
		newState = updateField(state, defaultLanguageId, editingLanguageId, propertyName, propertyValue);
	}

	return newState;
};

export default handleFieldEdited;