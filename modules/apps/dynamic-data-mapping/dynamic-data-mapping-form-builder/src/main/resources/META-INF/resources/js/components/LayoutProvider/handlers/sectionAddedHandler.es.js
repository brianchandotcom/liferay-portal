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

import {FIELD_TYPE_FIELDSET} from '../../../util/constants.es';
import {addField, createField} from '../../../util/fieldSupport.es';
import {createFieldSet} from '../util/fieldset.es';
import {updateField} from '../util/settingsContext.es';
import handleFieldDeleted from './fieldDeletedHandler.es';

const addNestedField = ({field, indexes, nestedField, props}) => {
	const layout = FormSupport.addFieldToColumn(
		[{rows: field.rows}],
		indexes.pageIndex,
		indexes.rowIndex,
		indexes.columnIndex,
		nestedField.fieldName
	);
	const nestedFields = [...field.nestedFields, nestedField];

	field = updateField(props, field, 'nestedFields', nestedFields);

	const {rows} = layout[indexes.pageIndex];

	field = updateField(props, field, 'rows', rows);

	return {
		...field,
		nestedFields,
		rows,
	};
};

const handleSectionAdded = (props, state, event) => {
	const {data, indexes} = event;
	const {fieldName, parentFieldName} = data;
	const {pages} = state;

	const newField = event.newField ?? createField(props, event);
	const existingField = FormSupport.findFieldByFieldName(pages, fieldName);
	const fieldSetField = createFieldSet(props, event, [
		existingField,
		newField,
	]);

	const visitor = new PagesVisitor(pages);

	let newPages;

	if (parentFieldName) {
		newPages = visitor.mapFields(
			(field) => {
				if (field.fieldName === parentFieldName) {
					const updatedParentField = FormSupport.findFieldByFieldName(
						handleFieldDeleted(props, state, {
							fieldName,
						}).pages,
						parentFieldName
					);

					return addNestedField({
						field: updatedParentField,
						indexes: {
							...indexes,
							pageIndex: 0,
						},
						nestedField: fieldSetField,
						props,
					});
				}

				return field;
			},
			false,
			true
		);
	}
	else if (existingField.type === FIELD_TYPE_FIELDSET) {
		newPages = addField({
			...props,
			indexes,
			newField,
			pages,
			parentFieldName: existingField.fieldName,
		}).pages;
	}
	else {
		newPages = visitor.mapFields((field) => {
			if (field.fieldName === fieldName) {
				return fieldSetField;
			}

			return field;
		});
	}

	return {
		focusedField: {
			...newField,
		},
		pages: newPages,
		previousFocusedField: fieldSetField,
	};
};

export default handleSectionAdded;
