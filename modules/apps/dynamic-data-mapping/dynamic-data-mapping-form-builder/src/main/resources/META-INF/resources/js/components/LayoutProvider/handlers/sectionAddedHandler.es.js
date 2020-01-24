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

import {
	generateInstanceId,
	getFieldProperties,
	normalizeSettingsContextPages
} from '../../../util/fieldSupport.es';
import {updateFocusedField} from '../util/focusedField.es';

const createField = (props, event) => {
	const {
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		spritemap
	} = props;
	const {fieldType, skipFieldNameGeneration = false} = event;

	let newFieldName;

	if (skipFieldNameGeneration) {
		const {settingsContext} = fieldType;
		const visitor = new PagesVisitor(settingsContext.pages);

		visitor.mapFields(({fieldName, value}) => {
			if (fieldName === 'name') {
				newFieldName = value;
			}
		});
	} else {
		newFieldName = fieldNameGenerator(fieldType.label);
	}

	const focusedField = {
		...fieldType,
		fieldName: newFieldName,
		settingsContext: {
			...fieldType.settingsContext,
			pages: normalizeSettingsContextPages(
				fieldType.settingsContext.pages,
				editingLanguageId,
				fieldType,
				newFieldName
			),
			type: fieldType.name
		}
	};

	const {fieldName, name, settingsContext} = focusedField;

	return {
		...getFieldProperties(
			settingsContext,
			defaultLanguageId,
			editingLanguageId
		),
		fieldName,
		instanceId: generateInstanceId(8),
		name,
		settingsContext,
		spritemap,
		type: fieldType.name
	};
};

const createSection = (props, event, sectionFields) => {
	const {fieldTypes} = props;

	const fieldType = fieldTypes.find(fieldType => {
		return fieldType.name === 'section';
	});

	const sectionField = createField(props, {...event, fieldType});

	let sectionFieldRows = {rows: []};

	sectionFields.reverse().forEach(field => {
		sectionFieldRows = FormSupport.addFieldToColumn(
			[sectionFieldRows],
			0,
			0,
			0,
			field
		)[0];
	});

	return updateFocusedField(
		props,
		{focusedField: sectionField},
		'rows',
		sectionFieldRows.rows
	);
};

const getColumn = (pages, nestedIndexes = []) => {
	let column;
	let context = pages;

	nestedIndexes.forEach(indexes => {
		const {columnIndex, pageIndex, rowIndex} = indexes;

		column = FormSupport.getColumn(
			context,
			column ? 0 : pageIndex,
			rowIndex,
			columnIndex
		);

		context = column.fields;
	});

	return column;
};

export default (props, state, event) => {
	const {pages} = state;

	const nestedIndexes = FormSupport.getNestedIndexes(
		event.data.target.parentElement
	);

	const targetColumn = getColumn(pages, nestedIndexes);

	const newField = createField(props, event);

	const sectionField = createSection(props, event, [
		...targetColumn.fields,
		newField
	]);

	targetColumn.fields = [sectionField];

	const {columnIndex, fieldIndex, pageIndex, rowIndex} = nestedIndexes.pop();

	return {
		focusedField: {
			...sectionField,
			columnIndex,
			fieldIndex,
			pageIndex,
			rowIndex
		},
		pages,
		previousFocusedField: sectionField
	};
};
