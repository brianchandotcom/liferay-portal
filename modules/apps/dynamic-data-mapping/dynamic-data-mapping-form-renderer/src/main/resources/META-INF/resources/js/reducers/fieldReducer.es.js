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

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {
	generateInstanceId,
	generateName,
	generateNestedFieldName,
	parseNestedFieldName,
} from '../util/repeatable.es';
import {PagesVisitor} from '../util/visitors.es';

export const createRepeatedField = (sourceField, repeatedIndex) => {
	const instanceId = generateInstanceId();

	let localizedValue;

	if (sourceField.localizedValue) {
		localizedValue = Object.keys(sourceField.localizedValue).reduce(
			(localizedValues, key) => {
				localizedValues[key] = '';

				return localizedValues;
			},
			{}
		);
	}

	return {
		...sourceField,
		instanceId,
		localizedValue,
		name: generateName(sourceField.name, {instanceId, repeatedIndex}),
		nestedFields: (
			sourceField.nestedFields || []
		).map((nestedField, index) =>
			createRepeatedField(nestedField, repeatedIndex + index)
		),
		value: sourceField.predefinedValue,
	};
};

export const updateNestedFieldNames = (parentFieldName, nestedFields) => {
	return (nestedFields || []).map((nestedField) => {
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

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.FIELD_BLUR: {
			const {fieldInstance} = action.payload;
			const pageVisitor = new PagesVisitor(state.pages);

			return {
				pages: pageVisitor.mapFields((field) => {
					const matches =
						field.name === fieldInstance.name &&
						field.required &&
						fieldInstance.value == '';

					return {
						...field,
						displayErrors: !!field.displayErrors || matches,
						focused: matches ? false : field.focused,
					};
				}),
			};
		}
		case EVENT_TYPES.FIELD_FOCUS: {
			const {fieldInstance} = action.payload;
			const pageVisitor = new PagesVisitor(state.pages);

			return {
				pages: pageVisitor.mapFields((field) => {
					const focused = field.name === fieldInstance.name;

					return {
						...field,
						focused,
					};
				}),
			};
		}
		case EVENT_TYPES.FIELD_REMOVED: {
			const pageVisitor = new PagesVisitor(state.pages);

			return {
				pages: pageVisitor.mapColumns((column) => {
					const filter = (fields) =>
						fields
							.filter((field) => field.name !== action.payload)
							.map((field) => {
								return {
									...field,
									nestedFields: field.nestedFields
										? filter(field.nestedFields)
										: [],
								};
							});

					return {
						...column,
						fields: filter(column.fields),
					};
				}),
			};
		}
		case EVENT_TYPES.FIELD_REPEATED: {
			const pageVisitor = new PagesVisitor(state.pages);

			return {
				pages: pageVisitor.mapColumns((column) => {
					const addRepeatedField = (fields) => {
						const sourceFieldIndex = fields.reduce(
							(sourceFieldIndex = -1, field, index) => {
								if (field.name === action.payload) {
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

							return [
								...fields.slice(0, newFieldIndex),
								newField,
								...fields.slice(newFieldIndex),
							].map((currentField) => {
								if (
									currentField.fieldName ===
									newField.fieldName
								) {
									const name = generateName(
										currentField.name,
										{
											repeatedIndex: currentRepeatedIndex++,
										}
									);

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
							});
						}

						return fields.map((field) => {
							return {
								...field,
								nestedFields: field.nestedFields
									? addRepeatedField(field.nestedFields)
									: [],
							};
						});
					};

					return {
						...column,
						fields: addRepeatedField(column.fields),
					};
				}),
			};
		}
		default:
			return state;
	}
};
