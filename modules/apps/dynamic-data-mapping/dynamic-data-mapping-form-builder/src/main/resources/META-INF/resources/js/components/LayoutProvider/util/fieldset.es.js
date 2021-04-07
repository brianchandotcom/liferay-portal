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
import {createField, generateInstanceId} from '../../../util/fieldSupport.es';
import {updateField} from '../util/settingsContext.es';

const addNestedFields = ({field, indexes, nestedFields, props}) => {
	let layout = [{rows: field.rows}];
	const visitor = new PagesVisitor(layout);

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (
			!nestedFields.some(
				(nestedField) => nestedField.fieldName === field.fieldName
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

	[...nestedFields].reverse().forEach((nestedField) => {
		if (!nestedField.instanceId) {
			nestedField.instanceId = generateInstanceId(8);
		}
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

export const createFieldSet = (
	props,
	event,
	nestedFields,
	rows = [{columns: [{fields: [], size: 12}]}]
) => {
	const {fieldTypes} = props;
	const fieldType = fieldTypes.find((fieldType) => {
		return fieldType.name === FIELD_TYPE_FIELDSET;
	});
	const fieldSetField = createField(props, {...event, fieldType});

	return addNestedFields({
		field: {
			...fieldSetField,
			rows,
			style: {},
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
