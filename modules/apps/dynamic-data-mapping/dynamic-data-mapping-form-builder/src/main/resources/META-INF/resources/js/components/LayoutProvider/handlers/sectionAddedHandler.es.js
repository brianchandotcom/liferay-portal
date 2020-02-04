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
	}
	else {
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

const createSection = (props, event, fields) => {
	const {fieldTypes} = props;

	const fieldType = fieldTypes.find(fieldType => {
		return fieldType.name === 'section';
	});

	const sectionField = createField(
		{
			...props,
			nestedFields: fields
		},
		{...event, fieldType}
	);

	let sectionFieldRows = {rows: []};

	fields.reverse().forEach(field => {
		sectionFieldRows = FormSupport.addFieldToColumn(
			[sectionFieldRows],
			0,
			0,
			0,
			field.fieldName
		)[0];
	});

	let focusedField = updateFocusedField(
		props,
		{focusedField: sectionField},
		'nestedFields',
		fields
	);

	focusedField = updateFocusedField(
		props,
		{focusedField},
		'rows',
		sectionFieldRows.rows
	);

	return focusedField;
};

const getColumn = (context, nestedIndexes = []) => {
	const {columnIndex, pageIndex, rowIndex} = nestedIndexes[
		nestedIndexes.length - 1
	];

	return FormSupport.getColumn(
		context,
		nestedIndexes.length > 1 ? 0 : pageIndex,
		rowIndex,
		columnIndex
	);
};

const getContext = (context, nestedIndexes = []) => {
	if (nestedIndexes.length) {
		nestedIndexes.forEach((indexes, i) => {
			const {columnIndex, pageIndex, rowIndex} = indexes;

			let fields =
				context[i > 0 ? 0 : pageIndex].rows[rowIndex].columns[
					columnIndex
				].fields;

			if (context[0].nestedFields) {
				fields = fields.map(field =>
					context[0].nestedFields.find(
						nestedField => nestedField.fieldName === field
					)
				);
			}

			context = fields;
		});
	}

	return context;
};

const getFields = (context, nestedIndexes) => {
	const {columnIndex, pageIndex, rowIndex} = nestedIndexes[
		nestedIndexes.length - 1
	];

	let fields = FormSupport.getColumn(
		context,
		nestedIndexes.length > 1 ? 0 : pageIndex,
		rowIndex,
		columnIndex
	).fields;

	if (context[0].nestedFields) {
		fields = fields.map(field =>
			context[0].nestedFields.find(
				nestedField => nestedField.fieldName === field
			)
		);
	}

	return fields;
};

export default (props, state, event) => {
	const {pages} = state;

	const nestedIndexes = FormSupport.getNestedIndexes(
		event.data.target.parentElement
	);

	const currentContext = getContext(pages, nestedIndexes.slice(0, -1));

	const currentColumn = getColumn(currentContext, nestedIndexes);

	const currentFields = getFields(currentContext, nestedIndexes);

	const newField = createField(props, event);

	const sectionField = createSection(props, event, [
		...currentFields,
		newField
	]);

	if (currentContext[0].nestedFields) {
		let newContext = [...currentContext][0];

		currentFields.forEach(field => {
			newContext.nestedFields.splice(
				newContext.nestedFields.indexOf(field),
				1
			);
		});

		newContext = updateFocusedField(
			props,
			{focusedField: newContext},
			'nestedFields',
			[...newContext.nestedFields, sectionField]
		);

		currentColumn.fields = [sectionField.fieldName];

		newContext = updateFocusedField(
			props,
			{focusedField: newContext},
			'rows',
			newContext.rows
		);

		currentContext[0].nestedFields = newContext.nestedFields;
	}
	else {
		currentColumn.fields = [sectionField];
	}

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
