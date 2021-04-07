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

import {FormSupport, PagesVisitor} from 'data-engine-js-components-web';

import {createDuplicatedField} from '../util/fields.es';
import {updateField} from '../util/settingsContext.es';

export const duplicateField = (
	activePage,
	props,
	pages,
	originalField,
	duplicatedField
) => {
	const visitor = new PagesVisitor(pages);

	const parentField = FormSupport.getParentField(
		pages,
		originalField.fieldName
	);

	if (parentField) {
		return visitor.mapFields(
			(field) => {
				if (field.fieldName === parentField.fieldName) {
					const nestedFields = field.nestedFields
						? [...field.nestedFields, duplicatedField]
						: [duplicatedField];

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

					let pages = [{rows}];

					const {rowIndex} = FormSupport.getFieldIndexes(
						pages,
						originalField.fieldName
					);

					const newRow = FormSupport.implAddRow(12, [
						duplicatedField.fieldName,
					]);

					pages = FormSupport.addRow(
						pages,
						rowIndex + 1,
						activePage,
						newRow
					);

					return updateField(props, field, 'rows', pages[0].rows);
				}

				return field;
			},
			true,
			true
		);
	}

	const {rowIndex} = FormSupport.getFieldIndexes(
		pages,
		originalField.fieldName
	);

	const newRow = FormSupport.implAddRow(12, [duplicatedField]);

	return FormSupport.addRow(pages, rowIndex + 1, activePage, newRow);
};

const handleFieldDuplicated = (props, state, {activePage, fieldName}) => {
	const {pages} = state;

	if (activePage === undefined) {
		activePage = state.activePage;
	}

	const originalField = JSON.parse(
		JSON.stringify(FormSupport.findFieldByFieldName(pages, fieldName))
	);

	const duplicatedField = createDuplicatedField(originalField, props);

	return {
		focusedField: {
			...duplicatedField,
		},
		pages: duplicateField(
			activePage,
			props,
			pages,
			originalField,
			duplicatedField
		),
	};
};

export default handleFieldDuplicated;
