import {capitalize} from './strings.es';

export const setValue = (obj, languageId, prop, val) => {
	if (!obj[prop]) {
		obj[prop] = {};
	}

	obj[prop][languageId] = val;

	return obj;
};

export const setLocalizedValue = (obj, languageId, prop, val) => {
	obj[prop] = val;

	const localizedProperty = `localized${capitalize(prop)}`;

	return setValue(obj, languageId, localizedProperty, val);
};
