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

import * as FormSupport from 'dynamic-data-mapping-form-renderer/js/components/FormRenderer/FormSupport.es';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';
import dom from 'metal-dom';

import {createField} from '../../../util/fieldSupport.es';
import {updateFocusedField} from '../util/focusedField.es';

const addNestedField = ({field, indexes, nestedField, props}) => {
	let nestedFields = [...(field.nestedFields || []), nestedField];
	let layout = [{rows: field.rows}];

	const existingFieldName =
		layout[indexes.pageIndex].rows[indexes.rowIndex].columns[
			indexes.columnIndex
		].fields[0];

	if (existingFieldName) {
		nestedFields = nestedFields.filter(
			({fieldName}) => fieldName !== existingFieldName
		);

		layout = FormSupport.removeFields(
			layout,
			indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex
		);
	}

	let parentField = updateFocusedField(
		props,
		{focusedField: field},
		'nestedFields',
		nestedFields
	);

	layout = FormSupport.addFieldToColumn(
		layout,
		indexes.pageIndex,
		indexes.rowIndex,
		indexes.columnIndex,
		nestedField.fieldName
	);

	const {rows} = layout[indexes.pageIndex];

	parentField = updateFocusedField(
		props,
		{focusedField: parentField},
		'rows',
		rows
	);

	return {
		...parentField,
		nestedFields,
		rows
	};
};

const createSection = (props, event, nestedField) => {
	const {fieldTypes} = props;
	const fieldType = fieldTypes.find(fieldType => {
		return fieldType.name === 'section';
	});
	const sectionField = createField(props, {...event, fieldType});

	return addNestedField({
		field: {
			...sectionField,
			rows: [{columns: [{fields: [], size: 12}]}]
		},
		indexes: {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0
		},
		nestedField,
		props
	});
};

export default (props, state, event) => {
	const {data, indexes} = event;
	const {pages} = state;

	const visitor = new PagesVisitor(pages);

	const {target} = data;

	const {fieldName} = target.dataset;

	const newField = event.newField || createField(props, event);

	const sectionField = createSection(props, event, newField);

	const parentFieldNode = dom.closest(target.parentElement, '.ddm-field');

	let parentFieldName;

	if (parentFieldNode) {
		parentFieldName = parentFieldNode.dataset.fieldName;
	}

	return {
		focusedField: {
			...sectionField,
			...indexes
		},
		pages: visitor.mapFields(
			field => {
				if (field.fieldName === fieldName) {
					return sectionField;
				} else if (field.fieldName === parentFieldName) {
					return addNestedField({
						field,
						indexes,
						nestedField: sectionField,
						props
					});
				}

				return field;
			},
			true,
			true
		),
		previousFocusedField: sectionField
	};
};
