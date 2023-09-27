/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addFieldToColumn, removeFields} from './FormSupport.es';
import {FIELD_TYPE_FIELDSET} from './constants';
import {createField, generateInstanceId} from './fieldSupport';
import {updateField} from './settingsContext';
import {PagesVisitor} from './visitors.es';

const addNestedFields = ({field, indexes, nestedFields, props}) => {
	let layout = [{rows: field.rows}];
	const visitor = new PagesVisitor(layout);

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (
			!nestedFields.some(
				(nestedField) => nestedField.fieldName === field.fieldName
			)
		) {
			layout = removeFields(layout, pageIndex, rowIndex, columnIndex);
		}
	});

	[...nestedFields].reverse().forEach((nestedField) => {
		if (!nestedField.instanceId) {
			nestedField.instanceId = generateInstanceId();
		}
		layout = addFieldToColumn(
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

export function createFieldSet(
	props,
	event,
	nestedFields,
	rows = [{columns: [{fields: [], size: 12}]}]
) {
	const {
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		fieldTypes,
		portletNamespace,
	} = props;
	const {skipFieldNameGeneration, useFieldName} = event;

	const fieldSetField = createField({
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		fieldType: fieldTypes.find(({name}) => name === FIELD_TYPE_FIELDSET),
		portletNamespace,
		skipFieldNameGeneration,
		useFieldName,
	});

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
}
