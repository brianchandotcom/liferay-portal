/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getFieldsFromSchema = (schema) => {
	const dbFields = {
		optional: [],
		required: [],
	};

	if (!schema) {
		return dbFields;
	}

	schema.forEach((field) => {
		if (field.required) {
			dbFields.required.push(field);
		}
		else {
			dbFields.optional.push(field);
		}
	});

	return dbFields;
};

export default getFieldsFromSchema;
