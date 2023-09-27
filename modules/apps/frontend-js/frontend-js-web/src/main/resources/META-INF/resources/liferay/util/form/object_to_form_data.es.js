/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isObject from './../is_object';

/**
 * Returns a FormData containing serialized object.
 * @param {!Object} obj Object to convert to a FormData
 * @param {FormData=} formData FormData object to recursively append the serialized data
 * @param {string=} namespace Property namespace for nested objects or arrays
 * @return {FormData} FormData with the serialized object
 * @review
 */

export default function objectToFormData(
	object = {},
	formData = new FormData(),
	namespace
) {
	Object.entries(object).forEach(([key, value]) => {
		const formKey = namespace ? `${namespace}[${key}]` : key;

		if (Array.isArray(value)) {
			value.forEach((item) => {
				objectToFormData(
					{
						[formKey]: item,
					},
					formData
				);
			});
		}
		else if (isObject(value) && !(value instanceof File)) {
			objectToFormData(value, formData, formKey);
		}
		else {
			formData.append(formKey, value);
		}
	});

	return formData;
}
