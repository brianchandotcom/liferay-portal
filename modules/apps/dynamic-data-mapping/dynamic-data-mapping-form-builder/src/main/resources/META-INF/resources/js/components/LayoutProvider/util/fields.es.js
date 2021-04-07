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
	normalizeFieldName,
} from 'data-engine-js-components-web';

import {
	getDefaultFieldName,
	localizeField,
} from '../../../util/fieldSupport.es';
import {sub} from '../../../util/strings.es';
import {
	getSettingsContextProperty,
	updateField,
	updateFieldLabel,
	updateSettingsContextInstanceId,
	updateSettingsContextProperty,
} from '../util/settingsContext.es';

export const generateFieldName = (
	pages,
	desiredName,
	currentName = null,
	blacklist = [],
	generateFieldNameUsingFieldLabel
) => {
	let fieldName;
	let existingField;

	if (generateFieldNameUsingFieldLabel) {
		let counter = 0;

		fieldName = normalizeFieldName(desiredName);

		existingField = FormSupport.findFieldByFieldName(pages, fieldName);

		while (
			(existingField && existingField.fieldName !== currentName) ||
			blacklist.includes(fieldName)
		) {
			if (counter > 0) {
				fieldName = normalizeFieldName(desiredName) + counter;
			}

			existingField = FormSupport.findFieldByFieldName(pages, fieldName);

			counter++;
		}

		return normalizeFieldName(fieldName);
	}
	else {
		fieldName = desiredName;

		existingField = FormSupport.findFieldByFieldName(pages, fieldName);

		while (
			(existingField && existingField.fieldName !== currentName) ||
			blacklist.includes(fieldName)
		) {
			fieldName = getDefaultFieldName();

			existingField = FormSupport.findFieldByFieldName(pages, fieldName);
		}

		return fieldName;
	}
};

export const getFieldProperty = (pages, fieldName, propertyName) => {
	const visitor = new PagesVisitor(pages);
	let propertyValue;

	visitor.mapFields(
		(field) => {
			if (field.fieldName === fieldName) {
				propertyValue = field[propertyName];
			}
		},
		true,
		true
	);

	return propertyValue;
};

export const getFieldValue = (pages, fieldName) => {
	return getFieldProperty(pages, fieldName, 'value');
};

export const getField = (pages, fieldName) => {
	const visitor = new PagesVisitor(pages);
	let field;

	visitor.mapFields((currentField) => {
		if (currentField.fieldName === fieldName) {
			field = currentField;
		}
	});

	return field;
};

export const getFieldLocalizedValue = (pages, fieldName, locale) => {
	const fieldLocalizedValue = getFieldProperty(
		pages,
		fieldName,
		'localizedValue'
	);

	return fieldLocalizedValue[locale];
};

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

export const updateFieldValidationProperty = (
	pages,
	fieldName,
	propertyName,
	propertyValue
) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields((field) => {
		if (field.fieldName === 'validation' && field.value) {
			const expression = field.value.expression;

			if (
				propertyName === 'fieldName' &&
				expression &&
				expression.value
			) {
				expression.value = expression.value.replace(
					fieldName,
					propertyValue
				);
			}

			field = {
				...field,
				validation: {
					...field.validation,
					[propertyName]: propertyValue,
				},
				value: {
					...field.value,
					expression,
				},
			};
		}

		return field;
	});
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
		editingLanguageId,
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

				let {rows = []} = duplicatedField;

				if (typeof rows === 'string') {
					rows = JSON.parse(rows);
				}

				const visitor = new PagesVisitor([
					{
						rows,
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
			defaultLanguageId,
			props.editingLanguageId,
			duplicatedField.settingsContext,
			'rows',
			duplicatedField.rows
		);

		duplicatedField.ddmStructureLayoutId = '';

		duplicatedField.settingsContext = updateSettingsContextProperty(
			defaultLanguageId,
			props.editingLanguageId,
			duplicatedField.settingsContext,
			'ddmStructureLayoutId',
			duplicatedField.ddmStructureLayoutId
		);
	}

	const settingsContext = updateSettingsContextInstanceId(duplicatedField);

	const settingsVisitor = new PagesVisitor(settingsContext.pages);

	duplicatedField.settingsContext = {
		...settingsContext,
		pages: settingsVisitor.mapFields((field) =>
			localizeField(field, defaultLanguageId, editingLanguageId)
		),
	};

	return updateField(
		props,
		duplicatedField,
		'validation',
		getValidation(duplicatedField)
	);
};

export const findInvalidFieldReference = (focusedField, pages, value) => {
	let hasInvalidFieldReference = false;

	const visitor = new PagesVisitor(pages);

	visitor.mapFields(
		(field) => {
			const fieldReference = getSettingsContextProperty(
				field.settingsContext,
				'fieldReference'
			);

			if (
				focusedField.fieldName !== field.fieldName &&
				fieldReference?.toLowerCase() === value?.toLowerCase()
			) {
				hasInvalidFieldReference = true;
			}
		},
		true,
		true
	);

	return hasInvalidFieldReference;
};
