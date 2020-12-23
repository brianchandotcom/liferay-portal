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

import {
	FormSupport,
	PagesVisitor,
	generateInstanceId,
} from 'dynamic-data-mapping-form-renderer';

import {getDefaultFieldName} from '../../../util/fieldSupport.es';
import {sub} from '../../../util/strings.es';
import {getFieldLocalizedValue} from '../util/fields.es';
import {
	getSettingsContextProperty,
	updateField,
	updateFieldLabel,
	updateSettingsContextInstanceId,
	updateSettingsContextProperty,
} from '../util/settingsContext.es';

export const getLabel = (
	originalField,
	defaultLanguageId,
	editingLanguageId
) => {
	const labelFieldLocalizedValue = getFieldLocalizedValue(
		originalField.settingsContext.pages,
		'label',
		editingLanguageId
	);

	if (!labelFieldLocalizedValue) {
		return;
	}

	return sub(Liferay.Language.get('copy-of-x'), [labelFieldLocalizedValue]);
};

export const getValidation = (originalField) => {
	const validation = getSettingsContextProperty(
		originalField.settingsContext,
		'validation'
	);

	return validation;
};

export const createDuplicatedField = (originalField, props, blacklist = []) => {
	const {
		availableLanguageIds,
		defaultLanguageId,
		fieldNameGenerator,
		generateFieldNameUsingFieldLabel,
	} = props;
	const newFieldName = fieldNameGenerator(
		getDefaultFieldName(),
		null,
		blacklist
	);

	let duplicatedField = updateField(
		props,
		originalField,
		'name',
		newFieldName
	);

	duplicatedField = updateField(
		props,
		duplicatedField,
		'fieldReference',
		newFieldName
	);

	duplicatedField.instanceId = generateInstanceId(8);

	availableLanguageIds.forEach((availableLanguageId) => {
		const label = getLabel(
			originalField,
			defaultLanguageId,
			availableLanguageId
		);

		if (label) {
			duplicatedField = updateFieldLabel(
				defaultLanguageId,
				availableLanguageId,
				fieldNameGenerator,
				duplicatedField,
				generateFieldNameUsingFieldLabel,
				label
			);
		}
	});

	if (duplicatedField.nestedFields?.length > 0) {
		duplicatedField.nestedFields = duplicatedField.nestedFields.map(
			(field) => {
				const newDuplicatedNestedField = createDuplicatedField(
					field,
					props,
					blacklist
				);

				blacklist.push(newDuplicatedNestedField.fieldName);

				const visitor = new PagesVisitor([
					{
						rows: duplicatedField.rows ?? [],
					},
				]);

				const layout = visitor.mapColumns((column) => {
					return {
						...column,
						fields: column.fields.map((fieldName) => {
							if (fieldName === field.fieldName) {
								return newDuplicatedNestedField.fieldName;
							}

							return fieldName;
						}),
					};
				});

				duplicatedField.rows = layout[0].rows;

				return newDuplicatedNestedField;
			}
		);

		duplicatedField.settingsContext = updateSettingsContextProperty(
			props.editingLanguageId,
			duplicatedField.settingsContext,
			'rows',
			duplicatedField.rows
		);
	}

	duplicatedField.settingsContext = updateSettingsContextInstanceId(
		duplicatedField
	);

	return updateField(
		props,
		duplicatedField,
		'validation',
		getValidation(duplicatedField)
	);
};

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

					let pages = [{rows: field.rows}];

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
