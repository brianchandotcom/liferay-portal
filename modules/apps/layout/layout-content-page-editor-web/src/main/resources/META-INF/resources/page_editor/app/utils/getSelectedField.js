/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const cache = {};

/**
 * Returns the selected field from the given key or return null.
 * This also check try to add a prefix to look for a specific field since older
 * Liferay versions didn't prefix ddm structure and vocabulary fields.
 *
 * @param {object} data
 * @param {Array}: data.fields Array of available fields for a given info type.
 * @param {string}: [data.mappingFieldsKey] Key used to store the mapping fields.
 * @param {string}: data.value Field key.
 * @return {object}
 */
export default function getSelectedField({fields, mappingFieldsKey, value}) {
	if (!value || !fields?.length) {
		return null;
	}

	const cacheKey = `${mappingFieldsKey}${value}`;

	if (mappingFieldsKey && cache[cacheKey]) {
		return cache[cacheKey];
	}

	const flattenField = fields.flatMap((fieldSet) => fieldSet.fields);

	const selectedField =
		flattenField.find((field) => field.key === value) ||
		flattenField.find((field) => field.name === value);

	if (selectedField) {
		if (mappingFieldsKey) {
			cache[cacheKey] = selectedField;
		}

		return selectedField;
	}

	return null;
}
