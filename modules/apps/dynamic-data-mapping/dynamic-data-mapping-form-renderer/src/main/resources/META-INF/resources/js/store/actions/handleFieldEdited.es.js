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

import {evaluate} from '../../util/evaluation.es';
import {PagesVisitor} from '../../util/visitors.es';

export default (evaluatorContext, properties) => {
	const {fieldInstance, value} = properties;
	const {evaluable, fieldName} = fieldInstance;
	const {editingLanguageId, pages} = evaluatorContext;
	const pageVisitor = new PagesVisitor(pages);

	const editedPages = pageVisitor.mapFields(field => {
		if (field.name === fieldInstance.name) {
			field = {
				...field,
				localizedValue: {
					...field.localizedValue,
					[editingLanguageId]: value
				},
				value
			};
		} else if (field.nestedFields) {
			field = {
				...field,
				nestedFields: field.nestedFields.map(nestedField => {
					if (nestedField.name === fieldInstance.name) {
						nestedField = {
							...nestedField,
							localizedValue: {
								...field.localizedValue,
								[editingLanguageId]: value
							},
							value
						};
					}

					return nestedField;
				})
			};
		}

		return field;
	});

	let promise = Promise.resolve(editedPages);

	if (evaluable) {
		promise = evaluate(fieldName, {
			...evaluatorContext,
			pages: editedPages
		});
	}

	return promise;
};
