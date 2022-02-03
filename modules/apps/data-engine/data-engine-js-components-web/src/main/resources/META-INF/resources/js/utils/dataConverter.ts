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

// @ts-ignore

import {PagesVisitor} from './visitors.es';

export const DEFAULT_DDM_FIELD_PROPERTIES = new Set([
	'defaultValue',
	'fieldType',
	'indexable',
	'indexType',
	'label',
	'localizable',
	'name',
	'readOnly',
	'repeatable',
	'required',
	'showLabel',
	'tip',
]);

/**
 * Gets a data definition from a field
 *
 * @param {object} field - The field
 * @param {Object[]} field.nestedFields - The array containing all nested fields.
 * 										  It may be undefined
 * @param {object} field.settingsContext - The settings context of a field
 */
export function fieldToDataDefinition({
	nestedFields = [],
	settingsContext,
}: Field) {
	const dataDefinition: DataDefinition = {
		customProperties: {},
		nestedDataDefinitionFields: nestedFields.map((field) =>
			fieldToDataDefinition(field)
		),
	};
	const settingsContextVisitor = new PagesVisitor(settingsContext.pages);

	settingsContextVisitor.mapFields(
		({fieldName, localizable, localizedValue = {}, value}: Field) => {
			if (fieldName === 'predefinedValue') {
				fieldName = 'defaultValue';
			}
			else if (fieldName === 'type') {
				fieldName = 'fieldType';
			}

			const properties = DEFAULT_DDM_FIELD_PROPERTIES.has(fieldName)
				? dataDefinition
				: dataDefinition.customProperties;

			// @ts-ignore

			properties[fieldName] = localizable ? localizedValue : value;
		},
		false
	);

	return dataDefinition;
}
