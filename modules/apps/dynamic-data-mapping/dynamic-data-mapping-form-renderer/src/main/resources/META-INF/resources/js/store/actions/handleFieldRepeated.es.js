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

import {
	generateInstanceId,
	generateName,
	generateNestedFieldName,
	parseNestedFieldName,
} from '../../util/repeatable.es';
import {PagesVisitor} from '../../util/visitors.es';

export const createRepeatedField = (sourceField, repeatedIndex) => {
	const instanceId = generateInstanceId();

	return {
		...sourceField,
		instanceId,
		localizedValue: {},
		name: generateName(sourceField.name, {instanceId, repeatedIndex}),
		nestedFields: (sourceField.nestedFields || []).map(nestedField => ({
			...nestedField,
			localizedValue: {},
			value: undefined,
		})),
		value: undefined,
	};
};

export const updateNestedFieldNames = (parentFieldName, nestedFields) => {
	return (nestedFields || []).map(nestedField => {
		const newNestedFieldName = generateNestedFieldName(
			nestedField.name,
			parentFieldName
		);

		return {
			...nestedField,
			name: newNestedFieldName,
			nestedFields: updateNestedFieldNames(
				newNestedFieldName,
				nestedField.nestedFields
			),
			...parseNestedFieldName(newNestedFieldName),
		};
	});
};

export default (pages, name) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapColumns(column => {
		const {fields} = column;
		const sourceFieldIndex = fields.reduce(
			(sourceFieldIndex = -1, field, index) => {
				if (field.name === name) {
					sourceFieldIndex = index;
				}

				return sourceFieldIndex;
			},
			-1
		);

		if (sourceFieldIndex > -1) {
			const newFieldIndex = sourceFieldIndex + 1;
			const newField = createRepeatedField(
				fields[sourceFieldIndex],
				newFieldIndex
			);

			let currentRepeatedIndex = 0;

			return {
				...column,
				fields: [
					...fields.slice(0, newFieldIndex),
					newField,
					...fields.slice(newFieldIndex),
				].map(currentField => {
					if (currentField.fieldName === newField.fieldName) {
						const name = generateName(currentField.name, {
							repeatedIndex: currentRepeatedIndex++,
						});

						return {
							...currentField,
							name,
							nestedFields: updateNestedFieldNames(
								name,
								currentField.nestedFields
							),
						};
					}

					return currentField;
				}),
			};
		}

		return column;
	});
};
