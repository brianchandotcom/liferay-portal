import {capitalize} from './strings.es';

export const setLocalizedValue = (obj, languageId, prop, val) => {
	obj[prop] = val;

	const localizedProperty = `localized${capitalize(prop)}`;

	if (!obj[localizedProperty]) {
		obj[localizedProperty] = {};
	}

	obj[localizedProperty][languageId] = val;

	return obj;
};