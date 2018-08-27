import { isObject as metalIsObject } from 'metal';

/**
 * Returns a FormData containing serilized object.
 * @param {!Object} obj to serilize
 * @param {FormData=} formData to add the object (it is usually used in recursive)
 * @param {string=} namespace to add nested objects/arrays (it is usually used in recursive)
 * @return {FormData} FormData with the serilized obj
 * @review
 */

export default function objectToFormData(obj = {}, formData = new FormData(), namespace) {
	Object.entries(obj).forEach(([key, value]) => {
		const formKey = namespace
			? `${namespace}[${key}]`
			: key;

		const isObject = metalIsObject(value);
		const isArray = Array.isArray(value);
		const isFile = value instanceof File;

		if (isObject && !isArray && !isFile) {
			objectToFormData(value, formData, key);
		} else if (isArray) {
			value.forEach(item => {
				objectToFormData({ [formKey]: item }, formData);
			});
		} else {
			formData.append(formKey, value);
		}
	});

	return formData;
};