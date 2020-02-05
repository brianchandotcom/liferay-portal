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

import {
	generateInstanceId,
	getFieldProperties,
	normalizeSettingsContextPages,
} from '../../../util/fieldSupport.es';
import {updateFocusedField} from '../util/focusedField.es';

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

const handleFieldAdded = (props, state, event) => {
	const {fieldType, indexes, skipFieldNameGeneration = false} = event;
	const {
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		spritemap,
	} = props;
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
			type: fieldType.name,
		},
	};

	const {pages} = state;
	const {fieldName, name, settingsContext} = focusedField;

	const fieldProperties = {
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
		type: fieldType.name,
	};

	if (!indexes.length || indexes.length === 1) {
		const {columnIndex, pageIndex, rowIndex} = indexes.length
			? indexes[0]
			: indexes;

		return {
			focusedField: {
				...fieldProperties,
				columnIndex,
				pageIndex,
				rowIndex
			},
			pages: FormSupport.addFieldToColumn(
				pages,
				pageIndex,
				rowIndex,
				columnIndex,
				fieldProperties
			),
			previousFocusedField: fieldProperties
		};
	} else {
		const currentContext = getContext(pages, indexes.slice(0, -1));

		const {columnIndex, rowIndex} = indexes[indexes.length - 1];

		let newContext = FormSupport.addFieldToColumn(
			currentContext,
			0,
			rowIndex,
			columnIndex,
			fieldProperties.fieldName
		)[0];

		newContext = updateFocusedField(
			props,
			{focusedField: newContext},
			'nestedFields',
			[...newContext.nestedFields, fieldProperties]
		);

		newContext = updateFocusedField(
			props,
			{focusedField: newContext},
			'rows',
			newContext.rows
		);

		currentContext[0].rows = newContext.rows;
		currentContext[0].settingsContext = newContext.settingsContext;
		currentContext[0].nestedFields = newContext.nestedFields;

		return {
			focusedField: {
				...fieldProperties,
				columnIndex,
				pageIndes: 0,
				rowIndex
			},
			pages,
			previousFocusedField: fieldProperties
		};
	}
};

export default handleFieldAdded;
