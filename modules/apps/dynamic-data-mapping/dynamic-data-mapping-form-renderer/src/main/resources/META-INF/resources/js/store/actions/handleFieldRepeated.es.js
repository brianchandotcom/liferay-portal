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

import {generateName, generateNestedFieldName} from '../../util/repeatable.es';
import {PagesVisitor} from '../../util/visitors.es';

export default (pages, name) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapColumns(column => {
		const {fields} = column;
		const currentIndex = fields.reduce(
			(currentIndex = -1, field, index) => {
				if (field.name === name) {
					currentIndex = index;
				}

				return currentIndex;
			},
			-1
		);

		if (currentIndex > -1) {
			const field = fields[currentIndex];
			const indexToAddField = currentIndex + 1;
			const newField = {
				...field,
				name: generateName(name, indexToAddField)
			};

			newField.localizedValue = {};

			delete newField.value;

			const newFields = [
				...fields.slice(0, indexToAddField),
				newField,
				...fields.slice(indexToAddField)
			];

			let currentRepeatedIndex = 0;

			column = {
				...column,
				fields: newFields.map((currentField, index) => {
					if (currentField.fieldName === newField.fieldName) {
						const name = generateName(
							currentField.name,
							currentRepeatedIndex++
						);

						currentField = {
							...currentField,
							name
						};

						if (currentField.nestedFields) {
							currentField = {
								...currentField,
								nestedFields: currentField.nestedFields.map(
									nestedField => {
										const newNestedField = {
											...nestedField,
											name: generateNestedFieldName(
												nestedField.name,
												name
											)
										};

										if (index === indexToAddField) {
											newField.localizedValue = {};

											delete newNestedField.value;
										}

										return newNestedField;
									}
								)
							};
						}
					}

					return currentField;
				})
			};
		}

		return column;
	});
};
