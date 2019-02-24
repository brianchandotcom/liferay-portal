import {FormSupport} from '../../Form/index.es';
import {updateRulesFieldName} from '../util/rules.es';
import {updateFocusedField} from '../util/focusedField.es';

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

export const handleFieldEdited = (state, event) => {
	const {propertyName, propertyValue} = event;
	let newState = {};

	if (propertyName !== 'name' || propertyValue !== '') {
		const {focusedField, pages, rules} = state;
		const updatedFocusedField = updateFocusedField(state, propertyName, propertyValue);

		newState = {
			focusedField: updatedFocusedField,
			pages: updatePages(pages, focusedField, updatedFocusedField),
			rules: updateRules(rules, focusedField, updatedFocusedField)
		};
	}

	return newState;
};

export default handleFieldEdited;