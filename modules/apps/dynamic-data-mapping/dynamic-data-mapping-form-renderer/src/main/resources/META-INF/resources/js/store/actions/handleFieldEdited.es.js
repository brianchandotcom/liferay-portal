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

let REVALIDATE_UPDATES = [];

const getEditedPages = ({editingLanguageId, name, pages, value}) => {
	const pageVisitor = new PagesVisitor(pages);

	return pageVisitor.mapFields(
		(field) => {
			if (field.name === name) {
				return {
					...field,
					localizedValue: {
						...field.localizedValue,
						[editingLanguageId]: value,
					},
					value,
				};
			}

			return field;
		},
		false,
		true
	);
};

export default (evaluatorContext, properties, updateState) => {
	const {fieldInstance, value} = properties;
	const {evaluable, fieldName} = fieldInstance;
	const {editingLanguageId, pages} = evaluatorContext;

	const editedPages = getEditedPages({
		editingLanguageId,
		name: fieldInstance.name,
		pages,
		value,
	});

	updateState(editedPages);

	let promise = Promise.resolve(editedPages);

	if (evaluable) {
		promise = evaluate(fieldName, {
			...evaluatorContext,
			pages: editedPages,
		}).then((evaluatedPages) => {
			if (REVALIDATE_UPDATES.length > 0) {
				// All non-evaluable operations that were performed after the request
				// was sent are used here to revalidate the new data.

				REVALIDATE_UPDATES.forEach((item) => {
					evaluatedPages = getEditedPages({
						...item,
						pages: evaluatedPages,
					});
				});

				// Redefine the list of updates to avoid leaking memory and avoid
				// more expensive operations in the next interactions

				REVALIDATE_UPDATES = [];
			}

			return evaluatedPages;
		});
	}
	else {
		REVALIDATE_UPDATES.push({
			editingLanguageId,
			name: fieldInstance.name,
			value,
		});
	}

	return promise;
};
