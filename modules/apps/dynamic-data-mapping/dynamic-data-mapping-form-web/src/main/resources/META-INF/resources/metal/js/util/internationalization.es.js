import {capitalize} from './string-format.es';

export const setLocalizedValue = (obj, languageId, prop, val) => {
	obj[prop] = val;

	let localizedProperty = `localized${capitalize(prop)}`;

	if (!obj[localizedProperty]) {
		obj[localizedProperty] = {};
	}

	obj[localizedProperty][languageId] = val;

	return obj;
};
