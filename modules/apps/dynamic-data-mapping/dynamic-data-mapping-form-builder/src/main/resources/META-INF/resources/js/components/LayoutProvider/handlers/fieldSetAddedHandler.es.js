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

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';

import {generateFieldName} from '../util/fields.es';

const handleFieldSetAdded = (props, state, event) => {
	const {fieldSetPages, indexes} = event;
	const {pages} = state;
	const {pageIndex, rowIndex} = indexes;

	const visitor = new PagesVisitor(fieldSetPages);

	const newFieldsetPages = visitor.mapFields(field => {
		const name = generateFieldName(pages, field.fieldName);

		const settingsContextVisitor = new PagesVisitor(
			field.settingsContext.pages
		);

		return {
			...field,
			fieldName: name,
			settingsContext: {
				...field.settingsContext,
				pages: settingsContextVisitor.mapFields(
					settingsContextField => {
						if (settingsContextField.fieldName === 'name') {
							settingsContextField = {
								...settingsContextField,
								value: name
							};
						}

						return settingsContextField;
					}
				)
			}
		};
	});

	const rows = newFieldsetPages[0].rows;

	for (let i = rows.length - 1; i >= 0; i--) {
		pages[pageIndex].rows.splice(rowIndex, 0, rows[i]);
	}

	return {pages};
};

export default handleFieldSetAdded;
