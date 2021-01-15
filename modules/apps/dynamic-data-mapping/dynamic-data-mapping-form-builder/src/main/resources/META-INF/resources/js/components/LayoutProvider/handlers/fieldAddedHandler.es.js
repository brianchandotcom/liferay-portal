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

import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';

import {createField} from '../../../util/fieldSupport.es';
import {updateField} from '../util/settingsContext.es';

export const addField = (
	props,
	{indexes, newField, pages, parentFieldName}
) => {
	const {columnIndex, pageIndex, rowIndex} = indexes;

	let newPages;

	if (parentFieldName) {
		const visitor = new PagesVisitor(pages);

		newPages = visitor.mapFields(
			(field) => {
				if (field.fieldName === parentFieldName) {
					const nestedFields = field.nestedFields
						? [...field.nestedFields, newField]
						: [newField];

					field = updateField(
						props,
						field,
						'nestedFields',
						nestedFields
					);

					let {rows} = field;

					if (typeof rows === 'string') {
						rows = JSON.parse(rows);
					}

					const pages = FormSupport.addFieldToColumn(
						[{rows}],
						0,
						rowIndex,
						columnIndex,
						newField.fieldName
					);

					return updateField(props, field, 'rows', pages[0].rows);
				}

				return field;
			},
			true,
			true
		);
	}
	else {
		newPages = FormSupport.addFieldToColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex,
			newField
		);
	}

	return {
		activePage: pageIndex,
		focusedField: {
			...newField,
		},
		pages: newPages,
		previousFocusedField: newField,
	};
};

const handleFieldAdded = (props, state, event) => {
	const {data, indexes} = event;
	const {pages} = state;
	const {parentFieldName} = data;

	const newField = createField(props, event);

	return addField(props, {
		indexes,
		newField,
		pages,
		parentFieldName,
	});
};

export default handleFieldAdded;
