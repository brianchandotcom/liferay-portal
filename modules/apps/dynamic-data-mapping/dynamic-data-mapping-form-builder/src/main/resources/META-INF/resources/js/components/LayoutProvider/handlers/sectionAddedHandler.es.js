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
import {updateField} from '../util/settingsContext.es';

const removeNestedField = ({field, nestedField, props}) => {
	let layout = [{rows: field.rows}];
	const visitor = new PagesVisitor(layout);

	let indexesToRemove = {};

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (field.fieldName === nestedField.fieldName) {
			indexesToRemove = {columnIndex, pageIndex, rowIndex};
		}
	});

	layout = FormSupport.removeFields(
		layout,
		indexesToRemove.pageIndex,
		indexesToRemove.rowIndex,
		indexesToRemove.columnIndex
	);

	const nestedFields = field.nestedFields.filter(
		({fieldName}) => fieldName !== nestedField.fieldName
	);

	field = updateField(props, field, 'nestedFields', nestedFields);

	const {rows} = layout[0];

	field = updateField(props, field, 'rows', rows);

	return {
		...field,
		nestedFields,
		rows,
	};
};

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

const addNestedFields = ({field, indexes, nestedFields, props}) => {
	let layout = [{rows: field.rows}];
	const visitor = new PagesVisitor(layout);

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (
			!nestedFields.some(
				nestedField => nestedField.fieldName === field.fieldName
			)
		) {
			layout = FormSupport.removeFields(
				layout,
				pageIndex,
				rowIndex,
				columnIndex
			);
		}
	});

	[...nestedFields].reverse().forEach(nestedField => {
		layout = FormSupport.addFieldToColumn(
			layout,
			indexes.pageIndex,
			indexes.rowIndex,
			indexes.columnIndex,
			nestedField.fieldName
		);
	});

	field = updateField(props, field, 'nestedFields', nestedFields);

	const {rows} = layout[indexes.pageIndex];

	return {
		...updateField(props, field, 'rows', rows),
		nestedFields,
		rows,
	};
};

const createSection = (props, event, nestedFields) => {
	const {fieldTypes} = props;
	const fieldType = fieldTypes.find(fieldType => {
		return fieldType.name === 'section';
	});
	const sectionField = createField(props, {...event, fieldType});

	return addNestedFields({
		field: {
			...sectionField,
			rows: [{columns: [{fields: [], size: 12}]}],
		},
		indexes: {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0,
		},
		nestedFields,
		props,
	});
};

export default (props, state, event) => {
	const {data, indexes} = event;
	const {target} = data;

	const {fieldName} = target.dataset;
	const {pages} = state;

	const newField = event.newField || createField(props, event);
	const existingField = FormSupport.findFieldByName(pages, fieldName);
	const sectionField = createSection(props, event, [existingField, newField]);

	const parentFieldNode = dom.closest(target.parentElement, '.ddm-field');
	let parentFieldName;

	if (parentFieldNode) {
		parentFieldName = parentFieldNode.dataset.fieldName;
	}

	const visitor = new PagesVisitor(pages);

	let modified = false;

	const newState = {
		focusedField: {
			...newField,
		},
		pages: visitor.mapFields(
			field => {
				if (field.fieldName === fieldName && !modified) {
					modified = true;

					return sectionField;
				}
				else if (field.fieldName === parentFieldName) {
					const newParentField = removeNestedField({
						field,
						nestedField: existingField,
						props,
					});

					return addNestedField({
						field: newParentField,
						indexes,
						nestedField: sectionField,
						props,
					});
				}

				return field;
			},
			true,
			true
		),
		previousFocusedField: sectionField,
	};

	return newState;
};
