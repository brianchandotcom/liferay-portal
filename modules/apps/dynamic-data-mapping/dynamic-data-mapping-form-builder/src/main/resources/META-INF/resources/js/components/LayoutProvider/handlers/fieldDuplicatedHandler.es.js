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

import {sub} from '../../../util/strings.es';
import {getFieldLocalizedValue} from '../util/fields.es';
import {
	getSettingsContextProperty,
	updateField,
} from '../util/settingsContext.es';

export const createDuplicatedField = (originalField, props) => {
	const {editingLanguageId, fieldNameGenerator} = props;
	const label = getLabel(originalField, editingLanguageId);
	const newFieldName = fieldNameGenerator(label);
	const oldFieldName = originalField.fieldName;

	let duplicatedField = updateField(
		props,
		originalField,
		'name',
		newFieldName
	);

	duplicatedField = updateField(props, duplicatedField, 'label', label);

	return updateField(
		props,
		duplicatedField,
		'validation',
		getValidation(duplicatedField, oldFieldName, newFieldName)
	);
};

export const getLabel = (originalField, editingLanguageId) => {
	return sub(Liferay.Language.get('copy-of-x'), [
		getFieldLocalizedValue(
			originalField.settingsContext.pages,
			'label',
			editingLanguageId
		),
	]);
};

export const getValidation = (originalField, oldFieldName, newFieldName) => {
	const validation = getSettingsContextProperty(
		originalField.settingsContext,
		'validation'
	);

	const expression = validation.expression;

	if (expression && expression.value) {
		return {
			...validation,
			expression: {
				...validation.expression,
				value: expression.value.replace(oldFieldName, newFieldName),
			},
		};
	}

	return validation;
};

export const duplicateField = (
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
			field => {
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

					let pages = [{rows: field.rows}];

					const {rowIndex} = FormSupport.getFieldIndexes(
						pages,
						originalField.fieldName
					);

					const newRow = FormSupport.implAddRow(12, [
						duplicatedField.fieldName,
					]);

					pages = FormSupport.addRow(pages, rowIndex + 1, 0, newRow);

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

	return FormSupport.addRow(pages, rowIndex + 1, 0, newRow);
};

const handleFieldDuplicated = (props, state, event) => {
	const {fieldName} = event;
	const {pages} = state;

	const originalField = FormSupport.findFieldByFieldName(pages, fieldName);

	const duplicatedField = createDuplicatedField(originalField, props);

	return {
		focusedField: {
			...duplicatedField,
		},
		pages: duplicateField(props, pages, originalField, duplicatedField),
	};
};

export default handleFieldDuplicated;
